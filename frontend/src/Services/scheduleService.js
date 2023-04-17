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
      console.log(this.setStartOrEndTime(dateStart, startTime, timeService));
      const res = await api.post(this.url, this.replaceItem(item));
      return res;
    } catch (error) {
      return error;
    }
  }
  setStartOrEndTime(dateStart, startTime, timeService) {
    let lStartTime = startTime.split(":");
    console.log(`${dateStart} ${startTime} ${timeService}`);
    console.log(lStartTime[0]);
    dateStart.setHours(parseInt(lStartTime[0]));    
    dateStart.getMinutes(parseInt(lStartTime[1]));
    dateStart.getSeconds(parseInt(lStartTime[2]));
    console.log(dateStart.toString()); 
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
