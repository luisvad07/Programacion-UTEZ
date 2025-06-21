const { response, Router } = require("express");
const { schema } = require("./products.gateway");
const validateError = require("../../../utils/functions");

const getAll = async (req, res = Response) => {
  try {
    const products = await schema.find();
    res.status(200).json(products);
    console.log(products);
   
  } catch (error) {
    console.log(error);
    const message = validateError(error);
    res.status(400).json({ message });
  }
};

const getById = async (req, res = Response) => {
  const id = req.params.id;
  try {
      
      const productsId = await schema.findOne({_id:id});
      console.log(productsId);
      res.status(200).json(productsId);
  } catch (error) {
      console.log(error);
      const message = validateError(error);
      res.status(400).json({ message });
  }
};


const insert = async(req, res=Response)=>{
  const body = req.body;
  try {
      const insertProducts= new schema(body);
      await insertProducts.save();
      res.status(200).json(insertProducts);

      console.log(insertProducts);
  } catch (error) {
      console.log(error);
      const message = validateError(error);
      res.status(400).json({ message });
  }
}

const updateProduct = async(req, res=Response)=>{
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

const deleteProduct = async(req, res=Response)=>{
const id= req.params.id;
try {
  const fetchProduct = await schema.findByIdAndDelete({_id:id});
  if (fetchProduct) {
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

const productsRouter = Router();

productsRouter.get(`/`, [], getAll);
productsRouter.get(`/:id`, [], getById)
productsRouter.post(`/`,[],insert);
productsRouter.put(`/:id`, [], updateProduct)
productsRouter.delete(`/:id`, [], deleteProduct )

module.exports = {
    productsRouter,
};