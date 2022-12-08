import api from '@/Services/API/api'

export default class ModeloMensagemService {
  async consultarTodos() {
    try {
      const res = await api.get('/modeloMensagens')
      return res.data
    } catch (error) {
      return error
    }
  }
  async cadastrar(item) {
    try {
      const res = await api.post('/modeloMensagens', item)      
      return res
    } catch (error) {
      return error
    }
  }
  async excluir(id) {
    try {
      const res = await api.delete(`/modeloMensagens/${id}`)
      return res
    } catch (error) {
      return error
    }
  }
  async alterar(id, item) {
    try {
      const res = await api.put(`/modeloMensagens/${id}`, item)
      return res
    } catch (error) {
      return error
    }
  }
  async buscarUm(id) {
    try {
      const res = await api.get(`/modeloMensagens/${id}`)
      return res
    } catch (error) {
      return error
    }
  }
}
