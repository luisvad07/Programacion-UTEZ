const mongoose = require('mongoose');
const Schema =  mongoose.Schema;

const userSchema = new Schema({
   nombrePromocion: String,
   descripcion: String,
   fechaInicio: Date,
   fechaFin: Date,
   
});

const schema = mongoose.model('Promotions', userSchema, 'Promotions');

module.exports={schema};