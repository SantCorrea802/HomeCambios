<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Beans.Usuario" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");

    if (usuario == null || usuario.getRol() != 2) { // Solo empleados pueden acceder
        response.sendRedirect("index.html");
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Modificar Usuario a Empleado</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css">
</head>
<body>
    <div class="container my-5">
        <h2 class="text-center">Modificar Usuario a Empleado</h2>
        <form id="formModificar">
            <div class="mb-3">
                <label for="userId" class="form-label">ID del Usuario</label>
                <input type="number" id="userId" class="form-control" placeholder="Ingresa el ID del usuario" required>
            </div>
            <div class="text-danger" id="errorDiv" style="display:none;"></div>
            <div class="text-success" id="successDiv" style="display:none;"></div>
            <div class="d-flex justify-content-between mt-4">
                <button type="button" onclick="window.close();" class="btn btn-secondary">Cancelar</button>
                <button type="submit" class="btn btn-warning">Modificar</button>
            </div>
        </form>
    </div>

    <script>
        document.getElementById("formModificar").addEventListener("submit", function (event) {
            event.preventDefault();

            const userId = document.getElementById("userId").value.trim();

            if (!userId || isNaN(userId)) {
                mostrarError("Por favor, ingresa un ID válido.");
                return;
            }

            fetch('http://localhost:8080/HomeCambios/modificarUsuario', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                credentials: 'include',
                body: JSON.stringify({ id: userId }),
            })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        mostrarExito("Usuario modificado a Empleado correctamente.");
                        setTimeout(() => {
                            window.opener.location.reload();
                            window.close();
                        }, 1500);
                    } else {
                        mostrarError(data.message || "Error al modificar el usuario.");
                    }
                })
                .catch(error => {
                    console.error("Error al modificar el usuario:", error);
                    mostrarError("Error al procesar la solicitud.");
                });
        });

        function mostrarError(mensaje) {
            const errorDiv = document.getElementById("errorDiv");
            errorDiv.textContent = mensaje;
            errorDiv.style.display = "block";
            document.getElementById("successDiv").style.display = "none";
        }

        function mostrarExito(mensaje) {
            const successDiv = document.getElementById("successDiv");
            successDiv.textContent = mensaje;
            successDiv.style.display = "block";
            document.getElementById("errorDiv").style.display = "none";
        }
    </script>
</body>
</html>
