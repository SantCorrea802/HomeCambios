document.addEventListener('DOMContentLoaded', function () {
    const ingresarDineroForm = document.getElementById('ingresarDineroForm');

    ingresarDineroForm.addEventListener('submit', function (event) {
        event.preventDefault(); 
        const moneda = document.getElementById('monedaIngresar').value;
        const cantidad = parseFloat(document.getElementById('cantidadIngresar').value);

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
                    alert('Dinero ingresado correctamente.');
                    location.reload();
                } else {
                    alert('Error al ingresar dinero: ' + data.message);
                }
            })
            .catch(error => console.error('Error:', error));
    });
});
