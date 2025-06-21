<template>
  <v-card>
    <v-card-title>
      Asignar personal a evento
      <v-divider class="mx-4" inset vertical></v-divider>
      <v-spacer></v-spacer>
      <v-text-field
        v-model="searchEventos"
        append-icon="mdi-magnify"
        label="Buscar"
        single-line
        hide-details
      ></v-text-field>
      <v-spacer></v-spacer>
    </v-card-title>

    <v-data-table
      class="mx-auto"
      style="height: auto; max-height: 500px; overflow-y: auto"
      :items-per-page-options="[5, 10, 15]"
      :headers="headers"
      :items="eventos"
      :server-items-length="totalItems"
      :items-per-page.sync="itemsPerPage"
      :loading="loading"
      :search="searchEventos"
      :page.sync="currentPage"
      @update:page="getAllEventos"
      @update:items-per-page="getAllEventos"
    >
      <template v-slot:item="{ item }">
        <tr>
          <td class="text-start">{{ item.numeroPersonas }}</td>
          <td class="text-start">{{ item.costoTotal }}</td>
          <td class="text-start">
            {{ formatDateTime(item.fechaHoraInicio) }}
          </td>
          <td class="text-start">
            {{ formatDateTime(item.fechaHoraFin) }}
          </td>
          <td class="text-start">{{ item.personalizado }}</td>
          <td class="text-start">{{item.estado}}</td>
          <td class="text-start">{{ formatDateTime(item.ultimaModificacion) }}</td>
          <td class="text-start">
            <v-chip :color="item.active ? 'green' : 'red'" outlined small>
              {{ item.active ? "Activo" : "Inactivo" }}
            </v-chip>
          </td>
          <td class="text-center">
            <v-icon color="red" @click="openAssignModal(item)">mdi-account-group</v-icon>
            <v-icon color="green" @click="openAssignNewPersonalDialog(item)">mdi-account-plus</v-icon>
          </td>
        </tr>
      </template>
    </v-data-table>

    <v-dialog v-model="assignModal" max-width="800px">
      <v-card>
        <v-card-title>Personal Asignado</v-card-title>
          <v-data-table
            :headers="personalHeaders"
            :items="personal"
            :items-per-page-options="[5, 10, 15]"
            class="elevation-1"
          >
            <template v-slot:item="{ item }">
              <tr>
                <td>{{ item.personal.usuarios.nombres }}</td>
                <td>{{ item.personal.usuarios.primerApellido }}</td>
                <td>{{ item.personal.usuarios.segundoApellido }}</td>
                <td class="text-center">
                  <v-icon color="red" @click="deletePersonalEvento(item.idPersonalEvento)">mdi-delete</v-icon>
                </td>
                
              </tr>
            </template>
          </v-data-table>
        <v-card-actions>
          <v-btn color="blue darken-1" text @click="assignModal = false">Cerrar</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <v-dialog v-model="assignNewPersonalDialog" max-width="800px">
      <v-card>
        <v-card-title>Asignar Nuevo Personal</v-card-title>
        <v-card-text>
          <v-data-table
            :headers="personalHeaders"
            :items="personalDisponibles"
            :items-per-page-options="[5, 10, 15]"
            class="elevation-1"
          >
            <template v-slot:item="{ item }">
              <tr>
                <td>{{ item.usuarios.nombres }}</td>
                <td>{{ item.usuarios.primerApellido }}</td>
                <td>{{ item.usuarios.segundoApellido }}</td>
                <td class="text-center">
                    <v-icon color="blue" @click="asignar(asigarEvento,item.idPersonal); personalDisponibles = personalDisponibles.filter(personal => personal.idPersonal !== item.idPersonal)">mdi-account-plus</v-icon>
                </td>
              </tr>
            </template>
          </v-data-table>
        </v-card-text>
        <v-card-actions>
          <v-btn color="blue darken-1" text @click="assignNewPersonalDialog = false">Cerrar</v-btn>
          <!-- Puedes agregar un botón para guardar la asignación de nuevo personal -->
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-card>
</template>

<script>
import { getAllPaginado } from "../../../services/EventosServices";
import moment from "moment";
import PersonalServices from "@/services/PersonalServices";

