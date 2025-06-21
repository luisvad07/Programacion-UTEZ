<template>
    <div>
      <b-breadcrumb :items="items"></b-breadcrumb>
      <b-link href="/#/user/hogar">Productos de Limpieza</b-link>
    </div>
</template>
  
  <script>
  export default {
    data() {
      return {
        items: [
          {
            text: "Tienda de Productos para el Hogar",
            href: "/#/user",
            name: "User",
          },
        ],
      };
    },
  }
  </script>
