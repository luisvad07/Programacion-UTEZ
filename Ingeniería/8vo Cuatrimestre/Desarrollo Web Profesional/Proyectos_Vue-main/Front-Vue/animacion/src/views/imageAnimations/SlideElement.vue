<template>
  <div>
    <button @click="moveBox">Mover caja</button>
    <transition name="slide">
      <div :style="{transform: 'translateX(' + posX + 'px)'}" class="box"></div>
    </transition>
  </div>
</template>

<script>
export default {
  data() {
    return {
      posX: 0
    };
  },
  methods: {
    moveBox() {
      this.posX += 100;
    }
  }
};
</script>

<style>
.slide-enter-active, .slide-leave-active {
  transition: transform 0.5s;
}
.slide-enter, .slide-leave-to {
  transform: translateX(-100%);
}
.box {
  width: 100px;
  height: 100px;
  background-color: red;
}
</style>
