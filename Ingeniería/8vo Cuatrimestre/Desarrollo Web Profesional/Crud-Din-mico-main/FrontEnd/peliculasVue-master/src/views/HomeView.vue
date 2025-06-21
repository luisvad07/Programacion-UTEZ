<template>
  <div>
    <h1 v-show="showElement">Peliculas</h1>
    <div style="display: grid; place-items: center; gap: 10px">
      <div style="display: flex; flex-direction: row; gap: 10px">
        <div
          style="
            width: 30%;
            margin-left: 10px;
            border: solid white 2px;
            border-radius: 0.5rem;
            display: grid;
            place-items: center;
          "
        >
          <p>Buscar por:</p>
          <b-form-input
            v-model="buscarparam"
            placeholder="Buscar"
            col="3"
          ></b-form-input>
        </div>
        <div
          style="
            width: 30%;
            margin-left: 10px;
            border: solid white 2px;
            border-radius: 0.5rem;
            display: grid;
            place-items: center;
          "
        >
          <p>Fecha Inicio</p>
          <b-form-input v-model="fechainicio" type="date"></b-form-input>
        </div>
        <div
          style="
            width: 30%;
            margin-left: 10px;
            border: solid white 2px;
            border-radius: 0.5rem;
            display: grid;
            place-tems: center;
          "
        >
          <p>Fecha Fin</p>
          <b-form-input v-model="fechafin" type="date"></b-form-input>
        </div>
      </div>

      <div style="display: flex; flex-direction: row; gap: 10px">
        <b-button v-b-modal.modal-p>Agregar Pelicula</b-button>

        <b-button @click="buscarPeliculasPorDirector"
          >Buscar por director</b-button
        >
        <b-button @click="buscarPeliculasPorNombre">Buscar por titulo</b-button>
        <b-button @click="buscarPeliculasPorFechaPublicacion"
          >Buscar por rango fechas</b-button
        >
        <b-button @click="buscarPeliculasPorGenero"
          >Buscar por categoria</b-button
        >
        <b-button @click="buscarPeliculasPorFechaPublicacionDesc"
          >Buscar por fecha descendente</b-button
        >
      </div>

      <b-modal id="modal-1" title="BootstrapVue">
        <p class="my-4">Hello from modal!</p>
      </b-modal>
      <b-modal id="modal-m" title="Modificar Película">
        <div>
          <b-form @submit="onSubmit2" @reset="onReset" v-if="show">
            <!-- Campo oculto para almacenar el ID de la película -->
            <input type="hidden" v-model="form.id_movie" value="peliculasel" />

            <b-form-group
              id="input-group-2"
              label="Nombre:"
              label-for="input-2"
            >
              <b-form-input
                id="input-2"
                v-model="form.nombre"
                placeholder="Ingrese el nombre de la película"
                required
              ></b-form-input>
            </b-form-group>

            <b-form-group
              id="input-group-3"
              label="Director:"
              label-for="input-3"
            >
              <b-form-input
                id="input-3"
                v-model="form.directorMovie"
                placeholder="Ingrese el nombre del director"
                required
              ></b-form-input>
            </b-form-group>

            <b-form-group
              id="input-group-3"
              label="Duración:"
              label-for="input-3"
            >
              <b-form-input
                id="input-3"
                v-model="form.duration"
                placeholder="Ingrese la duración de la película"
                required
              ></b-form-input>
            </b-form-group>

            <b-form-group
              id="input-group-4"
              label="Género:"
              label-for="input-4"
              class="mb-3"
            >
              <b-form-select
                id="input-4"
                v-model="form.genero"
                :options="generos"
                required
              ></b-form-select>
            </b-form-group>

            <b-button type="submit" variant="primary">Modificar</b-button>
            <b-button type="reset" variant="danger">Reset</b-button>
          </b-form>
        </div>
      </b-modal>

      <b-modal id="modal-p" title="Agregar Película">
        <div>
          <b-form @submit="onSubmit" @reset="onReset" v-if="show">
            <b-form-group
              id="input-group-2"
              label="Nombre:"
              label-for="input-2"
            >
              <b-form-input
                id="input-2"
                v-model="form.nombre"
                placeholder="Ingrese el nombre de la película"
                required
              ></b-form-input>
            </b-form-group>

            <b-form-group
              id="input-group-3"
              label="Director:"
              label-for="input-3"
            >
              <b-form-input
                id="input-3"
                v-model="form.directorMovie"
                placeholder="Ingrese el nombre del director"
                required
              ></b-form-input>
            </b-form-group>

            <b-form-group
              id="input-group-3"
              label="Duración:"
              label-for="input-3"
            >
              <b-form-input
                id="input-3"
                v-model="form.duration"
                placeholder="Ingrese la duración de la película"
                required
              ></b-form-input>
            </b-form-group>

            <b-form-group
              id="input-group-4"
              label="Género:"
              label-for="input-4"
              class="mb-3"
            >
              <b-form-select
                id="input-4"
                v-model="form.genero"
                :options="generos"
                required
              ></b-form-select>
            </b-form-group>

            <b-button type="submit" variant="primary">Agregar</b-button>
            <b-button type="reset" variant="danger">Reset</b-button>
          </b-form>
        </div>
      </b-modal>
    </div>
    <b-card-group id="mainPeliculas" deck>
      <b-card
        v-for="(pelicula, index) in peliculas"
        :key="index"
        :title="pelicula.nombre"
        header-tag="header"
        footer-tag="footer"
        draggable="true"
        @dragstart="onDragStart(pelicula, index)"
        @dragover.prevent
        @drop="onDrop(index)"
        @click="setPelicula(pelicula.id_movie)"
      >
        <b-card-text>Director: {{ pelicula.directorMovie }}</b-card-text>
        <b-card-text>Duración: {{ pelicula.duration }}</b-card-text>
        <b-card-text
          >Fecha:
          {{
            pelicula.fechaPublicacion
              ? pelicula.fechaPublicacion.slice(0, 10)
              : ""
          }}</b-card-text
        >
        <b-card-text>Género: {{ pelicula.genero.name }}</b-card-text>
        <template #header>
          <b-container align-h="center">
            <b-row>
              <b-col>
                <b-button href="#" variant="warning" v-b-modal.modal-m
                  >Editar</b-button
                >
                <b-button
                  href="#"
                  variant="danger"
                  @click="eliminarPelicula(pelicula.id_movie)"
                >
                  Eliminar
                </b-button>
              </b-col>
            </b-row>
          </b-container>
        </template>
      </b-card>
    </b-card-group>
  </div>
