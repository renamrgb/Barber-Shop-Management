import api from "@/Services/API/api";
import ProcedimentoService from "./procedimentoService";

export default class ScheduleService {
  url = "/schedules";
  procedimentoService = new ProcedimentoService();
  async searchAvailable(date, professionalId) {
    try {
      const res = await api.get(
        `${this.url}/available?date=${date}&professionalId=${professionalId}`
      );
      return res.data;
    } catch (error) {
      return error.response.data;
    }
  }
  async save(item, dateStart, startTime) {
    try {
      let timeService = await this.calculateServiceTime(item.procedures);
      const TIME_END_START = this.setStartOrEndTime(
        dateStart,
        startTime,
        timeService
      );
      item.startDate = TIME_END_START.startTime;
      item.endDate = TIME_END_START.endTime;
      const res = await api.post(this.url, item);
      return res;
    } catch (error) {
      return error;
    }
  }
  setStartOrEndTime(dateStart, startTime, timeService) {
    // const [YEAR,MONTH, DAY] = dateStart.split("-");
    // Extrai as horas, minutos e segundos da string de tempo
    const [HOURS, MINUTES, SECONDS] = startTime.split(":");
    const DATE = new Date(dateStart);
    // Cria uma nova data com os valores da data original e os da string de tempo
    const NEW_DATE = new Date(
      DATE.getFullYear(),
      DATE.getMonth(),
      DATE.getDate(),
      HOURS,
      MINUTES,
      SECONDS
    );
    NEW_DATE.setDate(NEW_DATE.getDate() + 1);
    // Calcula o endTime somando a newDate com o tempo em minutos
    const END_TIME = new Date(NEW_DATE.getTime() + timeService * 60000);
    // Retorna a nova data
    return {
      startTime:
        NEW_DATE.toISOString().split("T")[0] +
        " " +
        NEW_DATE.toLocaleTimeString([], { hour12: false }),
      endTime:
        END_TIME.toISOString().split("T")[0] +
        " " +
        END_TIME.toLocaleTimeString([], { hour12: false }),
    };
  }
  async calculateServiceTime(listProcedures) {
    let timeMinutes = 0;
    let listTime = await this.getTimes(listProcedures);
    for (const item of listTime) {
      timeMinutes =
        parseInt(timeMinutes) + parseInt(this.converterParaMinutos(item));
    }
    return timeMinutes;
  }
  async getTimes(listProcedures) {
    let listTime = [];
    let p;
    for (const item of listProcedures) {
      p = await this.procedimentoService.buscarUm(item.id);
      listTime.push(p.duration);
    }
    return listTime;
  }
  converterParaMinutos(tempo) {
    var partes = tempo.split(":");
    var horasEmMinutos = parseInt(partes[0]) * 60;
    var minutos = parseInt(partes[1]);
    var segundosEmMinutos = parseInt(partes[2]) / 60;
    var tempoTotalEmMinutos = horasEmMinutos + minutos + segundosEmMinutos;
    return tempoTotalEmMinutos;
  }
  async consultScheduledTimes(idProfessional) {
    try {
      const RES = await api.get(`${this.url}?professional=${idProfessional}`);
      return RES.data;
    } catch (error) {
      return error;
    }
  }
  async getById(id) {
    try {
      const RES = await api.get(`${this.url}/${id}`);
      return RES.data;
    } catch (error) {
      return error;
    }
  }
  async delete(id) {
    try {
      const RES = await api.delete(`${this.url}/${id}`);
      return RES;
    } catch (error) {
      return error.response.data;
    }
  }
  async update(id, item) {
    try {
      item.endDate = item.endDate.replace("T", " ");
      item.startDate = item.startDate.replace("T", " ");
      const res = await api.put(`${this.url}/${id}`, item);
      return res;
    } catch (error) {      
      return error.response.data;
    }
  }
  async finishService(id, item, payment) {
    try {
      item.endDate = item.endDate.replace("T", " ");
      item.startDate = item.startDate.replace("T", " ");
      item.payment = payment;
      const res = await api.put(`${this.url}/${id}`, item);
      return res;
    } catch (error) {
      return error.response.data;
    }
  }
  async reverseService(id) {
    try {
      const res = await api.put(`${this.url}/reverseService/${id}`);
      return res;
    } catch (error) {
      return error.response.data;
    }
  }
}
