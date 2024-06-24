<template>
  <el-button class='button'> {{ buttonText }} </el-button>
</template>

<script>

export default{

  props: {
    buttonText: {
      type: String,
      default: 'Default Text'
    }
  }
}
</script>


<style lang="scss" scoped>
.button {
  background-color:#0c3c75;
  color: white;
  font-size: 18px;
  height: 50px;
  width: 150px;

  transition: background-color 0.3s ease; 

  &:hover {
    background-color: #4a81c4;
  }
}
</style>
