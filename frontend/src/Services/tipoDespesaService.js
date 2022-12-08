import api from '@/Services/API/api'

export default class TipoDespesaService {
  async consultarTodos() {
    try {
      const res = await api.get('/tipoDespesas')
      return res.data
    } catch (error) {
      return error
    }
  }
  async cadastrar(item) {
    try {
      const res = await api.post('/tipoDespesas', item)      
      return res
    } catch (error) {
      return error
    }
  }
  async excluir(id) {
    try {
      const res = await api.delete(`/tipoDespesas/${id}`)
      return res
    } catch (error) {
      return error
    }
  }
  async alterar(id, item) {
    try {
      const res = await api.put(`/tipoDespesas/${id}`, item)
      return res
    } catch (error) {
      return error
    }
  }
  async buscarUm(id) {
    try {
      const res = await api.get(`/tipoDespesas/${id}`)
      return res
    } catch (error) {
      return error
    }
  }
}
