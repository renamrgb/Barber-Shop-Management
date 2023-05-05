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
  async find(title, filter, pageId) {
    try {      
      let filtro = `${this.url}?dtStart=${filter.dtStart}&dtEnd=${filter.dtEnd}&page=${pageId}`;            
      if (title != "") filtro = filtro + `&title=${title}`;     
      const res = await api.get(filtro);
      return res.data;
    } catch (error) {
      return error;
    }    
  }  
  async delete(id){
    try {             
      const res = await api.delete(`${this.url}/${id}`);
      return res;
    } catch (error) {
      return error.response.data;
    }    
  }
}
