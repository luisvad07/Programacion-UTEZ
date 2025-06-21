<template>
  <div class="flex flex-col justify-center items-center h-screen relative overflow-hidden">
    <div class="absolute inset-0 bg-gradient-to-r from-yellow-400 to-red-500 animate-gradient"></div>
    <div class="relative z-10 text-center">
      <h1 class="text-6xl font-bold text-white mb-4 drop-shadow-md sm:text-9xl">
        <span class="animate-bounce">4</span>
        <span class="animate-spin">🍽️</span>
        <span class="animate-bounce">4</span>
      </h1>
      <h2 class="text-3xl font-semibold text-white mb-4 drop-shadow-md sm:text-5xl">Recurso no encontrado</h2>
      <p class="text-lg text-white mb-8 drop-shadow-md sm:text-xl">
        Lo sentimos, la página que buscas no se encuentra. ¿Te apetece un platito mientras?
      </p>
      <div class="relative">
        <v-progress-circular
          :rotate="360"
          :size="100"
          :width="15"
          :value="value"
          color="yellow"
          aria-label="Cuenta regresiva"
          class="mb-4 transition-opacity duration-500"
          :class="{ 'opacity-0': value === 0 }"
        >
          {{ value }}
        </v-progress-circular>
        <transition name="fade">
          <div v-if="value === 0" class="absolute inset-0 flex items-center justify-center">
            <span class="text-lg text-white font-semibold drop-shadow-md">Redirigiendo...</span>
          </div>
        </transition>
      </div>
    </div>
   
  </div>
</template>

<script>
export default {
  data() {
    return {
      value: 5,
    };
  },
  mounted() {
    this.countdown();
  },
  methods: {
    countdown() {
      const interval = setInterval(() => {
        this.value--;
        if (this.value === 0) {
          clearInterval(interval);
          this.$router.push({ path: '/home' });
        }
      }, 1000);
    },
  },
};
</script>

<style scoped>
.animate-gradient {
  animation: gradient 10s ease infinite;
}

.animate-bounce {
  animation: bounce 1s infinite;
}

.animate-spin {
  animation: spin 2s linear infinite;
}

.animate-food-slide {
  animation: foodSlide 10s linear infinite;
}

@keyframes gradient {
  0% {
    background-position: 0% 50%;
  }
  50% {
    background-position: 100% 50%;
  }
  100% {
    background-position: 0% 50%;
  }
}

@keyframes bounce {
  0%, 100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-10px);
  }
}

@keyframes spin {
  from {
    transform: rotate(0deg);
  }
  to {
    transform: rotate(360deg);
  }
}



.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s;
}
.fade-enter,
.fade-leave-to {
  opacity: 0;
}
</style>