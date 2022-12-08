import api from '@/Services/API/api'

export default class ClienteService {
  async consultarTodos() {
    try {
      const res = await api.get('/clientes')
      return res.data
    } catch (error) {
      return error
    }
  }
  async cadastrar(item) {
    try {
      const res = await api.post('/clientes', item)      
      return res
    } catch (error) {
      return error
    }
  }
  async excluir(id) {
    try {
      const res = await api.delete(`/clientes/${id}`)
      return res
    } catch (error) {
      return error
    }
  }
  async alterar(id, item) {
    try {
      const res = await api.put(`/clientes/${id}`, item)
      return res
    } catch (error) {
      return error
    }
  }
  async buscarUm(id) {
    try {
      const res = await api.get(`/clientes/${id}`)
      return res
    } catch (error) {
      return error
    }
  }
}
