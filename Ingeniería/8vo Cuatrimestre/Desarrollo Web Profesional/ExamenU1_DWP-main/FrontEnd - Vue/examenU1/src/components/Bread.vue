<template>
    <b-breadcrumb :items="items"></b-breadcrumb>
</template>
  
  <script>
  export default {
      data() {
        return {
          items: [
            {
              text: 'Principal',
              href: '/#/principal',
              name:"principal"
            },
            {
              text: 'Formulario',
              href: '/#/formulario',
              name:'formulario'
            },
            {
              text: 'Paginación',
              href: '/#/paginacion',
              name:'paginacion'
            }
          ]
        }
      }
      };
  </script>
  
  <style>
  
  </style>