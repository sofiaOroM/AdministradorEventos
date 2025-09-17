/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.backend.db;

import com.mycompany.backend.model.Actividad;
import java.sql.*;
import java.util.*;
/**
 *
 * @author sofia
 */
public class ActividadDB {

    public List<Actividad> findAllByCongreso(String congresoId) throws SQLException {
        List<Actividad> lista = new ArrayList<>();
        String sql = "SELECT a.*, s.nombre AS salonNombre " +
                     "FROM actividad a " +
                     "JOIN salon s ON a.salon_id = s.id " +
                     "WHERE a.congreso_id=?";
        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, congresoId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Actividad a = new Actividad();
                    a.setId(rs.getInt("id"));
                    a.setNombre(rs.getString("nombre"));
                    a.setDescripcion(rs.getString("descripcion"));
                    a.setTipo(rs.getString("tipo"));
                    a.setHoraInicio(rs.getTime("hora_inicio").toLocalTime());
                    a.setHoraFin(rs.getTime("hora_fin").toLocalTime());
                    a.setSalonId(rs.getInt("salon_id"));
                    a.setSalonNombre(rs.getString("salonNombre"));
                    a.setCongresoId(rs.getString("congreso_id"));
                    a.setCupo(rs.getInt("cupo"));
                    lista.add(a);
                }
            }
        }
        return lista;
    }

    public void insert(Actividad a) throws SQLException {
        // Validación de horas
        if (a.getHoraInicio().isAfter(a.getHoraFin())) {
            throw new SQLException("La hora de inicio no puede ser posterior a la hora de fin.");
        }

        String sql = "INSERT INTO actividad (nombre, descripcion, tipo, hora_inicio, hora_fin, salon_id, congreso_id, cupo) " +
                     "VALUES (?,?,?,?,?,?,?,?)";
        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, a.getNombre());
            ps.setString(2, a.getDescripcion());
            ps.setString(3, a.getTipo());
            ps.setTime(4, Time.valueOf(a.getHoraInicio()));
            ps.setTime(5, Time.valueOf(a.getHoraFin()));
            ps.setInt(6, a.getSalonId());
            ps.setString(7, a.getCongresoId());
            if ("TALLER".equalsIgnoreCase(a.getTipo())) {
                ps.setInt(8, a.getCupo());
            } else {
                ps.setNull(8, Types.INTEGER);
            }
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM actividad WHERE id=?";
        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    } 
    
    public void update(Actividad a) throws SQLException {
        String sql = "UPDATE actividad SET nombre=?, descripcion=?, hora_inicio=?, hora_fin=?, salon_id=?, cupo=? WHERE id=?";
        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
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
    
    public boolean existeConflictoHorario(int salonId, Time inicio, Time fin, Integer excludeId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM actividad " +
                     "WHERE salon_id=? " +
                     "AND ( (hora_inicio < ? AND hora_fin > ?) " +
                     "   OR (hora_inicio < ? AND hora_fin > ?) " +
                     "   OR (hora_inicio >= ? AND hora_fin <= ?) )";

        if (excludeId != null) {
            sql += " AND id<>?";
        }

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, salonId);
            ps.setTime(2, fin);      // Para comparar inicio de otras
            ps.setTime(3, inicio);
            ps.setTime(4, inicio);   // Para comparar fin de otras
            ps.setTime(5, fin);
            ps.setTime(6, inicio);
            ps.setTime(7, fin);

            if (excludeId != null) {
                ps.setInt(8, excludeId);
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
