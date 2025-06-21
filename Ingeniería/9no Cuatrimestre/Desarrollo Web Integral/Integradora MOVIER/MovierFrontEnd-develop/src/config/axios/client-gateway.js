import AxiosClient from './axios'
import Swal from 'sweetalert2'

//interceptor para las request
AxiosClient.interceptors.request.use(
  (config) => {
    const authToken = localStorage.getItem("idToken");
    if (Boolean(authToken)) {
      if (!config.url.includes("login") && !config.url.includes("create_user") && !config.url.includes("set_password")) {
        config.headers.Authorization = `${authToken}`;
      }
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
)

//interceptor para los response
AxiosClient.interceptors.response.use(
  (response) => {
    if (response.status && response.status >= 200) {
      return Promise.resolve(response);
    }
  },
  (error) => {
    
    let errorMessage = 'Ha ocurrido un error en el servidor';
    if (error.response && error.response.data) {
      errorMessage = error.response.data.error_message || error.response.data.message;
    }
    if (errorMessage === "AuthenticationResult not in response") {
      window.location.href = '/complete-login';
      const config = JSON.parse(error.response.config.data);
      localStorage.setItem('email', config.username);
      localStorage.setItem('password', config.password);
    } else {      
      if(localStorage.getItem('idToken') !== null && !window.location.pathname.includes("/login") && !window.location.pathname.includes("/signup") && !window.location.pathname.includes("/complete-login")){
        switch(error.response ? error.response.status : 0){
          case 401:
            errorMessage = "Sesión expirada. Por favor inicia sesión nuevamente.";
            break;
          }
      } 
      Swal.fire({
        title: "Oops...",
        text: errorMessage,
        icon: 'error',
        confirmButtonText: 'Aceptar'
      })
    }
    return Promise.reject(error.response);
  }
)

export default {
  doGet(endPoint) {
    return AxiosClient.get(endPoint);
  },
  doPost(endPoint, object) {
    return AxiosClient.post(endPoint, object);
  },
  doPut(endPoint, object) {
    return AxiosClient.put(endPoint, object);
  },
  doPatch(endPoint, object) {
    return AxiosClient.patch(endPoint, object);
  },
  doDelete(endPoint) {
    return AxiosClient.delete(endPoint);
  }
}