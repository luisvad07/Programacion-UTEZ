const { response, Router } = require("express");
const { schema } = require("./promotion.gateway");
const validateError = require("../../../utils/functions");

const getAll = async (req, res = Response) => {
  try {
    const promotions = await schema.find();
    res.status(200).json(promotions);
    console.log(promotions);
   
  } catch (error) {
    console.log(error);
    const message = validateError(error);
    res.status(400).json({ message });
  }
};

const getById = async (req, res = Response) => {
  const id = req.params.id;
  try {
      
      const promotionsId = await schema.findOne({_id:id});
      console.log(promotionsId);
      res.status(200).json(promotionsId);
  } catch (error) {
      console.log(error);
      const message = validateError(error);
      res.status(400).json({ message });
  }
};


const insert = async(req, res=Response)=>{
  const body = req.body;
  try {
      const insertPromotions= new schema(body);
      await insertPromotions.save();
      res.status(200).json(insertPromotions);

      console.log(insertPromotions);
  } catch (error) {
      console.log(error);
      const message = validateError(error);
      res.status(400).json({ message });
  }
}

const updatePromotion = async(req, res=Response)=>{
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

const deletePromotion = async(req, res=Response)=>{
const id= req.params.id;
try {
  const fetchPromotion = await schema.findByIdAndDelete({_id:id});
  if (fetchPromotion) {
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

const promotionRouter = Router();

promotionRouter.get(`/`, [], getAll);
promotionRouter.get(`/:id`, [], getById)
promotionRouter.post(`/`,[],insert);
promotionRouter.put(`/:id`, [], updatePromotion)
promotionRouter.delete(`/:id`, [], deletePromotion )

module.exports = {
  promotionRouter,
};