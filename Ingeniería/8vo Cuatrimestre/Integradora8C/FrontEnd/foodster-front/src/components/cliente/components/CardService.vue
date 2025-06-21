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

    <v-img height="150" :src="servicio.imagen"></v-img>
    <v-rating
        :value="promedio"
        color="amber"
        dense
        half-increments
        readonly
        size="12"
    ></v-rating>

    <v-card-title class="text-h6">{{ servicio.nombre }}</v-card-title>

    <v-card-text>
      <div class="my-2 text-caption">
        {{ servicio.categoria.nombre }}
      </div>

      <div class="text-caption">
        {{ servicio.descripcion }}
      </div>

      <div class="my-2 font-weight-black">
        <template v-if="servicio.precioDescuento > 0">
          <s>${{ servicio.precio.toFixed(2) }}</s>
          ${{ servicio.precioDescuento.toFixed(2) }}
        </template>
        <template v-else>
          ${{ servicio.precio.toFixed(2) }}
        </template>
      </div>
    </v-card-text>

    <v-divider class="mx-2"></v-divider>
    <v-btn
        color="deep-purple lighten-2"
        text
        @click="showCalificaciones"
    >
      Ver calificaciones
    </v-btn>
    <v-card-actions>
      <v-btn
          v-if="!inCart"
          color="deep-purple lighten-2"
          text
          @click="agregarElemento(servicio)"
      >
        Agregar al carrito
      </v-btn>
      <div v-else>Ya en carrito</div>
    </v-card-actions>
    <v-dialog
        v-model="showDialog"
        class="mx-auto"
    >
      <v-card>
        <v-card-title class="headline">
          Calificaciones
        </v-card-title>

        <v-card-text>
          <v-list>
            <v-list-item-title>
              Usuario de foodster
            </v-list-item-title>
            <v-list-item
                v-for="(calificacion, index) in calificaciones"
                :key="index"
            >
              <v-list-item-content>
                <v-rating
                    :value="calificacion.calificacion"
                    color="amber"
                    dense
                    half-increments
                    readonly
                    size="12"
                ></v-rating>
                <v-list-item-title>
                  {{ calificacion.comentario }}
                </v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </v-list>
        </v-card-text>
        <v-pagination
            v-model="currentPage"
            :length="totalPages"
            :total-visible="5"
            @input="fetchCalificaciones"
            class="my-4 w-11/12 mx-auto"
        ></v-pagination>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
              color="deep-purple lighten-2"
              text
              @click="showDialog = false"
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
import {avgCalifServicio, getCalificacionesPaginadasByServicio} from "@/services/CalificationService";

export default {
  data: () => ({
    loading: false,
    selection: 1,
    show: false,
    showDialog: false,
    inCart: false,
    promedio: 0,
    calificaciones: [],
    currentPage: 1,
    totalPages: 0,
    itemsPerPage: 10,
  }),

  props: {
    servicio: {
      type: Object,
    },
  },
  mounted() {
    this.setIsInCart();
    this.getPromedio();
  },


  methods: {
    async showCalificaciones() {
      this.showDialog = true;
      await this.fetchCalificaciones();
    },

    async getPromedio() {
      this.promedio = await avgCalifServicio(this.servicio.idServicio)
    },

    async fetchCalificaciones() {
      this.loading = true;
      const response = await getCalificacionesPaginadasByServicio(this.servicio.idServicio, this.currentPage - 1, this.itemsPerPage);
      if (response) {
        this.totalPages = response.totalPages;
        this.calificaciones = response.content;
      } else {
        this.totalPages = 0;
        this.calificaciones = [];
        this.currentPage = 1;
      }
      this.loading = false;
    },

    agregarElemento(item) {
      const cart = useCartStore();
      this.loading = true;
      cart.addStuff(item);
      this.setIsInCart();
      this.loading = false;
    },
    setIsInCart() {
      const cart = useCartStore();
      this.inCart = cart.isInCart(this.$props.servicio.idServicio);
    },
  },
};
</script>
