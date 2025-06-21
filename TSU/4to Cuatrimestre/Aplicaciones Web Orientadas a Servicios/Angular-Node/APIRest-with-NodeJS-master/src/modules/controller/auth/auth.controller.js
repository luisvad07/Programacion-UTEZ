const { Response, Router } = require('express')
const { validateError } = require('../../../utils/functions')
const { login } = require('./auth.gateway')

//Funcion para controllar el Inicio de Sesion
const signing = async (req, res = Response) => {
    try {
        const { user, password } = req.body
        const token = await login({user, password})
        res.status(200).json(token)
    }catch (error){
        console.log(error)
        const message = validateError(error)
        res.status(400).json({message})
    }
}

//Metodo HTTP por el cual se recibe la peticion
const authRouter = Router()
authRouter.post(`/`,  signing)

module.exports = {
    authRouter
}