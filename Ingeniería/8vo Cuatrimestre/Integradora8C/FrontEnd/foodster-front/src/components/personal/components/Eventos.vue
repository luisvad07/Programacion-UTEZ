<template>
  <v-row class="fill-height">
    <v-col>
      <v-sheet height="64">
        <v-toolbar
            flat
        >
          <v-btn
              class="me-4"
              color="secondary"

              variant="outlined"
              @click="setToday"
          >
            Hoy
          </v-btn>
          <v-btn
              color="secondary"
              size="small"
              class="mr-2"
              variant="text"
              fab
              @click="prev"
          >
            <v-icon size="small">
              mdi-chevron-left
            </v-icon>
          </v-btn>
          <v-btn
              color="secondary"
              size="small"
              class="mr-2"
              variant="text"
              fab
              @click="next"
          >
            <v-icon size="small">
              mdi-chevron-right
            </v-icon>
          </v-btn>
          <v-toolbar-title v-if="calendar">
            {{ calendar.title }}
          </v-toolbar-title>
          <v-spacer></v-spacer>
          <v-menu location="bottom end">
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                  color="secondary"
                  variant="outlined"
                  v-bind="attrs"
                  v-on="on"
              >
                <span>{{ typeToLabel[type] }}</span>
                <v-icon end>
                  mdi-menu-down
                </v-icon>
              </v-btn>
            </template>
            <v-list>
              <v-list-item @click="type = 'day'">
                <v-list-item-title>Dia</v-list-item-title>
              </v-list-item>
              <v-list-item @click="type = 'week'">
                <v-list-item-title>Semana</v-list-item-title>
              </v-list-item>
              <v-list-item @click="type = 'month'">
                <v-list-item-title>Mes</v-list-item-title>
              </v-list-item>
            </v-list>
          </v-menu>
        </v-toolbar>
      </v-sheet>
      <v-sheet height="600">
        <v-calendar
            locale="es-MX"
            ref="calendar"
            v-model="focus"
            :events="eventos"
            :type="type"
            color="primary"
            @click:date="viewDay"
            @click:event="showEvent"
            @click:more="viewDay"
        ></v-calendar>
        <v-dialog
            max-width="500px"
            v-model="selectedOpen"
            :activator="selectedElement"
            :close-on-content-click="false"
        >
          <v-card
              class="w-full md:min-w-350px overflow-auto max-w-350px"
              flat
              v-if="selectedEvent.details"
          >
            <v-toolbar
                :color="selectedEvent.color"
                dark
            >
              <v-toolbar-title v-if="selectedEvent.name">
                {{ selectedEvent.name }}
              </v-toolbar-title>
              <v-spacer></v-spacer>
              <v-btn icon @click="closeModal">
                <v-icon>mdi-close</v-icon>
              </v-btn>
            </v-toolbar>
            <v-card-text>
              <div>
                <v-icon left>mdi-check</v-icon>
                <span>Estado: </span>
                <span>{{ selectedEvent.details.estado }}</span>
              </div>

              <div>
                <v-icon left>mdi-calendar</v-icon>
                <span>Fecha y Hora de Inicio: </span>
                <span>{{ formatDateTime(selectedEvent.start) }}</span>
              </div>
              <div>
                <v-icon left>mdi-calendar</v-icon>
                <span>Fecha y Hora de Fin: </span>
                <span>{{ formatDateTime(selectedEvent.end) }}</span>
              </div>
              <div>
                <v-icon left>mdi-account</v-icon>
                <span>Nombres: </span>
                <span>{{ selectedEvent.details.usuario.nombres }}</span>
              </div>
              <div>
                <v-icon left>mdi-account</v-icon>
                <span>Apellidos: </span>
                <span>{{
                    selectedEvent.details.usuario.primerApellido
                  }} {{ selectedEvent.details.usuario.segundoApellido }}</span>
              </div>
              <div>
                <v-icon left>mdi-phone</v-icon>
                <span>Telefono: </span>
                <span>{{ selectedEvent.details.usuario.telefono }}</span>
              </div>
              <div>
                <v-icon left>mdi-email</v-icon>
                <span>Correo: </span>
                <span>{{ selectedEvent.details.usuario.correo }}</span>
                <div>
                  <span>
                  <v-icon>mdi-map-marker</v-icon>
                  <span class="value">
                  Direccion:
                  </span>
                  <span>
                    Calle: {{ selectedEvent.details.direccion.calle }}, Col:
                    {{ selectedEvent.details.direccion.colonia }}, No°:
                    {{ selectedEvent.details.direccion.numero }}, CP:
                    {{ selectedEvent.details.direccion.codigoPostal }}, Municipio:
                    {{ selectedEvent.details.direccion.municipio }}, Estado:
                    {{ selectedEvent.details.direccion.estado }}
                  </span>
                </span>
                </div>
                <div>
                  <v-icon left>mdi-map-marker</v-icon>
                  <span class="value">
                  Referencias: {{ selectedEvent.details.direccion.referencias }}
                </span>
                </div>
              </div>
              <div class="mt-4">
                <h4>Servicios del evento:</h4>
                <v-expansion-panels>
                  <v-expansion-panel
                      v-for="(servicio, index) in selectedEvent.details.servicios"
                      :key="index"
                  >
                    <v-expansion-panel-header>
                      {{ servicio.servicio.nombre }}
                    </v-expansion-panel-header>
                    <v-expansion-panel-content>
                      <div class="flex justify-center items-center">
                        <img
                            :src="servicio.servicio.imagen"
                            alt="Servicio"
                            class="w-1/2 h-1/2"
                        />
                      </div>
                      <p>
                        <v-icon small class="mr-2">mdi-cube-outline</v-icon>
                        Cantidad: {{ servicio.cantidad }}
                      </p>
                      <p>
                        <v-icon small class="mr-2">mdi-information-outline</v-icon>
                        Descripción: {{ servicio.servicio.descripcion }}
                      </p>
                      <p>
                        <v-icon small class="mr-2">mdi-cash</v-icon>
                        Precio: {{ servicio.servicio.precio.toFixed(2) }}
                      </p>
                      <p>
                        <v-icon small class="mr-2">mdi-tag-outline</v-icon>
                        Precio con descuento: {{ servicio.servicio.precioDescuento }}
                      </p>
                      <p>
                        <v-icon small class="mr-2">mdi-archive-outline</v-icon>
                        Existencias: {{ servicio.servicio.existencias }}
                      </p>
                      <p>
                        <v-icon small class="mr-2">mdi-folder-outline</v-icon>
                        Categoría: {{ servicio.servicio.categoria.nombre }}
                      </p>
                    </v-expansion-panel-content>
                  </v-expansion-panel>
                </v-expansion-panels>
              </div>
              <div class="mt-4">
                <h4>Personal del evento:</h4>
                <v-expansion-panels>
                  <v-expansion-panel
                      v-for="(personal, index) in selectedEvent.details.personal"
                      :key="index"
                  >
                    <v-expansion-panel-header>
                      {{ personal.personal.usuarios.nombres }}
                    </v-expansion-panel-header>
                    <v-expansion-panel-content>
                      <p>
                        <v-icon small class="mr-2">mdi-account-outline</v-icon>
                        Nombre: {{ personal.personal.usuarios.nombres }} {{ personal.personal.usuarios.primerApellido }}
                        {{ personal.personal.usuarios.segundoApellido }}
                      </p>
                      <p>
                        <v-icon small class="mr-2">mdi-account-outline</v-icon>
                        Teléfono: {{ personal.personal.usuarios.telefono }}
                      </p>
                      <p>
                        <v-icon small class="mr-2">mdi-account-outline</v-icon>
                        Correo: {{ personal.personal.usuarios.correo }}
                      </p>
                      <p>
                        <v-icon small class="mr-2">mdi-account-outline</v-icon>
                        Cargo: {{ personal.personal.categoria.nombre }}
                      </p>
                    </v-expansion-panel-content>
                  </v-expansion-panel>
                </v-expansion-panels>
              </div>


            </v-card-text>
            <v-card-actions>
              <v-btn
                  color="secondary"
                  variant="text"
                  @click="closeModal"
              >
                Cerrar
              </v-btn>
              <v-btn
                  v-if="personal.categoria.nombre === 'Chef' && selectedEvent.details.estado === 'En proceso'"
                  color="secondary"
                  variant="text"
                  @click="setFinalizado(selectedEvent.details)"
              >
                Finalizar
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-sheet>
    </v-col>
  </v-row>
