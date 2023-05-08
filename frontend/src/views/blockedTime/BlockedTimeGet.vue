<template>
  <CRow>
    <CCol :xs="12">
      <CCard class="mb-4">
        <CCardHeader>
          <CRow class="align-items-start">
            <CCol> <h3>Bloqueios de horários para atendimentos</h3> </CCol>
            <CCol id="teste-align">
              <router-link to="/schedule/block/create">
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
                    @click="search"
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
                  <CTableHeaderCell scope="col">Descirção</CTableHeaderCell>
                  <CTableHeaderCell scope="col">Dt inicial</CTableHeaderCell>
                  <CTableHeaderCell scope="col">Dt final</CTableHeaderCell>
                  <CTableHeaderCell scope="col"></CTableHeaderCell>
                  <CTableHeaderCell scope="col"></CTableHeaderCell>
                </CTableRow>
              </CTableHead>
              <CTableBody>
                <CTableRow v-for="item in this.itens" :key="item.id">
                  <CTableHeaderCell scope="row">{{ item.id }}</CTableHeaderCell>
                  <CTableDataCell>{{ item.description }}</CTableDataCell>
                  <CTableDataCell>{{
                    formatDateBr.toDateBr(item.startDate)
                  }}</CTableDataCell>
                  <CTableDataCell>{{
                    formatDateBr.toDateBr(item.endDate)
                  }}</CTableDataCell>
                  <IconsTdTable
                    :isActiveProps="item.isActive"
                    :id-item-props="item.id"
                    :serviceProps="service"
                    @get-all="getAll"
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
import BlockedTimeService from "@/Services/blockedTimeService";
import Toast from "@/components/Toast.vue";
import NextPageTable from "@/components/NextPageTable.vue";
import IconsTdTable from "@/components/IconsTdTable.vue";
import FormatDateBr from "@/util/formatDateBr.js";
export default {
  components: { Toast, NextPageTable, IconsTdTable },
  name: "Cliente",
  data() {
    return {
      formatDateBr: new FormatDateBr(),
      service: new BlockedTimeService(),
      itens: "",
      modalExcluir: false,
      idItem: "",
      searchText: "",
      pageId: 0,
    };
  },
  methods: {
    async getAll() {
      let itensPaged = await this.service.getAll(this.pageId);
      this.itens = itensPaged.content;
      this.$refs.nextPageTable.totalPages = itensPaged.totalPages;
      this.$refs.nextPageTable.totalElements = itensPaged.totalElements;
      this.$refs.nextPageTable.pageable.pageNumber =
        itensPaged.pageable.pageNumber;
    },
    alterar(id) {
      this.$router.push({ path: `/schedule/block/get/${id}` });
    },
    async search() {
      let itensPaged = await this.service.search(
        this.pageId,
        this.searchText        
      );
      this.itens = itensPaged.content;
      this.$refs.nextPageTable.totalPages = itensPaged.totalPages;
      this.$refs.nextPageTable.totalElements = itensPaged.totalElements;
      this.$refs.nextPageTable.pageable.pageNumber =
        itensPaged.pageable.pageNumber;
    },
    updatePageId(newValue) {
      this.pageId = newValue;
      this.getAll();
    },
  },
  mounted() {
    this.getAll();
  },
};
</script>
<style scoped>
#teste-align {
  text-align: right;
}
</style>
