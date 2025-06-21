const { query } = require("../../../utils/mysql");

const findAll = async () => {
  const sql = `SELECT * FROM personal pe JOIN positions po ON po.id = pe.position_id`;
  return await query(sql, []);
};

const findById = async (id) => {
    if (Number.isNaN(id)) throw Error("Wrong Type");
    if (!id) throw Error("Missing fields");
    const sql = `SELECT pe.*, po.description FROM personal pe JOIN positions po ON po.id = pe.position_id WHERE pe.id=?;`;
    return await query(sql, [id]);
};

const save = async (person) => {
  if (!person.name || !person.surname ||  !person.birthday || !person.salary ||!person.position.id) throw Error("Missing Fields");
  const sql = `INSERT INTO personal (name,surname,lastname,birthday,salary,position_id) VALUES (?,?,?,?,?,?)`;
  const { insertedId } = await query(sql, [
    person.name,
    person.surname,
    person.lastname || null,
    person.salary,
    person.position.id,
  ]);
  return {...person, id: insertedId};
};

module.exports = {
    findAll,
    findById,
    save,
};