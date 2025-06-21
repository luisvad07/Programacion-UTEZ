import { fetchClient } from "../../utils/fetchClient";
// fetchClient(url, method, body)
export const getUsuariosActivos = async () => {
  try {
    let data = await fetchClient("/users/", "GET", null);
    return data;
  } catch (error) {
    console.error(error);
  }
};
export const save = async (user) => {
  try {
    let data = await fetchClient("/users/", "POST", user);
    return data;
  } catch (error) {
    console.error(error);
  }
};
export const update = async (user) => {
  try {
    let data = await fetchClient("/users/", "PUT", user);
    return data;
  } catch (error) {
    console.error(error);
  }
};
export const remove = async (username) => {
  try {
    let data = await fetchClient(`/users/${username}`, "DELETE", null);
    return data;
  } catch (error) {
    console.error(error);
  }
};

export const block = async (username) => {
  try {
    let data = await fetchClient(`/users/block/${username}`, "PUT", null);
    return data;
  } catch (error) {
    console.error(error);
  }
};
export const getUsuario = async (username) => {
  try {
    let data = null;
    data = await fetchClient(`/users/${username}`, "GET", null);
    return data;
  } catch (error) {
    console.error(error);
  }
};

export const changePassword = async (usuario) => {
  try {
    let data = await fetchClient("/users/pass/", "PUT", usuario);
    return data;
  } catch (error) {
    console.error(error);
  }
};
