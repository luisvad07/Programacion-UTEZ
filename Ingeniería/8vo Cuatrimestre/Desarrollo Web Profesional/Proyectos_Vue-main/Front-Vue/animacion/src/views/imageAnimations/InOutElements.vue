<template>
  <div>
    <button @click="toggle">Mostrar/Ocultar</button>
    <transition name="fade">
      <p v-if="show">Este es un elemento con animación de entrada y salida</p>
    </transition>
  </div>
</template>

<script>
export default {
  data() {
    return {
      show: false,
    };
  },
  methods: {
    toggle() {
      this.show = !this.show;
    },
  },
};
</script>

<style>
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s;
}
.fade-enter,
.fade-leave-to {
  opacity: 0;
}
</style>
