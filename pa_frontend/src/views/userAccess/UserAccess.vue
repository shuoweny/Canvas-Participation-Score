<template>
  <div class = "user-access">

    <div class = "content">
      <span class = "header"> {{ $route.params.subjectName }} </span>
      <div class = "table"> 
        <el-table :data="users" height="75vh" style="width:78vw">
          <el-table-column prop="id" label="Staff User Id"/>
          <el-table-column prop="name" label="Name"/>
          <el-table-column prop="type" label="Role"/>
          <el-table-column label="Has Access">
            <template #default="scope">
              <div v-if="scope.row.access"> <el-icon><Check /></el-icon> </div>
              <div v-else> <el-icon><Close /></el-icon> </div>
            </template>
          </el-table-column>
          <el-table-column label="Action">
            <template #default="scope"> 
              <div v-if="scope.row.access && scope.row.type == 'Instructor'" @click="revokeAccess(scope.row)"> <RevokeButton/> </div>
              <div v-if="!scope.row.access && scope.row.type == 'Instructor'" @click="grantAccess(scope.row)"> <GrantButton/> </div>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
  </div>
</template>

<script>
import GrantButton from './components/GrantButton.vue'
import RevokeButton from './components/RevokeButton.vue'
//import UsersTable from './components/Table.vue'; 
import { useRouter } from 'vue-router';
import { useRoute } from 'vue-router';
import { useStore } from 'vuex';
import { onMounted, ref, computed } from 'vue';
import api from '../../api';
import { ElMessage } from 'element-plus';

export default {
  name: "user-access-view",
  components: {
    RevokeButton, 
    GrantButton
  },
  data () {
    return {
      users: [
      

      ]
    }
  },

  setup() {
    const router = useRouter();
    const route = useRoute();
    const store = useStore(); 
    const users = ref(null);

    const subjectInfo = computed(() => {
      const courseName = route.params.subjectName;
      console.log("getting the course info of ", courseName);
      
      for (let subject of JSON.parse(localStorage.getItem("allSubjects"))) {
        console.log(subject);
        if (subject.name == courseName) {
          console.log("Subject found");
          return subject;
        }
      }
    })

    const toUpcoming = () => {
      router.push({ name: 'Upcoming' });
    };

    const toUserAccess = () => {
      router.push({ name: 'UserAccess' });
    };

    const fetchUserData = () => {
      api.getAllUsers(subjectInfo.value.id).then((res) => {
        console.log(res.data);
        if (res.state == 200) {
          users.value = res.data;
        } else {
          ElMessage.error("Error, Users Not Found.")
        }
      })
    }

    onMounted(() => {
      fetchUserData()
    });

    const revokeAccess = (user) => {
      console.log("revoke access of ", user.name)
      api.setUserAccess(subjectInfo.value.id, user.id, false).then((res) => {
        console.log(res.data)
        if (res.state == 200) {
          fetchUserData()
          ElMessage({
            message: "Access Revoked",
            type: 'success'
          })
        } else {
          ElMessage.error("Error, Revoke Failed")
        }
      })
    };

    const grantAccess = (user) => {
      console.log("grant access to ", user.name)
      api.setUserAccess(subjectInfo.value.id, user.id, true).then((res) => {
        if (res.state == 200) {
          fetchUserData()
          ElMessage({
            message: "Access Granted",
            type: 'success'
          })
        } else {
          ElMessage.error("Error, Grant Failed.")
        }
      })
    };



    return {
      toUpcoming,
      toUserAccess,
      subjectInfo,
      onMounted,
      users,
      revokeAccess,
      grantAccess,
      fetchUserData
    };
  }


}
</script>

<style lang="scss" scoped>
.user-access {
  display: flex; 

  
  .content{
    margin-top: 2rem;
    font-family: 'Lato', sans-serif;
    margin-left: 4rem;

    .header{
      font-weight:900;
      font-size: 2.5rem;
    }

    .table{
      margin-top: 70px;

      th{
        color: white;
        background-color: #9ba2ab;
        ///height: 100px; 
        font-weight: 600;
        font-size: 1.6rem;
      }

      td {
          color: black; 
          ///height: 110px;
          font-weight: 600;
          font-size: 1.2rem;
        }
    }
  }
}
</style>