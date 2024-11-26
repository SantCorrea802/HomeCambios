document.getElementById('contactFormFooter').addEventListener('submit', function (event) {
    event.preventDefault();

    // asignar los valores del formulario
    const userId = document.getElementById('userId').value.trim();
    const userName = document.getElementById('userName').value.trim();
    const age = document.getElementById('age').value.trim();
    const email = document.getElementById('emailAddressBelow').value.trim();
    const password = document.getElementById('userPassword').value.trim();
    const confirmPassword = document.getElementById('confirmpassword').value.trim();
    if (!userId || !userName || !age || !email || !password || password !== confirmPassword) {
        alert("Por favor, completa todos los campos correctamente");
        return;
    }

    // creamos un objeto con los datos del formulario
    const userData = {
        id: parseInt(userId),
        nombre: userName,
        edad: parseInt(age),
        correo: email,
        pass: password
    };

    fetch('http://localhost:8080/HomeCambios/registrarUsuario', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(userData)
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert("Usuario registrado con éxito");
                document.getElementById('contactFormFooter').reset(); // reseteamos el formulario
            } else {
                alert(data.message || "Error al registrar el usuario");
            }
        })
        .catch(error => {
            console.error("Error:", error);
            alert("Error al procesar la solicitud");
        });
});


document.addEventListener('DOMContentLoaded', function () {
    const loginButton = document.getElementById('loginButton');
    if (loginButton) {
        loginButton.addEventListener('click', function () {
            const id = document.getElementById('loginId').value.trim();
            const password = document.getElementById('loginPassword').value.trim();

            if (!id || !password) {
                alert('Porfavor, llena todos los campos');
                return;
            }

            fetch('http://localhost:8080/HomeCambios/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ id: id, password: password })
            })


            .then(response => {
                if (!response.ok) throw new Error('Error al iniciar sesión: ' + response.statusText);
                return response.json();
            })

            .then(data => {
                if (data.success) {
                    switch(data.role) {
                        case 1: // suponiendo que '1' es un usuario regular
                            window.location.href = 'inicio.jsp';
                            break;
                        case 2: // suponiendo que '2' es un empleado
                            window.location.href = 'empleado.jsp';
                            break;
                        default:
                            alert('Rol no reconocido, por favor, contacta al soporte técnico');
                            break;
                    }
                } else {
                    alert(data.message || 'Credenciales incorrectas, por favor, inténtalo de nuevo');
                }
            })
            .catch(error => {
                console.error('Error en la solicitud:', error);
                alert('Hubo un problema al iniciar sesión, inténtalo más tarde. Error: ' + error.message);
            });

        });
    } else {
        console.error('Boton de inicio de sesión no encontrado');
    }
});

