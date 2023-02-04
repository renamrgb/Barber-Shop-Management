<template #default="{ v }">
  <CRow>
    <CCol :xs="12">
      <CCard class="mb-4">
        <CCardHeader>
          <strong>Produtos</strong>
        </CCardHeader>
        <CCardBody>
          <CForm>
            <div class="mb-3">
              <CFormLabel for="title">* Título</CFormLabel>
              <CFormInput
                name="title"
                type="text"
                placeholder="Creme de barbear..."
                v-model="form.title"
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
              <CFormLabel for="descricao">Marca</CFormLabel>
              <CFormInput
                id="brand"
                type="text"
                placeholder=""
                v-model="form.brand"
                maxlength="65"
              />
              <div
                v-if="v$.form.brand.$errors.length > 0"
                class="invalid-input-form"
              >
                {{ v$.form.brand.$errors[0].$message }}
              </div>
            </div>
            <div class="row mb-3">
              <div class="col">
                <CFormLabel for="price">* Preço</CFormLabel>
                <CInputGroup>
                  <CInputGroupText>R$</CInputGroupText>
                  <CFormInput
                    name="price"
                    v-model="form.price"
                    min="0"
                    required
                  />
                </CInputGroup>
                <div
                  v-if="v$.form.price.$errors.length > 0"
                  class="invalid-input-form"
                >
                  {{ v$.form.price.$errors[0].$message }}
                </div>
              </div>
              <div class="col">
                <CFormLabel for="quantity">* Qtd em estoque</CFormLabel>
                <CFormInput
                  name="quantity"
                  type="number"
                  v-model="form.quantity"
                  min="0"
                  required
                />
                <div
                  v-if="v$.form.quantity.$errors.length > 0"
                  class="invalid-input-form"
                >
                  {{ v$.form.quantity.$errors[0].$message }}
                </div>
              </div>
            </div>
            <div class="mb-1">
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
              <a href="/#/forms/produto" class="btn btn-danger">Cancelar</a>
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
import { decimal } from '@vuelidate/validators'
import Service from '@/Services/produtoService.js'
import Toast from '@/components/Toast.vue'

export default {
  components: { Toast },
  name: 'Produtos',
  data() {
    return {
      v$: useVuelidate(),
      validationsMessage: new ValidationsMessage(),
      id: this.$route.params.id,
      service: new Service(),
      form: {
        title: '',
        brand: '',
        quantity: 0,
        price: '00.00',
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
        brand: { maxLength: this.validationsMessage.maxLengthMenssage(60) },
        quantity: {
          required: this.validationsMessage.requiredMessage,
          minValue: this.validationsMessage.minMenssage(0),
        },
        price: {
          required: this.validationsMessage.requiredMessage,
          decimal,
          minValue: this.validationsMessage.minMenssage(0),
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
      let dados = {
        title: this.form.title,
        brand: this.form.brand,
        quantity: this.form.quantity,
        price: parseFloat(this.form.price),
        isActive: this.form.isActive,
      }
      if (this.id == undefined) {
        dados.price = this.form.price.replace(',', '.')
        res = await this.service.cadastrar(dados)
      } else {
        res = await this.service.alterar(this.id, dados)
      }
      if (res.status == 201) {
        this.$refs.toast.createToast('Cadastrado com sucesso!')
        this.$router.push('/forms/produto')
      } else if (res.status == 200) {
        this.$refs.toast.createToast('Alterado com sucesso!')
      } else {
        let vetErros = res.response.data.fieldErrors

        vetErros.forEach((element) => {
          this.$refs.toast.createToast(`${element.message} `)
        })
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
<style scoped>
.div-center {
  display: flex;
  justify-content: center;
  margin: 30px;
}
.label {
  margin-right: 10px;
}
.error {
  border: 3px solid red;
}
.button-submit {
  margin-top: 15px;
  width: 250px;
}
.error-color {
  color: red;
}
</style>