</template>

<script>
import servicioPeliculas from "../services/Pelicula";

export default {
  data() {
    return {
      form: {
        nombre: "",
        directorMovie: "",
        duration: "",
        genero: null,
      },
      buscarparam: "",
      fechainicio:"",
      fechafin:"",
      peliculas: [],
      generos: [],
      generos_id: [],
      peliculasel: null,
      show: true,
      showElement: true,
      lastScrollPosition: 0,
      draggedIndex: null,
    };
  },
  mounted() {
    window.addEventListener("scroll", this.onScroll);
  },
  beforeDestroy() {
    window.removeEventListener("scroll", this.onScroll);
  },
  methods: {
    onScroll() {
      const currentScrollPosition = window.pageYOffset || document.documentElement.scrollTop;
        
      if (Math.abs(currentScrollPosition - this.lastScrollPosition) < 60) {
        return;
      }

      this.showElement = currentScrollPosition < this.lastScrollPosition;

      this.lastScrollPosition = currentScrollPosition;
      },
    onDragStart(pelicula, index) {
      event.dataTransfer.setData('text/plain', index);
      event.dataTransfer.effectAllowed = 'move';
      this.draggedItem = { index, pelicula };
    },
    onDrop(dropIndex) {
      const draggedIndex = this.draggedItem.index;
      const draggedPelicula = this.draggedItem.pelicula;
      this.peliculas.splice(draggedIndex, 1);
      this.peliculas.splice(dropIndex, 0, draggedPelicula);
    },
    setPelicula(ide) {
      this.peliculasel = ide;
    },
    async obtenerPeliculas() {
      try {
        this.peliculas = await servicioPeliculas.obtenerPeliculas();
        console.log(this.peliculas);
      } catch (error) {
        console.error("Error al obtener películas:", error);
      }
    },
    async obtenerGeneros() {
      try {
        const generos = await servicioPeliculas.obtenerGeneros();
        this.generos = generos.map((item) => item.name);
        this.generos_id = generos;
        console.log(this.generos);
      } catch (error) {
        console.error("Error al obtener géneros:", error);
      }
    },
    async buscarPeliculasPorNombre() {
      try {
        const response = await servicioPeliculas.buscarPeliculaPorNombre(
          this.buscarparam
        );
        this.peliculas = response;
        console.log(this.peliculas);
      } catch (error) {
        console.error("Error al buscar películas por nombre:", error);
      }
    },
    async buscarPeliculasPorDirector() {
      try {
        const response = await servicioPeliculas.buscarPeliculaPorDirector(
          this.buscarparam
        );
        this.peliculas = response;
        console.log(this.peliculas);
      } catch (error) {
        console.error("Error al buscar películas por director:", error);
      }
    },
    async buscarPeliculasPorFechaPublicacion() {
      try {
        const response =
          await servicioPeliculas.buscarPeliculaPorFechaPublicacion(
            this.fechainicio,
            this.fechafin
          );
        this.peliculas = response;
        console.log(this.peliculas);
      } catch (error) {
        console.error(
          "Error al buscar películas por rango de fechas de publicación:",
          error
        );
      }
    },
    async buscarPeliculasPorGenero() {
      try {
        const response = await servicioPeliculas.buscarPeliculaPorGenero(
          this.buscarparam
        );
        this.peliculas = response;
        console.log(this.peliculas);
      } catch (error) {
        console.error("Error al buscar películas por género:", error);
      }
    },
    async buscarPeliculasPorFechaPublicacionDesc() {
      try {
        const response =
          await servicioPeliculas.buscarPeliculaPorFechaPublicacionDesc();
        this.peliculas = response;
        console.log(this.peliculas);
      } catch (error) {
        console.error(
          "Error al buscar películas por fecha de publicación descendente:",
          error
        );
      }
    },
    async agregarPelicula() {
      try {
        const fidegenero = this.generos_id.find(
          (genero) => genero.name === this.form.genero
        );
        const id_gen = fidegenero.id_gen;
        await servicioPeliculas.agregarPelicula({
          ...this.form,
          genero: { id_gen }, fechaPublicacion: new Date().toISOString().slice(0, 10)
        });
        alert("Película agregada exitosamente");
        // Puedes recargar la lista de películas después de agregar una nueva si lo deseas
        this.obtenerPeliculas();
      } catch (error) {
        console.error("Error al agregar película:", error);
      }
    },
    async eliminarPelicula(idPelicula) {
      try {
        await servicioPeliculas.eliminarPelicula(idPelicula);
        alert("Película eliminada exitosamente");
        // Actualizar la lista de películas después de eliminar una
        this.obtenerPeliculas();
      } catch (error) {
        console.error("Error al eliminar película:", error);
      }
    },
    async actualizarPelicula(idPelicula) {
      try {
        const id_movie = idPelicula;
        const fidegenero = this.generos_id.find(
          (genero) => genero.name === this.form.genero
        );
        const id_gen = fidegenero.id_gen;
        console.log({
          ...this.form,
          genero: { id_gen }, fechaPublicacion: new Date().toISOString().slice(0, 10)
        });
        await servicioPeliculas.actualizarPelicula({
          ...this.form,
          genero: { id_gen },
          id_movie,
        });
        alert("Película actualizada exitosamente");
        // Actualizar la lista de películas después de actualizar una
        this.obtenerPeliculas();
      } catch (error) {
        console.error("Error al actualizar película:", error);
      }
    },
    onSubmit(event) {
      event.preventDefault();
      this.agregarPelicula();
    },
    onSubmit2(event) {
      event.preventDefault();
      this.actualizarPelicula(this.peliculasel);
    },
    onReset(event) {
      event.preventDefault();
      // Resetear los valores del formulario
      this.form.directorMovie = "";
      this.form.nombre = "";
      this.form.duration = "";
      this.form.fechaPublicacion = "";
      this.form.genero = null;
      // Truco para resetear/limpiar el estado de validación del formulario del navegador
      this.show = false;
      this.$nextTick(() => {
        this.show = true;
      });
    },
  },
  created() {
    // Llamar a las funciones de obtener películas y obtener géneros al crear el componente
    this.obtenerPeliculas();
    this.obtenerGeneros();
  },
};
</script>

<style>
</style>