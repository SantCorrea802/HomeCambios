/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Beans.Usuario;
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author HP
 */
@WebServlet("/ingresarDinero")
public class IngresarDineroServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = request.getReader().readLine()) != null) {
            sb.append(line);
        }

        JsonObject jsonRequest = JsonParser.parseString(sb.toString()).getAsJsonObject();
        String moneda = jsonRequest.get("moneda").getAsString();
        double cantidad = jsonRequest.get("cantidad").getAsDouble();

        if (cantidad <= 0) {
            response.getWriter().write("{\"success\": false, \"message\": \"Cantidad no válida.\"}");
            return;
        }

        boolean actualizado = ControlUsuarios.actualizarDinero(usuario.getId(), "credito_" + moneda, cantidad);

        if (actualizado) {
            double nuevoCredito = ControlUsuarios.obtenerCredito(usuario.getId(), "credito_" + moneda);
            // actualizar el objeto usuario con los nuevos valores
            if (moneda.equals("mxn")) usuario.setCreditoMxn(nuevoCredito);
            if (moneda.equals("cop")) usuario.setCreditoCop(nuevoCredito);
            if (moneda.equals("usd")) usuario.setCreditoUsd(nuevoCredito);
            if (moneda.equals("eur")) usuario.setCreditoEur(nuevoCredito);
            if (moneda.equals("jpy")) usuario.setCreditoJpy(nuevoCredito);

            session.setAttribute("usuario", usuario); // actualizar el usuario en la sesión
            response.getWriter().write("{\"success\": true}");
        } else {
            response.getWriter().write("{\"success\": false, \"message\": \"No se pudo actualizar el crédito.\"}");
        }
        System.out.println("Usuario ID: " + usuario.getId());
        System.out.println("Moneda: " + moneda);
        System.out.println("Cantidad a ingresar: " + cantidad);

    }

}