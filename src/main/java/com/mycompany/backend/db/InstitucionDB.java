/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.backend.db;

import com.mycompany.backend.model.Institucion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sofia
 */
public class InstitucionDB {

    public List<Institucion> listar() throws SQLException {
        List<Institucion> lista = new ArrayList<>();
        String sql = "SELECT id, nombre FROM institucion ORDER BY nombre ASC";
        try (Connection c = DBConnectionSingleton.getInstance().getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Institucion i = new Institucion();
                i.setId(rs.getInt("id"));
                i.setNombre(rs.getString("nombre"));
                lista.add(i);
            }
        }
        return lista;
    }

    public Institucion encontrarPorId(int id) throws SQLException {
        String sql = "SELECT id, nombre FROM institucion WHERE id=?";
        try (Connection c = DBConnectionSingleton.getInstance().getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Institucion i = new Institucion();
                    i.setId(rs.getInt("id"));
                    i.setNombre(rs.getString("nombre"));
                    return i;
                }
            }
        }
        return null;
    }

    public void insertar(String nombre) throws SQLException {
        String sql = "INSERT INTO institucion (nombre) VALUES (?)";
        try (Connection c = DBConnectionSingleton.getInstance().getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.executeUpdate();
        }
    }

    public void actualizar(int id, String nombre) throws SQLException {
        String sql = "UPDATE institucion SET nombre=? WHERE id=?";
        try (Connection c = DBConnectionSingleton.getInstance().getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM institucion WHERE id=?";
        try (Connection c = DBConnectionSingleton.getInstance().getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
