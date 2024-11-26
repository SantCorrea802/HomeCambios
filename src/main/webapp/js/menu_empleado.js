// inicializacion de los modal
var modalEliminar = new bootstrap.Modal(document.getElementById('modalEliminar'));
var modalModificar = new bootstrap.Modal(document.getElementById('modalModificar'));
var modalIngresarDinero = new bootstrap.Modal(document.getElementById('modalIngresarDinero'));

// botones para abrir los modal
document.getElementById('eliminarUsuario').onclick = function() {
    modalEliminar.show();
};
document.getElementById('modificarUsuario').onclick = function() {
    modalModificar.show();
};
document.getElementById('ingresarDinero').onclick = function() {
    modalIngresarDinero.show();
};

// eliminar usuario
document.getElementById('formEliminar').onsubmit = function(evento) {
    evento.preventDefault();
    let idUsuario = document.getElementById('idEliminar').value;
    console.log(`Eliminando usuario con ID: ${idUsuario}`);
    alert(`Usuario con ID ${idUsuario} eliminado.`);
    modalEliminar.hide();
};
// modificar el rol de un usuario (1) a 2 (empleado)
document.getElementById('formModificar').onsubmit = function(evento) {
    evento.preventDefault();
    let idUsuario = document.getElementById('idModificar').value;
    console.log(`Modificando usuario con ID: ${idUsuario} a tipo empleado.`);
    alert(`Usuario con ID ${idUsuario} ahora es empleado.`);
    modalModificar.hide();
};





// ingresar dinero
document.getElementById('formIngresarDinero').onsubmit = function(evento) {
    evento.preventDefault();
    let idUsuario = document.getElementById('idDinero').value;
    let moneda = document.getElementById('monedaDinero').value;
    let cantidad = document.getElementById('cantidadDinero').value;
    console.log(`Ingresando ${cantidad} ${moneda} al usuario con ID: ${idUsuario}`);
    alert(`Se han ingresado ${cantidad} ${moneda} al usuario con ID ${idUsuario}.`);
    modalIngresarDinero.hide();
};
