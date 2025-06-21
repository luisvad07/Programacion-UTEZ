const { Response, Router } = require('express')
const { findAll, findById, save, update, deletePerson } = require('./personas.gateway')
const { validateError } = require('../../../utils/functions')

//Funciones para controlar los Errores al ejecutar las Funciones CRUD
const getAll = async (req, res = Response) => {
    try {
        const personal = await findAll()
        res.status(200).json(personal)
    }catch (error) {
        console.log(error)
        const message = validateError(error)
        res.status(400).json({message})
    }
}

const getById = async (req, res = Response) => {
    try{
        const { id } = req.params
        const person = await findById(id)
        res.status(200).json(person)
    }catch (error) {
        console.log(error)
        const message = validateError(error)
        res.status(400).json({message})
    }

}

const insert = async (req, res = Response) => {
    try{
        const { user, password, name, lastname, email } = req.body
        const person = await save({
            user,
            password,
            name,
            lastname,
            email
        })
        res.status(200).json(person)
    }catch (error) {
        console.log(error)
        const message = validateError(error)
        res.status(400).json({message})
    }
}

const updateP = async (req,res = Response) => {
    try{
        const { id } = req.params
        const { user, password, name, lastname, email } = req.body
        const person = await update({
            user,
            password,
            name,
            lastname,
            email},
            id
        )
        res.status(200).json(person)
    }catch (error) {
        console.log(error)
        const message = validateError(error)
        res.status(400).json({message})
    }
}

const erase = async (req, res = Response) => {
    try {
        const { id } = req.params
        const person = await deletePerson(id)
        res.status(200).json(person)
    }catch (error) {
        console.log(error)
        const message = validateError(error)
        res.status(400).json({message})
    }
}

const personalRouter = Router()

//Metodos HTTP por los que se realizara cada uno
personalRouter.get('/', [], getAll)
personalRouter.get(`/:id`, [], getById)
personalRouter.post(`/`, [], insert)
personalRouter.put(`/:id`, [], updateP)
personalRouter.delete(`/:id`, [], erase)

module.exports = {
    personalRouter
}