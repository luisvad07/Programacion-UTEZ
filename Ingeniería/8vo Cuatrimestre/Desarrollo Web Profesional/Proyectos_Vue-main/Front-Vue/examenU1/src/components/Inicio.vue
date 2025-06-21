<script setup>

</script>

<template>
  <div id="app">
    <div>
      <b-navbar toggleable="lg" type="info" variant="info" sticky>
        <b-navbar-brand href="/#/inicio"
          ><img
            src="https://placekitten.com/g/30/30"
            class="d-inline-block align-top"
            alt="Kitten"
        />Inicio</b-navbar-brand>

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
        <b-nav vertical class="pt-5">
          <b-nav-item>
            <b-link :to="{ name: 'principal' }" class="text-primary">Principal</b-link>
          </b-nav-item>
          <b-nav-item >
            <b-link :to="{ name: 'formulario' }" class="text-primary">Formulario</b-link>
          </b-nav-item>
          <b-nav-item>
            <b-link :to="{ name: 'paginacion' }" class="text-primary">Paginación</b-link>
          </b-nav-item>
        </b-nav>
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
              img-width="1024"
              img-height="480"
              style="text-shadow: 1px 1px 2px #333;"
              @sliding-start="onSlideStart"
              @sliding-end="onSlideEnd"
            >
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

              <!-- Slides with img slot -->
              <!-- Note the classes .d-block and .img-fluid to prevent browser default image alignment -->
              <b-carousel-slide>
                <template #img>
                  <img
                    class="d-block img-fluid w-100"
                    width="1024"
                    height="480"
                    src="https://picsum.photos/1024/480/?image=55"
                    alt="image slot"
                  >
                </template>
              </b-carousel-slide>

              <!-- Slide with blank fluid image to maintain slide aspect ratio -->
              <b-carousel-slide caption="Blank Image" img-blank img-alt="Blank image">
                <p>
                  Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse eros felis, tincidunt
                  a tincidunt eget, convallis vel est. Ut pellentesque ut lacus vel interdum.
                </p>
              </b-carousel-slide>
            </b-carousel>

            <p class="mt-4">
              Slide #: {{ slide }}<br>
              Sliding: {{ sliding }}
            </p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        slide: 0,
        sliding: null
      }
    },
    methods: {
      onSlideStart(slide) {
        this.sliding = true
      },
      onSlideEnd(slide) {
        this.sliding = false
      }
    }
  }
</script>