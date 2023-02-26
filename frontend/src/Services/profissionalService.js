import api from "@/Services/API/api";

export default class ProfissionalService {
  url = "/professionals";
  async consultarTodos() {
    try {
      const res = await api.get(this.url);
      return res.data;
    } catch (error) {
      return error;
    }
  }
  async cadastrar(item) {
    try {
      item.user.address.zipCode = item.user.address.zipCode.replace(
        /[^\d]+/g,
        ""
      );
      item.user.phoneNumber = item.user.phoneNumber.replace(/[^\d]+/g, "");
      item.user.phoneNumber = item.user.phoneNumber.replace(" ", "");
      item.user.cpf = item.user.cpf.replace(/[^\d]+/g, "");
      const res = await api.post(this.url, item);
      return res;
    } catch (error) {
      return error;
    }
  }
  async excluir(id) {
    try {
      const res = await api.delete(`${this.url}/${id}`);
      return res;
    } catch (error) {
      return error;
    }
  }
  async alterar(id, item) {
    try {
      const res = await api.put(`${this.url}/${id}`, item);
      return res;
    } catch (error) {
      return error;
    }
  }
  async buscarUm(id) {
    try {
      const res = await api.get(`${this.url}/${id}`);
      return res.data;
    } catch (error) {
      return error;
    }
  }
  async findByName(name) {
    try {
      const res = await api.get(`${this.url}?name=${name}`);
      return res.data;
    } catch (error) {
      return error;
    }
  }
}
