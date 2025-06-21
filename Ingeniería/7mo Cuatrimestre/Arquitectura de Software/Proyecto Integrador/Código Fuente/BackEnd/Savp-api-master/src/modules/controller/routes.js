const { productoRouter } = require('./producto/product.controller');
const { plataformaRouter } = require('./plataforma/plataforma.controller');
const { itemRouter } = require('./item/item.controller');
const {rolRouter} = require('./rol/rol.controller');
const { authRouter } = require('./auth/auth.controller')
const {userRouter} = require('./user/user.controller');
const {rentaRouter} = require('./renta/renta.controller')
const {personalRouter} = require('./personal/personal.controller')
const {cartRouter} = require('./cart/cart.controller')

module.exports = {
    productoRouter,
    plataformaRouter,
    itemRouter,
    rolRouter,
    authRouter,
    userRouter,
    rentaRouter,
    personalRouter,
    cartRouter
}