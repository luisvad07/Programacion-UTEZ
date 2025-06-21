const { Response, Router } = require('express');
const { validateError } = require('../../../utils/functions');
const { save } = require('./user.gateway');

const saveAndFlush = async (req, res = Response) => {
  try {
    const { email, password, role, personal } = req.body;
    const user = await save({ email, password, role, personal });
    res.status(200).json(user);
  } catch (error) {
    console.log(error);
    const message = validateError(error);
    res.status(400).json({ message }); //{message:""}
  }
};

const userRouter = Router();
userRouter.post(`/`, [], saveAndFlush);

module.exports = { userRouter };
