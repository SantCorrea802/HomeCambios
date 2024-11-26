<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="Beans.Usuario" %>
<%
    // Obtener la sesión activa
    Usuario usuario = (Usuario) session.getAttribute("usuario");

    if (usuario == null || usuario.getRol() != 2) { // Solo empleados
        response.sendRedirect("index.html"); 
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Menú Empleado</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css">
</head>
<body>
    <header class="bg-primary text-white text-center py-3">
        <div class="container d-flex justify-content-between align-items-center">
            <h1 class="mb-0">Menú Empleado</h1>
            <a href="logout.jsp" class="btn btn-link text-white">Cerrar sesión</a>
        </div>
    </header>
    <main class="container text-center my-5">
        <h2>¿Qué deseas hacer?</h2>
        <div class="d-flex justify-content-center mt-4 gap-3">
            <button onclick="window.open('eliminarUsuario.jsp', 'EliminarUsuario', 'width=600,height=400');" class="btn btn-danger">Eliminar Usuario</button>
            <button onclick="window.open('modificarUsuario.jsp', 'ModificarUsuario', 'width=600,height=400');" class="btn btn-warning">Modificar Usuario a Empleado</button>
            <button onclick="window.open('obtenerInformacionUsuario.jsp', 'ObtenerInformacionUsuario', 'width=600,height=400');" class="btn btn-success">Obtener información de un usuario</button>

        </div>
    </main>
    <footer class="bg-primary text-white text-center py-3">
        <p>&copy; 2024 Money ExChange</p>
    </footer>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
