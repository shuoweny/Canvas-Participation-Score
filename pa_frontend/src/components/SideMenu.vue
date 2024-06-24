<template>
  <div class="whole-side-menu">
    <div class="side-menu">
      <div class="logo">
        <img src="../assets/images/logo.jpg" alt="Logo" />
      </div>
      <div class="side-menu-items">
        <div class="side-menu-item">
          <el-avatar shape="circle" :size="50" :src="avatarURL"/>
        </div>
        <div class="side-menu-item">
          <el-icon :size=30 name="Dashboard" color="white" @click="toDashboard"><Odometer /></el-icon>
          <p class="item-name">Dashboard</p>
        </div>
        <div class="side-menu-item" @click="toggleExpand">
          <el-icon :size=30 name="Subject" color="white"><Collection /></el-icon>
          <p class="item-name">Subjects</p>
        </div>
        <div class="side-menu-item" @click="toggleExpand2">
          <el-icon :size=30 name="Settings" color="white"><Setting /></el-icon>
          <p class="item-name">Settings</p>
        </div>
        <div class="side-menu-item" @click="toLogin">
          <el-icon :size=30 name="Log Out" color="white"><SwitchButton /></el-icon>
          <p class="item-name">logout</p>
        </div>
      </div>
  
      <!-- <div class="side-menu-item-logout" @click="$emit('to-upcoming')">
        <el-icon :size=30 name="ImportSubject" color="white"><SwitchButton /></el-icon>
        <p class="item-name">logout</p>
      </div> -->
    </div>
  
    <div v-if="isExpanded" class="expanded">
      <div class="list-header"> <p>Subjects</p> </div>
      <div class="subject-list">
        <div v-for="course in courses" :key="course" class="course-name" @click="toSubjectOverview(course.name)">
          <p>{{ course.name}}</p>
        </div>
      </div>
    </div>

    <div v-if="isExpanded2" class="expanded">
      <div class="list-header"> <p>Data Fetching Setting</p> </div>
      <MyButton :buttonText="'Load From Canvas Now'" style="margin-top:2rem" @click="loadData"></MyButton>
      <div class="cur-freq">
        Current Reload Data Freqnecy
        <div v-if="fetchFreq.sec == '0' && fetchFreq.hour=='0' && fetchFreq.day == '0'">
          No Auto Reload Set.
          <MyButton :buttonText="'Enable Auto Reload'" style="margin-top:1rem" v-if="!configDisplay" @click="configDisplay=true"></MyButton>
        </div>
        <div v-else>
          Every {{ fetchFreq.day }} day, {{ fetchFreq.hour }} hour, {{ fetchFreq.sec }} seconds.
          <MyButton :buttonText="'Config Auto Reload'" style="margin-top:1rem" v-if="!configDisplay" @click="configDisplay=true"></MyButton>
        </div>
      </div>

      <div class="new-freq" v-if="configDisplay">
        <el-form label-width="100px">
          <el-form-item label="Days" prop="count">
            <el-input
              v-model.number="updateForm.day"
              placeholder="Days 0-31"
              popper-class="select_drop_box"
              type='number'
            />
          </el-form-item>
          <el-form-item label="Hours" prop="count">
            <el-input
              v-model.number="updateForm.hour"
              placeholder="Hours 0-24"
              popper-class="select_drop_box"
              type='number'
            />
          </el-form-item>
          <el-form-item label="Seconds" prop="count">
            <el-input
              v-model.number="updateForm.sec"
              placeholder="Seconds 0-60"
              popper-class="select_drop_box"
              type='number'
            />
          </el-form-item>
        </el-form>
        <CancelButton  @click="configDisplay=false" style="margin-top: 1rem"></CancelButton>
        <MyButton :buttonText="'Confirm'" style="margin-left:0 ; margin-top: 1rem" @click="submitConfig"></MyButton>
      </div>
    </div>

  </div>

</template>

<script>
import { ElIcon, ElMessage } from 'element-plus'
import { onMounted, ref, watch} from 'vue';
import { useRouter } from 'vue-router';
import MyButton from '@/views/weighting/components/MyButton.vue';
import api from '../api'
import CancelButton from '@/views/weighting/components/CancelButton.vue';

