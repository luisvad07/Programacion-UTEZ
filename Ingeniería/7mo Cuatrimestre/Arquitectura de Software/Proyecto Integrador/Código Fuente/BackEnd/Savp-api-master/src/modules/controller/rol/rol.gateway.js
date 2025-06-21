const {query} = require('../../../utils/mysql');

const findAll = async () => {
    const sql = "SELECT * FROM Rol";
    return await query(sql, []);
}
const findById = async (id) => {
    const sql = `SELECT * FROM Rol WHERE id = ?`;
    return await query(sql, [id]);
}
const save = async(rol) => {
    if(!rol.rol) throw Error("Missing Fields");
    const sql = `INSERT INTO Rol (rol) VALUES (?)`;
    const { insertedId } = await query(sql, [
        rol.rol
    ]);
    return {...rol, id: insertedId};
}
const update = async(rol, id) => {
    if(Number.isNaN(id)) throw Error("Wrong Type");
    if(!id) throw Error("Misisng FIelds");
    if(!rol.rol) throw Error("Missing fields");
    const sql = `UPDATE Rol SET rol = ? WHERE id = ?`;
    await query(sql, [
        rol.rol,
        id
    ]);
    return {...rol, id: id};
}
const remove = async (id) => {
    if(Number.isNaN(id)) throw Error("Missing fields");
    if(!id) throw Error("Missing fields");
    const sql = `DELETE FROM Rol WHERE id=?`;
    await query(sql, [id]);
    return {isDeleted: id};
};

module.exports = {
    findAll,
    findById,
    save,
    update,
    remove,
}