<template>
    <div>
      <b-breadcrumb :items="items"></b-breadcrumb>
      <b-link href="/#/user/hogar/detergentes">Detergentes</b-link>
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
            name: "user",
          },
          {
            text: "Productos de Limpieza",
            href: "/#/user/hogar",
            name: "hogar",
          },
        ],
      };
    },
  };
  </script>
      
  <style>
  </style>