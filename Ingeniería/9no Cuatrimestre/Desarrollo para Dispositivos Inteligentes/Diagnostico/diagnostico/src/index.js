const express = require('express');
const cors = require('cors');

const app = express();

app.use(cors({ origin: "*" })) //Permite recibir cualquier peticion con X origen
app.use(express.json( )); //Permite peticiones en formato JSON

// Ruta para validar la cadena de texto
app.post('/validar-cadena', (req, res) => {
    const message = req.body.message; // Obtener la cadena en el cuerpo de la petición
    
  
    const tieneCaracteresEspeciales = (message) => {
        const regex = /[!@#$%^&*(),.?":{}|<>/]/g; // Expresión regular 
        return regex.test(message);
    };

    if (tieneCaracteresEspeciales(message)) {
        res.status(400).json({ error: 'La cadena contiene caracteres especiales.' });
    } else {
        const lista = message.split('');
        res.json({ arreglo: lista });
    }
});

//Ruta principal para comprobar que se está ejecutando
app.listen(3000, () => {
    console.log("Servicio NodeJS - Examen Diagnostico corriendo en el puerto 3000");
})