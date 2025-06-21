const { query } = require("../../../utils/mysql");
const { sendEmail, sendEmailRenta, sendEmailDevolucion } = require("../auth/emailServer");
const { changeStatusProceso, findByIdItem } = require("../item/item.gateway");
const { findByIdUser } = require("../user/user.gateway");

const findAll = async () => {
    const sql = `SELECT * FROM Renta`;
    return await query(sql, []);
}

const findAllDemora = async (id, username) => {
    const sql = `SELECT pro.titulo as titulo FROM renta 
    JOIN item on item.id=renta.item_fk
    JOIN producto pro on item.producto_fk=pro.id
    WHERE DATEDIFF(fecha_entrega, CURDATE()) = 7 AND user_fk=?`;
    const response = await query(sql, [id]);
    var productos = [];
    await Promise.all(
        productos = response.filter((item)=> item.titulo)
    );
    if (productos.length !== 0) {
        await sendEmailDevolucion(username, productos);
    }
    return productos;
}

const findAllRentaLog = async () => {
    const sql = `SELECT us.username as username, rel.fecha as fecha, rel.fecha_entrega as entrega, pro.titulo FROM rentalog rel 
    JOIN user us on us.id=rel.user_fk
    JOIN item on rel.item_fk=item.id
    JOIN producto pro on pro.id=item.producto_fk
    `;
    return await query(sql, []);
}

const findRentasUser = async (idUser) => {
    if (Number.isNaN(idUser)) throw Error("Wrong type");
    if (!idUser) throw Error("Missing Fields");
    const sql = `
    SELECT * FROM renta WHERE user_fk=?
`;

    const respuesta = await query(sql, [idUser]);
    console.log(respuesta.length, "hola");
    return respuesta.length === 0 ? true : false;
}

const findAllByUser = async (idUser) => {
    if (Number.isNaN(idUser)) throw Error("Wrong type");
    if (!idUser) throw Error("Missing Fields");
    const sql = `
    SELECT R.*, I.*, P.titulo AS producto_titulo, P.imagen AS producto_imagen
    FROM Renta R
    JOIN Item I ON R.item_fk = I.id
    JOIN Producto P ON I.producto_fk = P.id
    WHERE R.user_fk = ?
`;
    return await query(sql, [idUser]);
}
const findById = async (id) => {
    if (Number.isNaN(id)) throw Error("Wrong type");
    if (!id) throw Error("Missing Fields");
    const sql = `SELECT * FROM Renta WHERE id = ? `;
    return await query(sql, [id]);
}
const save = async (userId, renta) => {
    console.log(userId, renta);
    if (!userId) throw Error("Missing fields");
    if (!await findRentasUser(userId)) throw Error("Renta activa");
    let fecha = new Date();
    const sql = `INSERT INTO Renta (user_fk, item_fk,fecha, cajero_id, fecha_entrega) VALUES(?,?,?,?,?)`;
    const articulosRentados = [];
    var userData = await findByIdUser(userId);
    var { username } = userData[0];

    await Promise.all(renta.map(async (itemId) => {
        var itemData = await findByIdItem(itemId);
        await changeStatusProceso(itemId);
        await query(sql, [userId, itemId, fecha, 1, new Date(Date.now() + 7 * 24 * 60 * 60 * 1000).toLocaleDateString('es-ES', { day: '2-digit', month: '2-digit', year: 'numeric' }).split('/').reverse().join('-')]);
        articulosRentados.push(itemData[0]);
    }));

    await sendEmailRenta(username, articulosRentados);
    return { userId, articulosRentados};
}


const remove = async (id) => {
    if (Number.isNaN(id)) throw Error("Wrong TYpe");
    if (!id) throw Error("Missing fields");
    const sql = `DELETE FROM Renta WHERE id=?`;
    await query(sql, []);
    return { idDeleted: id };
}
module.exports = {
    findAll,
    findAllByUser,
    findById,
    save,
    remove,
    findAllRentaLog,
    findAllDemora
};