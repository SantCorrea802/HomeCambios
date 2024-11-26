package Servlets;

import Beans.Usuario;
import Control.Conexion;
import Control.ControlUsuarios;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.google.gson.Gson;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private Gson gson = new Gson();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = request.getReader().readLine()) != null) {
            sb.append(line);
        }

        System.out.println("JSON recibido del cliente: " + sb.toString()); 

        LoginRequest loginRequest = gson.fromJson(sb.toString(), LoginRequest.class);

        String id = loginRequest.getId();
        String pass = loginRequest.getPassword();

        System.out.println("ID recibido: " + id);
        System.out.println("Password recibido: " + pass);

        Usuario usuario = ControlUsuarios.verificarCredenciales(id, pass);

        if (usuario != null) {
            // guardar la sesion
            HttpSession session = request.getSession();
            session.setAttribute("usuario", usuario);

            session.setAttribute("userName", usuario.getNombre());

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            if (usuario.getRol() == 1) {
                System.out.println("Usuario autenticado como usuario regular.");
                response.getWriter().write("{\"success\": true, \"role\": 1}");
            } else if (usuario.getRol() == 2) {
                System.out.println("Usuario autenticado como empleado.");
                response.getWriter().write("{\"success\": true, \"role\": 2}");
            } else {
                System.out.println("Usuario con rol no reconocido.");
                response.getWriter().write("{\"success\": false, \"message\": \"Rol no reconocido.\"}");
            }
        } else {
            System.out.println("Usuario no registrado.");
            response.getWriter().write("{\"success\": false, \"message\": \"Usuario no registrado.\"}");
        }
    }

    private static class LoginRequest {
        private String id;
        private String password; // Cambiado de 'pass' a 'password'

        public String getId() {
            return id;
        }

        public String getPassword() { // Cambiado de 'getPass' a 'getPassword'
            return password;
        }
    }

}