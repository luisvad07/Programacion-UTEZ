const express = require('express');

const cors = require('cors');

const app = express();

app.use(cors({ origin: "*" })) //Permite recibir cualquier peticion con X origen
app.use(express.json( )); //Permite peticiones en formato JSON

// Ruta para validar la cadena de texto
app.post('/validar-cadena', (req, res) => {
    const message = req.body.message; // Obtener la cadena en el cuerpo de la petición
    
  
    const tieneCaracteresEspeciales = (message) => {
        const regex = /[!@#$%^&*(),.¿?":{}|<>/]/g; // Expresión regular 
        return regex.test(message);
    };

    if (tieneCaracteresEspeciales(message)) {
        res.status(400).json({ error: 'La cadena contiene caracteres especiales.' });
    } else {
        const lista = message.split('');
        res.json({ arreglo: lista });
    }
});

// Array bidimensional de letras e imágenes
const letrasConImagenes = [
    ['A', 'https://img.icons8.com/ios/50/sign-language-a.png'],
    ['B', 'https://img.icons8.com/ios/50/sign-language-b.png'],
    ['C', 'https://img.icons8.com/ios/50/sign-language-c.png'],
    ['D', 'https://img.icons8.com/ios/50/sign-language-d.png'],
    ['E', 'https://img.icons8.com/ios/50/sign-language-e.png'],
    ['F', 'https://img.icons8.com/ios/50/sign-language-f.png'],
    ['G', 'https://img.icons8.com/ios/50/sign-language-g.png'],
    ['H', 'https://img.icons8.com/ios/50/sign-language-h.png'],
    ['I', 'https://img.icons8.com/ios/50/sign-language-i.png'],
    ['J', 'https://img.icons8.com/ios/50/sign-language-j.png'],
    ['K', 'https://img.icons8.com/ios/50/sign-language-k.png'],
    ['L', 'https://img.icons8.com/ios/50/sign-language-l.png'],
    ['M', 'https://img.icons8.com/ios/50/sign-language-m.png'],
    ['N', 'https://img.icons8.com/ios/50/sign-language-n.png'],
    ['O', 'https://img.icons8.com/ios/50/sign-language-o.png'],
    ['P', 'https://img.icons8.com/ios/50/sign-language-p.png'],
    ['Q', 'https://img.icons8.com/ios/50/sign-language-q.png'],
    ['R', 'https://img.icons8.com/ios/50/sign-language-r.png'],
    ['S', 'https://img.icons8.com/ios/50/sign-language-s.png'],
    ['T', 'https://img.icons8.com/ios/50/sign-language-t.png'],
    ['U', 'https://img.icons8.com/ios/50/sign-language-u.png'],
    ['V', 'https://img.icons8.com/ios/50/sign-language-v.png'],
    ['W', 'https://img.icons8.com/ios/50/sign-language-w.png'],
    ['X', 'https://img.icons8.com/ios/50/sign-language-x.png'],
    ['Y', 'https://img.icons8.com/ios/50/sign-language-y.png'],
    ['Z', 'https://img.icons8.com/ios/50/sign-language-z.png'],
  ];


// Función para buscar la imagen asociada a una letra
const buscarImagen = (letra) => {
  const imagen = letrasConImagenes.find(pair => pair[0] === letra.toUpperCase());
  return imagen ? imagen[1] : null;
};

const tieneCaracteresEspeciales = (message) => {
        const regex = /[!@#$%^&*(),.¿?":{}|<>/]/g; // Expresión regular 
        return regex.test(message);
    };


app.post('/array-bidimensional-img', (req, res) => {

  const mensaje = req.body.mensaje.toUpperCase(); // Obtener el mensaje del cuerpo de la petición en Mayúsculas

    // Validar si el mensaje contiene caracteres especiales
    if (tieneCaracteresEspeciales(mensaje)) {
        return res.status(400).json({ error: 'El mensaje contiene caracteres especiales.' });
    }

    let mensajeConImagenes = [];

    // Iterar sobre cada letra en el mensaje y buscar la imagen asociada
    for (let i = 0; i < mensaje.length; i++) {
        const letra = mensaje[i];
        const imagen = buscarImagen(letra);
        if (imagen) {
            mensajeConImagenes.push({ letter: letra, url: imagen });
        } else {
            // Si no se encuentra la imagen, simplemente agregar la letra al mensaje
            mensajeConImagenes.push({ letter: letra, url: null });
        }
    }

    // Generar la respuesta en formato JSON
    res.json(mensajeConImagenes);
})


//Ruta principal para comprobar que se está ejecutando
app.listen(3000, () => {
    console.log("Servicio NodeJS - Examen Diagnostico corriendo en el puerto 3000");
})