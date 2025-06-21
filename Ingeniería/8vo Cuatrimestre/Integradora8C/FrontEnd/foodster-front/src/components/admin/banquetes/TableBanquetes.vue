<template>
    <v-card>
      <v-card-title>
        Banquetes
        <v-divider class="mx-4" inset vertical></v-divider>
        <v-spacer></v-spacer>
        <v-text-field v-model="search" append-icon="mdi-magnify" label="Buscar" single-line hide-details></v-text-field>
        <v-spacer></v-spacer>
        <v-dialog v-model="dialog" max-width="500px">
          <template v-slot:activator="{ on, attrs }">
            <v-btn color="primary" dark class="mb-2" v-bind="attrs" v-on="on">Nuevo Banquete</v-btn>
          </template>
          <v-card>
            <v-card-title>
              <span class="text-h5">Agregar nuevo usuario</span>
            </v-card-title>
            <v-card-text>
              <v-container>
                <v-row>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field v-model="nuevoUsuario.nombre" label="Nombre"></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field v-model="nuevoUsuario.apellidos" label="Apellidos"></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field v-model="nuevoUsuario.role" label="Role"></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field v-model="nuevoUsuario.username" label="Username"></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field v-model="nuevoUsuario.status" label="Status"></v-text-field>
                  </v-col>
                </v-row>
              </v-container>
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="blue darken-1" text @click="cerrarModalAgregarUsuario">Cancelar</v-btn>
              <v-btn color="blue darken-1" text @click="agregarUsuario">Guardar</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-card-title>
      <v-data-table class="mx-auto" style="height: 400px;" :headers="headers" :items="desserts" :search="search">
        <template v-slot:item="{ item }">
          <tr>
            <td class="text-start">{{ item.nombre }}</td>
            <td class="text-start">{{ item.apellidos }}</td>
            <td class="text-start">{{ item.role }}</td>
            <td class="text-start">{{ item.username }}</td>
            <td class="text-start">{{ item.status }}</td>
            <td class="text-center">
              <v-icon color="blue" @click="editItem(item)">mdi-pencil</v-icon>
              <v-icon color="red" @click="deleteItem(item)">mdi-delete</v-icon>
            </td>
          </tr>
        </template>
      </v-data-table>
    </v-card>
  </template>
  
  <script>
  export default {
    data() {
      return {
        search: '',
        dialog: false,
        nuevoUsuario: {
          nombre: '',
          apellidos: '',
          role: '',
          username: '',
          status: ''
        },
        headers: [
          { text: 'Nombre', align: 'start', sortable: false, value: 'nombre' },
          { text: 'Apellidos', align: 'start', sortable: false, value: 'apellidos' },
          { text: 'Role', align: 'start', sortable: false, value: 'role' },
          { text: 'Username', align: 'start', sortable: false, value: 'username' },
          { text: 'Status', align: 'start', sortable: false, value: 'status' },
          { text: 'Acciones', align: 'center', sortable: false, value: 'acciones' },
        ],
        desserts: [
          {
            nombre: 'John',
            apellidos: 'Doe',
            role: 'Admin',
            username: 'johndoe',
            status: 'Activo',
          },
          {
            nombre: 'Jane',
            apellidos: 'Doe',
            role: 'User',
            username: 'janedoe',
            status: 'Inactivo',
          },
          {
            nombre: 'Brayan',
            apellidos: 'Reynoso Macedo',
            role: 'Admin',
            username: 'bryanr0',
            status: 'Activo'
          }
          // Otros usuarios...
        ],
      }
    },
    methods: {
      editItem(item) {
        console.log("Edit")
      },
      deleteItem(item) {
        // Lógica para eliminar el elemento
      },
      cerrarModalAgregarUsuario() {
        this.dialog = false;
        // Limpiar el formulario al cerrar el modal
        this.nuevoUsuario = {
          nombre: '',
          apellidos: '',
          role: '',
          username: '',
          status: ''
        };
      },
      agregarUsuario() {
        // Aquí puedes agregar la lógica para guardar el nuevo usuario
        console.log("Nuevo usuario guardado:", this.nuevoUsuario);
        // Cerrar el modal
        this.dialog = false;
        // Limpiar el formulario al cerrar el modal
        this.nuevoUsuario = {
          nombre: '',
          apellidos: '',
          role: '',
          username: '',
          status: ''
        };
      }
    }
  }
  </script>
  