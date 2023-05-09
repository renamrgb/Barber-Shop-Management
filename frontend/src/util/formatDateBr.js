export default class FormatDateBr {
  toDateBr(dataISO) {
    const data = new Date(dataISO);
    let dia = data.getUTCDate().toString().padStart(2, "0"); // extrai o dia em UTC e adiciona um zero à esquerda, se necessário
    let mes = (data.getUTCMonth() + 1).toString().padStart(2, "0"); // extrai o mês em UTC (começando de 0) e adiciona um zero à esquerda, se necessário
    let ano = data.getUTCFullYear();
    let dataBR = `${dia}/${mes}/${ano}`;
    return dataBR;
  }
  datetimeToStringBr(datetime) {
    const date = new Date(datetime);
    const day = String(date.getDate()).padStart(2, "0");
    const month = String(date.getMonth() + 1).padStart(2, "0");
    const year = String(date.getFullYear());
    const hours = String(date.getHours()).padStart(2, "0");
    const minutes = String(date.getMinutes()).padStart(2, "0");
    const seconds = String(date.getSeconds()).padStart(2, "0");    
    return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`
  }
}
