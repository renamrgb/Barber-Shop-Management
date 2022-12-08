<template>
  <CRow>
    <CCol :xs="12">
      <CCard class="mb-4">
        <CCardHeader>
          <CRow class="align-items-start">
            <CCol> <h3>Cliente</h3> </CCol>
            <CCol id="teste-align">
              <router-link to="/forms/cliente/cadastro">
                <CIcon icon="cil-playlist-add" size="xxl" />
              </router-link>
            </CCol>
          </CRow>
        </CCardHeader>
        <CCardBody>
          <CTable>
            <CTableHead color="dark">
              <CTableRow>
                <CTableHeaderCell scope="col">#</CTableHeaderCell>
                <CTableHeaderCell scope="col">Nome</CTableHeaderCell>
                <CTableHeaderCell scope="col">Ativo</CTableHeaderCell>
                <CTableHeaderCell scope="col">Excluir?</CTableHeaderCell>
                <CTableHeaderCell scope="col">Alterar?</CTableHeaderCell>
              </CTableRow>
            </CTableHead>
            <CTableBody>
              <CTableRow v-for="item in this.itens" :key="item.id">
                <CTableHeaderCell scope="row">{{ item.id }}</CTableHeaderCell>
                <CTableDataCell>{{ item.nome }}</CTableDataCell>
                <CTableDataCell v-if="item.ativo == true">
                  <CIcon icon="cil-check" size="xl" />
                </CTableDataCell>
                <CTableDataCell v-else-if="item.ativo == false">
                  <CIcon icon="cil-x-circle" size="xl" />
                </CTableDataCell>
                <CTableDataCell>
                  <CIcon
                    icon="cil-trash"
                    size="xl"
                    @click="
                      () => {
                        modalExcluir = true
                        idItem = item.id
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
import Service from '@/Services/clienteService.js'
import Toast from '@/components/Toast.vue'
export default {
  components: { Toast },
  name: 'Cliente',
  data() {
    return {
      service: new Service(),
      itens: '',
      modalExcluir: false,
      idItem: '',
    }
  },
  methods: {
    async consultaTodos() {
      this.itens = await this.service.consultarTodos()
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
      this.$router.push({ path: `/forms/cliente/cadastro/${id}` })
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
