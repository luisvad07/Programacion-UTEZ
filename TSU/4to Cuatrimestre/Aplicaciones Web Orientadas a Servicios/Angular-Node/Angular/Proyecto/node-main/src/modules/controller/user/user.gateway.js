const { hashPassword } = require('../../../utils/functions');
const { query } = require('../../../utils/mysql');

const save = async (user) => {
  if (!user.email || !user.password || !user.role || !user.personal?.id)
    throw Error('Missing fields');
  const hashedPassword = await hashPassword(user.password);
  const sql = `INSERT INTO users (email,password,role,status,personal_id)
        VALUES (?,?,?,1,?)`;
  const { insertId } = await query(sql, [
    user.email,
    hashedPassword,
    user.role,
    user.personal.id,
  ]);
  delete user.password; // Elimina la propiedad del objeto user
  return { ...user, id: insertId };
};

module.exports = {
  save,
};
