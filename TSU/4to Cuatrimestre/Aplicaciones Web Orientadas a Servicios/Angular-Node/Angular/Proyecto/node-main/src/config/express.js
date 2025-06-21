const express = require('express');
require('dotenv').config(); //imports
const cors = require('cors');
const {
  personalRouter,
  userRouter,
  authRouter,
  positionsRouter
} = require('../modules/controller/routes');
const app = express(); //Instanciar server
app.set('port', process.env.PORT || 3000);
//middlewares
app.use(cors({ origins: '*' })); //Permite recibir cualquier peticion con X origen
app.use(express.json({ limit: '50mb' })); //Permite peticiones hasta 50Mb
//Routes
app.get('/', (request, response) => {
  response.send('Bienvenido a la AppRest Personal-UTEZ');
}); //Endpoints
app.use(`/api/personal`, personalRouter);
app.use(`/api/user`, userRouter);
app.use(`/api/auth`, authRouter);
app.use(`/api/position`, positionsRouter);
//app.use(`/api/user`, userRouter);
module.exports = {
  app,
}; // {app:app}
