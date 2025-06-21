<template>
  <div>
    <!-- Contenedor flexible para las tarjetas -->
    <div class="card-container row">
      <!-- Colocar cada tarjeta dentro de un contenedor flexible -->
      <div class="card-item col-lg-3 col-md-4 col-sm-6 col-12 mb-4" v-for="(item, index) in cards" :key="index">
        <Cards :expanded="index === expandedIndex" @expand="expandCard(index)" />
      </div>
    </div>
  </div>
</template>

<script>
import Cards from './Cards.vue';

export default {
  components: {
    Cards
  },
  data() {
    return {
      cards: Array.from({ length: 8 }, (_, index) => index),
      expandedIndex: null
    };
  },
  methods: {
    expandCard(index) {
      this.expandedIndex = this.expandedIndex === index ? null : index;
    }
  }
}
</script>

<style scoped>
.card-container {
  margin-top: 20px;
}

.card-item {
  flex: 1 0 auto;
}
</style>
