/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.backend.db;

import com.mycompany.backend.model.Cartera;
import java.math.BigDecimal;
import java.sql.*;

/**
 *
 * @author sofia
 */
public class CarteraDB {

    public Cartera obtenerPorUsuario(int usuarioId) throws SQLException {
        String sql = "SELECT usuario_id, saldo FROM cartera WHERE usuario_id = ?";
        try (Connection conn = DBConnectionSingleton.getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Cartera c = new Cartera();
                c.setUsuarioId(rs.getInt("usuario_id"));
                c.setSaldo(rs.getBigDecimal("saldo"));
                return c;
            }
        }
        return null;
    }

    public void recargar(int usuarioId, BigDecimal monto) throws SQLException {
        String sql = "INSERT INTO cartera (usuario_id, saldo) VALUES (?, ?) "
                + "ON DUPLICATE KEY UPDATE saldo = saldo + VALUES(saldo)";
        try (Connection conn = DBConnectionSingleton.getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            ps.setBigDecimal(2, monto);
            ps.executeUpdate();
        }
    }

    public void descontar(int usuarioId, BigDecimal monto) throws SQLException {
        String sql = "UPDATE cartera SET saldo = saldo - ? WHERE usuario_id = ? AND saldo >= ?";
        try (Connection conn = DBConnectionSingleton.getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBigDecimal(1, monto);
            ps.setInt(2, usuarioId);
            ps.setBigDecimal(3, monto);
            ps.executeUpdate();
        }
    }
}
