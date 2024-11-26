package Servlets;

import Beans.Usuario;
import Control.ControlUsuarios;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Dao.UsuarioDAO;
import java.io.PrintWriter;

@WebServlet("/obtenerCreditos")
public class ObtenerCreditosServlet extends HttpServlet {
    private UsuarioDAO usuarioDAO;

    public ObtenerCreditosServlet() {
        this.usuarioDAO = new UsuarioDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"success\": false, \"message\": \"No estás autenticado.\"}");
            return;
        }

        int userId = (int) session.getAttribute("userId");

        Usuario usuario = usuarioDAO.obtenerUsuarioPorId(userId);

        if (usuario != null) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            PrintWriter out = response.getWriter();
            out.write("{");
            out.write("\"success\": true,");
            out.write("\"credito_mxn\": " + usuario.getCreditoMxn() + ",");
            out.write("\"credito_cop\": " + usuario.getCreditoCop() + ",");
            out.write("\"credito_usd\": " + usuario.getCreditoUsd() + ",");
            out.write("\"credito_eur\": " + usuario.getCreditoEur() + ",");
            out.write("\"credito_jpy\": " + usuario.getCreditoJpy());
            out.write("}");
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("{\"success\": false, \"message\": \"Usuario no encontrado.\"}");
        }
    }
}