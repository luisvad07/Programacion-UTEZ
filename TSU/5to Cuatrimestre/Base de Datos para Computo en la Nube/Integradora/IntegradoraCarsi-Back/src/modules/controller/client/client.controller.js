const { response, Router } = require("express");
const { schema } = require("./client.gateway");
const validateError = require("../../../utils/functions");

const getAll = async (req, res = Response) => {
  try {
    const client = await schema.find();
    res.status(200).json(client);
    console.log(client);
   
  } catch (error) {
    console.log(error);
    const message = validateError(error);
    res.status(400).json({ message });
  }
};

const getById = async (req, res = Response) => {
  const id = req.params.id;
  try {
      
      const clientId = await schema.findOne({_id:id});
      console.log(clientId);
      res.status(200).json(clientId);
  } catch (error) {
      console.log(error);
      const message = validateError(error);
      res.status(400).json({ message });
  }
};


const insert = async(req, res=Response)=>{
  const body = req.body;
  try {
      const insertClient= new schema(body);
      await insertClient.save();
      res.status(200).json(insertClient);

      console.log(insertClient);
  } catch (error) {
      console.log(error);
      const message = validateError(error);
      res.status(400).json({ message });
  }
}

const updateClient = async(req, res=Response)=>{
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

const deleteClient = async(req, res=Response)=>{
const id= req.params.id;
try {
  const fetchClient = await schema.findByIdAndDelete({_id:id});
  if (fetchClient) {
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









const clientRouter = Router();

clientRouter.get(`/`, [], getAll);
clientRouter.get(`/:id`, [], getById)
clientRouter.post(`/`,[],insert);
clientRouter.put(`/:id`, [], updateClient)
clientRouter.delete(`/:id`, [], deleteClient )

module.exports = {
    clientRouter,
};