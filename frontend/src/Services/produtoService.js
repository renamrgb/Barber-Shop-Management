import api from "@/Services/API/api";

export default class ProdutoService {
  url = "/products";

  async updateOrInsert(id, data) {
    if (id == undefined) {
      return await this.cadastrar(data);
    } else {
      return await this.alterar(id, data);
    }
  }

  async consultarTodos() {
    try {
      const res = await api.get(this.url);
      return res.data;
    } catch (error) {
      return error;
    }
  }

  async getAllPaged(pageId, findAssets) {
    let filteAssets = `?page=${pageId}`;
    if (findAssets) filteAssets = `?isActive=true&page=${pageId}`;
    try {
      const res = await api.get(`${this.url}/productsPaged${filteAssets}`);
      return res.data;
    } catch (error) {
      return error;
    }
  }

  async cadastrar(item) {
    try {
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
  async getByTitle(tile) {
    try {
      const res = await api.get(`${this.url}?title=${tile}`);
      return res.data;
    } catch (error) {
      return error;
    }
  }
  async getByTitlePaged(title, pageId, findAssets) {
    let filteAssets = "";
    if (findAssets) filteAssets = `&isActive=true`;
    try {
      const res = await api.get(
        `${this.url}/productsPaged?title=${title}${filteAssets}&page=${pageId}`
      );
      return res.data;
    } catch (error) {
      return error;
    }
  }
}
