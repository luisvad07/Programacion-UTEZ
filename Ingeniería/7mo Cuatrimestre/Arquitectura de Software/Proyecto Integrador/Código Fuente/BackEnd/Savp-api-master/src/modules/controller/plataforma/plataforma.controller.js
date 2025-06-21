const {response, Router} = require('express');
const {findAll, findById, save, findAllByStatus, update, remove, changeStatus} = require('./plataforma.gateway');

const getAll = async(req, res = Response) => {
    try {
        const plataformas = await findAll();
        res.status(200).json(plataformas);
    } catch (error) {
        console.log(error);
        res.status(400).json({error});
    }
}
const getById = async(req,res = Response) => {
    try {
        const {id} = req.params;
        const plataforma = await findById(id);
        res.status(200).json(plataforma);
    } catch (error) {
        console.log(error);
        res.status(400).json({error});
    }
}
const getAllByStatus = async(req, res = Response) => {
    try {
        const {status} = req.params;
        const plataformas = await findAllByStatus(status);
        res.status(200).json(plataformas);
    } catch (error) {
        console.log(error);
        res.status(400).json({error});
    }
}
const insert = async( req, res = Response) => {
    try {
        const { plataforma , status} = req.body;
        const result = await save({plataforma, status});
        res.status(200).json(result);
    } catch (error) {
        console.log(error);
        res.status(200).json({error});
    }
}
const actualize = async(req, res = Response) => {
    try {
        const { plataforma, status} = req.body;
        const {id} = req.params;
        const response = await update({
            plataforma, 
            status
        }, id);
        res.status(200).json(response);
    } catch (error) {
        console.log(error);
        res.status(400).json({error});
    }
}
const eliminate = async(req, res = Response) => {
    try {
        const {id} = req.params;
        const result = await remove(id);
        res.status(200).json({result});
    } catch (error) {
        console.log(error);
        res.status(400).json({error})
    }
}
const statusChange = async(req, res = Response) => {
    try {
        const {id} = req.params;
        const result = await changeStatus(id);
        res.status(200).json({result});
    } catch (error) {
        console.log(error);
        res.status(400).json({error});
    }
}

const plataformaRouter = Router();

plataformaRouter.get('/', getAll);
plataformaRouter.get('/status/:status', getAllByStatus);
plataformaRouter.get('/:id' , getById);
plataformaRouter.post('/', insert);
plataformaRouter.put('/:id', actualize);
plataformaRouter.delete('/:id', eliminate);
plataformaRouter.put('/status/:id', statusChange);

module.exports = {
    plataformaRouter
}