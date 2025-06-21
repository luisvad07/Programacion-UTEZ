<template>
  <v-card>
    <v-card-title color-te>
      Descuentos
      <v-divider class="mx-4" inset vertical></v-divider>
      <v-spacer></v-spacer>
      <v-text-field v-model="search" append-icon="mdi-magnify" label="Buscar" single-line hide-details></v-text-field>
      <v-spacer></v-spacer>
      <v-dialog v-model="dialog" max-width="500px">
        <template v-slot:activator="{ on }">
          <v-btn color="#0091EA" dark class="mb-2" v-on="on"><v-icon dark>mdi-plus</v-icon></v-btn>
        </template>
        <v-card>
          <v-card-title>
            <span class="text-h5">Agregar nuevo descuento</span>
          </v-card-title>
          <v-card-text>
            <v-container>
              <v-row>
                <v-col cols="12" sm="6" md="4">
                  <v-text-field v-model="newDes.nombre" label="Nombre"></v-text-field>
                </v-col>
                <v-col cols="12" sm="6" md="4">
                  <v-text-field v-model="newDes.porcentaje" label="Porcentaje"></v-text-field>
                </v-col>
              </v-row>
            </v-container>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="blue darken-1" text @click="cerrarModalAgregarDescuento">Cancelar</v-btn>
            <v-btn color="blue darken-1" text @click="agregarDescuento">Guardar</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-card-title>
    <v-data-table class="mx-auto" style="height: 400px;" :headers="headers" :items="desserts" :search="search">
      <template v-slot:item="{ item }">
        <tr>
          <td class="text-start">{{ item.nombre }}</td>
          <td class="text-start">{{ item.porcentaje }}</td>
          <td class="text-start">
            <v-chip :color="item.status === 'Activo' ? 'green' : 'red'" outlined small>{{ item.status }}</v-chip>
          </td>
          <td class="text-center">
            <v-icon color="blue" @click="editItem(item)">mdi-pencil</v-icon>
            <v-icon color="red" @click="deleteItem(item)">mdi-delete</v-icon>
          </td>
        </tr>
      </template>
    </v-data-table>
    
    <v-dialog v-model="editDialog" max-width="500px">
      <v-card>
        <v-card-title>
          <span class="text-h5">Editar descuento</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="12" sm="6" md="4">
                <v-text-field v-model="editedItem.nombre" label="Nombre"></v-text-field>
              </v-col>
              <v-col cols="12" sm="6" md="4">
                <v-text-field v-model="editedItem.porcentaje" label="Porcentaje"></v-text-field>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="cancelEdit">Cancelar</v-btn>
          <v-btn color="blue darken-1" text @click="saveEdit">Guardar</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-card>
</template>

<script>

export default {
  data() {
    return {
      search: '',
      dialog:
      false,
      editDialog: false,
      newDes: {
        nombre: '',
        porcentaje: '',
        status: ''
      },
      editedItem: {
        nombre: '',
        porcentaje: '',
      },
      headers: [
        { text: 'Nombre', align: 'start', sortable: true, value: 'nombre'},
        { text: 'Porcentaje', align: 'start', sortable: true, value: 'porcentaje' },
        { text: 'Status', align: 'start', sortable: false, value: 'status' },
        { text: 'Acciones', align: 'center', sortable: false, value: 'acciones' },
      ],
      desserts: [
        {
          nombre: 'Comida Rapida',
          porcentaje: '20%',
          status: 'Activo',
        },
        // Otros descuentos...
      ],
    };
  },
  methods: {
    editItem(item) {
      // Copiar los datos del descuento a editar
      this.editedItem = { ...item };
      // Abrir el modal de edición
      this.editDialog = true;
    },
    cancelEdit() {
      // Cerrar el modal de edición
      this.editDialog = false;
      this.editedItem = {
        nombre: '',
        porcentaje: '',
      };
    },
    saveEdit() {
      console.log("Descuento editado:", this.editedItem);
      this.editDialog = false;
      this.editedItem = {
        nombre: '',
        porcentaje: '',
      };
    },
    deleteItem(item) {
      // Cambiar el estado del descuento de activo a inactivo o viceversa
      item.status = item.status === 'Activo' ? 'Inactivo' : 'Activo';
    },
    cerrarModalAgregarDescuento() {
      this.dialog = false;
      // Limpiar el formulario al cerrar el modal
      this.newDes = {
        nombre: '',
        porcentaje: '',
        status: ''
      };
    },
    agregarDescuento() {
      // Aquí puedes agregar la lógica para guardar el nuevo descuento
      console.log("Nuevo descuento guardado:", this.newDes);
      // Cerrar el modal
      this.dialog = false;
      // Limpiar el formulario al cerrar el modal
      this.newDes = {
        nombre: '',
        porcentaje: '',
        status: ''
      };
    }
  }
}
</script>
