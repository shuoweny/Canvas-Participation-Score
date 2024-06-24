<template>
  <div class="student">
    <div class="content"> 
      <span class="header"> {{ $route.params.subjectName }}: <br> {{$route.params.name}} </span>
      <div class="details" v-if="this.dataReady">
        <el-collapse accordion>
          <el-collapse-item name="lectures" title="Lectures">
            <DetailList :items="lectures"></DetailList>
          </el-collapse-item>
          <el-collapse-item name="canvas" title="Canvas">
            <DetailList :items="canvas"></DetailList>
          </el-collapse-item>
          <el-collapse-item name="h5ps" title="H5ps">
            <DetailList :items="h5ps"></DetailList>
          </el-collapse-item>
          <el-collapse-item name="tutorial" title="Tutorial">
            <DetailList :items="tutorial"></DetailList>
          </el-collapse-item>
          <el-collapse-item name="others" title="Others">
            <DetailList :items="others"></DetailList>
          </el-collapse-item>
          
  
        </el-collapse>
      </div>

      <div class="pie-chart">
        <PieChart :pieData="pieData" v-if="displayPie"></PieChart>
      </div>


      <div class="buttons"> 
        <MyButton :buttonText="'Email to Student'" @click="handleEmail"/>
        <MyButton :buttonText="'Download Report'" @click="handleExport"/> 
      </div>
    </div>
  </div>
</template>

<script>
import MyButton from './components/MyButton.vue';
import { useRouter } from 'vue-router';
import api from '../../api'
import { ElMessage } from 'element-plus';
import DetailList from "./components/DetailList";
import PieChart from './components/PieChart.vue';

export default {
  name: "student", 
  components: {
    MyButton,
    DetailList,
    PieChart
  }, 
  data() { 
    return {
      subjectInfo: null,
      lectures: [],
      canvas: [],
      h5ps: [],
      tutorial: [],
      others: [],
      dataReady: false,
      pieData: {
        completed: 1,
        notYet: 1,
        overDue : 1
      },
      displayPie: false,
      
    }

  },

  methods: {
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

    handleEmail() {
      console.log("Sending to Individual Student");
      var cid = this.subjectInfo.id;
      var sid = this.$route.params.id;
      api.sendEmailToStuent(cid, sid).then((res) => {
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
    },

    handleExport() {
      console.log("Exporting the indivdual Pdf report");
      var cid = this.subjectInfo.id;
      var sid = this.$route.params.id;
      api.downloadPdf(cid, sid)
    }



    
  },

  mounted() {
    this.getCourseInfo();
    var cid = this.subjectInfo.id;
    var sid = this.$route.params.id;
    var that = this;
    console.log("cid, sid: ", cid, sid)
    api.getStudentDetailScoreInfo(cid, sid).then((res) => {
      console.log(res.data)
      if (res.state == 200) {
        for (var item of res.data) {
          if (item.type == "lecture") {
            that.lectures.push(item)
          } else if (item.type == "h5p") {
            that.h5ps.push(item)
          } else if (item.type == "canvas") {
            that.canvas.push(item)
          } else if (item.type == "tutorial") {
            that.tutorial.push(item)
          } else {
            that.others.push(item)
          }
        }
        that.dataReady = true;
      } else {
        ElMessage.error("Student Detail not found")
      }
    })
    api.getPieData(cid, sid).then((res) => {
      console.log(res.data)
      if (res.state == 200) {
        that.pieData.completed = res.data.diagramData.completed;
        that.pieData.notYet = res.data.diagramData.notYetCompleted;
        that.pieData.overDue = res.data.diagramData.overDue;
        that.displayPie = true;
      } else {
        ElMessage.error("Pie data fetching error")
      }
    })
    
  },

}  

</script>

<style lang="scss" scoped> 
.student{
  display: flex; 
  width: 60vw;
  
  .content{
    margin-top: 2rem;
    font-family: 'Lato', sans-serif;
    margin-left: 4rem;
    width: 100%;

    .header{
      font-weight:900;
      font-size: 2rem;
    }

    .details{
      width: 100%;
      margin-top: 2rem;
      margin-bottom: 3rem;
    }

    .buttons{
      display: flex;
      justify-content: space-evenly;
    }

  }
}
</style>