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
      </div>
      <div class="row mb-3">
        <div class="col-4">
          <CFormLabel for="nome">Preço</CFormLabel>
          <CInputGroup class="mb-1">
            <CInputGroupText>R$</CInputGroupText>
            <CFormInput v-model="form.product.price" :disabled="true" />
          </CInputGroup>
        </div>
        <div class="col-4">
          <CFormLabel for="qtyProduct">* Qtde disponível</CFormLabel>
          <CFormInput
            name="qtyProduct"
            type="number"
            v-model="form.product.quantity"
            :disabled="true"
          />
        </div>
        <div class="col-4">
          <CFormLabel for="quantidade">* Qtde a ser baixada</CFormLabel>
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
      <CButton color="primary" @click="formValidation">Confirmar</CButton>
    </CModalFooter>
  </CModal>
  <toast ref="toast" />
</template>
<script>
import Toast from "@/components/Toast.vue";
import ProdutoService from "@/Services/produtoService";
import DateNow from "@/util/dateNow";
import StockWriteOffService from "@/Services/stockWriteOffService.js";
export default {
  components: { Toast },
  data() {
    return {
      service: new ProdutoService(),
      stockWriteOffService: new StockWriteOffService(),
      visibleLiveDemo: false,
      id: "",
      dateNow: new DateNow(),
      form: {
        product: {
          id: "",
          title: "",
          price: "",
          quantity: "",
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
    formValidation() {
      let valid = true;
      if (this.form.qty <= 0) {
        this.$refs.toast.createToastDanger("Quantidade deve ser maior que 0");
        valid = false;
      }
      if (this.form.reason == "") {
        this.$refs.toast.createToastDanger("Descrição é obrigatória");
        valid = false;
      }
      if (this.form.reason.length >= 255) {
        this.$refs.toast.createToastDanger(
          "O motivo deve conter até 255 caracteres"
        );
        valid = false;
      }
      if (valid) {
        this.save();
      }
    },
    async getById(id) {
      let product = await this.service.buscarUm(id);
      this.form.product = product;
    },
    setDateNow() {
      this.form.record = this.dateNow.dateNowISO();
    },
    async save() {
      this.form.product.id = this.id;
      let res = await this.stockWriteOffService.save(this.form);
      console.log(res.response);
      if (res.status == 201) {
        this.$refs.toast.createToast("Baixa registrada com sucesso");
        this.$router.push("/forms/produto");
        this.visibleLiveDemo = false;
        this.form.qty = 0;
        this.form.reason = "";
      } else {
        if (res.response.status == 400) {
          this.$refs.toast.createToastDanger(res.response.data.message);
        } else {
          this.$refs.toast.createToastDanger(
            "Ocorreu um erro ao realizar baixa"
          );
        }
      }
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
