const jwt = require('jsonwebtoken')
const {decode} = require("jsonwebtoken");
require('dotenv').config()

//Funcion para generar el Token con los datos de Inicio de Sesion
const generateToken = (payload) => {
    return jwt.sign(payload, process.env.SECRET)
}

//Funcion para Verificar que eL Token sea valido
const verifyToken = (generateToken) => {
    try {
        if (!generateToken){
            throw new Error('Token no proporcionado')
        }
        const decoded = jwt.verify(generateToken, process.env.SECRET)
        return decoded
    }catch (error){
        if (error.name === 'TokenExpiredError'){
            throw new Error ('Token Expirado')
        }else if (error.name === 'JsonWebTokenError'){
            throw new Error ('Token JWT Invalido')
        }else{
            throw new Error ('Error al verificar el token: ' + error.message)
        }
    }
}

module.exports = {
    generateToken,
    verifyToken
}