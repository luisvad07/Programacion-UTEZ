const {Response, Router} = require('express');
const {findAll, findAllByUser, save, findById, remove, findAllRentaLog, findAllDemora} = require("./renta.gateway");
const { sendEmailDevolucion } = require('../auth/emailServer');

const getAll = async(req, res = Response) => {
    try {
        const rentas = await findAll();
        res.status(200).json(rentas);
    } catch (error) {
        res.status(400).json({error});
    }
}

const getAllDemora = async(req, res = Response) => {
    try {
        const {id, username} = req.params;
        const rentas = await findAllDemora(id, username);
        res.status(200).json(rentas);
    } catch (error) {
        res.status(400).json({error});
    }
}

const getAllRentaLog = async(req, res = Response) => {
    try {
        const rentas = await findAllRentaLog();
        res.status(200).json(rentas);
    } catch (error) {
        res.status(400).json({error});
    }
}

const getAllByUser = async(req, res = Response) => {
    try {
        const {id} = req.params;
        const rentas = await findAllByUser(id)
        res.status(200).json(rentas);
    } catch (error) {
        res.status(400).json({error});
    }
}

const getById = async(req, res=Response)=>{
    try {
        const {id} = req.params;
        const renta = await findById(id);
        res.status(200).json(renta);
    } catch (error) {
        res.status(400).json({error});
    }
}

const insert = async(req, res=Response)=>{
    try {
        const {userId, rentalist} = req.body;
        const renta = await save(userId, rentalist);
        res.status(200).json(renta);
    } catch (error) {
        console.log(error)
        res.status(400).json({error});
    }
}

 const eliminate = async (req, res = Response) => {
    try {
       const{ id } =req.params;
       const renta = await remove(id);
       res.status(200).json(renta);
    } catch (error) {
       res.status(400).json({ error });
    }
 }
 const rentaRouter = Router();

 rentaRouter.get("/", getAll);
 rentaRouter.get("/log/", getAllRentaLog);
 rentaRouter.get("/:id", getById);
 rentaRouter.get("/user/:id", getAllByUser);
 rentaRouter.post("/", insert);
 rentaRouter.get("/demora/:id/:username", getAllDemora);
 rentaRouter.delete("/:id", eliminate);

 module.exports = {
    rentaRouter
 }