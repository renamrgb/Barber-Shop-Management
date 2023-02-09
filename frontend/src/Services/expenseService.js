import api from '@/Services/API/api'

export default class ExpenseService {
  url = '/expenses'

  async consultarTodos() {
    try {
      const res = await api.get(this.url)
      return res.data
    } catch (error) {
      return error
    }
  }
  async cadastrar(item) {
    try {
      const res = await api.post(this.url, item)
      return res
    } catch (error) {
      return error
    }
  }
  async generateInstallments(item) {
    try {
      const res = await api.post(`${this.url}/generateInstallments`, item)
      return res.data
    } catch (error) {
      return error
    }
  }
}
