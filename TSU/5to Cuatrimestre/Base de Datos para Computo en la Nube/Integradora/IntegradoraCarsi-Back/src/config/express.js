const express = require('express');
const bodyParser= require('body-parser');
const cors = require('cors');
const{
    categoryRouter,
    userRouter,
    clientRouter,
    ordersRouter,
    productsRouter,
    promotionRouter,
    providersRouter
}= require('../modules/controller/routes');
const { request, response } = require('express');
const app = express();//instanciamos el server

app.use(bodyParser.urlencoded({extended: false}));
app.use(bodyParser.json());

require('dotenv').config();
//mongo
const user = 'root';
const password= 'root';
const bd='Integradora';
const uri=`mongodb+srv://${user}:${password}@bd-5a-2023.qb4wyj0.mongodb.net/${bd}?retryWrites=true&w=majority`;

const mongoose = require('mongoose');
mongoose.connect(uri).then(()=> console.log('conexion exitosa')).catch(e => console.log(e));


//configuracion de la aplicacion
//se trae la configuracion del .env o se le da uno
//por defecto
app.set('port', process.env.PORT || 3000);

//middlewares
//permite la comunicacion entre varias aplicaciones
app.use(cors({origin: '*'}));// permite recibir peticiones desde cualquier origen
//limitamos el tañano de la peticion
app.use(express.json({limit:'50mb'})); //permite peticiones de hasta 50 mb

//Routes
app.get('/', (request, response)=>{
    response.send('OLA YA JALO')
});

//endpoints
app.use('/api/users', userRouter );
app.use('/api/category', categoryRouter );
app.use('/api/client', clientRouter );
app.use('/api/orders', ordersRouter );
app.use('/api/products', productsRouter );
app.use('/api/promotions', promotionRouter );
app.use('/api/providers', providersRouter );



//exportaciones
module.exports={
    app
}