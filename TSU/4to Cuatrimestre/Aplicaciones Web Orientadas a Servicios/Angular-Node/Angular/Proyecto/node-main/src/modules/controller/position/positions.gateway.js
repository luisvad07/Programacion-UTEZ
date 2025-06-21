const {query} = require('../../../utils/mysql');


const findAll = async () => {
  const sql = `SELECT * FROM position;`;
  return await query(sql, []);
};

module.exports = {
  findAll
};