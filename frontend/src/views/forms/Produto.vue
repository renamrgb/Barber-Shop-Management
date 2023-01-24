<template>
  <CRow>
    <CCol :xs="12">
      <CCard class="mb-4">
        <CCardHeader>
          <strong>Produtos</strong>
        </CCardHeader>
        <CCardBody>
          <CForm
            class="row g-3 needs-validation"
            novalidate
            :validated="validatedCustom"
          >
            <div class="mb-3">
              <CFormLabel for="descricao">* Título</CFormLabel>
              <CFormInput
                id="titulo"
                type="text"
                placeholder="Creme de barbear..."
                v-model.lazy="titulo"
                required
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
            <div class="row mb-2">
              <div class="col">
                <CFormLabel for="descricao">* Preço</CFormLabel>
                <CInputGroup class="mb-3">
                  <CInputGroupText>R$</CInputGroupText>
                  <CFormInput
                    id="valor"                    
                    placeholder="00,00"
                    v-model.lazy="preco"
                    min="0"
                    required
                  />
                </CInputGroup>
              </div>

              <div class="col">
                <CFormLabel for="descricao">* Qtd em estoque</CFormLabel>
                <CFormInput
                  id="quantidade"
                  type="number"
                  placeholder="0"
                  v-model.lazy="quantidade"
                  min="0"
                  required
                />
              </div>
            </div>
            <div class="mb-1">              
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
              <a href="/#/forms/produto" class="btn btn-danger">Cancelar</a>
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
      preco: '',
      marca: '',
      quantidade: '',
      ativo: false,
      service: new Service(),
      validatedCustom: null,
    }
  },
  methods: {
    async salvar(event) {
      const form = event.currentTarget
      if (form.checkValidity() === false) {
        event.preventDefault()
        event.stopPropagation()
      }
      this.validatedCustom = true
      let res = undefined
      this.valor = this.preco.replace(',', '.');
      let dados = {
        title: this.titulo,
        brand: this.marca,
        quantity: this.quantidade,
        price: parseFloat(this.preco),
        isActive: this.ativo,
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
          this.$refs.toast.createToast(`${element.message} `)
        })
      }
    },
    async consultarUm() {
      if (this.id != undefined) {
        let item = await this.service.buscarUm(this.id)
        this.titulo = item.data.title
        this.valor = item.data.price
        this.quantidade = item.data.quantity
        this.marca = item.data.brand
        this.ativo = item.data.isActive
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