export default {
  name: 'side-menu',
  components: {
    ElIcon,
    MyButton,
    CancelButton
  },
  props: {
    avatarURL: {
      type: String,
      default: 'https://unimelb-dev.instructure.com/images/messages/avatar-50.png'
    },
    displayUpdate: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
    
    }
  },

  setup(props) {
    const isExpanded = ref(false);
    const isExpanded2 = ref(false);
    const fetchFreq = ref(null);
    const toggleExpand = () => {
      isExpanded.value = !isExpanded.value;
      isExpanded2.value = false;
    };
    const toggleExpand2 = () => {
      isExpanded2.value = !isExpanded2.value;
      isExpanded.value = false;
    };

    const router = useRouter();

    const toDashboard = () => {
      router.push({ name: 'Dashboard' })
    }

    const toSubjectOverview = (subject) => {
      router.push({name:'SubjectOverview', params: {subjectName: subject}}); 
    }

    const toLogin = () => {
      router.push({name: 'Login'})
    }

    const courses = ref(JSON.parse(localStorage.getItem("allSubjects")));

    const loadCourses = () => {
    courses.value = JSON.parse(localStorage.getItem("allSubjects"));
    }

    onMounted(() => {
      api.getFetchingFrequency().then((res) => {
        console.log(res);
        if (res.state == 200) {
          const decodeRes = res.data.split(" ");
          const second = decodeRes[0];
          const hour = decodeRes[1];
          const day = decodeRes[2];
          fetchFreq.value = {
            sec : second,
            hour: hour,
            day : day
          }
        } else {
          ElMessage.error("Cannot Fetch Frequency")
        }
      })

    })

    const updateForm = ref({day: null, hour: null, sec: null})

    const options1 = Array.from({ length: 32 }).map((_, idx) => ({
      value: `${idx}`,
      label: `${idx}`,
    }))

    const options2 = Array.from({ length: 25 }).map((_, idx) => ({
      value: `${idx}`,
      label: `${idx}`,
    }))

    const options3 = Array.from({ length: 61 }).map((_, idx) => ({
      value: `${idx}`,
      label: `${idx}`,
    }))

    const configDisplay = ref(false);

    const submitConfig = () => {

      // if (typeof updateForm.day != "number" || typeof updateForm.hour != "number" || typeof updateForm.sec != "number") {
      //   ElMessage.error("Day, Hour and Sec Cannot be Empty at the Same Time")
      //   return null;
      // }

      api.setFrequency(updateForm.value).then((res) => {
        console.log(res);
        if (res.state == 200) {
          ElMessage({
            message: "Update Success",
            type: 'success'
          })
          api.getFetchingFrequency().then((res) => {
            console.log(res);
            if (res.state == 200) {
              const decodeRes = res.data.split(" ");
              const second = decodeRes[0];
              const hour = decodeRes[1];
              const day = decodeRes[2];
              fetchFreq.value = {
                sec : second,
                hour: hour,
                day : day
              }
            } else {
              ElMessage.error("Cannot Fetch Frequency")
            }
          })
        } else {
          ElMessage.error("Cannot Update Frequency")
        }
      })
    }

    const loadData = () => {
      api.loadCanvasData().then((res) => {
        console.log(res);
        if (res.state == 200) {
          ElMessage({
            message: "Fetching Data From Canvas",
            type: 'Warning'
          })
        } else {
          ElMessage.error("Cannot Load data")
        }
      })
    }


    // watch(() => store.getters.allSubjects, (newCourses) => {
    //   courses.value = newCourses;
    // });


    return {
      isExpanded,
      isExpanded2,
      toggleExpand,
      toggleExpand2,
      toDashboard,
      toSubjectOverview,
      courses,
      onMounted,
      fetchFreq,
      options1,
      options2,
      options3,
      updateForm,
      configDisplay,
      submitConfig,
      loadData,
      toLogin
    }
  }
}
</script>

<style lang="scss" scoped>
.whole-side-menu{
  position: sticky;
  top:0;
  display: flex;
  height: 100vh;
  z-index: 10000006 !important;
}

.expanded{
  height: 100%;
  box-shadow: 0 0px 8px rgb(179, 179, 179);
  width: 300px;
  position: fixed; 
  left: 100px; 
  z-index: 10000005 !important;
  background-color: rgb(250, 250, 250);
  padding-right: 1rem;

  .cur-freq{
    margin-top: 5rem;
    margin-bottom: 3rem;
  }
}

.list-header{
  height: 5vh;
  
  p{
    margin-top: 3vh;
    padding: 0;
    border: 0;
    font-weight: bolder;
    font-size: large;
  }
}

.subject-list{

  display: flex;
  
  ///flex-direction: column; 
  ///align-items: center; 
  ///justify-content: space-evenly; 
  height: 95vh;
  flex-direction: column;
  justify-content: flex-start;

  .course-name{
    color: rgb(12, 47, 122);
    font-weight: bold;
    &:hover{
      color: rgb(71, 101, 173);
      margin-left: 1%;
    }
  }
}

.side-menu {
  ///position: fixed;
  position: sticky;
  top:0;
  overflow-y: auto;
  width: 100px;
  height: 100vh;
  background-color: rgb(137, 145, 154);
  ///display: flex;
  ///flex-direction: column;
  ///align-items: center;
  margin: 0;
  ///justify-content: space-between;
  border: 0;
  padding: 0;


  .logo {
    img {
      width: 90px; // 可根据需要调整
      margin-bottom: 30px;
      margin-top: 50px;
      border-radius: 10px
    }
  }

  .side-menu-items {


    .side-menu-item{
      margin: 10px 0;
      margin-bottom: auto;
    
      .item-name{
        margin-top: 0;
        color: rgb(255, 255, 255);
      }

    }

  }


  .side-menu-item-logout{
    margin-top:auto;
    

    .item-name{
      margin-top: 0;
      color: rgb(255, 255, 255);
    }
  }

}
</style>

<style>
.select_drop_box{
  z-index: 3000000 !important;
}
</style>