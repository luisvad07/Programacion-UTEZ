const { query } = require("../../../utils/mysql")

// CREATE TABLE `Carrito` (
//     `id` int NOT NULL AUTO_INCREMENT,
//     `user_fk` int NOT NULL,
//     `item_fk` int NOT NULL,
//     `cantidad` int NOT NULL,
//     `fecha_agregado` datetime NOT NULL,
//     PRIMARY KEY (`id`),
//     KEY `user_fk` (`user_fk`),
//     KEY `item_fk` (`item_fk`),
//     CONSTRAINT `Carrito_ibfk_1` FOREIGN KEY (`user_fk`) REFERENCES `User` (`id`),
//     CONSTRAINT `Carrito_ibfk_2` FOREIGN KEY (`item_fk`) REFERENCES `Item` (`id`)
//   ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
  
const findAll = async () => {
    const sql = `
        SELECT
            c.id,
            u.username,
            i.descripcion,
            c.cantidad,
            c.fecha_agregado
        FROM
            Carrito c
        JOIN
            User u ON c.user_fk = u.id
        JOIN
            Item i ON c.item_fk = i.id
    `;
    return await query(sql, []);
}

module.exports = {
    findAll
}