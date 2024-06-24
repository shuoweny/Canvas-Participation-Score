<template>
  <div class = "subject-overview">

    <div class = "content">
      <span class = "header"> {{ $route.params.subjectName }} </span>
      <!-- <ImportButton class = "import"/> -->
      <div class = "statistics-content"> 
        <div class = "statistics-grid">
          <OverviewCard 
            v-for = "statistic in statistics"
            :key = "statistic.id"
            :statistic = "statistic"/>
        </div>
        <ExportButton @click="handleExport"/>
      </div>
    </div>
  </div>
</template>

<script>
import ExportButton from './components/ExportButton.vue';
import OverviewCard from './components/OverviewCard.vue'; 
// import ImportButton from './components/ImportButton.vue';
import { useRouter, useRoute } from 'vue-router';
import { ref, computed, onMounted, Static } from 'vue';
import { useStore } from 'vuex';
import api from '../../api'

export default {
  name: "subject-overview-view",
  components: {
    ExportButton,
    OverviewCard, 
  },
  data () {
    return {
      // statistics: [
      //   {
      //     id: 1, 
      //     name: "Student Average Overall", 
      //     detail: "90%"
      //   }, 
      //   {
      //     id: 2, 
      //     name: "Student Average Last Week", 
      //     detail: "90%"
      //   }, 
      //   {
      //     id: 3, 
      //     name: "Most Popular Assignment", 
      //     detail: "Assignment 1 (98%)"
      //   }, 
      //   {
      //     id: 4, 
      //     name: "Most Popular Activity", 
      //     detail: "Weekly quiz 2 (100%)"
      //   }, 
      //   {
      //     id: 5, 
      //     name: "Least Popular Assignemnt", 
      //     detail: "Assignments (98%)"
      //   }, 
      //   {
      //     id: 6, 
      //     name: "Least Popular Activity", 
      //     detail: "Quizzes (100%)"
      //   }
      // ]
    }
  },
  setup() {
    const router = useRouter();
    const route = useRoute();  
    const store = useStore();  

    const subjectInfo = ref(null);
    const courseInfo = computed(() => {
      const courseName = route.params.subjectName;
      console.log("getting the course info of ", courseName);

      console.log(JSON.parse(localStorage.getItem("allSubjects")))
      
      
      for (let subject of JSON.parse(localStorage.getItem("allSubjects"))) {
        console.log(subject);
        if (subject.name == courseName) {
          subjectInfo.value = subject;
          console.log("Subject found");
          return subjectInfo.value;
        }
      }
      return null;
    });


    const toUpcoming = () => {
      router.push({ name: 'Upcoming' });
    };

    const toUserAccess = (subject) => {
      router.push({name: 'UserAccess', params: {subjectName: subject}}); 
    };

    const toWeighting = (subject) => {
      router.push({name: 'Weighting', params: {subjectName: subject}}); 
    };

    const toStudents = (subject) => {
      router.push({name: 'Students', params: {subjectName: subject}}); 
    }; 

    const handleExport = () => {
      console.log("Downloading excel for ", courseInfo.value.course_code)
      api.downloadExcel(courseInfo.value.course_code)
    }

    const statistics = ref([
        {
          id: 1, 
          name: "Student Average Overall Score", 
          detail: {
            name: null,
            score: null,
            avalibility : false
          },
          placeHolder: "No Score Data Avaliable"
        }, 
        {
          id: 2, 
          name: "Student Average Score Last Week", 
          detail: {
            name: null,
            score: null,
            avalibility : false
          },
          placeHolder: "No Recent Data"
        }, 
        {
          id: 3, 
          name: "Most Popular Activity", 
          detail: {
            name: null,
            score: null,
            avalibility : false
          },
          placeHolder: "Data Not Avaliable"
        }, 
        {
          id: 4, 
          name: "Most Popular Category", 
          detail: {
            name: null,
            score: null,
            avalibility : false
          },
          placeHolder: "Data Not Avaliable"
        }, 
        {
          id: 5, 
          name: "Least Popular Activity", 
          detail: {
            name: null,
            score: null,
            avalibility : false
          },
          placeHolder: "Data Not Avaliable"
        }, 
        {
          id: 6, 
          name: "Least Popular Category", 
          detail: {
            name: null,
            score: null,
            avalibility : false
          },
          placeHolder: "Data Not Avaliable"
        }
      ])

    onMounted(() => {
      var cid = courseInfo.value.id;
      Promise.all(
        [
          api.getAverageParticipationTotal(cid),
          api.getAverageParticipationLastWeek(cid),
          api.getHighestParticipationActivity(cid),
          api.getHighestParticipationActivityType(cid),
          api.getLowestParticipationActivity(cid),
          api.getLowestParticipationActivityType(cid)
        ]).then((responses) => {
          console.log("6 stats: ", responses)
          console.log("Start Processing")
          for (var i = 0; i < responses.length; i++) {
            const res = responses[i];
            if (res.state == 200) {
              if (i == 0 || i == 1) {
                statistics.value[i].detail = {
                  name: "Average Score",
                  score: res.data,
                  avalibility : true
                }
              } else {
                statistics.value[i].detail = res.data
                statistics.value[i].detail.avalibility = true
              }
            }
          }
          console.log("Processed 6 stats: ", statistics.value)
        })
      
    })

    return {
      toUpcoming,
      toUserAccess,
      toWeighting,
      toStudents,
      courseInfo,
      handleExport,
      onMounted,
      statistics
    };
  }


}
</script>

<style lang="scss" scoped>
.subject-overview {
  display: flex;
  .content {
    margin-top: 2em;
    font-family: 'Lato', sans-serif;
    .header{
      font-weight:900;
      font-size: 2.5em;
      text-align: center;
    }
    .import{
      margin-left: 80%;
    }
  } 
  .statistics-content {
    height: 80vh;
    .statistics-grid{
      display: grid;
      grid-template-columns: repeat(3, 1fr);
      row-gap: 3vh;
      column-gap: 2rem;
      margin:1rem;
      ///height: 90%;
    }
  }
}
</style>
