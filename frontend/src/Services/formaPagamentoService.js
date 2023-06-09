import api from '@/Services/API/api'

export default class FormaPagamentoService {
  url = '/paymentMethod'

  async consultarFormasPagamento() {
    try {
      const res = await api.get(this.url)
      return res.data
    } catch (error) {
      return error
    }
  }
  async getAllPaged(pageId) {
    try {
      const res = await api.get(`${this.url}/paged?page=${pageId}`)
      return res.data
    } catch (error) {
      return error
    }
  }
  async cadastrar(formaPagamento) {
    try {
      const res = await api.post(this.url, formaPagamento)
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
  async alterar(id, formaPagamento) {
    try {
      const res = await api.put(`${this.url}/${id}`, formaPagamento)
      return res
    } catch (error) {
      return error
    }
  }
  async buscarUmaformaDePagamento(id) {
    try {
      const res = await api.get(`${this.url}/${id}`)
      return res
    } catch (error) {
      return error
    }
  }
  async getByDescription(description) {
    try {
      const data = await api.get(
        `${this.url}?description=${description}`,
      )      
      return data.data;
    } catch (error) {
      return error
    }
  }
  async getByDescriptionPaged(description, pageId) {
    try {
      const data = await api.get(
        `${this.url}/paged?description=${description}&page=${pageId}`,
      )      
      return data.data;
    } catch (error) {
      return error
    }
  }
}
