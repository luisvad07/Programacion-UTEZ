const mongoose = require('mongoose');
const Schema =  mongoose.Schema;

const userSchema = new Schema({
   descripcion: String,
   fecha: Date,
   cliente: String,
   empleado: String,
   total: Number
});

const schema = mongoose.model('Orders', userSchema, 'Orders');

module.exports={schema};