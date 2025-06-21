
import { API_URL } from "./constants";

export async function getUserData() {
  try {
    const userInfo = localStorage.getItem("userInfo");
    const user = JSON.parse(userInfo);
    return user;
  } catch (error) {
    console.log(error);
    return null;
  }
}

export async function fetchClient(ruta, metodo, datos) {
  try {
    
    const userInfo = await getUserData();
    const url = `${API_URL}${ruta}`;
    let params = null;
    if (userInfo) {
      params = {
        method: metodo,
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${userInfo.access_token}`,
        },
        body: datos ? JSON.stringify(datos) : null,
      };
    } else {
      params = {
        method: metodo,
        headers: {
          "Content-Type": "application/json",
        },
        body: datos ? JSON.stringify(datos) : null,
      };
    }
    const response = await fetch(url, params);
    if (response.status >= 200 && response.status < 300) {
      const result = await response.json();
      return result;
    } else if (response.status === 401) {
      localStorage.removeItem("userInfo");
      return null;
    }
  } catch (error) {
    console.log(error);
    return null;
  }
}
