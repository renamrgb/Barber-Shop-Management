import api from '@/Services/API/api'

export default class AddressService {
  url = '/address'

  async getAddressByCep(cep) {
    try {
      cep = cep.replace('-', '')
      const res = await api.get(`${this.url}/${cep}`)
      return res.data
    } catch (error) {
      return error
    }
  }
}
