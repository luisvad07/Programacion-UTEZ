const express = require('express');
require('dotenv').config();
const cors = require('cors')
const jwt = require("jsonwebtoken")
const bodyParser = require('body-parser')
const bcrypt = require('bcrypt');
const path = require('path');

const { productoRouter, plataformaRouter, itemRouter ,rolRouter, authRouter, userRouter, rentaRouter, personalRouter,cartRouter} = require('../modules/controller/routes');

const app = express();

app.set("port", process.env.PORT || 3000);
app.use(cors({ origins: "*" }))
app.use(express.json({ limit: '50mb' }));

app.get('/', (req, res) => {
    res.send("Servicio NodeJS - Integradora Arquitectura de Software 7ºC ");
})
app.use('/uploads', express.static(path.join(__dirname, '../uploads')));



app.use('/api/producto', productoRouter);
app.use('/api/plataforma', plataformaRouter);
app.use('/api/item', itemRouter);
app.use('/api/rol', rolRouter);
app.use('/api/auth', authRouter);
app.use('/api/user', userRouter)
app.use('/api/renta', rentaRouter)
app.use('/api/personal', personalRouter);
app.use('/api/cart', cartRouter);

module.exports = { app };