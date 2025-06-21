const {response, Router} = require('express');
const {findAll, findById, savePersonal,findByUserId, updatePersonal, updateSolo} = require('./personal.gateway');
const { auth, checkRoles } = require('../../../config/jwt');

const getAll = async(req, res = Response) => {
    try {
        const personals = await findAll();
        res.status(200).json(personals);
    } catch (error) {
        res.status(400).json({error});
    }
}
const getById = async(req, res=Response)=>{
    try {
        const {id} = req.params;
        const personal = await findById(id);
        res.status(200).json(personal);
    } catch (error) {
        res.status(400).json({error});
    }
}

const insert = async(req, res=Response)=>{
    try {
        const {name, birthday, address, status, user} = req.body;
        const personal = await savePersonal({name, birthday, address, status, user});
        res.status(200).json(personal);
    } catch (error) {
        console.log(error);
        res.status(400).json({error});
    }
}

const update = async(req, res=Response)=>{
    try {
        
        const {name, birthday, address, id, roleId, username, user_id} = req.body;
        const personal = await updatePersonal({name, birthday, address, id, roleId, username, user_id});
        res.status(200).json(personal);
    } catch (error) {
        console.log(error);
        res.status(400).json({error});
    }
}

const actualizeSolo = async(req, res=Response)=>{
    try {
        console.log(req.body);
        const personal = await updateSolo(req.body);
        res.status(200).json(personal);
    } catch (error) {
        console.log(error);
        res.status(400).json({error});
    }
}

const getByUserId = async(req, res=Response)=>{
    try {
        const {userId} = req.params;
        const personal = await findByUserId(userId);
        res.status(200).json(personal);
    } catch (error) {
        res.status(400).json({error});
    }
}



const personalRouter = Router();

personalRouter.get('/' ,getAll);
personalRouter.get('/:id', getById);
personalRouter.post('/', insert);
personalRouter.put('/', update);
personalRouter.put('/solo/', actualizeSolo);
personalRouter.get('/findByUserId/:userId', getByUserId);

module.exports = {
    personalRouter
}