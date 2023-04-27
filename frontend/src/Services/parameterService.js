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
  async save(data) {
    try {
      const res = await api.put(`${this.url}/1`, data);
      return res.data;
    } catch (error) {
      return error;
    }
  }
  async consultWorkSchedules() {
    try {
      const RES = await api.get(`${this.url}/1`);
      return RES.data.parameterValues;
    } catch (error) {
      return error;
    }
  }
  async getMaxPointsCard() {
    try {
      const RES = await api.get(`${this.url}/1`);
      const PARAMETER_VALUES = RES.data.parameterValues;
      for (let e of PARAMETER_VALUES) {
        if (e.parameter_key == "QTY_LOYALYTY_CARD")
          return parseInt(e.parameter_value);
      }
    } catch (error) {
      return error;
    }
  }
  async getParamerterKey(key) {
    try {
      const RES = await api.get(`${this.url}/findByKey?pameterKey=${key}`);
      return RES.data;
    } catch (error) {
      return error;
    }
  }
}
