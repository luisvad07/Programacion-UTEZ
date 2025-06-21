<template>
  <div>
    <b-row>
      <b-button class="col-5 m-3 p-3" @click="consumoAdmin()">ROL ADMIN </b-button>
      <b-button class="col-5 m-3 p-3" @click="consumoUser()">ROL USER </b-button>
    </b-row>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  methods: {
    consumoAdmin() {
      const token = localStorage.getItem("token");
      //validamos si existe token y hacemos la petición.
      if (token) {
        axios
          .get("http://localhost:8080/auth/admin/adminProfile", {
            headers: { //configuración de la petición donde introducimos el atributo de autorización
              Authorization: `Bearer ${token}`, //dependiendo de la configuración de JWT
            },//, nosotros tenemos que apuntar al valor de 'Bearer' y concatenar el token
          })
          .then((response) => {
            console.log(response);
            // Manejar respuesta
          })
          .catch((error) => {
            // Manejar error
          });
      }
    },
    consumoUser() {
      const token = localStorage.getItem("token");
      //validamos si existe token y hacemos la petición.
      if (token) {
        axios
          .get("http://localhost:8080/auth/user/userProfile", {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          })
          .then((response) => {
            console.log(response);
            // Manejar respuesta
          })
          .catch((error) => {
            // Manejar error
          });
      }
    },
  },
};
</script>

<style>
</style>