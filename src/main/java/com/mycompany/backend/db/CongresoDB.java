/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.backend.db;

import com.mycompany.backend.model.Congreso;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sofia
 */
public class CongresoDB {
    public List<Congreso> findAll() throws SQLException {
        List<Congreso> lista = new ArrayList<>();
        String sql = "SELECT c.id, c.titulo, c.descripcion, c.fecha_inicio, c.fecha_fin, c.precio, " +
             "c.institucion_id, i.nombre AS institucionNombre " +
             "FROM congreso c JOIN institucion i ON c.institucion_id = i.id";

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Congreso c = new Congreso();
                c.setId(rs.getString("id"));
                c.setTitulo(rs.getString("titulo"));
                c.setDescripcion(rs.getString("descripcion"));
                c.setFechaInicio(rs.getDate("fecha_inicio").toLocalDate());
                c.setFechaFin(rs.getDate("fecha_fin").toLocalDate());
                c.setPrecio(rs.getDouble("precio"));
                c.setInstitucionId(rs.getInt("institucion_id"));
                //c.setInstitucionNombre(rs.getString("institucionNombre"));
                lista.add(c);
            }
        }
        return lista;
    }

    public void insert(Congreso c) throws SQLException {
        String sql = "INSERT INTO congreso (id, titulo, descripcion, fecha_inicio, fecha_fin, precio, institucion_id) VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getId());
            ps.setString(2, c.getTitulo());
            ps.setString(3, c.getDescripcion());
            ps.setDate(4, Date.valueOf(c.getFechaInicio()));
            ps.setDate(5, Date.valueOf(c.getFechaFin()));
            ps.setDouble(6, c.getPrecio());
            ps.setInt(7, c.getInstitucionId());
            ps.executeUpdate();
        }
    }
    
    public void update(Congreso c) throws SQLException {
        String sql = "UPDATE congreso SET titulo=?, descripcion=?, fecha_inicio=?, fecha_fin=?, precio=?, institucion_id=? WHERE id=?";
        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getTitulo());
            ps.setString(2, c.getDescripcion());
            ps.setDate(3, Date.valueOf(c.getFechaInicio()));
            ps.setDate(4, Date.valueOf(c.getFechaFin()));
            ps.setDouble(5, c.getPrecio());
            ps.setInt(6, c.getInstitucionId());
            ps.setString(7, c.getId());
            ps.executeUpdate();
        }
    }

    public void delete(String id) throws SQLException {
        String sql = "DELETE FROM congreso WHERE id=?";
        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        }
    }
}
