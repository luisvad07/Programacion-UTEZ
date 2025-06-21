<template>
  <div class="container mt-5">
    <div class="row">
      <div class="col-md-6 offset-md-3">
        <h2>Registro de Usuario</h2>
        <form @submit.prevent="registerUser">
          <div class="mb-3">
            <label for="name" class="form-label">Nombre</label>
            <input type="text" class="form-control" id="name" v-model="user.name" required>
          </div>
          <div class="mb-3">
            <label for="username" class="form-label">Nombre de Usuario</label>
            <input type="text" class="form-control" id="username" v-model="user.username" required>
          </div>
          <div class="mb-3">
            <label for="password" class="form-label">Contraseña</label>
            <input type="password" class="form-control" id="password" v-model="user.password" required>
          </div>
          <button type="submit" class="btn btn-primary">Registrar</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'UserRegistration',
  data() {
    return {
      user: {
        name: '',
        username: '',
        password: '',
      },
    };
  },
  methods: {
    registerUser() {
      axios.post('http://localhost:8080/auth/addNewUser', this.user)
        .then(response => {
          console.log('Respuesta del servidor:', response.data);
          // Aquí puedes redirigir al usuario o mostrar algún mensaje de éxito.
          // Aquí conectamos con la lógica de navegación por ROL. O bien, le pedimos
          // al usuario que nos diga que ROL es el que tenemos que utilizar 
          // y hacemos la navegación coon dicho ROL
        })
        .catch(error => {
          console.error('Hubo un error al registrar el usuario:', error);
          // Aquí puedes manejar el error, posiblemente mostrar un mensaje al usuario
        });
    },
  },
};
</script>

<style scoped>
</style>
