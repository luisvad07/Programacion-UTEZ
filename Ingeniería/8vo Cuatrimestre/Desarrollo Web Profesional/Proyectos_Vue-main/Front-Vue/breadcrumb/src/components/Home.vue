<template>
    <div>
      <b-breadcrumb :items="items"></b-breadcrumb>
      <b-link href="/#/home/cocina">Cocina</b-link>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        items: [
          {
            text: "Tienda de Electrodomesticos",
            href: "/#/home",
            name: "Home",
          },
        ],
      };
    },
  };
  </script>
      
  <style>
  </style>