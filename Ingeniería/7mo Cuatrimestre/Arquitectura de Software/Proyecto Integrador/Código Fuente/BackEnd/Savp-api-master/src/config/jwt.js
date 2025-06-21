const jwt = require('jsonwebtoken');
require('dotenv').config();
const {findById} = require('../modules/controller/rol/rol.gateway');

const generateToken = (payload) => {
    const token = jwt.sign(payload, process.env.TOKEN_SECRET);
    return {token: token, role: payload.role, username: payload.username, id: payload.id}
}
const auth = async(req, res , next) => {
    try {
        const token = req.headers.authorization?.replace('Bearer ','');
        if(!token) throw Error('Invalid Token');
        const decodeToken = jwt.verify(token, process.env.TOKEN_SECRET);
        req.token = decodeToken;
        next();
    } catch (error) {
        res.status(400).json({message: 'Unauthorized'});
    }
};
const checkRoles = (roles) => {
    return async(req, res, next) => {
        try {
            const token = req.token;
            const rol1 = await findById(token.role );
            if(!token) throw Error('Invalid TOken');
            if(!roles.some((role) => role === rol1[0].rol )) throw Error('Invalid Role');
            next();
        } catch (error) {
            res.status(400).json({message: 'Unauthorized role'});
        }
    }
}
module.exports = {
    generateToken,
    auth,
    checkRoles
};