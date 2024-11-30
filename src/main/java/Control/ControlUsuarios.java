/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Beans.Usuario;
import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author HP
 */
public class ControlUsuarios {

    // agregar un nuevo usuario
    public static boolean agregarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre, correo, pass, rol, credito_mxn, credito_usd, credito_cop, credito_eur, credito_jpy) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getCorreo());
            pstmt.setString(3, usuario.getPass()); 
            pstmt.setInt(4, usuario.getRol());
            pstmt.setDouble(5, usuario.getCreditoMxn());
            pstmt.setDouble(6, usuario.getCreditoUsd());
            pstmt.setDouble(7, usuario.getCreditoCop());
            pstmt.setDouble(8, usuario.getCreditoEur());
            pstmt.setDouble(9, usuario.getCreditoJpy());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // verificar credenciales y rol
    public static Usuario verificarCredenciales(String id, String pass) {
        // 
        String sql = "SELECT id, nombre, edad, correo, rol, credito_mxn, credito_usd, credito_cop, credito_eur, credito_jpy FROM usuarios WHERE id = ? AND pass = ?";
        Usuario usuario = null;

        try (Connection conn = Conexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            pstmt.setString(2, pass);

            System.out.println("Ejecutando consulta SQL: " + pstmt);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Usuario encontrado en la base de datos.");
                    usuario = new Usuario(null,rs.getString("correo"),rs.getInt("rol"),rs.getDouble("credito_mxn"),rs.getDouble("credito_usd"),rs.getDouble("credito_cop"),rs.getDouble("credito_eur"),rs.getDouble("credito_jpy"),rs.getString("nombre"),rs.getInt("edad"),rs.getInt("id")
                    );
                } else {
                    System.out.println("No se encontró ningún usuario con las credenciales proporcionadas.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }
    
    public static JsonObject obtenerCreditos(int userId) {
        String sql = "SELECT credito_mxn, credito_cop, credito_usd, credito_eur, credito_jpy FROM usuarios WHERE id = ?";
        JsonObject creditos = new JsonObject();

        try (Connection conn = Conexion.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    creditos.addProperty("credito_mxn", rs.getDouble("credito_mxn"));
                    creditos.addProperty("credito_cop", rs.getDouble("credito_cop"));
                    creditos.addProperty("credito_usd", rs.getDouble("credito_usd"));
                    creditos.addProperty("credito_eur", rs.getDouble("credito_eur"));
                    creditos.addProperty("credito_jpy", rs.getDouble("credito_jpy"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return creditos;
    }

    public static boolean actualizarDinero(int userId, String columna, double cantidad) {
        String sql = "UPDATE usuarios SET " + columna + " = " + columna + " + ? WHERE id = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, cantidad);
            pstmt.setInt(2, userId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    public static double obtenerCredito(int userId, String columna) {
        String sql = "SELECT " + columna + " FROM usuarios WHERE id = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1); // devuelve el valor de la columna
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // si hay un error o no se encuentra el usuario
    }
    
    
    public static boolean eliminarUsuario(int userId) {
        String query = "DELETE FROM usuarios WHERE id = ?";
        try (Connection connection = Conexion.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    

    public static boolean modificarRolUsuario(int userId, int nuevoRol) {
        String query = "UPDATE usuarios SET rol = ? WHERE id = ?";
        try (Connection connection = Conexion.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, nuevoRol);
            statement.setInt(2, userId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Usuario obtenerUsuarioPorId(int userId) {
        String query = "SELECT * FROM usuarios WHERE id = ?";
        try (Connection connection = Conexion.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Usuario(resultSet.getString("pass"),resultSet.getString("correo"),resultSet.getInt("rol"),resultSet.getDouble("credito_mxn"),resultSet.getDouble("credito_usd"),resultSet.getDouble("credito_cop"),resultSet.getDouble("credito_eur"),resultSet.getDouble("credito_jpy"),resultSet.getString("nombre"),resultSet.getInt("edad"),resultSet.getInt("id")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    
    public static boolean registrarUsuario(Usuario usuario) {
        String query = "INSERT INTO usuarios (id, nombre, edad, correo, pass, rol, credito_mxn, credito_usd, credito_cop, credito_eur, credito_jpy) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = Conexion.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, usuario.getId());
            statement.setString(2, usuario.getNombre());
            statement.setInt(3, usuario.getEdad());
            statement.setString(4, usuario.getCorreo());
            statement.setString(5, usuario.getPass());
            statement.setInt(6, usuario.getRol());
            statement.setDouble(7, usuario.getCreditoMxn());
            statement.setDouble(8, usuario.getCreditoUsd());
            statement.setDouble(9, usuario.getCreditoCop());
            statement.setDouble(10, usuario.getCreditoEur());
            statement.setDouble(11, usuario.getCreditoJpy());

            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    
}
