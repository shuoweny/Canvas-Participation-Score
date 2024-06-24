<template>
  <div class="visualisation">
    <span class="header"> {{ $route.params.subjectName }} </span>
    <div class="visualisation-detail" v-if="isDataReady">
      <parallel-segment-bar-chart 
        :subjectInfo="courseInfo" 
        :visualData="parallelSegBarChartData"
      />
      
    </div>

  </div>

</template>


<script>
import { useRoute } from 'vue-router';
import { ref, computed, onMounted } from 'vue';
import { useStore } from 'vuex';
import api from '../../api'
import ParallelSegmentBarChart from "./components/ParallelSegmentBarChart.vue";


export default {
  name: "visualisation-view",
  components: {
    ParallelSegmentBarChart
  },

  setup() {
    const route = useRoute();  
    const store = useStore();  

    const subjectInfo = ref(null);
    const courseInfo = computed(() => {
      const courseName = route.params.subjectName;
      console.log("getting the course info of ", courseName);

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

    const parallelSegBarChartData = ref({})
    const isDataReady = ref(false)

    onMounted(() => {
      api.getParallelBarChartData(courseInfo.value.id).then((res) => {
        console.log(res)
        if (res.state == 200) {
          parallelSegBarChartData.value.yAxis = res.data.categories;
          parallelSegBarChartData.value.series = res.data.seriesData;
          parallelSegBarChartData.value.legend = res.data.segments;
          isDataReady.value = true;
          console.log("data ready : ")
          console.log(parallelSegBarChartData.value)
        } else {
          ElMessage.error("Data Fetching Error")
        }
      })

    })

    return {
      courseInfo,
      onMounted,
      parallelSegBarChartData,
      isDataReady


    };
  }


}
</script>

<style lang="scss" scoped>
.visualisation{
  width: 85vw;


  .header{
    font-weight:900;
    font-size: 2.5rem;
  }

  .visualisation-detail{
    margin-top: 2rem;
  }

}
</style>