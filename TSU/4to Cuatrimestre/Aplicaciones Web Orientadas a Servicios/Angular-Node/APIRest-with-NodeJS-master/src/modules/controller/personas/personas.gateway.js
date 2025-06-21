const { hashPassword } = require('../../../utils/functions')
const { query } = require('../../../utils/mysql')

//Funciones CRUD para realizar con la BD
const findAll = async () => {
    const sql = 'SELECT * FROM personas;'
    return await query(sql, [])
}

const findById = async (id) => {
    if (Number.isNaN(id)) throw Error('Wrong Type')
    if (!id) throw Error('Missing fields')
    const sql = `SELECT * FROM Personas WHERE id = ?;`
    return await query(sql, [id])
}

const save = async (person) => {
    if (!person.user || !person.password || !person.name || !person.lastname || !person.email) throw Error('Missing fields')
    const hashedPassword = await hashPassword(person.password)
    const sql = `Insert Into Personas (usuario, contrasenia, nombre, apellidoP, email) Values (?, ?, ?, ?, ?);`
    const { insertedId } = await query(sql, [
        person.user,
        hashedPassword,
        person.name,
        person.lastname,
        person.email
    ])
    delete person.password
    return { ...person, id:insertedId }
}

const update = async (person, id) => {
    if (Number.isNaN(id)) throw Error('Wrong Type')
    if (!person.user || !person.password || !person.name || !person.lastname || !person.email || !id) throw Error('Missing fields')
    const sql = 'Update personas Set usuario = ?, contrasenia = ?, nombre = ?, apellidoP = ?, email = ? Where Id = ?;'
    return await query(sql, [
        person.user,
        person.password,
        person.name,
        person.lastname,
        person.email,
        id
    ])
}

const deletePerson = async (id) => {
    if (Number.isNaN(id)) throw Error('Wrong Type')
    if (!id) throw Error('Missing fields')
    const sql = `DELETE FROM Personas WHERE id = ?;`
    return await query(sql, [id])
}

module.exports = {
    findAll,
    findById,
    save,
    update,
    deletePerson
}