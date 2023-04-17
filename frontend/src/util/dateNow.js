export default class DateNow {
  agora = new Date().toLocaleString("pt-BR", {
    timeZone: "America/Sao_Paulo",
  });

  dateNowISO() {
    const [dia, mes, ano] = this.agora.split(/[\/\s,:]+/);
    const dataFormatada = `${ano}-${mes.padStart(2, "0")}-${dia.padStart(
      2,
      "0"
    )}`;
    return dataFormatada;
  }
  date(date, qtde, sinal) {
    if (sinal == "+") {
      date.setDate(date.getDate() + qtde);
    } else {
      date.setDate(date.getDate() - qtde);
    }
    return this.formatDateISO(date.toLocaleString());
  }
  formatDateISO(date) {
    const [dia, mes, ano] = date.split(/[\/\s,:]+/);
    const dataFormatada = `${ano}-${mes.padStart(2, "0")}-${dia.padStart(
      2,
      "0"
    )}`;
    return dataFormatada;
  }
  compareDates(dataStr) {
    // Converter a string de data em um objeto Date
    const DATE = new Date(dataStr);
    // Converter a data atual em um objeto Date
    const DATE_NOW = new Date(this.formatDateISO(this.agora));
    // Calcular a diferença em milissegundos
    const DIFFERENCE_MS = DATE_NOW.getTime() - DATE.getTime();
    // Converter a diferença em dias
    const ONE_DAY_MS = 1000 * 60 * 60 * 24;
    const DIFFERENCE_IN_DAYS = Math.floor(DIFFERENCE_MS / ONE_DAY_MS);
    // Retornar a diferença em dias
    return DIFFERENCE_IN_DAYS;
  }
}
