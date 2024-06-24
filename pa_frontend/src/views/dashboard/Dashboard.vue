/* eslint-disable */
<template>
  <div class="dashboard">
    <!-- <Sidebar @to-upcoming="toUpcoming"/> -->
    <SideMenu @to-upcoming="toUpcoming" :avatarURL="this.avatarURL" :displayUpdate="this.displayUpdate"></SideMenu>
    
    <!-- <TopPath :segments="pathSegments"></TopPath> -->
    <div class="dashboard-content">
      <div class="dashboard-title"><p>Dashboard</p></div>
      <div class="content">
        <div class="subjects-grid">
          <SubjectCard 
            v-for="subject in subjects" 
            :key="subject.id" 
            :subject="subject"
            @click="toSubjectOverview(subject.name)"/>
        </div>
      </div>
    </div>
    <!-- <router-view></router-view> -->

  </div>
</template>

<script>
// import Sidebar from './components/Sidebar.vue';
import SubjectCard from './components/SubjectCard.vue';
// import Searchbar from './components/Searchbar.vue';
import { ref, onMounted, computed } from 'vue';
import api from '../../api'
import { useRoute, useRouter } from 'vue-router';
import SideMenu from '../../components/SideMenu.vue';
import { ElMessage } from 'element-plus';
import store from '../../store';

export default {
  name: 'Dashboard',
  components: {
    SubjectCard,
    SideMenu
  },
  data() {
    return {
      subjects: [],
      currentStaffData: [],
      currentStaff: "",
      avatarURL: '',
      displayUpdate: false
    };
  },
  /* mounted() {
    api.getSubjectsData({}).then(res => {
      console.log(res.data.data);
    })
  }, */
  methods: {
    toUpcoming() {
      this.$router.push({ name: 'Upcoming' });
    },
    toSubjectOverview(subject) {
      this.$router.push({name:'SubjectBasicView', params: {subjectName: subject}}); 
    },
    updatedComponents() {
      console.log("Update Display")
      this.displayUpdate = !this.displayUpdate
    }



  },
  beforeCreate() {

  },
  mounted() {
    var that = this

    if (store.getters.getUserInfo == null) {
      api.getSubjectsData().then((res) => {
        console.log(res.data)
        store.dispatch('updateAllSubjects', res.data);
        localStorage.setItem("allSubjects", JSON.stringify(res.data));
        that.subjects = store.getters.allSubjects
      })

      var userId = 111280
      api.getUserInfo(userId).then((res) => {
        console.log(res)
        if (res.state == 200) {
          ElMessage({
            message: "Welcome" + res.data.name,
            type: "success"
          })
          that.avatarURL = res.data.avatar_url
          console.log(that.avatarURL)

          if (store.getters.userInfo.id == null) {
            store.dispatch('updateUserInfo', res.data)
          }

          console.log("User Info Stored")
          console.log(store.getters.userInfo)
          that.updatedComponents()


        }
        
      })
    }
    
    // api.getCurrentStaffSubjectAccess().then(res => {
    //   this.currentStaffData = res.data.data;
    //   console.log(res.data.data);
    // });
    // api.getCurrentStaff().then(res => {
    //   this.currentStaff = res.data.data.firstname;
    //   console.log(res.data.data.firstname);
    // });
    
  }
}
</script>

<style lang="scss">
.dashboard {
  display: flex;


  h1 {
    margin-right: 85%; 
  }
}

.dashboard-content{
  width: 100%;

  .dashboard-title{
    font-size: 3em;
    font-weight: 900;
    margin-top:5vh;
    margin-bottom:5vh;
    color: rgb(37,53,73);
  }
}

.subjects-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
  margin: 30px;  
  place-items: center;
  padding: 0;
}


</style>