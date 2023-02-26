export default class ValidationTypePerson {
  /* 1 ==> Pessoa Física
  2 ==> Pessoa Jurícida */

  validation(typePerson, document) {
    if (typePerson == "LEGAL_PERSON" && !this.validarCNPJ(document)) {
      return {
        status: false,
        message: "CPNJ é invalido, por favor digite novamente.",
      };
    } else if (typePerson == "PHYSICAL_PERSON" && !this.validarCPF(document)) {
      return {
        status: false,
        message: "CPF é invalido, por favor digite novamente.",
      };
    }
  }

  validarCPF(cpf) {
    cpf = cpf.replace(/[^\d]+/g, ""); // Remove caracteres não numéricos

    // Verifica se o CPF tem 11 dígitos e não é uma sequência repetida
    if (cpf.length != 11 || /^(\d)\1{10}$/.test(cpf)) {
      return false;
    }

    // Validação dos dígitos do CPF
    var digito1 = 0;
    var digito2 = 0;

    for (var i = 0; i < 9; i++) {
      digito1 += parseInt(cpf.charAt(i)) * (10 - i);
      digito2 += parseInt(cpf.charAt(i)) * (11 - i);
    }

    digito1 = digito1 % 11 < 2 ? 0 : 11 - (digito1 % 11);
    digito2 =
      (digito2 + digito1 * 2) % 11 < 2
        ? 0
        : 11 - ((digito2 + digito1 * 2) % 11);

    // Verifica se os dígitos calculados são iguais aos dígitos informados
    if (
      digito1 != parseInt(cpf.charAt(9)) ||
      digito2 != parseInt(cpf.charAt(10))
    ) {
      return false;
    }

    return true; // CPF válido
  }

  validarCNPJ(cnpj) {
    cnpj = cnpj.replace(/[^\d]+/g, ""); // Remove caracteres não numéricos

    // Verifica se o CNPJ tem 14 dígitos
    if (cnpj.length != 14) {
      return false;
    }

    // Verifica se todos os dígitos são iguais
    if (/^(\d)\1{13}$/.test(cnpj)) {
      return false;
    }

    // Validação dos dígitos do CNPJ
    var tamanho = cnpj.length - 2;
    var numeros = cnpj.substring(0, tamanho);
    var digitos = cnpj.substring(tamanho);
    var soma = 0;
    var pos = tamanho - 7;

    for (var i = tamanho; i >= 1; i--) {
      soma += numeros.charAt(tamanho - i) * pos--;
      if (pos < 2) {
        pos = 9;
      }
    }

    var resultado = soma % 11 < 2 ? 0 : 11 - (soma % 11);

    if (resultado != digitos.charAt(0)) {
      return false;
    }

    tamanho = tamanho + 1;
    numeros = cnpj.substring(0, tamanho);
    soma = 0;
    pos = tamanho - 7;

    for (var i = tamanho; i >= 1; i--) {
      soma += numeros.charAt(tamanho - i) * pos--;
      if (pos < 2) {
        pos = 9;
      }
    }

    resultado = soma % 11 < 2 ? 0 : 11 - (soma % 11);

    if (resultado != digitos.charAt(1)) {
      return false;
    }

    return true; // CNPJ válido
  }
}
