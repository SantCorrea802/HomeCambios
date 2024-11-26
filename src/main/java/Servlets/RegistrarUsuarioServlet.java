/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Beans.Usuario;
import Control.ControlUsuarios;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author HP
 */
@WebServlet("/registrarUsuario")
public class RegistrarUsuarioServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            // parsear el js enviado desde el frontend
            JsonObject jsonRequest = JsonParser.parseReader(request.getReader()).getAsJsonObject();

            int id = jsonRequest.get("id").getAsInt();
            String nombre = jsonRequest.get("nombre").getAsString();
            int edad = jsonRequest.get("edad").getAsInt();
            String correo = jsonRequest.get("correo").getAsString();
            String pass = jsonRequest.get("pass").getAsString();

            // crear usuario con rol 1 y creditos 0
            Usuario nuevoUsuario = new Usuario(pass, correo, 1, 0, 0, 0, 0, 0, nombre, edad, id);

            // guardar el usuario en la base de datos
            boolean registrado = ControlUsuarios.registrarUsuario(nuevoUsuario);

            JsonObject jsonResponse = new JsonObject();
            if (registrado) {
                jsonResponse.addProperty("success", true);
                jsonResponse.addProperty("message", "Usuario registrado con éxito.");
            } else {
                jsonResponse.addProperty("success", false);
                jsonResponse.addProperty("message", "Error al registrar el usuario.");
            }
            response.getWriter().write(jsonResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
            JsonObject errorResponse = new JsonObject();
            errorResponse.addProperty("success", false);
            errorResponse.addProperty("message", "Error al procesar la solicitud.");
            response.getWriter().write(errorResponse.toString());
        }
    }
}