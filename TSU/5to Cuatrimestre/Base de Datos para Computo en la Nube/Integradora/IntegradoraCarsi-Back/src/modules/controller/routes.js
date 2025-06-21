const {userRouter} = require('./users/users.controller');
const {categoryRouter} = require('./category/category.controller')
const {clientRouter} = require('./client/client.controller')
const {ordersRouter} = require('./orders/orders.controller')
const {productsRouter} = require('./prodructs/products.controller')
const {promotionRouter} = require('./promotion/promotion.controller')
const {providersRouter} = require('./providers/providers.controller')

module.exports={
    userRouter,
    categoryRouter,
    clientRouter,
    ordersRouter,
    productsRouter,
    promotionRouter,
    providersRouter
}