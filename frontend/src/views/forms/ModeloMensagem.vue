<template>
  <CRow>
    <CCol :xs="12">
      <CCard class="mb-4">
        <CCardHeader>
          <strong>Modelo de mensagem</strong>
        </CCardHeader>
        <CCardBody>
          <CForm>
            <div class="mb-3">
              <CFormLabel for="titulo">* Título da mensagem</CFormLabel>
              <CFormInput
                id="titulo"
                type="text"
                placeholder="Mensagem de confirmação..."
                v-model.lazy="form.title"
                required
              />
              <div
                v-if="v$.form.title.$errors.length > 0"
                class="invalid-input-form"
              >
                {{ v$.form.title.$errors[0].$message }}
              </div>
            </div>
            <div class="mb-3">
              <CFormTextarea
                id="mensagem"
                label="* Corpo da Mensagem"
                rows="5"
                v-model.lazy="form.bodyMessage"
                required
              ></CFormTextarea>
              <div
                v-if="v$.form.bodyMessage.$errors.length > 0"
                class="invalid-input-form"
              >
                {{ v$.form.bodyMessage.$errors[0].$message }}
              </div>
            </div>
            <div class="mb-3">
              <CFormSwitch
                id="formSwitchCheckDefault"
                label="Ativo"
                v-model="form.isActive"
              />
            </div>
            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
              <CButton color="primary" @click="submitForm">Confirmar</CButton>
              <a class="btn btn-danger" href="/#/forms/modeloMensagem"
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
import { useVuelidate } from '@vuelidate/core'
import ValidationsMessage from '@/util/ValidationsMessage.js'
import Service from '@/Services/modeloMensagemService.js'
import Toast from '@/components/Toast.vue'
export default {
  components: { Toast },
  name: 'Tipo de Despesa',
  data() {
    return {
      v$: useVuelidate(),
      validationsMessage: new ValidationsMessage(),
      id: this.$route.params.id,
      service: new Service(),
      form: {
        title: '',
        bodyMessage: '',
        isActive: false,
      },
    }
  },
  validations() {
    return {
      form: {
        title: {
          required: this.validationsMessage.requiredMessage,
          maxLength: this.validationsMessage.maxLengthMenssage(60),
        },
        bodyMessage: {
          required: this.validationsMessage.requiredMessage,
          maxLength: this.validationsMessage.maxLengthMenssage(1000),
        },
      },
    }
  },
  methods: {
    submitForm() {
      this.v$.$validate()
      if (!this.v$.$error) {
        this.salvar()
      }
    },
    async salvar() {
      let res = undefined
      if (this.id == undefined) {
        res = await this.service.cadastrar(this.form)
      } else {
        res = await this.service.alterar(this.id, this.form)
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
            this.$refs.toast.createToast(`${element.message} `)
          })
        }
      }
    },
    async consultarUm() {
      if (this.id != undefined) {
        this.form = await this.service.buscarUm(this.id)        
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
