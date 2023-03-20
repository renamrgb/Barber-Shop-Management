<template>
  <CRow>
    <CCol :xs="12">
      <CCard class="mb-4">
        <CCardHeader>
          <strong>Lançar produtos em estoque</strong>
        </CCardHeader>
        <CCardBody>
          <div class="row mb-3">
            <div class="col">
              <CFormLabel for="nome">* Fornecedor</CFormLabel>
              <CFormInput name="nome" type="text" v-model="form.supplier" />
            </div>
          </div>
          <div class="row mb-3">
            <div class="col">
              <CFormLabel for="nome">* Produto</CFormLabel>
              <CFormSelect
                :options="optionsSelect"
                :searchable="true"
                v-model="product.product.id"
              ></CFormSelect>
            </div>
            <div class="col-2">
              <CFormLabel for="price">* Preço</CFormLabel>
              <CInputGroup class="mb-1">
                <CInputGroupText>R$</CInputGroupText>
                <CFormInput price="price" v-model="product.price" min="0" />
              </CInputGroup>
            </div>
            <div class="col-2">
              <CFormLabel for="price">* Quantidade</CFormLabel>
              <CInputGroup class="mb-1">
                <CFormInput
                  name="quantidade"
                  type="number"
                  v-model="product.quantity"
                />
              </CInputGroup>
            </div>
            <div class="col-1">
              <br />
              <CButton color="info" shape="rounded-pill" @click="addProduct"
                ><CIcon icon="cil-plus" size="x"
              /></CButton>
            </div>
          </div>
          <div class="bdr" v-if="form.products.length > 0">
            <CTable responsive="xl">
              <CTableHead class="table-dark">
                <CTableRow>
                  <CTableHeaderCell scope="col">#</CTableHeaderCell>
                  <CTableHeaderCell scope="col"
                    >Título do produto</CTableHeaderCell
                  >
                  <CTableHeaderCell scope="col">Preço</CTableHeaderCell>
                  <CTableHeaderCell scope="col">Quantidade</CTableHeaderCell>
                  <CTableHeaderCell scope="col"></CTableHeaderCell>
                </CTableRow>
              </CTableHead>
              <CTableBody>
                <CTableRow
                  v-for="(item, index) in this.form.products"
                  :key="index"
                >
                  <CTableHeaderCell scope="row">{{ item.id }}</CTableHeaderCell>
                  <CTableDataCell>{{ item.title }}</CTableDataCell>
                  <CTableDataCell>R$ {{ item.price }}</CTableDataCell>
                  <CTableDataCell>{{ item.quantity }}</CTableDataCell>
                  <CTableDataCell>
                    <CButton color="light" @click="removeProduct(index)">
                      <CIcon icon="cil-trash" />
                    </CButton>
                  </CTableDataCell>
                </CTableRow>
              </CTableBody>
            </CTable>
          </div>
          <div class="row mb-3">
            <div class="col">
              <CFormLabel for="nome">* Data da compra</CFormLabel>
              <CFormInput
                name="Data da compra"
                type="date"
                v-model="form.nfe.dateofPurchase"
              />
            </div>
            <div class="col">
              <CFormLabel for="nome">N° da nota fiscal</CFormLabel>
              <CFormInput
                name="Numero da nfe"
                type="number"
                v-model="form.nfe.numberNfe"
              />
            </div>
            <div class="col">
              <CFormLabel for="nome">CFOP</CFormLabel>
              <CFormInput name="Cfop" type="number" v-model="form.nfe.cfop" />
            </div>
            <div class="col">
              <CFormLabel for="nome">* Valor da nota fiscal</CFormLabel>
              <CInputGroup class="mb-1">
                <CInputGroupText>R$</CInputGroupText>
                <CFormInput price="price" v-model="form.nfe.valueNfe" />
              </CInputGroup>
            </div>
          </div>
          <div class="row mb-3">
            <div class="col">
              <CFormLabel for="nome">Chave</CFormLabel>
              <CFormInput name="chave" type="text" v-model="form.nfe.key" />
            </div>
          </div>
          <div class="d-grid gap-2 d-md-flex justify-content-md-end">
            <CButton color="primary" class="me-md-2" @click="formValidation"
              >Confirmar</CButton
            >
          </div>
        </CCardBody>
      </CCard>
    </CCol>
  </CRow>
  <toast ref="toast" />
