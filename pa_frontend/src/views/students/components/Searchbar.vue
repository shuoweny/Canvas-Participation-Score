<template>
  <el-input v-model="query" placeholder="Search student by name..."></el-input>
</template>

<script>
export default {
  name: 'SearchBar',
  data() {
    return {
      query: ''
    };
  },
  // 更多 JS 代码...
}
</script>

<style lang="scss">
  .el-input{
    height: 80px; 
    font-size: 25px;
  }

</style>
