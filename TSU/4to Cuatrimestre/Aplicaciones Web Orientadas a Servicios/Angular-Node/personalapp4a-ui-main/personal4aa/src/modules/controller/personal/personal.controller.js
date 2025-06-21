const { Response, Router } = require('express');
const validateError = require('../../../utils/functions');
const { findAll, findById, save, modify, changeStatus } = require('./personal.gateway');

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
    const { name, surname, lastname, birthday, salary, position, avatar } = req.body;
    const person = await save({
      name,
      surname,
      lastname,
      birthday,
      salary,
      position,
      avatar
    });
    res.status(200).json(person);
  } catch (error) {
    console.log(error);
    const message = validateError(error);
    res.status(400).json({ message });
  }
};

const update = async (req, res = Response) => {
  try {
    const { name, surname, lastname, birthday, salary, position, id } = req.body;
    const person = await modify({
      name,
      surname,
      lastname,
      birthday,
      salary,
      position,
      id
    });
    res.status(200).json(person);
  } catch (error) {
    console.log(error);
    const message = validateError(error);
    res.status(400).json({ message });
  }
};

const updateStatus = async (req, res = Response) => {
  try {
    const { id, status } = req.body;
    const person = await changeStatus({
      id,
      status,
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
personalRouter.put(`/`, [], update);
personalRouter.delete(`/`,[],updateStatus);

module.exports = {
  personalRouter,
};
