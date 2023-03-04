<template>
  <CRow>
    <CCol :xs="12">
      <CCard class="mb-4">
        <CCardHeader>
          <CRow class="align-items-start">
            <CCol> <h3>Produtos</h3> </CCol>
            <CCol id="teste-align">
              <router-link to="/forms/produto/cadastro">
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
                    @click="getByTile"
                  >
                    <CIcon icon="cil-magnifying-glass" size="x" />
                  </button>
                </span>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-md-12 mx-auto">
              <br>
              <CFormSwitch
                id="formSwitchCheckDefault"
                label="Apenas ativos"
                v-model="findAssets"              
              />
            </div>
          </div>
          <div class="bdr">
            <CTable responsive="xl">
              <CTableHead color="dark">
                <CTableRow>
                  <CTableHeaderCell scope="col">#</CTableHeaderCell>
                  <CTableHeaderCell scope="col">Título</CTableHeaderCell>
                  <CTableHeaderCell scope="col">Valor</CTableHeaderCell>
                  <CTableHeaderCell scope="col">Ativo</CTableHeaderCell>
                  <CTableHeaderCell scope="col">Excluir?</CTableHeaderCell>
                  <CTableHeaderCell scope="col">Alterar?</CTableHeaderCell>
                </CTableRow>
              </CTableHead>
              <CTableBody>
                <CTableRow v-for="item in this.itens" :key="item.id">
                  <CTableHeaderCell scope="row">{{ item.id }}</CTableHeaderCell>
                  <CTableDataCell>{{ item.title }}</CTableDataCell>
                  <CTableDataCell>R$ {{ item.price }}</CTableDataCell>
                  <CTableDataCell v-if="item.isActive == true">
                    <CIcon icon="cil-check" size="xl" />
                  </CTableDataCell>
                  <CTableDataCell v-else-if="item.isActive == false">
                    <CIcon icon="cil-x-circle" size="xl" />
                  </CTableDataCell>
                  <CTableDataCell>
                    <CIcon
                      icon="cil-trash"
                      size="xl"
                      @click="
                        () => {
                          modalExcluir = true;
                          idItem = item.id;
                        }
                      "
                    />
                  </CTableDataCell>
                  <CTableDataCell>
                    <CIcon
                      icon="cil-pencil"
                      size="xl"
                      @click="this.alterar(item.id)"
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
      <CButton color="primary" @click="this.excluir">Confirmar</CButton>
    </CModalFooter>
  </CModal>
  <toast ref="toast" />
</template>

<script>
import Service from "@/Services/produtoService.js";
import Toast from "@/components/Toast.vue";
import NextPageTable from "@/components/NextPageTable.vue";
export default {
  components: { Toast, NextPageTable },
  name: "Produto",
  data() {
    return {
      service: new Service(),
      itens: "",
      modalExcluir: false,
      idItem: "",
      searchText: "",
      pageId: 0,
      findAssets: false
    };
  },
  methods: {
    async getByTile() {      
      let itensPaged = await this.service.getByTitlePaged(this.searchText, this.pageId, this.findAssets);
      this.itens = itensPaged.content;      
      this.$refs.nextPageTable.totalPages = itensPaged.totalPages;
      this.$refs.nextPageTable.totalElements = itensPaged.totalElements;
      this.$refs.nextPageTable.pageable.pageNumber = itensPaged.pageable.pageNumber;
    },
    async consultaTodos() {
      let itensPaged = await this.service.getAllPaged(this.pageId, this.findAssets);
      this.itens = itensPaged.content;
      this.$refs.nextPageTable.totalPages = itensPaged.totalPages;
      this.$refs.nextPageTable.totalElements = itensPaged.totalElements;
      this.$refs.nextPageTable.pageable.pageNumber =
        itensPaged.pageable.pageNumber;
    },
    async excluir() {
      this.modalExcluir = false;
      console.log(this.idItem);
      if (this.idItem != undefined) {
        let res = await this.service.excluir(this.idItem);
        if (res.status == 204) {
          this.$refs.toast.createToast("Excluído com sucesso!");
          this.consultaTodos();
        } else {
          this.$refs.toast.createToast("Ocorreu um erro ao excluir item!");
        }
      }
    },
    alterar(id) {
      this.$router.push({ path: `/forms/produto/cadastro/${id}` });
    },    
    updatePageId(newValue) {
      this.pageId = newValue;
      this.consultaTodos();
    },
  },
  computed: {},
  mounted() {
    this.consultaTodos();
  },
};
</script>
<style scoped>
#teste-align {
  text-align: right;
}
</style>
