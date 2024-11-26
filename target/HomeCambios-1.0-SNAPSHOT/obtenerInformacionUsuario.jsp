<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Obtener Información de Usuario</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css">
</head>
<body>
    <div class="container my-5">
        <h2 class="text-center">Obtener Información de Usuario</h2>
        <form id="formInformacionUsuario" method="post" action="generarPDF" target="_blank">
            <div class="mb-3">
                <label for="userId" class="form-label">ID del Usuario</label>
                <input type="number" id="userId" name="userId" class="form-control" placeholder="Ingresa el ID del usuario" required>
            </div>
            <div class="d-flex justify-content-between mt-4">
                <button type="button" onclick="window.close();" class="btn btn-secondary">Cancelar</button>
                <button type="submit" class="btn btn-success">Generar PDF</button>
            </div>
        </form>
    </div>

    <script>
        function cerrarVentana() {
            window.close();
        }

        function generarPDF() {
            const userId = document.getElementById("userId").value.trim();

            if (!userId || isNaN(userId)) {
                mostrarError("Por favor, ingresa un ID válido.");
                return;
            }

            // crear una URL
            const url = `http://localhost:8080/HomeCambios/generarPDF?id=${userId}`;

            // abrir el PDF 
            window.open(url, '_blank');
        }

        function mostrarError(mensaje) {
            const errorDiv = document.getElementById("errorDiv");
            errorDiv.textContent = mensaje;
            errorDiv.style.display = "block";
        }
    </script>
</body>
</html>
