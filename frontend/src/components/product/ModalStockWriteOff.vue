<template>
  <CModal
    size="lg"
    :visible="visibleLiveDemo"
    @close="
      () => {
        visibleLiveDemo = false;
      }
    "
  >
    <CModalHeader>
      <CModalTitle> #{{ id }} Baixar produto em estoque</CModalTitle>
    </CModalHeader>
    <CModalBody>
      <div class="row mb-3">
        <div class="col">
          <CFormLabel for="nome">Título do produto</CFormLabel>
          <CFormInput
            name="Data da compra"
            type="text"
            v-model="form.product.title"
            :disabled="true"
          />
        </div>
        <div class="col-4">
          <CFormLabel for="nome">Preço</CFormLabel>
          <CInputGroup class="mb-1">
            <CInputGroupText>R$</CInputGroupText>
            <CFormInput v-model="form.product.price" :disabled="true" />
          </CInputGroup>
        </div>
        <div class="col-4">
          <CFormLabel for="quantidade">* Quantidade a ser baixada</CFormLabel>
          <CFormInput
            name="quantidade"
            type="number"
            v-model="form.qty"
            :disabled="false"
          />
        </div>
      </div>
      <div class="row mb-3">
        <div class="col">
          <CFormLabel for="registro">Registro</CFormLabel>
          <CFormInput
            name="registro"
            type="date"
            v-model="form.record"
            :disabled="true"
          />
        </div>
        <div class="col">
          <CFormLabel for="UserName">Usuário da baixa</CFormLabel>
          <CFormInput
            name="UserName"
            type="text"
            v-model="form.user.name"
            :disabled="true"
          />
        </div>
      </div>
      <div class="row mb-3">
        <div class="col">
          <CFormLabel for="UserName">* Motivo da baixa</CFormLabel>
          <CFormTextarea
            max="255"
            v-model="form.reason"
            rows="3"
            text="Deve ter até 255 caracteres"
          ></CFormTextarea>
        </div>
      </div>
    </CModalBody>
    <CModalFooter>
      <CButton
        color="secondary"
        @click="
          () => {
            visibleLiveDemo = false;
          }
        "
      >
        Fechar
      </CButton>
      <CButton color="primary">Confirmar</CButton>
    </CModalFooter>
  </CModal>
</template>
<script>
import ProdutoService from "@/Services/produtoService";
import DateNow from "@/util/dateNow";
export default {
  data() {
    return {
      service: new ProdutoService(),
      visibleLiveDemo: false,
      id: "",
      dateNow: new DateNow(),
      form: {
        product: {
          title: "",
          price: "",
        },
        qty: 0,
        record: "",
        reason: "",
        user: {
          id: localStorage.getItem("userId"),
          name: localStorage.getItem("nome_usuario"),
        },
      },
    };
  },
  methods: {
    async getById(id) {
      let product = await this.service.buscarUm(id);
      this.form.product = product;
    },
    setDateNow() {
      this.form.record = this.dateNow.dateNowISO();
    },
  },
  beforeUpdate() {
    if (this.id != "" && this.visibleLiveDemo) {
      this.getById(this.id);
      this.setDateNow();
    }
  },
};
</script>
