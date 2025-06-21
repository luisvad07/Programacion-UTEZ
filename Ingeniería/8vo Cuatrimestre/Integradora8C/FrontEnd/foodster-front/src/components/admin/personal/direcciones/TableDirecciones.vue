<template>

    <v-card>
        <v-breadcrumbs :items="items" large></v-breadcrumbs>
        <v-card-title>
            Direcciones
            <v-divider class="mx-4" inset vertical></v-divider>
            <v-spacer></v-spacer>
            <v-text-field v-model="search" append-icon="mdi-magnify" label="Buscar" single-line
                hide-details></v-text-field>
            <v-spacer></v-spacer>
            <v-dialog v-model="dialog" max-width="500px">
                <template v-slot:activator="{ on, attrs }">
                    <v-btn color="primary" dark class="mb-2" v-bind="attrs" v-on="on">Nueva Persona</v-btn>
                </template>
                <v-card>
                    <v-card-title>
                        <span class="text-h5">Agregar nueva persona</span>
                    </v-card-title>
                    <v-card-text>
                        <v-container>
                            <v-row>
                                <v-col cols="12" sm="6" md="4">
                                    <v-text-field v-model="nuevoUsuario.nombres" label="Nombre"></v-text-field>
                                </v-col>
                                <v-col cols="12" sm="6" md="4">
                                    <v-text-field v-model="nuevoUsuario.primerApellido"
                                        label="Primer Apellido"></v-text-field>
                                </v-col>
                                <v-col cols="12" sm="6" md="4">
                                    <v-text-field v-model="nuevoUsuario.segundoApellido"
                                        label="Segundo Apellido"></v-text-field>
                                </v-col>
                                <v-col cols="12" sm="6" md="4">
                                    <v-text-field v-model="nuevoUsuario.telefono" label="Telefono"></v-text-field>
                                </v-col>
                                <v-col cols="12" sm="6" md="4">
                                    <v-text-field v-model="nuevoUsuario.correo" :rules="emailRules" label="Correo"
                                        required></v-text-field>
                                </v-col>
                                <v-col cols="12" sm="6" md="4">
                                    <v-text-field v-model="nuevoUsuario.contrasena" label="Contraseña"
                                        required></v-text-field>
                                </v-col>
                                <v-col cols="12" sm="6" md="4">
                                    <v-text-field label="Confirmar contraseña"></v-text-field>
                                </v-col>
                                <v-col cols="12" sm="6" md="4">
                                    <v-select label="Cargo" v-model="nuevoUsuario.categoria.idCategoria"
                                        :items="categoriasPersonal" item-text="nombre" item-value="idCategoria">
                                    </v-select>
                                </v-col>

                            </v-row>
                        </v-container>
                    </v-card-text>
                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn color="blue darken-1" text @click="closeModalAddPersonal">Cancelar</v-btn>
                        <v-btn color="blue darken-1" text @click="addPersonal">Guardar</v-btn>
                    </v-card-actions>
                </v-card>
            </v-dialog>
        </v-card-title>
        <v-data-table class="mx-auto" style="height: auto; max-height: 500px; overflow-y: auto" :headers="headers" :items="direcciones" :search="search">
            <template v-slot:item="{ item }">
                <tr>
                    <td class="text-start">{{ item.idDireccion }}</td>
                    <td class="text-start">{{ item.estado }}</td>
                    <td class="text-start">{{ item.codigoPostal }}</td>
                    <td class="text-start">{{ item.colonia }}</td>
                    <td class="text-start">{{ item.calle }}</td>
                    <td class="text-start">{{ item.numero }}</td>
                    <td class="text-start">{{ item.referencias }}</td>
                    <td class="text-start">
                        <v-chip @click="changeStatus(item.active)" :color="item.active === true ? 'green' : 'red'"
                            outlined small>{{ item.active === true ? 'Activo' :
            "Inactivo" }}</v-chip>
                    </td>
                    <td class="text-start">{{ formatDateTime(item.ultimaModificacion) }}</td>
                    
                    <td class="text-center">
                        <v-icon color="blue" @click="editItem(item)">mdi-pencil</v-icon>
                        <v-icon color="red" @click="deleteItem(item.idPersonal)">mdi-delete</v-icon>
                    </td>
                </tr>
            </template>
        </v-data-table>
        <v-dialog v-model="editDialog" max-width="500px">
            <v-card>
                <v-card-title>
                    <span class="text-h5">Editar persona</span>
                </v-card-title>
                <v-card-text>
                    <v-container>
                        <v-row>
                            <v-col cols="12" sm="6" md="4">
                                <v-text-field v-model="editedItem.usuarios.nombres" label="Nombre"></v-text-field>
                            </v-col>
                            <v-col cols="12" sm="6" md="4">
                                <v-text-field v-model="editedItem.usuarios.primerApellido"
                                    label="Primer Apellido"></v-text-field>
                            </v-col>
                            <v-col cols="12" sm="6" md="4">
                                <v-text-field v-model="editedItem.usuarios.segundoApellido"
                                    label="Segundo Apellido"></v-text-field>
                            </v-col>
                            <v-col cols="12" sm="6" md="4">
                                <v-text-field v-model="editedItem.usuarios.telefono" label="Telefono"></v-text-field>
                            </v-col>
                            <v-col cols="12" sm="6" md="4">
                                <v-text-field v-model="editedItem.usuarios.correo" :rules="emailRules" label="Correo"
                                    required></v-text-field>
                            </v-col>
                            <v-col cols="12" sm="6" md="4">
                                <v-select label="Cargo" v-model="editedItem.categoria.idCategoria"
                                    :items="categoriasPersonal" item-text="nombre" item-value="idCategoria">
                                </v-select>
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
import direccionesService from '../../../../services/DireccionesService'
import personalServices from "@/services/PersonalServices";
import swalService from "@/services/SwalService";
import categoriasPersonal from "@/services/CategoriasPersonal";
import moment from 'moment';
export default {
    data() {
        return {
            direcciones: [],
            categoriasPersonal: [],
            search: '',
            dialog: false,
            editDialog: false,
            valid: false,
            items: [
                { text: 'Personal', disabled: false, href: '/admin/personal' },
                { text: 'Direcciones', disabled: false, href: '/admin/direeciones' },
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
                { text: '#', align: 'start', sortable: true, value: 'idPersonal' },
                { text: 'Estado', align: 'start', sortable: false, value: 'nombres' },
                { text: 'Codigo Postal', align: 'start', sortable: false, value: 'pApellido' },
                { text: 'Colonia', align: 'start', sortable: false, value: 'sApellido' },
                { text: 'Calle', align: 'start', sortable: false, value: 'telefono' },
                { text: 'Numero', align: 'start', sortable: false, value: 'correo' },
                { text: 'Referencias', align: 'start', sortable: false, value: 'role' },
                { text: 'Status', align: 'start', sortable: false, value: 'cargo' },
                { text: 'Ultima Modificación', align: 'start', sortable: false, value: 'ultimaModificacion' },
                { text: 'Acciones', align: 'center', sortable: false, value: 'acciones' },
            ],

            nameRules: [
                v => !!v || 'El nombre es requerido',
                v => v.length <= 10 || 'Name must be less than 10 characters',
            ],
            email: '',
            emailRules: [
                v => !!v || 'El correo es requerido',
              (v) => /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(v) || "El correo tiene un formato incorrecto",
            ],
        };
    },
    mounted() {
        this.getDirecciones();
        this.getCategoriasPersonal();
    },
    methods: {
      formatDateTime(dateTimeString) {
        //mexico city time
        return moment(dateTimeString).format("YYYY-MM-DD HH:mm");
      },
        async getCategoriasPersonal() {
            try {
                const response = await categoriasPersonal.getCategoriasPersonalByStatus(true);
                console.log(response)
                this.categoriasPersonal = response;
            } catch (error) {
                console.log(error)
            }
        },
        async getDirecciones() {
            try {
                const response = await direccionesService.getDirecciones();
                console.log(response)
                this.direcciones = response;
            } catch (error) {
                console.error(error);
            }
        },
        editItem(item) {
            console.log(item)
            this.editedItem = { ...item };
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
        saveEdit()
        {
          try {
            personalServices.update(this.editedItem);
          } catch (error) {
            console.log(error)
          } finally {
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
          }

        }
,
      deleteItem: async function (idPersonal) {
        let proceder = await swalService.confirmationWarning(
            "¿Estás seguro de eliminar el registro?",
        );
        if (proceder) {
          await personalServices.delete_(idPersonal);
          this.getPersonal();
        }
      }
      ,
      changeStatus: function (idPersonal) {
        personalServices.changeStatus(idPersonal);
      }
      ,
        closeModalAddPersonal() {
            this.dialog = false;
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
                await personalServices.insert(this.nuevoUsuario);
            } catch (error) {
            } finally {
                this.resetNuevoPersona();
                this.closeModalAddPersonal();
                this.getPersonal()
            }
        }


    }
}
</script>