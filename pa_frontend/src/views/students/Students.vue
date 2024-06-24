<template>
  <div class="students">

    <div class="content" v-if="displayContent">
      <span class="header"> {{ $route.params.subjectName }} </span>
      <!-- <div class="search-bar"><Searchbar/></div>  -->
      <div class="table"> 
        <el-table :data="allStudents" height="60vh" style="width:100%">
          <el-table-column prop="id" label="Student ID" width="100%"/>
          <el-table-column prop="name" label="Student Name"/>
          <el-table-column label="Participation Score (Current/Total Possible)">
            <template #default="scope"> 
              <div>
                {{ scope.row.studentTotal }}/{{ scope.row.totalPossible }}
              </div>
            </template>
          </el-table-column>
          <el-table-column label="Details">
            <template #default="scope"> 
              <div > <ViewButton @to-student="toStudent(scope.row.name, scope.row.id)"> </ViewButton> </div>
            </template>
          </el-table-column>
        </el-table>
      </div>

      <el-button class='button' @click="handleSendToAll">Email to All Students</el-button>
    </div>

    <router-view></router-view>

    

  </div> 
</template>

<script>
import ViewButton from './components/ViewButton.vue';
import Searchbar from './components/Searchbar.vue';
import api from '@/api';
import { ElMessage } from 'element-plus';

export default {
  name: 'students-view',
  components: {
    ViewButton, 
    Searchbar
  }, 

  data() {
    return {
      students: [],
      allStudents: null,
      subjectInfo: null,
      displayContent: true,
      allStudentsScore: null,
    } 
  },

  methods: {
    toUpcoming() {
      this.$router.push({ name: 'Upcoming' });
    },

    toStudent(sname, sid) {
      this.$router.push({ name: 'StudentDetail', params: { name: sname, id: sid } });
    },

    getAllStudentData() {
      var that = this;
      var cid = that.subjectInfo.id;
      Promise.all([api.getAllCourseStudents(cid), api.getAllStudentScores(cid)]).then((res) => {
        console.log(res[0])
        console.log(res[1])
        const res1 = res[0];
        const res2 = res[1];

        if (res1.state == 200 && res2.state == 200) {
          that.allStudents = res1.data;
          that.allStudentsScore = res2.data;
          for (var studentInfo of that.allStudents) {
            var totalScore = null;
            for (var scoreInfo of that.allStudentsScore) {
              if (studentInfo.id == scoreInfo.id) {
                totalScore = scoreInfo
                break;
              }
            }
            studentInfo.studentTotal = totalScore.studentTotal;
            studentInfo.totalPossible = totalScore.totalPossible;
          }
          console.log(that.allStudents)
        } else {
          ElMessage.error("Connection Error")
        }
      })

    },

    getCourseInfo() {
      const courseName = this.$route.params.subjectName;
      console.log("getting the course info of ", courseName);
      
      for (let subject of JSON.parse(localStorage.getItem("allSubjects"))) {
        console.log(subject);
        if (subject.name == courseName) {
          this.subjectInfo = subject;
          console.log("Subject found");
          return;
        }
      }
    },

    handleSendToAll() {
      console.log("Sending to All Student");
      var cid = this.subjectInfo.id;
      api.sendEmailToAll(cid).then((res) => {
        console.log(res)
        if (res.state == 200) {
          ElMessage({
            message: "Email Sent",
            type: 'success'
          })
        } else {
          ElMessage.error("Emial Sent Fail")
        }
      })
    }
  },

  watch: {
    '$route'(to, from) {
      if (to.name == "StudentDetail") {
        this.displayContent = false;
      } else {
        this.displayContent = true;
      }
    },
    

  },

  mounted() {
    this.getCourseInfo();
    this.getAllStudentData();
    // ... (implementation remains unchanged)
  }

  
}
</script>

<style lang="scss" scoped>
.students {
  display: flex; 
  width: 78vw;
  

  
  .content{
    margin-top: 2rem;
    font-family: 'Lato', sans-serif;
    margin-left: 2rem;
    width: 100%;

    .header{
      font-weight:900;
      font-size: 2.5rem;
    }

    .search-bar{
      margin-top:2.5rem; 
    }

    .table{
      margin-top: 1rem;
    }

    .button {
      background-color:#0c3c75;
      color: white;
      font-size: 18px;
      height: 3rem;
      width: 15rem;
      margin-top: 3rem;

      transition: background-color 0.3s ease; 

      &:hover {
        background-color: #4a81c4;
      }
    }


  }

  .el-table .el-table__row:hover {
    background-color: rgba(19, 3, 108, 0.1); 
    transition: background-color 0.3s ease;
  }
}
</style>