<template>
  <CRow>
    <CCol :xs="12">
      <CCard class="mb-4">
        <CCardHeader>
          <strong>Produto</strong>
        </CCardHeader>
        <CCardBody>
          <CForm>
            <div class="mb-3">
              <CFormLabel for="descricao">Título</CFormLabel>
              <CFormInput
                id="titulo"
                type="text"
                placeholder="Creme de barbear..."
                v-model.lazy="titulo"
              />
            </div>
            <div class="mb-3">
              <CFormLabel for="descricao">Marca</CFormLabel>
              <CFormInput
                id="Marca"
                type="text"
                placeholder=""
                v-model.lazy="marca"
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
              <div class="col-auto">
                <CFormLabel for="descricao">Quantidade em estoque</CFormLabel>
                <CFormInput
                  id="quantidade"
                  type="number"
                  placeholder="0"
                  v-model.lazy="quantidade"
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
              <router-link to="/forms/produto"
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
import Service from '@/Services/produtoService.js'
import Toast from '@/components/Toast.vue'
export default {
  components: { Toast },
  name: 'Produto',
  data() {
    return {
      id: this.$route.params.id,
      titulo: '',
      valor: '',
      marca: '',
      quantidade: '',
      ativo: false,
      service: new Service(),
    }
  },
  methods: {
    async salvar() {
      let res = undefined
      let dados = { 
        titulo: this.titulo, 
        marca: this.marca, 
        quantidade: this.quantidade, 
        valor: this.valor, 
        ativo: this.ativo 
      }
      if (this.id == undefined) {
        res = await this.service.cadastrar(dados)
      } else {
        res = await this.service.alterar(this.id, dados)
      }      
      if (res.status == 201) {
        this.$refs.toast.createToast('Cadastrado com sucesso!')
        this.$router.push('/forms/produto')
      } else if (res.status == 200) {
        this.$refs.toast.createToast('Alterado com sucesso!')
      } else {
        let vetErros = res.response.data.fieldErrors

        vetErros.forEach((element) => {
          this.$refs.toast.createToast(
            ` [${element.fieldName}] ${element.message} `,
          )
        })
      }
    },
    async consultarUm() {
      if (this.id != undefined) {
        let item = await this.service.buscarUm(this.id)
        this.titulo = item.data.titulo
        this.valor = item.data.valor
        this.quantidade = item.data.quantidade
        this.marca = item.data.marca
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

