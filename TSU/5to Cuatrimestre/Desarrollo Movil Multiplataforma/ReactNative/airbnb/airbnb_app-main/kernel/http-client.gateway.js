import AxiosClient from '../config/axios'
export default{
    doGet(endPoint){
        return AxiosClient.get(endPoint)
    },
    doPost(endPoint, payload){
        return AxiosClient.post(endPoint,payload)
    },
    doPut(endPoint, payload){
        return AxiosClient.put(endPoint, payload)
    },
    doDelete(endPoint){
        return AxiosClient.delete(endPoint)
    }
}

//doPostBlob -> para subir archivos
//doGet xsls -> para recibir archivos excel