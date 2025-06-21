<template>
  <v-container>
    <v-row>
      <v-col cols="12" sm="6">
        <v-card
            v-for="(item, index) in servicios"
            :key="index"
            class="pa-2"
            draggable="true"
            outlined
            tile
            @dragstart="dragStart(index, $event)"
        >
          <CardService :servicio="item"/>
        </v-card>
        <v-pagination
            v-model="currentPage"
            :length="totalPages"
            :total-visible="5"
            class="my-4 w-11/12 mx-auto"
            @input="fetchServices"
        ></v-pagination>
      </v-col>
      <v-col cols="12" sm="6">
        <v-card
            class="pa-2"
            color="grey lighten-3"
            @drop="drop($event)"
            @dragover.prevent
        ><h3>Coloca aqui con drag and drop</h3>
          <v-card-title>

            <h2>Paquete: {{ paquete.nombre }}</h2>
          </v-card-title>
          <v-card
              v-for="(servicio, index) in serviciosPaquete"
              :key="index"
              class="mb-2"
          >
            <CardService :servicio="servicio"/>
            <v-icon
                color="red"
                @click="deleteItemServicioPaquete(servicio)"
            >mdi-delete
            </v-icon>
          </v-card>
        </v-card>
      </v-col>
    </v-row>
    <v-btn @click="close(paquete.idPaquete)">Cerrar</v-btn>
  </v-container>
</template>

<script>
import CardService from "@/components/admin/paquete/CardService.vue";
import {
  createServicioPaquete,
  deleteServicioPaquete,
  getAllServiciosPaginado,
  getServiciosByPaquete
} from "@/services/ServicesServices";
import {showNotification} from "@/utils/notification";
import swalService from "@/services/SwalService";

export default {
  components: {CardService},
  data: () => ({
    servicios: [],
    serviciosPaquete: [],
    draggedItem: null,
    loading: false,
    currentPage: 1,
    totalPages: 0,
    itemsPerPage: 10,

  }),
  props: {
    paquete: {
      type: Object,
    },
    close: {
      type: Function,
    },
  },
  methods: {
    async deleteItemServicioPaquete(servicio) {
      try {
        let proceder = await swalService.confirmationWarning(
            "¿Estás seguro de eliminar el servicio?"
        );
        if (proceder) {
          await deleteServicioPaquete(servicio.idServicioPaquete);
          await this.fetchServiciosPaquete();
          await this.fetchServices();
        }
      } catch (error) {
        console.error("Error al eliminar categoría de servicio:", error);
      }
    },

    async fetchServices() {
      this.loading = true;
      const response = await getAllServiciosPaginado(this.currentPage - 1, this.itemsPerPage);
      if (response) {
        this.totalPages = response.totalPages;
        this.servicios = response.content;
      } else {
        this.totalPages = 0;
        this.servicios = [];
        this.currentPage = 1;
      }
      this.loading = false;
    },
    async fetchServiciosPaquete() {
      this.loading = true;
      this.serviciosPaquete = [];
      const response = await getServiciosByPaquete(this.paquete.idPaquete);
      if (response) {
        response.forEach(serviciosPaquete => {
          this.serviciosPaquete.push({
            ...serviciosPaquete.servicio,
            idServicioPaquete: serviciosPaquete.idServicioPaquete
          });
        });
      } else {
        this.serviciosPaquete = [];
      }
      this.loading = false;
    },
    dragStart(index, event) {
      this.draggedItem = this.servicios[index];
      event.dataTransfer.setData('text/plain', this.draggedItem);
    },
    async drop(event) {
      event.preventDefault();
      if (this.draggedItem && !this.serviciosPaquete.some((item) => item.idServicio === this.draggedItem.idServicio)) {
        this.serviciosPaquete.push(this.draggedItem);
        let servicioPaquete = {
          paquete: {
            idPaquete: this.paquete.idPaquete,
          },
          servicio: {
            idServicio: this.draggedItem.idServicio,
          },
          active: true,
        }
        await createServicioPaquete(servicioPaquete);
      } else {
        showNotification("error", "El servicio ya se encuentra asignado al paquete.");
      }
    },
  },
  mounted() {
    this.fetchServices();
    this.fetchServiciosPaquete();
  },
};
</script>