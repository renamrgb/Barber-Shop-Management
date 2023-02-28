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
                          modalExcluir = true
                          id = fp.id
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
      <CButton color="primary" @click="this.delete">Confirmar</CButton>
    </CModalFooter>
  </CModal>
  <toast ref="toast" />
</template>

<script>
import ExpenseService from '@/Services/expenseService'
import Toast from '@/components/Toast.vue'
import { CForm } from '@coreui/vue'
export default {
  components: { Toast, CForm },
  name: 'FormaPagamento',
  data() {
    return {
      service: new ExpenseService(),
      itens: '',
      modalExcluir: false,
      id: '',
      searchText: '',
    }
  },
  methods: {
    async delete() {
      let res = await this.service.delete(this.id)
      if (res.status == 400) {
        this.$refs.toast.createToast(res.message);
      }
      this.modalExcluir = false
      this.getExpenses()
    },
    async getByDescription() {
      this.itens = await this.service.consultarTodos()
      console.log(this.itens)
    },
    async getExpenses() {
      this.itens = await this.service.consultarTodos()
    },
    alterar(id) {
      this.$router.push({ path: `/expense/form/${id}` })
    },
  },
  mounted() {
    this.getExpenses()
  },
}
</script>
<style scoped>
#teste-align {
  text-align: right;
}
</style>
