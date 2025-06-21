<template>
    <div>
      <b-breadcrumb :items="items"></b-breadcrumb>
      <b-link href="/#/inicio/electronicos/videojuegos">Videojuegos</b-link>
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
            name: "Inicio",
          },
        ],
      };
    },
  };
  </script>
      
  <style>
  </style>