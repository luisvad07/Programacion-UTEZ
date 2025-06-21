const mongoose = require('mongoose');
const Schema =  mongoose.Schema;

const userSchema = new Schema({
   nombreProducto: String,
   descripcion: String,
   categoria: String,
   precio: Number,
   cantidad: Number
});

const schema = mongoose.model('Products', userSchema, 'Products');

module.exports={schema};