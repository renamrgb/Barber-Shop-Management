import { required, helpers, minValue, maxLength } from '@vuelidate/validators'

export default class ValidationsMessage {  

  constructor() {
    this.requiredMessage = helpers.withMessage(
      'Este campo não pode estar vazio',
      required,
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
