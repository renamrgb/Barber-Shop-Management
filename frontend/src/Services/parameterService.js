import api from "@/Services/API/api";

export default class ParameterService {
  url = "/parameters";

  async getParamerter() {
    try {
      const res = await api.get(`${this.url}/1`);
      return res.data;
    } catch (error) {
      return error;
    }
  }
  async save(data){
    try {
      const res = await api.put(`${this.url}/1`, data);
      return res.data;
    } catch (error) {
      return error;
    }
  }
}
