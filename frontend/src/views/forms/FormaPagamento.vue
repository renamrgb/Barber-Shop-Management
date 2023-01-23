<template>
  <CRow>
    <CCol :xs="12">
      <CCard class="mb-4">
        <CCardHeader>
          <strong>Formas de Pagamento</strong>
        </CCardHeader>
        <CCardBody>
          <CForm
            class="row g-3 needs-validation"
            novalidate
            :validated="validatedCustom"            
          >
            <div :md="4">
              <CFormLabel for="descricao">* Descrição</CFormLabel>
              <CFormInput
                id="descricao"
                type="text"
                placeholder="Cartão de crédito"
                v-model.lazy="descricao"
                required
              />
            </div>
            <div class="mb-3">
              <CFormSwitch
                id="formSwitchCheckDefault"
                label="Ativo"
                v-model="ativo"
              />
            </div>
            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
              <CButton
                color="primary"
                class="me-md-2"
                @click="saveFormaPagamento"
                >Confirmar</CButton
              >
              <a class="btn btn-danger" href="/#/forms/forma-pagamento"
                >Cancelar</a
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
import FormaPagamentoService from '@/Services/formaPagamentoService'
import Toast from '@/components/Toast.vue'
export default {
  components: { Toast },
  name: 'FormaPagamento',
  data() {
    return {
      id: this.$route.params.id,
      descricao: '',
      ativo: false,
      fp: new FormaPagamentoService(),
      fps: '',
      validatedCustom: null,
    }
  },
  methods: {    
    async saveFormaPagamento(event) {
      const form = event.currentTarget
      let res = undefined
      let dados = { description: this.descricao, isActive: this.ativo }
      if (form.checkValidity() === false) {
        event.preventDefault()
        event.stopPropagation()
      }
      this.validatedCustom = true

      if (this.id == undefined) {
        res = await this.fp.cadastrar(dados)
      } else {
        res = await this.fp.alterar(this.id, dados)
      }
      if (res.status == 201) {
        this.$refs.toast.createToast('Cadastrado com sucesso!')
        this.$router.push({ path: `/forms/forma-pagamento` })
      } else if (res.status == 200) {
        this.$refs.toast.createToast('Alterado com sucesso!')
      } else {
        let vetErros = res.response.data.fieldErrors

        vetErros.forEach((element) => {
          this.$refs.toast.createToast(`${element.message}`)
        })
      }
    },
    async buscarFormaPagamento() {
      if (this.id != undefined) {
        let formaPagamento = await this.fp.buscarUmaformaDePagamento(this.id)
        this.descricao = formaPagamento.data.description
        this.ativo = formaPagamento.data.isActive
      }
    },
  },
  mounted() {
    console.log(this.id)
    if (this.id != undefined) {
      this.buscarFormaPagamento()
    }
  },
}
</script>
