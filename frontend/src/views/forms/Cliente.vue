<template>
  <CRow>
    <CCol :xs="12">
      <CCard class="mb-4">
        <CCardHeader>
          <strong>Clientes</strong>
        </CCardHeader>
        <CCardBody>
          <CForm>
            <div class="mb-3">
              <CFormLabel for="nome">* Nome</CFormLabel>
              <CFormInput
                name="nome"
                type="text"
                placeholder=""
                v-model="form.user.name"
              />
              <div
                v-if="v$.form.user.name.$errors.length > 0"
                class="invalid-input-form"
              >
                {{ v$.form.user.name.$errors[0].$message }}
              </div>
            </div>
            <div class="mb-3">
              <CFormLabel for="email">* E-mail</CFormLabel>
              <CFormInput
                name="email"
                type="email"
                placeholder="exemplo@gmail.com"
                v-model="form.user.email"
              />
              <div
                v-if="v$.form.user.email.$errors.length > 0"
                class="invalid-input-form"
              >
                {{ v$.form.user.email.$errors[0].$message }}
              </div>
            </div>
            <div class="row mb-3">
              <div v-if="btnChangePassword != true">
                <div class="col">
                  <CFormLabel for="senha">* Senha</CFormLabel>
                  <CFormInput
                    id="senha"
                    name="senha"
                    type="password"
                    placeholder="*******"
                    v-model="form.user.password"
                  />
                  <div
                    v-if="v$.form.user.password.$errors.length > 0"
                    class="invalid-input-form"
                  >
                    {{ v$.form.user.password.$errors[0].$message }}
                  </div>
                </div>
                <div class="col">
                  <CFormLabel for="confirmaSenha">* Confirmar senha</CFormLabel>
                  <CFormInput
                    id="confirmaSenha"
                    name="confirmaSenha"
                    type="password"
                    placeholder="*******"
                    v-model="form.user.confirmPassword"
                  />
                  <div
                    v-if="v$.form.user.confirmPassword.$errors.length > 0"
                    class="invalid-input-form"
                  >
                    {{ v$.form.user.confirmPassword.$errors[0].$message }}
                  </div>
                </div>
                <div class="invalid-input-form">
                  {{ messagePassword }}
                </div>
                <CFormSwitch
                  name="mostraSenha"
                  label="Mostrar Senha"
                  v-model="mostraSenha"
                  @change="mostrarSenha()"
                />
              </div>
              <div v-else>
                <button
                  type="button"
                  class="btn btn-light"
                  @click="
                    () => {
                      btnChangePassword = false
                    }
                  "
                >
                  Alterar senha
                </button>
              </div>
            </div>
            <div class="row mb-3">
              <div class="col">
                <CFormLabel for="rg">RG</CFormLabel>
                <CFormInput name="rg" type="text" v-model="form.user.rg" />
              </div>
              <div class="col">
                <CFormLabel for="rg">Tipo De Pessoa</CFormLabel>
                <select class="form-select" v-model="typePerson">
                  <option value="1" selected>Pessoa Física</option>
                  <option value="2">Pessoa Jurícida</option>
                </select>
              </div>
              <div class="col">
                <CFormLabel for="cpf">CPF / CNPJ</CFormLabel>
                <input
                  type="text"
                  class="form-control"
                  v-mask="['###.###.###-##', '##.###.###/####-##']"
                  v-model="form.user.cpf"
                />
                <div
                  v-if="messageTypePersonValid != undefined"
                  class="invalid-input-form"
                >
                  {{ messageTypePersonValid.message }}
                </div>
              </div>
            </div>
            <div class="mb-3">
              <CFormLabel for="telefone">* Telefone / Celular</CFormLabel>
              <input
                name="telefone"
                type="tel"
                class="form-control"
                v-mask="['(##) #####-####']"
                v-model="form.user.phoneNumber"
              />
              <div
                v-if="v$.form.user.phoneNumber.$errors.length > 0"
                class="invalid-input-form"
              >
                {{ v$.form.user.phoneNumber.$errors[0].$message }}
              </div>
            </div>
            <div id="getAddres">
              <div class="row">
                <CFormLabel for="cep">CEP</CFormLabel>
              </div>
              <div class="row g-4 mb-3">
                <div class="col-md-2">
                  <input
                    name="cep"
                    type="text"
                    class="form-control"
                    v-mask="'#####-###'"
                    v-model="form.user.address.zipCode"
                  />
                </div>
                <div class="col">
                  <button
                    type="button"
                    class="btn btn-primary"
                    @click="getAddressByCep"
                  >
                    Consultar CEP
                  </button>
                </div>
              </div>
            </div>
            <div id="addres" v-if="this.consultedZipCode == true">
              <div class="row g-4 mb-3">
                <div class="col">
                  <CFormLabel for="descricao">Logradouro</CFormLabel>
                  <CFormInput
                    name="quantidade"
                    type="logradouro"
                    placeholder="Rua..."
                    v-model="form.user.address.publicPlace"
                  />
                </div>
              </div>
              <div class="row g-4 mb-3">
                <div class="col">
                  <CFormLabel for="cep">Bairro</CFormLabel>
                  <CFormInput
                    name="bairro"
                    type="text"
                    placeholder="Centro"
                    v-model="form.user.address.neighborhood"
                  />
                </div>
                <div class="col">
                  <CFormLabel for="complemento">Complemento</CFormLabel>
                  <CFormInput
                    name="complemento"
                    type="complemento"
                    placeholder=""
                    v-model="form.user.address.complement"
                  />
                </div>
                <div class="col">
                  <CFormLabel for="cidade">Cidade</CFormLabel>
                  <CFormInput
                    name="cidade"
                    v-model="form.user.address.city"
                    disabled
                  />
                </div>
                <div class="col-md-2">
                  <CFormLabel for="uf">UF</CFormLabel>
                  <input
                    name="uf"
                    class="form-control"
                    v-model="form.user.address.state"
                    max="2"
                    disabled
                  />
                </div>
              </div>
            </div>
            <div class="mb-3">
              <br />
              <CFormSwitch
                name="formSwitchCheckDefault"
                label="Ativo"
                v-model="form.user.isActive"
              />
            </div>
            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
              <CButton color="primary" class="me-md-2" @click="submitForm"
                >Confirmar</CButton
              >
              <a href="/#/forms/cliente" class="btn btn-danger">Cancelar</a>
            </div>
          </CForm>
        </CCardBody>
      </CCard>
    </CCol>
  </CRow>
  <toast ref="toast" />
