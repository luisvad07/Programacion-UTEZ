const { query } = require('../../../utils/mysql');
const fs = require('fs');
const path = require('path');

const findAll = async () => {
    const sql = "SELECT id, titulo, descripcion, imagen FROM Producto";
    const products = await query(sql, []);

        const productsWithFullURLs = products.map(product => ({
        ...product,
        imageUrl: `http://192.168.137.168:8080/uploads/${product.imagen}`,
    }));

    return productsWithFullURLs;
};


const findById = async(id) => {
    const sql = `SELECT * FROM Producto WHERE id = ?`
    return await query(sql, [id])
};

const save = async (producto) => {
        if (!producto.titulo || !producto.descripcion || !producto.imagen) {
        throw Error("Missing Fields");
    }

    try {
        const nameImg = `image_${producto.titulo}.png`;
        const sql = `INSERT INTO Producto (titulo, descripcion, imagen) VALUES (?, ?, ?)`;
        const { insertedId } = await query(sql, [
            producto.titulo,
            producto.descripcion,
            nameImg,
        ]);

        // Guardar la imagen en la carpeta uploads
        const base64Data = producto.imagen.replace(/^data:image\/\w+;base64,/, '');
        const dataBuffer = Buffer.from(base64Data, 'base64');
        const imagePath = path.join(__dirname, '../../../uploads', nameImg);

        fs.writeFileSync(imagePath, dataBuffer);

        return { ...producto, id: insertedId, imagePath };
    } catch (error) {
        throw error;
    }
};


const update = async(producto, id) => {
    if(Number.isNaN(id)) throw Error("Wrong Type");
    if(!id) throw Error("Missing Fields -> id");
    if(!producto.titulo || !producto.descripcion) throw Error("Missing Fileds");

    const sql = `UPDATE Producto SET titulo = ? , descripcion=? WHERE id=?`;
    await query(sql, [
        producto.titulo,
        producto.descripcion,
        id
    ]);
    return {...producto, id: id}
};

const remove = async (id) => {
        if (!id || Number.isNaN(id)) {
        throw new Error("Invalid or missing ID");
    }

    try {
        // Primero, eliminamos registros en la tabla Renta que hacen referencia a Item
        const deleteRentaQuery = `DELETE FROM Renta WHERE item_fk IN (SELECT id FROM Item WHERE producto_fk = ?)`;
        await query(deleteRentaQuery, [id]);

        // Luego, eliminamos registros en la tabla Item que hacen referencia a Producto
        const deleteItemQuery = `DELETE FROM Item WHERE producto_fk = ?`;
        await query(deleteItemQuery, [id]);

        // Finalmente, eliminamos el producto
        const deleteProductoQuery = `DELETE FROM Producto WHERE id = ?`;
        await query(deleteProductoQuery, [id]);

        return { idDeleted: id };
    } catch (error) {
        console.error("Error removing product:", error);
        throw new Error("Error removing product");
    }
};




module.exports = {
    findAll,
    findById,
    save,
    update,
    remove
}