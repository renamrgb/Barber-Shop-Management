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
      <div class="row mb-3">
        <div class="col">
          <CFormLabel for="price">* Valor Pago</CFormLabel>
          <CInputGroup>
            <CInputGroupText>R$</CInputGroupText>
            <CFormInput name="price" v-model="expense.amountPaid" required />
          </CInputGroup>
        </div>
        <div class="col">
          <CFormLabel for="typeExpense">* Forma De Pagamento</CFormLabel>
          <CFormSelect :options="optionsSelect" :searchable="true">
          </CFormSelect>
        </div>
        <div class="col-md-3">
          <CFormLabel for="releaseDate">* Data de Pagamento</CFormLabel>
          <input
            type="date"
            name="releaseDate"
            v-model="expense.paymentDate"
            class="form-control"            
          />
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
      <CButton color="primary">Confirmar</CButton>
    </CModalFooter>
  </CModal>
</template>

<script>
import FormaPagamentoService from "@/Services/formaPagamentoService";
export default {
  name: "QuitarDepesa",
  data() {
    return {
      formaPagamentoService: new FormaPagamentoService(),
      visibleLiveDemo: false,
      expense: {
        description: "",
        releaseDate: "",
        paymentMethod: {
          id: "",
        },
        amountPaid: "",
        paymentDate: ""
      },
      optionsSelect: [],
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
  },
  mounted() {
    this.carregarOptionsSelect();
  },
};
</script>

<style></style>
