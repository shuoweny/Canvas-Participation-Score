<template>
  <el-button class='button'>{{ buttonText }}</el-button>
</template>
<script>
export default {
  name: 'my-button',
  props: {
    buttonText: {
      type: String,
      default: "Config"
    }
  },
  components: {
  }
}

</script>
<style lang="scss" scoped>
.button {
  background-color:#0c3c75;
  color: white;
  font-size: 1rem;
  height: 30px;
  width: 200px;

  transition: background-color 0.3s ease; 

  &:hover {
    background-color: #4a81c4;
  }
}
</style>
