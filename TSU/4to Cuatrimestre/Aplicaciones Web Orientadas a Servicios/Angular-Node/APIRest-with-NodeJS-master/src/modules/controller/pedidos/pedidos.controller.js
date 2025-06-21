const { Response, Router } = require('express')
const { validateError } = require('../../../utils/functions')
const { findAll, findById, save, update, deleteOrder } = require('./pedidos.gateway')

//Funciones para controlar los Errores al ejecutar las Funciones CRUD
const getAll = async (req, res = Response) => {
    try {
        const orders = await findAll()
        res.status(200).json(orders)
    }catch (error){
        console.log(error)
        const message = validateError(error)
        res.status(400).json({message})
    }
}

const getById = async (req, res = Response) => {
    try {
        const { id } = req.params
        const orders = await findById(id)
        res.status(200).json(orders)
    }catch (error) {
        console.log(error)
        const message = validateError(error)
        res.status(400).json({message})
    }
}

const insert = async (req, res = Response) => {
    try {
        const { personId, product, amount } = req.body
        const orders = await save({
            personId,
            product,
            amount
        })
        res.status(200).json(orders)
    }catch (error) {
        console.log(error)
        const message = validateError(error)
        res.status(400).json({message})
    }
}

const updateO = async (req, res = Response) => {
    try{
        const { id } = req.params
        const { personId, product, amount } = req.body
        const orders = await update({
            personId,
            product,
            amount},
            id
        )
        res.status(200).json(orders)
    }catch (error) {
        console.log(error)
        const message = validateError(error)
        res.status(400).json({message})
    }
}

const erase = async (req, res = Response) => {
    try{
        const { id } = req.params
        const orders = await deleteOrder(id)
        res.status(200).json(orders)
    }catch (error) {
        console.log(error)
        const message = validateError(error)
        res.status(400).json({message})
    }
}

const orderRouter = Router()

//Metodos HTTP por los que se realizara cada uno
orderRouter.get('/', [], getAll)
orderRouter.get(`/:id`, [], getById)
orderRouter.post(`/`, [], insert)
orderRouter.put(`/:id`, [], updateO)
orderRouter.delete(`/:id`, [], erase)

module.exports = {
    orderRouter
}