<template>
  <div>
    <b-button @click="añadir()">Añadir elemento</b-button>
    <b-button @click="quitar()">Quitar elemento</b-button>
    <b-row cols=12>
      <transition-group name="fade" tag="div" class="d-flex flex-row flex-wrap">
        <b-col v-for="(item, index) in arreglo" :key="index" class="mb-2">
          <b-card
            title="Mi Tarjeta"
            img-src="https://via.placeholder.com/300"
            img-alt="Imagen de ejemplo"
            img-top
            tag="article"
            style="max-width: 20rem"
            class="mb-2"
            :key="index"
          >
            <b-card-text>
              Esta es una tarjeta de ejemplo con Vue.js y Bootstrap-Vue.
            </b-card-text>

            <b-button href="#" variant="primary">Ir a algún lugar</b-button>
          </b-card>
        </b-col>
      </transition-group>
    </b-row>
  </div>
</template>

<script>
export default {
  data() {
    return {
      arreglo: [1, 2, 3],
    };
  },
  methods: {
    añadir() {
      this.arreglo.push(1);
    },
    quitar() {
      this.arreglo.pop();
    },
  },
};
</script>

<style>
</style>