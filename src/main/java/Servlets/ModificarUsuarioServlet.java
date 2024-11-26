/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Control.ControlUsuarios;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author HP
 */
@WebServlet("/modificarUsuario")
public class ModificarUsuarioServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            // leer solicitud
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = request.getReader().readLine()) != null) {
                sb.append(line);
            }

            JsonObject jsonRequest = JsonParser.parseString(sb.toString()).getAsJsonObject();
            int userId = jsonRequest.get("id").getAsInt();

            // cambiar el rol
            boolean modificado = ControlUsuarios.modificarRolUsuario(userId, 2);

            JsonObject jsonResponse = new JsonObject();
            if (modificado) {
                jsonResponse.addProperty("success", true);
                jsonResponse.addProperty("message", "Usuario modificado correctamente.");
            } else {
                jsonResponse.addProperty("success", false);
                jsonResponse.addProperty("message", "El usuario no existe o no se pudo modificar.");
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