const { query } = require('../../../utils/mysql');

const findAll = async () => {
    const sql = `SELECT * FROM positions;`;
    return await query(sql, []);
};

const findById = async (id) =>{
    if(Number.isNaN(id)) throw Error('Wrong type');
    if(!id) throw Error('Missing Fields');
    const sql = `SELECT * FROM positions WHERE id = ?;`;
    return await query(sql, [id]);
};

const save = async (position) => {
    if(
        !position.name || !position.description
    ) throw Error('Missing Fields');
    const sql = `INSERT INTO positions 
    (position,description) values(?,?);`;
    const {insertedId} = await query(sql,[
        position.name,
        position.description
    ]);
    return { ...position, id: insertedId};
};

module.exports = {
    findAll,
    findById,
    save,
}

