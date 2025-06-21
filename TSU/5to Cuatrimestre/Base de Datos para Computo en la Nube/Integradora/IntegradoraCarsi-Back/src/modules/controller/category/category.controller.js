const { response, Router } = require("express");
const { schema } = require("./category.gateway");
const validateError = require("../../../utils/functions");

const getAll = async (req, res = Response) => {
  try {
    const category = await schema.find();
    res.status(200).json(category);
    console.log(category);
   
  } catch (error) {
    console.log(error);
    const message = validateError(error);
    res.status(400).json({ message });
  }
};

const getById = async (req, res = Response) => {
  const id = req.params.id;
  try {
      
      const categoryId = await schema.findOne({_id:id});
      console.log(categoryId);
      res.status(200).json(categoryId);
  } catch (error) {
      console.log(error);
      const message = validateError(error);
      res.status(400).json({ message });
  }
};


const insert = async(req, res=Response)=>{
  const body = req.body;
  try {
      const insertCategory= new schema(body);
      await insertCategory.save();
      res.status(200).json(insertCategory);

      console.log(insertCategory);
  } catch (error) {
      console.log(error);
      const message = validateError(error);
      res.status(400).json({ message });
  }
}

const updateCategory = async(req, res=Response)=>{
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

const deleteCategory = async(req, res=Response)=>{
const id= req.params.id;
try {
  const fetchCategory = await schema.findByIdAndDelete({_id:id});
  if (fetchCategory) {
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









const categoryRouter = Router();

categoryRouter.get(`/`, [], getAll);
categoryRouter.get(`/:id`, [], getById)
categoryRouter.post(`/`,[],insert);
categoryRouter.put(`/:id`, [], updateCategory)
categoryRouter.delete(`/:id`, [], deleteCategory )

module.exports = {
  categoryRouter,
};