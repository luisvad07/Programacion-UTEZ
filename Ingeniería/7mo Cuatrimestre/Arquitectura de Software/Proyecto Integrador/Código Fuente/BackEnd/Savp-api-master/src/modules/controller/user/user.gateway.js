const { query } = require('../../../utils/mysql');
const { hashPassword } = require("../../../utils/functios");
const { sendEmail } = require('../auth/emailServer');
const { savePersonal } = require('../personal/personal.gateway');

const findAll = async() => {
    const sql = "SELECT * FROM User";
    return await query(sql, []);
}

const findById = async(id) => {
    if(Number.isNaN(id)) throw Error("Wrong Type");
    if(!id) throw Error("Missing fields");
    const sql = `SELECT * FROM user WHERE id = ?`;
    return await query(sql, [id]);
}

const findByIdUser = async(id) => {
    if(Number.isNaN(id)) throw Error("Wrong Type");
    if(!id) throw Error("Missing fields");
    const sql = `SELECT * FROM user WHERE id = ?`;
    return await query(sql, [id]);
}

// Define el ID del rol de cajero
const ID_ROL_CAJERO = 2; // Puedes ajustar este valor según tus necesidades

// Modifica la función `save` en tu gateway
const save = async (user) => {
    if (!user.username || !user.password || !user.status || !user.roleId) {
      throw Error("Missing fields");
    }
  
    const sql = `INSERT INTO User (username, password, status, rol_fk) VALUES(?,?,?,?)`;
    const hashPass = await hashPassword(user.password);
    const { insertId } = await query(sql, [user.username, hashPass, 1, user.roleId]);
    await savePersonal({name:"", birthday:"", address:"", user_fk:insertId})
    await sendEmail(user.username);
    delete user.password;
    return { ...user, id: insertId };
  };

  const saveUserPersonal = async (user) => {
    if (!user.username || !user.password || !user.status || !user.roleId || !user.name || !user.birthday || !user.address) {
      throw Error("Missing fields");
    }
    console.log(user);
  
    const sql = `INSERT INTO User (username, password, status, rol_fk) VALUES(?,?,?,?)`;
    const hashPass = await hashPassword(user.password);
    const { insertId } = await query(sql, [user.username, hashPass, 1, user.roleId]);
    console.log("Hola",insertId);
    await savePersonal({name:user.name, birthday:user.birthday, address:user.address, user_fk:insertId})
    delete user.password;
    return { ...user, id: insertId };
  };
  


  
const update = async(user, id) => {
    if(Number.isNaN(id)) throw Error("Wrong Type");
    if(!id) throw Error("Missing fields");
    if(!user.username || !user.password || !user.status || !user.roleId) throw Error("Missing fields");
    const sql = `UPDATE User SET username=?, password=?, status=?, rol_fk WHERE id=?`;
    const hashPass = await hashPassword(user.password);
    await query(sql, [
        user.username,
        hashPass,
        user.status,
        user.roleId,
        id
    ]);
    return {...user, id:id}
}
const remove = async(id) => {
    if(Number.isNaN(id)) throw Error("Wrong Type");
    if(!id) throw Error("Missing fields");
    const sql = `DELETE FROM User WHERE id=?`;
    await query(sql, []);
    return {idDeleted: id};
}
module.exports = {
    findAll,
    findById,
    save,
    update,
    remove,
    findByIdUser,
    saveUserPersonal
};