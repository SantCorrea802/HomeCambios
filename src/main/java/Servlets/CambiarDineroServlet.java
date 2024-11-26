/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Beans.Usuario;
import Control.ControlUsuarios;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author HP
 */
@WebServlet("/cambiarDinero")
public class CambiarDineroServlet extends HttpServlet {
    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            // deserializar el json
            JsonObject jsonRequest = gson.fromJson(request.getReader(), JsonObject.class);

            // obtener sus datos
            String monedaOrigen = jsonRequest.get("monedaOrigen").getAsString();
            String monedaDestino = jsonRequest.get("monedaDestino").getAsString();
            double cantidad = jsonRequest.get("cantidad").getAsDouble();
            double cantidadDestino = jsonRequest.get("cantidadDestino").getAsDouble();

            //
            System.out.println("Cambiar Dinero Servlet");
            System.out.println("Usuario autenticado: " + (usuario != null ? "Sí" : "No"));
            if (usuario != null) {
                System.out.println("Usuario ID: " + usuario.getId());
                System.out.println("Moneda Origen: " + monedaOrigen);
                System.out.println("Moneda Destino: " + monedaDestino);
                System.out.println("Cantidad a cambiar: " + cantidad);
                System.out.println("Cantidad que se recibe: " + cantidadDestino);
            }

            if (usuario == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                JsonObject errorResponse = new JsonObject();
                errorResponse.addProperty("success", false);
                errorResponse.addProperty("message", "No estás autenticado.");
                response.getWriter().write(gson.toJson(errorResponse));
                return;
            }

            // actualizar los creditos en la base de datos
            boolean actualizado = ControlUsuarios.actualizarDinero(
                usuario.getId(),
                "credito_" + monedaOrigen.toLowerCase(),
                -cantidad
            ) && ControlUsuarios.actualizarDinero(
                usuario.getId(),
                "credito_" + monedaDestino.toLowerCase(),
                cantidadDestino
            );

            JsonObject jsonResponse = new JsonObject();
            if (actualizado) {
                // obtener los nuevos valores desde la base de datos
                double nuevoCreditoOrigen = ControlUsuarios.obtenerCredito(usuario.getId(), "credito_" + monedaOrigen.toLowerCase());
                double nuevoCreditoDestino = ControlUsuarios.obtenerCredito(usuario.getId(), "credito_" + monedaDestino.toLowerCase());

                // actualizar el objeto usuario con los nuevos valores
                if (monedaOrigen.equalsIgnoreCase("mxn")) usuario.setCreditoMxn(nuevoCreditoOrigen);
                if (monedaOrigen.equalsIgnoreCase("cop")) usuario.setCreditoCop(nuevoCreditoOrigen);
                if (monedaOrigen.equalsIgnoreCase("usd")) usuario.setCreditoUsd(nuevoCreditoOrigen);
                if (monedaOrigen.equalsIgnoreCase("eur")) usuario.setCreditoEur(nuevoCreditoOrigen);
                if (monedaOrigen.equalsIgnoreCase("jpy")) usuario.setCreditoJpy(nuevoCreditoOrigen);

                if (monedaDestino.equalsIgnoreCase("mxn")) usuario.setCreditoMxn(nuevoCreditoDestino);
                if (monedaDestino.equalsIgnoreCase("cop")) usuario.setCreditoCop(nuevoCreditoDestino);
                if (monedaDestino.equalsIgnoreCase("usd")) usuario.setCreditoUsd(nuevoCreditoDestino);
                if (monedaDestino.equalsIgnoreCase("eur")) usuario.setCreditoEur(nuevoCreditoDestino);
                if (monedaDestino.equalsIgnoreCase("jpy")) usuario.setCreditoJpy(nuevoCreditoDestino);

                // actualizar el usuario en la sesión
                session.setAttribute("usuario", usuario);

                jsonResponse.addProperty("success", true);
                jsonResponse.addProperty("message", "Cambio realizado con éxito.");
            } else {
                jsonResponse.addProperty("success", false);
                jsonResponse.addProperty("message", "Error al actualizar los créditos.");
            }

            response.getWriter().write(gson.toJson(jsonResponse));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error en el procesamiento de la solicitud: " + e.getMessage());
            JsonObject errorResponse = new JsonObject();
            errorResponse.addProperty("success", false);
            errorResponse.addProperty("message", "Error al procesar la solicitud.");
            response.getWriter().write(gson.toJson(errorResponse));
        }
    }
}
