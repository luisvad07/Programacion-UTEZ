<template>
  <div class="container mt-5">
    <div class="row">
      <div class="col-md-6 offset-md-3">
        <h2>Iniciar Sesión</h2>
        <form @submit.prevent="login">
          <div class="mb-3">
            <label for="username" class="form-label">Nombre de usuario</label>
            <input type="text" class="form-control" id="username" v-model="authRequest.username" required>
          </div>
          <div class="mb-3">
            <label for="password" class="form-label">Contraseña</label>
            <input type="password" class="form-control" id="password" v-model="authRequest.password" required>
          </div>
          <button type="submit" class="btn btn-primary">Iniciar Sesión</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import { jwtDecode } from "jwt-decode";
export default {
  name: 'LoginComponent',
  data() {
    return {
      authRequest: {
        username: '',
        password: '',
      },
    };
  },
  methods: {
    login() {
      axios.post('http://localhost:8080/auth/generateToken', this.authRequest)
        .then(response => {
          let decoded = jwtDecode(response.data);
          localStorage.setItem('token', response.data);
          console.log(decoded);
          // como en el proyecto anterior, se puede preguntar al usuario
          //a qué funcionalidades desea ingresar, por lo que sería válido preguntar sobre
          //su rol activo y redirigirlo hacia esas funciones. Así como guardar en localStorage,
          //el rol principal o el rol activo.
        })
        .catch(error => {
          console.error('Error de autenticación:', error);
          // Manejo del error, posiblemente mostrar un mensaje al usuario
        });
    },
  },
};
</script>
