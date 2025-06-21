<template>
  <v-card
      :loading="loading"
      class="mx-auto my-4 animate__animated animate__rubberBand"
      max-width="300"
  >
    <template slot="progress">
      <v-progress-linear
          color="blue"
          height="10"
          indeterminate
      ></v-progress-linear>
    </template>

    <v-img
        :src="paquete.imagen"
        height="150"
    ></v-img>

    <v-card-title class="text-h6">{{ paquete.nombre }}</v-card-title>

    <v-card-text>
      <div class="my-2 font-weight-black">
        Numero de pedidos:
        {{ paquete.numeroPedidos }}
      </div>
      <div class="my-2 text-caption">
        Recomendado para {{ paquete.recomendadoPara }}
      </div>


      <div class="text-caption">
        {{ paquete.descripcion }}
      </div>

    </v-card-text>

    <v-divider class="mx-2"></v-divider>
    <v-btn
        color="deep-purple lighten-2"
        text
        @click="showElementos"
    >
      Ver elementos del paquete
    </v-btn>
    <v-card-actions>

      <v-btn
          color="deep-purple lighten-2"
          text
          @click="agregarPaquete"
      >
        Agregar al carrito
      </v-btn>
    </v-card-actions>
    <v-dialog
        v-model="show"
        class="w-1/4 overflow-auto mx-auto bg-white shadow-lg rounded-lg max-h-96"
        flat
        offset-y
    >
      <v-card class="p-6">
        <v-card-title class="text-xl font-bold text-gray-700">
          Elementos del paquete
        </v-card-title>
        <v-card-text class="mt-4">
          <v-list class="grid grid-cols-3 gap-4">
            <v-list-item
                v-for="elemento in elementosDelPaquete"
                :key="elemento.idServicio"
                class="mb-2 last:mb-0"
            >
              <CardService
                  :servicio="elemento"
              />
            </v-list-item>
          </v-list>
        </v-card-text>
        <v-card-actions class="mt-4">
          <v-btn
              class="px-4 py-2"
              color="blue"
              text
              @click="closeDialog"
          >
            Cerrar
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-card>


</template>

<script>
import {useCartStore} from "@/stores/cart.store";
import {getServiciosByPaquete} from "@/services/ServicesServices";
import CardService from "@/components/cliente/components/CardService.vue";

export default {
  components: {CardService},
  data: () => ({
    loading: false,
    selection: 1,
    show: false,
    elementosDelPaquete: [],
  }),

  props: {
    paquete: {
      type: Object,
    }
  },

  methods: {
    async agregarPaquete() {
      this.loading = true
      const cart = useCartStore();
      await cart.setPaquete(this.paquete.idPaquete);
      this.loading = false
    },
    async loadElementos() {
      this.loading = true
      const response = await getServiciosByPaquete(this.paquete.idPaquete);
      if (response) {
        response.forEach(serviciosPaquete => {
          this.elementosDelPaquete.push(serviciosPaquete.servicio);
        });
      } else {
        this.elementosDelPaquete = [];
      }
      this.loading = false
    },
    async showElementos() {
      await this.loadElementos();
      this.show = true;
    },
    async closeDialog() {
      this.elementosDelPaquete = [];
      this.show = false;
    }
  },
}
</script>
