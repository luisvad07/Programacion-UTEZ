const mongoose = require('mongoose');
const Schema =  mongoose.Schema;

const userSchema = new Schema({
   nombreCategoria: String,
});

const schema = mongoose.model('Category', userSchema, 'Category');

module.exports={schema};