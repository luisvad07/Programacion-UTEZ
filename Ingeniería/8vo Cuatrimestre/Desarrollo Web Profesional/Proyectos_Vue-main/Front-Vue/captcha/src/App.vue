<script setup>
import { WidgetInstance } from 'friendly-challenge';
import { ref } from 'vue';
</script>

<template>
  <div id="app">
    <form @submit.prevent="submitForm">
      <label for="name">Nombre:</label>
      <input type="text" id="name" v-model="formData.name" required /> <br/><br/>
    </form>
    <div ref="container" class="frc-captcha" data-sitekey="FCMTF4B0MJJQGK1H"></div>
    <button type="submit">Enviar</button>
  </div>
</template>

<script>
import { WidgetInstance } from 'friendly-challenge';
import { ref } from 'vue';

export default{
  data(){
    return {
      container: ref("container"),
      widget: ref(),
      formData: {
        name: "",
        captchaToken: null,
      }
    };
  }
  
}
</script>