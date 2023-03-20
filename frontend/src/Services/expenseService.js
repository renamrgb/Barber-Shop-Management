import api from "@/Services/API/api";

export default class ExpenseService {
  url = "/expenses";

  replaceItem(item) {
    if (typeof item.total != "string") item.total = item.total.toString();

    item.total = item.total.replace(",", ".");
    item.total = parseFloat(item.total);
    return item;
  }
  async saveOrUpdate(id, installments, item) {
    if (installments != undefined && id == undefined) {
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
  async getAllPaged(pageId, filter) {
    try {
      const res = await api.get(`${this.url}/paged?dtStart=${filter.dtStart}&dtEnd=${filter.dtEnd}&bringPaid=${filter.bringPaid}&page=${pageId}`);
      return res.data;
    } catch (error) {
      return error;
    }
  }
  async getByDescriptionPaged(description, pageId, filter) {
    try {
      const res = await api.get(
        `${this.url}/paged?dtStart=${filter.dtStart}&dtEnd=${filter.dtEnd}&description=${description}&bringPaid=${filter.bringPaid}&page=${pageId}`
      );
      return res.data;
    } catch (error) {
      return error;
    }
  }
  async payOffExpense(expenseId, installmentId, obj) {    
    try {
      obj.amountPaid = obj.amountPaid.replace(",", ".");
      const res = await api.post(
        `${this.url}/payOffExpense/${expenseId}?installmentId=${installmentId}`,
        obj
      );
      return res;
    } catch (error) {
      return error;
    }
  }
  async reverse(expenseId, installmentId) {
    try {
      const res = await api.post(
        `${this.url}/reverse?expenseId=${expenseId}&installmentId=${installmentId}`
      );
      return res;
    } catch (error) {
      return error;
    }
  }
}
