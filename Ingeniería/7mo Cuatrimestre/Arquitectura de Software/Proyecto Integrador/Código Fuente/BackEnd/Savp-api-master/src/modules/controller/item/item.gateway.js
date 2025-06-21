const { query } = require("../../../utils/mysql")

const findAll = async () => {
    const sql = `
        SELECT Item.id, Item.status, Item.descripcion, Item.estado, Producto.titulo, Plataforma.plataforma
        FROM Item
        JOIN Producto ON Item.producto_fk = Producto.id
        JOIN Plataforma ON Item.plataforma_fk = Plataforma.id 
    `;
    return await query(sql, []);
}
const findById = async (id) => {
    const sql = `SELECT * FROM Item WHERE id = ?`
    return await query(sql, [id])
};

const findByIdItem = async (id) => {
    const sql = `SELECT item.descripcion as item_desc, prod.titulo as titulo, prod.descripcion as descripcion, pla.plataforma as plataforma FROM item JOIN producto prod on prod.id=item.producto_fk
    JOIN plataforma pla on pla.id=item.plataforma_fk
    WHERE item.id = ?`
    return await query(sql, [id])
};

const findByEstado = async () => {
    const sql = `SELECT Item.id, Item.status, Item.descripcion, Item.estado, Producto.titulo, Plataforma.plataforma
    FROM Item
    JOIN Producto ON Item.producto_fk = Producto.id
    JOIN Plataforma ON Item.plataforma_fk = Plataforma.id WHERE estado=2`
    return await query(sql, [])
};

const findAllByProducto = async (productoId) => {
    const sql = `SELECT
    i.id,
    p.titulo AS producto_titulo,
    i.estado,
    pla.plataforma AS plataforma
FROM
    Item i
JOIN
    Producto p ON i.producto_fk = p.id
JOIN
    Plataforma pla ON i.plataforma_fk = pla.id
WHERE
    i.producto_fk = ? AND i.status = true
`
    return await query(sql, [productoId]);
}

const findAllByStatus = async (status) => {
    const sql = `SELECT * FROM Items WHERE status = ?`;
    return await query(sql, [status]);
}
const save = async (item) => {
    if (!item.estado ||
        !item.descripcion ||
        !item.productoId ||
        !item.plataformaId ||
        !item.status)
        throw Error("Missing Fields");

    const sql = `INSERT INTO Item (estado, producto_fk, descripcion, plataforma_fk, status) VALUES(?,?,?,?,?)`;
    const { insertedId } = await query(sql, [
        item.estado,
        item.productoId,
        item.descripcion,
        item.plataformaId,
        item.status
    ]);
    return { ...item, id: insertedId };
}
const update = async (item, id) => {
    if (Number.isNaN(id)) throw Error("Wrong Type");
    if (!id) throw Error("Missing Fields");
    if (!item.descripcion || !item.productoId || !item.plataformaId) {
        throw Error("Missing Fields");
    }

    const sql = `UPDATE Item SET descripcion=?, producto_fk=?, plataforma_fk=? WHERE id=?`;
    await query(sql, [
        item.descripcion,
        item.productoId,
        item.plataformaId,
        id
    ]);

    return { ...item, id: id };
};


const remove = async (id) => {
    if (Number.isNaN(id)) throw Error("Missing Fields");
    if (!id) throw Error("Missing Fields");
    const sql = `DELETE FROM Item WHERE id=?`;
    await query(sql, [id]);
    return { isDelete: id };
}

const changeStatus = async (id) => {
    if (Number.isNaN(id)) throw Error("Missing Fields");
    if (!id) throw Error("Missing Fields");

    const update = await findById(id);
    if (!update) throw Error("Not found");
    const newStatus = !update[0].status;

    const sql = `UPDATE Item SET status = ? WHERE id = ?`;
    await query(sql, [newStatus, id]);

    return { ...update[0], status: newStatus };
};

const changeStatusEntregado = async (id) => {
    if (Number.isNaN(id)) throw Error("Missing Fields");
    if (!id) throw Error("Missing Fields");

    const sql = `UPDATE Item SET estado = 3 WHERE id = ?`;
    await query(sql, [id]);

    return { ...update[0] };
};

const changeStatusProceso = async (id) => {
    if (Number.isNaN(id)) throw Error("Missing Fields");
    if (!id) throw Error("Missing Fields");

    const sql = `UPDATE Item SET estado = 2 WHERE id = ?`;
    await query(sql, [id]);
    await changeStatus(id);

    return { ...update[0] };
};

const changeStatusDevuelto = async (id) => {
    if (Number.isNaN(id)) throw Error("Missing Fields");
    if (!id) throw Error("Missing Fields");

    const sql = `UPDATE Item SET status=true, estado = 1 WHERE id = ?`;

    await query(sql, [id]);

    const sqlRemove = `DELETE FROM renta WHERE item_fk=?`;
    
    await query(sqlRemove, [id]);

    return { ...update[0] };
};

module.exports = {
    findAll,
    findById,
    findAllByStatus,
    save,
    update,
    remove,
    changeStatus,
    findAllByProducto,
    changeStatusEntregado,
    findByEstado,
    changeStatusProceso,
    changeStatusDevuelto,
    findByIdItem
};