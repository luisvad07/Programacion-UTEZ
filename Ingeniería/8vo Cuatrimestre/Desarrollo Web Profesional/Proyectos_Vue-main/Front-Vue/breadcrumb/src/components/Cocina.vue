<template>
    <div>
      <b-breadcrumb :items="items"></b-breadcrumb>
      <b-link href="/#/home/cocina/estufa">Estufas</b-link>
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
          {
            text: "Cocina",
            href: "/#/home/cocina",
            name: "cocina",
          },
        ],
      };
    },
  };
  </script>
      
  <style>
  </style>