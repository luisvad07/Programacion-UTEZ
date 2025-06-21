<template>
  <v-card>
    <v-progress-linear
        color="blue darken-2"
        height="5"
        indeterminate
        v-if="loading"
    ></v-progress-linear>
    <v-card-title>
      Paquete
      <v-divider class="mx-4" inset vertical></v-divider>
      <v-spacer></v-spacer>
      <v-text-field v-model="searchPaquetes" append-icon="mdi-magnify" hide-details label="Buscar"
                    single-line></v-text-field>
      <v-spacer></v-spacer>
      <v-dialog v-model="dialogPaquete" max-width="500px">
        <template v-slot:activator="{ on, attrs }">
          <v-btn class="mb-2" color="primary" dark v-bind="attrs" v-on="on">Nuevo paquete</v-btn>
        </template>
        <v-card>
          <v-form ref="formNuevoPaquete" class="space-y-4" @submit.prevent="agregarPaquete">
            <v-card-title>
              <span class="text-h5">Agregar nuevo paquete</span>
            </v-card-title>
            <v-card-text>
              <v-container>
                <v-row>
                  <v-col cols="12" md="6" sm="6">
                    <v-text-field v-model="nuevoPaquete.nombre"
                                  :rules="[(v) => !!v || 'El nombre es requerido'  , (v) => (v && v.length <= 50 && v.length >= 3) || 'El nombre debe tener entre 3 y 50 caracteres']"
                                  label="Nombre"></v-text-field>
                    <v-text-field v-model="nuevoPaquete.descripcion"
                                  :rules="[(v) => !!v || 'La descripción es requerida', (v) => (v && v.length <= 70 && v.length >= 5) || 'La descripción debe tener entre 5 y 70 caracteres']"
                                  label="Descripción" type="text"></v-text-field>

                    <input accept="image/*" type="file" @change="onFileChange"/>
                  </v-col>
                  <v-col cols="12" md="6" sm="6">
                    <v-text-field v-model="nuevoPaquete.recomendadoPara" :rules="[
        (v) => !!v || 'El recomendado para es requerido',
        (v) => (v && v.length <= 50 && v.length >= 3) || 'El recomendado para debe tener entre 3 y 50 caracteres'
      ]" label="Recomendado para" type="text"></v-text-field>
                  </v-col>
                </v-row>
              </v-container>
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="blue darken-1" text @click="cerrarModalAgregarPaquete">Cerrar</v-btn>
              <v-btn color="blue darken-1" text type="submit">Guardar</v-btn>
            </v-card-actions>
          </v-form>
        </v-card>
      </v-dialog>
    </v-card-title>
    <v-data-table
        :footer-props="{
          showFirstLastPage: true,
          'items-per-page-text': 'Items por página',
          'items-per-page-all-text': 'Todos',
          'items-per-page-options': [10, 20, 30, 40, 50]
        }"
        :headers="headersPaquetes"
        :items="paquetes"
        :items-per-page-options="[5, 10, 15]"
        :items-per-page.sync="itemsPerPage"
        :loading="loading"
        :page.sync="currentPage"
        :search="searchPaquetes"
        :server-items-length="totalItems"
        class="mx-auto"
        style="height: auto; max-height: 500px; overflow-y: auto"
        @update:page="getAllPaquetes"
        @update:items-per-page="getAllPaquetes"

    >
      <template v-slot:item="{ item }">
        <tr>
          <td class="text-start">{{ item.nombre }}</td>
          <td class="text-start">{{ item.descripcion }}</td>
          <td class="text-start">{{ item.recomendadoPara }}</td>
          <td class="text-start">
            <img :src="item.imagen" alt="Paquete de servicios" style="max-width: 100px; max-height: 100px"/>
          </td>
          <td class="text-start">{{ item.numeroPedidos }}</td>
          <td class="text-start">{{ formatDateTime(item.ultimaModificacion) }}</td>
          <td class="text-start">
            <v-chip :color="item.active ? 'green' : 'red'" outlined small>{{
                item.active ? "Activo" : "Inactivo"
              }}
            </v-chip>
          </td>
          <td class="text-center">
            <v-dialog v-model="dialogAsignar[item.idPaquete]" class="w-1/4 overflow-auto mx-auto bg-white shadow-lg rounded-lg max-h-96" flat
                      offset-y>
              <v-card class="p-6">
                <v-card-title class="text-xl font-bold text-gray-700">
                  Elementos del paquete
                </v-card-title>
                <AsignarServicios :paquete="item" :close="closeAsignarDialog"/>
              </v-card>
            </v-dialog>
            <v-dialog v-model="dialogosEditarPaquete[item.idPaquete]" max-width="500px">
              <template v-slot:activator="{ on, attrs }">
                <v-icon color="blue" v-bind="attrs" @click="openEditServicioDialog(item.idPaquete)"
                        v-on="on">mdi-pencil
                </v-icon>
              </template>
              <v-card>
                <v-form ref="formUpdatePaquete" v-model="validUpdate" class="space-y-4"
                        @submit.prevent="editItemPaquete(item)">
                  <v-card-title> Editar servicio</v-card-title>
                  <v-card-text>
                    <v-container>
                      <v-row>

                        <v-col cols="12" md="6" sm="6">
                          <v-text-field v-model="item.nombre" :rules="[(v) => !!v || 'El nombre es requerido',
                                          (v) => (v && v.length <= 50 && v.length >= 3) || 'El nombre debe tener entre 3 y 50 caracteres']"
                                        label="Nombre"></v-text-field>
                          <v-text-field v-model="item.descripcion" :rules="[(v) => !!v || 'La descripción es requerida'
                                        , (v) => (v && v.length <= 70 && v.length >= 5) || 'La descripción debe tener entre 5 y 70 caracteres']"
                                        label="Descripción"
                                        type="text"></v-text-field>
                          <v-select v-model="item.active" :items="[
        { text: 'Activo', value: true },
        { text: 'Inactivo', value: false }
      ]" label="Estado"></v-select>
                        </v-col>
                        <v-col cols="12" md="6" sm="6">
                          <v-text-field v-model="item.recomendadoPara" :rules="[
        (v) => !!v || 'El recomendado para es requerido',
        (v) => (v && v.length <= 50 && v.length >= 3) || 'El recomendado para debe tener entre 3 y 50 caracteres'
      ]" label="Recomendado para" type="text"></v-text-field>
                          <input accept="image/*" type="file" @change="onFileChange"/>
                        </v-col>

                      </v-row>
                    </v-container>
                  </v-card-text>
                  <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="blue darken-1" text @click="cancelEditItemPaquete(item)">Cerrar</v-btn>

                    <v-btn color="blue darken-1" text type="submit">Guardar</v-btn>
                  </v-card-actions>
                </v-form>
              </v-card>

            </v-dialog>
            <v-icon color="red" @click="deleteItemPaquete(item.idPaquete)">mdi-delete</v-icon>
            <v-btn color="blue darken-1" text @click="openAsignarDialog(item.idPaquete)">Asignar servicios</v-btn>

          </td>
        </tr>
      </template>
    </v-data-table>

  </v-card>
