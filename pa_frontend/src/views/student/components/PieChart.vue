<template>
  <div ref="chart" style="height: 400px;"></div>
</template>

<script>
import * as echarts from 'echarts/core';
import {
  PieChart
} from 'echarts/charts';
import {
  TitleComponent,
  TooltipComponent,
  LegendComponent
} from 'echarts/components';
import {
  CanvasRenderer
} from 'echarts/renderers';

echarts.use([
  TitleComponent,
  TooltipComponent,
  LegendComponent,
  PieChart,
  CanvasRenderer
]);

export default {
  props: {
    pieData: {
      type: Object,
      default: {
        completed: 1,
        notYet: 1,
        overDue : 1
      }
    }
  },
  data() {
    return {
      chart: null,
    };
  },
  mounted() {
    console.log("Passed in pie data is : ", this.pieData)
    this.chart = echarts.init(this.$refs.chart);
    this.chart.setOption({
      title: {
        text: 'Student Participation Pie Chart',
        subtext: 'Student Name',
        left: 'center'
      },
      tooltip: {
        trigger: 'item'
      },
      legend: {
        orient: 'vertical',
        left: 'left',
      },
      series: [
        {
          name: 'Assignment Completion',
          type: 'pie',
          radius: '70%',
          data: [
            { value: this.pieData.completed, name: 'Done' },
            { value: this.pieData.notYet, name: 'Catching up' },
            { value: this.pieData.overDue, name: 'Not done' },
          ],
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        }
      ]
    });
  },
  beforeUnmount() {
    if (this.chart) {
      this.chart.dispose();
    }
  },
};
</script>
