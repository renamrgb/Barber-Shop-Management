import api from "@/Services/API/api";

export default class LancarProdutoService {
  url = "/stockEntry";

  async getAllPaged(pageId, filter) {
    try {
      const res = await api.get(
        `${this.url}/paged?dtStart=${filter.dtStart}&dtEnd=${filter.dtEnd}&page=${pageId}`
      );
      return res.data;
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
  async getByDescriptionPaged(supplier, pageId, filter) {
    try {
      const res = await api.get(
        `${this.url}/paged?dtStart=${filter.dtStart}&dtEnd=${filter.dtEnd}&supplier=${supplier}&page=${pageId}`
      );
      return res.data;
    } catch (error) {
      return error;
    }
  }
  async save(item) {
    try {
      const res = await api.post(`${this.url}`, item);
      return res;
    } catch (error) {
      return error;
    }
  }
  async reverse(id) {
    try {
      const res = await api.post(`${this.url}/reverse/${id}`);
      return res.data;
    } catch (error) {
      return error;
    }
  }
}
