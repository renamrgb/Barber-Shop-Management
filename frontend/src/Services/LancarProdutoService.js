import api from "@/Services/API/api";

export default class LancarProdutoService {
  url = "/stockEntry";

  async save(item) {
    try {
      const res = await api.post(`${this.url}`, item);
      return res;
    } catch (error) {
      return error;
    }
  }
}
