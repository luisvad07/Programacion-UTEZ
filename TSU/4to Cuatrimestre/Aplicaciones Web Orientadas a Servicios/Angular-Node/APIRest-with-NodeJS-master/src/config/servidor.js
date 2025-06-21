const express = require('express')
require('dotenv').config()
const cors = require('cors')
const {
    personalRouter,
    orderRouter,
    authRouter
} = require('../modules/controller/routes') //Acceso a los Router

const app = express()

app.set('port', process.env.PORT || 3000) //Pone como puerto el Definido en el archivo .env o 3000

//Middleware's
app.use(cors({origins: '*'})) //Permite recibir cualquier peticion con X origen
app.use(express.json({limit: '50mb'})) //Permite peticiones hasta 50mb

//Route's
app.get('/', (request, response) => {
    response.send('Bienvenido a mi API Rest')
})

//Endpoint's
app.use('/api/persons', personalRouter)
app.use('/api/orders', orderRouter)
app.use('/api/auth', authRouter)

module.exports = {
    app
}