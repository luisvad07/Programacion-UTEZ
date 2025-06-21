const {response, Router} = require('express');
const {findAll, findById, save, update, remove, saveUserPersonal} = require('./user.gateway');

const getAll = async(req, res = Response) => {
    try {
        const users = await findAll();
        res.status(200).json(users);
    } catch (error) {
        res.status(400).json({error});
    }
}
const getById = async(req, res=Response)=>{
    try {
        const {id} = req.params;
        const user = await findById(id);
        res.status(200).json(user);
    } catch (error) {
        res.status(400).json({error});
    }
}

const insert = async(req, res=Response)=>{
    try {
        const {username, password, status, roleId} = req.body;
        const user = await save({username, password, status, roleId});
        res.status(200).json(user);
    } catch (error) {
        console.log(error);
        res.status(400).json({error});
    }
}

const insertUserPersonal = async(req, res=Response)=>{
    try {
        const {username, password, status, roleId, address, name, birthday} = req.body;
        const user = await saveUserPersonal({username, password, status, roleId, address, name, birthday});
        res.status(200).json(user);
    } catch (error) {
        console.log(error);
        res.status(400).json({error});
    }
}

const actualize = async (req, res = Response) => {
    try {
       const {username, password, status, roleId} = req.body;
       const { id } = req.params;
       const user = await update({username, password, status, roleId}, id)
       res.status(200).json(user);
    } catch (error) {
       
       res.status(400).json({ error });
    }
 
 }

 const eliminate = async (req, res = Response) => {
    try {
       const{ id } =req.params;
       const user = await remove(id);
       res.status(200).json(user);
    } catch (error) {
       res.status(400).json({ error });
    }
 }

 const userRouter = Router();

userRouter.get('/', getAll);
userRouter.get('/:id', getById);
userRouter.post('/', insert);
userRouter.post('/insert/', insertUserPersonal);
userRouter.put('/:id', actualize);
userRouter.delete('/:id',eliminate);

module.exports = {
    userRouter
};