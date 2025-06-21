<template>
    <div
        ref="container"
        class="frc-captcha"
        data-sitekey="FCMJTM9F6S3ERAC7"
        data-lang="es"
    ></div>
</template>


<script>
import { WidgetInstance } from "friendly-challenge";

import { ref } from "vue";

export default {
  data() {
    return {
      container: ref(),
      widget: ref(),
    };
  },

  methods: {
    async verifyCaptcha(solution) {
      console.log("Captcha solution: ", solution);
      this.$emit('solucion', solution);
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
      this.widget = new WidgetInstance(
          this.$refs.container, {
            startMode: "",
            doneCallback: this.doneCallback,
            errorCallback: this.errorCallback,
          }
      );
    }
  },

  beforeDestroy() {
    if (this.widget) {
      this.widget.destroy();
    }
  },
};
</script>
