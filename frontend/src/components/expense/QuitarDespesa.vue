<template>
  <CModal
    size="xl"
    :visible="visibleLiveDemo"
    @close="
      () => {
        visibleLiveDemo = false;
      }
    "
  >
    <CModalHeader>
      <CModalTitle>Quitar Despesa</CModalTitle>
    </CModalHeader>
    <CModalBody>
      <div class="row mb-3">
        <div class="col mb-auto">
          <CFormLabel for="description">* Descrição</CFormLabel>
          <input
            name="description"
            type="text"
            class="form-control"
            placeholder=""
            v-model="expense.description"
            disabled
          />
        </div>
        <div class="col-md-3">
          <CFormLabel for="releaseDate">* Data de lançamento</CFormLabel>
          <input
            type="date"
            name="releaseDate"
            v-model="expense.releaseDate"
            class="form-control"
            disabled
          />
        </div>
      </div>
      <div class="row">
        <div class="bdr">
          <CTable responsive="xl">
            <CTableHead class="table-dark">
              <CTableRow>
                <CTableHeaderCell scope="col">#</CTableHeaderCell>
                <CTableHeaderCell scope="col"
                  >Valor Da Parcela</CTableHeaderCell
                >
                <CTableHeaderCell scope="col">Vencimento</CTableHeaderCell>
                <CTableHeaderCell scope="col">Valor Pago</CTableHeaderCell>
                <CTableHeaderCell scope="col"
                  >Forma De Pagamento</CTableHeaderCell
                >
                <CTableHeaderCell scope="col"
                  ></CTableHeaderCell
                >
              </CTableRow>
            </CTableHead>
            <CTableBody>
              <CTableRow
                v-for="item in this.expense.installments"
                :key="item.id"
              >
                <CTableHeaderCell scope="row">{{ item.id }}</CTableHeaderCell>
                <CTableHeaderCell scope="row"
                  >R$ {{ item.installmentValue.toFixed(2) }}</CTableHeaderCell
                >
                <CTableHeaderCell scope="row">{{
                  this.formatDateBr.toDateBr(item.dueDate)
                }}</CTableHeaderCell>
                <CTableHeaderCell scope="row">
                  <CInputGroup>
                    <CInputGroupText>R$</CInputGroupText>
                    <CFormInput
                      name="price"
                      v-model="expense.amountPaid"                      
                      required
                      :disabled="true"
                    />
                  </CInputGroup>
                  <div
                    v-if="v$.expense.amountPaid.$errors.length > 0"
                    class="invalid-input-form"
                  >
                    {{ v$.expense.amountPaid.$errors[0].$message }}
                  </div></CTableHeaderCell
                >
                <CTableHeaderCell scope="row">
                  <CFormSelect
                    :options="optionsSelect"
                    :searchable="true"
                    v-model="paymentMethod.id"
                    :disabled="true"
                  >
                  </CFormSelect>
                  <div
                    v-if="v$.paymentMethod.id.$errors.length > 0"
                    class="invalid-input-form"
                  >
                    {{ v$.paymentMethod.id.$errors[0].$message }}
                  </div>
                </CTableHeaderCell>
                <CTableHeaderCell scope="row">
                  <CButton color="primary" variant="outline">Baixar</CButton>
                </CTableHeaderCell>
              </CTableRow>
            </CTableBody>
          </CTable>
        </div>
      </div>
    </CModalBody>
    <CModalFooter>
      <CButton
        color="secondary"
        @click="
          () => {
            visibleLiveDemo = false;
          }
        "
      >
        Fechar
      </CButton>
      <CButton color="primary" @click="submitForm()">Confirmar</CButton>
    </CModalFooter>
  </CModal>  
</template>

<script>
import { useVuelidate } from "@vuelidate/core";
import ValidationsMessage from "@/util/ValidationsMessage.js";
import { decimal } from "@vuelidate/validators";
import FormaPagamentoService from "@/Services/formaPagamentoService";
import FormatDateBr from "@/util/formatDateBr";
import ExpenseService from "@/Services/expenseService";
export default {
  name: "QuitarDepesa",
  data() {
    return {
      v$: useVuelidate(),
      validationsMessage: new ValidationsMessage(),
      formatDateBr: new FormatDateBr(),
      formaPagamentoService: new FormaPagamentoService(),
      service: new ExpenseService(),
      visibleLiveDemo: false,
      visibleLiveDemo2: false,
      paymentMethod: { id: "" },
      expense: {
        description: "",
        releaseDate: "",
        amountPaid: "",
        paymentDate: "",
      },
      optionsSelect: [],
    };
  },
  validations() {
    return {
      expense: {
        amountPaid: {
          required: this.validationsMessage.requiredMessage,
          decimal,
          minValue: this.validationsMessage.minMenssage(0.1),
        },
      },
      paymentMethod: {
        id: {
          required: this.validationsMessage.requiredMessage,
        }
      }
    };
  },
  methods: {
    async carregarOptionsSelect() {
      let res = await this.formaPagamentoService.consultarFormasPagamento();
      res.forEach((element) => {
        this.optionsSelect.push({
          value: element.id,
          label: element.description,
        });
      });
    },
    submitForm() {
      this.v$.$validate();      
      if(this.expense.amountPaid != this.expense.installments[0].installmentValue){
        this.v$.expense.amountPaid.$errors.push({$message: "O Valor Pago deve ser igual ao valor da parcela"});        
      }      
      if (!this.v$.$error) {
        this.confirm();
      }
      
    },
    async confirm() {
      let res = await this.service.payOffExpense(this.expense, this.paymentMethod);
    },
  },
  mounted() {
    this.carregarOptionsSelect();
  },
};
</script>

<style></style>