</template>
<script>
import Toast from "@/components/Toast.vue";
import ProdutoService from "@/Services/produtoService";
import LancarProdutoService from "@/Services/LancarProdutoService";
export default {
  name: "Lançar Produtos",
  components: { Toast },
  data() {
    return {
      productService: new ProdutoService(),
      optionsSelect: ["Abra este menu de seleção"],
      service: new LancarProdutoService(),
      form: {
        supplier: "",
        products: [],
        nfe: {
          dateofPurchase: "",
          numberNfe: "",
          cfop: "",
          valueNfe: 0,
          key: "",
        },
      },
      product: {
        product: {
          id: "",
        },
        title: "",
        price: 0,
        quantity: 0,
      },
    };
  },
  methods: {
    removeProduct(index) {
      this.form.nfe.valueNfe =
        parseFloat(this.form.nfe.valueNfe) -
        (parseFloat(this.form.products[index].price) -
          this.form.products[index].quantity);
      this.form.products.pop(index);
    },
    addProduct() {
      let valid = true;
      let product = this.product;

      if (product.product.id == "") {
        this.$refs.toast.createToastDanger(
          "Selecionar o produto é obrigatório"
        );
        valid = false;
      } else {
        let criterio = function (elemento) {
          return elemento.value == product.product.id;
        };
        let p = this.optionsSelect.find(criterio);
        product.title = p.title;
      }
      if (product.price == "" || product.price <= 0) {
        this.$refs.toast.createToastDanger(
          "O preço do produto deve ser maior que 0"
        );
        valid = false;
      }
      if (product.quantity <= 0) {
        this.$refs.toast.createToastDanger("A quantidade deve ser maior que 0");
        valid = false;
      }
      if (valid) {
        this.form.nfe.valueNfe = this.addValueNfe(
          product.price,
          product.quantity
        );
        product.price = parseFloat(product.price);
        this.form.products.push(product);
        this.product = {
          product: {
            id: "",
          },
          title: "",
          price: 0,
          quantity: 0,
        };
      }
    },
    addValueNfe(value, qty) {
      let valueNfe = parseFloat(this.form.nfe.valueNfe);
      valueNfe += parseFloat(value) * qty;
      return valueNfe;
    },
    subtractValueNfe(value) {
      let valueNfe = parseFloat(this.form.valueNfe);
      valueNfe = valueNfe - parseFloat(value);
      return valueNfe;
    },
    async carregarOptionsSelect() {
      let res = await this.productService.consultarTodos();
      res.forEach((element) => {
        this.optionsSelect.push({
          value: element.id,
          label: `${element.title} - R$ ${element.price.toFixed(2)} Qtde: ${
            element.quantity
          }`,
          title: element.title,
          price: element.price,
        });
      });
    },
    formValidation() {
      let item = this.form;
      let valid = true;

      if (item.supplier == "") {
        valid = false;
        this.$refs.toast.createToastDanger("Fornecedor é obrigatório");
      }
      if (item.products.length == 0) {
        valid = false;
        this.$refs.toast.createToastDanger(
          "É obrigatório selecionar pelo menos um produto"
        );
      }
      if (item.dateofPurchase == "") {
        valid = false;
        this.$refs.toast.createToastDanger("A data da compra é obrigatória");
      }
      if (item.valueNfe <= 0) {
        valid = false;
        this.$refs.toast.createToastDanger("Valor da NFE deve ser maior que 0");
      }
      let duplicate = this.validateDuplicateProduct();
      if (duplicate.length > 0) {
        valid = false;
        this.$refs.toast.createToastDanger(
          "A lista de produtos não pode conter produtos duplicados, remova e tente novamente"
        );
      }
      if (valid) this.salvar();
    },
    async salvar() {
      let res = await this.service.save(this.form);
      if (res.status == 201)
        this.$refs.toast.createToast("Produtos(s) lançados com sucesso.");
      else
        this.$refs.toast.createToastDanger(
          "Ocorreu um erro ao lançar o produto"
        );
    },
    validateDuplicateProduct() {
      let products = this.form.products;
      let duplicados = [];
      for (var i = 0; i < products.length; i++) {
        for (var j = i + 1; j < products.length; j++) {
          if (
            products[i] === products[j] &&
            duplicados.indexOf(products[i]) === -1
          ) {
            duplicados.push(products[i]);
          }
        }
      }
      return duplicados;
    },
  },
  mounted() {
    this.carregarOptionsSelect();
  },
};
</script>
