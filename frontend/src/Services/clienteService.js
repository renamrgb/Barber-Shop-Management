import api from "@/Services/API/api";

export default class ClienteService {
  url = "/customers";

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
      const res = await api.post(this.url, this.replaceItem(item));
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
    console.log(`${JSON.stringify(item)}`);
    try {
      const res = await api.put(`${this.url}/${id}`, this.replaceItem(item));
      return res;
    } catch (error) {
      console.log(error);
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
  async getAllPaged(pageId) {
    try {
      const res = await api.get(`${this.url}/paged?page=${pageId}`);
      return res.data;
    } catch (error) {
      return error;
    }
  }
  async getByNamePaged(name, pageId) {
    try {
      const res = await api.get(
        `${this.url}/paged?name=${name}&page=${pageId}`
      );
      return res.data;
    } catch (error) {
      return error;
    }
  }
  replaceItem(item) {
    item.user.phoneNumber = item.user.phoneNumber.replace(/[^\d]+/g, "");
    item.user.document = item.user.document.replace(/[^\d]+/g, "");
    item.user.rg = item.user.rg.replace(/[^\d]+/g, "");
    if (item.user.address.zipCode != undefined)
      item.user.address.zipCode = item.user.address.zipCode.replace(
        /[^\d]+/g,
        ""
      );
    return item;
  }
}
