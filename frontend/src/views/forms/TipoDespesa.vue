<template>
  <CRow>
    <CCol :xs="12">
      <CCard class="mb-4">
        <CCardHeader>
          <strong>Tipos de Despesas</strong>
        </CCardHeader>
        <CCardBody>
          <CForm
            class="row g-3 needs-validation"
            novalidate
            :validated="validatedCustom"
            @submit="validationRequired"
          >
            <div>
              <CFormLabel for="descricao">Descrição</CFormLabel>
              <CFormInput
                id="descricao"
                type="text"
                placeholder="Corte de cabelo..."
                v-model.lazy="descricao"
                required
              />
            </div>
            <div>
              <br />
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
                @click="salvar"
                type="submit"
                >Confirmar</CButton
              >
              <a class="btn btn-danger" href="/#/forms/tipoDespesas"
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
import Service from '@/Services/tipoDespesaService.js'
import Toast from '@/components/Toast.vue'
export default {
  components: { Toast },
  name: 'Tipo de Despesa',
  data() {
    return {
      id: this.$route.params.id,
      descricao: '',
      ativo: false,
      service: new Service(),
      visibleLiveDemo: false,
      validatedCustom: null,
    }
  },
  methods: {
    validationRequired(event) {
      const form = event.currentTarget
      if (form.checkValidity() === false) {
        event.preventDefault()
        event.stopPropagation()
      }
      this.validatedCustom = true
    },
    async salvar() {
      let res = undefined
      let dados = {
        descricao: this.descricao,
        ativo: this.ativo,
      }
      if (this.id == undefined) {
        res = await this.service.cadastrar(dados)
      } else {
        res = await this.service.alterar(this.id, dados)
      }
      if (res.status == 201) {
        this.$refs.toast.createToast('Cadastrado com sucesso!')
        this.$router.push('/forms/tipoDespesas')
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
        this.descricao = item.data.descricao
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
