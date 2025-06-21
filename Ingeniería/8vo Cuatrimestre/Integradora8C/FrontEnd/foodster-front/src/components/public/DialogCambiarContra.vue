<template>
  <v-dialog v-model="visible" persistent max-width="600px">
    <template v-slot:activator="{ on, attrs }">
      <v-btn color="green" dark v-bind="attrs" v-on="on">
        <v-icon>mdi-plus</v-icon>
      </v-btn>
    </template>
    <v-form ref="form" @submit.prevent="cambiarContrasena">
      <v-card>
        <v-card-title>
          <span class="text-h5">Cambiar Contraseña</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="12" sm="6">
                <v-text-field
                    v-model="contrasena"
                    :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                    :rules="requiredRules"
                    :type="show1 ? 'text' : 'password'"
                    name="input-10-1"
                    label="Contraseña"
                    hint="La contraseña es requerida"
                    counter
                    @click:append="show1 = !show1"
                ></v-text-field>
              </v-col>

              <v-col cols="12" sm="6">
                <v-text-field
                    v-model="nuevaContrasena"
                    :append-icon="show2 ? 'mdi-eye' : 'mdi-eye-off'"
                    :rules="passwordRules"
                    :type="show2 ? 'text' : 'password'"
                    name="input-10-2"
                    label="Nueva Contraseña"
                    hint="La nueva contraseña es requerida"
                    counter
                    @click:append="show2 = !show2"
                ></v-text-field>

              </v-col>
              <v-col cols="12" sm="6">
                <v-text-field
                    v-model="confirmarContrasena"
                    :append-icon="show3 ? 'mdi-eye' : 'mdi-eye-off'"
                    :rules="confirmarContrasenaRules"
                    :type="show3 ? 'text' : 'password'"
                    name="input-10-3"
                    label="Confirmar Contraseña"
                    hint="La confirmación de la contraseña es requerida"
                    counter
                    @click:append="show3 = !show3"
                ></v-text-field>
              </v-col>


            </v-row>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="red" text @click="openClose"> Cancelar</v-btn>
          <v-btn
              color="green"
              text
              type="submit"
          >
            Guardar
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-form>
  </v-dialog>
</template>

<script>
import {useAuthStore} from "@/stores";
import UsersServices from "@/services/UsersServices";
import {showNotification} from "@/utils/notification";

export default {
  props: {
    visible: Boolean,
    openClose: Function,
  },
  data() {
    return {
      show1: false,
      show2: false,
      show3: false,
      contrasena: "",
      nuevaContrasena: "",
      confirmarContrasena: "",
      correo: "",
      passwordRules: [
        v => !!v || 'la contraseña es requerida',
        v => (v && v.length >= 8) || 'Minimo 8 caracteres',
        v => (v && /[A-Z]/.test(v)) || 'Al menos una letra mayúscula',
        v => (v && /[a-z]/.test(v)) || 'Al menos una letra minúscula',
        v => /\d/.test(v) || 'Al menos un número',
        v => (v && !/\s/.test(v)) || 'No espacios en blanco',
        v => (v && /[!@#$%^&*()_+\-=[\]{};':"\\|,.<>?]+/.test(v)) || 'Al menos un caracter especial',
      ],
      confirmarContrasenaRules: [
        (v) => !!v || "La confirmación de la contraseña es requerida",
        (v) => v === this.nuevaContrasena || "Las contraseñas no coinciden",
      ],
      requiredRules: [
        (v) => !!v || "Campo requerido",
      ],
    };
  },
  methods: {
    async cambiarContrasena() {
      if (this.$refs.form.validate()) {
        this.loading = true;
        try {
          let cambioDto = {
            correo: this.correo,
            contrasena: this.contrasena,
            nuevaContrasena: this.nuevaContrasena,
            confirmarContrasena: this.confirmarContrasena,
          };
          const response = await UsersServices.cambiarContrasena(cambioDto);
          if (response) {
            useAuthStore().logout();
          } else {
            showNotification("Error al cambiar la contraseña", "error")
          }
        } catch (e) {
          console.log(e);
        } finally {
          this.loading = false;
        }
      }
    },
    async loadCorreo() {
      this.correo = useAuthStore().user.usuarios.correo;
    }
  },
  mounted() {
    this.loadCorreo();
  },
};
</script>