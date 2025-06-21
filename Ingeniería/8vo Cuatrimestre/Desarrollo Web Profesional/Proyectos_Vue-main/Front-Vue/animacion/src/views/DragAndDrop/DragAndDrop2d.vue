<template>
  <div class="drag-container">
    <div class="row" v-for="(row, rowIndex) in matrix" :key="'row-' + rowIndex">
      <div
        class="col"
        v-for="(item, colIndex) in row"
        :key="'col-' + colIndex"
        :class="{ 'drag-item': item !== null }"
        @dragstart="handleDragStart($event, rowIndex, colIndex)"
        @dragover.prevent
        @drop="handleDrop(rowIndex, colIndex)"
        draggable="true"
      >
        {{ item !== null ? item : "Drop Here" }}
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      matrix: [
        ['1A', '2A', '3A'],
        ['1B', '2B', '3B'],
        ['1C', '2C', '3C'],
      ],
      draggedItem: null,
    };
  },
  methods: {
    handleDragStart(event, rowIndex, colIndex) {
      // Guardamos el elemento que estamos arrastrando, esta se guarda desde el momento
      // que realizamos el arrastre del objeto
      this.draggedItem = { rowIndex, colIndex };
    },
    handleDrop(rowIndex, colIndex) {

      // Obtenemos el elemento arrastrado
      const { draggedItem } = this;

      // Verificamos si el elemento está siendo arrastrado dentro de la misma matriz
      if (
        draggedItem.rowIndex === rowIndex &&
        draggedItem.colIndex === colIndex
      ) {
        return;
      }

      // Realizamos el intercambio de elementos
      const temp = this.matrix[rowIndex][colIndex];
      this.$set(
        this.matrix[rowIndex],
        colIndex,
        this.matrix[draggedItem.rowIndex][draggedItem.colIndex]
      );
      this.$set(this.matrix[draggedItem.rowIndex], draggedItem.colIndex, temp);

      // Reseteamos draggedItem a null después de soltar
      this.draggedItem = null;
    },
  },
};
</script>

<style scoped>
.drag-container {
  display: flex;
  flex-direction: column;
}

.row {
  display: flex;
}

.col {
  border: 1px solid #ccc;
  padding: 10px;
  margin: 5px;
}

.drag-item {
  background-color: #f0f0f0;
  cursor: pointer;
}
</style>
