<template>
  <b-container class="mt-5">
    <div class="overflow-auto">
      <b-pagination
        v-model="currentPage"
        :total-rows="rows"
        :per-page="perPage"
        aria-controls="my-table"
      ></b-pagination>

      <p class="mt-3">Current Page: {{ currentPage }}</p>
    </div>
    <b-table
      id="my-table"
      :items="obtenerPersonas"
      :per-page="perPage"
      :current-page="currentPage"
      :fields="fields"
      :sort-by.sync="sortBy"
      :sort-desc.sync="sortDesc"
      label-sort-asc=""
      label-sort-desc=""
      small
      :filter="filtro"
      @filtered="onFiltered"
    >
    </b-table>
    
  </b-container>
</template>

<script>

import personaService from "../services/Persona";

export default {
  data() {
    return {
      filtro: null,
      sortBy: "name",
      sortDesc: false,
      perPage: 5,
      rows: 0,
      currentPage: 1,
      personas: [],
      fields: [
        { key: "name", label: "Nombre", sortable: true },
        { key: "firstname", label: "Apellido Paterno", sortable: true },
        { key: "lastname", label: "Apellido Materno", sortable: true },
        { key: "address", label: "Direccion", sortable: true },
        { key: "birthday", label: "Fech. Nac.", sortable: true },
        { key: "email", label: "Email", sortable: true },
      ],
    };
  },
  methods: {
    async obtenerPersonas(ctx) {
      console.log(ctx);
      try {
        const data = await personaService.obtenerPersonasPaginadas(
          parseInt(ctx.currentPage)-1,
          parseInt(ctx.perPage),
          ctx.sortBy
        );
        this.rows = data.totalElements;
        return data.content;
      } catch (error) {
        console.error(error);
      }
    },
    onFiltered(filteredItems) {
      this.currentPage = 1;
      this.rows = filteredItems.length;
    },
  },
};
</script>