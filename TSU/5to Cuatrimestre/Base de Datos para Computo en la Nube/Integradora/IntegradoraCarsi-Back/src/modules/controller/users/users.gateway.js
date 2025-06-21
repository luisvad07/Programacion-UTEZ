const mongoose = require('mongoose');
const Schema =  mongoose.Schema;

const userSchema = new Schema({
   nombreUsuario: String,
    contraseña: String,
    correoElectronico: String,
    telefono:Number,
    role: String
});

const schema = mongoose.model('Users', userSchema, 'Users');

module.exports={schema};