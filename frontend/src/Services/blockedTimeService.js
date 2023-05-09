import api from "@/Services/API/api";
import FormatDateBr from "@/util/formatDateBr";
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
    item = this.replaceItems(item);
    try {
      const res = await api.post(`${this.url}`, item);
      return res;
    } catch (error) {      
      return error.response.data;
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
  async getById(id) {
    try {
      const res = await api.get(`${this.url}/${id}`);
      return res.data;
    } catch (error) {
      return error;
    }
  }
  async alterar(id, item) {
    try {
      const res = await api.put(`${this.url}/${id}`, this.replaceItems(item));
      return res;
    } catch (error) {
      console.log(error);
      return error.response.data;
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
  replaceItems(item) {
    let f = new FormatDateBr();
    item.startDate = f.datetimeToStringBr(item.startDate);
    item.endDate = f.datetimeToStringBr(item.endDate);
    // item.startDate = item.startDate.replace("T", " ");
    // item.endDate = item.endDate.replace("T", " ");
    // item.startDate = item.startDate.replace("Z", " ");
    // item.endDate = item.endDate.replace("Z", " ");
    return item;
  }
}
