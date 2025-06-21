const mongoose = require('mongoose');
const Schema =  mongoose.Schema;

const userSchema = new Schema({
   nombreEmpresa: String,
   direccion: String,
   telefono: Number,
   articulo: String,
});

const schema = mongoose.model('Providers', userSchema, 'Providers');

module.exports={schema};