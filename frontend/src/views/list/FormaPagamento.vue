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
                  <CTableHeaderCell scope="col">Excluir?</CTableHeaderCell>
                  <CTableHeaderCell scope="col">Alterar?</CTableHeaderCell>
                </CTableRow>
              </CTableHead>
              <CTableBody>
                <CTableRow v-for="fp in this.fps" :key="fp.id">
                  <CTableHeaderCell scope="row">{{ fp.id }}</CTableHeaderCell>
                  <CTableDataCell>{{ fp.description }}</CTableDataCell>
                  <CTableDataCell v-if="fp.isActive == true">
                    <CIcon icon="cil-check" size="xl" />
                  </CTableDataCell>
                  <CTableDataCell v-else-if="fp.isActive == false">
                    <CIcon icon="cil-x-circle" size="xl" />
                  </CTableDataCell>
                  <CTableDataCell>
                    <CIcon
                      icon="cil-trash"
                      size="xl"
                      @click="
                        () => {
                          modalExcluir = true
                          idFp = fp.id
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
          </div>
        </CCardBody>
      </CCard>
    </CCol>
  </CRow>
  <CModal
    :visible="modalExcluir"
    @close="
      () => {
        modalExcluir = false
      }
    "
  >
    <CModalHeader
      dismiss
      @close="
        () => {
          modalExcluir = false
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
            modalExcluir = false
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
import FormaPagamentoService from '@/Services/formaPagamentoService'
import Toast from '@/components/Toast.vue'
import { CForm } from '@coreui/vue'
export default {
  components: { Toast, CForm },
  name: 'FormaPagamento',
  data() {
    return {
      fp: new FormaPagamentoService(),
      fps: '',
      modalExcluir: false,
      idFp: '',
      searchText: '',
    }
  },
  methods: {
    async getByDescription() {
      this.fps = await this.fp.getByDescription(this.searchText)
      console.log(this.fps)
    },
    async getFormasPagamento() {
      this.fps = await this.fp.consultarFormasPagamento()
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
