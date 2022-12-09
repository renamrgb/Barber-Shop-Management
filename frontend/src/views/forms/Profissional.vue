<template>
  <CRow>
    <CCol :xs="12">
      <CCard class="mb-4">
        <CCardHeader>
          <strong>Profissional</strong>
        </CCardHeader>
        <CCardBody>
          <CForm>
            <div class="mb-3">
              <CFormLabel for="nome">* Nome</CFormLabel>
              <CFormInput id="nome" type="text" placeholder="Rafael..." v-model.lazy="nome" />
            </div>
            <div class="mb-3">
              <CFormLabel for="email">* E-mail</CFormLabel>
              <CFormInput id="email" type="email" placeholder="exemplo@gmail.com" v-model.lazy="email" />
            </div>
            <div class="row mb-3">
              <div class="col">
                <CFormLabel for="senha">* Senha</CFormLabel>
                <CFormInput id="senha" type="password" placeholder="*******" v-model.lazy="senha" />
              </div>
              <div class="col">
                <CFormLabel for="senha">* Confirmar senha</CFormLabel>
                <CFormInput id="confirmaSenha" type="password" placeholder="*******" v-model.lazy="confirmaSenha" />
              </div>
            </div>
            <CFormSwitch class="row mb-3" id="mostraSenha" label="Mostrar Senha" v-model="mostraSenha"
              @change="mostrarSenha()" />
            <div class="mb-3">
              <Multiselect v-model="value" placeholder="Procedimentos que o profissional realiza..." label="descricao"
                trackBy="descricao" :options="options" :searchable="true" mode="tags">
              </Multiselect>
            </div>
            <div class="row mb-3">
              <div class="col">
                <CFormLabel for="rg">RG</CFormLabel>
                <CFormInput id="rg" type="text" placeholder="11111111111" v-model="rg" v-mask="'##.###.###-##'" />
              </div>
              <div class="col">
                <CFormLabel for="senha">CPF</CFormLabel>
                <CFormInput id="cpf" type="text" placeholder="111.111.111-11" v-model.lazy="cpf"  />
              </div>
            </div>
            <div class="mb-3">
              <CFormLabel for="telefone">Telefone/Celular</CFormLabel>
              <CFormInput id="telefone" type="tel" placeholder="(18) 99999-9999" v-model.lazy="telefone" />
            </div>
            <div class="row g-4 mb-3">
              <div class="col-md-2">
                <CFormLabel for="cep">CEP</CFormLabel>
                <CFormInput id="cep" type="text" placeholder="99999-000" v-model.lazy="cep" maxlength="8" />
              </div>
              <div class="col">
                <CFormLabel for="descricao">Logradouro</CFormLabel>
                <CFormInput id="quantidade" type="logradouro" placeholder="Rua..." v-model.lazy="logradouro" />
              </div>
            </div>
            <div class="row g-4 mb-3">
              <div class="col">
                <CFormLabel for="cep">Bairro</CFormLabel>
                <CFormInput id="bairro" type="text" placeholder="Centro" v-model.lazy="bairro" />
              </div>
              <div class="col">
                <CFormLabel for="complemento">Complemento</CFormLabel>
                <CFormInput id="complemento" type="complemento" placeholder="" v-model.lazy="complemento" />
              </div>
              <div class="col">
                <CFormLabel for="cidade">Cidade</CFormLabel>
                <CFormInput id="cidade" type="cidade" placeholder="Tarabai" v-model.lazy="cidade" />
              </div>
              <div class="col-md-2">
                <CFormLabel for="uf">UF</CFormLabel>
                <CFormInput id="uf" type="uf" placeholder="SP" v-model.lazy="uf" maxlength="2" />
              </div>
            </div>
            <div class="mb-3">
              <br />
              <CFormSwitch id="formSwitchCheckDefault" label="Ativo" v-model="ativo" />
            </div>
            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
              <CButton color="primary" class="me-md-2" @click="salvar">Confirmar</CButton>
              <router-link to="/forms/profissional">
                <CButton color="danger">Cancelar</CButton>
              </router-link>
            </div>
          </CForm>
        </CCardBody>
      </CCard>
    </CCol>
  </CRow>
  <toast ref="toast" />
