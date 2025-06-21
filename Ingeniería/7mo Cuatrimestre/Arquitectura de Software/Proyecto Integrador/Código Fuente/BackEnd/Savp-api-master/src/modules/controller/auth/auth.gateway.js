const { query } = require("../../../utils/mysql");
const {generateToken} = require("../../../config/jwt");
const {validatePassword, hashPassword} = require("../../../utils/functios");
const { transporter, sendEmail, sendEmailReset } = require("./emailServer");

const generateRandomString = (length) => {
    const charset = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    let randomString = '';
  
    for (let i = 0; i < length; i++) {
      const randomIndex = Math.floor(Math.random() * charset.length);
      randomString += charset[randomIndex];
    }
  
    return randomString;
  };

const login = async(user) => {
    const {username, password} = user;
    if(!username || !password) throw Error("Missing Fields");
    const sql = `SELECT * FROM User WHERE username=? AND status=1`;
    const exist = await query(sql, [username]);
    if(
        await validatePassword(password, exist[0].password)
    )
    return generateToken({
        id: exist[0].id,
        username: exist[0].username,
        role: exist[0].rol_fk,
        isLogged: true
    })
    throw Error('Password missmatch');
}

const resetPassword = async(user) => {
    const {email} = user;
    if(!email) throw Error("Missing Fields");
    const randomString = generateRandomString(8);
    //envio de correo aqui
    
    const hashedpassword = await hashPassword(randomString);
    await sendEmailReset(email, randomString);
    const sql = `UPDATE User SET password=? WHERE username=?`;
    const exist = await query(sql, [hashedpassword, email]);
    return {status:true}
}

module.exports = {
    login, resetPassword
};