<template>
    <v-card>
        <v-card-title color-te>
            Roles
            <v-divider class="mx-4" inset vertical></v-divider>
            <v-spacer></v-spacer>
            <v-text-field v-model="search" append-icon="mdi-magnify" label="Buscar" single-line
                hide-details></v-text-field>
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
        <v-data-table class="mx-auto" style="height: 400px;" :headers="headers" :items="roles" :search="search">
            <template v-slot:item="{ item }">
                <tr>
                    <td class="text-start">{{ item.idRol }}</td>
                    <td class="text-start">{{ item.nombre }}</td>
                    <td class="text-start">{{ item.ultimaModificacion }}</td>

                    <td class="text-start">
                        <v-chip :color="item.active ? 'green' : 'red'" outlined small>{{ item.active ? 'Activo' : 'Inactivo' }}</v-chip>
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
import rolesServices from '../../../services/RolesService'
export default {
    data() {
        return {
            roles: [],
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
                { text: '#', align: 'start', sortable: true, value: 'idRol' },
                { text: 'Nombre', align: 'start', sortable: true, value: 'nombre' },
                { text: 'Modificación', align: 'start', sortable: false, value: 'modificacion' },
                { text: 'Estado', align: 'start', sortable: false, value: 'estado' },
                { text: 'Acciones', align: 'center', sortable: false, value: 'acciones' },
            ],

        };
    },
    mounted() {
        this.getRoles();
    },
    methods: {
        async getRoles() {
            try {
                const response = await rolesServices.getRoles();
                console.log("DATA ROLE->", response);
                if (Array.isArray(response)) {
                    this.roles = response;
                } else if (typeof response === 'object') {
                    this.roles = [response];
                } else {
                    console.error('La respuesta de la API no es un array ni un objeto válido:', response);
                }
            } catch (error) {
                if (error.response) {
                    // Error en la respuesta de la API (por ejemplo, código de estado no 200)
                    console.error('Error en la respuesta de la API:', error.response);
                } else if (error.request) {
                    // Error de red (por ejemplo, solicitud no completada)
                    console.error('Error de red:', error.request);
                } else {
                    // Otros errores
                    console.error('Error al procesar la solicitud:', error.message);
                }
            }
        },

        editItem(item) {
            // Copiar los datos del descuento a editar
this.editedItem = { ...item };            // Abrir el modal de edición
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