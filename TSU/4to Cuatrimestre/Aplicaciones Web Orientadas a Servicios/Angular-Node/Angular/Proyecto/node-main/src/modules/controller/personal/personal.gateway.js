const { query } = require('../../../utils/mysql');

const findAll = async () => {
  const sql = `SELECT pe.*, po.description FROM personal pe JOIN position po
        ON po.id = pe.position_id ORDER BY id DESC`;
  return await query(sql, []);
};

const findById = async (id) => {
  if (Number.isNaN(id)) throw Error('Wrong type');
  if (!id) throw Error('Missing fields');
  const sql = `SELECT pe.*, po.description FROM personal pe JOIN position po
    ON po.id = pe.position_id WHERE pe.id = ?;`;
  return await query(sql, [id]);
};

const save = async (person) => {
  if (
    !person.name ||
    !person.surname ||
    !person.birthday ||
    !person.salary ||
    !person.position.id
  )
    throw Error('Missing fields');
  const sql = `INSERT INTO personal (name,surname,lastname,birthday,
        salary, position_id, avatar) VALUES (?,?,?,?,?,?,?)`;
  const { insertedId } = await query(sql, [
    person.name,
    person.surname,
    person.lastname || null,
    person.birthday,
    person.salary,
    person.position.id,
    person.avatar
  ]);
  return { ...person, id: insertedId };
};

const update = async (person) => {
  if (
    !person.name ||
    !person.surname ||
    !person.birthday ||
    !person.salary ||
    !person.position.id ||
    !person.id
  )
    throw Error('Missing fields');
  const sql = `UPDATE personal SET name = ?, surname= ?,lastname= ?,birthday = ?,
        salary = ?, position_id = ? WHERE id = ?`;
  const result = await query(sql, [
    person.name,
    person.surname,
    person.lastname || null,
    person.birthday,
    person.salary,
    person.position.id,
    person.id
  ]);
  console.log(result);
  return true;
};
const changeStatus = async (person) => {
  if (
    !person.id
  )
    throw Error('Missing fields');
  const sql = `UPDATE personal SET status = ? WHERE id = ?`;
  const result = await query(sql, [
    person.status,
    person.id
  ]);
  console.log(result);
  return true;
};

module.exports = {
  findAll,
  findById,
  save,
  update,
  changeStatus
};
