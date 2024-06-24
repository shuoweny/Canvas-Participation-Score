<template>
  <div class="collaps-title"> 
    <div>{{ titleData.name }}</div>
    <div>{{ titleData.defaultScore}}</div>
  </div>
</template>

<script>

export default {
  name: 'default-weight',
  props: {
    titleData: {
      type: Object,
      default: {
        name: 'Lecture',
        method: 'Complete',
        defaultScore: 20,
        totalScore: 80 
      }
    }
  },
  components: {
  }
}
</script>


<style lang='scss' scoped>

</style>