require('./config/conexion');
const express = require('express');
const port = (process.env.port || 3000);

//Express
const app = express()

//Se instancia el config
app.set('port', port);

//Admitir
app.use(express.json());

//Rutas
app.use('/',require('./rutas'));
app.use('/api',require('./rutas'));

//Iniciar Express
app.listen(app.get('port'),(error)=>{
    if (error) {
        console.log('Error al iniciar el Servidor: '+error);
    } else {
        console.log('Servidor inicializado en el puerto: '+port);
        console.log('http://localhost:3000/');
    }
});