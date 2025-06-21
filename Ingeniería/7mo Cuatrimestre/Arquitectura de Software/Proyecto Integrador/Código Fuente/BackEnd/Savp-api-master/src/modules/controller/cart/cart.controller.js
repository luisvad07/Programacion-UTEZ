const { response, Router } = require('express');
const { findAll } = require('./cart.gateway');

const getAll = async (req, res = Response) => {
    try {
        const cart = await findAll();
        res.status(200).json(cart);
    } catch (error) {
        console.log(error);
        res.status(400).json({ error });
    }
}

const cartRouter = Router();

cartRouter.get('/cart', getAll);
module.exports = { cartRouter };