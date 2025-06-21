<template>
  <div class="drag-container list-group" @dragover.prevent @drop="handleDrop">
    <div
      class="drag-item list-group-item"
      v-for="(item, index) in items"
      :key="index"
      draggable="true"
      @dragstart="handleDragStart(index)"
    >
      {{ item }}
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      items: ["Elemento 1", "Elemento 2", "Elemento 3"],
    };
  },
  methods: {
    handleDragStart(index) {
      // Guardamos el índice del elemento arrastrado
      event.dataTransfer.setData("text/plain", index);
    },
    handleDrop(event) {
      // Prevenir el comportamiento predeterminado
      event.preventDefault();

      // Obtener el índice del elemento arrastrado
      const index = event.dataTransfer.getData("text/plain");

      // Obtener el texto del elemento arrastrado
      const itemText = this.items[index];

      // Obtener el índice del contenedor donde se soltó el elemento
      const dropIndex = this.items.indexOf(event.target.innerText);

      // Eliminar el elemento de la lista original
      this.items.splice(index, 1);

      // Insertar el elemento en su nueva posición
      this.items.splice(dropIndex, 0, itemText);
    },
  },
};
</script>

<style scoped>
.drag-container {
  width: 300px;
  border: 2px dashed #ccc;
  padding: 20px;
}

.drag-item {
  background-color: #f0f0f0;
  border: 1px solid #ccc;
  margin-bottom: 10px;
}

.drag-item:hover {
  cursor: grab;
}
</style>
