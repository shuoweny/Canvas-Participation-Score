import axios from "axios";
import path from "../api/path";
import router from "@/router";
import { ElMessage } from "element-plus";

const service = axios.create({
  baseURL: path.baseUrlCICD,
  timeout:10000,
  headers:{
    'Content-Type' : 'application/json',
  },
  // withCredentials : true,
  crossDomain: true
})

service.interceptors.request.use(
  (config) => {
    // if(config.url !== "/sys/login"){   
    // 	config.headers.token = sessionStorage.getItem("token");
    // }
    return config;
  },
	(error)=>{
		return Promise.reject(error);
	}
)


service.interceptors.response.use(
  (res) => {
    console.log(res)
		let code = res.status
		if(code===200){         
			return res.data
    } else if (code == 401) {
      router.push({name: 'Login'})
      ElMessage.error("Authentication Invalid")
      // ElMessage.error(res.message)
    } else {
      router.push({name: 'Login'})
      ElMessage.error("ERROR")
      return res.data
    }
	},
  (error) => {
    router.push({name: 'Login'})
		return Promise.reject(error);
	}
)

export default service;