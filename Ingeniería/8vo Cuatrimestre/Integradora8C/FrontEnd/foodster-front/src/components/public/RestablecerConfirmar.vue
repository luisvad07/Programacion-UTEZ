<template>
  <div class="flex h-screen">
    <div class="hidden lg:flex w-1/2 items-center justify-center bg-fdoscuro text-fdoscuro">

      <div class="max-w-md text-center">
        <img class="fixed left-[7%] top-[15%] logo" src="../../assets/images/logorealista.png" alt="" srcset=""/>
        <section class="rotate left-[7%] top-[15%]">
          <svg viewBox="0 0 100 100" class="">
            <path id="circlePath" fill="none" stroke-width="5" stroke="none" d="
          M 10, 50
          a 40,40 0 1,1 80,0
          a 40,40 0 1,1 -80,0
        "/>
            <text stroke="black" stroke-width="0.45" id="text" font-family="monospace" font-size="12.7"
                  font-weight="bolder" fill="#e2e2e2">
              <textPath id="textPath" href="#circlePath">
                🍱 Foodster 🍚 Foodster 🥗 Foodster
              </textPath>
            </text>
          </svg>
        </section>
      </div>
    </div>
    <!-- Right Pane -->
    <div class="w-full bg-gray-200 lg:w-1/2 flex items-center justify-center">

      <div class="max-w-md w-full p-6">
        <h1 class="text-3xl font-bold mb-6 text-black text-center lg:hidden">
          Foodster
        </h1>
        <h2 class="text-3xl font-semibold mb-6 text-black text-center sm:text-2xl lg:text-3xl">
          Recuperar Contraseña
        </h2>
        <h1 class="text-sm font-semibold mb-6 text-gray-500 text-center">
          Escribe tu nueva contraseña
        </h1>
        <v-form class="space-y-4" @submit.prevent="onSubmit" ref="form" v-model="valid">
          <div>
            <div>
              <v-text-field v-model="contrasenia" :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                            :rules="passwordRules" :type="show1 ? 'text' : 'password'" name="input-10-1"
                            label="Contraseña" hint="La contraseña es requerida" counter
                            @click:append="show1 = !show1"></v-text-field>
            </div>
            <div>
              <v-btn
                  :loading="loading"
                  type="submit"
                  color="secondary"
                  class="w-full bg-fdoscuro text-white p-2 rounded-md hover:bg-gray-800 focus:outline-none focus:bg-black focus:ring-2 focus:ring-offset-2 focus:ring-gray-900 transition-colors duration-300"
              >
                Cambiar Contraseña
              </v-btn>
            </div>
          </div>
        </v-form>
        <div class="mt-4 text-sm text-gray-600 text-center">
          <p>
            ¿Recuerdas tu contraseña?
            <a href="/home/login/" class="text-black hover:underline">Inicia Sesión..!</a>
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {confirmarCambio} from "@/services/RecuperarContraService";

export default {
  data() {
    return {
      valid: true,
      loading: false,
      token: '',
      contrasenia: '',
      passwordRules: [
        v => !!v || 'la contraseña es requerida',
        v => (v && v.length >= 8) || 'Minimo 8 caracteres',
        v => (v && /[A-Z]/.test(v)) || 'Al menos una letra mayúscula',
        v => (v && /[a-z]/.test(v)) || 'Al menos una letra minúscula',
        v => /\d/.test(v) || 'Al menos un número',
        v => (v && !/\s/.test(v)) || 'No espacios en blanco',
        v => (v && /[!@#$%^&*()_+\-=[\]{};':"\\|,.<>?]+/.test(v)) || 'Al menos un caracter especial',
      ],
      show1: false,
    };
  },
  methods: {
    async onSubmit() {
      if (this.$refs.form.validate()) {
        let restablecerDto = {
          token: this.token,
          contrasenia: this.contrasenia,
        };
        await confirmarCambio(restablecerDto);

      }
    },
  },

  mounted() {
    if (this.$route.query.token) {
      this.token = this.$route.query.token;
    } else {
      this.$router.push({name: 'login'});
    }
  },
};
</script>

<style scoped>
.rotate {
  position: fixed;
  width: 30rem;
  animation: spin 50s linear infinite;
  padding: 20px;
}

.logo {
  height: 30rem;
  width: 30rem;
}


@keyframes spin {
  0% {
    transform: rotate(0deg);
    /* Rotación inicial */
  }

  100% {
    transform: rotate(360deg);
    /* Rotación completa */
  }
}
</style>