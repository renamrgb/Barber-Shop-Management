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
      return error;
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
      console.log(JSON.stringify(item));
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
}
