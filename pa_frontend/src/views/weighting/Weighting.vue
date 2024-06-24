<template>
  <div class= "weighting" v-if="!isLoading">

    <div class="content"> 


      <span class = "header"> {{ $route.params.subjectName }} </span>


      <div class="table"> 


        <el-table :data="assignments" v-model="assignments" style="font-size: 1.2rem;">

          <!-- Assignment Weights -->
          <el-table-column type="expand">
            <template #default="props">
              <div class="expand_table">


                  <el-table 
                    :data="props.row.activities" 
                    style="font-size: 1.2rem; margin-top:1rem; margin-bottom:3rem; width:80%"
                    max-height="40vh"> 
                    <el-table-column prop="name" label="Assignment Name"/>
                    <el-table-column label="Weight">
                      <template #default="scope"> 
                        <span v-if="scope.row.participationWeighting!=null"> {{ scope.row.participationWeighting }} </span>
                        <span v-else> {{ scope.row.defaultParticipationWeighting }} </span>
                      </template>

                    </el-table-column>

                    <el-table-column label="Calculation Method" #default="scope">
                      {{ scope.row.method==null ? scope.row.default_method : scope.row.method }}
                    </el-table-column>

                    <el-table-column label="Config Assignment Weight" #default="scope">
                      <MyButton @click="openAssignmentConfigDialog(scope.row)"/>
                    </el-table-column>  
                  </el-table>


              </div>
            </template>
        </el-table-column>

        <!-- Lecture, Canvas ... -->
        <el-table-column prop="type" label="Assignment Type"/>

        <!-- Default Score -->
        <el-table-column prop="defaultScore">
          <template #header> 
            <span>Default Weight </span> 
            <el-icon class="info" @click="showInfo"><InfoFilled /></el-icon>
          </template>
        </el-table-column>

        <!-- Total Score -->
        <el-table-column prop="totalScore" >
          <template #header>
            <span> Total Score </span>
          </template>
        </el-table-column>

        <!-- default method -->
        <el-table-column prop="defaultMethod" >
          <template #header>
            <span> Default Method </span>
          </template>
        </el-table-column>

        <!-- <el-table-column prop="calculationMode" label="Calculation Model"/> -->

        <el-table-column label="Config Default Weight" #default="scope"> 
          <MyButton :buttonText="'Config Default'" @click="openDefaultDialog(scope.row)"/>
        </el-table-column>
      </el-table>
        

      </div>
    
    
    </div>



  </div>


  <!-- Dialog of Default Configuration -->
  <el-dialog v-model="displayDefaultDialog" title="Configuring Default Weight">
    <h3>Editing Default Config of {{defaultForm.type}}</h3>
    <el-form :model="defaultForm">
      <el-form-item label="Default Weight" :label-width="formLabelWidth">
        <el-input v-model="defaultForm.weight" autocomplete="off" />
      </el-form-item>
      <el-form-item label="Default Calculation Method" :label-width="formLabelWidth">
        <el-select v-model="defaultForm.method">
          <el-option label="Weight" value="weight" />
          <el-option label="Complete" value="complete" />
        </el-select>
      </el-form-item>
    </el-form>

    <template #footer>
      <span class="dialog-footer">
        <CancelButton @click="cancelDefaultConfig()"/>
        <MyButton :buttonText="'Confirm'" @click="confirmDefaultChange"/>
      </span>
    </template>
  </el-dialog>


  <!-- Dialog of Individual Configuration -->
  <el-dialog v-model="displayIndividualDialog" title="Configuring Assignment Weight">
    <h3>Editing Config of {{individualForm.assignmentId}}</h3>
    <el-form :model="individualForm">
      <el-form-item label="Default Weight" :label-width="formLabelWidth">
        <el-input v-model="individualForm.weight" autocomplete="off" />
      </el-form-item>
      <el-form-item label="Calculation Method" :label-width="formLabelWidth">
        <el-select v-model="individualForm.method">
          <el-option label="Weight" value="weight" />
          <el-option label="Complete" value="complete" />
        </el-select>
      </el-form-item>
    </el-form>

    <template #footer>
      <span class="dialog-footer">
        <CancelButton @click="cancelIndividualConfig()"/>
        <MyButton :buttonText="'Confirm'" @click="confirmIndividualChange"/>
      </span>
    </template>
  </el-dialog>

