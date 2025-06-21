const nodemailer = require("nodemailer");

const transporter = nodemailer.createTransport({
    service: "gmail",
    auth: {
        user: "barriostecorral@gmail.com",
        pass: "xmgbuxytdkdddciq"
    }
})

const sendEmail = async (email)=>{
    const mailOptions = {
        to: email,
        subject: "Bienvenido",
        html:`
            <div>
                <h1>Bienvenido ${email}</h1>
            </div>
        `
    }
    try {
        await transporter.sendMail(mailOptions);
    } catch (error) {
        console.log(error)
    }
}

const sendEmailReset = async (email, nueva)=>{
    const mailOptions = {
        to: email,
        subject: "Cambio de Contraseña",
        html:`
            <div>
                <h1>Tu nueva contraseña es:  ${nueva}</h1>
            </div>
        `
    }
    try {
        await transporter.sendMail(mailOptions);
    } catch (error) {
        console.log(error)
    }
}

const sendEmailDevolucion = async (email, articulos)=>{
    const mailOptions = {
        to: email,
        subject: "Se Acerca La Fecha De Pago",
        html:`
            <div>
                <h3>La fecha limite para los articulos</h3>
                ${articulos.map((item)=>(
                    `<br>
                    <h3>${item.titulo}</h3>
                    <br>`
                ))}
                <h2>es mañana</h2>
            </div>
        `
    }
    try {
        await transporter.sendMail(mailOptions);
    } catch (error) {
        console.log(error)
    }
}

const sendEmailRenta = async (email, datos)=>{
    console.log("Holaaa", datos);
    const mailOptions = {
        to: email,
        subject: "Gracias Por Rentar",
        html:`
            <div>
                <h1>Gracias por la renta de los siguientes titulos:</h1>
                ${datos.map((item)=>(
                    `<br>
                    <h3>${item.titulo}</h3>
                    <h4>Plataforma: ${item.plataforma}</h4>
                    <div>${item.item_desc}</div>
                    <br>`
                ))}
                <h4>Fecha de Entrega: ${new Date(Date.now() + 7 * 24 * 60 * 60 * 1000).toLocaleDateString('es-ES')}</h4>
                <h5>Pase a Recoger su Pedido a Tienda</h5>
            </div>
        `
    }
    try {
        await transporter.sendMail(mailOptions);
    } catch (error) {
        console.log(error)
    }
}


module.exports = {transporter, sendEmail, sendEmailReset, sendEmailRenta, sendEmailDevolucion}