<template>
    <div>
      <b-breadcrumb :items="items"></b-breadcrumb>
      Whirpool, Koblenz y Teka
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
          {
            text: "Estufas",
            href: "/#/home/cocina/estufa",
            name: "estufa",
          },
        ],
      };
    },
  };
  </script>
      
  <style>
  </style>