</template>

<script>
import Service from '@/Services/profissionalService.js'
import ProcedimentoService from '@/Services/procedimentoService.js'
import Toast from '@/components/Toast.vue'
import Multiselect from '@vueform/multiselect/src/Multiselect'

export default {
  components: { Toast, Multiselect },
  name: 'Profissional',
  data() {
    return {
      id: this.$route.params.id,
      nome: '',
      email: '',
      senha: '',
      confirmaSenha: '',
      mostraSenha: false,
      telefone: '',
      cpf: '',
      rg: '',
      cep: '',
      logradouro: '',
      bairro: '',
      complemento: '',
      cidade: '',
      uf: '',
      ativo: false,
      service: new Service(),
      procedimentoService: new ProcedimentoService(),
      value: [],
      options: [],
      procedimentos: [],
    }
  },
  methods: {
    async salvar() {
      let res = undefined

      this.value.forEach((element) => {
        this.procedimentos.push({ id: element })
      })

      if (this.compararSenhas()) {
        let dados = {
          usuario: {
            nome: this.nome,
            email: this.email,
            senha: this.senha,
            telefone: this.telefone,
            cpf: this.cpf,
            rg: this.rg,
            endereco: {
              cep: this.cep,
              logradouro: this.logradouro,
              bairro: this.bairro,
              complemento: this.complemento,
              cidade: this.cidade,
              uf: this.uf,
            },
            nivelAcesso: {
              authority: 'ROLE_ADMIN',
            },
            ativo: this.ativo,
          },
          procedimentos: this.procedimentos,
        }
        if (this.id == undefined) {
          res = await this.service.cadastrar(dados)
        } else {
          res = await this.service.alterar(this.id, dados)
        }
        console.log(res)
        if (res.status == 201) {
          this.$refs.toast.createToast('Cadastrado com sucesso!')
          this.$router.push('/forms/profissional')
        } else if (res.status == 200) {
          this.$refs.toast.createToast('Alterado com sucesso!')
        } else {
          let vetErros = res.response.data.fieldErrors
          console.log(vetErros);
          if (vetErros.isArray()) {
            vetErros.forEach((element) => {
              this.$refs.toast.createToast(
                ` [${element.fieldName}] ${element.message} `,
              )
            })
          } else this.$refs.toast.createToast(` [${vetErros.fieldName}] ${vetErros.message} `)
        }
      } else {
        this.$refs.toast.createToast('As senhas não são iguais.')
      }
    },
    async consultarUm() {
      if (this.id != undefined) {
        let item = await this.service.buscarUm(this.id)
        item = item.data;
        this.nome = item.usuario.nome
        this.email = item.usuario.email
        this.senha = item.usuario.senha
        this.confirmaSenha = item.usuario.senha
        this.telefone = item.usuario.telefone
        this.cpf = item.usuario.cpf
        this.rg = item.usuario.rg
        this.cep = item.usuario.endereco.cep
        this.logradouro = item.usuario.endereco.logradouro
        this.bairro = item.usuario.endereco.bairro
        this.complemento = item.usuario.endereco.complemento
        this.cidade = item.usuario.endereco.cidade
        this.uf = item.usuario.endereco.uf
        this.ativo = item.usuario.ativo
        this.carregaValue(item.procedimentos)

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
      return this.senha === this.confirmaSenha
    },
    async carregarSelectProcedimentos() {
      let res = await this.procedimentoService.consultarTodos()
      res.forEach((element) => {
        this.options.push({ value: element.id, descricao: element.descricao })
      })
    },
    carregaValue(itens) {
      itens.forEach((element) => {
        this.value.push(element.id);
      })
    },
  },
  mounted() {
    this.carregarSelectProcedimentos()
    if (this.id != undefined) {
      this.consultarUm()
    }
  },
}
</script>
<style src="@vueform/multiselect/themes/default.css">

</style>
