<template>
  <CRow>
    <CCol :xs="12">
      <CCard class="mb-4">
        <CCardHeader>
          <CRow class="align-items-start">
            <CCol> <h3>Forma de pagamento</h3> </CCol>
            <CCol id="teste-align">
              <router-link to="/forms/forma-pagamento/cadastro">
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
                <CTableHeaderCell scope="col">Descrição</CTableHeaderCell>
                <CTableHeaderCell scope="col">Ativo</CTableHeaderCell>
                <CTableHeaderCell scope="col">Excluir?</CTableHeaderCell>
                <CTableHeaderCell scope="col">Alterar?</CTableHeaderCell>
              </CTableRow>
            </CTableHead>
            <CTableBody>
              <CTableRow v-for="fp in this.fps" :key="fp.id">
                <CTableHeaderCell scope="row">{{ fp.id }}</CTableHeaderCell>
                <CTableDataCell>{{ fp.descricao }}</CTableDataCell>
                <CTableDataCell v-if="fp.ativo == true">
                  <CIcon icon="cil-check" size="xl" />
                </CTableDataCell>
                <CTableDataCell v-else-if="fp.ativo == false">
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
export default {
  components: { Toast },
  name: 'FormaPagamento',
  data() {
    return {
      descricao: '',
      ativo: false,
      fp: new FormaPagamentoService(),
      fps: '',
      modalExcluir: false,
      idFp: '',
    }
  },
  methods: {
    async saveFormaPagamento() {
      let res = await this.fp.saveFormasPagamento({
        descricao: this.descricao,
        ativo: this.ativo,
      })
      if (res.status == 201) {
        this.$refs.toast.createToast(
          'Forma de pagamento cadastrada com sucesso!',
        )
      } else {
        let vetErros = res.response.data.fieldErrors

        vetErros.forEach((element) => {
          this.$refs.toast.createToast(
            ` [${element.fieldName}] ${element.message} `,
          )
        })
      }
    },
    async getFormasPagamento() {
      this.fps = await this.fp.consultarFormasPagamento()
      console.log(this.fps)
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
