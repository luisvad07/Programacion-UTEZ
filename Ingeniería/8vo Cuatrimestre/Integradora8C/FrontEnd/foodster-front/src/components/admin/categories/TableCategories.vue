<template>
  <v-card>
    <v-tabs v-model="tab" background-color="primary" dark>
      <v-tab>Personal</v-tab>
      <v-tab>Servicios</v-tab>
    </v-tabs>

    <v-tabs-items v-model="tab">
      <v-tab-item>
        <v-card>
          <v-card-title>
            Categoría Personal
            <v-divider class="mx-4" inset vertical></v-divider>
            <v-spacer></v-spacer>
            <v-text-field
                v-model="searchPersonal"
                append-icon="mdi-magnify"
                label="Buscar"
                single-line
                hide-details
            ></v-text-field>
            <v-spacer></v-spacer>
            <v-dialog v-model="dialogPersonal" max-width="500px">
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                    color="primary"
                    dark
                    class="mb-2"
                    v-bind="attrs"
                    v-on="on"
                >Nuevo personal
                </v-btn
                >
              </template>
              <v-form ref="formAgregarCategoriaPersonal" @submit.prevent="agregarCategoriaPersonal">
                <v-card>
                  <v-card-title>
                    <span class="text-h5"
                    >Agregar nueva categoría de personal</span
                    >
                  </v-card-title>
                  <v-card-text>
                    <v-container>
                      <v-row>
                        <v-col cols="12" sm="6" md="4">
                          <v-text-field
                              v-model="nuevaCategoriaPersonal.nombre"
                              label="Nombre"
                              :rules="[v => !!v || 'El nombre es requerido', v => /^[a-zA-Z0-9\s]+$/.test(v) || 'El nombre solo puede contener letras y números']"
                          ></v-text-field>
                        </v-col>
                        <v-col cols="12" sm="6" md="4">
                          <v-select
                              v-model="nuevaCategoriaPersonal.active"
                              :items="[
                            { text: 'Activo', value: true },
                            { text: 'Inactivo', value: false },
                          ]"
                              label="Estado"
                          ></v-select>
                        </v-col>
                      </v-row>
                    </v-container>
                  </v-card-text>
                  <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn
                        color="blue darken-1"
                        text
                        @click="cerrarModalAgregarCategoriaPersonal"
                    >Cancelar
                    </v-btn
                    >
                    <v-btn
                        color="blue darken-1"
                        text
                        type="submit"
                    >Guardar
                    </v-btn
                    >
                  </v-card-actions>
                </v-card>
              </v-form>
            </v-dialog>
          </v-card-title>
          <v-data-table
              class="mx-auto"
              style="height: auto; max-height: 500px; overflow-y: auto;"
              :headers="headersPersonal"
              :items="categoriasPersonal"
              :search="searchPersonal"
              :items-per-page-options="[5, 10, 15]"
              :server-items-length="totalItems"
              :items-per-page.sync="itemsPerPage"
              :loading="loading"
              :page.sync="currentPage"
              @update:page="getCategoriasPersonales"
              @update:items-per-page="getCategoriasPersonales"
          >
            <template v-slot:item="{ item }">
              <tr>
                <td class="text-start">{{ item.nombre }}</td>
                <td class="text-start">
                  <v-chip :color="item.active ? 'green' : 'red'" outlined small>{{
                      item.active ? "Activo" : "Inactivo"
                    }}
                  </v-chip>
                </td>
                <td class="text-start">{{
                    formatDateTime(item.ultimaModificacion)
                  }}
                </td>
                <td class="text-center">
                  <v-dialog
                      v-model="dialogosEditar[item.idCategoria]"
                      max-width="500px"
                  >
                    <template v-slot:activator="{ on, attrs }">
                      <v-icon
                          color="blue"
                          v-bind="attrs"
                          v-on="on"
                          @click="openEditDialog(item.idCategoria)"
                      >mdi-pencil
                      </v-icon
                      >
                    </template>
                    <v-form ref="formEditarCategoriaPersonal" @submit.prevent="editItemPersonal(item)">
                      <v-card>
                        <v-card-title>
                          Editar categoría de personal
                        </v-card-title>
                        <v-card-text>
                          <v-container>
                            <v-row>
                              <v-col cols="12" sm="6" md="4">
                                <v-text-field
                                    v-model="item.nombre"
                                    label="Nombre"
                                    :rules="[v => !!v || 'El nombre es requerido', v => /^[a-zA-Z0-9\s]+$/.test(v) || 'El nombre solo puede contener letras y números']"
                                ></v-text-field>
                              </v-col>
                              <v-col cols="12" sm="6" md="4">
                                <v-select
                                    v-model="item.active"
                                    :items="[
                                  { text: 'Activo', value: true },
                                  { text: 'Inactivo', value: false },
                                ]"
                                    label="Estado"
                                ></v-select>
                              </v-col>
                            </v-row>
                          </v-container>
                        </v-card-text>
                        <v-card-actions>
                          <v-spacer></v-spacer>
                          <v-btn
                              color="blue darken-1"
                              text
                              @click="cancelEditItemPersonal(item)"
                          >Cerrar
                          </v-btn
                          >

                          <v-btn
                              color="blue darken-1"
                              text
                              type="submit"
                          >Guardar
                          </v-btn
                          >
                        </v-card-actions>
                      </v-card>
                    </v-form>
                  </v-dialog>
                  <v-icon
                      color="red"
                      @click="deleteItemPersonal(item.idCategoria)"
                  >mdi-delete
                  </v-icon
                  >
                </td>
              </tr>
            </template>
          </v-data-table>
        </v-card>
      </v-tab-item>
      <v-tab-item>
        <v-card>
          <v-card-title>
            Categoría Servicio
            <v-divider class="mx-4" inset vertical></v-divider>
            <v-spacer></v-spacer>
            <v-text-field
                v-model="searchServicios"
                append-icon="mdi-magnify"
                label="Buscar"
                single-line
                hide-details
            ></v-text-field>
            <v-spacer></v-spacer>
            <v-dialog v-model="dialogServicios" max-width="500px">
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                    color="primary"
                    dark
                    class="mb-2"
                    v-bind="attrs"
                    v-on="on"
                >Nuevo servicio
                </v-btn
                >
              </template>
              <v-form ref="formAgregarCategoriaServicio" @submit.prevent="agregarCategoriaServicio">
                <v-card>
                  <v-card-title>
                  <span class="text-h5"
                  >Agregar nueva categoría de servicio</span
                  >
                  </v-card-title>
                  <v-card-text>
                    <v-container>
                      <v-row>
                        <v-col cols="12" sm="6" md="4">
                          <v-text-field
                              v-model="nuevoServicio.nombre"
                              label="Nombre"
                              :rules="[v => !!v || 'El nombre es requerido', v => v.length <= 50 && v.length > 3 || 'El nombre debe tener entre 3 y 50 caracteres']"
                          ></v-text-field>
                        </v-col>
                        <v-col cols="12" sm="6" md="4">
                          <v-select
                              v-model="nuevoServicio.active"
                              :items="[
                            { text: 'Activo', value: true },
                            { text: 'Inactivo', value: false },
                          ]"
                              label="Estado"
                          ></v-select>
                        </v-col>
                      </v-row>
                    </v-container>
                  </v-card-text>
                  <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn
                        color="blue darken-1"
                        text
                        @click="cerrarModalAgregarCategoriaServicio"
                    >Cancelar
                    </v-btn
                    >
                    <v-btn
                        color="blue darken-1"
                        text
                        type="submit"
                    >Guardar
                    </v-btn
                    >
                  </v-card-actions>
                </v-card>
              </v-form>
            </v-dialog>
          </v-card-title>
          <v-data-table
              class="mx-auto"
              style="height: auto; max-height: 500px; overflow-y: auto"
              :items-per-page-options="[5, 10, 15]"
              :headers="headersServicios"
              :items="categoriasServicios"
              :server-items-length="totalItemsCategoriasServicios"
              :items-per-page.sync="itemsPerPageCategoriasServicios"
              :loading="loadingCategoriasServicios"
              :search="searchServicios"
              :page.sync="currentPageCategoriasServicios"
              @update:page="getCategoriasServicios"
              @update:items-per-page="getCategoriasServicios"

          >
            <template v-slot:item="{ item }">
              <tr>
                <td class="text-start">{{ item.nombre }}</td>
                <td class="text-start">
                  <v-chip :color="item.active ? 'green' : 'red'" outlined small>{{
                      item.active ? "Activo" : "Inactivo"
                    }}
                  </v-chip>
                </td>
                <td class="text-start">
                  {{ formatDateTime(item.ultimaModificacion) }}
                </td>
                <td class="text-center">
                  <v-dialog
                      v-model="dialogosEditarServicio[item.idCategoria]"
                      max-width="500px"
                  >
                    <template v-slot:activator="{ on, attrs }">
                      <v-icon
                          color="blue"
                          v-bind="attrs"
                          v-on="on"
                          @click="openEditServicioDialog(item.idCategoria)"
                      >mdi-pencil
                      </v-icon
                      >
                    </template>
                    <v-form ref="formEditarCategoriaServicio" @submit.prevent="editItemServicio(item)">
                      <v-card>
                        <v-card-title>
                          Editar categoría de servicio
                        </v-card-title>
                        <v-card-text>
                          <v-container>
                            <v-row>
                              <v-col cols="12" sm="6" md="4">
                                <v-text-field
                                    v-model="item.nombre"
                                    label="Nombre"
                                    :rules="[v => !!v || 'El nombre es requerido', v => v.length <= 50 && v.length > 3  || 'El nombre debe tener entre 3 y 50 caracteres']"
                                ></v-text-field>
                              </v-col>
                              <v-col cols="12" sm="6" md="4">
                                <v-select
                                    v-model="item.active"
                                    :items="[
                                    { text: 'Activo', value: true },
                                    { text: 'Inactivo', value: false },
                                  ]"
                                    label="Estado"
                                ></v-select>
                              </v-col>
                            </v-row>
                          </v-container>
                        </v-card-text>
                        <v-card-actions>
                          <v-spacer></v-spacer>
                          <v-btn
                              color="blue darken-1"
                              text
                              @click="cancelEditItemServicio(item)"
                          >Cerrar
                          </v-btn
                          >

                          <v-btn
                              color="blue darken-1"
                              text
                              type="submit"
                          >Guardar
                          </v-btn
                          >
                        </v-card-actions>
                      </v-card>
                    </v-form>
                  </v-dialog>
                  <v-icon
                      color="red"
                      @click="deleteItemServicio(item.idCategoria)"
                  >mdi-delete
                  </v-icon
                  >
                </td>
              </tr>
            </template>
          </v-data-table>
        </v-card>
      </v-tab-item>
    </v-tabs-items>
  </v-card>
