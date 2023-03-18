export default class DateNow {
  agora = new Date().toLocaleString("pt-BR", {
    timeZone: "America/Sao_Paulo",
  });

  dateNowISO() {    
    const [dia, mes, ano] = this.agora.split(/[\/\s,:]+/);       
    const dataFormatada = `${ano}-${mes.padStart(2, '0')}-${dia.padStart(2, '0')}`;
    console.log(dataFormatada);
    return dataFormatada;
  }
}
