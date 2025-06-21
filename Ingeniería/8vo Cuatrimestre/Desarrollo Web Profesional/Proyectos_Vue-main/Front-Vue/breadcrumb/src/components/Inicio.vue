<template>
    <div>
      <b-breadcrumb :items="items"></b-breadcrumb>
      <b-link href="/#/inicio/electronicos">Electronicos</b-link>
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
        ],
      };
    },
  };
  </script>
      
  <style>
  </style>