const { generateToken } = require('../../../config/jwt');
const { validatePassword } = require('../../../utils/functions');
const { query } = require('../../../utils/mysql');

const login = async (user) => {
    if (!user.email || !user.password) throw Error('Minning fields');
    const sql = `SELECT * FROM users WHERE email = ? AND status = 1;`;
    const existUser = await query(sql, [user.email]);
    if (await validatePassword(user.password, existUser[0].password)) {
        return {
            token: generateToken({
                email: user.email,
                role: existUser[0].role,
                id: existUser[0].id,
                personal: existUser[0].personal_id,
            })
        }
    }
    throw Error('Password mismatch')
};

module.exports = {
    login,
}