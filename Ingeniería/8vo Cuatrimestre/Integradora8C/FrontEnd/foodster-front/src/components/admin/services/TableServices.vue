<template>
  <v-card>
    <v-tabs v-model="tab" background-color="primary" dark>
      <v-tab>Servicios</v-tab>
      <v-tab>Paquetes</v-tab>
    </v-tabs>

    <v-tabs-items v-model="tab">
      <v-tab-item>
        <v-card>
          <v-card-title>
            Servicios
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
                </v-btn>
              </template>
              <v-form
                @submit.prevent="agregarServicio"
                ref="formAgregarServicio"
                v-model="validAgregarServicio"
              >
                <v-card>
                  <v-card-title>
                    <span class="text-h5">Agregar nuevo servicio</span>
                  </v-card-title>
                  <v-card-text>
                    <v-container>
                      <v-row>
                        <v-col cols="12" sm="6" md="6">
                          <v-text-field
                            v-model="nuevoServicio.nombre"
                            label="Nombre"
                            :rules="[
                              (v) => !!v || 'El nombre es requerido',
                              (v) =>
                                (v && v.length >= 3 && v.length <= 50) ||
                                'El nombre debe tener entre 3 y 50 caracteres',
                            ]"
                          ></v-text-field>
                          <v-text-field
                            v-model="nuevoServicio.descripcion"
                            label="Descripcion"
                            :rules="[
                              (v) => !!v || 'La descripción es requerida',
                              (v) =>
                                (v && v.length >= 3 && v.length <= 255) ||
                                'La descripción debe tener entre 3 y 255 caracteres',
                            ]"
                          ></v-text-field>
                          <v-text-field
                            v-model="nuevoServicio.precio"
                            label="Precio"
                            type="number"
                            step="0.01"
                            :rules="[
                              (v) => !!v || 'El precio no puede ser nulo',
                              (v) => v > 0 || 'El precio debe ser positivo',
                              (v) =>
                                !isNaN(v) || 'El precio debe ser un número',
                            ]"
                          ></v-text-field>
                          <v-text-field
                            v-model="nuevoServicio.precioDescuento"
                            label="Precio Descuento"
                            type="number"
                            step="0.01"
                            :rules="[
                              (v) => v > -1 || 'El precio debe ser positivo',
                              (v) =>
                                !isNaN(v) || 'El precio debe ser un número',
                            ]"
                          ></v-text-field>

                          <input
                            type="file"
                            @change="onFileChange"
                            accept="image/*"
                          />
                        </v-col>

                        <!-- Segunda columna -->
                        <v-col cols="12" sm="6" md="6">
                          <v-text-field
                            v-model="nuevoServicio.existencias"
                            label="Existencias"
                            type="number"
                            :rules="[
                              (v) => !!v || 'Las existencias son requeridas',
                              (v) =>
                                v > 0 || 'Las existencias deben ser positivas',
                              (v) =>
                                !isNaN(v) ||
                                'Las existencias deben ser un número',
                            ]"
                          ></v-text-field>
                          <v-select
                            v-model="nuevoServicio.categoria.idCategoria"
                            :items="categoriasServicios"
                            item-text="nombre"
                            item-value="idCategoria"
                            label="Categoria"
                            :rules="[(v) => !!v || 'La categoría es requerida']"
                          ></v-select>
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
                      @click="cerrarModalAgregarServicio"
                      >Cancelar
                    </v-btn>
                    <v-btn color="blue darken-1" text type="submit"
                      >Guardar</v-btn
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
            :items="servicios"
            :server-items-length="totalItemsServicios"
            :items-per-page.sync="itemsPerPageServicios"
            :loading="loadingServicios"
            :search="searchServicios"
            :page.sync="currentPageServicios"
            @update:page="fetchServicios"
            @update:items-per-page="fetchServicios"
            :footer-props="{
              showFirstLastPage: true,
              'items-per-page-text': 'Items por página',
              'items-per-page-all-text': 'Todos',
              'items-per-page-options': [10, 20, 30, 40, 50],
            }"
          >
            <template v-slot:item="{ item }">
              <tr>
                <td class="text-start">{{ item.nombre }}</td>
                <td class="text-start">{{ item.descripcion }}</td>
                <td class="text-start">{{ item.precio }}</td>
                <td class="text-start">{{ item.precioDescuento }}</td>
                <td class="text-start">
                  <img
                    :src="item.imagen"
                    alt="Servicio"
                    style="max-width: 100px; max-height: 100px"
                  />
                </td>

                <td class="text-start">{{ item.existencias }}</td>
                <td class="text-start">{{ item.categoria.nombre }}</td>
                <td class="text-start">
                  <v-chip :color="item.active ? 'green' : 'red'" outlined small
                    >{{ item.active ? "Activo" : "Inactivo" }}
                  </v-chip>
                </td>
                <td class="text-start">
                  {{ formatDateTime(item.ultimaModificacion) }}
                </td>
                <td class="text-center">
                  <v-dialog
                    v-model="dialogosEditarServicio[item.idServicio]"
                    max-width="500px"
                  >
                    <template v-slot:activator="{ on, attrs }">
                      <v-icon
                        color="blue"
                        v-bind="attrs"
                        v-on="on"
                        @click="openEditServicioDialog(item.idServicio)"
                        >mdi-pencil
                      </v-icon>
                    </template>
                    <v-form
                      @submit.prevent="editItemServicio(item)"
                      ref="formEditarServicio"
                    >
                      <v-card>
                        <v-card-title> Editar servicio</v-card-title>
                        <v-card-text>
                          <v-container>
                            <v-row>
                              <v-col cols="12" sm="6" md="4">
                                <v-text-field
                                  v-model="item.nombre"
                                  label="Nombre"
                                  :rules="[
                                    (v) => !!v || 'El nombre es requerido',
                                  ]"
                                ></v-text-field>
                                <v-text-field
                                  v-model="item.descripcion"
                                  label="Descripcion"
                                  :rules="[
                                    (v) => !!v || 'La descripción es requerida',
                                  ]"
                                  type="text"
                                ></v-text-field>
                                <v-text-field
                                  v-model="item.precio"
                                  label="Precio"
                                  :rules="[
                                    (v) => !!v || 'El precio es requerido',
                                    (v) =>
                                      v > 0 || 'El precio debe ser positivo',
                                  ]"
                                  type="number"
                                ></v-text-field>
                                <v-text-field
                                  v-model="item.precioDescuento"
                                  label="Precio Descuento"
                                  :rules="[
                                    (v) =>
                                      v > -1 || 'El precio debe ser positivo',
                                    (v) =>
                                      !isNaN(v) ||
                                      'El precio debe ser un número',
                                  ]"
                                  type="number"
                                ></v-text-field>
                                <v-text-field
                                  v-model="item.existencias"
                                  label="Existencias"
                                  :rules="[
                                    (v) =>
                                      !!v || 'Las existencias son requeridas',
                                    (v) =>
                                      v > 0 ||
                                      'Las existencias deben ser positivas',
                                  ]"
                                  type="number"
                                ></v-text-field>
                              </v-col>
                              <v-col cols="12" sm="6" md="4">
                                <v-select
                                  v-model="item.categoria.idCategoria"
                                  :items="categoriasServicios"
                                  item-text="nombre"
                                  item-value="idCategoria"
                                  label="Categoria"
                                  :rules="[
                                    (v) => !!v || 'La categoría es requerida',
                                  ]"
                                ></v-select>
                                <v-select
                                  v-model="item.active"
                                  :items="[
                                    { text: 'Activo', value: true },
                                    { text: 'Inactivo', value: false },
                                  ]"
                                  label="Estado"
                                ></v-select>
                                <input
                                  type="file"
                                  @change="onFileChange"
                                  accept="image/*"
                                />
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
                          </v-btn>
                          <v-btn color="blue darken-1" text type="submit"
                            >Guardar
                          </v-btn>
                        </v-card-actions>
                      </v-card>
                    </v-form>
                  </v-dialog>
                  <v-icon
                    color="red"
                    @click="deleteItemServicio(item.idServicio)"
                    >mdi-delete
                  </v-icon>

                </td>
              </tr>
            </template>
          </v-data-table>
        </v-card>
      </v-tab-item>
      <v-tab-item>
        <v-card>
          <v-card-title>
            Servicio Paquete
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
            <v-dialog v-model="dialogPaquete" max-width="500px">
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                  color="primary"
                  dark
                  class="mb-2"
                  v-bind="attrs"
                  v-on="on"
                  >Nuevo paquete
                </v-btn>
              </template>
              <v-form @submit.prevent="agregarPaquete" ref="formAgregarPaquete">
                <v-card>
                  <v-card-title>
                    <span class="text-h5">Agregar nuevo paquete</span>
                  </v-card-title>
                  <v-card-text>
                    <v-container>
                      <v-row>
                        <v-col cols="12" sm="6" md="4">
                          <v-select
                            v-model="nuevoPaquete.paquete.idPaquete"
                            :items="paquete"
                            item-text="nombre"
                            item-value="idPaquete"
                            label="Paquete"
                            :rules="[(v) => !!v || 'El paquete es requerido']"
                          ></v-select>
                          <v-select
                            v-model="nuevoPaquete.servicio.idServicio"
                            :items="servicios"
                            item-text="nombre"
                            item-value="idServicio"
                            label="Servicio"
                            :rules="[(v) => !!v || 'El servicio es requerido']"
                          ></v-select>
                        </v-col>
                        <v-col cols="12" sm="6" md="4">
                          <v-select
                            v-model="nuevoPaquete.active"
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
                      @click="cerrarModalAgregarPaquete"
                      >Cancelar
                    </v-btn>
                    <v-btn color="blue darken-1" text type="submit"
                      >Guardar
                    </v-btn>
                  </v-card-actions>
                </v-card>
              </v-form>
            </v-dialog>
          </v-card-title>
          <v-data-table
            class="mx-auto"
            style="height: auto; max-height: 500px; overflow-y: auto"
            :headers="headersPaquete"
            :items="servicioPaquete"
            :search="searchPaquete"
            :server-items-length="totalItemsPaquete"
            :items-per-page.sync="itemsPerPagePaquete"
            :loading="loadingPaquete"
            :page.sync="currentPagePaquete"
            @update:page="fetchServiciosPaquete"
            @update:items-per-page="fetchServiciosPaquete"
            :footer-props="{
              showFirstLastPage: true,
              'items-per-page-text': 'Items por página',
              'items-per-page-all-text': 'Todos',
              'items-per-page-options': [10, 20, 30, 40, 50],
            }"
          >
            <template v-slot:item="{ item }">
              <tr>
                <td class="text-start">{{ item.paquete.nombre }}</td>
                <td class="text-start">{{ item.servicio.nombre }}</td>
                <td class="text-start">
                  {{ formatDateTime(item.ultimaModificacion) }}
                </td>
                <td class="text-start">
                  <v-chip :color="item.active ? 'green' : 'red'" outlined small
                    >{{ item.active ? "Activo" : "Inactivo" }}
                  </v-chip>
                </td>
                <td class="text-start">
                  {{ item.ultima_modificacion }}
                  <v-dialog
                    v-model="dialogosEditarPaquete[item.idServicioPaquete]"
                    max-width="500px"
                  >
                    <template v-slot:activator="{ on, attrs }">
                      <v-icon
                        color="blue"
                        v-bind="attrs"
                        v-on="on"
                        @click="openEditPaqueteDialog(item.idServicioPaquete)"
                        >mdi-pencil
                      </v-icon>
                    </template>
                    <v-form
                      @submit.prevent="editarServicioPaquete(item)"
                      ref="formEditarServicioPaquete"
                    >
                      <v-card>
                        <v-card-title> Editar servicio paquete</v-card-title>
                        <v-card-text>
                          <v-container>
                            <v-row>
                              <v-col cols="12" sm="6" md="4">
                                <v-select
                                  v-model="item.paquete.idPaquete"
                                  :items="paquete"
                                  item-text="nombre"
                                  item-value="idPaquete"
                                  label="Paquete"
                                  :rules="[
                                    (v) => !!v || 'El paquete es requerido',
                                  ]"
                                ></v-select>
                                <v-select
                                  v-model="item.servicio.idServicio"
                                  :items="servicios"
                                  item-text="nombre"
                                  item-value="idServicio"
                                  label="Servicio"
                                  :rules="[
                                    (v) => !!v || 'El servicio es requerido',
                                  ]"
                                ></v-select>
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
                            @click="cancelEditItemPaquete(item)"
                            >Cerrar
                          </v-btn>

                          <v-btn color="blue darken-1" text type="submit"
                            >Guardar
                          </v-btn>
                        </v-card-actions>
                      </v-card>
                    </v-form>
                  </v-dialog>
                  <v-icon
                    color="red"
                    @click="deleteItemServicioPaquete(item.idServicioPaquete)"
                    >mdi-delete
                  </v-icon>
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
  updateServicio,
  createServicio,
  deleteServicio,
  deleteServicioPaquete,
  createServicioPaquete,
  updateServicioPaquete,
  getAllServiciosPaginado,
  getServiciosPaquetePaginado,
} from "@/services/ServicesServices";
import { getCategoriasServicios } from "@/services/CategoryServices";
import { getAllPaquetes } from "@/services/PaquetesServices";
import swalService from "@/services/SwalService";
import moment from "moment";

