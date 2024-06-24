<template>
  <nav class="breadcrumbs">
    <ul>
      <li>
        <router-link to="/dashboard">Dashboard</router-link>
      </li>
      <li v-if="subjectName">
        <span class="separator">></span>
        <router-link :to="`/subject/${subjectName}`">{{ subjectName }}</router-link>
      </li>
      <li v-if="subRoute">
        <span class="separator">></span>
        <router-link :to="`/subject/${subjectName}/${subRoute}`">{{ subRouteName }}</router-link>
      </li>
      <li v-if="subsubRoute">
        <span class="separator">></span>
        <router-link :to="`/subject/${subjectName}/${subRoute}/${subsubRoute.join('/')}`">{{ subsubRoute[1].replaceAll("%20", " ") }}</router-link>
      </li>
    </ul>
  </nav>
</template>

<script>
export default {
  name: 'bread-crumbs',
  computed: {
    subjectName() {
      return this.$route.params.subjectName;
    },
    subRoute() {
      const pathSegments = this.$route.path.split('/');
      return pathSegments.length > 3 ? pathSegments[3] : null;
    },
    subsubRoute() {
      const pathSegments = this.$route.path.split('/');
      return pathSegments.length > 5 ? pathSegments.slice(4) : null;
    },
    subRouteName() {
      const subRoute = this.subRoute;
      console.log("Subroute " + subRoute)
      if (!subRoute) return '';
      const nameMap = {
        subjectOverview: 'Overview',
        userAccess: 'User Access',
        students: 'Students',
        visualisation: 'Visualizations',
        weightings: 'Weightings'
      };
      console.log(nameMap[subRoute])
      return nameMap[subRoute] || subRoute;
    }
  }
}
</script>

<style scoped>
.breadcrumbs {
  padding: 0;
  margin-top: 1em;
  margin-left: 1em;
}

.breadcrumbs ul {
  list-style-type: none;
  margin: 0;
  padding: 0;
  display: flex;
  flex-direction: row;
}

.breadcrumbs li {
  margin-right: 0.5rem;
}

.breadcrumbs .separator {
  margin-left: 0.5rem;
  margin-right: 0.5rem;
}
</style>
