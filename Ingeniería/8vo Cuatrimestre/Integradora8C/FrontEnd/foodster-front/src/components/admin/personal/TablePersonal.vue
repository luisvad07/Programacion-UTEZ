<template>

  <v-card>
    <v-card-title>
      Personal
      <v-divider class="mx-4" inset vertical></v-divider>
      <v-spacer></v-spacer>
      <v-text-field v-model="search" append-icon="mdi-magnify" label="Buscar" single-line hide-details></v-text-field>
      <v-spacer></v-spacer>
      <v-dialog v-model="dialog" max-width="500px">
        <template v-slot:activator="{ on, attrs }">
          <v-btn color="primary" dark class="mb-2" v-bind="attrs" v-on="on">Nueva Persona</v-btn>
        </template>
        <v-card>
          <v-form ref="form" @submit.prevent="addPersonal">
            <v-card-title>
              <span class="text-h5">Agregar nueva persona</span>
            </v-card-title>
            <v-card-text>
              <v-container>
                <v-row>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field v-model="nuevoUsuario.nombres" label="Nombre"
                                  :rules="[v => !!v || 'El nombre es requerido', v => (v && v.length <= 30 && v.length >= 3) || 'El nombre debe tener entre 3 y 30 caracteres']"

                    ></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field v-model="nuevoUsuario.primerApellido" label="Primer Apellido"
                                  :rules="[v => !!v || 'El apellido es requerido', v => (v && v.length <= 30 && v.length >= 3) || 'El apellido debe tener entre 3 y 30 caracteres']"
                    ></v-text-field>

                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field v-model="nuevoUsuario.segundoApellido" label="Segundo Apellido"
                                  :rules="[v => !!v || 'El apellido es requerido', v => (v && v.length <= 30 && v.length >= 3) || 'El apellido debe tener entre 3 y 30 caracteres']"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field v-model="nuevoUsuario.telefono" label="Telefono"
                                  :rules="[v => !!v || 'El telefono es requerido',
                                  v => (v && v.length === 10) || 'El telefono debe tener 10 caracteres',
                                  v => !isNaN(v) || 'Solo se permiten números']"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field v-model="nuevoUsuario.correo" label="Correo"
                                  :rules="[v => !!v || 'El correo es requerido',
                                   (v) => /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(v) || 'El correo electronico tiene un formato incorrecto'
                                   ]"
                                  required></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field v-model="nuevoUsuario.contrasena" label="Contraseña"
                                  :rules="passwordRules"
                                  required></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field label="Confirmar contraseña"
                                  :rules="[v => !!v || 'La confirmación de la contraseña es requerida', v => v === nuevoUsuario.contrasena || 'Las contraseñas no coinciden']"
                                  required></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-select label="Cargo" v-model="nuevoUsuario.categoria.idCategoria" :items="categoriasPersonal"
                              item-text="nombre" item-value="idCategoria"
                              :rules="[v => !!v || 'El cargo es requerido']"
                    >
                    </v-select>
                  </v-col>

                </v-row>
              </v-container>
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="blue darken-1" text @click="closeModalAddPersonal">Cancelar</v-btn>
              <v-btn color="blue darken-1" text type="submit"> <!-- Cambiar a submit -->
                Guardar
              </v-btn>
            </v-card-actions>
          </v-form>
        </v-card>
      </v-dialog>
    </v-card-title>
    <v-data-table
        class="mx-auto"
        style="height: auto; max-height: 500px; overflow-y: auto"
        :items-per-page-options="[5, 10, 15]"
        :headers="headers"
        :items="personal"
        :server-items-length="totalItems"
        :items-per-page.sync="itemsPerPage"
        :loading="loading"
        :search="search"
        :page.sync="currentPage"
        @update:page="getPersonal"
        @update:items-per-page="getPersonal"
        :footer-props="{
          showFirstLastPage: true,
          'items-per-page-text': 'Items por página',
          'items-per-page-all-text': 'Todos',
          'items-per-page-options': [10, 20, 30, 40, 50]
        }"

    >
      <template v-slot:item="{ item }">
        <tr>
          <td class="text-start">{{ item.usuarios.nombres }}</td>
          <td class="text-start">{{ item.usuarios.primerApellido }}</td>
          <td class="text-start">{{ item.usuarios.segundoApellido }}</td>
          <td class="text-start">{{ item.usuarios.telefono }}</td>
          <td class="text-start">{{ item.usuarios.correo }}</td>
          <td class="text-start">{{ item.usuarios.roles[0].nombre }}</td>
          <td class="text-start">{{ item.categoria.nombre }}</td>
          <td class="text-start">{{ formatDateTime(item.usuarios.ultimaModificacion) }}</td>
          <td class="text-start">
            <v-chip @click="changeStatus(item.idPersonal)" :color="item.active === true ? 'green' : 'red'" outlined
                    small>{{
                item.active === true ? 'Activo' :
                    "Inactivo"
              }}
            </v-chip>
          </td>
          <td class="text-center">
            <v-icon color="blue" @click="editItem(item)">mdi-pencil</v-icon>
            <v-icon color="red" @click="deleteItem(item.idPersonal)">mdi-delete</v-icon>
          </td>
        </tr>
      </template>
    </v-data-table>
    <v-dialog v-model="editDialog" max-width="500px">
      <v-form ref="formUpdate" @submit.prevent="saveEdit" v-model="valid">
        <v-card>
          <v-card-title>
            <span class="text-h5">Editar persona</span>
          </v-card-title>
          <v-card-text>
            <v-container>
              <v-row>
                <v-col cols="12" sm="6" md="4">
                  <v-text-field v-model="editedItem.usuarios.nombres" label="Nombre"
                                :rules="[v => !!v || 'El nombre es requerido', v => (v && v.length <= 30 && v.length >= 3) || 'El nombre debe tener entre 3 y 30 caracteres']"
                  ></v-text-field>

                </v-col>
                <v-col cols="12" sm="6" md="4">
                  <v-text-field v-model="editedItem.usuarios.primerApellido" label="Primer Apellido"
                                :rules="[v => !!v || 'El apellido es requerido', v => (v && v.length <= 30 && v.length >= 3) || 'El apellido debe tener entre 3 y 30 caracteres']"
                  ></v-text-field>
                </v-col>
                <v-col cols="12" sm="6" md="4">
                  <v-text-field v-model="editedItem.usuarios.segundoApellido" label="Segundo Apellido"
                                :rules="[v => !!v || 'El apellido es requerido', v => (v && v.length <= 30 && v.length >= 3) || 'El apellido debe tener entre 3 y 30 caracteres']"
                  ></v-text-field>
                </v-col>
                <v-col cols="12" sm="6" md="4">
                  <v-text-field v-model="editedItem.usuarios.telefono" label="Telefono"
                                :rules="[v => !!v || 'El telefono es requerido',
                                v => (v && v.length === 10) || 'El telefono debe tener 10 caracteres',
                                v => !isNaN(v) || 'Solo se permiten números']"
                  ></v-text-field>
                </v-col>
                <v-col cols="12" sm="6" md="4">
                  <v-select label="Cargo" v-model="editedItem.categoria.idCategoria" :items="categoriasPersonal"
                            item-text="nombre" item-value="idCategoria" :rules="[v => !!v || 'El cargo es requerido']">
                  </v-select>
                </v-col>
              </v-row>
            </v-container>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="blue darken-1" text @click="cancelEdit">Cancelar</v-btn>
            <v-btn color="blue darken-1" text type="submit">Guardar</v-btn>
          </v-card-actions>
        </v-card>
      </v-form>
    </v-dialog>
  </v-card>
