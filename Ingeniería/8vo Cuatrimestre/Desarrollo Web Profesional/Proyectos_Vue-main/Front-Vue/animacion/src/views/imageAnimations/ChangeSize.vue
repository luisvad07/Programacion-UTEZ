<template>
  <div>
    <button @click="toggleSize">Cambiar tamaño</button>
    <transition name="scale">
      <div :style="{width: boxWidth + 'px', height: boxHeight + 'px'}" class="box"></div>
    </transition>
  </div>
</template>

<script>
export default {
  data() {
    return {
      boxWidth: 100,
      boxHeight: 100
    };
  },
  methods: {
    toggleSize() {
      this.boxWidth = this.boxWidth === 100 ? 200 : 100;
      this.boxHeight = this.boxHeight === 100 ? 200 : 100;
    }
  }
};
</script>

<style>
.scale-enter-active, .scale-leave-active {
  transition: all 0.5s;
}
.scale-enter, .scale-leave-to {
  transform: scale(0);
}
.box {
  background-color: blue;
}
</style>
