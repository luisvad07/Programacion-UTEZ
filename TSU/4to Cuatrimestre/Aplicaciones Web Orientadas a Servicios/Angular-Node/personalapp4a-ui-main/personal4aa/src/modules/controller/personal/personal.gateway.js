const { query } = require('../../../utils/mysql');

const findAll = async () => {
  const sql = `SELECT pe.*, po.description FROM personal pe JOIN positions po
        ON po.id = pe.position_id ORDER BY id DESC`;
  return await query(sql, []);
};

const findById = async (id) => {
  if (Number.isNaN(id)) throw Error('Wrong type');
  if (!id) throw Error('Missing fields');
  const sql = `SELECT pe.*, po.description FROM personal pe JOIN positions po
    ON po.id = pe.position_id WHERE pe.id = ?;`;
  return await query(sql, [id]);
};

const save = async (person) => {
  console.log(person);
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
    person.avatar,
  ]);
  return { ...person, id: insertedId };
};

const modify = async (person) => {
  console.log(person);
  if (
    !person.name ||
    !person.surname ||
    !person.birthday ||
    !person.salary ||
    !person.position.id ||
    !person.id
  )
    throw Error('Missing fields');
  const sql = `UPDATE personal SET name = ?, surname = ?, lastname = ?, birthday = ?,
        salary = ?, position_id = ? WHERE id = ?;`;
  const { insertedId } = await query(sql, [
    person.name,
    person.surname,
    person.lastname || null,
    person.birthday,
    person.salary,
    person.position.id,
    person.id,
  ]);
  return { ...person, id: insertedId };
};

const changeStatus = async (person) => {
  console.log(person);
  if (
    !person.id
  )
    throw Error('Missing fields');
  const sql = `UPDATE personal SET status = ? WHERE id = ?;`;
  const { insertedId } = await query(sql, [
    person.status == 1?0:1,
    person.id,
  ]);
  return { ...person, id: insertedId };
};

module.exports = {
  findAll,
  findById,
  save,
  modify,
  changeStatus,
};
