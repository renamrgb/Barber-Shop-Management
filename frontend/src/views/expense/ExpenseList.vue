<template>
  <CRow>
    <CCol :xs="12">
      <CCard>
        <CCardHeader>
          <CRow>
            <CCol> <h4>Despesas</h4> </CCol>
            <CCol id="teste-align">
              <router-link to="/expense/form">
                <CIcon icon="cil-playlist-add" size="xxl" />
              </router-link>
            </CCol>
          </CRow>
        </CCardHeader>
        <CCardBody>
          <div class="row mb-3">
            <div class="col-md-12 mx-auto">
              <div class="input-group">
                <input
                  class="form-control border-end-0 border rounded-pill"
                  type="search"
                  placeholder="Buscar"
                  id="example-search-input"
                  v-model="searchText"
                />
                <span class="input-group-append">
                  <button
                    class="btn btn-outline-secondary bg-dark border-bottom-0 border rounded-pill ms-n5"
                    type="button"
                    @click="getByDescription"
                  >
                    <CIcon icon="cil-magnifying-glass" size="x" />
                  </button>
                </span>
              </div>
            </div>
          </div>
          <CCard>
            <CCardBody>
              <div class="row mb-2">
                <div class="col">
                  <CFromLabel>Data de lançamento da despesa:</CFromLabel>
                </div>
              </div>
              <div class="row mb-3">
                <div class="col-2">
                  <input
                    type="date"
                    name=""
                    id=""
                    class="form-control"
                    v-model="filter.dtStart"
                  />
                </div>
                <div class="col-2">
                  <input
                    type="date"
                    name=""
                    id=""
                    class="form-control"
                    v-model="filter.dtEnd"
                  />
                </div>
              </div>
              <div class="row mb-0">
                <div class="col">
                  <CFormSwitch
                    id="formSwitchCheckDefault"
                    label="Apenas despesas pagas"
                    v-model="filter.bringPaid"
                  />
                </div>
              </div>
            </CCardBody>
          </CCard>
          <div class="bdr">
            <CTable responsive="xl">
              <CTableHead class="table-dark">
                <CTableRow>
                  <CTableHeaderCell scope="col">#</CTableHeaderCell>
                  <CTableHeaderCell scope="col"
                    >Data de lançamento</CTableHeaderCell
                  >
                  <CTableHeaderCell scope="col">Descrição</CTableHeaderCell>
                  <CTableHeaderCell scope="col">Valor</CTableHeaderCell>
                  <CTableHeaderCell scope="col"
                    >Tipo De Despesa</CTableHeaderCell
                  >
                  <CTableHeaderCell scope="col">Qtde Parcelas</CTableHeaderCell>
                  <CTableHeaderCell scope="col"></CTableHeaderCell>
                </CTableRow>
              </CTableHead>
              <CTableBody>
                <CTableRow v-for="item in this.itens" :key="item.id">
                  <CTableHeaderCell scope="row">{{ item.id }}</CTableHeaderCell>
                  <CTableDataCell>{{
                    formatDateBr.toDateBr(item.releaseDate)
                  }}</CTableDataCell>
                  <CTableDataCell>{{ item.description }}</CTableDataCell>
                  <CTableDataCell
                    >R$ {{ item.total.toFixed(2) }}</CTableDataCell
                  >
                  <CTableDataCell>{{
                    item.expenseType.description
                  }}</CTableDataCell>
                  <CTableDataCell>{{
                    item.quantityOfInstallments
                  }}</CTableDataCell>

                  <CTableDataCell>
                    <CButton
                      color="light"
                      @click="payOffExpense(item)"
                      style="margin-right: 1%"
                      >Quitar</CButton
                    >
                    <CButton
                      color="light"
                      style="margin-right: 1%"
                      @click="
                        () => {
                          modalExcluir = true;
                          id = item.id;
                        }
                      "
                    >
                      <CIcon icon="cil-trash" size="x" />
                    </CButton>
                    <CButton
                      color="light"
                      style="margin-right: 1%"
                      @click="this.alterar(item.id)"
                    >
                      <CIcon icon="cil-pencil" size="x" />
                    </CButton>
                  </CTableDataCell>
                </CTableRow>
              </CTableBody>
            </CTable>
            <NextPageTable
              ref="nextPageTable"
              :page-id="pageId"
              @update-pageId="updatePageId"
            />
          </div>
        </CCardBody>
      </CCard>
    </CCol>
  </CRow>
  <CModal
    :visible="modalExcluir"
    @close="
      () => {
        modalExcluir = false;
      }
    "
  >
    <CModalHeader
      dismiss
      @close="
        () => {
          modalExcluir = false;
        }
      "
    >
      <CModalTitle>Deseja excluir esse item?</CModalTitle>
    </CModalHeader>
    <CModalBody
      >O item será excluído permanentemente do banco de dados.</CModalBody
    >
    <CModalFooter>
      <CButton
        color="secondary"
        @click="
          () => {
            modalExcluir = false;
          }
        "
        >Cancelar</CButton
      >
      <CButton color="primary" @click="this.delete()">Confirmar</CButton>
    </CModalFooter>
  </CModal>
  <toast ref="toast" />
  <QuitarDespesa ref="quitarDespesa" />
