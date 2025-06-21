<script setup>

</script>

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
            <h1>Formulario</h1>
            <div class="m-5">
              <b-breadcrumb :items="items"></b-breadcrumb>
              <b-form @submit="onSubmit" @reset="onReset" v-if="show">
                <b-row>
                  <b-col
                    ><b-form-group id="marcaCarro" label="Marca">
                      <b-form-input
                        id="marcaCarro"
                        v-model="form.marcaCarro"
                        type="text"
                        required
                        pattern="^[a-zA-Z0-9_]*$"
                      ></b-form-input> </b-form-group
                  ></b-col>
                  <b-col>
                    <b-form-group id="modeloCarro" label="Modelo">
                      <b-form-input
                        id="modeloCarro"
                        v-model="form.modeloCarro"
                        type="text"
                        pattern="[A-Za-z0-9]+"
                        required
                      ></b-form-input> </b-form-group
                  ></b-col>
                  <b-col>
                    <b-form-group id="yearCarro" label="Año">
                      <b-form-input
                        id="yearCarro"
                        v-model="form.yearCarro"
                        type="number"
                        required
                      ></b-form-input> </b-form-group
                  ></b-col>
                </b-row>
                <b-row>
                  <b-col
                    ><b-form-group id="numeroSerie" label=" Numero de serie">
                      <b-form-input
                        id="numeroSerie"
                        v-model="form.numeroSerie"
                        type="text"
                        required
                      ></b-form-input> </b-form-group
                  ></b-col>
                </b-row>
                <div class="mt-2">
                  <b-button type="submit" variant="success" class="me-2">
                    Enviar
                  </b-button>
                  <b-button type="reset" variant="danger" class="me-2">
                    Limpiar
                  </b-button>
                </div>
              </b-form>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      show: true,
      form: {
        marcaCarro: "",
        modeloCarro: "",
        yearCarro: "",
        numeroSerie: "",
      },
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
          text: "Formulario",
          active: true,
        },
      ],
    };
  },

  methods: {
    onSubmit(event) {
      event.preventDefault();
      const yearCarroActual = new Date().getFullYear();
      const alfanumericoRegex = /^[a-z0-9]+$/i;

      if (!alfanumericoRegex.test(this.form.marcaCarro)) {
        alert("La marca debe de contener letras y números");
        return;
      }

      if (!alfanumericoRegex.test(this.form.modeloCarro)) {
        alert("El modeloCarro debe de contener letras y números");
        return;
      }

      if (this.form.yearCarro > yearCarroActual) {
        alert("El año no puede ser superior que el actual ");
        return;
      }

      const serieRegex = /^[A-Za-z]{4}\d{3}-\d{2}[A-ZamodeloCarro-z]{2}$/;
      if (!serieRegex.test(this.form.numeroSerie)) {
        alert("El número de serie no cumple con el formato requerido ejem: ABCD123-45EF");
        return;
      }

      alert("Vehículo creado con éxito");


      console.log('Datos:', {
        marcaCarro: this.marcaCarro,
                modeloCarro: this.modeloCarro,
                yearCarro: this.yearCarro,
                numeroSerie: this.numeroSerie,
          });
    },
    onReset(event) {
      event.preventDefault();
      this.form.marcaCarro = "";
      this.form.modeloCarro = "";
      this.form.yearCarro = "";
      this.form.numeroSerie = "";
    },
  },
};
</script>

<style></style>