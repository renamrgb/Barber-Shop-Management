import api from '@/Services/API/api'

export default class TipoDespesaService {
  async consultarTodos() {
    try {
      const res = await api.get('/tipoProdutoProcedimento')
      return res.data
    } catch (error) {
      return error
    }
  }
  async cadastrar(item) {
    try {
      const res = await api.post('/tipoProdutoProcedimento', item)      
      return res
    } catch (error) {
      return error
    }
  }
  async excluir(id) {
    try {
      const res = await api.delete(`/tipoProdutoProcedimento/${id}`)
      return res
    } catch (error) {
      return error
    }
  }
  async alterar(id, item) {
    try {
      const res = await api.put(`/tipoProdutoProcedimento/${id}`, item)
      return res
    } catch (error) {
      return error
    }
  }
  async buscarUm(id) {
    try {
      const res = await api.get(`/tipoProdutoProcedimento/${id}`)
      return res
    } catch (error) {
      return error
    }
  }
}
