import api from "@/Services/API/api";

export default class FormaPagamentoService {
  async consultarFormasPagamento() {
    api.get('/formaPagamentos')
      .then((dados) => {
        console.log(dados)
      })
      .catch((error) => {
        console.log(error)
      })
  }
  async saveFormasPagamento(formaPagamento) {    
    api.post('/formaPagamentos', formaPagamento)
      .then((dados) => {
        console.log(dados)
      })
      .catch((error) => {
        console.log(error)
      })
  }
}
