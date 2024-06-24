<template> 
 <div class="login">
      <span> Sign in</span>
      <div class="username"> 
        <span> Username <br> </span>
        <span class="example"> e.g. jsmith (not email address) <br> </span>
        <input class="input_username" type="text" v-model="username" disabled>
      </div>
      <div class="password"> 
        <span> Password <br> </span>
        <input class="input_password" type="password" v-model="password" disabled>
      </div>
      <LoginButton class="login_button" @click="proceedLogin"> </LoginButton>
  </div>
</template>

<script>
import LoginButton from './components/LoginButton.vue';
import { ElMessage } from 'element-plus';

export default {
  name: "login", 
  components: {
    LoginButton,
  },
  data() {
    return {
      username:"Shuowen Yu", 
      password:"xxxxxxxx", 
    }
  }, 
  methods: {
    proceedLogin() {
      if (this.username != "" && this.password != "") {
        // proceed login API
        console.log("Username ", this.username);
        // var that = this;

        this.toDashboard()
        

      } else {
        ElMessage.error("Please enter the Username and Password.")
      }
    },
    toDashboard() {
      this.$router.push('Dashboard')
    }
  }
}
</script> 

<style lang="scss" scoped> 

  .login{
  
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    font-size: 25px; 
    color: black; 

    .username{
      font-size: 18px; 
      text-align: left;
      margin-top: 20px; 
      
      .input_username {
        margin-top:4px; 
        width: 350px; 
        height: 35px; 
      }
    }

    .password{
      font-size: 18px;
      text-align: left;
      margin-top: 10px; 

      .input_password{
        margin-top:4px; 
        width: 350px; 
        height: 35px; 
      }
    }

    .login_button{
      margin-top: 25px; 
    }

  }
</style>

