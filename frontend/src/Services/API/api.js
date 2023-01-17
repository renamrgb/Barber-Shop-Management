import axios from 'axios'

const api = axios.create({
  baseURL: 'http://localhost:8081',
})
api.interceptors.request.use(async (config) => {
  try {
    const token = localStorage.getItem('access_token')    
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  } catch (error) {
    console.log(error)
  }
})
export default api
