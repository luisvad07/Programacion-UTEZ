<template>
  <div class="container">
    <h1 v-if="!isAuthenticated">Bienvenido a la aplicación</h1>

    <div v-if="isAuthenticated">
      <h1> Hola, {{ user.name }} bienvenido a la aplicación </h1>
    </div>

    <button v-if="!isAuthenticated" @click="login" class="btn btn-custom mt-3">Iniciar Sesión</button>
  </div>
</template>

<script>
import { useAuth0 } from '@auth0/auth0-vue';

export default {
  name: "HomeContent",
  setup() {
    const auth0 = useAuth0();

    return {
      isAuthenticated: auth0.isAuthenticated,
      isLoading: auth0.isLoading,
      user: auth0.user,
      login() {
        auth0.loginWithRedirect();
      },
      logout() {
        auth0.logout({
          logoutParams: {
            returnTo: window.location.origin
          }
        });
      }
    }
  }
};
</script>

<style scoped>
.btn-custom {
  background-color: #007bff;
  color: #fff;
  border: none;
}

.btn-custom:hover {
  background-color: #0056b3;
  border-color: #0056b3;
}
</style>
