const mysql = require('mysql')
require('dotenv').config()

//Crea un Pool de Conexiones con un Limite de 5
const conexion = mysql.createPool({
    connectionLimit: 5,
    host: process.env.DB_HOST,
    user: process.env.DB_USER,
    password: process.env.DB_PASSWORD,
    database: process.env.DB_DATABASE,
    port: process.env.DB_PORT
})

//Funcion para recibir las peticiones SQL, acceder a la BD y Ejecutarlas
const query = (sql, params) => {
    return new Promise ((resolve, reject) => {
        conexion.getConnection((err, conn) => {
            if (err) {
                reject(err);
                return;
            }
            conn.query(sql, params, (err, rows) => {
                if (err) {
                    conn.release();
                    reject(err);
                    return;
                }
                conn.release();
                resolve(rows);
            });
        });
    });
}

module.exports = {
    query
}