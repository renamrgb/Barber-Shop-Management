<template>
  <CRow>
    <CCol :xs="12">
      <CCard class="mb-4">
        <CCardHeader>
          <strong>Forma de Pagamento</strong>          
        </CCardHeader>
        <CCardBody>
          <CForm
            class="row g-3 needs-validation"
            novalidate
            :validated="validatedCustom01"
            @submit="validationRequired"
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
                type="submit"
                >Confirmar</CButton
              >
              <router-link to="/forms/forma-pagamento"
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
      visibleLiveDemo: false,
      validatedCustom01: null,
    }
  },
  methods: {
    validationRequired(event) {
      const form = event.currentTarget
      if (form.checkValidity() === false) {
        event.preventDefault()
        event.stopPropagation()
      }
      this.validatedCustom01 = true
    },
    async saveFormaPagamento() {
      let res = undefined
      let dados = { description: this.descricao, isActive: this.ativo }
      if (this.id == undefined) {
        res = await this.fp.cadastrar(dados)
      } else {
        res = await this.fp.alterar(this.id, dados)
      }
      if (res.status == 201) {
        this.$refs.toast.createToast('Cadastrado com sucesso!')
        this.$router.push('/forms/forma-pagamento')
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
        this.descricao = formaPagamento.data.descricao
        this.ativo = formaPagamento.data.ativo
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
