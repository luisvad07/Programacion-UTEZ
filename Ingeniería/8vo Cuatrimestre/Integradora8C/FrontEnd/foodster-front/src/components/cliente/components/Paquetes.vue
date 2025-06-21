<template>
  <div>
    <div class="card-container row">
      <v-progress-linear
          color="blue darken-2"
          height="5"
          indeterminate
          v-if="loading"
      ></v-progress-linear>
      <div class="card-item col-lg-3 col-md-4 col-sm-6 col-12 mb-4" v-for="(item, index) in paquetes" :key="index">
        <CardPaquete :paquete="item"/>
      </div>
    </div>
    <v-pagination
        v-model="currentPage"
        :length="totalPages"
        :total-visible="5"
        @input="fetchPaquetes"
        class="my-4 w-11/12 mx-auto"
    ></v-pagination>
  </div>
</template>

<script>
import CardService from "@/components/cliente/components/CardService.vue";
import CardPaquete from "@/components/cliente/components/CardPaquete.vue";
import {getAllPaquetesPaginado} from "@/services/PaquetesServices";

export default {
  components: {
    CardPaquete,
    CardService
  },
  data() {
    return {
      paquetes: [],
      loading: false,
      currentPage: 1,
      totalPages: 0,
      itemsPerPage: 10,
    };
  },
  methods: {
    async fetchPaquetes() {
      this.loading = true;
      const response = await getAllPaquetesPaginado(this.currentPage - 1, this.itemsPerPage);
      if (response) {
        this.totalPages = response.totalPages;
        this.paquetes = response.content;
      } else {
        this.totalPages = 0;
        this.paquetes = [];
        this.currentPage = 1;
      }
      this.loading = false;
    },
  },
  mounted() {
    this.fetchPaquetes();
  },
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
