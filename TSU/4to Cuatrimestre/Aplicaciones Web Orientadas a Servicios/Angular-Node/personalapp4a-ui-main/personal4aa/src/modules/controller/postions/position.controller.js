const { Response, Router } = require('express');
const validateError = require('../../../utils/functions');
const { findAll, findById, save } = require('./position.gateway');

const getAll = async (req, res = Response) => {
    try {
        const postions = await findAll();
        res.status(200).json(postions);
    } catch (error) {
        console.log(error);
        const message = validateError(error);
        res.status(400).json({ message });
    }
};

const getById = async (req, res = Response) => {
    try {
        const { id } = req.params;
        const postion = await findById(id);
        res.status(200).json(postion);
    } catch (error) {
        console.log(error);
        const message = validateError(error);
        res.status(400).json(error);
    }
};

const insert = async (req, res = Response) => {
    try {
        const { name, description } = req.body;
        const position = await save({
            name,
            description,
        });
        res.status(200).json(position);
    } catch (error) {
        console.log(error);
        const message = validateError(error);
        res.status(400).json({ message });
    }
}

const positionRouter = Router();

positionRouter.get(`/`, [], getAll);
positionRouter.get(`/:id`, [], getById);
positionRouter.post(`/`, [], insert);

module.exports = {
    positionRouter,
};