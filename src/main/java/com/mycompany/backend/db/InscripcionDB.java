/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.backend.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.mycompany.backend.model.Usuario;
/**
 *
 * @author sofia
 */
public class InscripcionDB {

    public boolean existeInscripcion(int usuarioId, int actividadId) throws SQLException {
        String sql = "SELECT 1 FROM inscripcion WHERE usuario_id=? AND actividad_id=? LIMIT 1";
        try (Connection c = DBConnectionSingleton.getInstance().getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            ps.setInt(2, actividadId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void inscribir(int usuarioId, int actividadId) throws SQLException {
        String sql = "INSERT INTO inscripcion (usuario_id, actividad_id) VALUES (?, ?)";
        try (Connection c = DBConnectionSingleton.getInstance().getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            ps.setInt(2, actividadId);
            ps.executeUpdate();
        }
    }

    public void eliminar(int usuarioId, int actividadId) throws SQLException {
        String sql = "DELETE FROM inscripcion WHERE usuario_id=? AND actividad_id=?";
        try (Connection c = DBConnectionSingleton.getInstance().getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            ps.setInt(2, actividadId);
            ps.executeUpdate();
        }
    }

    public int contarInscritos(int actividadId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM inscripcion WHERE actividad_id=?";
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
