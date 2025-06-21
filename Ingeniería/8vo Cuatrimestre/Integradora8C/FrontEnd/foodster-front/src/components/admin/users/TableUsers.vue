<template>
  <v-card>
    <v-card-title color-te>
      Usuarios
      <v-divider class="mx-4" inset vertical></v-divider>
      <v-spacer></v-spacer>
      <v-text-field v-model="search" append-icon="mdi-magnify" label="Buscar" single-line hide-details></v-text-field>
      <v-spacer></v-spacer>
      <v-dialog v-model="dialog" max-width="500px">
        <template v-slot:activator="{ on }">
          <v-btn color="#0091EA" dark class="mb-2" v-on="on">
            <v-icon dark>mdi-plus</v-icon>
          </v-btn>
        </template>
        <v-card>
          <v-form ref="formAgregar" @submit.prevent="addUsuario">
            <v-card-title>
              <span class="text-h5">Agregar nuevo usuario</span>
            </v-card-title>
            <v-card-text>
              <v-container>
                <v-row>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field v-model="nuevoUsuario.nombres" label="Nombres"
                                  :rules="[v => !!v || 'Ingrese el nombre', v => v.length <= 30 && v.length >=3 || 'El nombre debe de tener entre 3 y 30 caracteres']"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field v-model="nuevoUsuario.primerApellido" label="Primer Apellido"
                                  :rules="[v => !!v || 'Ingrese el segundo apellido', v => v.length <= 30 && v.length >=3 || 'El apellido debe de tener entre 3 y 30 caracteres']"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field v-model="nuevoUsuario.segundoApellido" label="Segundo Apellido"
                                  :rules="[v => !!v || 'Ingrese el segundo apellido', v => v.length <= 30 && v.length >=3 || 'El apellido debe de tener entre 3 y 30 caracteres']"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field v-model="nuevoUsuario.telefono" label="Teléfono"
                                  :rules="[v => !!v || 'El telefono es requerido',
                                v => (v && v.length === 10) || 'El telefono debe tener 10 caracteres',
                                v => !isNaN(v) || 'Solo se permiten números']"
                    ></v-text-field>
                  </v-col>

                  <v-col cols="12" sm="6" md="4">
                    <v-text-field v-model="nuevoUsuario.correo" label="Correo"
                                  :rules="[v => !!v || 'Ingrese el correo', v => /.+@.+\..+/.test(v) || 'Ingrese un correo válido']"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field v-model="nuevoUsuario.contrasena" label="Contraseña"
                                  :rules="passwordRules"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field label="Confirmar contraseña"
                                  :rules="[v => !!v || 'Confirme la contraseña', v => (v === nuevoUsuario.contrasena) || 'Las contraseñas no coinciden']"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-select label="Rol" v-model="nuevoUsuario.roles[0].idRol" :items="roles" item-text="nombre"
                              item-value="idRol" :rules="[v => !!v || 'Seleccione un rol']">
                    </v-select>
                  </v-col>
                </v-row>
              </v-container>
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="blue darken-1" text @click="closeModalAddUsuario">Cancelar</v-btn>
              <v-btn color="blue darken-1" text type="submit">Guardar</v-btn>
            </v-card-actions>
          </v-form>
        </v-card>

      </v-dialog>
    </v-card-title>
    <v-data-table
        class="mx-auto"
        style="height: auto; max-height: 500px; overflow-y: auto"

        :headers="headers"
        :items="users"
        :server-items-length="totalItems"
        :items-per-page.sync="itemsPerPage"
        :loading="loading"
        :search="search"
        :page.sync="currentPage"
        @update:page="getUsers"
        @update:items-per-page="getUsers"
        :footer-props="{
          showFirstLastPage: true,
          'items-per-page-text': 'Items por página',
          'items-per-page-all-text': 'Todos',
          'items-per-page-options': [10, 20, 30, 40, 50]
        }"

    >
      <template v-slot:item="{ item }">
        <tr>
          <td class="text-start">{{ item.nombres }}</td>
          <td class="text-start">{{ item.primerApellido }}</td>
          <td class="text-start">{{ item.segundoApellido }}</td>
          <td class="text-start">{{ item.telefono }}</td>
          <td class="text-start">{{ item.correo }}</td>
          <td class="text-start">{{
              formatDateTime(item.ultimaModificacion)
            }}
          </td>
          <td class="text-start">
            <v-chip @click="changeStatus(item.idUsuario)" :color="item.active ? 'green' : 'red'" outlined small>
              {{ item.active ? 'Activo' : 'Inactivo' }}
            </v-chip>
          </td>
          <td class="text-start">{{ item.roles && item.roles.length > 0 ? item.roles[0].nombre : 'SN' }}</td>

          <td class="text-center">
            <v-icon color="blue" @click="editItem(item)">mdi-pencil</v-icon>
            <v-icon color="red" @click="deleteUser(item.idUsuario)">mdi-delete</v-icon>
          </td>
        </tr>
      </template>
    </v-data-table>
    <v-dialog v-model="editDialog" max-width="500px">
      <v-form ref="formEditar" @submit.prevent="saveEdit">
        <v-card>
          <v-card-title>
            <span class="text-h5">Editar Usuario</span>
          </v-card-title>
          <v-card-text>
            <v-container>
              <v-row>
                <v-col cols="12" sm="6" md="4">
                  <v-text-field v-model="usuarioEditado.nombres" label="Nombres"
                                :rules="[v => !!v || 'Ingrese el nombre', v => v.length <= 30 && v.length >=3 || 'El nombre debe de tener entre 3 y 30 caracteres']"
                  ></v-text-field>
                </v-col>
                <v-col cols="12" sm="6" md="4">
                  <v-text-field v-model="usuarioEditado.primerApellido" label="Primer Apellido"
                                :rules="[v => !!v || 'Ingrese el segundo apellido', v => v.length <= 30 && v.length >=3 || 'El apellido debe de tener entre 3 y 30 caracteres']"
                  ></v-text-field>
                </v-col>
                <v-col cols="12" sm="6" md="4">
                  <v-text-field v-model="usuarioEditado.segundoApellido" label="Segundo Apellido"
                                :rules="[v => !!v || 'Ingrese el segundo apellido', v => v.length <= 30 && v.length >=3 || 'El apellido debe de tener entre 3 y 30 caracteres']"
                  ></v-text-field>
                </v-col>
                <v-col cols="12" sm="6" md="4">
                  <v-text-field v-model="usuarioEditado.telefono" label="Teléfono"
                                :rules="[v => !!v || 'Ingrese el teléfono', v => (v && v.length === 10) || 'Ingrese un teléfono válido']"
                  ></v-text-field>
                </v-col>
                <v-col cols="12" sm="6" md="4">
                  <v-select label="Rol" v-model="usuarioEditado.roles[0].idRol" :items="roles" item-text="nombre"
                            item-value="idRol"
                            :rules="[v => !!v || 'Seleccione un rol']"
                  >
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
import usersServices from '../../../services/UsersServices'
import rolesService from '../../../services/RolesService'
import moment from 'moment'

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
      users: [],
      currentPage: 1,
      totalItems: 0,
      totalPages: 0,
      itemsPerPage: 10,
      roles: [],
      search: '',
      dialog: false,
      editDialog: false,
      nuevoUsuario: {
        nombres: '',
        primerApellido: '',
        segundoApellido: '',
        telefono: '',
        correo: '',
        contrasena: "",
        active: true,
        roles: [{
          idRol: "",
        }]
      },
      usuarioEditado: {
        nombres: '',
        primerApellido: '',
        segundoApellido: '',
        telefono: '',
        correo: '',
        contrasena: "",
        active: '',
        roles: [{
          idRol: "",
        }]
      },
      headers: [
        {text: 'Nombres', align: 'start', sortable: true, value: 'nombres'},
        {text: 'Primer Apellido', align: 'start', sortable: true, value: 'primerApellido'},
        {text: 'Segundo Apellido', align: 'start', sortable: true, value: 'segundoApellido'},
        {text: 'Teléfono', align: 'start', sortable: false, value: 'telefono'},
        {text: 'Correo', align: 'start', sortable: false, value: 'correo'},
        {text: 'Última Modificación', align: 'start', sortable: true, value: 'ultimaModificacion'},
        {text: 'Estado', align: 'start', sortable: false, value: 'active'},
        {text: 'Nombre del Rol', align: 'start', sortable: false, value: 'rolNombre'},
        {text: 'Acciones', value: 'actions', sortable: false}
      ],
    }
  },
  mounted() {
    this.getUsers();
    this.getRoles();
  },
  methods: {
    formatDateTime(dateTimeString) {
      //mexico city time
      return moment(dateTimeString).format("YYYY-MM-DD HH:mm");
    },
    async getUsers() {
      try {
        this.loading = true;
        const response = await usersServices.getAllPaginado(this.currentPage - 1, this.itemsPerPage);
        if (response) {
          this.totalPages = response.totalPages;
          this.totalItems = response.totalElements;
          this.users = response.content;
          this.loading = false;
        } else {
          this.totalPages = 0;
          this.users = [];
          this.currentPage = 1;
          this.totalItems = 0;
          this.loading = false;
        }
      } catch (error) {
        console.log(error)
        this.loading = false;
      }
    },
    async getRoles() {
      try {
        const response = await rolesService.getRoles();
        console.log(response)
        this.roles = response;
      } catch (error) {
        console.log(error)
      }
    },
    //NOT FOUND, THIS METHOD NOT EXIST IN THE API
    async changeStatus(idUsuario) {
      try {
        await usersServices.changeStatus(idUsuario);
        await this.getUsers();
      } catch (error) {
        console.log(error)
      }
    },
    closeModalAddUsuario() {
      this.nuevoUsuario = {
        nombres: '',
        primerApellido: '',
        segundoApellido: '',
        telefono: '',
        correo: '',
        contrasena: "",
        active: 'true',
        roles: [
          {
            idRol: "",
          }
        ]
      }
      this.dialog = false;
    },

    async addUsuario() {
      try {
        if (this.$refs.formAgregar.validate()) {
          await usersServices.insert(this.nuevoUsuario);
          await this.getUsers();
          this.dialog = false;
          this.nuevoUsuario = {
            nombres: '',
            primerApellido: '',
            segundoApellido: '',
            telefono: '',
            correo: '',
            contrasena: "",
            active: '1',
            roles: [
              {
                idRol: "",
              }
            ]
          }
        }
      } catch (error) {
        console.log(error);
      }
    },
    editItem(item) {
      this.usuarioEditado = {...item};
      this.editDialog = true;
    },
    cancelEdit() {
      this.editDialog = false;
      this.usuarioEditado = {
        nombres: '',
        primerApellido: '',
        segundoApellido: '',
        telefono: '',
        correo: '',
        contrasena: "",
        active: '',
        roles: [
          {
            idRol: "",
          }
        ]
      }
    },
    async saveEdit() {
      try {
        if (this.$refs.formEditar.validate()) {
          await usersServices.update(this.usuarioEditado);
          await this.getUsers();
          this.editDialog = false;
          this.usuarioEditado = {
            nombres: '',
            primerApellido: '',
            segundoApellido: '',
            telefono: '',
            correo: '',
            contrasena: "",
            active: '1',
            roles: [
              {
                idRol: "",
              }
            ]
          }
        }
      } catch (error) {
        console.log(error);
      }
    },
    async deleteUser(idUsuario) {
      try {
        await usersServices.deleteUser(idUsuario);
        await this.getUsers();
      } catch (error) {
        console.log(error);
      }
    },

  },
  watch: {
    search: async function (val) {
      if (val){
        this.users = this.users.filter((item) => {
          return item.nombres.toLowerCase().includes(val.toLowerCase()) ||
              item.primerApellido.toLowerCase().includes(val.toLowerCase()) ||
              item.segundoApellido.toLowerCase().includes(val.toLowerCase()) ||
              item.telefono.toLowerCase().includes(val.toLowerCase()) ||
              item.correo.toLowerCase().includes(val.toLowerCase()) ||
              item.ultimaModificacion.toLowerCase().includes(val.toLowerCase()) ||
              item.roles[0].nombre.toLowerCase().includes(val.toLowerCase()) ||
              item.active.toString().toLowerCase().includes(val.toLowerCase())
        });
      }else {
        await this.getUsers();
      }
    }
  }
}
</script>