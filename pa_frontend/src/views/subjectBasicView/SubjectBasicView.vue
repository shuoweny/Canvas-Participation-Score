<template>
  <div class="basic">
    <SideMenu @to-upcoming="toUpcoming" :avatarURL="this.avatarURL" :displayUpdate="this.displayUpdate"></SideMenu>
    <!-- <el-menu class="side-bar">
      <div class ="side-bar-top"></div>
      <el-menu-item class="side-bar-item" index="1" @click="switchTo('overview')">Overview</el-menu-item>
      <el-menu-item class="side-bar-item" index="2" @click="switchTo('students')">Students</el-menu-item>
      <el-menu-item class="side-bar-item" index="3" @click="switchTo('visualisation')">Visualisation</el-menu-item>
      <el-menu-item class="side-bar-item" index="4" @click="switchTo('weightings')">Weightings</el-menu-item>
      <el-menu-item class="side-bar-item" index="5" @click="switchTo('userAccess')">User Access</el-menu-item>
    </el-menu> -->
    <ul class="side-bar">
      <div class ="side-bar-top"></div>
      <li class="side-bar-item" index="1" @click="switchTo('overview')">Overview</li>
      <li class="side-bar-item" index="2" @click="switchTo('students')">Students</li>
      <li class="side-bar-item" index="3" @click="switchTo('visualisation')">Visualisation</li>
      <li class="side-bar-item" index="4" @click="switchTo('weightings')">Weightings</li>
      <li class="side-bar-item" index="5" @click="switchTo('userAccess')">User Access</li>
    </ul>
    <div class="router-content">
      <div class="bread-crumbs">
        <breadcrumbs></breadcrumbs>
      </div>
      <router-view></router-view>
    </div>


  </div>
</template>


<script>
import store from "../../store"
import Breadcrumbs from "@/components/Breadcrumbs.vue";
import router from "@/router";

export default {
  name: 'dashboard-view',
  components: {
    Breadcrumbs,
  },
  data() {
    return {
      avatarURL: '',
      displayUpdate: false,
      course_info:null,
    };
  },
  computed: {

  },
  methods: {
    toUpcoming() {
      this.$router.push({ name: 'Upcoming' });
    },
    updateComponents() {
      console.log("Update Display")
      this.displayUpdate = !this.displayUpdate
    },
    switchTo(dest) {
      const routeNameMapping = {
        overview: 'SubjectOverview',
        students: 'Students',
        visualisation: 'Visualisation',
        weightings: 'Weightings',
        userAccess: 'UserAccess',
      };
      const routeName = routeNameMapping[dest];
      if (routeName) {
        this.$router.push({
          name: routeName,
          params: { subjectName: this.currentSubjectName },
        });
      } else {
        console.error(`Unknown destination: ${dest}`);
        this.toUpcoming();
      }
    },
  },
  beforeCreate() {
  },
  mounted() {
    this.avatarURL = store.getters.userInfo.avatar_url
    console.log("avatar : ", this.avatarURL)
    if (this.avatarURL == null) {
      this.avatarURL = 'https://unimelb-dev.instructure.com/images/messages/avatar-50.png'
    }




    console.log("Mounting :" + this.$route.params.subjectName)
    





    this.switchTo("overview")


  }
}
</script>

<style lang="scss" scoped>
.basic {
  display: flex;


  h1 {
    margin-right: 85%; 
  }
}

.router-content{
  display: flex;
  flex-direction: column;

  .bread-crumbs{
    padding: 0;
  }

}

.side-bar{
  width: 10vw;
  padding: 0;
  margin: 0;
  border-right: 2px solid rgba(128, 128, 128, 0.5);
  .side-bar-top{
    height: 10vh;
  }

  .side-bar-item{
    font-size: 1rem;
    font-weight: 500;
    color: rgb(12,47,112);
    list-style-type:none;
    margin-top: 2rem;
    margin-left: 1rem;
    margin-right: 1rem;

    &:hover{
      margin-left: 5px;
      color: black;
    }

  }

}

</style>


