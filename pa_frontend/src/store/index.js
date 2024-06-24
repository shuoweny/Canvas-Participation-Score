import { createStore } from 'vuex'

export default createStore({
  state: {
    userInfo: {
      id: null,
      name: null,
      token: null,
      avatar_url: null,
      last_name: null,
      first_name: null
    },
    isAuthenticated: false,
    token: null,
    allSubjects: []
  },
  mutations: {
    SET_USER_INFO(state, userInfo) {
      state.userInfo = userInfo;
    },
    SET_AUTHENTICATION_STATUS(state, status) {
      state.isAuthenticated = status;
    },
    SET_TOKEN(state, token) {
      state.token = token;
    },
    SET_ALL_SUBJECTS(state, subjects) {
      state.allSubjects = subjects;
    }
  },
  actions: {
    updateUserInfo({ commit }, userInfo) {
      commit('SET_USER_INFO', userInfo);
    },
    updateAuthenticationStatus({ commit }, status) {
      commit('SET_AUTHENTICATION_STATUS', status);
    },
    updateToken({ commit }, token) {
      commit('SET_TOKEN', token);
    },
    updateAllSubjects({ commit }, subjects) {
      commit('SET_ALL_SUBJECTS', subjects);
    }
  },
  getters: {
    userInfo: state => state.userInfo,
    isAuthenticated: state => state.isAuthenticated,
    token: state => state.token,
    allSubjects: state => state.allSubjects
  }
})