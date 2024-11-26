<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Beans.Usuario" %>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");

    if (usuario == null) {
        response.sendRedirect("index.html"); 
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Money Exchange - Tus Créditos</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css">
    <script>
        // Función para mostrar los creditos en la web
        function actualizarCreditos(data) {
            if (data) {
                document.getElementById('creditoMXN').textContent = data.creditoMxn || 0;
                document.getElementById('creditoCOP').textContent = data.creditoCop || 0;
                document.getElementById('creditoUSD').textContent = data.creditoUsd || 0;
                document.getElementById('creditoEUR').textContent = data.creditoEur || 0;
                document.getElementById('creditoJPY').textContent = data.creditoJpy || 0;
            }
        }
    </script>
</head>
<body>
    <header class="bg-primary text-white text-center py-3">
        <div class="container d-flex justify-content-between align-items-center">
            <h1 class="mb-0">Money Exchange</h1>
            <a href="logout.jsp" class="btn btn-danger">Cerrar Sesión</a>
        </div>
    </header>
    <main class="container text-center my-5">
        <section class="mb-5">
            <h2>Tus Créditos</h2>
            <div class="row justify-content-center mt-4">
                <div class="col-md-2">
                    <div class="credito-box">
                        <h3>MXN</h3>
                        <p id="creditoMXN">${usuario.creditoMxn}</p>
                    </div>
                </div>
                <div class="col-md-2">
                    <div class="credito-box">
                        <h3>COP</h3>
                        <p id="creditoCOP">${usuario.creditoCop}</p>
                    </div>
                </div>
                <div class="col-md-2">
                    <div class="credito-box">
                        <h3>USD</h3>
                        <p id="creditoUSD">${usuario.creditoUsd}</p>
                    </div>
                </div>
                <div class="col-md-2">
                    <div class="credito-box">
                        <h3>EUR</h3>
                        <p id="creditoEUR">${usuario.creditoEur}</p>
                    </div>
                </div>
                <div class="col-md-2">
                    <div class="credito-box">
                        <h3>JPY</h3>
                        <p id="creditoJPY">${usuario.creditoJpy}</p>
                    </div>
                </div>
            </div>
        </section>
        <section>
            <h2>¿Qué quieres hacer?</h2>
            <div class="btn-group mt-4">
                <!-- Botones que activan las ventanas emergentes -->
                <button onclick="window.open('cambiarDinero.jsp', 'CambiarDinero', 'width=600,height=400');" class="btn btn-primary">Cambiar Dinero</button>
                <button onclick="window.open('ingresarDinero.jsp', 'IngresarDinero', 'width=600,height=400');" class="btn btn-success">Ingresar Dinero</button>
            </div>
        </section>
    </main>
    <footer class="bg-primary text-white text-center py-3">
        <p>&copy; 2024 Money Exchange</p>
    </footer>
</body>
</html>
