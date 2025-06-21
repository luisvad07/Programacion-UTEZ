const bcrypt = require('bcrypt');


const hashPassword = async(pass) => {
    const salt = await bcrypt.genSalt(15);
    return await bcrypt.hash(pass,salt);
}
const validatePassword = async(password, hashedPassword) => {
    return await bcrypt.compare(password, hashedPassword);
}
module.exports = { 
    hashPassword, validatePassword
}
