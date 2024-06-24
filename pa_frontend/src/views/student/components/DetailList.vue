<template>
  <div class="detail-list">
    <el-table :data="items" max-height="40vh">
      <el-table-column prop="id" label="Id"/>
      <el-table-column prop="name" label="Name"/>
      <el-table-column prop="method" label="Measure Cirteria"/>
      <el-table-column  label="Score">
        <template #default="scope"> 
          <div v-if="scope.row.method=='weight'">
            {{ scope.row.participationScoreWeight==null ? "Not Yet Graded" : scope.row.participationScoreWeight}}
          </div>
          <div v-if="scope.row.method=='complete'">
            {{ scope.row.participationScoreComplete }}
          </div>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  name: 'detail-list',
  props: {
    items: {
      type: Array,
      default: null
    }
  }
  
  
}
</script>

<style lang="scss" scoped>
.detail-list{
  height: 100%;
  width: 100%;
}
</style>

