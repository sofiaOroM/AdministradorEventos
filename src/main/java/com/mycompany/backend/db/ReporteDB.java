/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.backend.db;

import com.mycompany.backend.model.Congreso;
import com.mycompany.backend.model.Usuario;
import com.mycompany.backend.model.reportes.ReporteGanancias;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author sofia
 */
public class ReporteDB {

    public List<ReporteGanancias> gananciasPorIntervalo(Date inicio, Date fin, Integer institucionId) throws SQLException {
        List<ReporteGanancias> lista = new ArrayList<>();

        String sql = "SELECT i.nombre AS institucionNombre, "
                + "       c.id AS congresoId, c.titulo, c.fecha_inicio, c.fecha_fin, c.precio, "
                + "       COUNT(p.usuario_id) AS inscritos, "
                + "       (COUNT(p.usuario_id) * c.precio) AS recaudado, "
                + "       (COUNT(p.usuario_id) * c.precio) * cfg.comision/100 AS comision, "
                + "       (COUNT(p.usuario_id) * c.precio) - ((COUNT(p.usuario_id) * c.precio) * cfg.comision/100) AS ganancia "
                + "FROM congreso c "
                + "JOIN institucion i ON c.institucion_id = i.id "
                + "LEFT JOIN pago p ON c.id = p.congreso_id "
                + "JOIN configuracion cfg ON 1=1 "
                + "WHERE (? IS NULL OR c.fecha_inicio >= ?) "
                + "  AND (? IS NULL OR c.fecha_fin <= ?) "
                + "  AND (? IS NULL OR i.id = ?) "
                + "GROUP BY i.nombre, c.id, c.titulo, c.fecha_inicio, c.fecha_fin, c.precio, cfg.comision "
                + "ORDER BY i.nombre ASC, ganancia DESC";

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            // parámetros (inicio y fin pueden ser null)
            ps.setDate(1, inicio);
            ps.setDate(2, inicio);
            ps.setDate(3, fin);
            ps.setDate(4, fin);
            if (institucionId != null) {
                ps.setInt(5, institucionId);
                ps.setInt(6, institucionId);
            } else {
                ps.setNull(5, java.sql.Types.INTEGER);
                ps.setNull(6, java.sql.Types.INTEGER);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ReporteGanancias r = new ReporteGanancias();
                    r.setInstitucionNombre(rs.getString("institucionNombre"));
                    r.setCongresoId(rs.getInt("congresoId"));
                    r.setTitulo(rs.getString("titulo"));
                    r.setFechaInicio(rs.getDate("fecha_inicio").toLocalDate());
                    r.setFechaFin(rs.getDate("fecha_fin").toLocalDate());
                    r.setPrecio(rs.getBigDecimal("precio"));
                    r.setInscritos(rs.getInt("inscritos"));
                    r.setRecaudado(rs.getBigDecimal("recaudado"));
                    r.setComision(rs.getBigDecimal("comision"));
                    r.setGanancia(rs.getBigDecimal("ganancia"));
                    lista.add(r);
                }
            }
        }
        return lista;
    }

    public List<Congreso> congresosPorInstitucion(Date inicio, Date fin, Integer institucionId) throws SQLException {
        List<Congreso> lista = new ArrayList<>();

        String sql = "SELECT i.nombre AS institucionNombre, "
                + "       c.id, c.titulo, c.fecha_inicio, c.fecha_fin, c.precio "
                + "FROM congreso c "
                + "JOIN institucion i ON c.institucion_id = i.id "
                + "WHERE (? IS NULL OR c.fecha_inicio >= ?) "
                + "  AND (? IS NULL OR c.fecha_inicio <= ?) "
                + "  AND (? IS NULL OR i.id = ?) "
                + "ORDER BY i.nombre ASC, c.fecha_inicio ASC";

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, inicio);
            ps.setDate(2, inicio);
            ps.setDate(3, fin);
            ps.setDate(4, fin);

            if (institucionId != null) {
                ps.setInt(5, institucionId);
                ps.setInt(6, institucionId);
            } else {
                ps.setNull(5, java.sql.Types.INTEGER);
                ps.setNull(6, java.sql.Types.INTEGER);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Congreso c = new Congreso();
                    c.setId(rs.getString("id"));
                    c.setTitulo(rs.getString("titulo"));
                    c.setFechaInicio(rs.getDate("fecha_inicio").toLocalDate());
                    c.setFechaFin(rs.getDate("fecha_fin").toLocalDate());
                    c.setPrecio(rs.getBigDecimal("precio").doubleValue());
                    c.setInstitucionNombre(rs.getString("institucionNombre"));
                    lista.add(c);
                }
            }
        }
        return lista;
    }

    public List<Usuario> participantesPorCongreso(int congresoId, String tipo) throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT u.id, u.nombre, u.organizacion, u.correo, u.telefono, u.identificacion, ip.tipo "
                + "FROM inscripcion i "
                + "JOIN usuario u ON i.usuario_id = u.id "
                + "JOIN inscripcion_participacion ip ON i.id = ip.inscripcion_id "
                + "WHERE i.congreso_id=? "
                + "AND (? IS NULL OR ip.tipo=?)";

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, congresoId);
            ps.setString(2, tipo);
            ps.setString(3, tipo);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Usuario u = new Usuario();
                    u.setId(rs.getInt("id"));
                    u.setNombre(rs.getString("nombre"));
                    u.setOrganizacion(rs.getString("organizacion"));
                    u.setCorreo(rs.getString("correo"));
                    u.setTelefono(rs.getString("telefono"));
                    u.setIdentificacion(rs.getString("identificacion"));
                    u.setRol(rs.getString("tipo"));
                    lista.add(u);
                }
            }
        }
        return lista;
    }

    public List<Map<String, Object>> asistenciaPorActividad(Integer actividadId, Integer salonId, Date inicio, Date fin) throws SQLException {
        List<Map<String, Object>> lista = new ArrayList<>();
        String sql = "SELECT a.nombre, s.nombre AS salon, a.fecha, a.hora_inicio, a.hora_fin, COUNT(asist.id) AS asistencias "
                + "FROM actividad a "
                + "JOIN salon s ON a.salon_id = s.id "
                + "LEFT JOIN asistencia asist ON a.id = asist.actividad_id "
                + "WHERE (? IS NULL OR a.id=?) "
                + "AND (? IS NULL OR s.id=?) "
                + "AND (? IS NULL OR a.fecha >= ?) "
                + "AND (? IS NULL OR a.fecha <= ?) "
                + "GROUP BY a.id, s.nombre, a.fecha, a.hora_inicio, a.hora_fin";

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, actividadId);
            ps.setObject(2, actividadId);
            ps.setObject(3, salonId);
            ps.setObject(4, salonId);
            ps.setDate(5, inicio);
            ps.setDate(6, inicio);
            ps.setDate(7, fin);
            ps.setDate(8, fin);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> fila = new HashMap<>();
                    fila.put("actividad", rs.getString("nombre"));
                    fila.put("salon", rs.getString("salon"));
                    fila.put("fecha", rs.getDate("fecha"));
                    fila.put("horaInicio", rs.getTime("hora_inicio"));
                    fila.put("horaFin", rs.getTime("hora_fin"));
                    fila.put("asistencias", rs.getInt("asistencias"));
                    lista.add(fila);
                }
            }
        }
        return lista;
    }

    public List<Map<String, Object>> reservasDeTaller(int tallerId) throws SQLException {
        List<Map<String, Object>> lista = new ArrayList<>();
        String sql = "SELECT a.id, a.nombre, a.cupo, COUNT(i.id) AS reservas, "
                + "(a.cupo - COUNT(i.id)) AS disponibles "
                + "FROM actividad a "
                + "LEFT JOIN inscripcion i ON a.id = i.actividad_id "
                + "WHERE a.id=? AND a.tipo='TALLER' "
                + "GROUP BY a.id, a.nombre, a.cupo";

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, tallerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> fila = new HashMap<>();
                    fila.put("id", rs.getInt("id"));
                    fila.put("nombre", rs.getString("nombre"));
                    fila.put("cupo", rs.getInt("cupo"));
                    fila.put("reservas", rs.getInt("reservas"));
                    fila.put("disponibles", rs.getInt("disponibles"));
                    lista.add(fila);
                }
            }
        }
        return lista;
    }

    public List<Map<String, Object>> gananciasPorCongreso(Integer congresoId, Date inicio, Date fin) throws SQLException {
        List<Map<String, Object>> lista = new ArrayList<>();
        String sql = "SELECT c.id, c.titulo, SUM(p.monto) AS recaudado, "
                + "SUM(p.monto * c.comision/100) AS comision, "
                + "SUM(p.monto) - SUM(p.monto * c.comision/100) AS ganancia "
                + "FROM pago p "
                + "JOIN congreso c ON p.congreso_id = c.id "
                + "WHERE (? IS NULL OR c.id=?) "
                + "AND (? IS NULL OR p.fecha >= ?) "
                + "AND (? IS NULL OR p.fecha <= ?) "
                + "GROUP BY c.id, c.titulo, c.comision";

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, congresoId);
            ps.setObject(2, congresoId);
            ps.setDate(3, inicio);
            ps.setDate(4, inicio);
            ps.setDate(5, fin);
            ps.setDate(6, fin);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> fila = new HashMap<>();
                    fila.put("id", rs.getInt("id"));
                    fila.put("titulo", rs.getString("titulo"));
                    fila.put("recaudado", rs.getBigDecimal("recaudado"));
                    fila.put("comision", rs.getBigDecimal("comision"));
                    fila.put("ganancia", rs.getBigDecimal("ganancia"));
                    lista.add(fila);
                }
            }
        }
        return lista;
    }

}
