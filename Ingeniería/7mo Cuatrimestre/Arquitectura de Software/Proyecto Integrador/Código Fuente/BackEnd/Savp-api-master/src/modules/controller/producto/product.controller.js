const {response, Router} = require('express');
const {findAll, findById, save, update , remove} = require('./product.gateway');

const getAll = async(req, res = Response) => {
    try {
        const productos = await findAll();
        res.status(200).json(productos);
    } catch (error) {
        console.log(error)
        res.status(400).json({error})
    }
}
const getById = async(req, res = Response) => {
    try {
        const { id } = req.params;
        const productos = await findById(id);
        res.status(200).json(productos);
    } catch (error) {
        console.log(error)
        res.status(400).json({error})
    }
}
const insert = async(req, res = Response) => {
    try {
        const { titulo, descripcion, imagen} = req.body;
        const producto = await save({titulo, descripcion, imagen});
        res.status(200).json(producto);
    } catch (error) {
        console.log(error)
        res.status(400).json({error})
    }
}
const actualize = async(req, res = Response) => {
    try {
        const { titulo, descripcion } = req.body;
        const {id} = req.params;
        const response = await update({
            titulo ,
            descripcion
        }, id);
        res.status(200).json(response);
    } catch (error) {
        console.log(error)
        res.status(400).json({error});
    };
}
const eliminate = async(req, res = Response) => {
    try {
        const {id } = req.params;
        const producto = await remove(id);
        res.status(200).json({producto});
    } catch (error) {
        console.log(error);
        res.status(400).json({error})
    }
}

const productoRouter = Router();

productoRouter.get('/', getAll);
productoRouter.get('/:id' , getById);
productoRouter.post('/', insert)
productoRouter.put('/:id', actualize);
productoRouter.delete("/:id", eliminate)


module.exports = {
    productoRouter
}