</template>

<script setup>
import {onMounted, ref} from 'vue'

const calendar = ref()

const typeToLabel = {
  month: 'Mes',
  week: 'Semana',
  day: 'Dia',

}

const focus = ref('')
const type = ref('month')
const selectedElement = ref(null)
const events = ref([])

onMounted(() => {
  calendar.value.checkChange()
})

function viewDay({date}) {
  focus.value = date
  type.value = 'day'
}

function setToday() {
  focus.value = ''
}

function prev() {
  calendar.value.prev()
}

function next() {
  calendar.value.next()
}


</script>

<script>
import {getEventosByPersonalIdUsuario, setFinalizarEvento} from "@/services/EventosServices";
import {useAuthStore} from "@/stores";
import moment from "moment/moment";
import ServicioEventoService from "@/services/ServicioEventoService";
import PersonalServices from "@/services/PersonalServices";

export default {
  data: () => ({
    focus: '',
    type: 'month',
    typeToLabel: {
      month: 'Mes',
      week: 'Semana',
      day: 'Dia',

    },
    personal: {},
    selectedEvent: {},
    selectedElement: null,
    selectedOpen: false,
    eventos: [],
    colors: ['blue', 'indigo', 'deep-purple', 'cyan', 'green', 'orange', 'grey darken-1'],
  }),
  mounted() {
    this.$refs.calendar.checkChange()
    this.loadPersonal()
  },
  methods: {
    showEvent({nativeEvent, event}) {
      const open = () => {
        this.selectedEvent = event
        this.selectedElement = nativeEvent.target
        requestAnimationFrame(() => requestAnimationFrame(() => this.selectedOpen = true))
      }
      if (this.selectedOpen) {
        this.selectedOpen = false
        requestAnimationFrame(() => requestAnimationFrame(() => open()))
      } else {
        open()
      }
      nativeEvent.stopPropagation()
    },
    formatDateTime(dateTimeString) {
      //mexico city time
      return moment(dateTimeString).format("YYYY-MM-DD HH:mm");
    },
    viewDay({date}) {
      this.focus = date
      this.type = 'day'
    },
    getEventColor(event) {
      return event.color
    },
    setToday() {
      this.focus = ''
    },
    prev() {
      this.$refs.calendar.prev()
    },
    next() {
      this.$refs.calendar.next()
    },
    convertToEvents(json) {
      return json.map(evento => ({
        name: evento.usuario.nombres,
        start: new Date(evento.fechaHoraInicio),
        end: new Date(evento.fechaHoraFin),
        color: 'secondary',
        timed: true,
        details: evento,
      }));
    },
    closeModal() {
      this.selectedOpen = false
    },
    loadPersonal() {
      let user = useAuthStore().user
      this.personal = user.personal ? user.personal : {}
      if (Object.keys(this.personal).length === 0) {
        useAuthStore().logout()
      } else {
        this.fetchEvents()
      }
    },


    async setFinalizado(evento) {
      try {

        await setFinalizarEvento(evento.idEvento)
        this.eventos = []
        await this.fetchEvents()
        this.closeModal()
      } catch (e) {
        console.log(e)
      }
    },

    async fetchEvents() {
      try {
        let eventos = await getEventosByPersonalIdUsuario()
        for (const evento of eventos) {
          evento.servicios = await ServicioEventoService.getServiciosEventoByEvento(evento.idEvento);
          evento.personal = await PersonalServices.getPersonalEvento(evento.idEvento);
        }
        this.eventos = this.convertToEvents(eventos)
      } catch (e) {
        console.log(e)
      }
    },
  },
}
</script>