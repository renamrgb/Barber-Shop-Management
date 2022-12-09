import api from '@/Services/API/api'
var qs = require('qs')
var axios = require('axios')

export default class UsuarioService {
  async login(usuario, senha) {
    try {
      var data = qs.stringify({
        username: usuario,
        password: senha,
        grant_type: 'password',
      })      
      let res = await api.post('/oauth/token', data, {
        headers: {
          Authorization: 'Basic YnNtOmJzbTEyMw==',
          'Content-Type': 'application/x-www-form-urlencoded',
        },
      });
      return res
    } catch (error) {
      return error
    }
  }
}
