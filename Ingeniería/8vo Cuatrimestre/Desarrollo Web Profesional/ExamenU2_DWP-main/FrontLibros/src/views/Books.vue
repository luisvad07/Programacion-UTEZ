<template>
  <div>
    <b-navbar class="bd-nav" type="dark"   style="background-color: #72467c;">
  <b-navbar-brand href="#">
    <img src="https://steamuserimages-a.akamaihd.net/ugc/197428727428285207/DAD71B1EBCE9BFBF3AFB09A5A13B84998D7CC49F/?imw=637&imh=358&ima=fit&impolicy=Letterbox&imcolor=%23000000&letterbox=true" class="d-inline-block align-top" alt="Kitten" height="60px" width="100px">
    Libros
  </b-navbar-brand>
</b-navbar>

    <h3 class="text-center mt-3">Libros</h3>

    <b-container >
      <div v-show="showElement">
        <b-row class="d-flex justify-content-center">
          <b-col cols="12" sm="12" md="8">
            <div>
              <b-carousel
                id="carousel-1"
                v-model="slide"
                :interval="4000"
                controls
                indicators
                background="#ababab"
                img-width="1024"
                img-height="480"
                style="text-shadow: 1px 1px 2px #333"
                @sliding-start="onSlideStart"
                @sliding-end="onSlideEnd"
              >

                <div v-if="imageCarrusel.length > 0" v-for="(image, index) in imageCarrusel" :key="index">
                  <b-carousel-slide 
                    :img-src="image"
                    img-height="200"
                  ></b-carousel-slide>
                </div>

                <!-- Slide with blank fluid image to maintain slide aspect ratio -->
                <b-carousel-slide
                  v-else
                  caption="Blank Image"
                  img-blank
                  img-alt="Blank image"
                >
                </b-carousel-slide>
              </b-carousel>
            </div>
          </b-col>
        </b-row>
      </div>
      <b-row class="mt-5">
        <b-col >
          <b-row>
            <b-col cols="12" sm="12" md="4">
              <b-button style="background-color: #72467c;" pill @click="getBooksOrderByAuthorDesc"
                >Ordenar por autor
              </b-button>
              
            </b-col>
            <br>
            <b-col cols="12" sm="12" md="4">
              <b-button style="background-color: #72467c;" pill @click="getBooksOrderByDateDesc"
                >Ordenar por fecha
              </b-button>
            </b-col>
            <br>
            <b-col cols="12" sm="12" md="4">
              <b-button style="background-color: #72467c;" pill @click="getBooksHasCover"
                >Mostrar si tiene imagen
              </b-button>
            </b-col>
          </b-row>

          <div
            cols="12"
            class="align-items-stretch d-flex justify-content-center mt-5 ml-5"
            v-if="books.length != 0"
          >
            <TransitionGroup
              name="backDown"
              tag="div"
              class="d-flex flex-row flex-wrap"
            >
              <b-col
                v-for="book in books"
                :key="book.id"
                class="my-2"
                cols="12"
                sm="12"
                md="4"
              >
                <b-card
                  :title="book.name"
                  :img-src="book.cover"
                  img-alt="Image"
                  img-top
                  tag="article"
                  style="max-width: 20rem"
                  class="mb-2"
                  @dragover.prevent
                  @dragenter.prevent
                  @dragstart="startDrag($event, book)"
                  draggable="true"
                >
                  <b-card-text>
                    <!-- <p>Libro: {{ book.name }}</p> -->
                    <p>Autor: {{ book.author }}</p>
                    <p>Fecha de publicación: {{ book.atPublish }}</p>
                    <p>Estatus: {{ book.status ? "Activo" : "Inactivo" }}</p>
                  </b-card-text>
                </b-card>
              </b-col>
            </TransitionGroup>
          </div>

          <b-row
            cols="12"
            class="align-items-stretch d-flex justify-content-center mt-5 ml-5"
            v-else
          >
            <b-col cols="12" sm="12" md="12">
              <h3 class="text-center">No hay registros</h3>
            </b-col>
          </b-row>
        </b-col>
        <b-col cols="12" sm="12" md="2">
          <b-row class="mt-3 text-center">
            <b-col cols="12" sm="12" class="mb-2">
              <b-button v-b-modal.save pill style="background-color: #72467c;">
                <b-icon icon="plus" aria-hidden="true"></b-icon>
              </b-button>
            </b-col>
            <b-col cols="12" sm="12" class="mb-2">
              <b-button
              pill
                @drop="onDropUpdate($event)"
                @dragover.prevent
                @dragenter.prevent
                style="background-color: #72467c;"
              >
                <b-icon icon="pencil" aria-hidden="true"></b-icon>
              </b-button>
            </b-col>
            <b-col cols="12" sm="12" class="mb-2">
              <b-button
              pill
                @drop="onDrop($event)"
                @dragover.prevent
                @dragenter.prevent
                style="background-color: #72467c;"
              >
                <b-icon icon="trash-fill" aria-hidden="true"></b-icon>
              </b-button>
            </b-col>
          </b-row>
        </b-col>
      </b-row>
    </b-container>
    <!-- modal save-->
    <b-modal id="save" title="Registrar libro" size="lg" hide-footer>
      
        <b-col cols="12" sm="12" md="6">
          <b-form>
            <b-form-group>
              <b-form-input
                id="input-1"
                type="text"
                placeholder="Ingresa el nombre del libro"
                v-model="book.name"
                required
              />
            </b-form-group>
          </b-form>
        </b-col>
        <b-col cols="12" sm="12" md="6">
          <b-form>
            <b-form-group>
              <b-form-input
                id="input-1"
                type="text"
                placeholder="Ingresa el nombre del autor"
                v-model="book.author"
                required
              />
            </b-form-group>
          </b-form>
        </b-col>
      
      
        <b-col cols="12" sm="12" md="6">
          <b-form>
            <b-form-group>
              <b-form-datepicker
                id="example-datepicker"
                v-model="book.atPublish"
                class="mb-2"
              ></b-form-datepicker>
            </b-form-group>
          </b-form>
        </b-col>
        <b-col cols="12" sm="12" md="6">
          <b-form>
            <b-form-group>
              <b-form-file
                id="file-small"
                @change="handleFileChange"
                browse-text="Buscar"
                placeholder="Selecciona una imagen"
              ></b-form-file>
            </b-form-group>
          </b-form>
        </b-col>
     

      <b-row>
        <b-col cols="12">
          <b-form>
            <b-form-group>
              <b-img
                v-if="previewImage"
                :src="previewImage"
                fluid
                thumbnail
                alt="Image"
              ></b-img>
            </b-form-group>
          </b-form>
        </b-col>
      </b-row>

      <div class="col-12 mt-3 px-0 text-right">
        <b-button style="background-color: #72467c;" @click="saveBook"> Guardar</b-button>
      </div>
    </b-modal>

    <!-- modal update -->
    <b-modal id="update" title="Actualizar libro" size="lg" hide-footer>
      <b-row>
        <b-col cols="12" sm="12" md="6">
          <b-form>
            <b-form-group>
              <b-form-input
                id="input-1"
                type="text"
                placeholder="Ingresa el nombre del libro"
                v-model="selectedBook.name"
                required
              />
            </b-form-group>
          </b-form>
        </b-col>
        <b-col cols="12" sm="12" md="6">
          <b-form>
            <b-form-group>
              <b-form-input
                id="input-1"
                type="text"
                placeholder="Ingresa el nombre del autor"
                v-model="selectedBook.author"
                required
              />
            </b-form-group>
          </b-form>
        </b-col>
      </b-row>
      <b-row>
        <b-col cols="12" sm="12" md="6">
          <b-form>
            <b-form-group>
              <b-form-datepicker
                id="example-datepicker"
                v-model="selectedBook.atPublish"
                class="mb-2"
                locale="en-US"
              ></b-form-datepicker>
            </b-form-group>
          </b-form>
        </b-col>
        <b-col cols="12" sm="12" md="6">
          <b-form>
            <b-form-group>
              <b-form-file
                id="file-small"
                @change="convertFileToBase64Update"
                browse-text="Buscar"
                placeholder="Selecciona una imagen"
              ></b-form-file>
            </b-form-group>
          </b-form>
        </b-col>
      </b-row>

      <b-row>
        <b-col cols="12">
          <b-form>
            <b-form-group>
              <b-img
                v-if="selectedBook.cover"
                :src="selectedBook.cover"
                fluid
                thumbnail
                alt="Image"
              ></b-img>
            </b-form-group>
          </b-form>
        </b-col>
      </b-row>

      <div class="col-12 mt-3 px-0 text-right">
        <b-button style="background-color: #72467c;"  @click="update"> Actualizar</b-button>
      </div>
    </b-modal>
  </div>
