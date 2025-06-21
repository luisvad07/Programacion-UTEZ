<template>
    <div>
      <b-breadcrumb :items="items"></b-breadcrumb>
      Fabuloso, MrLimpio y Roma
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
          {
            text: "Detergentes",
            href: "/#/user/hogar/detergentes",
            name: "detergentes",
          },
        ],
      };
    },
  };
  </script>
      
  <style>
  </style>