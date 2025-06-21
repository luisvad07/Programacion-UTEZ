import axios from "axios"
const SERVER_URL = 'http://<ip>:8080/api-airbnb'
const instance = axios.create({
    baseURL: SERVER_URL,
    timeout: 5000
})

export default instance;