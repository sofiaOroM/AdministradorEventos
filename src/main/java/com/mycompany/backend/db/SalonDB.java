/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.backend.db;

import com.mycompany.backend.model.Salon;
import java.sql.*;
import java.util.*;

/**
 *
 * @author sofia
 */
public class SalonDB {
    public List<Salon> findAll() throws SQLException {
        List<Salon> lista = new ArrayList<>();
        String sql = "SELECT * from salon";
        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Salon s = new Salon();
                s.setId(rs.getInt("id"));
                s.setNombre(rs.getString("nombre"));
                s.setUbicacion(rs.getString("ubicacion"));
                s.setCongresoId(rs.getString("congreso_id"));
                lista.add(s);
            }
        }
        return lista;
    }    
    public void insert(Salon s) throws SQLException {
        String sql = "INSERT INTO salon (nombre, ubicacion, congreso_id) VALUES (?,?,?)";
        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getNombre());
            ps.setString(2, s.getUbicacion());
            ps.setString(3, s.getCongresoId());
            ps.executeUpdate();
        }
    }

    public void update(Salon s) throws SQLException {
        String sql = "UPDATE salon SET nombre=?, ubicacion=?, congreso_id=? WHERE id=?";
        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getNombre());
            ps.setString(2, s.getUbicacion());
            ps.setString(3, s.getCongresoId());
            ps.setInt(4, s.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        // Solo se van a poder borra si no hay actividades asociadas
        String sql = "DELETE FROM salon WHERE id=? AND id NOT IN (SELECT DISTINCT salon_id FROM actividad)";
        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
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
