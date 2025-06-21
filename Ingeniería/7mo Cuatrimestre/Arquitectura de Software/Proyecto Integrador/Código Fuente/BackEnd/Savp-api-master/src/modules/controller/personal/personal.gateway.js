const { query } = require('../../../utils/mysql');
const { response, Router } = require('express');

const findAll = async () => {
    const sql = "SELECT *, Personal.id as id FROM Personal JOIN user on user.id=personal.user_fk";
    return await query(sql, []);
};

const findById = async (id) => {
    if (Number.isNaN(id)) throw Error("Wrong Type");
    if (!id) throw Error("Missing fields");

    const sql = `SELECT * FROM Personal WHERE id = ?`;
    const result = await query(sql, [id]);

    const sqlUser = `SELECT * FROM User WHERE id= ? `;
    const result2 = await query(sqlUser, [result[0].user_fk]);

    return { ...result[0], user: result2[0] };
};

const findByUserId = async (userId) => {
    if (Number.isNaN(userId)) throw Error("Wrong Type");
    if (!userId) throw Error("Missing fields");

    const sql = `SELECT * FROM Personal WHERE user_fk = ?`;
    const result = await query(sql, [userId]);

    const sqlUser = `SELECT * FROM User WHERE id= ? `;
    const result2 = await query(sqlUser, [result[0].user_fk]);

    return { ...result[0], user: result2[0] };
};

const savePersonal = async (personal) => {
    console.log(personal)
    

    const sql = `INSERT INTO Personal (name, birthday, address, status, user_fk) VALUES(?,?,?,?,?)`;
    const { insertedID } = await query(sql, [
        personal.name,
        personal.birthday,
        personal.address,
        1,
        personal.user_fk,
    ]);

    return { ...personal, id: insertedID };
};

const updatePersonal = async (personal) => {
    console.log(personal)
    

    const sql = `UPDATE personal SET name=?, birthday=?, address=? WHERE id=?`;
    const { insertedID } = await query(sql, [
        personal.name,
        personal.birthday,
        personal.address,
        personal.id,
    ]);
    const sql2 = `UPDATE user SET username=?, rol_fk=? WHERE id=?`;
    await query(sql2, [personal.username, personal.roleId, personal.user_id]);

    return { ...personal, id: insertedID };
};

const updateSolo = async (personal) => {
    console.log(personal)
    

    const sql = `UPDATE personal SET name=?, birthday=?, address=? WHERE id=?`;
    const { insertedID } = await query(sql, [
        personal.name,
        personal.birthday,
        personal.address,
        personal.user_fk,
    ]);
    return { ...personal, id: insertedID };
};

module.exports = {
    findAll,
    findById,
    savePersonal,
    findByUserId,
    updatePersonal,
    updateSolo
};