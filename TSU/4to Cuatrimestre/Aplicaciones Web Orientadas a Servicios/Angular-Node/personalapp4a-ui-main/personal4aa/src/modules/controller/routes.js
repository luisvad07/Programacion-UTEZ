const { personalRouter } = require('./personal/personal.controller');
const { userRouter } = require('./user/user.controller')
const { authRouter } = require('./auth/auth.controller')
const { positionRouter} = require('./postions/position.controller')
module.exports = {
  personalRouter,
  userRouter,
  authRouter,
  positionRouter,
};
