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
                  >Forma de Pagamento</CTableHeaderCell
                >
              </CTableRow>
            </CTableHead>
            <CTableBody>
              <CTableRow
                v-for="item in this.expense.installments"
                :key="item.id"
              >
                <CTableHeaderCell scope="row">{{ item.id }}</CTableHeaderCell>
                <CTableHeaderCell scope="row">R$ {{
                  item.installmentValue.toFixed(2)
                }}</CTableHeaderCell>
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
                    /> </CInputGroup
                ></CTableHeaderCell>
                <CTableHeaderCell scope="row">
                  <CFormSelect :options="optionsSelect" :searchable="true">
                  </CFormSelect>
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
      <CButton color="primary">Confirmar</CButton>
    </CModalFooter>
  </CModal>
</template>

<script>
import FormaPagamentoService from "@/Services/formaPagamentoService";
import FormatDateBr from "@/util/formatDateBr";
export default {
  name: "QuitarDepesa",
  data() {
    return {
      formatDateBr: new FormatDateBr(),
      formaPagamentoService: new FormaPagamentoService(),
      visibleLiveDemo: false,
      expense: {
        description: "",
        releaseDate: "",
        paymentMethod: {
          id: "",
        },
        amountPaid: "",
        paymentDate: "",
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
