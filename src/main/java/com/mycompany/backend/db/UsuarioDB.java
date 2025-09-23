/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.backend.db;

import com.mycompany.backend.model.Usuario;
import com.mycompany.backend.util.PasswordUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sofia
 */
public class UsuarioDB {

    public void crear(Usuario u) throws SQLException {

        try (Connection conn = DBConnectionSingleton.getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement("INSERT INTO usuario ("
                + "nombre, organizacion, correo, telefono, "
                + "identificacion, password, rol, activo, foto )"
                + "VALUES (?,?,?,?,?,?,?,?,?)")) {
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getOrganizacion());
            ps.setString(3, u.getCorreo());
            ps.setString(4, u.getTelefono());
            ps.setString(5, u.getIdentificacion());
            ps.setString(6, PasswordUtil.encodeBase64(u.getPassword()));
            ps.setString(7, u.getRol());
            ps.setBoolean(8, u.isActivo());
            ps.setBytes(9, u.getFoto());

            ps.executeUpdate();
        }
    }

    public Usuario encontrarPorNombre(String nombre) throws SQLException {
        String sql = " SELECT * FROM usuario WHERE nombre=?";
        Connection conn = DBConnectionSingleton.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, nombre);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return mapUsuario(rs);
        }
        return null;
    }

    public Usuario encontrarPorCorreo(String correo) throws SQLException {
        String sql = " SELECT * FROM usuario WHERE correo=?";
        Connection conn = DBConnectionSingleton.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, correo);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return mapUsuario(rs);
        }
        return null;
    }

    public Usuario encontrarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE id = ?";
        try (Connection conn = DBConnectionSingleton.getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario u = new Usuario();
                    u.setId(rs.getInt("id"));
                    u.setNombre(rs.getString("nombre"));
                    u.setOrganizacion(rs.getString("organizacion"));
                    u.setCorreo(rs.getString("correo"));
                    u.setTelefono(rs.getString("telefono"));
                    u.setIdentificacion(rs.getString("identificacion"));
                    u.setPassword(rs.getString("password"));
                    u.setRol(rs.getString("rol"));
                    u.setActivo(rs.getBoolean("activo"));
                    u.setFoto(rs.getBytes("foto"));
                    return u;
                }
            }
        }
        return null;
    }

    public List<Usuario> findAll() throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuario";
        try (Connection conn = DBConnectionSingleton.getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapUsuario(rs));
            }
        }
        return lista;
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM usuario WHERE id=?";
        try (Connection conn = DBConnectionSingleton.getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public Usuario findByEmailOrIdentificacion(String s) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE correo=? OR identificacion=?";
        try (Connection c = DBConnectionSingleton.getInstance().getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, s);
            ps.setString(2, s);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario u = new Usuario();
                    u.setId(rs.getInt("id"));
                    u.setNombre(rs.getString("nombre"));
                    u.setCorreo(rs.getString("correo"));
                    u.setIdentificacion(rs.getString("identificacion"));
                    u.setRol(rs.getString("rol"));
                    u.setActivo(rs.getBoolean("activo"));
                    return u;
                }
            }
        }
        return null;
    }

    private Usuario mapUsuario(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setId(rs.getInt("Id"));
        u.setNombre(rs.getString("nombre"));
        u.setOrganizacion(rs.getString("organizacion"));
        u.setCorreo(rs.getString("correo"));
        u.setTelefono(rs.getString("telefono"));
        u.setIdentificacion(rs.getString("identificacion"));
        u.setPassword(rs.getString("password"));
        u.setRol(rs.getString("rol"));
        u.setActivo(rs.getBoolean("activo"));
        u.setFoto(rs.getBytes("foto"));
        return u;
    }

    public void updatePerfil(Usuario u) throws SQLException {
        String sql = "UPDATE usuario SET nombre=?, organizacion=?, correo=?, telefono=?, identificacion=?, password=?, foto=? WHERE id=?";
        try (Connection c = DBConnectionSingleton.getInstance().getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, u.getNombre());
            ps.setString(2, u.getOrganizacion());
            ps.setString(3, u.getCorreo());
            ps.setString(4, u.getTelefono());
            ps.setString(5, u.getIdentificacion());
            ps.setString(6, u.getPassword());
            ps.setBytes(7, u.getFoto());
            ps.setInt(8, u.getId());

            ps.executeUpdate();
        }
    }

    public void updateAdmin(Usuario u) throws SQLException {
        String sql = "UPDATE usuario SET nombre=?, organizacion=?, correo=?, telefono=?, identificacion=?, rol=?, activo=?, foto=? WHERE id=?";
        try (Connection conn = DBConnectionSingleton.getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getOrganizacion());
            ps.setString(3, u.getCorreo());
            ps.setString(4, u.getTelefono());
            ps.setString(5, u.getIdentificacion());
            ps.setString(6, u.getRol()); // el admin SÍ puede cambiar esto
            ps.setBoolean(7, u.isActivo());
            ps.setBytes(8, u.getFoto());
            ps.setInt(9, u.getId());
            ps.executeUpdate();
        }
    }
}
