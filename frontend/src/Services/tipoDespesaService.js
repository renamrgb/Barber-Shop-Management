import api from '@/Services/API/api'

export default class TipoDespesaService {
  url = '/expenseTypes'

  async consultarTodos() {
    try {
      const res = await api.get(this.url)
      return res.data
    } catch (error) {
      return error
    }
  }
  async getByDescription(description) {
    try {
      const res = await api.get(`${this.url}?description=${description}`)
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
  async excluir(id) {
    try {
      const res = await api.delete(`${this.url}/${id}`)
      return res
    } catch (error) {
      return error
    }
  }
  async alterar(id, item) {
    try {
      const res = await api.put(`${this.url}/${id}`, item)
      return res
    } catch (error) {
      return error
    }
  }
  async buscarUm(id) {
    try {
      const res = await api.get(`${this.url}/${id}`)
      return res.data
    } catch (error) {
      return error
    }
  }
}
