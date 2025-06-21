
<script setup>
  import Bread from './components/Bread.vue';
</script>

<template>
  <div id="app">
    <div>
      <b-navbar toggleable="lg" type="dark" variant="info" sticky>
        <b-navbar-brand href="#"
          ><img
            src="https://placekitten.com/g/30/30"
            class="d-inline-block align-top"
            alt="Kitten"
        /></b-navbar-brand>

        <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

        <b-collapse id="nav-collapse" is-nav>
          <!-- Right aligned nav items -->
          <b-navbar-nav class="ml-auto" right>
            <b-nav-item-dropdown text="Lang" right>
              <b-dropdown-item href="#">EN</b-dropdown-item>
              <b-dropdown-item href="#">ES</b-dropdown-item>
              <b-dropdown-item href="#">RU</b-dropdown-item>
              <b-dropdown-item href="#">FA</b-dropdown-item>
            </b-nav-item-dropdown>

            <b-nav-item-dropdown right>
              <template #button-content>
                <em>User</em>
              </template>
              <b-dropdown-item href="#">Profile</b-dropdown-item>
              <b-dropdown-item href="#">Sign Out</b-dropdown-item>
            </b-nav-item-dropdown>
            <b-breadcrumb :items="items"></b-breadcrumb>
          </b-navbar-nav>
        </b-collapse>
      </b-navbar>
    </div>
    <div class="row mt-1">
      <div class="col-3">
        <div class="px-3 py-2">
          <h1>Sidebar</h1>
          
        </div>
        <div>
          <b-nav vertical class="pt-5">
            
            <b-nav-item 
              ><b-link :to="{ name: 'inicio' }">
                Tienda de Juguetes
                </b-link>
            </b-nav-item>
          
            <b-nav-item 
              ><b-link :to="{ name: 'home' }">Tienda de Electrodomesticos</b-link>
            </b-nav-item>
          
            <b-nav-item 
              ><b-link :to="{ name: 'user' }">Tienda de Productos para el Hogar</b-link>
            </b-nav-item>
          </b-nav>  
        </div>
      </div>
      <div class="col-9">
        <div>
          <div>
            <router-view></router-view>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>