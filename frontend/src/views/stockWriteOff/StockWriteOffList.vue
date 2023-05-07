<template>
  <CRow>
    <CCol :xs="12">
      <CCard>
        <CCardHeader>
          <CRow>
            <CCol> <h4>Baixas de Estoque</h4> </CCol>
          </CRow>
        </CCardHeader>
        <CCardBody>
          <div class="row mb-3">
            <div class="col-md-12 mx-auto">
              <div class="input-group">
                <input
                  class="form-control border-end-0 border rounded-pill"
                  type="search"
                  placeholder="Motivo da baixa..."
                  id="example-search-input"
                  v-model="searchText"
                />
                <span class="input-group-append">
                  <button
                    class="btn btn-outline-secondary bg-dark border-bottom-0 border rounded-pill ms-n5"
                    type="button"
                    @click="find"
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
                  <CFromLabel>Data da baixa:</CFromLabel>
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
            </CCardBody>
          </CCard>
          <div class="bdr">
            <CTable responsive="xl">
              <CTableHead class="table-dark">
                <CTableRow>
                  <CTableHeaderCell scope="col">#</CTableHeaderCell>
                  <CTableHeaderCell scope="col">Motivo da baixa</CTableHeaderCell>
                  <CTableHeaderCell scope="col">Qtde</CTableHeaderCell>
                  <CTableHeaderCell scope="col">Data</CTableHeaderCell>
                  <CTableHeaderCell scope="col"></CTableHeaderCell>
                </CTableRow>
              </CTableHead>
              <CTableBody>
                <CTableRow v-for="item in this.itens" :key="item.id">
                  <CTableHeaderCell scope="row">{{ item.id }}</CTableHeaderCell>
                  <CTableDataCell>{{ item.product.title }}</CTableDataCell>
                  <CTableDataCell>{{ item.qty }}</CTableDataCell>
                  <CTableDataCell>{{
                    formatDateBr.toDateBr(item.record)
                  }}</CTableDataCell>
                  <CTableDataCell>
                    <CButton color="light" @click="toCallModalDele(item.id)">
                      <CIcon icon="cil-trash" />
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
  <toast ref="toast" />
  <ModalDelete
    v-if="modalExcluir != undefined"
    :modalExcluir="modalExcluir"
    @confirmeDelete="deleteItem"
  />
</template>
<script>
import StockWriteOffService from "@/Services/stockWriteOffService";
import Toast from "@/components/Toast.vue";
import { CForm, CTableDataCell } from "@coreui/vue";
import NextPageTable from "@/components/NextPageTable.vue";
import DateNow from "@/util/dateNow.js";
import FormatDateBr from "@/util/formatDateBr.js";
import ModalDelete from "@/components/ModalDelete.vue";
export default {
  components: { Toast, CForm, NextPageTable, CTableDataCell, ModalDelete },
  name: "Baixas de estoque",
  data() {
    return {
      id: "",
      dateNow: new DateNow(),
      service: new StockWriteOffService(),
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
      },
    };
  },
  methods: {
    async deleteItem() {
      const RESPONSE = await this.service.delete(this.id);      
      if (RESPONSE.status == 204) this.$router.push(`/stockWriteOff`);
      else
        this.$refs.toast.createToastDanger("Ocorreu um erro ao excluir o item");
      this.modalExcluir = false;
      this.find();
    },
    toCallModalDele(id) {
      this.id = id;
      this.modalExcluir = true;
    },
    async find() {
      let res = await this.service.find(
        this.searchText,
        this.filter,
        this.pageId
      );
      this.itens = res.content;
    },
    getDate() {
      this.filter.dtStart = this.dateNow.date(new Date(), 15, "-");
      this.filter.dtEnd = this.dateNow.date(new Date(), 15, "+");
    },
  },
  mounted() {
    this.getDate();
    this.find();
  },
};
</script>
<style scoped>
#teste-align {
  text-align: right;
}
</style>
