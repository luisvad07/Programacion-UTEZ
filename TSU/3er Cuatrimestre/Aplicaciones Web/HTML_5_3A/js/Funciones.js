function holaMundo() {
	alert('Hola desde funciones.js');
}

function variablesSimples() {
	var numero1 = 5;
	var numero2 = prompt('Numero a multiplicar');
	var resultado = numero1 * numero2;
	alert('El resultado de la Multiplicacion es: '+resultado);
}

function variablesString() {
	var str = new String();
	str = "Luis Valladolid";
	alert('El tamaño de la palabra ' + str.length);
	alert('El primer carater ' + str.charAt(0));
	var minutos = fecha.getMinutes();

	alert('Cambio a mayusculas: '+str.toUpperCase());
}

function variablesDate() {
	var fecha = new Date();
	var hora = fecha.getHours();	
	var seg = fecha.getSeconds();
	alert(hora+':'+minutos+':'+seg);

	if (hora<12 && hora>0) {
		alert('Buenos Dias :)');
	} else {
		alert('Tardes ya...xD');
	}
}

function llamarId() {
	var elemento = document.getElementById('idicito');
	console.log(elemento);
	alert(elemento.innerHTML);
	elemento.style.color ="red";
	elemento.style.backgroundColor ="black";
}

function llamarNombre() {
	var elemento = document.getElementsByName('nombrecito');
	console.log(elemento);
	alert(elemento[1].innerHTML);
	alert(elemento[2].innerHTML);
	elemento[0].style.color ="blue";
	elemento[2].style.backgroundColor ="gray";
}

function llamarClase() {
	var elemento = document.getElementsByClassName('verdecito');
	console.log(elemento);
	for (var i = 0; i < elemento.length; i++) {
		elemento[i].style.backgroundColor ="green";
		elemento[i].style.color ="white";
	}
}

function llamarEtiqueta() {
	var elemento = document.getElementsByTagName('button');
	console.log(elemento);
	for (var i = 0; i < elemento.length; i++) {
		elemento[i].style.border ="solid";
		elemento[i].style.borderColor ="red";
		elemento[i].style.borderRadius ="10px";
	}
}

function crearElementos() {
	var elemento = document.getElementById('id-div');
	elemento.innerHTML = '<br><button onclick ="modificarElemento()">Modificar</button>';
}

function modificarElemento() {
	var elemento = document.getElementById('btn1');
	elemento.innerHTML = 'Boton Cambiado :)';
}

function mensajeClic() {
	alert('Hola desde Evento Clic');
}

function mensajeSobre() {
	alert('Hola desde Evento MouseOver');
}

function mensajeCarga() {
	var elemento = document.getElementsByTagName('body');
	alert('Hola desde Evento OnLoad');
	elemento[0].style.backgroundColor ="black";
}

