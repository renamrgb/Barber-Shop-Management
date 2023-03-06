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
          <div class="row">
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
          <div class="bdr">
            <CTable responsive="xl">
              <CTableHead class="table-dark">
                <CTableRow>
                  <CTableHeaderCell scope="col">#</CTableHeaderCell>
                  <CTableHeaderCell scope="col">Descrição</CTableHeaderCell>
                  <CTableHeaderCell scope="col">Excluir?</CTableHeaderCell>
                  <CTableHeaderCell scope="col">Alterar?</CTableHeaderCell>
                </CTableRow>
              </CTableHead>
              <CTableBody>
                <CTableRow v-for="fp in this.itens" :key="fp.id">
                  <CTableHeaderCell scope="row">{{ fp.id }}</CTableHeaderCell>
                  <CTableDataCell>{{ fp.description }}</CTableDataCell>
                  <CTableDataCell>
                    <CIcon
                      icon="cil-trash"
                      size="xl"
                      @click="
                        () => {
                          modalExcluir = true;
                          id = fp.id;
                        }
                      "
                    />
                  </CTableDataCell>
                  <CTableDataCell>
                    <CIcon
                      icon="cil-pencil"
                      size="xl"
                      @click="this.alterar(fp.id)"
                    />
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
      <CButton color="primary" @click="this.delete">Confirmar</CButton>
    </CModalFooter>
  </CModal>
  <toast ref="toast" />
</template>

<script>
import ExpenseService from "@/Services/expenseService";
import Toast from "@/components/Toast.vue";
import { CForm } from "@coreui/vue";
import NextPageTable from "@/components/NextPageTable.vue";

export default {
  components: { Toast, CForm, NextPageTable },
  name: "FormaPagamento",
  data() {
    return {
      service: new ExpenseService(),
      itens: "",
      modalExcluir: false,
      id: "",
      searchText: "",
      pageId: 0,
      searchText: "",
    };
  },
  methods: {
    async delete() {
      let res = await this.service.delete(this.id);
      if (res.status == 400) {
        this.$refs.toast.createToast(res.message);
      }
      this.modalExcluir = false;
      this.getExpenses();
    },
    async getByDescription() {
      let itensPaged = await this.service.getByDescriptionPaged(
        this.searchText,
        this.pageId
      );
      this.itens = itensPaged.content;
      this.$refs.nextPageTable.totalPages = itensPaged.totalPages;
      this.$refs.nextPageTable.totalElements = itensPaged.totalElements;
      this.$refs.nextPageTable.pageable.pageNumber =
        itensPaged.pageable.pageNumber;
    },
    async getExpenses() {
      let itensPaged = await this.service.getAllPaged(this.pageId);
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
  },
  mounted() {
    this.getExpenses();
  },
};
</script>
<style scoped>
#teste-align {
  text-align: right;
}
</style>
