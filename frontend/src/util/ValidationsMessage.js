import {
  required,
  helpers,
  minValue,
  maxLength,
  decimal,
  email,
  numeric,
} from '@vuelidate/validators'

export default class ValidationsMessage {
  constructor() {
    this.requiredMessage = helpers.withMessage(
      'Este campo não pode estar vazio',
      required,
    )
    this.decimalMessage = helpers.withMessage(
      'Coloque ponto(.) ao invés da vírgula(,)',
      decimal,
    )
    this.emailMessage = helpers.withMessage(
      'O valor não é um endereço de e-mail válido',
      email,
    )
    this.phoneNumberMessage = helpers.withMessage(
      'O valor deve ser numérico',
      numeric,
    )
  }
  minMenssage(min) {
    return helpers.withMessage(
      `O valor mínimo permitido é ${min}`,
      minValue(min),
    )
  }
  maxLengthMenssage(max) {
    return helpers.withMessage(
      `O comprimento máximo permitido é ${max}`,
      maxLength(max),
    )
  }
}
