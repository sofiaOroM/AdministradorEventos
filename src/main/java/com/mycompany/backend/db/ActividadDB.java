/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.backend.db;

import com.mycompany.backend.model.Actividad;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sofia
 */
public class ActividadDB {

    public List<Actividad> findAllByCongreso(String congresoId) throws SQLException {
        List<Actividad> lista = new ArrayList<>();
            String sql = "SELECT a.*, s.nombre AS salonNombre, c.titulo AS congresoTitulo " +
                 "FROM actividad a " +
                 "JOIN salon s ON a.salon_id = s.id " +
                 "JOIN congreso c ON a.congreso_id = c.id " +
                 "WHERE a.congreso_id=?";
            /*String sql = "SELECT a.*, s.nombre AS salonNombre "
                + "FROM actividad a "
                + "JOIN salon s ON a.salon_id = s.id "
                + "WHERE a.congreso_id=?";*/
        try (Connection conn = DBConnectionSingleton.getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, congresoId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Actividad a = new Actividad();
                    a.setId(rs.getInt("id"));
                    a.setNombre(rs.getString("nombre"));
                    a.setDescripcion(rs.getString("descripcion"));
                    a.setTipo(rs.getString("tipo"));
                    a.setFecha(rs.getDate("fecha").toLocalDate());
                    a.setHoraInicio(rs.getTime("hora_inicio").toLocalTime());
                    a.setHoraFin(rs.getTime("hora_fin").toLocalTime());
                    a.setSalonId(rs.getInt("salon_id"));
                    a.setSalonNombre(rs.getString("salonNombre"));
                    a.setCongresoId(rs.getString("congreso_id"));
                    a.setCongresoTitulo(rs.getString("congresoTitulo"));
                    a.setCupo(rs.getInt("cupo"));
                    lista.add(a);
                }
            }
        }
        return lista;
    }

    public Actividad findById(int id) throws SQLException {
        String sql = "SELECT id, nombre, descripcion, tipo, fecha, hora_inicio, hora_fin, salon_id, congreso_id, cupo "
                + "FROM actividad WHERE id=?";
        try (Connection c = DBConnectionSingleton.getInstance().getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Actividad a = new Actividad();
                    a.setId(rs.getInt("id"));
                    a.setNombre(rs.getString("nombre"));
                    a.setDescripcion(rs.getString("descripcion"));
                    a.setTipo(rs.getString("tipo"));
                    a.setFecha(rs.getDate("fecha").toLocalDate());
                    a.setHoraInicio(rs.getTime("hora_inicio").toLocalTime());
                    a.setHoraFin(rs.getTime("hora_fin").toLocalTime());
                    a.setSalonId(rs.getInt("salon_id"));
                    a.setCongresoId(rs.getString("congreso_id"));
                    a.setCupo(rs.getInt("cupo"));
                    return a;
                }
            }
        }
        return null;
    }

    public List<Actividad> findAllJoinBasic() throws SQLException {
        List<Actividad> lista = new ArrayList<>();
        String sql = "SELECT a.*, c.titulo AS congresoTitulo " +
                 "FROM actividad a " +
                 "JOIN congreso c ON a.congreso_id = c.id " +
                 "ORDER BY a.fecha, a.hora_inicio";
        /*String sql = "SELECT id, nombre, fecha, hora_inicio, hora_fin, congreso_id "
                + "FROM actividad ORDER BY fecha, hora_inicio";*/
        try (Connection c = DBConnectionSingleton.getInstance().getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Actividad a = new Actividad();
                a.setId(rs.getInt("id"));
                a.setNombre(rs.getString("nombre"));
                a.setFecha(rs.getDate("fecha").toLocalDate());
                a.setHoraInicio(rs.getTime("hora_inicio").toLocalTime());
                a.setHoraFin(rs.getTime("hora_fin").toLocalTime());
                a.setCongresoId(rs.getString("congreso_id"));
                a.setCongresoTitulo(rs.getString("congresoTitulo"));
                lista.add(a);
            }
        }
        return lista;
    }

    public void insert(Actividad a) throws SQLException {
        // Validación de horas
        if (a.getHoraInicio().isAfter(a.getHoraFin())) {
            throw new SQLException("La hora de inicio no puede ser posterior a la hora de fin.");
        }

        String sql = "INSERT INTO actividad (nombre, descripcion, tipo, fecha, hora_inicio, hora_fin, salon_id, congreso_id, cupo) "
                + "VALUES (?,?,?,?,?,?,?,?,?)";
        try (Connection conn = DBConnectionSingleton.getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, a.getNombre());
            ps.setString(2, a.getDescripcion());
            ps.setString(3, a.getTipo());
            ps.setDate(4, java.sql.Date.valueOf(a.getFecha()));
            ps.setTime(5, Time.valueOf(a.getHoraInicio()));
            ps.setTime(6, Time.valueOf(a.getHoraFin()));
            ps.setInt(7, a.getSalonId());
            ps.setString(8, a.getCongresoId());
            if ("TALLER".equalsIgnoreCase(a.getTipo())) {
                ps.setInt(9, a.getCupo());
            } else {
                ps.setNull(9, Types.INTEGER);
            }
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM actividad WHERE id=?";
        try (Connection conn = DBConnectionSingleton.getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public void update(Actividad a) throws SQLException {
        String sql = "UPDATE actividad SET nombre=?, descripcion=?, hora_inicio=?, hora_fin=?, salon_id=?, cupo=? WHERE id=?";
        try (Connection conn = DBConnectionSingleton.getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, a.getNombre());
            ps.setString(2, a.getDescripcion());
            ps.setTime(3, Time.valueOf(a.getHoraInicio()));
            ps.setTime(4, Time.valueOf(a.getHoraFin()));
            ps.setInt(5, a.getSalonId());
            if ("TALLER".equalsIgnoreCase(a.getTipo())) {
                ps.setInt(6, a.getCupo());
            } else {
                ps.setNull(6, Types.INTEGER);
            }
            ps.setInt(7, a.getId());
            ps.executeUpdate();
        }
    }

    public boolean existeConflictoHorario(int salonId, Time inicio, Time fin, LocalDate fecha, Integer excludeId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM actividad "
                + "WHERE salon_id=? AND fecha= ? "
                + "AND ( (hora_inicio < ? AND hora_fin > ?) "
                + "   OR (hora_inicio < ? AND hora_fin > ?) "
                + "   OR (hora_inicio >= ? AND hora_fin <= ?) )";

        if (excludeId != null) {
            sql += " AND id<>?";
        }

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, salonId);
            ps.setDate(2, Date.valueOf(fecha));
            ps.setTime(3, fin);      // Para comparar inicio de otras
            ps.setTime(4, inicio);
            ps.setTime(5, inicio);   // Para comparar fin de otras
            ps.setTime(6, fin);
            ps.setTime(7, inicio);
            ps.setTime(8, fin);

            if (excludeId != null) {
                ps.setInt(9, excludeId);
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
}
