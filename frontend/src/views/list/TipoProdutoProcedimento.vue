<template>
  <CRow>
    <CCol :xs="12">
      <CCard class="mb-4">
        <CCardHeader>
          <CRow class="align-items-start">
            <CCol> <h4>Tipo de produto/procedimento</h4> </CCol>
            <CCol id="teste-align">
              <router-link to="/forms/tipoProdutoProcedimento/cadastro">
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
              <CTableHead color="dark">
                <CTableRow>
                  <CTableHeaderCell scope="col">#</CTableHeaderCell>
                  <CTableHeaderCell scope="col">Descrição</CTableHeaderCell>
                  <CTableHeaderCell scope="col">Ativo</CTableHeaderCell>                  
                  <CTableHeaderCell scope="col"></CTableHeaderCell>
                </CTableRow>
              </CTableHead>
              <CTableBody>
                <CTableRow v-for="item in this.itens" :key="item.id">
                  <CTableHeaderCell scope="row">{{ item.id }}</CTableHeaderCell>
                  <CTableDataCell>{{ item.description }}</CTableDataCell>
                  <IconsTdTable
                    :isActiveProps="item.isActive"
                    :id-item-props="item.id"
                    :serviceProps="service"
                    @get-all="consultaTodos"
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
import Service from '@/Services/typeProductProcedureService.js'
import Toast from '@/components/Toast.vue'
import NextPageTable from "@/components/NextPageTable.vue";
import IconsTdTable from "@/components/IconsTdTable.vue";
export default {
  components: { Toast, NextPageTable, IconsTdTable },
  data() {
    return {
      service: new Service(),
      itens: '',
      modalExcluir: false,
      idItem: '',
      searchText: '',
      pageId: 0,
    }
  },
  methods: {
    async getByDescription() {      
      let itensPaged = await this.service.getByDescriptionPaged(this.searchText, this.pageId);
      this.itens = itensPaged.content;
      this.$refs.nextPageTable.totalPages = itensPaged.totalPages;
      this.$refs.nextPageTable.totalElements = itensPaged.totalElements;
      this.$refs.nextPageTable.pageable.pageNumber =
        itensPaged.pageable.pageNumber;
    },
    async consultaTodos() {
      let itensPaged = await this.service.getAllPaged(this.pageId);
      this.itens = itensPaged.content;
      this.$refs.nextPageTable.totalPages = itensPaged.totalPages;
      this.$refs.nextPageTable.totalElements = itensPaged.totalElements;
      this.$refs.nextPageTable.pageable.pageNumber =
        itensPaged.pageable.pageNumber;
    },
    async excluir() {
      this.modalExcluir = false
      console.log(this.idItem)
      if (this.idItem != undefined) {
        let res = await this.service.excluir(this.idItem)
        if (res.status == 204) {
          this.$refs.toast.createToast('Excluído com sucesso!')
          this.consultaTodos()
        } else {
          this.$refs.toast.createToast('Ocorreu um erro ao excluir item!')
        }
      }
    },
    alterar(id) {
      this.$router.push({
        path: `/forms/tipoProdutoProcedimento/cadastro/${id}`,
      })
    },
    updatePageId(newValue) {
      this.pageId = newValue;
      this.consultaTodos();
    },
  },

  mounted() {
    this.consultaTodos()
  },
}
</script>
<style scoped>
#teste-align {
  text-align: right;
}
</style>
