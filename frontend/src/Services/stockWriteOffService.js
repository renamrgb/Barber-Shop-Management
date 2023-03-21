import api from "@/Services/API/api";

export default class StockWriteOffService {
  url = "/stockWriteOff";

  async save(item) {
    try {
      const res = await api.post(`${this.url}`, item);
      return res;
    } catch (error) {
      return error;
    }
  }
}
