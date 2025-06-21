use aeropuerto;

db.aeropuertos.insertMany([
    {nombre: "Aeropuerto Internacional de la Ciudad de México", ciudad: "Ciudad de México", pais: "México", abreviatura: "AICM"},
    {nombre: "Aeropuerto Internacional de Cancún", ciudad: "Cancún", pais: "México", abreviatura: "AIC"},
    {nombre: "Aeropuerto Internacional de Los Ángeles", ciudad: "Los Ángeles", pais: "Estados Unidos" , abreviatura: "LAX"},
    {nombre: "Aeropuerto Internacional de Madrid-Barajas", ciudad: "Madrid", pais: "España",abreviatura: "MAD"},
    {nombre: "Aeropuerto Internacional de París-Charles de Gaulle", ciudad: "París", pais: "Francia", abreviatura: "CDG"},
]);

db.aviones.insertMany([
    {marca: "Boeing", modelo: "737", no_serie: "123456", matricula: "X-ABCD"},
    {marca: "Airbus", modelo: "A320", no_serie: "654321", matricula: "X-DCBA"},
    {marca: "Boeing", modelo: "747", no_serie: "789456", matricula: "X-EFGH"},
    {marca: "Airbus", modelo: "A380", no_serie: "987654", matricula: "X-HGFE"},
    {marca: "Boeing", modelo: "787", no_serie: "456123", matricula: "X-IJKL"},
]);
db.pilotos.insertMany([
    {nombre: "Juan", apellidos: "Pérez", experiencia: 10, telefono: "1234567890"},
    {nombre: "María", apellidos: "González", experiencia: 15, telefono: "0987654321"},
    {nombre: "Pedro", apellidos: "López", experiencia: 20, telefono: "1357924680"},
    {nombre: "Ana", apellidos: "Martínez", experiencia: 25, telefono: "2468135790"},
    {nombre: "Luis", apellidos: "Hernández", experiencia: 30, telefono: "9876543210"},
]);
db.auxiliares.insertMany([
    {nombre: "Sofía", apellidos: "Ramírez", puesto: "Jefa de Cabina", telefono: "1234567890"},
    {nombre: "Carlos", apellidos: "Gómez", puesto: "Auxiliar de Vuelo", telefono: "0987654321"},
    {nombre: "Fernanda", apellidos: "Díaz", puesto: "Auxiliar de Vuelo", telefono: "1357924680"},
    {nombre: "Jorge", apellidos: "Torres", puesto: "Auxiliar de Vuelo", telefono: "2468135790"},
    {nombre: "Diana", apellidos: "Sánchez", puesto: "Auxiliar de Vuelo", telefono: "9876543210"},
]);
db.pasajeros.insertMany([
    {nombre: "José", apellidos: "García", telefono: "1234567890", correo: "josegarcia@gmasil.com"},
    {nombre: "Luisa", apellidos: "Martínez", telefono: "0987654321", correo: "luisa_martinez@outlook.es"},
    {nombre: "Roberto", apellidos: "Hernández", telefono: "1357924680", correo: "roberthdz@gmail.com"},
    {nombre: "María", apellidos: "González", telefono: "2468135790", correo: "maria_002@hotmail.com"},
    {nombre: "Carlos", apellidos: "Pérez", telefono: "9876543210", correo: "carlitos2043@utez.edu.mx"},
]);
db.vuelos.insertMany([
    {
        num_vuelo: "AMX123",
        origen: "AICM",
        destino: "AIC",
        fecha_salida: new Date("2021-10-01T08:00:00Z"),
        fecha_llegada: new Date("2021-10-01T10:00:00Z"),
        pilotos:[{telefono: "1234567890"},{telefono: "0987654321"}],
        auxiliares:[{telefono: "1234567890"},{telefono: "0987654321"},{telefono: "1357924680"}]
    },
]);
db.boletos.insertOne({
    pasajero: "josegarcia@gmasil.com",
    tipo_asiento: "Primera Clase",
    num_asiento: "1A",
    costo: 5000,
    extra: "Sin extra",
    status: "Pagado",
    vuelo: "AMX123"
});

db.aeropuertos.find()
db.auxiliares.find()
db.aviones.find()
db.boletos.find()
db.pasajeros.find()
db.pilotos.find()
db.vuelos.find()

db.vuelos.aggregate([
    {
        $lookup:{
            from: "aeropuertos",
            localField: "destino",
            foreignField: "abreviatura",
            as: "destino"
        }
    },
    {
        $lookup:{
            from: "aeropuertos",
            localField: "origen",
            foreignField: "abreviatura",
            as: "origen"
        }
    },
    {
        $lookup:{
            from: "pilotos",
            localField: "pilotos.telefono",
            foreignField: "telefono",
            as: "pilotos"
        }
    },
    {
        $lookup:{
            from: "auxiliares",
            localField: "auxiliares.telefono",
            foreignField: "telefono",
            as: "auxiliares"
        }
    },
]);

db.boletos.aggregate([
    {
        $lookup:{
            from: "pasajeros",
            localField: "pasajero",
            foreignField: "correo",
            as: "pasajero"
        }
    },

   {
        $lookup:{
            from: "vuelos",
            localField: "vuelo",
            foreignField: "num_velo",
            as: "vuelo"
        }
    },
]);