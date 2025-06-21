const bcrypt = require('bcrypt')

//Funcion para validar Errores
const validateError = (error) => {
    switch (error.message) {
        case 'Wrong type':
            return 'Review request fields';
        case 'Missing fields':
            return 'Validate fields';
        case 'Nonexistent role':
            return 'Role not registered';
        case 'Nothing found':
            return 'No data found';
        case 'Password mismatch':
            return 'Credentials mismatch';
        case 'User disabled':
            return 'User disabled';
        default:
            return 'Review request';
    }
};

//Funcion para Hashear la contraseña
const hashPassword = async (password) => {
    const salt = await bcrypt.genSalt(15)
    return await bcrypt.hash(password, salt)
}

//Funcion para comparar la contraseña ingresada con el Hash Almacenado
const validatePassword = async (password, hashedPassword) => {
    return await bcrypt.compare(password, hashedPassword)
}

module.exports = {
    validateError,
    hashPassword,
    validatePassword,
}