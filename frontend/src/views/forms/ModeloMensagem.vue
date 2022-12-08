<template>
  <CRow>
    <CCol :xs="12">
      <CCard class="mb-4">
        <CCardHeader>
          <strong>Modelo de Mensagem</strong>
        </CCardHeader>
        <CCardBody>
          <CForm>
            <div class="mb-3">
              <CFormLabel for="titulo">Descrição</CFormLabel>
              <CFormInput
                id="titulo"
                type="text"
                placeholder="Mensagem de confirmação..."
                v-model.lazy="titulo"
              />
            </div>
            <div class="mb-3">
              <CFormTextarea
                id="mensagem"
                label="Corpo de Mensagem"
                rows="5"
                v-model.lazy="mensagem"
              ></CFormTextarea>
            </div>
            <div class="mb-3">              
              <CFormSwitch
                id="formSwitchCheckDefault"
                label="Ativo"
                v-model="ativo"
              />
            </div>
            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
              <CButton color="primary" class="me-md-2" @click="salvar"
                >Confirmar</CButton
              >
              <router-link to="/forms/modeloMensagem"
                ><CButton color="danger">Cancelar</CButton></router-link
              >
            </div>
          </CForm>
        </CCardBody>
      </CCard>
    </CCol>
  </CRow>
  <toast ref="toast" />
</template>

<script>
import Service from '@/Services/modeloMensagemService.js'
import Toast from '@/components/Toast.vue'
export default {
  components: { Toast },
  name: 'Tipo de Despesa',
  data() {
    return {
      id: this.$route.params.id,
      titulo: '',
      mensagem: '',
      ativo: false,
      service: new Service(),
    }
  },
  methods: {
    async salvar() {
      let res = undefined
      let dados = {
        titulo: this.titulo,
        mensagem: this.mensagem,
        ativo: this.ativo,
      }
      if (this.id == undefined) {
        res = await this.service.cadastrar(dados)
      } else {
        res = await this.service.alterar(this.id, dados)
      }
      if (res.status == 201) {
        this.$refs.toast.createToast('Cadastrado com sucesso!')
        this.$router.push('/forms/modeloMensagem')
      } else if (res.status == 200) {
        this.$refs.toast.createToast('Alterado com sucesso!')
      } else {
        if (res.status == 500) {
          this.$refs.toast.createToast(res.error)
        } else {
          let vetErros = res.response.data.fieldErrors

          vetErros.forEach((element) => {
            this.$refs.toast.createToast(
              ` [${element.fieldName}] ${element.message} `,
            )
          })
        }
      }
    },
    async consultarUm() {
      if (this.id != undefined) {
        let item = await this.service.buscarUm(this.id)
        this.titulo = item.data.titulo
        this.mensagem = item.data.mensagem
        this.ativo = item.data.ativo
      }
    },
  },
  mounted() {
    if (this.id != undefined) {
      this.consultarUm()
    }
  },
}
</script>
