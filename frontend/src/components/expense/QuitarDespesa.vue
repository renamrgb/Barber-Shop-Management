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
                  >Data Do Pagamento</CTableHeaderCell
                >
                <CTableHeaderCell scope="col"></CTableHeaderCell>
              </CTableRow>
            </CTableHead>
            <CTableBody>
              <CTableRow
                v-for="(item, index) in this.expense.installments"
                :key="index"
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
                    <input
                      name="price"
                      v-model="item.amountPaid"
                      class="form-control"
                      :disabled="disabledInputs[index].disabled"
                    />
                  </CInputGroup>
                </CTableHeaderCell>
                <CTableHeaderCell scope="row">
                  <CFormSelect
                    :options="optionsSelect"
                    :searchable="true"
                    v-model="item.paymentMethod.id"
                    :disabled="disabledInputs[index].disabled"
                  >
                  </CFormSelect>
                </CTableHeaderCell>
                <CTableHeaderCell>
                  <input
                    type="date"
                    class="form-control"
                    name="paymentDate"
                    id="paymentDate"
                    v-model="item.paymentDate"
                    :disabled="disabledInputs[index].disabled"
                  />
                </CTableHeaderCell>
                <CTableHeaderCell scope="row">
                  <CButton
                    v-if="item.paid == false"
                    color="primary"
                    variant="outline"
                    @click="baixar(index, item)"
                    >Baixar</CButton
                  >
                  <CButton
                    v-else-if="item.paid == true"
                    color="primary"
                    variant="outline"
                    @click="estornar(index, item)"
                    >Estornar</CButton
                  >
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
    </CModalFooter>
  </CModal>
  <toast ref="toast" />
</template>

<script>
import { useVuelidate } from "@vuelidate/core";
import ValidationsMessage from "@/util/ValidationsMessage.js";
import FormaPagamentoService from "@/Services/formaPagamentoService";
import FormatDateBr from "@/util/formatDateBr";
import ExpenseService from "@/Services/expenseService";
import Service from "@/Services/expenseService.js";
import Toast from "@/components/Toast.vue";
import DateNow from "@/util/dateNow.js";
export default {
  components: { Toast },
  name: "QuitarDepesa",
  data() {
    return {
      v$: useVuelidate(),
      dateNow: new DateNow(),
      validationsMessage: new ValidationsMessage(),
      formatDateBr: new FormatDateBr(),
      formaPagamentoService: new FormaPagamentoService(),
      service: new ExpenseService(),
      visibleLiveDemo: false,
      visibleLiveDemo2: false,
      paymentMethod: { id: "" },
      service: new Service(),
      expense: {
        description: "",
        releaseDate: "",
        amountPaid: "",
        paymentDate: "",
      },
      optionsSelect: ["Abra este menu de seleção"],
      disabledInputs: [],
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
    setStateInputInstallment(size) {
      for (let i = 0; i < size; i++) {
        this.disabledInputs.push({ disabled: true });
      }
    },
    baixar(index) {
      console.log(` index ${index} \n paid ${this.expense.installments[index].paid} \n disabled ${this.disabledInputs[index].disabled}`);
      debugger;
      if (this.disabledInputs[index].disabled) {
        if (index == 0 && this.expense.installments[index].paid == false)
          this.disabledInputs[index].disabled = false;
        else if (
          index > 0 &&
          this.expense.installments[index - 1].paid == false
        ) {
          this.disabledInputs[index].disabled = true;
          this.$refs.toast.createToastDanger(
            "Você não pode quitar essa parcela, a anterior ainda não está paga!"
          );
        }else{
          this.disabledInputs[index].disabled = false;
        }
      } else this.save(index);
    },
    async save(index) {
      let obj = {
        amountPaid: this.expense.installments[index].amountPaid,
        paymentMethod: {
          id: this.expense.installments[index].paymentMethod.id,
        },
        paymentDate: this.expense.installments[index].paymentDate,
      };
      if (this.validForm(index)) {
        let res = await this.service.payOffExpense(this.expense.id, index, obj);
        if (res.status == 200) {
          res = res.data;
          this.$refs.toast.createToast("Quitação feita com sucesso!");
          this.disabledInputs[index].disabled = true;
          this.expense.installments[index].paymentDate =
            res.installments[index].paymentDate;
          this.expense.installments[index].paid = res.installments[index].paid;
        }
      }
    },
    async estornar(index) {
      let res = await this.service.reverse(this.expense.id, index);
      if (res.status == 200) {
        res = res.data;
        this.$refs.toast.createToast("Estorno feito com sucesso!");
        this.expense.installments[index].amountPaid =
          res.installments[index].amountPaid;
        this.expense.installments[index].paymentMethod.id = res.installments[
          index
        ].paymentMethod.id = 0;
        this.expense.installments[index].paymentDate =
          res.installments[index].paymentDate;
        this.expense.installments[index].paid = res.installments[index].paid;
      }
    },
    validForm(index) {
      let valid = true;
      let amountPaid = parseInt(this.expense.installments[index].amountPaid);
      let installmentValue = this.expense.installments[index].installmentValue;
      let paymentMethod = this.expense.installments[index].paymentMethod.id;
      let paymentDate = this.expense.installments[index].paymentDate;
      if (
        paymentMethod == undefined ||
        paymentMethod == "Abra este menu de seleção"
      ) {
        valid = false;
        this.$refs.toast.createToastDanger(
          "Preencha o campo forma de pagamento!"
        );
      }
      if (amountPaid != installmentValue) {
        valid = false;
        this.$refs.toast.createToastDanger(
          "O valor pago deve ser igual ao valor da parcela!"
        );
      }
      if (paymentDate == undefined || paymentDate > this.dateNow.dateNowISO()) {
        valid = false;
        this.$refs.toast.createToastDanger(
          "A data de pagamento não pode ser maior que a data atual ou vazia!"
        );
      }
      return valid;
    },
  },
  mounted() {
    this.carregarOptionsSelect();
  },
  beforeUpdate() {
    this.setStateInputInstallment(30);
  },
};
</script>

<style></style>
