<template>
  <div>
    <div class="v-sheet-container">
      <v-sheet :elevation="5" border rounded class="half-sheet" color="#F1F1F1">
        <div class="title flex justify-end text-2xl font-bold py-4">
          <h1 class="flex-grow text-center">Mi perfil</h1>
          <button
              @click="openCloseDialogCambio"
              class="block px-4 py-2 text-sm text-white bg-blue-500 hover:bg-blue-700 rounded mr-4"
              role="menuitem"
              tabindex="-1"
              id="user-menu-item-1"
          >
            <v-icon>mdi-lock-reset</v-icon>
            Cambiar contraseña
          </button>
          <button
              @click="signOut"
              class="block px-4 py-2 text-sm text-white bg-red-500 hover:bg-red-700 rounded"
              role="menuitem"
              tabindex="-1"
              id="user-menu-item-1"
          >
            <v-icon dark left color="white"> mdi-logout</v-icon>
            Cerrar sesion
          </button>
        </div>
        <v-divider
            :thickness="2"
            class="border-black border-opacity-50 my-4"
        ></v-divider>
        <div class="InfoCont">
          <br/>
          <v-form
              class="space-y-4 margin"
              v-model="validUpdateUser"
              ref="formUpdateUser"
              @submit.prevent="updateUser"
          >
            <v-text-field
                class="value"
                v-model="usuario.nombres"
                outlined
                dense
                label="Nombres"
                required
                :rules="[
                (v) => !!v || 'Nombres es requerido',
                (v) => v.length <= 30 && v.length >= 3 || 'El nombre debe tener entre 3 y 30 caracteres',
              ]"
            ></v-text-field>
            <v-divider
                :thickness="7"
                color="black"
                class="border-opacity-75"
            ></v-divider>
            <v-text-field
                class="value"
                v-model="usuario.primerApellido"
                outlined
                dense
                label="Primer apellido"
                required
                :rules="[
                (v) => !!v || 'Primer apellido es requerido',
                (v) => v.length <= 30 && v.length >= 3 || 'El apellido debe tener entre 3 y 30 caracteres',
              ]"
            ></v-text-field>
            <v-divider
                :thickness="7"
                color="black"
                class="border-opacity-75"
            ></v-divider>
            <v-text-field
                class="value"
                v-model="usuario.segundoApellido"
                outlined
                dense
                label="Segundo apellido"
                required
                :rules="[
                (v) => !!v || 'Segundo apellido es requerido',
                (v) => v.length <= 30 && v.length >= 3 || 'El apellido debe tener entre 3 y 30 caracteres',
              ]"
            ></v-text-field>
            <v-divider
                :thickness="7"
                color="black"
                class="border-opacity-75"
            ></v-divider>
            <v-text-field
                class="value"
                v-model="usuario.telefono"
                outlined
                dense
                label="Teléfono"
                required
                :rules="[
                (v) => !!v || 'Teléfono es requerido',
                (v) => /^[0-9]{10}$/.test(v) || 'Teléfono no es válido',
              ]"
            ></v-text-field>
            <v-divider
                :thickness="7"
                color="black"
                class="border-opacity-75"
            ></v-divider>
            <div class="actions">
              <v-btn icon color="blue" type="submit">
                <v-icon>mdi-pencil</v-icon>
              </v-btn>
            </div>
          </v-form>
        </div>
        <DialogCambiarContra :visible="dialogCambio" :openClose="openCloseDialogCambio"/>
      </v-sheet>
    </div>

    <footer class="footer">
      <p>
        Tus datos en Foodster son completamente privados y seguros. Respetamos
        tu privacidad en todo momento.
      </p>
      <p>© 2024 Foodster. Todos los derechos reservados.</p>
    </footer>
  </div>
</template>

<script>
import {useAuthStore} from "@/stores";
import UsersServices from "@/services/UsersServices";
import DialogCambiarContra from "@/components/public/DialogCambiarContra.vue";

export default {
  components: {DialogCambiarContra},
  data() {
    return {
      dialogCambio: false,
      validUpdateUser: true,
      validUpdateDireccion: true,
      dialog: false,
      usuario: {},
      direcciones: [],
      eventos: [],
      loading: false,
      servicios: [],
      nuevaDireccion: {
        calle: "", //
        colonia: "", //
        numero: "", //
        municipio: "",
        estado: "",
        codigoPostal: "",
        referencias: "", ///
      },
    };
  },
  methods: {
    openCloseDialogCambio() {
      this.dialogCambio = !this.dialogCambio;
    },

    signOut() {
      this.loading = true;
      const authStore = useAuthStore();
      authStore.logout();
      this.loading = false;
    },
    async getUserAndDirecciones() {
      this.loading = true;
      this.usuario = await UsersServices.getMyUser();
      this.loading = false;
    },
    async updateUser() {
      if (this.$refs.formUpdateUser.validate()) {
        this.loading = true;
        await UsersServices.update(this.usuario);
        this.loading = false;
      }
    },

  },

  watch: {
    dialog(val) {
      if (!val) {
        this.nuevaDireccion = {
          calle: "",
          colonia: "",
          numero: "",
          municipio: "",
          estado: "",
          codigoPostal: "",
          referencias: "",
        };
      }
    },
  },

  mounted() {
    this.getUserAndDirecciones();
  },
};
</script>

<style>
.v-sheet-container {
  display: flex;
  flex-direction: column;
  margin-top: 20px;
  align-items: center;
}

.half-sheet {
  padding: 20px;
  width: 85%;
  height: auto;
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 10px;
}

.footer {
  margin-top: 20px;
  padding: 10px;
  text-align: center;
}

.InfoCont {
  align-items: center;
  margin-top: 5vh;
  width: 70%;
  font-family: "Trebuchet MS", "Lucida Sans Unicode", "Lucida Grande",
  "Lucida Sans", Arial, sans-serif;
}

.label {
  width: 300px;
  display: inline-block;
  vertical-align: top;
  font-weight: bold;
  color: #121212;
  font-size: 1.2em;
  margin-top: 10px;
  margin-left: 10px;
}

.value {
  width: 95%;
  display: inline-block;
  vertical-align: top;
  color: #424242;
  font-size: 1.2em;
  margin-top: 10px;
  margin-left: 10px;
}

.title {
  width: 100%;
  align-items: center;
  color: #000000;
  margin-bottom: 10px;
}

.v-divider {
  opacity: 0.1;
  margin-bottom: 10px;
}

.actions {
  display: flex;
  justify-content: space-around;
  margin-top: 20px;
}

@media (max-width: 768px) {
  .v-sheet-container {
    flex-direction: column;
  }

  .half-sheet {
    width: 90%;
    height: auto;
    margin-bottom: 20px;
  }
}

/* END: Custom Styles */
</style>
