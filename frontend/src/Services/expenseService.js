import api from "@/Services/API/api";

export default class ExpenseService {
  url = "/expenses";

  replaceItem(item) {
    item.total = item.total.replace(",", ".");
    return item;
  }

  async saveOrUpdate(id, installments, item) {
    if (installments != undefined) {
      installments.forEach((element) => {
        item.installments.push({
          installmentValue: element.installmentValue,
          dueDate: element.dueDate,
        });
      });
    }
    if (id == undefined) {
      return await this.cadastrar(this.replaceItem(item));
    } else return await this.alterar(this.replaceItem(item), id);
  }

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
      if (!item.expenseType.generateInstallments) {
        item.installments.push({
          installmentValue: item.total,
          dueDate: item.dueDate,
        });
      }
      const res = await api.post(this.url, item);
      return res;
    } catch (error) {
      return error;
    }
  }
  async alterar(item, id) {
    try {
      const res = await api.put(`${this.url}/${id}`, item);
      return res;
    } catch (error) {
      return error;
    }
  }
  async generateInstallments(item) {
    try {
      const res = await api.post(`${this.url}/generateInstallments`, item);
      return res.data;
    } catch (error) {
      return error;
    }
  }
  async getOneExpense(id) {
    try {
      const res = await api.get(`${this.url}/${id}`);
      return res.data;
    } catch (error) {
      return error;
    }
  }
  async delete(id) {
    try {
      const res = await api.delete(`${this.url}/${id}`);
      return res.data;
    } catch (error) {
      return error.response.data;
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
  async getByDescriptionPaged(description, pageId) {    
    try {
      const res = await api.get(
        `${this.url}/paged?description=${description}&page=${pageId}`
      );
      return res.data;
    } catch (error) {
      return error;
    }
  }
}
