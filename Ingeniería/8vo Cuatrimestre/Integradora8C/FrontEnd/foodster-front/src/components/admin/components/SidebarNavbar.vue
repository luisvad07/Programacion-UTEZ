<template>
  <div class="h-[100vh] flex">
    <div class="hidden w-27 bg-gray-900 overflow-y-auto md:block">
      <div class="w-full py-6 flex flex-col items-center">
        <div class="flex-shrink-0 flex items-center">
          <img class="h-12 w-auto" src="/src/assets/images/logorealista.png" alt="Workflow" />
        </div>

        <div class="flex-1 mt-6 w-full px-2 space-y-1">
          <a href="/admin" class="text-indigo-100 hover:bg-gray-800 hover:text-white group w-full p-3 rounded-md flex flex-col items-center text-xs font-medium">
            <v-icon color="white"> mdi-home </v-icon>
            <span class="mt-2">Home</span>
          </a>

          <a href="/admin/categorias" class="text-indigo-100 hover:bg-gray-800 hover:text-white group w-full p-3 rounded-md flex flex-col items-center text-xs font-medium">
            <v-icon color="white"> mdi-food-apple </v-icon>
            <span class="mt-2">Categorias</span>
          </a>

          <a href="/admin/personal" class="text-indigo-100 hover:bg-gray-800 hover:text-white group w-full p-3 rounded-md flex flex-col items-center text-xs font-medium">
            <v-icon color="white"> mdi-account-circle </v-icon>
            <span class="mt-2">Personal</span>
          </a>
          <a href="/admin/eventos" class="text-indigo-100 hover:bg-gray-800 hover:text-white group w-full p-3 rounded-md flex flex-col items-center text-xs font-medium">
            <v-icon color="white"> mdi-calendar </v-icon>
            <span class="mt-2">Eventos</span>
          </a>

          <a href="/admin/users" class="text-indigo-100 hover:bg-gray-800 hover:text-white group w-full p-3 rounded-md flex flex-col items-center text-xs font-medium">
            <v-icon color="white"> mdi-account </v-icon>
            <span class="mt-2">Usuarios</span>
          </a>

          <a href="/admin/servicios" class="text-indigo-100 hover:bg-gray-800 hover:text-white group w-full p-3 rounded-md flex flex-col items-center text-xs font-medium">
            <v-icon color="white"> mdi-food </v-icon>
            <span class="mt-2">Servicios</span>
          </a>
          <a href="/admin/paquete" class="text-indigo-100 hover:bg-gray-800 hover:text-white group w-full p-3 rounded-md flex flex-col items-center text-xs font-medium">
            <v-icon color="white"> mdi-package-variant-closed </v-icon>
            <span class="mt-2">Paquetes</span>
          </a>
          <a href="/admin/personalEvento" class="text-indigo-100 hover:bg-gray-800 hover:text-white group w-full p-3 rounded-md flex flex-col items-center text-xs font-medium">
                    <v-icon color="white"> mdi-account-multiple </v-icon>
                    <span class="mt-2">Asignar personal</span>
                  </a>
        </div>
      </div>
    </div>

    <div v-if="mobileMenuOpen" class="md:hidden" role="dialog" aria-modal="true">
      <div class="fixed inset-0 z-40 flex">
        <div class="fixed inset-0 bg-gray-900 bg-opacity-75" aria-hidden="true"></div>

        <div class="relative max-w-xs w-full bg-gray-900 pt-5 pb-4 flex-1 flex flex-col">
          <div class="absolute top-1 right-0 -mr-14 p-1">
            <button type="button" class="h-12 w-12 rounded-full flex items-center justify-center focus:outline-none focus:ring-2 focus:ring-white" @click="toggleMobileMenu">              <!-- Heroicon name: outline/x -->
              <v-icon color="white"> mdi-close </v-icon>
              <span class="sr-only">Close sidebar</span>
            </button>
          </div>

          <div class="flex-shrink-0 px-4 flex items-center">
            <img class="h-12 w-auto" src="/src/assets/images/logorealista.png" alt="Workflow" />
          </div>
          <div class="mt-2 flex-1 h-0 px-2 overflow-y-auto">
            <nav class="h-full flex flex-col">
              <div class="space-y-1">
                  <a href="/admin" class="text-indigo-100 hover:bg-gray-800 hover:text-white group w-full p-3 rounded-md flex flex-col items-center text-xs font-medium">
                    <v-icon color="white"> mdi-home </v-icon>
                    <span class="mt-2">Home</span>
                  </a>

                  <a href="/admin/categorias" class="text-indigo-100 hover:bg-gray-800 hover:text-white group w-full p-3 rounded-md flex flex-col items-center text-xs font-medium">
                    <v-icon color="white"> mdi-food-apple </v-icon>
                    <span class="mt-2">Categorias</span>
                  </a>

                  <a href="/admin/personal" class="text-indigo-100 hover:bg-gray-800 hover:text-white group w-full p-3 rounded-md flex flex-col items-center text-xs font-medium">
                    <v-icon color="white"> mdi-account-circle </v-icon>
                    <span class="mt-2">Personal</span>
                  </a>
                  <a href="/admin/eventos" class="text-indigo-100 hover:bg-gray-800 hover:text-white group w-full p-3 rounded-md flex flex-col items-center text-xs font-medium">
                    <v-icon color="white"> mdi-calendar </v-icon>
                    <span class="mt-2">Eventos</span>
                  </a>

                  <a href="/admin/users" class="text-indigo-100 hover:bg-gray-800 hover:text-white group w-full p-3 rounded-md flex flex-col items-center text-xs font-medium">
                    <v-icon color="white"> mdi-account </v-icon>
                    <span class="mt-2">Usuarios</span>
                  </a>

                  <a href="/admin/servicios" class="text-indigo-100 hover:bg-gray-800 hover:text-white group w-full p-3 rounded-md flex flex-col items-center text-xs font-medium">
                    <v-icon color="white"> mdi-food </v-icon>
                    <span class="mt-2">Servicios</span>
                  </a>
                  <a href="/admin/paquete" class="text-indigo-100 hover:bg-gray-800 hover:text-white group w-full p-3 rounded-md flex flex-col items-center text-xs font-medium">
                    <v-icon color="white"> mdi-package-variant-closed </v-icon>
                    <span class="mt-2">Paquetes</span>
                  </a>
                  <a href="/admin/personalEvento" class="text-indigo-100 hover:bg-gray-800 hover:text-white group w-full p-3 rounded-md flex flex-col items-center text-xs font-medium">
                    <v-icon color="white"> mdi-account-multiple </v-icon>
                    <span class="mt-2">Asignar personal</span>
                  </a>
              </div>
            </nav>
          </div>
        </div>

        <div class="flex-shrink-0 w-14" aria-hidden="true" @click="toggleMobileMenu">
          <!-- Dummy element to force sidebar to shrink to fit close icon -->
        </div>
      </div>
    </div>

    <!-- Content area -->
    <div class="flex-1 flex flex-col overflow-hidden">
      <header class="w-full">
        <div class="relative z-10 flex-shrink-0 h-16 bg-white border-b border-gray-200 shadow-sm flex">
          <button type="button" class="border-r border-gray-200 px-4 text-gray-500 focus:outline-none focus:ring-2 focus:ring-inset focus:ring-indigo-500 md:hidden" @click="toggleMobileMenu">
            <span class="sr-only">Open sidebar</span>

            <svg class="h-6 w-6" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor" aria-hidden="true">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h7" />
            </svg>
          </button>
          <div class="flex-1 flex justify-between px-4 sm:px-6">
            <div class="ml-2 flex justify-end items-center space-x-4 sm:ml-6 sm:space-x-6">
              <div class="relative flex-shrink-0">
                <div>
                  <button type="button" class="bg-white rounded-full flex text-sm focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500" id="user-menu-button" aria-expanded="false" aria-haspopup="true" @click="toggleMenu">
                    <span class="sr-only">Open user menu</span>
                    <v-icon  x-large> mdi-account-circle </v-icon>
                  </button>
                </div>

                <div v-show="menuOpen" class="origin-top-right absolute right-0 mt-2 w-48 rounded-md shadow-lg py-1 bg-white ring-1 ring-black ring-opacity-5 focus:outline-none" role="menu" aria-orientation="vertical" aria-labelledby="user-menu-button" tabindex="-1">
                  <a href="/admin/perfil" class="block px-4 py-2 text-sm text-gray-700" role="menuitem" tabindex="-1" id="user-menu-item-0">
                    <v-icon dark left color="#03071E"> mdi-account-circle </v-icon>
                    Perfil</a
                  >
                  <v-divider></v-divider>

                  <button @click="signOut" class="block px-4 py-2 text-sm text-gray-700" role="menuitem" tabindex="-1" id="user-menu-item-1">
                    <v-icon dark left color="red"> mdi-arrow-left </v-icon>
                    Cerrar sesion
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </header>

      <!-- Main content -->
      <div class="flex-1 flex items-stretch overflow-hidden">
        <main class="flex-1 overflow-y-auto">
          <!-- Primary column -->
          <section aria-labelledby="primary-heading" class="min-w-0 flex-1 h-full flex flex-col lg:order-last p-3">
            <h1 id="primary-heading" class="sr-only">Photos</h1>
            <router-view />
          </section>
        </main>
      </div>
    </div>
  </div>
</template>

<script>
import { useAuthStore } from '@/stores'

export default {
  data() {
    return {
      menuOpen: false,
      mobileMenuOpen: false,
    }
  },
  methods: {
    toggleMenu() {
      this.menuOpen = !this.menuOpen
    },
    toggleMobileMenu() {
      this.mobileMenuOpen = !this.mobileMenuOpen
    },
    signOut() {
      const authStore = useAuthStore()
      authStore.logout()
    },
  },
}
</script>
