<script setup>
import VueRouter from 'vue-router';
</script>

<template>
  <div id="app">
    <div>
      <b-navbar toggleable="lg" type="dark" variant="info" sticky>
        <b-navbar-brand href="#"><img src="https://placekitten.com/g/30/30" class="d-inline-block align-top" alt="Kitten"></b-navbar-brand>

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
            
          </b-navbar-nav>
        </b-collapse>
      </b-navbar>
    </div>

    <div class="row mt-1">
      <div class="col-3">
        <div class="px-3 py-2">
          <h1>Sidebar</h1>
          <p>
            Cras mattis consectetur purus sit amet fermentum. Cras justo odio, dapibus ac facilisis
            in, egestas eget quam. Morbi leo risus, porta ac consectetur ac, vestibulum at eros.
          </p>
          <b-img src="https://img.freepik.com/foto-gratis/arbol-navidad-decorado-estrella_318354-1719.jpg?size=338&ext=jpg&ga=GA1.1.1880011253.1704931200&semt=sph" fluid thumbnail></b-img>
        </div>
        <div>
          <b-nav vertical class="pt-5">
    
            <b-nav-item 
              ><b-link :to="{ name: 'inicio' }">
                Opcion 1
                </b-link>
            </b-nav-item>
          
            <b-nav-item 
              ><b-link :to="{ name: 'home' }">Opcion 2</b-link>
            </b-nav-item>
          
            <b-nav-item 
              ><b-link :to="{ name: 'user' }">Opcion 3</b-link>
            </b-nav-item>
          </b-nav>  
        </div>
      </div>
      <div class="col-9">
        <div>
          <div>
              <b-carousel
                id="carousel-1"
                v-model="slide"
                :interval="4000"
                controls
                indicators
                background="#ababab"
                img-width="200"
                img-height="100"
                style="text-shadow: 1px 1px 2px #333;"
                @sliding-start="onSlideStart"
                @sliding-end="onSlideEnd">
                <!-- Text slides with image -->
                <b-carousel-slide
                  caption="First slide"
                  text="Nulla vitae elit libero, a pharetra augue mollis interdum."
                  img-src="https://picsum.photos/1024/480/?image=52"
                ></b-carousel-slide>

                <!-- Slides with custom text -->
                <b-carousel-slide img-src="https://picsum.photos/1024/480/?image=54">
                  <h1>Hello world!</h1>
                </b-carousel-slide>

                <!-- Slides with image only -->
                <b-carousel-slide img-src="https://picsum.photos/1024/480/?image=58"></b-carousel-slide>

              </b-carousel>
            </div>
        </div>
        <div>
          <div class="mt-3">
            <b-card-group deck>
              <b-card img-src="https://placekitten.com/1000/300" img-alt="Card image" img-top>
                <b-card-text>
                  Some quick example text to build on the card and make up the bulk of the card's content.
                </b-card-text>
              </b-card>
            </b-card-group>
          </div>
        </div>
        <div>
          <router-view> </router-view>
        </div>
      </div>
    </div>


  </div>
</template>
