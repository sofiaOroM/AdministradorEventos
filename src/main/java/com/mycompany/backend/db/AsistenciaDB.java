/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.backend.db;

import com.mycompany.backend.model.Asistencia;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sofia
 */
public class AsistenciaDB {

    public boolean existeAsistencia(int actividadId, int usuarioId) throws SQLException {
        String sql = "SELECT 1 FROM asistencia WHERE actividad_id=? AND usuario_id=? LIMIT 1";
        try (Connection c = DBConnectionSingleton.getInstance().getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, actividadId);
            ps.setInt(2, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void registrar(int actividadId, int usuarioId, LocalDate fecha) throws SQLException {
        String sql = "INSERT INTO asistencia (actividad_id, usuario_id, fecha) VALUES (?,?,?)";
        try (Connection c = DBConnectionSingleton.getInstance().getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, actividadId);
            ps.setInt(2, usuarioId);
            ps.setDate(3, Date.valueOf(fecha));
            ps.executeUpdate();
        }
    }

    public List<Asistencia> listarPorActividad(int actividadId) throws SQLException {
        List<Asistencia> lista = new ArrayList<>();
        String sql = "SELECT a.id, a.actividad_id, a.usuario_id, a.fecha, a.hora_registro, "
                + "u.nombre AS usuarioNombre, u.correo AS usuarioCorreo, act.nombre AS actividadNombre "
                + "FROM asistencia a "
                + "JOIN usuario u ON u.id = a.usuario_id "
                + "JOIN actividad act ON act.id = a.actividad_id "
                + "WHERE a.actividad_id=? ORDER BY a.hora_registro DESC";
        try (Connection c = DBConnectionSingleton.getInstance().getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, actividadId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Asistencia as = new Asistencia();
                    as.setId(rs.getLong("id"));
                    as.setActividadId(rs.getInt("actividad_id"));
                    as.setUsuarioId(rs.getInt("usuario_id"));
                    as.setFecha(rs.getDate("fecha").toLocalDate());
                    as.setHoraRegistro(rs.getTimestamp("hora_registro").toLocalDateTime());
                    as.setUsuarioNombre(rs.getString("usuarioNombre"));
                    as.setUsuarioCorreo(rs.getString("usuarioCorreo"));
                    as.setActividadNombre(rs.getString("actividadNombre"));
                    lista.add(as);
                }
            }
        }
        return lista;
    }

    public int contarAsistencias(int actividadId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM asistencia WHERE actividad_id=?";
        try (Connection c = DBConnectionSingleton.getInstance().getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, actividadId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

}
