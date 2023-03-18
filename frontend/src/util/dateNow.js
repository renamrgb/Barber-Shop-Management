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
}
