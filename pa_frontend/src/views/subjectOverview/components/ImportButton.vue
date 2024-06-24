<template>
  <el-upload class="import">
    <el-button class="button">
      <el-icon class="icon"><UploadFilled /></el-icon>
      Import <br> External
    </el-button>
  </el-upload>
</template>

<style lang="scss">
.import {
  .button{
    background-color: #0c3c75;
    color: white; 
    height: 50px;
    width: 120px;
    text-align: left;
    font-size: 16px;
    font-family: Arial, Helvetica, sans-serif;
    font-weight: 600;
    
    .icon{
      margin-right: 8%;
      margin-left: -10%;
    }
  }
  // SCSS样式...
  //color: "#365e8d";
}
</style>