</template>

<script>
import { useVuelidate } from '@vuelidate/core'
import ValidationsMessage from '@/util/ValidationsMessage.js'
import Service from '@/Services/clienteService.js'
import AddressService from '@/Services/addressService.js'
import ValidationTypePerson from '@/util/validationTypePerson.js'
import Toast from '@/components/Toast.vue'
export default {
  components: { Toast },
  name: 'Cliente',
  data() {
    return {
      v$: useVuelidate(),
      validationsMessage: new ValidationsMessage(),
      id: this.$route.params.id,
      service: new Service(),
      addressService: new AddressService(),
      validationTypePerson: new ValidationTypePerson(),
      mostraSenha: false,
      messagePassword: '',
      btnChangePassword: false,
      consultedZipCode: false,
      typePerson: 1,
      messageTypePersonValid: undefined,
      form: {
        user: {
          name: '',
          email: '',
          password: '',
          confirmPassword: '',
          phoneNumber: '',
          cpf: '',
          rg: '',
          address: {
            zipCode: '',
            publicPlace: '',
            neighborhood: '',
            complement: '',
            city: '',
            state: '',
          },
          nivelAcesso: {
            authority: 'ROLE_USER',
          },
          isActive: false,
        },
      },
    }
  },
  validations() {
    return {
      form: {
        user: {
          name: {
            required: this.validationsMessage.requiredMessage,
            maxLength: this.validationsMessage.maxLengthMenssage(100),
          },
          email: {
            required: this.validationsMessage.requiredMessage,
            maxLength: this.validationsMessage.maxLengthMenssage(100),
            email: this.validationsMessage.emailMessage,
          },
          password: {
            required: this.validationsMessage.requiredMessage,
            maxLength: this.validationsMessage.maxLengthMenssage(10),
          },
          confirmPassword: {
            required: this.validationsMessage.requiredMessage,
            maxLength: this.validationsMessage.maxLengthMenssage(10),
          },
          phoneNumber: {
            required: this.validationsMessage.requiredMessage,
            maxLength: this.validationsMessage.maxLengthMenssage(15),
          },
        },
      },
    }
  },
  methods: {
    submitForm() {
      if (this.form.user.password == undefined) {
        this.form.user.password = '*******'
        this.form.user.confirmPassword = '*******'
      }
      this.v$.$validate()
      this.messageTypePersonValid = this.validationTypePerson();
      if (!this.v$.$error && !this.btnChangePassword && this.compararSenhas() && this.messageTypePersonValid == undefined) {
        this.salvar()
      } else if (!this.v$.$error && this.btnChangePassword) {
        this.salvar()
      }
    },
    async salvar() {
      let res = undefined
      let dados = this.form
      dados.user.address.zipCode = dados.user.address.zipCode.replace(
        /[^\w\s]/gi,
        '',
      )
      dados.user.phoneNumber = dados.user.phoneNumber.replace(/[^\w\s]/gi, '')
      dados.user.phoneNumber = dados.user.phoneNumber.replace(' ', '')
      dados.user.cpf = dados.user.cpf.replace(/[^\w\s]/gi, '')

      if (this.id == undefined) {
        res = await this.service.cadastrar(dados)
      } else {
        this.form.password = ''
        res = await this.service.alterar(this.id, dados)
      }
      if (res.status == 201) {
        this.$refs.toast.createToast('Cadastrado com sucesso!')
        this.$router.push('/forms/cliente')
      } else if (res.status == 200) {
        this.$refs.toast.createToast('Alterado com sucesso!')
      } else {
        let vetErros = res.response.data.fieldErrors
        vetErros.forEach((element) => {
          this.$refs.toast.createToast(
            ` [${element.fieldName}] ${element.message} `,
          )
        })
      }
    },
    async consultarUm() {
      if (this.id != undefined) {
        this.form.user = await this.service.buscarUm(this.id)
      }
    },
    mostrarSenha() {
      if (this.mostraSenha == false) {
        document.getElementById('senha').type = 'text'
        document.getElementById('confirmaSenha').type = 'text'
      } else {
        document.getElementById('senha').type = 'password'
        document.getElementById('confirmaSenha').type = 'password'
      }
    },
    compararSenhas() {
      if (this.form.user.password == this.form.user.confirmPassword) {
        return true
      } else {
        this.messagePassword = 'As senhas não são iguais.'
        return false
      }
    },
    async getAddressByCep() {
      this.form.user.address = await this.addressService.getAddressByCep(
        this.form.user.address.zipCode,
      )
      if (this.form.user.address.zipCode != undefined) {
        this.consultedZipCode = true
      }
    },
    validationTypePerson() {
      this.validationTypePerson.validation(this.typePerson, this.form.user.cpf)
    },
  },
  mounted() {
    if (this.id != undefined) {
      this.consultarUm()
      this.btnChangePassword = true
    }
  },
}
</script>
