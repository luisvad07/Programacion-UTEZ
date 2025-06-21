const { response, Router } = require("express");
const { schema } = require("./orders.gateway");
const validateError = require("../../../utils/functions");

const getAll = async (req, res = Response) => {
  try {
    const orders = await schema.find();
    res.status(200).json(orders);
    console.log(orders);
   
  } catch (error) {
    console.log(error);
    const message = validateError(error);
    res.status(400).json({ message });
  }
};

const getById = async (req, res = Response) => {
  const id = req.params.id;
  try {
      
      const ordersId = await schema.findOne({_id:id});
      console.log(ordersId);
      res.status(200).json(ordersId);
  } catch (error) {
      console.log(error);
      const message = validateError(error);
      res.status(400).json({ message });
  }
};


const insert = async(req, res=Response)=>{
  const body = req.body;
  try {
      const insertOrder= new schema(body);
      await insertOrder.save();
      res.status(200).json(insertOrder);

      console.log(insertOrder);
  } catch (error) {
      console.log(error);
      const message = validateError(error);
      res.status(400).json({ message });
  }
}

const updateOrder = async(req, res=Response)=>{
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

const deleteOrder = async(req, res=Response)=>{
const id= req.params.id;
try {
  const fetchOrder = await schema.findByIdAndDelete({_id:id});
  if (fetchOrder) {
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

const ordersRouter = Router();

ordersRouter.get(`/`, [], getAll);
ordersRouter.get(`/:id`, [], getById)
ordersRouter.post(`/`,[],insert);
ordersRouter.put(`/:id`, [], updateOrder)
ordersRouter.delete(`/:id`, [], deleteOrder )

module.exports = {
    ordersRouter,
};