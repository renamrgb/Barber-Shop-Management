import api from '@/Services/API/api'

export default class ProdutoService {
  async consultarTodos() {
    try {
      const res = await api.get('/produtos')
      return res.data
    } catch (error) {
      return error
    }
  }
  async cadastrar(item) {
    try {
      const res = await api.post('/produtos', item)      
      return res
    } catch (error) {
      return error
    }
  }
  async excluir(id) {
    try {
      const res = await api.delete(`/produtos/${id}`)
      return res
    } catch (error) {
      return error
    }
  }
  async alterar(id, item) {
    try {
      const res = await api.put(`/produtos/${id}`, item)
      return res
    } catch (error) {
      return error
    }
  }
  async buscarUm(id) {
    try {
      const res = await api.get(`/produtos/${id}`)
      return res
    } catch (error) {
      return error
    }
  }
}
