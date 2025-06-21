<template>
  <div style="height: 2000px" class="content">
    <h1>Scroll hacia abajo para ver el efecto</h1>
    <p>Contenido de ejemplo...</p>
    <p v-show="showElement">
      Este elemento se desaparecerá al hacer scroll hacia abajo.
    </p>
  </div>
</template>

<script>
export default {
  data() {
    return {
      showElement: true,

      lastScrollPosition: 0
    };
  },
  mounted() {
    window.addEventListener("scroll", this.onScroll);
  },
  beforeDestroy() {
    window.removeEventListener("scroll", this.onScroll);
  },

  methods: {
    onScroll() {

      // Obtiene la posición actual del scroll
      const currentScrollPosition = window.pageYOffset || document.documentElement.scrollTop;
        console.log(currentScrollPosition);
      
      // La función abs para tener valores absolutos y se delimita con un offset o bien llamado 
      // margen para que el valor de la posición sea después de desplazarce y no desde que uno se desplaza
      if (Math.abs(currentScrollPosition - this.lastScrollPosition) < 60) {
        return;
      }
      // aqui determinamos si la posición es mayor a la posición anterior. Entonces, si lo es, mostramos el elemento.
      this.showElement = currentScrollPosition < this.lastScrollPosition;
      //  
      this.lastScrollPosition = currentScrollPosition;
    },
  },
};
</script>

<style>
.hidden {
  display: none;
}
</style>