import { createRouter, createWebHistory } from 'vue-router'
import DashboardView from '../views/dashboard/Dashboard.vue'
import Subject from '../views/subjectBasicView/SubjectBasicView.vue'

const routes = [
  {
    path: '/', 
    name: 'Login', 
    component: () => import('../views/login/Login.vue')
  },
  {
    path: '/Dashboard',
    name: 'Dashboard',
    component: DashboardView,
  },
  {
    path: '/subject/:subjectName',
    name: 'SubjectBasicView',
    component: Subject,
    children: [
      {
        path: '', 
        name: 'SubjectOverview',
        component: () => import('../views/subjectOverview/SubjectOverview.vue')
      },
      {
        path: 'userAccess', 
        name: 'UserAccess',
        component: () => import('../views/userAccess/UserAccess.vue')
      },
      {
        path: 'weightings',  
        name: 'Weightings',
        component: () => import('../views/weighting/Weighting.vue')
      },
      {
        path: 'students',  
        name: 'Students',
        component: () => import('../views/students/Students.vue'),
        children: [
          {
            path: 'student/:name/:id',
            name: 'StudentDetail',
            component: () => import("../views/student/Student.vue")
          }
        ]
      },
      {
        path: 'visualisation',  
        name: 'Visualisation',
        component: () => import('../views/visualisation/Visualisation.vue')
      }
    ]
  },
  {
    path: '/upcoming',
    name: 'Upcoming',
    component: () => import('../views/upcoming/Upcoming.vue')
  }, 
  // {
  //   path: '/subjectOverview/:subjectName',
  //   name: 'SubjectOverview', 
  //   component: () => import('../views/subjectOverview/SubjectOverview.vue')
  // }, 
  // {
  //   path: '/userAccess/:subjectName', 
  //   name: 'UserAccess', 
  //   component: () => import('../views/userAccess/UserAccess.vue')
  // }, 
  // {
  //   path: '/weighting/:subjectName', 
  //   name: 'Weighting', 
  //   component: () => import('../views/weighting/Weighting.vue')
  // },
  {
    path: '/testing',
    name: 'Testing',
    component: () => import('../views/TestingView.vue')
  },
  // {  
  //   path: '/students/:subjectName', 
  //   name: 'Students', 
  //   component: () => import('../views/students/Students.vue'),
  // },
  // {
  //   path: '/student/:subjectName/:studentName', 
  //   name: 'Student', 
  //   component: () => import('../views/student/Student.vue') 

  // }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
