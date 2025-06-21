<template>
    <v-card
      :loading="loading"
      class="mx-auto my-4 animate__animated animate__rubberBand"
      max-width="300"
      draggable
      @dragstart="dragStart"
    >
      <template slot="progress">
        <v-progress-linear
          color="blue"
          height="10"
          indeterminate
        ></v-progress-linear>
      </template>
  
      <v-img
        height="150"
        :src="paquete.imagen"
      ></v-img>
  
      <v-card-title class="text-h6">{{ paquete.nombre }}</v-card-title>
  
      <v-card-text>
        <div class="my-2 text-caption">
          {{ paquete.recomendadoPara }}
        </div>
  
        <div class="text-caption">
          {{ paquete.descripcion }}
        </div>
  
        <div class="my-2 font-weight-black">
          ${{ paquete.numeroPedidos }}
        </div>
      </v-card-text>
  
      <v-divider class="mx-2"></v-divider>
    </v-card>
  </template>
  
  <script>
  export default {
    data: () => ({
      loading: false,
      selection: 1,
      show: false,
    }),
  
    props: {
      paquete: {
        type: Object,
      }
    },
  
    methods: {
      dragStart(event) {
        event.dataTransfer.setData("text/plain", JSON.stringify(this.paquete));
      },
    },
  }
  </script>
  