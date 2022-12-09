import api from '@/Services/API/api'

export default class ProfissionalService {
  async consultarTodos() {
    try {
      const res = await api.get('/profissionais')
      return res.data
    } catch (error) {
      return error
    }
  }
  async cadastrar(item) {
    try {
      const res = await api.post('/profissionais', item)      
      return res
    } catch (error) {
      return error
    }
  }
  async excluir(id) {
    try {
      const res = await api.delete(`/profissionais/${id}`)
      return res
    } catch (error) {
      return error
    }
  }
  async alterar(id, item) {
    try {
      const res = await api.put(`/profissionais/${id}`, item)
      return res
    } catch (error) {
      return error
    }
  }
  async buscarUm(id) {
    try {
      const res = await api.get(`/profissionais/${id}`)
      return res
    } catch (error) {
      return error
    }
  }
}
