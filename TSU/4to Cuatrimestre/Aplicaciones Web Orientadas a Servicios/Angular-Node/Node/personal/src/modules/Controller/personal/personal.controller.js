const { Response, Router } = require('express');
const { validateError } = require('../../../utils/functions');
const { findAll, findById, save } = require('./personal.gateway');

const getAll = async (req, res = Response) => {
  try {
    const personal = await findAll();
    res.status(200).json(personal);
  } catch (error) {
    console.log(error);
    const message = validateError(error);
    res.status(400).json({ message }); //{message:""}
  }
};

const getById = async (req, res = Response) => {
  try {
    const { id } = req.params;
    const person = await findById(id);
    res.status(200).json(person);
  } catch (error) {
    console.log(error);
    const message = validateError(error);
    res.status(400).json({ message });
  }
};

const insert = async (req, res = Response) => {
  try {
    const { name, surname, lastname, birthday, salary, position } = req.body;
    const person = await save({
      name,
      surname,
      lastname,
      birthday,
      salary,
      position,
    });
    res.status(200).json(person);
  } catch (error) {
    console.log(error);
    const message = validateError(error);
    res.status(400).json({ message });
  }
};

const personalRouter = Router();

personalRouter.get(`/`, [], getAll);
personalRouter.get(`/:id`, [], getById);
personalRouter.post(`/`, [], insert);

module.exports = {
  personalRouter,
};