</template>

<script>
import {getCategoriasPersonales} from "@/services/CategoryServices";
import swalService from "@/services/SwalService";
import moment from "moment";
import PersonalServices from "@/services/PersonalServices";

export default {
  data() {
    return {
      passwordRules: [
        v => !!v || 'la contraseña es requerida',
        v => (v && v.length >= 8) || 'Minimo 8 caracteres',
        v => (v && /[A-Z]/.test(v)) || 'Al menos una letra mayúscula',
        v => (v && /[a-z]/.test(v)) || 'Al menos una letra minúscula',
        v => /\d/.test(v) || 'Al menos un número',
        v => (v && !/\s/.test(v)) || 'No espacios en blanco',
        v => (v && /[!@#$%^&*()_+\-=[\]{};':"\\|,.<>?]+/.test(v)) || 'Al menos un caracter especial',
      ],
      loading: false,
      personal: [],
      currentPage: 1,
      totalItems: 0,
      totalPages: 0,
      itemsPerPage: 10,
      categoriasPersonal: [],
      search: '',
      dialog: false,
      editDialog: false,
      valid: false,
      items: [
        {text: 'Personal', disabled: false, href: '/admin/personal'},
        {text: 'Direcciones', disabled: false, href: '/admin/direcciones'},

      ],
      nuevoUsuario: {
        nombres: '',
        primerApellido: '',
        segundoApellido: '',
        telefono: '',
        correo: '',
        contrasena: '',
        idRol: "3ab228ec-a90d-496c-811b-d404347f1d24",
        categoria: {
          idCategoria: ''
        },
      },
      editedItem: {
        idPersonal: "",
        usuarios: {
          idUsuario: "",
          nombres: "",
          primerApellido: "",
          segundoApellido: "",
          telefono: "",
          correo: "",
          contrasena: "",
          active: '',
          roles: [
            {
              idRol: "",
            }
          ]
        },
        categoria: {
          idCategoria: "",
        },
        active: ''
      },
      headers: [

        {text: 'Nombre', align: 'start', sortable: false, value: 'nombres'},
        {text: 'Primer Apellido', align: 'start', sortable: false, value: 'pApellido'},
        {text: 'Segundo Apellido', align: 'start', sortable: false, value: 'sApellido'},
        {text: 'Telefono', align: 'start', sortable: false, value: 'telefono'},
        {text: 'Correo', align: 'start', sortable: false, value: 'correo'},
        {text: 'Rol', align: 'start', sortable: false, value: 'role'},
        {text: 'Cargo', align: 'start', sortable: false, value: 'cargo'},
        {text: 'Ultima Modificación', align: 'start', sortable: false, value: 'ultimaModificacion'},
        {text: 'Estado', align: 'start', sortable: false, value: 'estado'},
        {text: 'Acciones', align: 'center', sortable: false, value: 'acciones'},
      ],
      email: '',
    };
  },
  mounted() {
    this.getPersonal();
    this.getCategoriasPersonal();
  },
  methods: {
    formatDateTime(dateTimeString) {
      //mexico city time
      return moment(dateTimeString).format("YYYY-MM-DD HH:mm");
    },
    async getCategoriasPersonal() {
      try {
        this.categoriasPersonal = await getCategoriasPersonales();
      } catch (error) {
        console.log(error)
      }
    },
    async getPersonal() {
      try {
        this.loading = true;
        const response = await PersonalServices.getAllPaginado(this.currentPage - 1, this.itemsPerPage);
        if (response) {
          this.totalPages = response.totalPages;
          this.totalItems = response.totalElements;
          this.personal = response.content;
          this.loading = false;
        } else {
          this.totalPages = 0;
          this.personal = [];
          this.currentPage = 1;
          this.totalItems = 0;
          this.loading = false;
        }
      } catch (error) {
        console.error(error);
        this.loading = false;
      }
    },
    editItem(item) {
      this.editedItem = {...item};
      this.editDialog = true;
    },
    cancelEdit() {
      // Cerrar el modal de edición
      this.editDialog = false;
      // Restablecer los datos de la persona en edición
      this.editedItem = {
        idPersonal: "",
        usuarios: {
          idUsuario: "",
          nombres: "",
          primerApellido: "",
          segundoApellido: "",
          telefono: "",
          correo: "",
          contrasena: "",
          active: '',
          roles: [
            {
              idRol: "",
            }
          ]
        },
        categoria: {
          idCategoria: "",
        },
        active: ''
      };
    },
    async saveEdit() {
      try {
        if (this.$refs.formUpdate.validate()) {
          await PersonalServices.update(this.editedItem);
          this.editDialog = false;
          this.editedItem = {
            idPersonal: "",
            usuarios: {
              idUsuario: "",
              nombres: "",
              primerApellido: "",
              segundoApellido: "",
              telefono: "",
              correo: "",
              contrasena: "",
              active: '',
              roles: [
                {
                  idRol: "",
                }
              ]
            },
            categoria: {
              idCategoria: "",
            },
            active: ''
          };
          await this.getPersonal();
        }
      } catch (error) {
      }

    },
    async deleteItem(idPersonal) {
      let proceder = await swalService.confirmationWarning(
          "¿Estás seguro de eliminar este personal?",
      );
      if (proceder) {
        await personalServices.delete_(idPersonal);
        await this.getPersonal();
      }
    }
    ,
    async changeStatus(idPersonal) {
      let proceder = await swalService.confirmationWarning(
          "¿Estás seguro de cambiar el estado de este personal?",
      );
      if (proceder) {
        await PersonalServices.changeStatus(idPersonal);
        await this.getPersonal();
      }
    }

    ,
    closeModalAddPersonal() {
      this.dialog = false;
      this.resetNuevoPersona();
    },
    resetNuevoPersona() {
      this.nuevoUsuario = {
        nombres: '',
        primerApellido: '',
        segundoApellido: '',
        telefono: '',
        correo: '',
        contrasena: '',
        idRol: "3ab228ec-a90d-496c-811b-d404347f1d24",
        categoria: {
          idCategoria: ''
        },
      }
    },
    async addPersonal() {
      try {
        if (this.$refs.form.validate()) {
          await PersonalServices.insert(this.nuevoUsuario);
          this.closeModalAddPersonal();
          await this.getPersonal()
        }
      } catch (error) {
        console.log(error)
      }
    }


  },
  watch: {
    search: async function (val) {
      if (val) {
        // filter personal data based on search value
        this.personal = this.personal.filter(person =>
            person.usuarios.nombres.toLowerCase().includes(val.toLowerCase()) ||
            person.usuarios.primerApellido.toLowerCase().includes(val.toLowerCase()) ||
            person.usuarios.segundoApellido.toLowerCase().includes(val.toLowerCase()) ||
            person.usuarios.telefono.toLowerCase().includes(val.toLowerCase()) ||
            person.usuarios.correo.toLowerCase().includes(val.toLowerCase())
        );
      } else {
        await this.getPersonal();
      }
    }
  }
}
</script>