</template>

<script>
import CancelButton from './components/CancelButton.vue';
import MyButton from './components/MyButton.vue'; 
import { useRouter, useRoute } from 'vue-router';
import { useStore } from 'vuex';
import { ElMessage } from 'element-plus';
import { computed, ref } from 'vue';
import api from '../../api';

export default {
  name: "weighting-view",
  components: {
    CancelButton, 
    MyButton,

  }, 
  data() {
    return {
      user: {
        id: 1,
        lastname: "Robertson", 
        firstname: "Tom",
        role: "Subject Coordinator", 
        hasAccess: true, 
      }, 
      rawData: null,
      assignments: [
        {type: "canvas", defaultScore: null, totalScore: 0, activities :[], defaultMethod: null},
        {type: "lecture", defaultScore:null, totalScore: 0, activities: [], defaultMethod: null},
        {type: "h5p", defaultScore: null, totalScore: 0, activities: [], defaultMethod: null},
        {type: "tutorial", defaultScore: null, totalScore: 0, activities: [], defaultMethod: null},
        {type: "other", defaultScore: null, totalScore: 0, activities: [], defaultMethod: null},
      ],
      displayDefaultDialog: false,
      defaultTarget: null,
      displayIndividualDialog: false,
      defaultForm: null,
      individualForm: null,
      formLabelWidth: "10vw",
      courseInfo: null,
      isLoading: true,
      

    }
  },
  methods: {
    openDefaultDialog(info) {
      console.log("Editing Default Config ", info.type)
      this.defaultForm = {
            mode: "group",
            type: info.type,
            weight: info.defaultScore,
            method: 'weight'
      }
        
      this.displayDefaultDialog = true;
    },
    openAssignmentConfigDialog(assignment) {
      console.log("Editing Individual Config", assignment.name)
      this.individualForm = {
        mode: "single",
        assignmentId: assignment.id,
        weight: assignment.participationWeighting == null ? assignment.defaultParticipationWeighting : assignment.participationWeighting,
        method: assignment.method
      }
      this.displayIndividualDialog = true;
    },
    cancelDefaultConfig() {
      console.log("Cancel Editing Default Config");
      this.displayDefaultDialog = false;
      // this.defaultForm = null;
    },
    cancelIndividualConfig() {
      console.log("Cancel Editing Assginment Config");
      this.displayIndividualDialog = false;
      // this.individualForm = null;
    },
    confirmDefaultChange() {
      console.log("Confirm Edit Default Config");
      var that = this;
      api.updatedWeight(this.courseInfo.id, this.defaultForm).then((res) => {
        console.log(res)
        if (res.state == 200) {
          ElMessage({
            message: 'update success',
            type: 'success'
          })
          that.displayDefaultDialog = false;
          that.fetchAssignments();
        } else {
          ElMessage.error("Update Fail")
          that.cancelDefaultConfig();
        }
      })
      // this.defaultForm = null;
    },
    confirmIndividualChange() {
      console.log("Confirm Edit Assginment Config");
      var that = this;
      api.updatedWeight(this.courseInfo.id, this.individualForm).then((res) => {
        console.log(res)
        if (res.state == 200) {
          ElMessage({
            message: 'update success',
            type: 'success'
          })
          that.displayIndividualDialog = false;
          that.fetchAssignments();
        } else {
          ElMessage.error("Update Fail")
          that.cancelIndividualConfig();
        }
      })
      // this.individualForm = null;
    },

    async fetchAssignments() {
      var that = this;
      const res = await api.getAllAssignmentByCid(that.courseInfo.id)
      console.log("res of all assignment ", res)
      if (res.state = 200) {
        that.rawData = res.data;
        for (var i = 0; i < 5; i++){
          that.assignments[i].activities = [];
          that.assignments[i].totalScore = 0;
          that.assignments[i].defaultScore = null;
          that.assignments[i].defaultMethod = null;
        }
        console.log("Processing Weighting data")
        for (var ass of this.rawData) {
          switch (ass.type) {
            case "canvas":
              that.assignments[0].activities.push(ass)
              if (ass.participationWeighting != null) {
                that.assignments[0].totalScore += ass.participationWeighting
              } else {
                that.assignments[0].totalScore += ass.defaultParticipationWeighting
              }
              if (that.assignments[0].defaultScore == null) {
                that.assignments[0].defaultScore = ass.defaultParticipationWeighting
              }
              if (that.assignments[0].defaultMethod == null) {
                that.assignments[0].defaultMethod = ass.default_method;
              }
              break;

            case "lecture":
              that.assignments[1].activities.push(ass)
              if (ass.participationWeighting != null) {
                that.assignments[1].totalScore += ass.participationWeighting
              } else {
                that.assignments[1].totalScore += ass.defaultParticipationWeighting
              }
              if (that.assignments[1].defaultScore == null) {
                that.assignments[1].defaultScore = ass.defaultParticipationWeighting
              }
              if (that.assignments[1].defaultMethod == null) {
                that.assignments[1].defaultMethod = ass.default_method;
              }
              break;

            case "h5p":
              that.assignments[2].activities.push(ass)
              if (ass.participationWeighting != null) {
                that.assignments[2].totalScore += ass.participationWeighting
              } else {
                that.assignments[2].totalScore += ass.defaultParticipationWeighting
              }
              if (that.assignments[2].defaultScore == null) {
                that.assignments[2].defaultScore = ass.defaultParticipationWeighting
              }
              if (that.assignments[2].defaultMethod == null) {
                that.assignments[2].defaultMethod = ass.default_method;
              }
              break;

            case "tutorial":
              that.assignments[3].activities.push(ass)
              if (ass.participationWeighting != null) {
                that.assignments[3].totalScore += ass.participationWeighting
              } else {
                that.assignments[3].totalScore += ass.defaultParticipationWeighting
              }
              if (that.assignments[3].defaultScore == null) {
                that.assignments[3].defaultScore = ass.defaultParticipationWeighting
              }
              if (that.assignments[3].defaultMethod == null) {
                that.assignments[3].defaultMethod = ass.default_method;
              }
              break;

            default:
              that.assignments[4].activities.push(ass)
              if (ass.participationWeighting != null) {
                that.assignments[4].totalScore += ass.participationWeighting
              } else {
                that.assignments[4].totalScore += ass.defaultParticipationWeighting
              }
              if (that.assignments[4].defaultScore == null) {
                that.assignments[4].defaultScore = ass.defaultParticipationWeighting
              }
              if (that.assignments[4].defaultMethod == null) {
                that.assignments[4].defaultMethod = ass.default_method;
              }
            
          }
        }
      } else {
        ElMessage.error("Failed to Fetch Assignment Weights")
        throw new Error("Failed to Fetch Assignment Weights")
      }
    }

    
  },
  async mounted() {
    var that = this;
    try {
      console.log(that.$route.params.subjectName)
      const res1 = await api.getSubjectByName(that.$route.params.subjectName.replace(" ","%20"))
      console.log("res of get subject by name : ", res1.state)
      if (res1.state != 200) {
        throw new Error("Subject Not Found")
      }
      that.courseInfo = res1.data[0];

      that.fetchAssignments();


    } catch(error) {
      console.error("An error occured: ", error)
    } finally {
      this.isLoading = false;
    }

    // api.getAllAssignmentByCid(this.courseInfo.id).then((res) => {
    //   console.log(res)
    //   if (res.state = 200) {
    //     that.rawData = res.data;
    //     console.log("Processing Weighting data")
    //     for (var ass of this.rawData) {
    //       switch (ass.type) {
    //         case "canvas":
    //           that.assignments[0].activities.push(ass)
    //           if (ass.participationWeighting != null) {
    //             that.assignments[0].totalScore += ass.participationWeighting
    //           } else {
    //             that.assignments[0].totalScore += ass.defaultParticipationWeighting
    //           }
    //           if (that.assignments[0].defaultScore == null) {
    //             that.assignments[0].defaultScore = ass.defaultParticipationWeighting
    //           }
    //           break;

    //         case "lecture":
    //           that.assignments[1].activities.push(ass)
    //           if (ass.participationWeighting != null) {
    //             that.assignments[1].totalScore += ass.participationWeighting
    //           } else {
    //             that.assignments[1].totalScore += ass.defaultParticipationWeighting
    //           }
    //           if (that.assignments[1].defaultScore == null) {
    //             that.assignments[1].defaultScore = ass.defaultParticipationWeighting
    //           }
    //           break;

    //         case "h5p":
    //           that.assignments[2].activities.push(ass)
    //           if (ass.participationWeighting != null) {
    //             that.assignments[2].totalScore += ass.participationWeighting
    //           } else {
    //             that.assignments[2].totalScore += ass.defaultParticipationWeighting
    //           }
    //           if (that.assignments[2].defaultScore == null) {
    //             that.assignments[2].defaultScore = ass.defaultParticipationWeighting
    //           }
    //           break;

    //         case "tutorial":
    //           that.assignments[3].activities.push(ass)
    //           if (ass.participationWeighting != null) {
    //             that.assignments[3].totalScore += ass.participationWeighting
    //           } else {
    //             that.assignments[3].totalScore += ass.defaultParticipationWeighting
    //           }
    //           if (that.assignments[3].defaultScore == null) {
    //             that.assignments[3].defaultScore = ass.defaultParticipationWeighting
    //           }
    //           break;

    //         default:
    //           that.assignments[4].activities.push(ass)
    //           if (ass.participationWeighting != null) {
    //             that.assignments[4].totalScore += ass.participationWeighting
    //           } else {
    //             that.assignments[4].totalScore += ass.defaultParticipationWeighting
    //           }
    //           if (that.assignments[4].defaultScore == null) {
    //             that.assignments[4].defaultScore = ass.defaultParticipationWeighting
    //           }
            
    //       }
    //     }
    //   } else {
    //     ElMessage.error("Failed to Fetch Assignment Weights")
    //   }
    // })
    
  },

  setup() {
    const router = useRouter();

    const toUpcoming = () => {
      router.push({ name: 'Upcoming' });
    };



    

    const showInfo =() => {
      ElMessage({
        showClose: true, 
        message: "Default Scores: \n These are scores set for each activity groups and each activity instance" +
        "within these group. Numbers on activity groups means the default score for all individual activities" +
        "within the group. Numbers on an individual activities are just the score for this activity - black means" +
        "means a coordinator has set it to a particular number, and grey means it's following the group default." +
        "Changing a group's default doesn't change individul activity scores that have already been set manually.",
        duration: 0,
      })
    };

    return {
      toUpcoming, 
      showInfo
    };
  }


}
</script>

<style lang="scss"> 
.weighting{
  display: flex; 
  
  .content{
    margin-top: 2rem;
    font-family: 'Lato', sans-serif;
    margin-left: 2rem;
    width: 80vw;

    .header{
      font-weight:900;
      font-size: 40px;
    }

    .table{
      margin-top: 50px;

      .expand_table {
        margin-left: 50px; 
      }

      th{
        color: black;
        ///height: 120px; 
        font-weight: 700;
        ///font-size: 28px;
        background-color: #0a2ec050;
        //border-radius: 1rem;

        
        .info{
          margin-left: 50px; 
          color: #0c3c75;

          .ElMessage{
            font-size: 25px; 
          }
        }
      }

      input {
        height: 35px;
        /* font-weight: 500; */
        /* font-size: 28px; */
        /* width: 170px; */
        text-align: center;
      }

      td {
          color: black; 
          height: 4rem;
          font-weight: 500;
          font-size: 1rem;
          text-align: left; 


          th{
            background-color: #0a80c028;
          }
        }
    }

    .buttons{
      margin-top: 100px; 
    }
  }
}
</style>