export default {
  data() {
    return {
      loadingServicios: false,
      loadingPaquete: false,
      tab: null,
      validAgregarServicio: true,
      searchServicios: "",
      searchPaquete: "",
      dialogServicios: false,
      dialogPaquete: false,
      dialogosEditarServicio: {},
      dialogosEditarPaquete: {},
      categoriasServicios: [],

      nuevoServicio: {
        nombre: "",
        descripcion: "",
        precio: 0,
        precioDescuento: 0,
        imagen: "",
        existencias: 0, // Corregido a "existencias" en lugar de "existencia"
        categoria: {
          idCategoria: "", // Deberías decidir si este campo es generado automáticamente o si el usuario lo ingresa
          nombre: "",
          ultimaModificacion: new Date().toISOString(), // Esto generará la fecha actual en el formato correcto
          active: true,
        },
        ultimaModificacion: new Date().toISOString(), // Esto generará la fecha actual en el formato correcto
        active: true,
      },

      nuevoPaquete: {
        paquete: {
          idPaquete: "",
        },

        servicio: {
          idServicio: "",
        },
        ultimaModificacion: new Date().toISOString(),
        active: true,
      },
      headersServicios: [
        { text: "Nombre", align: "start", sortable: false, value: "nombre" },
        {
          text: "Descripcion",
          align: "start",
          sortable: false,
          value: "descripcion",
        },
        { text: "Precio", align: "start", sortable: false, value: "precio" },
        {
          text: "Descuento",
          align: "start",
          sortable: false,
          value: "precioDescuento",
        },
        { text: "Imagen", align: "start", sortable: false, value: "imagen" },
        {
          text: "Existencia",
          align: "start",
          sortable: false,
          value: "existencia",
        },
        {
          text: "Categoria",
          align: "start",
          sortable: false,
          value: "categoria.nombre",
        }, // Corregido aquí
        { text: "Estado", align: "start", sortable: false, value: "active" },
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

      servicios: [],
      currentPageServicios: 1,
      totalItemsServicios: 0,
      totalPagesServicios: 0,
      itemsPerPageServicios: 10,

      headersPaquete: [
        {
          text: "Nombre paquete",
          align: "start",
          sortable: false,
          value: "nombrePaquete",
        },
        {
          text: "Nombre servicio",
          align: "start",
          sortable: false,
          value: "nombrePaquete",
        },
        {
          text: "Última modificación",
          align: "start",
          sortable: false,
          value: "ultimaModificacion",
        },
        { text: "Estado", align: "start", sortable: false, value: "active" },
        {
          text: "Acciones",
          align: "center",
          sortable: false,
          value: "acciones",
        },
      ],
      paquete: [],
      servicioPaquete: [],
      currentPagePaquete: 1,
      totalItemsPaquete: 0,
      totalPagesPaquete: 0,
      itemsPerPagePaquete: 10,
    };
  },
  methods: {
    formatDateTime(dateTimeString) {
      //mexico city time
      return moment(dateTimeString).format("YYYY-MM-DD HH:mm");
    },
    async fetchServicios() {
      // Renombrado el método
      try {
        this.loadingServicios = true;
        const response = await getAllServiciosPaginado(
          this.currentPageServicios - 1,
          this.itemsPerPageServicios
        );
        if (response) {
          this.totalPagesServicios = response.totalPages;
          this.totalItemsServicios = response.totalElements;
          this.servicios = response.content;
          this.loadingServicios = false;
        } else {
          this.totalPagesServicios = 0;
          this.servicios = [];
          this.currentPageServicios = 1;
          this.totalItemsServicios = 0;
          this.loadingServicios = false;
        }
      } catch (error) {
        console.error(error);
        this.loadingServicios = false;
      }
    },
    async fetchServiciosPaquete() {
      try {
        this.loadingPaquete = true;
        const response = await getServiciosPaquetePaginado(
          this.currentPagePaquete - 1,
          this.itemsPerPagePaquete
        );
        if (response) {
          this.totalPagesPaquete = response.totalPages;
          this.totalItemsPaquete = response.totalElements;
          this.servicioPaquete = response.content;
          this.loadingPaquete = false;
        } else {
          this.totalPagesPaquete = 0;
          this.servicioPaquete = [];
          this.currentPagePaquete = 1;
          this.totalItemsPaquete = 0;
          this.loadingPaquete = false;
        }
      } catch (error) {
        console.error(error);
      }
    },
    async getCategoriasServicios() {
      try {
        const response = await getCategoriasServicios();
        if (response) {
          this.categoriasServicios = response;
        }
      } catch (error) {
        console.error(error);
      }
    },
    async getAllPaquetes() {
      try {
        const response = await getAllPaquetes();
        if (response) {
          this.paquete = response;
        }
      } catch (error) {
        console.error(error);
      }
    },

    cancelEditItemServicio(item) {
      this.dialogosEditarServicio[item.idServicio] = false;
    },
    cancelEditItemPaquete(item) {
      this.dialogosEditarPaquete[item.idServicioPaquete] = false;
    },
    cerrarModalAgregarServicio() {
      this.dialogServicios = false;
      // Limpiar el formulario al cerrar el modal
      this.nuevoServicio = {
        nombre: "",
        descripcion: "",
        precio: 0,
        precioDescuento: 0,
        imagen: "",
        existencias: 0, // Corregido aquí
        categoria: {
          idCategoria: "", // Deberías decidir si este campo es generado automáticamente o si el usuario lo ingresa
          nombre: "",
          ultimaModificacion: new Date().toISOString(), // Esto generará la fecha actual en el formato correcto
          active: true,
        },
        ultimaModificacion: new Date().toISOString(), // Esto generará la fecha actual en el formato correcto
        active: true,
      };
    },
    async editItemServicio(nuevoServicio) {
      this.loadingServicios = true;
      nuevoServicio.ultimaModificacion = new Date().toISOString(); // Esto generará la fecha actual en el formato correcto
      nuevoServicio.imagen = this.nuevoServicio.imagen;
      try {
        if (this.$refs.formEditarServicio.validate()) {
          await updateServicio(nuevoServicio);
          await this.fetchServicios(); // Llamada al método renombrado
          this.dialogosEditarServicio[nuevoServicio.idServicio] = false;
          this.loadingServicios = false;
        }
      } catch (error) {
        console.error("Error al actualizar categoría de servicio:", error);
        this.loadingServicios = false;
      }
    },

    cerrarModalAgregarPaquete() {
      this.dialogPaquete = false;
      // Limpiar el formulario al cerrar el modal
      this.nuevoPaquete = {
        paquete: {
          idPaquete: "",
        },
        servicio: {
          idServicio: "",
        },
        ultimaModificacion: new Date().toISOString(),
        active: true,
      };
    },

    async editarServicioPaquete(nuevoPaquete) {
      nuevoPaquete.ultimaModificacion = new Date().toISOString(); // Esto generará la fecha actual en el formato correcto
      try {
        if (this.$refs.formEditarServicioPaquete.validate()) {
          await updateServicioPaquete(nuevoPaquete);
          await this.fetchServiciosPaquete();
          this.dialogosEditarPaquete[nuevoPaquete.idServicioPaquete] = false;
        }
      } catch (error) {
        console.error("Error al actualizar categoría de servicio:", error);
      }
    },
    async deleteItemServicio(idServicio) {
      try {
        let proceder = await swalService.confirmationWarning(
          "¿Estás seguro de eliminar el servicio?"
        );
        if (proceder) {
          await deleteServicio(idServicio);
          await this.fetchServicios(); // Llamada al método renombrado
        }
      } catch (error) {
        console.error("Error al eliminar servicio:", error);
      }
    },
    async deleteItemServicioPaquete(idServicioPaquete) {
      try {
        await deleteServicioPaquete(idServicioPaquete);

        const index = this.servicioPaquete.findIndex(
          (service) => service.idServicioPaquete === idServicioPaquete
        );
        if (index !== -1) {
          this.servicioPaquete.splice(index, 1);
        }
      } catch (error) {
        console.error("Error al eliminar categoría de servicio:", error);
      }
    },
    async agregarServicio() {
      try {
        if (this.$refs.formAgregarServicio.validate()) {
          const nuevoServicio = await createServicio(this.nuevoServicio);
          if (nuevoServicio) {
            this.dialogServicios = false;
            this.nuevoServicio = {
              nombre: "",
              descripcion: "",
              precio: 0,
              precioDescuento: 0,
              imagen: "",
              existencias: 0, // Corregido aquí
              categoria: {
                idCategoria: "", // Deberías decidir si este campo es generado automáticamente o si el usuario lo ingresa
                nombre: "",
                ultimaModificacion: new Date().toISOString(), // Esto generará la fecha actual en el formato correcto
                active: true,
              },
              ultimaModificacion: new Date().toISOString(), // Esto generará la fecha actual en el formato correcto

              active: true, // Por defecto, nuevo servicio activo
            };
            this.cerrarModalAgregarServicio();
            await this.fetchServicios(); // Llamada al método renombrado
          }
        }
      } catch (error) {
        console.error("Error al agregar categoría de servicio:", error);
      }
    },
    async agregarPaquete() {
      try {
        if (this.$refs.formAgregarPaquete.validate()) {
          const nuevoPaquete = await createServicioPaquete(this.nuevoPaquete);
          if (nuevoPaquete) {
            this.dialogPaquete = false;
            this.nuevoPaquete = {
              paquete: {
                idPaquete: "",
              },
              ultimaModificacion: new Date().toISOString(),
              active: true,
            };
            this.cerrarModalAgregarPaquete();
            await this.getAllPaquetes(); // Llamada al método renombrado
            await this.fetchServiciosPaquete();
          }
        }
      } catch (error) {
        console.error("Error al agregar categoría de servicio:", error);
      }
    },

    openEditServicioDialog(idServicio) {
      this.$set(this.dialogosEditarServicio, idServicio, true);
    },
    openEditPaqueteDialog(idServicioPaquete) {
      this.$set(this.dialogosEditarPaquete, idServicioPaquete, true);
    },
    onFileChange(event) {
      const file = event.target.files[0];
      if (file) {
        if (file.size > 16 * 1024 * 1024) {
          alert("El tamaño máximo de la imagen es de 16 MB.");
        } else {
          this.convertToBase64(file);
        }
      }
    },

    convertToBase64(file) {
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => {
        this.$set(this.nuevoServicio, "imagen", reader.result);
      };
      reader.onerror = (error) => {
        console.error("Error al leer la imagen:", error);
      };
    },
  },
  mounted() {
    this.fetchServicios(); // Llamada al método renombrado
    this.fetchServiciosPaquete();
    this.getCategoriasServicios();
    this.getAllPaquetes();
  },
  watch: {
    searchPaquete: async function (val) {
      if (val) {
        this.servicioPaquete = this.servicioPaquete.filter((item) => {
          return (
            item.paquete.nombre.toLowerCase().includes(val.toLowerCase()) ||
            item.servicio.nombre.toLowerCase().includes(val.toLowerCase()) ||
            item.ultimaModificacion.toString().includes(val) ||
            item.active.toString().includes(val)
          );
        });
      } else {
        await this.fetchServiciosPaquete();
      }
    },
    searchServicios: async function (val) {
      if (val) {
        this.servicios = this.servicios.filter((item) => {
          return (
            item.nombre.toLowerCase().includes(val.toLowerCase()) ||
            item.descripcion.toLowerCase().includes(val.toLowerCase()) ||
            item.precio.toString().includes(val) ||
            item.precioDescuento.toString().includes(val) ||
            item.existencias.toString().includes(val) ||
            item.categoria.nombre.toLowerCase().includes(val.toLowerCase())
          );
        });
      } else {
        await this.fetchServicios(); // Llamada al método renombrado
      }
    },
  },
};
</script>