</template>

<script>
import {createPaquete, deletePaquete, getAllPaquetesPaginado, updatePaquete,} from "@/services/PaquetesServices";
import moment from "moment";
import AsignarServicios from "@/components/admin/paquete/AsignarServicios.vue";

export default {
  components: {AsignarServicios},
  data() {
    return {
      dialogAsignar:{},
      loading: false,
      tab: null,
      validUpdate: true,
      searchPaquetes: "",
      dialogPaquete: false,
      dialogosEditarPaquete: {},

      nuevoPaquete: {
        nombre: "",
        descripcion: "",
        recomendadoPara: "",
        imagen: "",
        numeroPedidos: 0,
        ultimaModificacion: new Date().toISOString(),
        active: true,
      },
      headersPaquetes: [
        {text: "Nombre", align: "start", sortable: false, value: "nombre"},
        {
          text: "Descripción",
          align: "start",
          sortable: false,
          value: "descripcion",
        },
        {
          text: "Recomendado para: ",
          align: "start",
          sortable: false,
          value: "precioDescuento",
        },
        {text: "Imagen", align: "start", sortable: false, value: "imagen"},
        {
          text: "Número pedidos",
          align: "start",
          sortable: false,
          value: "existencias",
        },
        {
          text: "Última Modificación",
          align: "start",
          sortable: false,
          value: "ultimaModificacion",
        },
        {text: "Estado", align: "start", sortable: false, value: "active"},
        {
          text: "Acciones",
          align: "center",
          sortable: false,
          value: "acciones",
        },
      ],
      paquetes: [],
      currentPage: 1,
      totalItems: 0,
      totalPages: 0,
      itemsPerPage: 10,
    };
  },
  methods: {
    formatDateTime(dateTimeString) {
      //mexico city time
      return moment(dateTimeString).format("YYYY-MM-DD HH:mm");
    },

    openAsignarDialog(idPaquete) {
      this.$set(this.dialogAsignar, idPaquete, true);
    },
    closeAsignarDialog(idPaquete) {
      this.$set(this.dialogAsignar, idPaquete, false);
    },
    async getAllPaquetes() {
      try {
        this.loading = true;
        const response = await getAllPaquetesPaginado(this.currentPage - 1, this.itemsPerPage);
        if (response) {
          this.totalPages = response.totalPages;
          this.totalItems = response.totalElements;
          this.paquetes = response.content;
          this.loading = false;
        } else {
          this.totalPages = 0;
          this.paquetes = [];
          this.currentPage = 1;
          this.totalItems = 0;
          this.loading = false;
        }
      } catch (error) {
        console.error(error);
        this.loading = false;
      }
    },

    cancelEditItemPaquete(item) {
      // Close the edit dialog without saving
      this.dialogosEditarPaquete[item.idPaquete] = false;
    },
    cerrarModalAgregarPaquete() {
      this.dialogPaquete = false;
      // Limpiar el formulario al cerrar el modal
      this.nuevoPaquete = {
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
    editItemPaquete: async function (nuevoPaquete) {

      try {
        if (this.$refs.formUpdatePaquete.validate()) {
          this.loading = true;
          nuevoPaquete.ultimaModificacion = new Date().toISOString(); // Esto generará la fecha actual en el formato correcto
          nuevoPaquete.imagen = this.nuevoPaquete.imagen;
          await updatePaquete(nuevoPaquete);
          await this.getAllPaquetes;
          this.dialogosEditarPaquete[nuevoPaquete.idPaquete] = false;
          this.loading = false;
        }
      } catch (error) {
        console.error("Error al actualizar paquete", error);
        this.loading = false;
      }
    },
    async deleteItemPaquete(idPaquete) {
      try {
        await deletePaquete(idPaquete);
        await this.getAllPaquetes(); // Recargar la lista de servicios
      } catch (error) {
        console.error("Error al eliminar servicio:", error);
      }
    },
    async agregarPaquete(nuevoPaquete) {
      console.log(nuevoPaquete);
      try {
        if (this.$refs.formNuevoPaquete.validate()) {
          const nuevoPaquete = await createPaquete(this.nuevoPaquete);
          if (nuevoPaquete) {
            this.dialogPaquete = false;
            this.nuevoPaquete = {
              paquete: {
                nombre: "",
                descripcion: "",
                recomendadoPara: "",
                imagen: "",
                numeroPedidos: 0,
                ultimaModificacion: new Date().toISOString(),
                active: true,
              },
              ultimaModificacion: new Date().toISOString(),
              active: true,
            };
            this.cerrarModalAgregarPaquete();
            await this.getAllPaquetes();
          }
        }
      } catch (error) {
        console.error("Error al agregar categoría de servicio:", error);
      }
    },

    openEditServicioDialog(idPaquete) {
      this.$set(this.dialogosEditarPaquete, idPaquete, true);
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
        this.$set(this.nuevoPaquete, "imagen", reader.result);
      };
      reader.onerror = (error) => {
        console.error("Error al leer la imagen:", error);
      };
    },

    convertToImageUrl(base64) {
      return "data:image/jpeg;base64," + base64;
    },
  },
  mounted() {
    this.getAllPaquetes();
  },
  watch: {
    searchPaquetes: async function (val) {
      if (val) {
        this.paquetes = this.paquetes.filter((item) => {
          return item.nombre.toLowerCase().includes(val.toLowerCase()) ||
              item.descripcion.toLowerCase().includes(val.toLowerCase()) ||
              item.recomendadoPara.toLowerCase().includes(val.toLowerCase()) ||
              item.numeroPedidos.toString().toLowerCase().includes(val.toLowerCase());
        });
      } else {
        await this.getAllPaquetes();
      }
    },
  },
};
</script>
