const rutas = require('express').Router();
const conexion = require('./config/conexion');

/* RUTAS PRINCIPALES */
//Se asignan las rutas
rutas.get('/', function(req, res){
    res.send('INICIO');
});

rutas.get('/api', function(req, res){
    res.send('Bienvenido a la app de Tienda SIGESU');
});


/********** GESTION EMPLEADOS **********/

//get Usuarios
rutas.get('/empleados', (req, res) => {
    let sql = `SELECT *, tipo_usurio.rol FROM empleados INNER JOIN tipo_usurio WHERE empleados.id = tipo_usurio.id`;
    conexion.query(sql, (err, rows, fields)=>{
        if (err) {
            throw err;
        } else {
            res.json(rows);
        }
    })
});

//get un usuario
rutas.get('/empleados/:id', (req, res) => {
    const {id} = req.params
    let sql = `SELECT * FROM empleados ep WHERE ep.id = ?`;
    conexion.query(sql,[id],(err, rows, fields)=>{
        if (err) {
            throw err;
        } else {
            res.json(rows);
        }
    })
});

//agregar usuario
rutas.post('/empleados/', (req, res) => {
    const {name, surname, lastname, password, curp, id_usuario} = req.body
    let sql = `INSERT INTO empleados(name, surname, lastname, password, curp, id_usuario) VALUES ('${name}','${surname}','${lastname}','${password}','${curp}','${id_usuario}')`;
    conexion.query(sql,(err, rows, fields)=>{
        if (err) {
            throw err;
        } else {
            res.json({status: 'Usuario agregado correctamente'});
        }
    })
});

//get un usuario
rutas.delete('/empleados/:id', (req, res) => {
    const {id} = req.params
    let sql = `DELETE FROM empleados WHERE empleados.id = '${id}'`;
    conexion.query(sql,[id],(err, rows, fields)=>{
        if (err) {
            throw err;
        } else {
            res.json(rows);
        }
    })
});

//agregar usuario
rutas.put('/empleados/:id', (req, res) => {
    const {id} = req.params
    const {name, surname, lastname, password, curp, id_usuario} = req.body
    let sql = `UPDATE empleados set name = '${name}', 
                surname = '${surname}', lastname = '${lastname}', 
                password = '${password}', curp = '${curp}', 
                id_usuario = '${id_usuario}' WHERE id = '${id}'`;
    conexion.query(sql,(err, rows, fields)=>{
        if (err) {
            throw err;
        } else {
            res.json({status: 'Usuario modificado correctamente'});
        }
    })
});



/********** GESTION PRODUCTOS**********/

//mostrar productos
rutas.get('/productos', (req, res) => {
    let sql = `SELECT *, tipo_producto.rol FROM productos INNER JOIN tipo_producto WHERE productos.id = tipo_producto.id`;
    conexion.query(sql, (err, rows, fields)=>{
        if (err) {
            throw err;
        } else {
            res.json(rows);
        }
    })
});

//get un producto
rutas.get('/productos/:id', (req, res) => {
    const {id} = req.params
    let sql = `SELECT * FROM productos ep WHERE ep.id = ?`;
    conexion.query(sql,[id],(err, rows, fields)=>{
        if (err) {
            throw err;
        } else {
            res.json(rows);
        }
    })
});

//agregar producto
rutas.post('/productos/', (req, res) => {
    const {name_product, codigo_product, precio, cantidad, id_tipo_product} = req.body
    let sql = `INSERT INTO productos(name_product, codigo_product, precio, cantidad, id_tipo_product) VALUES ('${name_product}','${codigo_product}','${precio}','${cantidad}','${id_tipo_product}')`;
    conexion.query(sql,(err, rows, fields)=>{
        if (err) {
            throw err;
        } else {
            res.json({status: 'Producto agregado correctamente'});
        }
    })
});

//eliminar un producto
rutas.delete('/productos/:id', (req, res) => {
    const {id} = req.params
    let sql = `DELETE FROM productos WHERE productos.id = '${id}'`;
    conexion.query(sql,[id],(err, rows, fields)=>{
        if (err) {
            throw err;
        } else {
            res.json(rows);
        }
    })
});

//agregar producto
rutas.put('/productos/:id', (req, res) => {
    const {id} = req.params
    const {name_product, codigo_product, precio, cantidad, id_tipo_product} = req.body
    let sql = `UPDATE productos set name_product = '${name_product}', 
                codigo_product = '${codigo_product}', precio = '${precio}', 
                cantidad = '${cantidad}', id_tipo_product = '${id_tipo_product}' WHERE id = '${id}'`;
    conexion.query(sql,(err, rows, fields)=>{
        if (err) {
            throw err;
        } else {
            res.json({status: 'Producto modificado correctamente'});
        }
    })
});


