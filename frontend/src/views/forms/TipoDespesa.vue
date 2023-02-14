<template>
  <CRow>
    <CCol :xs="12">
      <CCard class="mb-4">
        <CCardHeader>
          <strong>Tipos de Despesas</strong>
        </CCardHeader>
        <CCardBody>
          <CForm>
            <div>
              <CFormLabel for="description">Descrição</CFormLabel>
              <CFormInput
                name="description"
                type="text"                
                v-model.lazy="form.description"
                required
              />
              <div
                v-if="v$.form.description.$errors.length > 0"
                class="invalid-input-form"
              >
                {{ v$.form.description.$errors[0].$message }}
              </div>
            </div>
            <div>
              <br />
              <CFormSwitch
                id="formSwitchCheckDefault"
                label="Esse tipo gera parcelas?"
                v-model="form.generateInstallments"
              />
            </div>
            <div>
              <br />
              <CFormSwitch
                id="formSwitchCheckDefault"
                label="Ativo"
                v-model="form.isActive"
              />
            </div>
            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
              <CButton color="primary" class="me-md-2" @click="submitForm"
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
import { useVuelidate } from '@vuelidate/core'
import ValidationsMessage from '@/util/ValidationsMessage.js'
import Service from '@/Services/tipoDespesaService.js'
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
      visibleLiveDemo: false,
      validatedCustom: null,
      form: {
        description: '',
        generateInstallments: false,
        isActive: false,
      },
    }
  },
  validations() {
    return {
      form: {
        description: {
          required: this.validationsMessage.requiredMessage,
          maxLength: this.validationsMessage.maxLengthMenssage(60),
        },
      },
    }
  },
  methods: {
    submitForm(event) {      
      this.v$.$validate()
      if (!this.v$.$error) {
        this.salvar()
      }
    },
    async salvar(event) {
      let res = undefined
      if (this.id == undefined) {
        res = await this.service.cadastrar(this.form)
      } else {
        console.log(this.form);
        res = await this.service.alterar(this.id, this.form)
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
