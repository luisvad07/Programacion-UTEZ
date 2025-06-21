//Archivo para manejar las Rutas de Acceso y poder acceder a ellas desde el Servidor
//Comprime las funciones de .controller y .gateway en un solo acceso
const { personalRouter } = require('./personas/personas.controller')
const { orderRouter } = require('./pedidos/pedidos.controller')
const { authRouter } = require('./auth/auth.controller')

module.exports = {
    personalRouter,
    orderRouter,
    authRouter
}