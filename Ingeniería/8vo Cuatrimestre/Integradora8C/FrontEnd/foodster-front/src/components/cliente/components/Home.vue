<template>
  <div>
    <header class="w-full">
      <v-tabs fixed-tabs dark class="bg-gray-900 animate__animated animate__fadeInLeft">
        <v-tab v-for="item in items" :key="item.title" :to="item.to">
          <v-icon>{{ item.icon }}</v-icon>
          {{ item.title }}
        </v-tab>
      </v-tabs>
    </header>
    <router-view></router-view>
  </div>
</template>

<script>
import { watch } from 'vue';
import { useAuthStore } from "@/stores";

export default {
  setup() {
    const authStore = useAuthStore();
    const items = [
      {title: 'Inicio', icon: 'mdi-home', to: '/home/inicio'},
      {title: 'Paquetes', icon: 'mdi-package-variant-closed', to: '/home/paquetes'},
      {title: 'Servicios', icon: 'mdi-food', to: '/home/servicios'},
      {title: 'Carrito', icon: 'mdi-cart', to: '/home/carrito'},
    ];

    watch(() => authStore.user, (newUser) => {
      if (newUser && newUser.usuarios.roles[0].nombre === 'CLIENTE') {
        items.splice(4, 2)
        items.push({title: 'Perfil', icon: 'mdi-account', to: '/home/perfil'});
      } else {
        items.splice(4, 1)
        items.push({title: 'Iniciar sesión', icon: 'mdi-login', to: '/home/login'});
        items.push({title: 'Registrarse', icon: 'mdi-account-plus', to: '/home/registro'});
      }
    }, { immediate: true });

    return { items };
  }
}
</script>

<style scoped>
.v-app-bar {
  background-color: #6A040F;
}

.v-toolbar {
  color: white;
}

.v-btn {
  color: white;
}

.v-icon {
  color: white;
}

/* Estilos personalizados según sea necesario */
</style>