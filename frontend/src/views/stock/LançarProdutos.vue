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
              <CFormInput
                name="nome"
                type="text"
                v-model="form.supplier"
                :disabled="disabled"
              />
            </div>
          </div>
          <div class="row mb-3" v-if="id == undefined">
            <div class="col">
              <CFormLabel for="nome">* Produto</CFormLabel>
              <v-select
                id="selectV"
                v-model="productSelected"
                label="label"
                :options="optionsSelect"
              ></v-select>
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
                    <CButton
                      color="light"
                      @click="removeProduct(index)"
                      v-if="id == undefined"
                    >
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
                :disabled="disabled"
              />
            </div>
            <div class="col">
              <CFormLabel for="nome">N° da nota fiscal</CFormLabel>
              <CFormInput
                name="Numero da nfe"
                type="number"
                v-model="form.nfe.numberNfe"
                :disabled="disabled"
              />
            </div>
            <div class="col">
              <CFormLabel for="nome">CFOP</CFormLabel>
              <CFormInput
                name="Cfop"
                type="number"
                v-model="form.nfe.cfop"
                :disabled="disabled"
              />
            </div>
            <div class="col">
              <CFormLabel for="nome">* Valor da nota fiscal</CFormLabel>
              <CInputGroup class="mb-1">
                <CInputGroupText>R$</CInputGroupText>
                <CFormInput
                  price="price"
                  v-model="form.nfe.valueNfe"
                  :disabled="disabled"
                />
              </CInputGroup>
            </div>
          </div>
          <div class="row mb-3">
            <div class="col">
              <CFormLabel for="nome">Chave</CFormLabel>
              <CFormInput
                name="chave"
                type="text"
                v-model="form.nfe.key"
                :disabled="disabled"
              />
            </div>
          </div>
          <div
            class="row mb-3"
            v-if="form.reversed != undefined"
            style="color: red;"
          >
            <div class="col" style="text-align: center">
              O Lançamento já foi estornado
            </div>
          </div>
          <div class="d-grid gap-2 d-md-flex justify-content-md-end">
            <CButton
              color="primary"
              class="me-md-2"
              @click="formValidation"
              v-if="id == un"
              >Confirmar</CButton
            >
            <a href="/#/stock/listLaunchProducts" class="btn btn-danger"
              >Cancelar</a
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
      id: this.$route.params.id,
      productService: new ProdutoService(),
      optionsSelect: [],
      service: new LancarProdutoService(),
      form: {
        reversed: undefined,
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
      disabled: false,
      productSelected: "",
    };
  },
  methods: {
    removeProduct(index) {
      const priceProduct = this.form.products[index].price;
      const qtyProduct = this.form.products[index].quantity;
      const valueSubtract = parseFloat(priceProduct) * qtyProduct;
      this.form.nfe.valueNfe =
        parseFloat(this.form.nfe.valueNfe) - parseFloat(valueSubtract);
      this.form.products.pop(index);
    },
    addProduct() {
      let valid = true;
      let product = {
        product: {
          id: this.productSelected.value,
        },
        title: this.product.title,
        price: this.product.price,
        quantity: this.product.quantity,
      };
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
      const duplicate = this.validateDuplicateProduct(product.product.id);
      if (duplicate == undefined) {
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
          this.productSelected = "";
        }
      } else {
        this.$refs.toast.createToastDanger(
          `O produto ${duplicate.title} já está na lista e não é permitido duplicidade!`
        );
      }
    },
    addValueNfe(value, qty) {
      let valueNfe = parseFloat(this.form.nfe.valueNfe);
      valueNfe += parseFloat(value) * qty;
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
    validateDuplicateProduct(id) {
      const duplicado = this.form.products.find(
        (product) => product.product.id == id
      );
      return duplicado;
    },
    disabladInpluts() {
      this.disabled = true;
    },
    async getById(id) {
      this.form = await this.service.getById(id);
    },
  },
  mounted() {
    this.carregarOptionsSelect();
    if (this.id != undefined) {
      this.disabladInpluts();
      this.getById(this.id);
    }
  },
};
</script>
<style></style>
