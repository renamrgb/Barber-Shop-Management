export default class FormatDateBr {
  toDateBr(dataISO) {
    // const dataISO = "2023-03-06T12:34:56.789Z";
    const data = new Date(dataISO);
    let dia = data.getUTCDate().toString().padStart(2, "0"); // extrai o dia em UTC e adiciona um zero à esquerda, se necessário
    //dia = parseInt(dia) + 1;
    let mes = (data.getUTCMonth() + 1).toString().padStart(2, "0"); // extrai o mês em UTC (começando de 0) e adiciona um zero à esquerda, se necessário
    let ano = data.getUTCFullYear();
    let dataBR = `${dia}/${mes}/${ano}`;
    return dataBR; // "06/03/2023"
  }
}
