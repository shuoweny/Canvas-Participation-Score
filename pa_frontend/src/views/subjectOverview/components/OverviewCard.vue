<template> 
  <el-card class="outer-box" :body-style="{ 'flex-direction': 'column', 'display': 'flex'}"> 
    <div class="card-header"> 
      {{ statistic.name }} 
    </div>
    <div v-if="statistic.detail.avalibility">
      <el-card class="inner-box">
        <span class="card-content">{{ statistic.detail.name }}</span>
        <br>
        <br>
        <span class="card-content">Average Score : {{ statistic.detail.score}}</span>
      </el-card>
    </div>
    <div v-else>
      <el-card class="inner-box">
        <span class="card-content"> {{ statistic.placeHolder }} </span>
      </el-card>
    </div>


  </el-card>
</template>

<script>
export default {
  name: 'OverviewCard', 
  props: {
    statistic: {
      type: Object, 
      required: true 
    }
  }
}
</script>

<style lang="scss" scoped>
.outer-box {
  //display: flex;
  width: 100%;

  .card-body{
    display: flex;
    flex-direction: column;
    justify-content: space-evenly;
    margin:auto; 
  }

  
  background-color: #d9d9d9;
  .card-header{
    text-align: center;
    font-size: 1.2rem;
    font-weight: 600; 
    ///margin-top: 10%;
    word-wrap: break-word;

  }
  .inner-box{
    align-self: center;
    width: 100%;
    background-color: white;
    margin-top: 2rem;
   

    .card-content {
      text-align: center;
      font-size: 1rem;
      font-weight: 600;
    }

    &:hover {
      transition: all 0.3s ease;
      box-shadow: 2px 2px 2px rgba(0, 0, 0, 0.5); 
      transform: translate(-1px, -1px); 
      background-color: rgb(248, 248, 255);
    }

  }
  
  transition: all 0.3s ease;

  &:hover {

    box-shadow: 10px 4px 12px rgba(0, 0, 0, 0.5);
    transform: translate(-1px, -1px); 
    background-color: #cdcdd3;
  }

}
</style>