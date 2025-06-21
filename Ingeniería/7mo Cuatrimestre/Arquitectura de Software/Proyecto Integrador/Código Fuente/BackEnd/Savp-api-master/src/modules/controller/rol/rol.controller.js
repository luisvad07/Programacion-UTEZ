const {response, Router} = require('express');
const {findAll, findById, save, update, remove} = require('./rol.gateway');

const getAll = async(req, res = Response) => {
    try {
        const roles = await findAll();
        res.status(200).json(roles);
    } catch (error) {
        console.log(error);
        res.status(400).json({error});
    }
}
const getById = async(req, res = Response) => {
    try {
        const {id} = req.params;
        const rol = await findById(id);
        res.status(200).json(rol);
    } catch (error) {
        console.log(error);
        res.status(400).json({error});
    }
}
const insert = async(req, res = Response) => {
    try {
        const { rol } = req.body;
        const rolSave = await save({rol});
        res.status(200).json(rolSave);
    } catch (error) {
        console.log(error);
        res.status(400).json({error});
    }
}
const actualize = async(req, res = Response) => {
    try {
        const { rol } = req.body;
        const {id} = req.params;
        const response = await update({
            rol
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
        const rol = await remove(id);
        res.status(200).json(rol);
    } catch (error) {
        console.log(error);
        res.status(400).json({error})
    }
}

const rolRouter = Router();

rolRouter.get('/', getAll);
rolRouter.get('/:id', getById);
rolRouter.post('/', insert);
rolRouter.put('/:id', actualize);
rolRouter.delete('/:id', eliminate);

module.exports = {
    rolRouter
}