export default {
  data() {
    return {
      loading: false,
      searchEventos: "",
      headers: [
        { text: "Número de Personas", align: "start", sortable: false, value: "numeroPersonas" },
        { text: "Costo Total", align: "start", sortable: false, value: "costoTotal" },
        { text: "Fecha y Hora de Inicio", align: "start", sortable: false, value: "fechaHoraInicio" },
        { text: "Fecha y Hora de Fin", align: "start", sortable: false, value: "fechaHoraFin" },
        { text: "Personalizado", align: "start", sortable: false, value: "personalizado" },
        { text: "Estado", align: "start", sortable: false, value: "active" },
        { text: "Ultima Modificacion", align: "start", sortable: false, value: "ultimaModificacion" },
        { text: "Estado", align: "start", sortable: false, value: "active" },
        { text: "Acciones", align: "center", sortable: false, value: "acciones" },
      ],
      personalHeaders: [
        { text: "Nombres", align: "start", sortable: false, value: "nombre" },
        { text: "Primer Apellido", align: "start", sortable: false, value: "primerApellido" },
        { text: "Segundo Apellido", align: "start", sortable: false, value: "segundoApellido" },
        { text: "Acciones", align: "center", sortable: false, value: "acciones" }
        // Puedes agregar más campos del personal aquí
      ],
      eventos: [],
      personal: [], // Array para almacenar el personal asignado al evento
      personalDisponibles: [], // Array para almacenar el personal disponible para asignar al evento
      currentPage: 1,
      totalItems: 0,
      asigarEvento:"",
      totalPages: 0,
      itemsPerPage: 10,
      assignModal: false, // Variable para controlar la visibilidad del modal de asignación
      assignNewPersonalDialog: false, // Variable para controlar la visibilidad del modal de asignación de nuevo personal
    };
  },

  methods: {
    formatDateTime(dateTimeString) {
      //mexico city time
      return moment(dateTimeString).format("YYYY-MM-DD HH:mm");
    },

    async getAllEventos() {
      try {
        this.loading = true;
        const response = await getAllPaginado(this.currentPage - 1, this.itemsPerPage);
        if (response) {
          this.totalPages = response.totalPages;
          this.totalItems = response.totalElements;
          this.eventos = response.content;
          this.loading = false;
        } else {
          this.totalPages = 0;
          this.eventos = [];
          this.currentPage = 1;
          this.totalItems = 0;
          this.loading = false;
        }
      } catch (error) {
        console.error(error);
        this.loading = false;
      }
    },

    openAssignModal(item) {
      // Abre el modal para asignar personal al evento
      this.assignModal = true;
      // Obtén el personal asignado a este evento
      this.getPersonalEvento(item.idEvento);
    },

    async getPersonalEvento(idEvento) {
      try {
        this.loading = true;
        // Usa el método para obtener el personal asignado a este evento
        this.personal = await PersonalServices.getPersonalEvento(idEvento);
        console.log("Personal asignado al evento:", this.personal);
      } catch (error) {
        console.error("Error al obtener el personal asignado al evento:", error);
      } finally {
        this.loading = false;
      }
    },

    async deletePersonalEvento(idPersonalEvento) {
      try {
        this.loading = true;
        console.log("Eliminando personal asignado al evento con ID:", idPersonalEvento);
        const response = await PersonalServices.deletePersonalEvento(idPersonalEvento);
        if (response) {
          console.log("Personal eliminado del evento:", response);
          // Actualiza la lista de personal asignado al evento
          this.getPersonalEvento(response.idEvento);
        }
      } catch (error) {
        console.error("Error al eliminar el personal asignado al evento:", error);
      } finally {
        this.loading = false;
      }
    },

    async openAssignNewPersonalDialog(item) {
      try {
        // Abre el modal para asignar nuevo personal al event
        // Usa el método para obtener el personal disponible para asignar al evento
        this.personalDisponible(item);
        this.assignNewPersonalDialog = true;
      } catch (error) {
        console.error("Error al obtener el personal disponible para asignar al evento:", error);
      }
    },

    async personalDisponible(item){
      try{
        this.loading = true;
        this.personalDisponibles = await PersonalServices.getPersonalDisponible(moment(item.fechaHoraInicio).format("YYYY-MM-DD HH:mm:ss"), moment(item.fechaHoraFin).format("YYYY-MM-DD HH:mm:ss"));
        this.asigarEvento = item.idEvento;
        this.assignNewPersonalDialog = true;
      } catch (error) {
        console.error("Error al obtener el personal disponible para asignar al evento:", error);
      } finally {
        this.loading = false;
      }
    },

    async asignar(idEvento, idPersonal){
      const asignacion = {
        eventos:{
          idEvento: idEvento
        },
        personal:{
          idPersonal: idPersonal
        },
        active: true
      };
      console.log("Asignar personal al evento:", asignacion);

      try {
        this.loading = true;
        const response = await PersonalServices.asignarPersonalEvento(asignacion);
        if (response) {
          console.log("Personal asignado al evento:", response);
          // Actualiza la lista de personal asignado al evento
          this.getPersonalEvento(response.idEvento);
        }
      } catch (error) {
        console.error("Error al asignar el personal al evento:", error);
      } finally {
        this.loading = false;
      }
    }

  },
  mounted() {
    this.getAllEventos();
  },
  watch: {
    searchEventos: async function (val) {
      if(val){
        this.eventos = this.eventos.filter((evento) => {
          return evento.numeroPersonas.toString().toLowerCase().includes(val.toLowerCase()) ||
              evento.costoTotal.toString().toLowerCase().includes(val.toLowerCase()) ||
              evento.fechaHoraInicio.toString().toLowerCase().includes(val.toLowerCase()) ||
              evento.fechaHoraFin.toString().toLowerCase().includes(val.toLowerCase()) ||
              evento.personalizado.toString().toLowerCase().includes(val.toLowerCase()) ||
              evento.estado.toLowerCase().includes(val.toLowerCase());
        });
      }else {
        await this.getAllEventos();
      }
    },
  },
};

</script>
