/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.backend.db;

import com.mycompany.backend.model.Usuario;
import com.mycompany.backend.util.PasswordUtil;
import java.sql.*;

/**
 *
 * @author sofia
 */
public class UsuarioDB {
    
    public void crear(Usuario u) throws SQLException {
        
        try (Connection conn = DBConnectionSingleton.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("INSERT INTO usuario ("
                + "nombre, organizacion, correo, telefono, "
                + "identificacion, password, rol, activo, foto )"
                + "VALUES (?,?,?,?,?,?,?,?,?)")){
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
        if (rs.next()){
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
        if (rs.next()){
            return mapUsuario(rs);
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
}
