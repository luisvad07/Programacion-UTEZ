const { response, Router } = require("express");
const { schema } = require("./users.gateway");
const validateError = require("../../../utils/functions");

const getAll = async (req, res = Response) => {
  try {
    const user = await schema.find();
    res.status(200).json(user);
    console.log(user);
   
  } catch (error) {
    console.log(error);
    const message = validateError(error);
    res.status(400).json({ message });
  }
};


const getById = async (req, res = Response) => {
    const id = req.params.id;
    try {
        
        const userId = await schema.findOne({_id:id});
        console.log(userId);
        res.status(200).json(userId);
    } catch (error) {
        console.log(error);
        const message = validateError(error);
        res.status(400).json({ message });
    }
};


const insert = async(req, res=Response)=>{
    const body = req.body;
    try {
        const insertUser= new schema(body);
        await insertUser.save();
        res.status(200).json(insertUser);

        console.log(insertUser);
    } catch (error) {
        console.log(error);
        const message = validateError(error);
        res.status(400).json({ message });
    }
}

const updateUser = async(req, res=Response)=>{
    const id = req.params.id;
    const body = req.body;
    try {
        const update = await schema.findByIdAndUpdate( id, body, {useFindAndModify:false})
        console.log(update);
        res.status(200).json(update);
    } catch (error) {
        console.log(error);
        const message = validateError(error);
        res.status(400).json({ message });
    }
}

const deleteUser = async(req, res=Response)=>{
const id= req.params.id;
try {
    const fetchUser = await schema.findByIdAndDelete({_id:id});
    if (fetchUser) {
        res.status(200).json({
            estado: true,
            mensaje: 'perfecto'
        });
    }else{
        res.status(400).json({
            estado: false,
            mensaje: 'no jalo'
        });
    }
} catch (error) {
    console.log(error);
    const message = validateError(error);
    res.status(400).json({ message });
}
}

const userRouter = Router();

userRouter.get(`/`, [], getAll);
userRouter.get(`/:id`, [], getById)
userRouter.post(`/`,[],insert);
userRouter.put(`/:id`, [], updateUser)
userRouter.delete(`/:id`, [], deleteUser )

module.exports = {
  userRouter,
};