</template>
<script>
import Vue from "vue";
import Axios from "axios";

export default Vue.extend({
  data() {
    return {
      imageCarrusel: [],
      previewImage: null,
      slide: 0,
      sliding: null,
      books: [],
      pageable: {
        currentPage: 1,
        sort: "id",
        direction: "asc",
        page: 0,
        size: 4,
      },
      book: {
        author: "",
        name: "",
        atPublish: "",
        cover: null,
      },
      selectedBook: {
        author: "",
        name: "",
        atPublish: "",
        cover: null,
      },
      show: true,
      showElement: true,
    };
  },
  methods: {
    startDrag(evt, book) {
      evt.dataTransfer.dropEffect = "move";
      evt.dataTransfer.effectAllowed = "move";
      evt.dataTransfer.setData("book", book.id);
      this.selectedBook = { ...book };
    },
    onDrop(evt) {
      const book = evt.dataTransfer.getData("book");
      this.changeStatus(book);
    },
    onDropUpdate(evt) {
      this.$bvModal.show("update");
    },
    async update() {
      try {
        const response = await Axios.put(
          "http://localhost:8080/api/books/update/",
          this.selectedBook
        );
        if (response.status === 200) {
          this.getBooks();
          this.$swal.fire({
            title: "Éxito",
            text: "Acción realizada",
            icon: "success",
            confirmButtonText: "Aceptar",
          });
        }
        this.$bvModal.hide("update");
      } catch (error) {
        throw error;
      }
    },
    onSlideStart(slide) {
      this.sliding = true;
    },
    onSlideEnd(slide) {
      this.sliding = false;
    },
    async getBooks() {
      try {
        this.isLoading = true;
        const response = await Axios.get(
          `http://localhost:8080/api/books/?sort=id,desc`
        );
        this.totalRows = response.data.totalElements;
        this.books = response.data.content;
      } catch (error) {
        throw error;
      } finally {
        this.isLoading = false;
      }
    },
    async saveBook() {
      try {
        const response = await Axios.post(
          "http://localhost:8080/api/books/",
          this.book
        );
        console.log(response);
        if (response.status === 200) {
          this.getBooks();
          this.book = {
            author: "",
            name: "",
            atPublish: "",
            cover: null,
          };
          this.previewImage = null;
        }
        this.$bvModal.hide("save");
      } catch (error) {
        throw error;
      }
    },

    async getBooksOrderByDateDesc() {
      try {
        this.isLoading = true;
        const response = await Axios.get(
          `http://localhost:8080/api/books/order-by-publish-date/`
        );
        this.totalRows = response.data.totalElements;
        this.books = response.data.content;
      } catch (error) {
        throw error;
      } finally {
        this.isLoading = false;
      }
    },
    async getBooksOrderByAuthorDesc() {
      try {
        this.isLoading = true;
        const response = await Axios.get(
          `http://localhost:8080/api/books/order-by-author/`
        );
        this.totalRows = response.data.totalElements;
        this.books = response.data.content;
      } catch (error) {
        throw error;
      } finally {
        this.isLoading = false;
      }
    },
    async getBooksHasCover() {
      try {
        this.isLoading = true;
        const response = await Axios.get(
          `http://localhost:8080/api/books/has-cover/`
        );
        this.totalRows = response.data.totalElements;
        this.books = response.data.content;
      } catch (error) {
        throw error;
      } finally {
        this.isLoading = false;
      }
    },
    async changeStatus(id) {
      try {
        this.$swal
          .fire({
            title: "Cuidado",
            text: "¿Seguro que desea realizar la acción?",
            icon: "question",
            confirmButtonText: "Aceptar",
            showCancelButton: true,
            cancelButtonText: "Cancelar",
          })
          .then(async (result) => {
            if (result.isConfirmed) {
              const response = await Axios.patch(
                "http://localhost:8080/api/books/change-status/",
                { id: id }
              );
              console.log("changeStatus", response);
              if (response.status === 200) {
                this.getBooks();
                this.$swal.fire({
                  title: "Éxito",
                  text: "Acción realizada",
                  icon: "success",
                  confirmButtonText: "Aceptar",
                });
              }
            }
          });
      } catch (error) {
        throw error;
      }
    },
    onScroll() {
      // Obtiene la posición actual del scroll
      const currentScrollPosition =
        window.pageYOffset || document.documentElement.scrollTop;
      if (Math.abs(currentScrollPosition) > 5 && this.books.length > 0) {
        this.showElement = false;
      } else if (Math.abs(currentScrollPosition) < 5) {
        this.showElement = true;
      }
    },

    handleFileChange(event) {
      const file = event.target.files[0];
      if (file) {
        this.previewImage = URL.createObjectURL(file);
      } else {
        this.previewImage = null;
      }
      this.convertFileToBase64(file);
    },

    convertFileToBase64(file) {
      const reader = new FileReader(); // Crea un lector de archivos

      reader.readAsDataURL(file); // Lee el archivo como base64

      reader.onload = () => {
        const base64String = reader.result; // Obtiene el resultado como cadena base64
        this.book.cover = base64String; // Muestra la cadena base64 en la consola
        // Aquí puedes realizar otras acciones con la cadena base64
      };
    },

    handleImageBooks() {
      if (this.books.length > 0) {
        const images = this.books
          .filter((book) => book.cover !== null)
          .map((book) => book.cover);
        this.imageCarrusel = images;
      }
    },

    convertFileToBase64Update(event) {
      const file = event.target.files[0];

      const reader = new FileReader(); // Crea un lector de archivos

      reader.readAsDataURL(file); // Lee el archivo como base64

      reader.onload = () => {
        const base64String = reader.result; // Obtiene el resultado como cadena base64
        this.selectedBook.cover = base64String; // Muestra la cadena base64 en la consola
        // Aquí puedes realizar otras acciones con la cadena base64
      };
    },
  },
  async mounted() {
    await this.getBooks();
    this.handleImageBooks();

    window.addEventListener("scroll", this.onScroll);
  },
  beforeDestroy() {
    window.removeEventListener("scroll", this.onScroll);
  },
  watch: {
    books() {
      this.handleImageBooks();
    },
  },
});
</script>

<style scoped>
.drop-zone {
  background-color: #eee;
  margin-bottom: 10px;
  padding: 10px;
}

.drag-el {
  background-color: #fff;
  margin-bottom: 10px;
  padding: 5px;
}
.bd-navbar {
   background-color: #563d7c;
}
</style>
