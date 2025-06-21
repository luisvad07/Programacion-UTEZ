const { generateToken } = require('../../../config/jwt')
const { validatePassword } = require('../../../utils/functions')
const { query } = require('../../../utils/mysql')

//Funcion para validar en la BD los datos para el Inicio de Sesion
const login = async (person) => {
    if (!person.user || !person.password) throw new Error ('Missing fields')
    const sql = `SELECT * FROM Personas WHERE usuario = ?`
    const existPerson = await query(sql, [person.user])
    //Validacion con Bcrypt la Contraseña Ingresada
    if (await validatePassword(person.password, existPerson[0].contrasenia)){
        //Creacion de un Token  en caso de que el Inicio sea Existoso
        return {
            //Datos que contendra el Token
            token: generateToken({
                userID: existPerson[0].id,
                user: person.user,
                userName: existPerson[0].nombre,
                userLastname: existPerson[0].apellidoP,
                userEmail: existPerson[0].email
            })
        }
    } else {
        throw Error ('Password mismatch')
    }
}

module.exports = {
    login
}