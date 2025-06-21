const { query } = require('../../../utils/mysql')

const findAll = async() => {
    const sql = "SELECT * FROM Plataforma";
    return await query(sql, []);
}
const findById = async(id) => {
    const sql = `SELECT * FROM Plataforma WHERE id = ?`;
    return await query(sql, [id]);
}
const findAllByStatus = async(status) => {
    const sql = `SELECT * FROM Plataforma WHERE status = ?`;
    return await query(sql, [status]);
}
const save = async(plataforma) => {
    if(!plataforma.plataforma) throw Error("Missing fields");
    const sql = `INSERT INTO Plataforma (plataforma, status) VALUES(?,?) `;
    const { insertedId } = await query(sql , [
        plataforma.plataforma,
        true
    ]);
    return {...plataforma, id: insertedId};
}
const update = async(plataforma, id) => {
    if(Number.isNaN(id)) throw Error("Wrong Type");
    if(!id) throw Error("Missing fields");
    if(!plataforma.plataforma || !plataforma.status) throw Error("Missing fields");

    const sql = `UPDATE Plataforma SET plataforma = ? , status = ? WHERE id=?`
    await query(sql, [
        plataforma.plataforma,
        plataforma.status,
        id
    ]);
    return {...plataforma, id: id};
};

const remove = async(id) => {
    if(Number.isNaN(id)) throw Error("Missing Fields");
    if(!id) throw Error("Missing Fields");
    const sql = `DELETE FROM Plataforma WHERE id=?`;
    await query(sql,[id]);
    return {idDeleted: id}
}
const changeStatus = async (id) => {
    if (Number.isNaN(id)) throw Error("Missing Fields");
    if (!id) throw Error("Missing Fields");
  
    const update = await findById(id);
    if (!update) throw Error("Not found"); 
    const newStatus = !update[0].status;
  
    const sql = `UPDATE Plataforma SET status = ? WHERE id = ?`;
    return await query(sql, [newStatus, id]);
};
  
module.exports = {
    findAll,
    findById,
    findAllByStatus,
    save,
    update,
    changeStatus,
    remove
}