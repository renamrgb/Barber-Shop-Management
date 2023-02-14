<template #default="{ v }">
  <CRow>
    <CCol :xs="12">
      <CCard class="mb-4">
        <CCardHeader>
          <strong>Registar Despesa</strong>
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
            <div class="row mb-3">
              <div class="col">
                <CFormLabel for="typeExpense">* Tipo De Despesa</CFormLabel>
                <CFormSelect
                  v-model="form.expenseType.id"
                  :options="optionsSelect"
                  :searchable="true"
                  @click="
                    () => {
                      if (
                        this.form.expenseType.id != undefined &&
                        this.optionsSelect[this.form.expenseType.id]
                          .generateInstallments == true
                      ) {
                        this.minQtyInstallments = 1
                        this.minDaysBInstallments = 1
                      } else {
                        this.minQtyInstallments = 0
                        this.minDaysBInstallments = 0
                      }
                    }
                  "
                >
                </CFormSelect>
              </div>
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
            </div>
            <div
              class="row"
              v-if="
                this.form.expenseType.id != undefined &&
                this.optionsSelect[this.form.expenseType.id]
                  .generateInstallments == false
              "
            >
              <div class="col mb-3">
                <div class="col">
                  <CFormLabel for="releaseDate"
                    >* Data de vencimento</CFormLabel
                  >
                  <input
                    type="date"
                    name="releaseDate"
                    v-model="form.dueDate"
                    class="form-control"
                  />
                </div>
              </div>
            </div>
            <div
              v-if="
                this.form.expenseType.id != undefined &&
                this.optionsSelect[this.form.expenseType.id]
                  .generateInstallments == true
              "
            >
              <div class="row mb-3">
                <div class="col">
                  <CFormLabel for="quantityOfInstallments"
                    >* Qtde de parcelas</CFormLabel
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
            </div>
          </CForm>
          <div v-if="this.installments.length > 0">
            <div id="tableInstallments">
              <div class="bdr">
                <CTable responsive="xl">
                  <CTableHead class="table-dark">
                    <CTableRow>
                      <CTableHeaderCell scope="col">#</CTableHeaderCell>
                      <CTableHeaderCell scope="col">Descrição</CTableHeaderCell>
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
                        this.form.description
                      }}</CTableDataCell>
                      <CTableDataCell
                        >R$ {{ e.installmentValue.toFixed(2) }}</CTableDataCell
                      >
                      <CTableDataCell>
                        <input
                          type="date"
                          name="releaseDate"
                          v-model="e.dueDate"
                          class="form-control"
                        />
                      </CTableDataCell>
                    </CTableRow>
                  </CTableBody>
                </CTable>
              </div>
            </div>
          </div>
          <div>
            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
              <CButton
                v-if="
                  this.form.expenseType.id != undefined &&
                  this.optionsSelect[this.form.expenseType.id]
                    .generateInstallments == true
                "
                color="primary"
                class="me-md-2"
                @click="this.generateInstallments()"
                >Gerar parcelas</CButton
              >
              <CButton
                v-if="
                  (this.form.expenseType.id != undefined &&
                    this.optionsSelect[this.form.expenseType.id]
                      .generateInstallments == false) ||
                  this.installments.length > 0
                "
                color="primary"
                class="me-md-2"
                @click="submitForm"
                >Confirmar</CButton
              >
              <a href="/#/expense" class="btn btn-danger">Cancelar</a>
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
import TipoDespesaService from '@/Services/tipoDespesaService'
export default {
  components: { Toast },
  name: 'Produtos',
  data() {
    return {
      v$: useVuelidate(),
      validationsMessage: new ValidationsMessage(),
      tipoDeDespesaService: new TipoDespesaService(),
      id: this.$route.params.id,
      service: new Service(),
      alreadyGeneratedInstallments: false,
      generateInstallment: false,
      form: {
        description: '',
        expenseType: {
          id: undefined,
        },
        total: '00.00',
        daysBeetwenInstallments: 0,
        quantityOfInstallments: 0,
        releaseDate: this.currentDate(),
        dueDate: this.currentDate(),
        installments: [],
      },
      optionsSelect: ['Abra este menu de seleção'],
      installments: [],
      minQtyInstallments: 0,
      minDaysBInstallments: 0,
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
          minValue: this.validationsMessage.minMenssage(0.1),
        },
        releaseDate: {
          required: this.validationsMessage.requiredMessage,
        },
        daysBeetwenInstallments: {
          required: this.validationsMessage.requiredMessage,
          minValue: this.validationsMessage.minMenssage(
            this.minDaysBInstallments,
          ),
        },
        quantityOfInstallments: {
          required: this.validationsMessage.requiredMessage,
          minValue: this.validationsMessage.minMenssage(
            this.minQtyInstallments,
          ),
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
    currentDate() {
      const timeElapsed = Date.now()
      const today = new Date(timeElapsed)
      let date = today.toISOString().split('T')
      return date[0]
    },
    async salvar() {
      let res = undefined
      if (this.id == undefined) {
        if (this.installments != undefined) {
          this.installments.forEach((element) => {
            this.form.installments.push({
              installmentValue: element.installmentValue,
              dueDate: element.dueDate,
            })
          })
        }
        this.total = this.form.total.replace(',', '.')
        res = await this.service.cadastrar(this.form)
      } else {
        this.form.installments = this.installments
        res = await this.service.alterar(this.form, this.id)
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
    async carregarOptionsSelect() {
      let res = await this.tipoDeDespesaService.consultarTodos()
      res.forEach((element) => {
        this.optionsSelect.push({
          value: element.id,
          label: element.description,
          generateInstallments: element.generateInstallments,
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
    async getOneExpense(id) {
      this.form = await this.service.getOneExpense(id)
      this.installments = this.form.installments
    },
  },
  mounted() {
    this.carregarOptionsSelect()
    if (this.id != undefined) {
      this.getOneExpense(this.id)
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
</style>
<style src="@vueform/multiselect/themes/default.css"></style>
