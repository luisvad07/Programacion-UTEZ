const { generateToken } = require('../../../config/jwt');
const { validatePassword } = require('../../../utils/functions');
const { query } = require('../../../utils/mysql');

const login = async (user) => {
  if (!user.email || !user.password) throw Error('Missing fields');
  const sql = `SELECT * FROM users WHERE email = ? AND status = 1;`;
  const existsUser = await query(sql, [user.email]);
  if (await validatePassword(user.password, existsUser[0].password)) {
    return {
      token: generateToken({
        email: user.email,
        role: existsUser[0].role,
        id: existsUser[0].id,
        personal: existsUser[0].personal_id,
      }),
    };
  }
  throw Error('Password mismatch');
};
module.exports = { login };
