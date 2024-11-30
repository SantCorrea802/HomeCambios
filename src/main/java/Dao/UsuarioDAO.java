/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

/**
 *
 * @author HP
 */
import Control.Conexion;

import Beans.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioDAO {

    public Usuario obtenerUsuarioPorId(int userId) {
        String sql = "SELECT id, nombre, edad, credito_mxn, credito_cop, credito_usd, credito_eur, credito_jpy, correo, rol FROM usuarios WHERE id = ?";
        try (Connection conn = Conexion.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(null,rs.getString("correo"),rs.getInt("rol"),rs.getDouble("credito_mxn"),rs.getDouble("credito_usd"),rs.getDouble("credito_cop"),rs.getDouble("credito_eur"),rs.getDouble("credito_jpy"),rs.getString("nombre"),rs.getInt("edad"),rs.getInt("id")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}