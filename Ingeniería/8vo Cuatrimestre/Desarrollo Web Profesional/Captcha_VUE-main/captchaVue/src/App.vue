<template>
  <form @submit.prevent="submitForm">
    <label for="name"> Nombre:</label><br />
    <input type="text" id="name" v-model="formData.name" required /><br /><br />

    <div
      ref="container"
      class="frc-captcha"
      data-sitekey="FCMTF4B0MJJQGK1H"
      data-lang="es"
    ></div>

    <!-- Botón de envío -->
    <button type="submit">Enviar</button>
  </form>
</template>


<script>
import { WidgetInstance } from "friendly-challenge";

import { ref } from "vue";

import axios from "axios";

export default {
  data() {
    return {
      container: ref("container"),
      widget: ref(),
      formData: {
        name: "",
        captchaToken: null, // token generado por FriendlyCaptcha
      },
    };
  },

  methods: {
    //No comunicamos hacia
    //el back de nuestra aplicación.
    submitForm() {},
    async verifyCaptcha(solution) {
      try {
        axios.defaults.headers.common["Access-Control-Request-Method"] = "*";
        const response = await axios.post(
          "http://localhost:8080/api/captcha/verificar-captcha",
          {
            solution,
          }
        );
        this.respuestaCaptcha = response.data;
      } catch (error) {
        console.error("Error al verificar el captcha:", error);
      }
    },
    doneCallback(solution) {
      this.verifyCaptcha(solution);
    },

    errorCallback: (err) => {
      console.log("There was an error when trying to solve the Captcha.");
      console.log(err);
    },
  },

  mounted() {
    if (this.$refs.container) {
      this.widget = new WidgetInstance(this.$refs.container, {
        //startMode: "auto",
        doneCallback: this.doneCallback,
        errorCallback: this.errorCallback,
      });
    }
  },

  beforeDestroy() {
    if (this.widget) {
      this.widget.destroy();
    }
  },
};
</script>

<style></style>
