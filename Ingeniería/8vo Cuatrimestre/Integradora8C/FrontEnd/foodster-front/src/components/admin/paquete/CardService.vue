<template>
  <v-card
      :loading="loading"
      class="mx-auto my-4 animate__animated animate__rubberBand"
      max-width="300"
  >
    <template slot="progress">
      <v-progress-linear
          color="blue"
          height="10"
          indeterminate
      ></v-progress-linear>
    </template>

    <v-img height="150" :src="servicio.imagen"></v-img>

    <v-card-title class="text-h6">{{ servicio.nombre }}</v-card-title>

    <v-card-text>
      <div class="my-2 text-caption">
        {{ servicio.categoria.nombre }}
      </div>

      <div class="text-caption">
        {{ servicio.descripcion }}
      </div>

      <div class="my-2 font-weight-black">
        <template v-if="servicio.precioDescuento > 0">
          <s>${{ servicio.precio.toFixed(2) }}</s>
          ${{ servicio.precioDescuento.toFixed(2) }}
        </template>
        <template v-else>
          ${{ servicio.precio.toFixed(2) }}
        </template>
      </div>
    </v-card-text>
  </v-card>
</template>

<script>

export default {
  data: () => ({
    loading: false,
    selection: 1,
    show: false,
    inCart: false,
  }),

  props: {
    servicio: {
      type: Object,
    },
  },
};
</script>
