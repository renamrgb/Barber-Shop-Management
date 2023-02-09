<template #default="{ v }">
  <CRow>
    <CCol :xs="12">
      <CCard class="mb-4">
        <CCardHeader>
          <strong>Lançar depesa</strong>
        </CCardHeader>
        <CCardBody>
          <CForm>
            <div class="mb-3">
              <CFormLabel for="description">* Descrição</CFormLabel>
              <CFormInput
                name="description"
                type="text"
                placeholder=""
                v-model="form.description"
                required
              />
              <div
                v-if="v$.form.description.$errors.length > 0"
                class="invalid-input-form"
              >
                {{ v$.form.description.$errors[0].$message }}
              </div>
            </div>
            <div class="mb-3">
              <Multiselect
                v-model="value"
                placeholder="Procedimentos que o profissional realiza..."
                label="description"
                trackBy="description"
                :options="options"
                :searchable="true"
                mode="tags"
              >
              </Multiselect>
            </div>
            <div class="row mb-3">
              <div class="col">
                <CFormLabel for="total">* Total</CFormLabel>
                <CInputGroup>
                  <CInputGroupText>R$</CInputGroupText>
                  <CFormInput id="total" v-model="form.total" min="0" />
                </CInputGroup>
                <div
                  v-if="v$.form.total.$errors.length > 0"
                  class="invalid-input-form"
                >
                  {{ v$.form.total.$errors[0].$message }}
                </div>
              </div>
              <div class="col">
                <CFormLabel for="total">* Dias entre as parcelas</CFormLabel>
                <CFormInput
                  id="total"
                  v-model="form.daysBeetwenInstallments"
                  min="0"
                />
                <div
                  v-if="v$.form.daysBeetwenInstallments.$errors.length > 0"
                  class="invalid-input-form"
                >
                  {{ v$.form.daysBeetwenInstallments.$errors[0].$message }}
                </div>
              </div>
            </div>
            <div class="row mb-3">
              <div class="col">
                <CFormLabel for="releaseDate">* Data de lançamento</CFormLabel>
                <input
                  type="date"
                  name="releaseDate"
                  v-model="form.releaseDate"
                  class="form-control"
                />
                <div
                  v-if="v$.form.releaseDate.$errors.length > 0"
                  class="invalid-input-form"
                >
                  {{ v$.form.releaseDate.$errors[0].$message }}
                </div>
              </div>
              <div class="col">
                <CFormLabel for="quantityOfInstallments"
                  >* Qtd de parcelas</CFormLabel
                >
                <CFormInput
                  name="quantityOfInstallments"
                  type="number"
                  v-model="form.quantityOfInstallments"
                  min="0"
                  required
                />
                <div
                  v-if="v$.form.quantityOfInstallments.$errors.length > 0"
                  class="invalid-input-form"
                >
                  {{ v$.form.quantityOfInstallments.$errors[0].$message }}
                </div>
              </div>
            </div>
            <div v-if="!this.generateInstallment">
              <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                <CButton
                  color="primary"
                  class="me-md-2"
                  @click="this.generateInstallments()"
                  >Gerar parcelas</CButton
                >
              </div>
            </div>
          </CForm>
          <div v-if="this.installments.length > 0">
            <div id="tableInstallments">
              <div class="bdr">
                <CTable responsive="xl">
                  <CTableHead class="table-dark">
                    <CTableRow>
                      <CTableHeaderCell scope="col">#</CTableHeaderCell>
                      <CTableHeaderCell scope="col">Valor</CTableHeaderCell>
                      <CTableHeaderCell scope="col"
                        >Vencimento</CTableHeaderCell
                      >
                    </CTableRow>
                  </CTableHead>
                  <CTableBody>
                    <CTableRow v-for="e in this.installments" :key="e.id">
                      <CTableHeaderCell scope="row">{{
                        e.id
                      }}</CTableHeaderCell>
                      <CTableDataCell>{{
                        e.installmentValue.toFixed(2)
                      }}</CTableDataCell>
                      <CTableDataCell>{{ e.dueDate }}</CTableDataCell>
                    </CTableRow>
                  </CTableBody>
                </CTable>
                <div v-if="this.generateInstallment">
                  <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                    <CButton color="primary" class="me-md-2" @click="submitForm"
                      >Confirmar</CButton
                    >
                    <a href="/#/forms/produto" class="btn btn-danger"
                      >Cancelar</a
                    >
                  </div>
                </div>
              </div>
            </div>
          </div>
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
import Service from '@/Services/expenseService.js'
import Toast from '@/components/Toast.vue'
import Multiselect from '@vueform/multiselect/src/Multiselect'
import TipoDespesaService from '@/Services/tipoDespesaService'

export default {
  components: { Toast, Multiselect },
  name: 'Produtos',
  data() {
    return {
      v$: useVuelidate(),
      validationsMessage: new ValidationsMessage(),
      tipoDeDespesaService: new TipoDespesaService(),
      id: this.$route.params.id,
      service: new Service(),
      generateInstallment: false,
      form: {
        description: '',
        expenseType: {
          id: '',
        },
        total: '00.00',
        daysBeetwenInstallments: 0,
        releaseDate: '',
        installments: [],
      },
      installments: [],
      value: [],
      options: [],
    }
  },
  validations() {
    return {
      form: {
        description: {
          required: this.validationsMessage.requiredMessage,
          maxLength: this.validationsMessage.maxLengthMenssage(60),
        },
        total: {
          required: this.validationsMessage.requiredMessage,
          decimal,
          minValue: this.validationsMessage.minMenssage(0),
        },
        total: { maxLength: this.validationsMessage.maxLengthMenssage(60) },
        releaseDate: {
          required: this.validationsMessage.requiredMessage,
        },
        daysBeetwenInstallments: {
          required: this.validationsMessage.requiredMessage,
          minValue: this.validationsMessage.minMenssage(0),
        },
        quantityOfInstallments: {
          required: this.validationsMessage.requiredMessage,
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
      this.form.expenseType.id = this.value[0]
      this.installments.forEach((element) => {
        this.form.installments.push({
          installmentValue: element.installmentValue.toFixed(2),
          dueDate: element.dueDate,
        })
      })
      console.log(this.form)
      if (this.id == undefined) {
        this.total = this.form.total.replace(',', '.')
        res = await this.service.cadastrar(this.form)
      } else {
        res = await this.service.alterar(this.id, this.form)
      }
      if (res.status == 201) {
        this.$refs.toast.createToast('Cadastrado com sucesso!')
        this.$router.push('/expense')
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
    async carregarMultiSelect() {
      let res = await this.tipoDeDespesaService.consultarTodos()
      res.forEach((element) => {
        this.options.push({
          value: element.id,
          description: element.description,
        })
      })
    },
    async generateInstallments() {
      this.v$.$validate()
      if (!this.v$.$error) {
        this.installments = await this.service.generateInstallments(this.form)
        this.generateInstallment = true
      }
    },
  },
  mounted() {
    this.carregarMultiSelect()
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
<style src="@vueform/multiselect/themes/default.css"></style>
