<template>
  <nav class="navbar navbar-expand-md navbar-light bg-primary">
    <div class="container">
      <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
        <ul class="navbar-nav">
          <li class="nav-item" v-if="isAuthenticated">
            <a :class="{ 'nav-link': true, 'active': isActive('/') }" href="/">Home</a>
          </li>
          <li class="nav-item" v-if="isAuthenticated">
            <a :class="{ 'nav-link': true, 'active': isActive('/profile') }" href="#/profile">Profile</a>
          </li>
          <li class="nav-item dropdown" v-if="isAuthenticated">
            <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
              Settings
            </a>
            <ul class="dropdown-menu">
              <li>
                <a class="dropdown-item" @click="logout">Logout</a>
              </li>
            </ul>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<script>
import { useAuth0 } from '@auth0/auth0-vue';
import { useRoute } from 'vue-router';

export default {
  name: "NavBar",
  setup() {
    const auth0 = useAuth0();
    const route = useRoute();

    const isActive = (path) => {
      return route.path === path;
    };

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
      },
      isActive
    }
  }
};
</script>

<style scoped>
.navbar-nav .nav-item {
  margin-right: 10px;
}

.dropdown-menu {
  min-width: auto;
}

.nav-link.active {
  color: #fff;
  /* Color de texto para enlace activo */
}

/* Color de fondo para enlaces en hover */
.nav-link:hover {
  background-color: rgba(255, 255, 255, 0.1);
}

/* Estilo específico para enlaces dentro del navbar */
.navbar-light .navbar-nav .nav-link {
  color: #fff;
  /* Color de texto para enlace */
}
</style>
