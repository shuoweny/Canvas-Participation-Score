<template>
  <el-button class='inline-button' @click="$emit('to-student')"> View</el-button>
</template>

<script>

// import { useRouter } from 'vue-router';
export default {
  name: 'ViewButton',
  emits: ["to-student"],
  setup() {
    return {
    };
  }
}
</script>

<style lang="scss" scoped>
.inline-button {
  background-color:#0c3c75;
  //background-color: #000f46;
  color: white;
  font-size: 1.2em;
  font-weight: 600;
  height: 60%;
  width: 70%;

  transition: background-color 0.3s ease; 

  &:hover {
    background-color: #4a81c4;
  }
}
</style>
