const mysql = require('mysql')

const connection = mysql.createConnection({
  host: 'localhost',
  user: 'root',
  password: 'root',
  port: 3306,
  database: 'SIGESU'
})

connection.connect((err)=>{
    if (err) {
        console.log('Ha ocurrido un error: '+err);
    } else {
        console.log('Base de datos conectada');
    }
});

module.exports = connection;