//********** GESTION DE CLIENTES **********//

//get clientes
rutas.get('/clientes', (req, res) => {
    let sql = `SELECT *, tipo_cliente.tipo_cliente FROM clientes INNER JOIN tipo_cliente WHERE clientes.id = tipo_cliente.id`;
    conexion.query(sql, (err, rows, fields)=>{
        if (err) {
            throw err;
        } else {
            res.json(rows);
        }
    })
});

//get un cliente
rutas.get('/clientes/:id', (req, res) => {
    const {id} = req.params
    let sql = `SELECT * FROM clientes WHERE clientes.id = ?`;
    conexion.query(sql,[id],(err, rows, fields)=>{
        if (err) {
            throw err;
        } else {
            res.json(rows);
        }
    })
});

//agregar cliente
rutas.post('/clientes/', (req, res) => {
    const {id, nombre, apellidos, email, password, id_cliente} = req.body
    let sql = `INSERT INTO clientes(nombre, apellidos, email, password, id_cliente) VALUES ('${nombre}','${apellidos}','${email}','${password}','${id_cliente}')`;
    conexion.query(sql,(err, rows, fields)=>{
        if (err) {
            throw err;
        } else {
            res.json({status: 'Cliente agregado correctamente'});
        }
    })
});

//eliminar cliente
rutas.delete('/clientes/:id', (req, res) => {
    const {id} = req.params
    let sql = `DELETE FROM clientes WHERE clientes.id = '${id}'`;
    conexion.query(sql,[id],(err, rows, fields)=>{
        if (err) {
            throw err;
        } else {
            res.json(rows);
        }
    })
});

//agregar usuario
rutas.put('/clientes/:id', (req, res) => {
    const {id} = req.params
    const {nombre, apellidos, email, password, id_cliente} = req.body
    let sql = `UPDATE clientes set nombre = '${nombre}', 
    apellidos = '${apellidos}', email = '${email}', 
                password = '${password}', 
                id_cliente = '${id_cliente}' 
                WHERE id = '${id}'`;
    conexion.query(sql,(err, rows, fields)=>{
        if (err) {
            throw err;
        } else {
            res.json({status: 'Usuario modificado correctamente'});
        }
    })
});


/********** GESTION DE VENTAS **********/

//get Usuarios
rutas.get('/ventas', (req, res) => {
    let sql = `select ventas.id_venta, ventas.codigoVenta, productos.name_product, ventas.productosVendidos, productos.precio*ventas.productosVendidos AS total, ventas.nomCajero FROM ventas INNER JOIN productos`;
    conexion.query(sql, (err, rows, fields)=>{
        if (err) {
            throw err;
        } else {
            res.json(rows);
        }
    })
});

//get un usuario
rutas.get('/ventas/:id', (req, res) => {
    const {id} = req.params
    let sql = `SELECT * FROM ventas ep WHERE ep.id_venta = ?`;
    conexion.query(sql,[id],(err, rows, fields)=>{
        if (err) {
            throw err;
        } else {
            res.json(rows);
        }
    })
});

//agregar usuario
rutas.post('/ventas/', (req, res) => {
    const {codigoVenta, id_producto, productosVendidos,nomCajero} = req.body
    let sql = `INSERT INTO ventas(codigoVenta, id_producto, productosVendidos, nomCajero) VALUES ('${codigoVenta}','${id_producto}','${productosVendidos}','${nomCajero}')`;
    conexion.query(sql,(err, rows, fields)=>{
        if (err) {
            throw err;
        } else {
            res.json({status: 'Venta agregada correctamente'});
        }
    })
});

//get un usuario
rutas.delete('/ventas/:id', (req, res) => {
    const {id} = req.params
    let sql = `DELETE FROM ventas WHERE ventas.id_venta = '${id}'`;
    conexion.query(sql,[id],(err, rows, fields)=>{
        if (err) {
            throw err;
        } else {
            res.json(rows);
        }
    })
});

//agregar usuario
rutas.put('/ventas/:id', (req, res) => {
    const {id} = req.params
    const {codigoVenta, id_producto, productosVendidos, nomCajero} = req.body
    let sql = `UPDATE ventas set codigoVenta = '${codigoVenta}', id_producto = '${id_producto}', 
                productosVendidos = '${productosVendidos}', 
                nomCajero = '${nomCajero}' WHERE id_venta = '${id}'`;
    conexion.query(sql,(err, rows, fields)=>{
        if (err) {
            throw err;
        } else {
            res.json({status: 'venta modificada correctamente'});
        }
    })
});

module.exports = rutas;