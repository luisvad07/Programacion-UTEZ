const { query } = require('../../../utils/mysql')

//Funciones CRUD para realizar con la BD
const findAll = async () => {
    const sql = `Select * From Pedidos;`
    return await query(sql, [])
}

const findById = async (id) => {
    if (Number.isNaN(id)) throw Error ('Wrong type')
    if (!id) throw Error ('Missing fields')
    const sql = `Select * From Pedidos Where id_pedidos = ?;`
    return await query(sql, [id])
}

const save = async (order) => {
    if (!order.personId || !order.product || !order.amount) throw Error ('Missing fields')
    const sql = `Insert Into Pedidos (id_persona, producto, cantidad) Values (?, ?, ?);`
    const { insertedId } = await query(sql, [
        order.personId,
        order.product,
        order.amount
    ])
    return { ...order,  id: insertedId }
}

const update = async (order, id) => {
    if (Number.isNaN(id)) throw Error ('Wrong Type')
    if (!order.personId || !order.product || !order.amount || !id) throw Error('Missing fields')
    const sql = `Update Pedidos Set id_persona = ?, producto = ?, cantidad = ? Where id_pedidos = ?;`
    return await query(sql, [
        order.personId,
        order.product,
        order.amount,
        id
    ])
}

const deleteOrder = async (id) => {
    if (Number.isNaN(id)) throw Error ('Wrong Type')
    if (!id) throw Error ('Missing fields')
    const sql = `Delete From Pedidos Where id_pedidos = ?;`
    return await query(sql, [id])
}

module.exports = {
    findAll,
    findById,
    save,
    update,
    deleteOrder
}