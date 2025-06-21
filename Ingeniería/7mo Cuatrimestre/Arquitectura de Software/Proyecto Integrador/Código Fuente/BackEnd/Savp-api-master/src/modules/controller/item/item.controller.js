const { response, Router } = require('express');
const { findAll, findById, findAllByStatus, save, update, remove, changeStatus, findAllByProducto, changeStatusEntregado, findByEstado, changeStatusDevuelto } = require('./item.gateway');

const getAll = async (req, res = Response) => {
    try {
        const items = await findAll();
        res.status(200).json(items);
    } catch (error) {
        console.log(error);
        res.status(400).json({ error });
    }
}

const getAllByEstado = async (req, res = Response) => {
    try {
        const items = await findByEstado();
        res.status(200).json(items);
    } catch (error) {
        console.log(error);
        res.status(400).json({ error });
    }
}

const getById = async (req, res = Response) => {
    try {
        const { id } = req.params;
        const item = await findById(id);
        res.status(200).json(item);
    } catch (error) {
        console.log(error);
        res.status(400).json({ error });
    }
}
const getAllByProducto = async (req, res = Response) => {
    try {
        const { productoId } = req.params;
        const items = await findAllByProducto(productoId);
        res.status(200).json(items);
    } catch (error) {
        console.log(error);
        res.status(400).json({ error });
    }
}
const getAllByStatus = async (req, res = Response) => {
    try {
        const { status } = req.params;
        const items = await findAllByStatus(status);
        res.status(200).json(items);
    } catch (error) {
        res.status(400).json({ error });
    }
}
const insert = async (req, res = Response) => {
    console.log(req.body);
    try {
        const { estado, descripcion, productoId, plataformaId, status } = req.body;
        const item = await save({ descripcion, estado, status, productoId, plataformaId });
        res.status(200).json(item);
    } catch (error) {
        console.log(error);
        res.status(400).json({ error });
    }
}
const actualize = async (req, res = Response) => {

    console.log("body: xd ", req.body);
    try {
        const { descripcion, productoId, plataformaId } = req.body;
        const { id } = req.params; // Cambiado de res.params a req.params
        const item = await update({ descripcion, productoId, plataformaId }, id);
        res.status(200).json(item);
    } catch (error) {
        console.log(error);
        res.status(400).json({ error });
    }
}

const eliminate = async (req, res = Response) => {
    try {
        const { id } = req.params;
        const result = await remove(id);
        res.status(200).json(result);
    } catch (error) {
        res.status(400).json({ error });
    }
}
const statusChange = async (req, res = Response) => {
    try {
        const { id } = req.params;
        const result = await changeStatus(id);
        res.status(200).json(result);
    } catch (error) {
        res.status(400).json({ error });
    }
}

const statusChangeEntregado = async (req, res = Response) => {
    try {
        const { id } = req.params;
        const result = await changeStatusEntregado(id);
        res.status(200).json(result);
    } catch (error) {
        res.status(400).json({ error });
    }
}

const statusChangeDevuelto = async (req, res = Response) => {
    try {
        const { id } = req.params;
        const result = await changeStatusDevuelto(id);
        res.status(200).json(result);
    } catch (error) {
        res.status(400).json({ error });
    }
}

const itemRouter = Router();

itemRouter.get('/', getAll);
itemRouter.get('/estado/', getAllByEstado);
itemRouter.get('/:id', getById);
itemRouter.get('/producto/:productoId', getAllByProducto);
itemRouter.get('/status/:status', getAllByStatus);
itemRouter.post('/', insert);
itemRouter.put('/:id', actualize);
itemRouter.delete('/:id', eliminate);
itemRouter.put('/status/:id', statusChange);
itemRouter.put('/status_change/:id', statusChangeEntregado);
itemRouter.put('/status_devuelto/:id', statusChangeDevuelto);

module.exports = {
    itemRouter
}