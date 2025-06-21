const mongoose = require('mongoose');
const Schema =  mongoose.Schema;

const userSchema = new Schema({
   nombre: String,
   correoElectronico: String
});

const schema = mongoose.model('Client', userSchema, 'Client');

module.exports={schema};