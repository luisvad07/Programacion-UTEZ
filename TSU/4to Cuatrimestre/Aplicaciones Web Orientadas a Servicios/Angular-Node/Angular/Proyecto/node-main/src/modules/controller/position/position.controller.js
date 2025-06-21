const { Router, Response } = require('express');
const { validateError } = require('../../../utils/functions');
const { findAll } = require('./positions.gateway');


const getAll = async (req, res = Response) => {
  try {
    const positions = await findAll();
    res.status(200).json(positions);
  } catch (error) {
    console.log(error.message);
    const message = validateError(error);
    res.status(400).json({ message });
  }
};


const positionsRouter = Router();

positionsRouter.get(`/`, [], getAll);

module.exports = {
  positionsRouter
};