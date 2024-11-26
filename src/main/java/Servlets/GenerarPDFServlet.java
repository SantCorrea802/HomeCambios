/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Beans.Usuario;
import Control.ControlUsuarios;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

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
@WebServlet("/generarPDF")
public class GenerarPDFServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userIdParam = request.getParameter("userId");

        System.out.println("Generar PDF ServletS");
        System.out.println("ID recibido: " + userIdParam);

        if (userIdParam == null || userIdParam.isEmpty()) {
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": false, \"message\": \"ID de usuario no proporcionado.\"}");
            return;
        }

        int userId;
        try {
            userId = Integer.parseInt(userIdParam);
        } catch (NumberFormatException e) {
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": false, \"message\": \"ID de usuario no válido.\"}");
            return;
        }

        // obgtener información del usuario
        Usuario usuario = ControlUsuarios.obtenerUsuarioPorId(userId);

        if (usuario == null) {
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": false, \"message\": \"Usuario no encontrado.\"}");
            return;
        }

        
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=usuario_" + userId + ".pdf");

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            document.add(new Paragraph("Información del Usuario"));
            document.add(new Paragraph("ID: " + usuario.getId()));
            document.add(new Paragraph("Nombre: " + usuario.getNombre()));
            document.add(new Paragraph("Correo: " + usuario.getCorreo()));
            document.add(new Paragraph("Edad: " + usuario.getEdad()));
            document.add(new Paragraph("Créditos:"));
            document.add(new Paragraph("  - MXN: " + usuario.getCreditoMxn()));
            document.add(new Paragraph("  - COP: " + usuario.getCreditoCop()));
            document.add(new Paragraph("  - USD: " + usuario.getCreditoUsd()));
            document.add(new Paragraph("  - EUR: " + usuario.getCreditoEur()));
            document.add(new Paragraph("  - JPY: " + usuario.getCreditoJpy()));

            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": false, \"message\": \"Error al generar el PDF.\"}");
        }
    }
}
