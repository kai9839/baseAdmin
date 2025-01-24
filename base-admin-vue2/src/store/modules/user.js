import { login, logout } from '@/api/auth'
import { Message } from 'element-ui'

const state = {
  token: localStorage.getItem('token'),
  userInfo: null
}

const mutations = {
  SET_TOKEN: (state, token) => {
    state.token = token
    localStorage.setItem('token', token)
  },
  SET_USER: (state, user) => {
    state.userInfo = user
  },
  CLEAR_USER: (state) => {
    state.token = null
    state.userInfo = null
    localStorage.removeItem('token')
  }
}

const actions = {
  // 登录
  login({ commit }, userInfo) {
    return new Promise((resolve, reject) => {
      login(userInfo)
        .then(response => {
          const { token, user } = response.data
          commit('SET_TOKEN', token)
          commit('SET_USER', user)
          resolve()
        })
        .catch(error => {
          Message.error(error.message || '登录失败')
          reject(error)
        })
    })
  },

  // 登出
  logout({ commit }) {
    return new Promise((resolve, reject) => {
      logout().then(() => {
        commit('CLEAR_USER')
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
} 