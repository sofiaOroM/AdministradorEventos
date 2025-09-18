/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.backend.db;

import com.mycompany.backend.model.PagoCongreso;
import java.math.BigDecimal;
import java.sql.*;

/**
 *
 * @author sofia
 */
public class PagoDB {

    public boolean existePagoConfirmado(int usuarioId, String congresoId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM pago_congreso WHERE usuario_id = ? AND congreso_id = ? AND estado = 'CONFIRMADO'";
        try (Connection conn = DBConnectionSingleton.getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            ps.setString(2, congresoId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    public void registrarPago(int usuarioId, String congresoId, BigDecimal monto) throws SQLException {
        String sql = "INSERT INTO pago_congreso (usuario_id, congreso_id, monto, estado) VALUES (?,?,?,'CONFIRMADO')";
        try (Connection conn = DBConnectionSingleton.getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            ps.setString(2, congresoId);
            ps.setBigDecimal(3, monto);
            ps.executeUpdate();
        }
    }
}
