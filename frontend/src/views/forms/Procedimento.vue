<template>
  <CRow>
    <CCol :xs="12">
      <CCard class="mb-4">
        <CCardHeader>
          <strong>Procedimento</strong>
        </CCardHeader>
        <CCardBody>
          <CForm>
            <div class="mb-3">
              <CFormLabel for="descricao">Descrição</CFormLabel>
              <CFormInput
                id="descricao"
                type="text"
                placeholder="Corte de cabelo..."
                v-model.lazy="descricao"
              />
            </div>
            <div class="row g-4">
              <div class="col-auto">
                <CFormLabel for="descricao">Valor</CFormLabel>
                <CFormInput
                  id="valor"
                  type="number"
                  placeholder="R$ 00,00"
                  v-model.lazy="valor"
                />
              </div>
            </div>
            <div class="mb-3">
              <br />
              <CFormSwitch
                id="formSwitchCheckDefault"
                label="Ativo"
                v-model="ativo"
              />
            </div>
            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
              <CButton color="primary" class="me-md-2" @click="salvar"
                >Confirmar</CButton
              >
              <router-link to="/forms/procedimento"
                ><CButton color="danger">Cancelar</CButton></router-link
              >
            </div>
          </CForm>
        </CCardBody>
      </CCard>
    </CCol>
  </CRow>
  <toast ref="toast" />
</template>

<script>
import Service from '@/Services/procedimentoService.js'
import Toast from '@/components/Toast.vue'
export default {
  components: { Toast },
  name: 'Procedimento',
  data() {
    return {
      id: this.$route.params.id,
      descricao: '',
      valor: '',
      ativo: false,
      service: new Service(),
    }
  },
  methods: {
    async salvar() {
      let res = undefined
      let dados = {
        descricao: this.descricao,
        valor: this.valor,
        ativo: this.ativo,
      }
      if (this.id == undefined) {
        res = await this.service.cadastrar(dados)
      } else {
        res = await this.service.alterar(this.id, dados)
      }
      if (res.status == 201) {
        this.$refs.toast.createToast('Cadastrado com sucesso!')
        this.$router.push('/forms/procedimento')
      } else if (res.status == 200) {
        this.$refs.toast.createToast('Alterado com sucesso!')
      } else {
        if (res.status == 500) {
          this.$refs.toast.createToast(res.error);
        } else {
          let vetErros = res.response.data.fieldErrors

          vetErros.forEach((element) => {
            this.$refs.toast.createToast(
              ` [${element.fieldName}] ${element.message} `,
            )
          })
        }
      }
    },
    async consultarUm() {
      if (this.id != undefined) {
        let item = await this.service.buscarUm(this.id)
        this.descricao = item.data.descricao
        this.valor = item.data.valor
        this.ativo = item.data.ativo
      }
    },
  },
  mounted() {
    console.log(this.id)
    if (this.id != undefined) {
      this.consultarUm()
    }
  },
}
</script>
