function declararVar() {
	var materia = 'WEB';

	if (true) {
		var materia = 'BD';
		console.log(materia);
	}
}

function declararLet() {
	let materia = 'WEB';

	if (true) {
		let materia ='BD'
		console.log(materia);
	}
	console.log(materia);
}

function declararConst() {
	const materia = 'WEB';

	if (true) {
		const materia ='BD'
		console.log(materia);
	}
	console.log(materia);
}

function declararObj() {
	const Persona = {
		nombre: "Luis",
		apellido:"Valladolid",
		edad: 19,
		colorF: "Azul"
	}
	console.log(Persona);
	console.log(Persona.apellido);

	Persona.nombre = "Eduardo";
	console.log(Persona);

	//Object.freeze(Persona.nombre);
	Object.defineProperty(Persona, "nombre", {configurable:false, writable:false});
	Persona.nombre = "CR7 Mamarre";
	console.log(Persona);
}

//Funciones Lambda

function sumaNormal(a,b) {
	return (a+b);
}
console.log(sumaNormal(4,19));

//Funcion flecha return implicito
let sumaFlechaIm = (a,b) => a+b;
console.log(sumaFlechaIm(10,10));

//Funcion flecha return explicito
let sumaFlechaEx = (a,b) => {
	return a+b;
}
console.log(sumaFlechaEx(5,5));

let multiplicacion = (a,b) => console.log(a*b);
multiplicacion(4,5);

let usuario = (nombre="Luis", apellido="Valladolid") => {
	console.log("Hola mi nombre es: "+nombre+" "+apellido);
}
usuario();
usuario("Eduardo");
usuario("Benito", "Camelo");

////// TEMPLATE LITERALS ////////

let dia = "Martes :(";
let concatenado = `Hoy es : ${dia}`;
console.log(concatenado);
console.log(`Hoy es : ${dia}`);

let musica  = (nombre, artista) => `Hola, estoy escuchando ${nombre} de ${artista}`
console.log(musica("Duro dos horas","Faraon Love Shady"));

console.log("Primer Linea \nSegunda Linea");

console.log(`Primer Linea
	Segunda Linea`);

////// SPREAD OPERATOR ////////
let miArreglo  = [1,2,3];
let sumarArreglo = (a,b,c) =>console.log(a+b+c);
console.log(miArreglo);
console.log(...miArreglo);
sumarArreglo(...miArreglo);

let otroArray = [10,11,12];
let miArreglo2 = [...miArreglo,4,5,6,...otroArray];
console.log(...miArreglo2);

////// DESTRUCTURACION ////////
const celular = {
	modelo: "Galaxy Note 10+",
	marca: "Samsung",
	precio: 10000,
	color: "Blanco",
	memoria: {
		ram:12,
		rom:256
	},
	direccion: {
		calle: "calle",
		colonia: "colonia",
		numero: {
			interior:100,
			exterior:2
		}
	}
}

/*let modelo = celular.modelo;
let precio = celular.precio;
console.log(`El celular ${modelo} cuesta ${precio} y tiene ${celular.memoria.ram} gb de ram`);
const {marca, color, memoria} = celular; //memoria:{rom}
const {rom} = memoria;
console.log(`El celular ${marca} es de color ${color} y tiene ${rom} de rom`);*/

console.log(celular);
console.log(celular.color);
console.log(celular.memoria.ram);
console.log(celular.direccion.numero.interior);

const {color, memoria:{ram}, direccion:{numero}} = celular;
const {interior} = numero;
console.log(color);
console.log(ram);
console.log(interior);

/* EVENT LISTENER

let elemento = document.getElementById('id-evento');
elemento.addEventListener("click", () => {
	alert('Hola desde EVENT LISTENER') 
});

let elemento = document.querySelector('#id-evento'); //All para arreglos
console.log(elemento);
elemento.addEventListener("click", () => {
	alert('Hola desde EVENT LISTENER'); 
});*/


///////////ASYNC AWAIT 

const canciones = [
	{
		id: 1,
		nombre: "Provenza",
		artista: "Karol G",
		duracion: 180
	},
	{
		id: 2,
		nombre: "No me Conoce",
		artista: "JhayCortez, Bad Bunny, J Balvin",
		duracion: 150
	},
	{
		id: 3,
		nombre: "Volvi",
		artista: "Aventura, Bad Bunny",
		duracion: 190
	}
]

/*const consultarCanciones = () => {
	return canciones;
}*/

const consultarCanciones = () => {
	return new Promise((resolve, reject)=>{
		setTimeout(() => {
			resolve(canciones);
		}, 5000);
	})
	
}

//console.log(consultarCanciones());

/*consultarCanciones().then((canciones) => {
	console.log(canciones);
})*/

async function misRolitas(arguments) {
	const listaCanciones = await consultarCanciones();
	console.log(listaCanciones);
}

misRolitas();





