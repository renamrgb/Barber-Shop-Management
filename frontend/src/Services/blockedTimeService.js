import api from "@/Services/API/api";

export default class BlockedTimeService {
  url = "/blockedTimes";

  async getAll(pageId) {
    try {
      const res = await api.get(`${this.url}?page=${pageId}`);
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
  async search(pageId, description) {
    try {
      let filtro = `${this.url}?page=${pageId}`;
      if (description != "") filtro += `&description=${description}`;
      const res = await api.get(filtro);
      return res.data;
    } catch (error) {
      return error;
    }
  }
}
