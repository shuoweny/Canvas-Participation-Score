<template>

  <el-form :model="form">
    <el-form-item label="Default Method" >
      <el-input v-model="form.name" autocomplete="off" />
    </el-form-item>
    <el-form-item label="Zones" >
      <el-select v-model="form.region" placeholder="Please select a zone">
        <el-option label="Zone No.1" value="shanghai" />
        <el-option label="Zone No.2" value="beijing" />
      </el-select>
    </el-form-item>
  </el-form>

</template>

<script>
export default {
  name: "change-weight-form",
  components: {

  },
  props: {
    subjectInfo: {
      type: Object,
      default: null
    },
    assignmentType: {
      type: String,
      default: null
    },
    formType: {
      type: String,
      default: 'group'
    },
    originalSetting: {
      type: Object,
      default: null
    }
  },
  data() {
    return {
      form: {
        mode: this.formType,
        assignmentType: null,
        weight: this.originalSetting.weight,
        method: this.originalSetting.method
      }

    }
  },

  methods: {

  },
}

</script>


<style lang="scss" scoped>

</style>