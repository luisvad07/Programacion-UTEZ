const { personalRouter } = require('./personal/personal.controller');
const { userRouter } = require('./user/user.controller');
const { authRouter } = require('./auth/auth.controller');
const { positionsRouter } = require('./position/position.controller');
module.exports = {
  personalRouter,
  userRouter,
  authRouter,
  positionsRouter
};
