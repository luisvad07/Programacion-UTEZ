import axios from 'axios';

let GET_PELICULA = 'http://localhost:8080/api/peliculas/getAll';
let GET_GENERO = 'http://localhost:8080/api/generos/getAll';
let ADD_PELICULA = 'http://localhost:8080/api/peliculas/registrar';
let DELETE_PELICULA = 'http://localhost:8080/api/peliculas/';
let UPDATE_PELICULA = 'http://localhost:8080/api/peliculas/actualizar';

let SEARCH_BY_NAME = 'http://localhost:8080/api/peliculas/buscarPorNombre';
let SEARCH_BY_DIRECTOR = 'http://localhost:8080/api/peliculas/buscarPorDirector';
let SEARCH_BY_DATE_RANGE = 'http://localhost:8080/api/peliculas/buscarPorFechaPublicacion';
let SEARCH_BY_GENRE = 'http://localhost:8080/api/peliculas/buscarPorGenero';
let SEARCH_BY_DATE_DESCENDING = 'http://localhost:8080/api/peliculas/buscarPorFechaPublicacionDesc';

const obtenerPeliculas = async () => {
    try {
       const response = await axios.get(GET_PELICULA);
       console.log(response);
       return response.data.data;
    } catch (error) {
       throw error;
    }
 };
 
 const obtenerGeneros = async () => {
    try {
       const response = await axios.get(GET_GENERO);
       console.log(response);
       return response.data.data;
    } catch (error) {
       throw error;
    }
 };
 
 const agregarPelicula = async (datosPelicula) => {
     try {
        const response = await axios.post(ADD_PELICULA, datosPelicula);
        console.log(response);
        return response.data.data;
     } catch (error) {
        throw error;
     }
 };
 
 const eliminarPelicula = async (idPelicula) => {
     try {
        const response = await axios.delete(`${DELETE_PELICULA}${idPelicula}`);
        console.log(response);
        return response.data.data;
     } catch (error) {
        throw error;
     }
 };
 
 const actualizarPelicula = async (datosPelicula) => {
     try {
        const response = await axios.put(UPDATE_PELICULA, datosPelicula);
        console.log(response);
        return response.data.data;
     } catch (error) {
        throw error;
     }
 };

 const buscarPeliculaPorNombre = async (nombre) => {
    try {
        const response = await axios.get(`${SEARCH_BY_NAME}?nombre=${nombre}`);
        console.log(response);
        return response.data.data;
    } catch (error) {
        throw error;
    }
};

const buscarPeliculaPorDirector = async (director) => {
    try {
        const response = await axios.get(`${SEARCH_BY_DIRECTOR}?director=${director}`);
        console.log(response);
        return response.data.data;
    } catch (error) {
        throw error;
    }
};

const buscarPeliculaPorFechaPublicacion = async (fechaInicio, fechaFin) => {
    try {
        const response = await axios.get(`${SEARCH_BY_DATE_RANGE}?fechaInicio=${fechaInicio}&fechaFin=${fechaFin}`);
        console.log(response);
        return response.data.data;
    } catch (error) {
        throw error;
    }
};

const buscarPeliculaPorGenero = async (genero) => {
    try {
        const response = await axios.get(`${SEARCH_BY_GENRE}?genero=${genero}`);
        console.log(response);
        return response.data.data;
    } catch (error) {
        throw error;
    }
};

const buscarPeliculaPorFechaPublicacionDesc = async () => {
    try {
        const response = await axios.get(SEARCH_BY_DATE_DESCENDING);
        console.log(response);
        return response.data.data;
    } catch (error) {
        throw error;
    }
};

export default {
    obtenerPeliculas,
    obtenerGeneros,
    agregarPelicula,
    eliminarPelicula,
    actualizarPelicula,
    buscarPeliculaPorNombre,
    buscarPeliculaPorDirector,
    buscarPeliculaPorFechaPublicacion,
    buscarPeliculaPorGenero,
    buscarPeliculaPorFechaPublicacionDesc
};
