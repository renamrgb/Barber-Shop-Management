<template>
  <CRow>
    <CCol :xs="12">
      <CCard>
        <CCardHeader>
          <CRow>
            <CCol> <h4>Formas de pagamento</h4> </CCol>
            <CCol id="teste-align">
              <router-link to="/forms/forma-pagamento/cadastro">
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
                  <CTableHeaderCell scope="col">Ativo</CTableHeaderCell>
                  <CTableHeaderCell scope="col"></CTableHeaderCell>
                  <!-- <CTableHeaderCell scope="col">Alterar?</CTableHeaderCell> -->
                </CTableRow>
              </CTableHead>
              <CTableBody>
                <CTableRow v-for="fp in this.fps" :key="fp.id">
                  <CTableHeaderCell scope="row">{{ fp.id }}</CTableHeaderCell>
                  <CTableDataCell>{{ fp.description }}</CTableDataCell>
                  <IconsTdTable
                    :isActiveProps="fp.isActive"
                    :id-item-props="fp.id"
                    :serviceProps="this.fp"
                    @get-all="getFormasPagamento"
                    @alterar-item="alterar"
                  />                  
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
</template>

<script>
import FormaPagamentoService from '@/Services/formaPagamentoService'
import Toast from '@/components/Toast.vue'
import { CForm } from '@coreui/vue'
import NextPageTable from "@/components/NextPageTable.vue";
import IconsTdTable from "@/components/IconsTdTable.vue";
export default {
  components: { Toast, CForm, NextPageTable, IconsTdTable },
  name: 'FormaPagamento',
  data() {
    return {
      fp: new FormaPagamentoService(),
      fps: '',
      modalExcluir: false,
      idFp: '',
      searchText: '',
      pageId: 0,
    }
  },
  methods: {
    async getByDescription() {
      let itensPaged = await this.fp.getByDescriptionPaged(this.searchText, this.pageId);
      this.fps = itensPaged.content;
      this.$refs.nextPageTable.totalPages = itensPaged.totalPages;
      this.$refs.nextPageTable.totalElements = itensPaged.totalElements;
      this.$refs.nextPageTable.pageable.pageNumber =
        itensPaged.pageable.pageNumber;
    },
    async getFormasPagamento() {
      let itensPaged = await this.fp.getAllPaged(this.pageId);
      this.fps = itensPaged.content;
      this.$refs.nextPageTable.totalPages = itensPaged.totalPages;
      this.$refs.nextPageTable.totalElements = itensPaged.totalElements;
      this.$refs.nextPageTable.pageable.pageNumber =
        itensPaged.pageable.pageNumber;
    },
    async excluir() {
      this.modalExcluir = false
      console.log(this.idFp)
      if (this.idFp != undefined) {
        let res = await this.fp.excluir(this.idFp)
        if (res.status == 204) {
          this.$refs.toast.createToast(
            'Forma de pagamento excluída com sucesso!',
          )
          this.getFormasPagamento()
        } else {
          this.$refs.toast.createToast(
            'Ocorreu um erro ao excluir forma de pagamento!',
          )
        }
      }
    },
    alterar(id) {
      this.$router.push({ path: `/forms/forma-pagamento/cadastro/${id}` })
    },
    updatePageId(newValue) {
      this.pageId = newValue;
      this.getFormasPagamento();
    },
  },

  mounted() {
    this.getFormasPagamento()
  },
}
</script>
<style scoped>
#teste-align {
  text-align: right;
}
</style>
