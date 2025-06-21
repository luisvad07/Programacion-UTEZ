<template>
    <div>
      <b-breadcrumb :items="items"></b-breadcrumb>
      Minecraft, Fornite y Need for Speed
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        items: [
          {
            text: "Tienda de Juguetes",
            href: "/#/inicio",
            name: "Inicio",
          },
          {
            text: "Electronicos",
            href: "/#/inicio/electronicos",
            name: "Electronicos",
          },
          {
            text: "Videojuegos",
            href: "/#/inicio/electronicos/videojuegos",
            name: "videojuegos",
          },
        ],
      };
    },
  };
  </script>
      
  <style>
  </style>