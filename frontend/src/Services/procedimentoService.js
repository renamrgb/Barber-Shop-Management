import api from '@/Services/API/api'

export default class ProcedimentoService {
  async consultarTodos() {
    try {
      const res = await api.get('/procedimentos')
      return res.data
    } catch (error) {
      return error
    }
  }
  async cadastrar(item) {
    try {
      const res = await api.post('/procedimentos', item)      
      return res
    } catch (error) {
      return error
    }
  }
  async excluir(id) {
    try {
      const res = await api.delete(`/procedimentos/${id}`)
      return res
    } catch (error) {
      return error
    }
  }
  async alterar(id, item) {
    try {
      const res = await api.put(`/procedimentos/${id}`, item)
      return res
    } catch (error) {
      return error
    }
  }
  async buscarUm(id) {
    try {
      const res = await api.get(`/procedimentos/${id}`)
      return res
    } catch (error) {
      return error
    }
  }
}
