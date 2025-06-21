<template>
  <div class="landing-page">
    <div class="carousel-container animate__animated animate__fadeInLeft">
      <v-carousel cycle>
        <v-carousel-item v-for="(item, i) in items" :key="i" :src="item.src" reverse-transition="fade-transition"
          transition="fade-transition"></v-carousel-item>
      </v-carousel>
    </div>

    <!-- Main Content -->
    <div class="main-content">
      <div class="container mx-auto text-center mt-3">
        <h2 class="text-3xl font-bold mb-4 text-white">Bienvenido a FOODSTER</h2>
        <p class="text-lg mb-6 text-white">Descubre los mejores buffets cerca de ti.</p>
        <v-divider class="mb-6"></v-divider>
        <p class="text-lg text-white">Aquí encontrarás una amplia variedad de buffets para tus eventos especiales.</p>
      </div>

      <!-- CTA Section -->
      <div class="bg-gray-900 py-6">
        <div class="container mx-auto text-center">
          <h3 class="text-2xl font-semibold mb-4 text-white">¡Únete a Foodtser hoy mismo!</h3>
          <p class="text-lg mb-6 text-white">Regístrate ahora para acceder a los mejores buffets y ofertas especiales.</p>
          <v-btn href="/home/registro" class="mr-4">
            Registrarse
          </v-btn>
          <v-btn href="/home/login" outlined class="primary--text">
            Iniciar Sesión
          </v-btn>
        </div>
      </div>

      <!-- Testimonials Section -->
      <div class="container mx-auto py-12">
        <div class="text-center">
          <h3 class="text-3xl font-bold mb-4 text-white">Lo que nuestros clientes dicen</h3>
          <v-divider class="mb-6"></v-divider>
          <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-8">
            <!-- Testimonial Card -->
            <div class="bg-white p-6 rounded-lg shadow-lg">
              <p class="text-lg mb-4">"Foodster me ayudó a encontrar el buffet perfecto para mi fiesta de cumpleaños.
                ¡Altamente recomendado!"</p>
              <p class="font-semibold">- María Sánchez</p>
            </div>
            <!-- Testimonial Card -->
            <div class="bg-white p-6 rounded-lg shadow-lg">
              <p class="text-lg mb-4">"¡Increíble variedad de opciones! Encontré exactamente lo que necesitaba para mi
                evento corporativo."</p>
              <p class="font-semibold">- Juan Pérez</p>
            </div>
            <!-- Testimonial Card -->
            <div class="bg-white p-6 rounded-lg shadow-lg">
              <p class="text-lg mb-4">"La experiencia de usuario en Foodster es excepcional. ¡No podría estar más feliz
                con el servicio!"</p>
              <p class="font-semibold">- Ana Martínez</p>
            </div>
          </div>
        </div>
      </div>

       <!-- Footer -->
    <footer class="bg-gray-800 py-8">
      <div class="container mx-auto text-center text-white">
        <p class="text-lg font-semibold">Información de contacto:</p>
        <p class="mt-2">Dirección: Av. Plan de Ayala 123, Cuernavaca, Morelos</p>
        <p>Teléfono: (123) 456-7890</p>
        <p>Email: contacto@foodster.com</p>
      </div>
    </footer>
    </div>
  </div>
</template>

<script>
import banquete1 from '@/assets/images/eventos/banquete1.jpg';
import banquete2 from '@/assets/images/eventos/banquete2.jpg';
import banquete3 from '@/assets/images/eventos/banquete3.jpg';
import banquete4 from '@/assets/images/eventos/banquete4.jpg';
import banquete5 from '@/assets/images/eventos/banquete5.jpg';

export default {
  name: 'LandingPage',
  data() {
    return {
      items: [
        { src: banquete1 },
        { src: banquete2 },
        { src: banquete3 },
        { src: banquete4 },
        { src: banquete5 },
      ],
    };
  },
};
</script>

<style scoped>
.carousel-container {
  width: 100%;
  
}
.landing-page {
  /* Fondo con degradado */
  background: linear-gradient(135deg, #161b22, #03071E);
  /* Animación de movimiento */
  animation: gradientAnimation 10s linear infinite alternate;
}
.footer {
  background-color: #333; /* Color de fondo */
  color: #fff; /* Color de texto */
  padding: 20px; /* Espaciado interno */
}

.footer p {
  margin: 5px 0;
}

.footer .font-semibold {
  font-weight: 600; 
}
@keyframes gradientAnimation {
  0% {
    background-position: 0% 50%;
  }
  100% {
    background-position: 100% 50%;
  }
}

</style>
