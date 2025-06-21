const { response, Router } = require("express");
const { schema } = require("./providers.gateway");
const validateError = require("../../../utils/functions");

const getAll = async (req, res = Response) => {
  try {
    const providers = await schema.find();
    res.status(200).json(providers);
    console.log(providers);
   
  } catch (error) {
    console.log(error);
    const message = validateError(error);
    res.status(400).json({ message });
  }
};

const getById = async (req, res = Response) => {
  const id = req.params.id;
  try {
      
      const providersId = await schema.findOne({_id:id});
      console.log(providersId);
      res.status(200).json(providersId);
  } catch (error) {
      console.log(error);
      const message = validateError(error);
      res.status(400).json({ message });
  }
};


const insert = async(req, res=Response)=>{
  const body = req.body;
  try {
      const insertProviders= new schema(body);
      await insertProviders.save();
      res.status(200).json(insertProviders);

      console.log(insertProviders);
  } catch (error) {
      console.log(error);
      const message = validateError(error);
      res.status(400).json({ message });
  }
}

const updateProvider = async(req, res=Response)=>{
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

const deleteProvider = async(req, res=Response)=>{
const id= req.params.id;
try {
  const fetchProvider = await schema.findByIdAndDelete({_id:id});
  if (fetchProvider) {
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









const providersRouter = Router();

providersRouter.get(`/`, [], getAll);
providersRouter.get(`/:id`, [], getById)
providersRouter.post(`/`,[],insert);
providersRouter.put(`/:id`, [], updateProvider)
providersRouter.delete(`/:id`, [], deleteProvider )

module.exports = {
    providersRouter,
};