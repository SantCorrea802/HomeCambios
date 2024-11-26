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
    <title>Ingresar Dinero</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <h2 class="text-center">Ingresar Dinero</h2>
        <form id="ingresarDineroForm">
            <div class="mb-3">
                <label for="moneda" class="form-label">Selecciona la Moneda</label>
                <select id="moneda" name="moneda" class="form-select" required>
                    <option value="mxn">MXN</option>
                    <option value="cop">COP</option>
                    <option value="usd">USD</option>
                    <option value="eur">EUR</option>
                    <option value="jpy">JPY</option>
                </select>
            </div>
            <div class="mb-3">
                <label for="cantidad" class="form-label">Cantidad</label>
                <input type="number" id="cantidad" name="cantidad" class="form-control" min="0" step="0.01" required>
            </div>
            <div class="d-flex justify-content-between">
                <button type="button" id="confirmar" class="btn btn-primary">Confirmar</button>
                <button type="button" onclick="window.close();" class="btn btn-secondary">Cancelar</button>
            </div>
        </form>
        <div id="mensaje" class="mt-3 text-center"></div>
    </div>
    <script>
        document.getElementById("confirmar").addEventListener("click", function () {
            const moneda = document.getElementById("moneda").value;
            const cantidad = parseFloat(document.getElementById("cantidad").value);

            if (isNaN(cantidad) || cantidad <= 0) {
                alert("Por favor, ingresa una cantidad válida.");
                return;
            }

            fetch('http://localhost:8080/HomeCambios/ingresarDinero', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                credentials: 'include',
                body: JSON.stringify({ moneda, cantidad }),
            })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        alert("Dinero ingresado correctamente.");
                        window.opener.location.reload(); 
                        window.close();
                    } else {
                        document.getElementById("mensaje").textContent = "Error: " + data.message;
                    }
                })
                .catch(error => {
                    console.error("Error al ingresar dinero:", error);
                    document.getElementById("mensaje").textContent = "Error al procesar la solicitud.";
                });
        });
    </script>
</body>
</html>
