<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="Beans.Usuario" %>
<%
    // obtener la sesion que está logueada
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
    <title>Cambiar Dinero</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <h2 class="text-center">Cambiar Dinero</h2>
        <form id="cambiarDineroForm">
            <div class="mb-3">
                <label for="monedaOrigen" class="form-label">Moneda que deseas cambiar</label>
                <select id="monedaOrigen" class="form-select" required>
                    <option value="USD">USD</option>
                    <option value="COP">COP</option>
                    <option value="MXN">MXN</option>
                    <option value="EUR">EUR</option>
                    <option value="JPY">JPY</option>
                </select>
            </div>
            <div class="mb-3">
                <label for="monedaDestino" class="form-label">Moneda que deseas obtener</label>
                <select id="monedaDestino" class="form-select" required>
                    <option value="USD">USD</option>
                    <option value="COP">COP</option>
                    <option value="MXN">MXN</option>
                    <option value="EUR">EUR</option>
                    <option value="JPY">JPY</option>
                </select>
            </div>
            <div class="mb-3">
                <label for="cantidad" class="form-label">Cantidad</label>
                <input type="number" id="cantidad" class="form-control" min="0.01" step="0.01" required>
            </div>
            <div class="d-flex justify-content-between">
                <button type="button" class="btn btn-secondary" onclick="window.close();">Cancelar</button>
                <button type="button" id="confirmar" class="btn btn-primary">Confirmar</button>
            </div>
        </form>
        <div id="mensaje" class="mt-3 text-center"></div>
    </div>

    <script>
        document.getElementById("confirmar").addEventListener("click", function () {
            // obtener los datos de la encuesta
            const monedaOrigen = document.getElementById("monedaOrigen").value;
            const monedaDestino = document.getElementById("monedaDestino").value;
            const cantidad = parseFloat(document.getElementById("cantidad").value);
            const cantidadDestino = calcularCantidadDestino(monedaOrigen, monedaDestino, cantidad);

            if (monedaOrigen === monedaDestino) {
                alert("No puedes cambiar la misma moneda.");
                return;
            }
            if (isNaN(cantidad) || cantidad <= 0) {
                alert("Por favor, ingresa una cantidad válida.");
                return;
            }



            fetch('http://localhost:8080/HomeCambios/cambiarDinero', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                credentials: 'include',
                body: JSON.stringify({ monedaOrigen, monedaDestino, cantidad, cantidadDestino }),
            })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        alert("Cambio realizado con éxito.");
                        window.opener.location.reload();
                        window.close();
                    } else {
                        document.getElementById("mensaje").textContent = "Error: " + data.message;
                    }
                })
                .catch(error => {
                    console.error("Error al procesar la solicitud:", error);
                    document.getElementById("mensaje").textContent = "Error al procesar la solicitud.";
                });
        });

        function calcularCantidadDestino(monedaOrigen, monedaDestino, cantidad) {
            // Tasas de cambios
            const tasas = {
                USD: { COP: 4130, MXN: 19.5, EUR: 0.9, JPY: 145 },
                COP: { USD: 1 / 4010, MXN: 1 / 195, EUR: 1 / 4600, JPY: 1 / 30 },
                MXN: { USD: 1 / 19.5, COP: 225, EUR: 0.046, JPY: 7.4 },
                EUR: { USD: 1.1, COP: 4740, MXN: 21.7, JPY: 160 },
                JPY: { USD: 1 / 145, COP: 32, MXN: 1 / 7.4, EUR: 1 / 160 }
            };

            return cantidad * (tasas[monedaOrigen][monedaDestino] || 1);
        }
    </script>
</body>
</html>
