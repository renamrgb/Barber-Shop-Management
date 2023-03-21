<template>
  <CRow>
    <CCol :xs="12">
      <CCard>
        <CCardHeader>
          <CRow>
            <CCol> <h4>Lançamentos de produtos em estoque</h4> </CCol>
            <CCol id="teste-align">
              <router-link to="/stock/launchProducts">
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
                  placeholder="Nome do fornecedor..."
                  id="example-search-input"
                  v-model="searchText"
                />
                <span class="input-group-append">
                  <button
                    class="btn btn-outline-secondary bg-dark border-bottom-0 border rounded-pill ms-n5"
                    type="button"
                    @click="getBySupplier"
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
                  <CFromLabel>Data da nota fiscal:</CFromLabel>
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
                  <CTableHeaderCell scope="col">Fornecedor</CTableHeaderCell>
                  <CTableHeaderCell scope="col">Valor</CTableHeaderCell>
                  <CTableHeaderCell scope="col">Data da NFE</CTableHeaderCell>
                  <CTableHeaderCell scope="col"></CTableHeaderCell>
                </CTableRow>
              </CTableHead>
              <CTableBody>
                <CTableRow v-for="item in this.itens" :key="item.id">
                  <CTableHeaderCell scope="row">{{ item.id }}</CTableHeaderCell>
                  <CTableDataCell>{{ item.supplier }}</CTableDataCell>
                  <CTableDataCell
                    >R$ {{ item.nfe.valueNfe.toFixed(2) }}</CTableDataCell
                  >
                  <CTableDataCell>{{ formatDateBr.toDateBr(item.nfe.dateofPurchase) }}</CTableDataCell>
                  <CTableDataCell>
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
                      Estornar
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
</template>
<script>
import LancarProdutoService from "@/Services/LancarProdutoService";
import Toast from "@/components/Toast.vue";
import { CForm, CTableDataCell } from "@coreui/vue";
import NextPageTable from "@/components/NextPageTable.vue";
import DateNow from "@/util/dateNow.js";
import FormatDateBr from "@/util/formatDateBr.js";
export default {
  components: { Toast, CForm, NextPageTable, CTableDataCell },
  name: "Listar Lançamentos de Produtos",
  data() {
    return {
      id: "",
      dateNow: new DateNow(),
      service: new LancarProdutoService(),
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
    getDate() {
      this.filter.dtStart = this.dateNow.date(new Date(), 30, "-");
      this.filter.dtEnd = this.dateNow.dateNowISO();
    },
    async getBySupplier() {
      let itensPaged = await this.service.getByDescriptionPaged(
        this.searchText,
        this.pageId,
        this.filter
      );
      this.itens = itensPaged.content;      
    },
    async gelAll() {
      let itensPaged = await this.service.getAllPaged(this.pageId, this.filter);
      this.itens = itensPaged.content;
    },
    alterar(id) {
      this.$router.push({ path: `/stock/launchProducts/${id}` });
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
    this.gelAll();
  },
};
</script>
<style scoped>
#teste-align {
  text-align: right;
}
</style>
