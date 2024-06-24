<template>
  <el-menu class="sidebar">
    <el-menu-item index="1" @click="$emit('to-upcoming')">Profile</el-menu-item>
    <el-menu-item index="2" @click="$emit('to-upcoming')">Dashboard</el-menu-item>
    <el-menu-item index="3" @click="$emit('to-upcoming')">Subjects</el-menu-item>
    <el-menu-item index="4" @click="$emit('to-upcoming')">Setting</el-menu-item>
    <el-menu-item index="5" @click="$emit('to-upcoming')">Message</el-menu-item>
  </el-menu>
</template>

<script>

// import { useRouter } from 'vue-router';
export default {
  name: 'Sidebar',
  emits: ["to-upcoming"],
  setup() {
    // const router = useRouter();

    // const toUpcoming = () => {
    //   router.push({ name: 'Upcoming' });
    // };

    return {
      // subjects
      // toUpcoming
    };
  }
}
</script>

<style lang="scss">
.sidebar {
  // SCSS样式...
}
</style>
