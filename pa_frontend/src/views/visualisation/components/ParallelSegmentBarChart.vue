<template>
  <div>
    <div class="diagram-title"></div>
    <div ref="chart" style="width: 100%; height:80vh;"></div>
  </div>
</template>

<script>
import * as echarts from 'echarts';

export default {
  name: 'ParallelSegmentBarChart',
  props: {
    subjectInfo: {
      type: Object,
      default: null
    },
    visualData: {
      type: Object,
      default: null
    }
  },
  data() {
    return {
      chart: null,
      categories: ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'],
      seriesData: [
        { name: 'Segment1', type: 'bar', stack: 'total', data: [30, 40, 20, 50, 40, 60, 70, 80, 90, 10] },
        { name: 'Segment2', type: 'bar', stack: 'total', data: [40, 30, 50, 20, 30, 20, 10, 10, 5, 40] },
        { name: 'Segment3', type: 'bar', stack: 'total', data: [30, 30, 30, 30, 30, 20, 20, 10, 5, 50] }
      ],
      segments:[],

      option: {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        legend: {
          data: []
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'value',
          max: 100,
          name: '%'
        },
        yAxis: {
          type: 'category',
          data: [],
          name: 'Assignment'
        },
        series: []
      }
    };
  },
  beforeMount() {
    console.log("Before Barchart Mount")
    console.log(this.visualData)
    this.option.yAxis.data = this.visualData.yAxis;
    this.option.series = this.visualData.series;
    this.option.legend.data = this.visualData.legend;
  },
  mounted() {
    window.addEventListener('resize', this.resizeChart);
    this.createChart();
  },
  beforeDestroy() {
    window.removeEventListener('resize', this.resizeChart);
    if (this.chart) {
      this.chart.dispose();
    }
  },
  methods: {
    createChart() {
      this.chart = echarts.init(this.$refs.chart);
      this.chart.setOption(this.option);
    },
    resizeChart() {
      if (this.chart) {
        this.chart.resize();
      }
    }
  }
};
</script>

<style scoped>
.detail-list {
  height: 100%;
  width: 100%;
}
</style>