</template>

<script>
import ExpenseService from "@/Services/expenseService";
import Toast from "@/components/Toast.vue";
import { CForm, CTableDataCell } from "@coreui/vue";
import NextPageTable from "@/components/NextPageTable.vue";
import QuitarDespesa from "@/components/expense/QuitarDespesa.vue";
import DateNow from "@/util/dateNow.js";
import FormatDateBr from "@/util/formatDateBr";
export default {
  components: { Toast, CForm, NextPageTable, CTableDataCell, QuitarDespesa },
  name: "FormaPagamento",
  data() {
    return {
      id: "",
      dateNow: new DateNow(),
      service: new ExpenseService(),
      formatDateBr: new FormatDateBr(),
      itens: "",
      modalExcluir: false,
      id: "",
      searchText: "",
      pageId: 0,
      searchText: "",
      filter: {
        dtStart: "",
        dtEnd: "",
        bringPaid: false,
      },
    };
  },
  methods: {
    getDate() {
      this.filter.dtStart = this.dateNow.date(new Date(), 30, "-");
      this.filter.dtEnd = this.dateNow.dateNowISO();
    },
    async delete() {
      let res = await this.service.delete(this.id);
      if (res.status == 400) {
        this.$refs.toast.createToastDanger(res.message);
      }
      this.modalExcluir = false;
      this.getExpenses();
    },
    async getByDescription() {
      let itensPaged = await this.service.getByDescriptionPaged(
        this.searchText,
        this.pageId,
        this.filter
      );
      this.itens = itensPaged.content;
      this.$refs.nextPageTable.totalPages = itensPaged.totalPages;
      this.$refs.nextPageTable.totalElements = itensPaged.totalElements;
      this.$refs.nextPageTable.pageable.pageNumber =
        itensPaged.pageable.pageNumber;
    },
    async getExpenses() {
      let itensPaged = await this.service.getAllPaged(this.pageId, this.filter);
      this.itens = itensPaged.content;
      this.$refs.nextPageTable.totalPages = itensPaged.totalPages;
      this.$refs.nextPageTable.totalElements = itensPaged.totalElements;
      this.$refs.nextPageTable.pageable.pageNumber =
        itensPaged.pageable.pageNumber;
    },
    alterar(id) {
      this.$router.push({ path: `/expense/form/${id}` });
    },
    updatePageId(newValue) {
      this.pageId = newValue;
      this.getExpenses();
    },
    payOffExpense(item) {
      this.$refs.quitarDespesa.visibleLiveDemo = true;
      this.$refs.quitarDespesa.expense = item;
    },
  },
  mounted() {
    this.getDate();
    this.getExpenses();
  },
};
</script>
<style scoped>
#teste-align {
  text-align: right;
}
</style>
