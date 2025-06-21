// JAVASCRIPT TARJETA.HTML

var elemento = document.getElementsByTagName("body");
var nombreapellido = prompt ("Ingrese su nombre completo: ");
var telefono = prompt ("Ingrese su numero de telefono: ");
var correo = prompt ("Ingrese su correo electronico: ");

document.getElementById("nombre-apellido").innerHTML = "Nombre Completo: " + nombreapellido;
document.getElementById("telefono").innerHTML = "Telefono: " + telefono;
document.getElementById("correo").innerHTML = "Correo Electronico: " + correo;
