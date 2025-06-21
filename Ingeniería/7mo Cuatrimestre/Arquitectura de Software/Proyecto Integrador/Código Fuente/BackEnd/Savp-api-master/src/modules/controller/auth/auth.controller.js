const { Response, Router} = require('express');
const {validateError} = require("../../../utils/functios");
const {login, resetPassword} = require('./auth.gateway');

const singin = async(req, res=Response)=>{
    try {
        const {username, password} = req.body;
        const token = await login({username, password});
        res.status(200).json(token);
    } catch (error) {
        res.status(400).json({error});
    }
}

const resetPasswordController = async(req, res=Response)=>{
    try {
        const {email} = req.body;
        const token = await resetPassword({email});
        res.status(200).json(token);
    } catch (error) {
        res.status(400).json({error});
    }
}

const authRouter = Router();
authRouter.post('/', singin);
authRouter.post('/reset/', resetPasswordController);

module.exports = {
    authRouter
}