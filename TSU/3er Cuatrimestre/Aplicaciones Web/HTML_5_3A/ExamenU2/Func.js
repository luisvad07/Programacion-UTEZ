// JAVASCRIPT EXAMENU2.HTML

var nombreapellido = "Luis Eduardo Bahena Castillo"
var telefono = "7772822722";
var correo = "luiseduardobahenacastillo007@gmail.com";
var sitioweb = "luisValladolid.com.mx";

document.getElementById("nombre-apellido").innerHTML = nombreapellido;
document.getElementById("telefono").innerHTML = "Telefono: " + telefono;
document.getElementById("correo").innerHTML = "Correo Electronico: " + correo;
document.getElementById("sitio-web").innerHTML = "Sitio Web: " + sitioweb;

document.getElementById("uno").innerHTML = "Tecnico en Programador";
document.getElementById("dos").innerHTML = "Idiomas: Frances, Ingles y Español";
document.getElementById("tres").innerHTML = "Diseño Industrial";
document.getElementById("cuatro").innerHTML = "Desarrollo de Software Multiplataforma";

document.getElementById("unot").innerHTML = "Empacador";
document.getElementById("dost").innerHTML = "Chalan";
document.getElementById("trest").innerHTML = "Repartidor";
document.getElementById("cuatrot").innerHTML = "Profesor de Idiomas";

function estudios() {
	alert('Estudie en el Kinder Selma Lagerloff');
	alert('Estudie en la Primaria Ignacio Manuel Altamirano');
	alert('Estudie en la Secundaria Tecnica No.23');
	alert('Estudie en el Centro de Estudios Tecnologicos Industrial y de Servicios No.44');
	alert('Estudio en la Universidad Tecnologica de Emiliano Zapata');
}

function imagen() {
	var nombre="Luis Eduardo";
	var apellido="Bahena Castillo";
	alert("Imagen de: "+nombre+" "+apellido);
}


function consola() {
	alert('Observe el mensaje que esta en el console.log');
}

var text = "Curriculum Vitae de @LuisEduardoBahenaCastillo";
console.log(text);