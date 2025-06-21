<template>
  <div id="app">
    <div>
      <b-navbar toggleable="lg" type="info" variant="info" sticky>
        <b-navbar-brand href="/#/inicio"
          ><img
            src="https://placekitten.com/g/30/30"
            class="d-inline-block align-top"
            alt="Kitten"
        />Inicio</b-navbar-brand>

        <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

        <b-collapse id="nav-collapse" is-nav>
          <!-- Right aligned nav items -->
          <b-navbar-nav class="ml-auto" right>
            <b-nav-item-dropdown text="Lang" right>
              <b-dropdown-item href="#">EN</b-dropdown-item>
              <b-dropdown-item href="#">ES</b-dropdown-item>
              <b-dropdown-item href="#">RU</b-dropdown-item>
              <b-dropdown-item href="#">FA</b-dropdown-item>
            </b-nav-item-dropdown>

            <b-nav-item-dropdown right>
              <template #button-content>
                <em>User</em>
              </template>
              <b-dropdown-item href="#">Profile</b-dropdown-item>
              <b-dropdown-item href="#">Sign Out</b-dropdown-item>
            </b-nav-item-dropdown>
          </b-navbar-nav>
        </b-collapse>
      </b-navbar>
    </div>
    <div class="row mt-1">
      <div class="col-3">
        <b-nav vertical class="pt-5">
          <b-nav-item>
            <b-link :to="{ name: 'principal' }" class="text-primary">Principal</b-link>
          </b-nav-item>
          <b-nav-item >
            <b-link :to="{ name: 'formulario' }" class="text-primary">Formulario</b-link>
          </b-nav-item>
          <b-nav-item>
            <b-link :to="{ name: 'paginacion' }" class="text-primary">Paginación</b-link>
          </b-nav-item>
        </b-nav>
      </div>
      <div class="col-9">
        <div>
          <div>
            <h1>Paginación</h1>
            <div class="m-5">
              <b-breadcrumb :items="items"></b-breadcrumb>
              <div class="overflow-auto">
              <b-pagination
                v-model="currentPage"
                :total-rows="totalRows"
                :per-page="perPage"
              ></b-pagination>
              <b-table
                striped
                :items="obtenerVehiculos"
                :per-page="perPage"
                :current-page="currentPage"
                small
                :fields="fields"
                :sort-by.sync="sortBy"
                :sort-desc.sync="sortDesc"
                :filter="filtro"
                @filtered="onFiltered"
              ></b-table>
            </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

  <script>
  import vehiculoService from "../services/Vehiculos"; // Ajusta la ruta según tu estructura de archivos

  export default {
    data() {
      return {
        perPage: 5,
        currentPage: 0,
        vehiculos: [],
        filtro: null,
        sortBy: "",
        sortDesc: false,
        fields: [
          { key: "brand", label: "Marca", sortable: true },
          { key: "model", label: "Modelo", sortable: true },
          { key: "year", label: "Año de fabricación", sortable: true },
          { key: "serie", label: "Numero de serie", sortable: true },
        ],
        items: [
          {
            text: "Inicio",
            to: "/#/inicio",
          },
          {
            text: "Principal",
            to: "/principal",
          },
          {
            text: "Paginación",
            active: true,
          },
        ],
      };
    },
    mounted() {
      this.obtenerVehiculos();
    },
    methods: {
    async obtenerVehiculos(ctx) {
      console.log(ctx);
      try {
        const data = await vehiculoService.obtenerVehiculosPaginados(
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
  
  <style></style>