</template>

<script>
import {
  actualizarCategoriaPersonal,
  crearCategoriaPersonal,
  eliminarCategoriaPersonal,
  actualizarCategoriaServicio,
  crearCategoriaServicio,
  eliminarCategoriaServicio, getAllCategoriasServiciosPaginado, getAllCategoriasPersonalesPaginado,
} from "../../../services/CategoryServices.js";
import swalService from "@/services/SwalService";
import moment from "moment";

export default {
  data() {
    return {
      tab: null,
      searchPersonal: "",
      dialogPersonal: false,
      dialogServicios: false,
      dialogosEditar: {},
      dialogosEditarServicio: {},
      nuevaCategoriaPersonal: {
        nombre: "",
        active: true, // Por defecto, nueva categoría activa
      },
      headersPersonal: [
        {text: "Nombre", align: "start", sortable: false, value: "nombre"},
        {text: "Estado", align: "start", sortable: false, value: "active"},
        {
          text: "Última Modificación",
          align: "start",
          sortable: false,
          value: "ultimaModificacion",
        },
        {
          text: "Acciones",
          align: "center",
          sortable: false,
          value: "acciones",
        },
      ],
      categoriasPersonal: [],
      loading: false,
      currentPage: 1,
      totalItems: 0,
      totalPages: 0,
      itemsPerPage: 10,


      searchServicios: "",
      nuevoServicio: {
        nombre: "",
        active: true, // Por defecto, nuevo servicio activo
      },
      headersServicios: [
        {text: "Nombre", align: "start", sortable: false, value: "nombre"},
        {text: "Estado", align: "start", sortable: false, value: "active"},
        {
          text: "Última Modificación",
          align: "start",
          sortable: false,
          value: "ultimaModificacion",
        },
        {
          text: "Acciones",
          align: "center",
          sortable: false,
          value: "acciones",
        },
      ],
      categoriasServicios: [],
      loadingCategoriasServicios: false,
      currentPageCategoriasServicios: 1,
      totalItemsCategoriasServicios: 0,
      totalPagesCategoriasServicios: 0,
      itemsPerPageCategoriasServicios: 10,
    };
  },
  methods: {

    formatDateTime(dateTimeString) {
      //mexico city time
      return moment(dateTimeString).format("YYYY-MM-DD HH:mm");
    },

    async getCategoriasPersonales() {
      try {
        this.loading = true;
        const response = await getAllCategoriasPersonalesPaginado(this.currentPage - 1, this.itemsPerPage);
        if (response) {
          this.totalPages = response.totalPages;
          this.totalItems = response.totalElements;
          this.categoriasPersonal = response.content;
          this.loading = false;
        } else {
          this.totalPages = 0;
          this.categoriasPersonal = [];
          this.currentPage = 1;
          this.totalItems = 0;
          this.loading = false;
        }

      } catch (error) {
        console.error(error);
        this.loading = false;
      }
    },
    async getCategoriasServicios() {
      try {
        this.loadingCategoriasServicios = true;
        const response = await getAllCategoriasServiciosPaginado(this.currentPageCategoriasServicios - 1, this.itemsPerPageCategoriasServicios);
        if (response) {
          this.totalPagesCategoriasServicios = response.totalPages;
          this.totalItemsCategoriasServicios = response.totalElements;
          this.categoriasServicios = response.content;
          this.loadingCategoriasServicios = false;
        } else {
          this.totalPagesCategoriasServicios = 0;
          this.categoriasServicios = [];
          this.currentPageCategoriasServicios = 1;
          this.totalItemsCategoriasServicios = 0;
          this.loadingCategoriasServicios = false;
        }
      } catch (error) {
        console.error(error);
        this.loading = false;
      }
    },

    cancelEditItemPersonal(item) {
      // Close the edit dialog without saving
      this.dialogosEditar[item.idCategoria] = false;
    },
    cancelEditItemServicio(item) {
      // Close the edit dialog without saving
      this.dialogosEditarServicio[item.idCategoria] = false;
    },
    async editItemPersonal(item) {
      console.log("Editando categoría personal:", item);
      try {
        // Set the edit dialog state for this row to true
        this.$set(this.dialogosEditar, item.idCategoria, true);

        // Find the index of the object in categoriasPersonal and update it
        const index = this.categoriasPersonal.findIndex(
            (category) => category.idCategoria === item.idCategoria
        );
        if (index !== -1) {
          // Update the object in the categoriasPersonal list
          this.categoriasPersonal[index].nombre = item.nombre;
          this.categoriasPersonal[index].active = item.active;

          // Call the service to update the category
          await actualizarCategoriaPersonal(item);
        }
        // Close the edit dialog after saving
        this.dialogosEditar[item.idCategoria] = false;
      } catch (error) {
        console.error("Error al editar categoría personal:", error);
      }
    },
    async editItemServicio(item) {
      try {
        if (this.$refs.formEditarCategoriaServicio.validate()) {
          // Set the edit dialog state for this row to true
          this.$set(this.dialogosEditarServicio, item.idCategoria, true);

          // Find the index of the object in categoriasServicios and update it
          const index = this.categoriasServicios.findIndex(
              (category) => category.idCategoria === item.idCategoria
          );
          if (index !== -1) {
            // Update the object in the categoriasServicios list
            this.categoriasServicios[index].nombre = item.nombre;
            this.categoriasServicios[index].active = item.active;

            // Call the service to update the category
            await actualizarCategoriaServicio(item);
            this.dialogosEditarServicio[item.idCategoria] = false;
          }
        }
        // Close the edit dialog after saving

      } catch (error) {
        console.error("Error al editar categoría de servicio:", error);
      }
    },

    async deleteItemPersonal(item) {
      try {
        let proceder = await swalService.confirmationWarning(
            "¿Estás seguro de que deseas eliminar esta categoría de personal?"
        );
        if (proceder) {
          // Call the service to delete the category
          await eliminarCategoriaPersonal(item);
          await this.getCategoriasPersonales();
        }
      } catch (error) {
        console.error("Error al eliminar categoría personal:", error);
      }
    },
    async deleteItemServicio(item) {
      try {
        let proceder = await swalService.confirmationWarning(
            "¿Estás seguro de que deseas eliminar esta categoría de servicio?"
        );
        if (proceder) {
          // Call the service to delete the category
          await eliminarCategoriaServicio(item);
          await this.getCategoriasServicios();
        }
      } catch (error) {
        console.error("Error al eliminar categoría de servicio:", error);
      }
    },
    cerrarModalAgregarCategoriaPersonal() {
      this.dialogPersonal = false;
      // Limpiar el formulario al cerrar el modal
      this.nuevaCategoriaPersonal = {
        nombre: "",
        active: true, // Por defecto, nueva categoría activa
      };
    },
    cerrarModalAgregarCategoriaServicio() {
      this.dialogServicios = false;
      // Limpiar el formulario al cerrar el modal
      this.nuevoServicio = {
        nombre: "",
        active: true, // Por defecto, nuevo servicio activo
      };
    },
    async agregarCategoriaPersonal() {
      try {
        if (this.$refs.formAgregarCategoriaPersonal.validate()) {
          const nuevaCategoria = await crearCategoriaPersonal(
              this.nuevaCategoriaPersonal
          );
          if (nuevaCategoria) {
            await this.getCategoriasPersonales();
            await this.getCategoriasServicios();

            // Cerrar el modal
            this.dialogPersonal = false;

            // Limpiar el formulario al cerrar el modal
            this.nuevaCategoriaPersonal = {
              nombre: "",
              active: true, // Por defecto, nueva categoría activa
            };
          }
        }
      } catch (error) {
        console.error("Error al agregar categoría personal:", error);
      }
    },
    async agregarCategoriaServicio() {
      try {
        if (this.$refs.formAgregarCategoriaServicio.validate()) {
          const nuevoServicio = await crearCategoriaServicio(this.nuevoServicio);
          if (nuevoServicio) {
            await this.getCategoriasPersonales();
            await this.getCategoriasServicios();
            this.dialogServicios = false;
            this.nuevoServicio = {
              nombre: "",
              active: true, // Por defecto, nuevo servicio activo
            };
          }
        }
      } catch (error) {
        console.error("Error al agregar categoría de servicio:", error);
      }
    },
    openEditDialog(idCategoria) {
      this.$set(this.dialogosEditar, idCategoria, true);
    },
    openEditServicioDialog(idCategoria) {
      this.$set(this.dialogosEditarServicio, idCategoria, true);
    },
    cerrarModalAgregarServicio() {
      this.dialogServicios = false;
      // Limpiar el formulario al cerrar el modal
      this.nuevoServicio = {
        nombre: "",
        active: true, // Por defecto, nuevo servicio activo
      };
    },
  },
  mounted() {
    this.getCategoriasPersonales();
    this.getCategoriasServicios();
  },
  watch: {
    searchServicios: async function (val) {
      if (val) {
        this.categoriasServicios = this.categoriasServicios.filter((item) => {
          return item.nombre.toLowerCase().includes(val.toLowerCase());
        });
      } else {
        await this.getCategoriasServicios();

      }
    },
    searchPersonal: async function (val) {
      if (val) {
        this.categoriasPersonal = this.categoriasPersonal.filter((item) => {
          return item.nombre.toLowerCase().includes(val.toLowerCase());
        });
      } else {
        await this.getCategoriasPersonales();
      }
    },
  },
};
</script>