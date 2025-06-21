<template>
  <div class="home">
    <h1>Login con OAUTH</h1>
    <!-- Revisa la librería se encuentra cargando antes de mostrar los botones de acceso -->
    <div v-if="!$auth.loading">
      <!-- muestra el botón de login si no está autentificado -->
      <button v-if="!$auth.isAuthenticated" @click="login">Log in</button>
      <!-- muestra el botón de Log out si está autentificado -->
      <button v-if="$auth.isAuthenticated" @click="logout">Log out</button>
    </div>

  </div>
</template>

<script>
export default {
  name: "home",

  methods: {
    login() {
      this.$auth.loginWithRedirect();
    },
    logout() {
      this.$auth.logout({
        logoutParams: {
          returnTo: window.location.origin,
        },
      });
    },
  },
};
</script>