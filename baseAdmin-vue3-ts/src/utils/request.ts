import axios from 'axios'
import type { AxiosInstance, AxiosRequestConfig, AxiosResponse } from 'axios'
import { message } from 'ant-design-vue'

const service: AxiosInstance = axios.create({
    baseURL: import.meta.env.VITE_API_URL,
    timeout: 15000
})

service.interceptors.request.use(
    (config) => {
        // 可以在这里添加token等
        return config
    },
    (error) => {
        return Promise.reject(error)
    }
)

service.interceptors.response.use(
    (response: AxiosResponse) => {
        const { code, msg, data } = response.data
        if (code === 200) {
            return data
        }
        message.error(msg || '请求失败')
        return Promise.reject(new Error(msg || '请求失败'))
    },
    (error) => {
        message.error(error.message || '请求失败')
        return Promise.reject(error)
    }
)

export default service 