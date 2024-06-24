import { ElMessage } from 'element-plus'
import axios from '../utils/request'
import path from './path'


const api = {
  getUserInfo(id) {
    return axios.get(path.user + path.getUserById + '/' + id)
  },

  getSubjectsData() {
    return axios.get(path.course + path.getAllCourse)
  },

  getSubjectByName(name) {
    return axios.get(path.course + path.getCourseByCourseName + "/" + name)
  },

  getAllCourseStudents(subjectId) {
    return axios.get(path.student + path.getStudentByCourseId + '/' + subjectId)
  },

  getAllStudentScores(cid) {
    return axios.get(path.student + path.getAllStudentScores + '/' + cid)
  },

  getStudentDetailScoreInfo(cid, sid) {
    return axios.get(path.score + path.getScoreByCidAndSid + '/' + cid + '/' + sid)
  },

  sendEmailToStuent(cid, sid) {
    return axios.get(path.email + path.sendIndividual + '/' + cid + '/' + sid)
  },

  sendEmailToAll(cid) {
    return axios.get(path.email + path.sendToAll + "/" + cid)
  },

  getAllUsers(cid) {
    return axios.get(path.teacher + path.getTeacherByCourseId + "/" + cid)
  },

  setUserAccess(cid, uid, access) {
    return axios.post(path.teacher + path.setTeacherAccess + '/' + cid + '/' + uid, { 'access': access })
  },


  downloadPdf(courseId, userId) {
    axios({
      url: '/pdf/download/' + courseId + '/' + userId,
      method: 'GET',
      responseType: 'blob'
    })
      .then((response) => {
        const blob = response;
        console.log(blob)
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = userId + '_Report.pdf';
        a.click();
        window.URL.revokeObjectURL(url);
        ElMessage({
          message: "Report Downloaded",
          type: 'success'
        })
      })
      .catch((error) => {
        console.error("Error downloading the file:", error);
        ElMessage.error("Report Download Failed")
      });
  },

  downloadExcel(course_code) {
    axios({
      url: '/participation/exportCompleteExcel/' + course_code,
      method: 'GET',
      responseType: 'blob'
    })
      .then((response) => {
        const blob = response;
        console.log(blob)
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = course_code + '_participation.xlsx';
        a.click();
        window.URL.revokeObjectURL(url);
        ElMessage({
          message: "Excel Downloaded",
          type: 'success'
        })
      })
      .catch((error) => {
        console.error("Error downloading the file:", error);
        ElMessage.error("Excel Download Failed")
      });
  },

  getAverageParticipationTotal(cid) {
    return axios.get(path.overview + "/getAverageParticipationTotal" + '/' + cid);
  },

  getAverageParticipationLastWeek(cid) {
    return axios.get(path.overview + "/getAverageParticipationLastWeek" + '/' + cid);
  },

  getHighestParticipationActivity(cid) {
    return axios.get(path.overview + "/getHighestParticipationActivity" + '/' + cid);
  },

  getHighestParticipationActivityType(cid) {
    return axios.get(path.overview + "/getHighestParticipationActivityType" + '/' + cid);
  },

  getLowestParticipationActivity(cid) {
    return axios.get(path.overview + "/getLowestParticipationActivity" + '/' + cid);
  },

  getLowestParticipationActivityType(cid) {
    return axios.get(path.overview + "/getLowestParticipationActivityType" + '/' + cid);
  },

  getParallelBarChartData(cid) {
    return axios.get(path.chart + path.getAssignmentBarChartsData + '/' + cid)
  },

  getPieData(cid, sid) {
    return axios.get(path.chart + path.getIndividualPieData + '/' + cid + '/' + sid)
  },

  getAllAssignmentByCid(cid) {
    return axios.get(path.assignment + path.getAssignmentByCourseId + '/' + cid)
  },
    // {
  //   "weights": [
  //       {
  //           "mode": "group",
  //           "type": "tutorial",
  //           "weight": 15,
  //           "method": "complete"
  //       },
  //       {
  //           "mode": "group",
  //           "type": "lecture",
  //           "weight": 60,
  //           "method": "weight"
  //       },
  //       {
  //           "mode": "group",
  //           "type": "h5p",
  //           "weight": 30,
  //           "method": "complete"
  //       },
  //       {
  //           "mode": "group",
  //           "type": "canvas",
  //           "weight": 40,
  //           "method": "complete"
  //       },
  //       {
  //           "mode": "single",
  //           "assignmentId": 45410,
  //           "weight": 20,
  //           "method": "complete"
  //       }
  //   ]
  // }

  updatedWeight(cid, form) {
    // const format = {
    //   "weights": [
    //     {
    //       "mode": form.mode,
    //       "type": form.type,
    //       "weight": parseInt(form.weight),
    //       "method": form.method
    //     }
    //   ]
    // };
    form.weight = parseInt(form.weight);
    return axios.post(path.assignment + path.changeWeight + '/' + cid, { weights : [form] })
  },

  getFetchingFrequency() {
    return axios.get(path.save + path.getFreq)
  },

  setFrequency(form) {
    for (const key in form) {
      if (form[key] == null) { 
        form[key] = 0;
      }
    }
    const cronExp = form.sec + ' ' + form.hour + ' ' + form.day + " * * ?"
    console.log("CRON-EXP ", cronExp);
    return axios.post(path.save + path.setFreq, {'cron' : cronExp})
  },

  loadCanvasData() {
    return axios.get(path.save + "/all")
  },

  


  getCurrentStaff(){
    return axios.get( path.staff + '/getCurrentStaff')
  },
  getCurrentStaffSubjectAccess(){
    return axios.get(path.access + '/getCurrentSubjectAccess')
  },
  getCurrentSubjectStaffAccess(subjectName){
    return axios.get((path.access + '/getCurrentStaffAccess' + '/' + subjectName))
  }
  
}
export default api
