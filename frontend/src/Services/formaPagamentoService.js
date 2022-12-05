import api from '@/Services/API/api'

export default class FormaPagamentoService {
  async consultarFormasPagamento() {
    try {
      const res = await api.get('/formaPagamentos')
      return res.data
    } catch (error) {
      return error
    }
  }
  async cadastrar(formaPagamento) {
    try {
      const res = await api.post('/formaPagamentos', formaPagamento)
      return res
    } catch (error) {
      return error
    }
  }
  async excluir(id) {
    try {
      const res = await api.delete(`/formaPagamentos/${id}`)
      return res
    } catch (error) {
      return error
    }
  }
  async alterar(id, formaPagamento) {
    try {
      const res = await api.put(`/formaPagamentos/${id}`, formaPagamento)
      return res
    } catch (error) {
      return error
    }
  }
  async buscarUmaformaDePagamento(id) {
    try {
      const res = await api.get(`/formaPagamentos/${id}`)
      return res
    } catch (error) {
      return error
    }
  }
}
