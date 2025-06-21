<template>
    <b-breadcrumb :items="items"></b-breadcrumb>
  </template>
  
  <script>
  export default {
      data() {
        return {
          items: [
            {
              text: 'Inicio',
              href: '/#/inicio',
              name:"Inicio"
            },
            {
              text: 'Home',
              href: '/#/home',
              name:'Home'
            },
            {
              text: 'user',
              href: '/#/user',
              name:'User'
            }
          ]
        }
      }
      };
  </script>
  
  <style>
  
  </style>