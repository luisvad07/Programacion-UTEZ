import instance from 'axios';
import {URL} from '../../utils/constans'
const AxiosClient = instance.create({
    baseURL: `${URL}:8080/api`,
});

const requestHandler = (request) => {
    request.headers['Accept'] = "application/json";
    request.headers['Content-type']='application/json';
    const session = JSON.parse(localStorage.getItem('user') || null);
    if(session?.isLogged)
    request.headers["Authorization"] = `Bearer ${session}`;
    return request;
};

const errorResponseHandler = (error) => {
    return Promise.reject(error);
};
const successResponseHandler = (response) => Promise.resolve(response.data);


AxiosClient.interceptors.request.use(
(request)=>requestHandler(request),
    (error) => Promise.reject(error)
);

AxiosClient.interceptors.response.use(
    (response) => successResponseHandler(response),
    (error)=> errorResponseHandler(error)
);
export default AxiosClient;