const { app } = require('./config/servidor')

const main = () => {
    try{
        //Se pone en escucha el servidor
        app.listen(app.get('port'))
        console.log(`Running in http://localhost:${app.get('port')}/`)
    }catch (err) {
        //En caso de error se imprime aqui
        console.log(err)
    }
}

//Se ejecuta el metodo main
main()