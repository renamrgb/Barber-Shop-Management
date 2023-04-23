<template>
  <CRow>
    <CCol :xs="12">
      <CCard class="mb-4">
        <CCardHeader>
          <div class="row">
            <div class="col">
              <strong>{{ titleCardHeader }}</strong>
            </div>
            <div class="col" style="text-align: right" v-if="id != undefined">
              <CIcon icon="cil-trash" @click="toCallModalDele()" />
            </div>
          </div>
        </CCardHeader>
        <CCardBody>
          <div class="row mb-3">
            <div class="col">
              <CFormLabel for="nome">* Data do agendamento</CFormLabel>
              <CFormInput
                name="Data da compra"
                type="date"
                v-model="date"
                @change="changeDate()"
                :disabled="disabled"
              />
            </div>
            <div class="col">
              <CFormLabel for="nome">* Profissional</CFormLabel>
              <v-select
                id="selectV"
                v-model="profissionalSelected"
                label="label"
                :options="optionsSelectProfessional"
                :input="handleSelectChange()"
                :disabled="disabledById"
              ></v-select>
            </div>
          </div>
          <div class="row mb-3" v-if="time == undefined && id == undefined">
            <div class="col d-grid gap-2">
              <CButton color="secondary" @click="searchValidation()"
                >Buscar</CButton
              >
            </div>
          </div>
          <div
            v-if="
              availableTimes.length > 0 || time != undefined || id != undefined
            "
          >
            <div class="row mb-3">
              <div class="col">
                <CFormLabel for="nome">{{ labelTimeAvaliabel }}</CFormLabel>
                <CFormSelect
                  :options="availableTimes"
                  :searchable="true"
                  v-model="startTime"
                  :disabled="disabled"
                >
                </CFormSelect>
              </div>
              <div class="col">
                <CFormLabel for="nome">* Procedimentos</CFormLabel>
                <Multiselect
                  v-model="proceduresSelected"
                  label="description"
                  trackBy="description"
                  :options="optionsSelectProcedures"
                  :searchable="true"
                  mode="tags"
                >
                </Multiselect>
              </div>
            </div>
            <div class="row">
              <div class="col"></div>
              <div class="col">
                <span style="font-weight: bold">Tempo de atendimento:</span>
                {{ timeService }}
              </div>
            </div>
            <div class="row">
              <div class="col">
                <CFormLabel for="nome">* Cliente</CFormLabel>
              </div>
              <div class="col"></div>
            </div>
            <div class="row mb-3">
              <div class="col">
                <v-select
                  id="selectV"
                  v-model="customerSelected"
                  label="label"
                  :options="optionsSelectCustomer"
                  :disabled="disabledById"
                ></v-select>
              </div>
              <div class="col-auto">
                <a
                  href="/#/forms/cliente/cadastro"
                  target="_blank"
                  class="btn btn-secondary"
                  >Criar Cliente</a
                >
              </div>
            </div>
            <div class="row mb-3" v-if="id != undefined">
              <div class="row">
                <div class="col">
                  <CFormLabel for="nome">* Produto</CFormLabel>
                </div>
                <div class="col-5">
                  <CFormLabel for="price">* Quantidade</CFormLabel>
                </div>
              </div>
              <div class="col">
                <v-select
                  id="selectV"
                  v-model="productSelected"
                  label="label"
                  :options="optionsSelectProduct"
                ></v-select>
              </div>
              <div class="col-4">
                <CInputGroup class="mb-1">
                  <CFormInput
                    name="quantidade"
                    type="number"
                    v-model="product.quantity"
                    :min="0"
                  />
                </CInputGroup>
              </div>
              <div class="col-1">
                <CButton color="info" shape="rounded-pill" @click="addProduct"
                  ><CIcon icon="cil-plus" size="x"
                /></CButton>
              </div>
              <div class="bdr" v-if="form.products.length > 0">
                <CTable responsive="xl">
                  <CTableHead class="table-dark">
                    <CTableRow>
                      <CTableHeaderCell scope="col">#</CTableHeaderCell>
                      <CTableHeaderCell scope="col"
                        >Título do produto</CTableHeaderCell
                      >
                      <CTableHeaderCell scope="col"
                        >Quantidade</CTableHeaderCell
                      >
                      <CTableHeaderCell scope="col">Preço</CTableHeaderCell>
                      <CTableHeaderCell scope="col"></CTableHeaderCell>
                    </CTableRow>
                  </CTableHead>
                  <CTableBody>
                    <CTableRow
                      v-for="(item, index) in form.products"
                      :key="index"
                    >
                      <CTableHeaderCell scope="row">{{
                        item.id
                      }}</CTableHeaderCell>
                      <CTableDataCell>{{ item.title }}</CTableDataCell>
                      <CTableDataCell>{{ item.quantity }}</CTableDataCell>
                      <CTableDataCell
                        >R$ {{ item.price.toFixed(2) }}</CTableDataCell
                      >
                      <CTableDataCell>
                        <CButton color="light" @click="removeProduct(item.id)">
                          <CIcon icon="cil-trash" />
                        </CButton>
                      </CTableDataCell>
                    </CTableRow>
                    <CTableRow>
                      <CTableDataCell :colspan="4">
                        <span style="font-weight: bold"
                          >Total a ser pago:
                        </span>
                      </CTableDataCell>
                      <CTableDataCell :colspan="1"
                        >R$ {{ calculateTotal }}</CTableDataCell
                      >
                    </CTableRow>
                  </CTableBody>
                </CTable>
              </div>
            </div>
          </div>
          <div class="d-grid gap-2 d-md-flex justify-content-md-end">
            <CButton
              v-if="availableTimes.length > 0"
              color="primary"
              class="me-md-2"
              @click="formValidation"
              >Confirmar</CButton
            >
            <a href="/#/schedule/get" class="btn btn-danger">Cancelar</a>
          </div>
        </CCardBody>
      </CCard>
    </CCol>
  </CRow>
  <toast ref="toast" />
  <ModalDelete
    v-if="modalExcluir != undefined"
    :modalExcluir="modalExcluir"
    @confirmeDelete="deleteItem"
  />
</template>
<script>
import Toast from "@/components/Toast.vue";
import ProfissionalService from "@/Services/profissionalService";
import ProcedimentoService from "@/Services/procedimentoService";
import Multiselect from "@vueform/multiselect/src/Multiselect";
import ClienteService from "@/Services/clienteService";
import ScheduleService from "@/Services/scheduleService";
import ProdutoService from "@/Services/produtoService";
import DateNow from "@/util/dateNow.js";
import ModalDelete from "@/components/ModalDelete.vue";
export default {
  name: "Registrar Atendiemtno",
  components: { Toast, Multiselect, ModalDelete },
  data() {
    return {
      modalExcluir: false,
      dateNow: new DateNow(),
      time: this.$route.params.time,
      id: this.$route.params.id,
      disabled: false,
      disabledById: false,
      date: "",
      pService: new ProfissionalService(),
      prService: new ProcedimentoService(),
      scheduleService: new ScheduleService(),
      cServeice: new ClienteService(),
      productService: new ProdutoService(),
      form: {
        client: {
          id: "",
        },
        startDate: "",
        endDate: "",
        professional: {
          id: "",
        },
        procedures: [],
        products: [],
      },
      profissionalSelected: {
        value: "",
      },
      profissionalSelectedAux: "",
      customerSelected: {
        value: "",
      },
      optionsSelectProfessional: [],
      optionsSelectProcedures: [],
      proceduresSelected: [],
      optionsSelectCustomer: [],
      optionsSelectProduct: [],
      availableTimes: [],
      startTime: "",
      product: {
        id: "",
        title: "",
        quantity: 0,
        price: 0,
      },
      productSelected: "",
    };
  },
  computed: {
    calculateTotal() {
      let total = parseFloat(0);
      for (const ELEMENT of this.form.products) {
        total += ELEMENT.price * ELEMENT.quantity;
      }
      return total.toFixed(2);
    },
    timeService() {
      let value = 0,
        procedure = undefined,
        vetTimes = [];
      for (const E of this.proceduresSelected) {
        procedure = this.optionsSelectProcedures.find((p) => p.id == E.id);
        vetTimes.push(procedure.duration);
      }
      return this.calcularHorarioTotal(vetTimes);
    },
    labelTimeAvaliabel() {
      if (this.id == undefined) {
        return "* Horários disponíveis";
      } else {
        return "* Horário do Atendimento";
      }
    },
    titleCardHeader() {
      if (this.id == undefined) {
        return "Registrar Agendamento";
      } else {
        return "Alterar Atendimento";
      }
    },
  },
  methods: {
    toCallModalDele() {
      this.modalExcluir = true;
    },
    async deleteItem() {
      const RESPONSE = await this.scheduleService.delete(this.id);
      console.log(RESPONSE.status);
      if (RESPONSE.status == 204) {
        this.$router.push(`/schedule/get`);
      } else {
        this.$refs.toast.createToastDanger("Ocorreu um erro ao excluir o item");
      }
    },
    removeProduct(id) {
      console.log(`id: ${id}`);
      let indice = this.form.products.findIndex(
        (elemento) => elemento.id === id
      );
      if (indice > -1) this.form.products.splice(indice, 1);
      console.log(JSON.stringify(this.form.products));
      console.log(indice);
    },
    validationAddProduct() {
      let valid = true;
      if (this.productSelected == "") {
        valid = false;
        this.$refs.toast.createToastDanger(
          "Você deve selecionar um produto para poder adicionar"
        );
      }
      if (this.product.quantity <= 0) {
        valid = false;
        this.$refs.toast.createToastDanger(
          "A quantidade precisa ser maior que 0"
        );
      }
      return valid;
    },
    addProduct() {
      if (this.validationAddProduct()) {
        let product = {
          id: this.productSelected.value,
          title: this.productSelected.title,
          price: this.productSelected.price,
          quantity: this.product.quantity,
        };
        this.form.products.push(product);
      }
    },
    async carregarProductSelect() {
      let res = await this.productService.consultarTodos();
      res.forEach((element) => {
        this.optionsSelectProduct.push({
          value: element.id,
          label: `${element.title} - R$ ${element.price.toFixed(2)} Qtde: ${
            element.quantity
          }`,
          title: element.title,
          price: element.price,
        });
      });
    },
    calcularHorarioTotal(vetor) {
      const totalSegundos = vetor.reduce((acumulador, valorAtual) => {
        const [horas, minutos, segundos] = valorAtual.split(":");
        return acumulador + horas * 3600 + minutos * 60 + segundos * 1; // multiplicar por 1 para converter em número
      }, 0);

      const horas = Math.floor(totalSegundos / 3600);
      const minutos = Math.floor((totalSegundos % 3600) / 60);
      const segundos = totalSegundos % 60;

      return `${horas.toString().padStart(2, "0")}:${minutos
        .toString()
        .padStart(2, "0")}:${segundos.toString().padStart(2, "0")}`;
    },
    changeDate() {
      if (this.form.professional.id != "") {
        const DIFFERENCE_IN_DAYS = this.dateNow.compareDates(this.date);
        if (DIFFERENCE_IN_DAYS > 0) {
          this.$refs.toast.createToastDanger(
            "A data do agendamento não pode ser maior que a data atual ou vazia!"
          );
          this.availableTimes = [];
        } else this.searchSchedules();
      }
    },
    async searchSchedules() {
      this.adjustmentSelects();
      const RES = await this.scheduleService.searchAvailable(
        this.date,
        this.form.professional.id
      );
      this.availableTimes = [];
      this.availableTimes.push("Selecione um horário");
      RES.forEach((item) => {
        this.availableTimes.push(item);
      });
      this.searchProceduresProfessional(this.form.professional.id);
      this.carregarOptionsCustomer();
    },
    async searchProceduresProfessional(id) {
      let res = await this.pService.buscarUm(id);
      const PROCEDURES = res.procedures;
      this.optionsSelectProcedures = [];
      for (const ELEMENT of PROCEDURES) {
        this.optionsSelectProcedures.push({
          value: ELEMENT.id,
          description: ELEMENT.description,
          duration: ELEMENT.duration,
        });
      }
    },
    adjustmentSelects() {
      this.form.professional.id = this.profissionalSelected.value;
      this.form.client.id = this.customerSelected.value;
      this.form.procedures = [];
      this.proceduresSelected.forEach((item) => {
        this.form.procedures.push({
          id: item,
        });
      });
    },
    async carregarOptionsProfessional() {
      let res = await this.pService.consultarTodos();
      res.forEach((element) => {
        this.optionsSelectProfessional.push({
          value: element.id,
          label: element.name,
        });
      });
    },
    async carregarOptionsCustomer() {
      const RES = await this.cServeice.consultarTodos();
      RES.forEach((element) => {
        this.optionsSelectCustomer.push({
          value: element.id,
          label: element.name,
        });
      });
    },
    searchValidation() {
      let item = this.form;
      let valid = true;
      this.adjustmentSelects();
      if (item.professional.id == "") {
        valid = false;
        this.$refs.toast.createToastDanger("Profissional é obrigatório");
      }
      if (this.date == "") {
        valid = false;
        this.$refs.toast.createToastDanger("Data é obrigatória");
      }
      const DIFFERENCE_IN_DAYS = this.dateNow.compareDates(this.date);
      if (DIFFERENCE_IN_DAYS > 0) {
        valid = false;
        this.$refs.toast.createToastDanger(
          "A data do agendamento não pode ser maior que a data atual ou vazia!"
        );
      }
      if (valid) this.searchSchedules();
    },
    formValidation() {
      let valid = true;
      this.adjustmentSelects();
      if (this.startTime == "") {
        valid = false;
        this.$refs.toast.createToastDanger(
          "Você deve selecionar um horário para agendamento"
        );
      }
      if (this.form.procedures.length == 0) {
        valid = false;
        this.$refs.toast.createToastDanger(
          "Você deve selecionar um ou mais procedimentos para o agendamento"
        );
      }
      if (this.form.client.id == "") {
        valid = false;
        this.$refs.toast.createToastDanger(
          "Você deve selecionar o cliente para o agendamento"
        );
      }
      if (valid) this.save();
    },
    async save() {
      let res = undefined;
      if (this.id == undefined)
        res = await this.scheduleService.save(
          this.form,
          this.date,
          this.startTime
        );
      else res = await this.scheduleService.update(this.id, this.form);

      if (res.status == 201) {
        this.$refs.toast.createToast("Atendiemnto registrado com sucesso!");
        this.$router.push(`/schedule/get`);
      } else if (res.status == 200)
        this.$refs.toast.createToast("Atendiemnto registrado com sucesso!");
      else
        this.$refs.toast.createToastDanger(
          "Ocorreu um erro ao registar agendamento!"
        );
    },
    screenviaFullCalendar() {
      let aux = this.time.split("T");
      this.date = aux[0];
      aux = aux[1].split("-");
      this.time = aux[0];
      this.availableTimes.push(this.time);
      this.startTime = this.time;
      this.carregarOptionsCustomer();
      this.carregarOptionsProfessional();
      this.disabled = true;
    },
    handleSelectChange() {
      if (
        this.time != undefined &&
        this.profissionalSelectedAux != this.profissionalSelected.value
      ) {
        if (this.profissionalSelected.value != "")
          this.searchProceduresProfessional(this.profissionalSelected.value);
        this.profissionalSelectedAux = this.profissionalSelected.value;
      }
    },
    async screenById(id) {
      this.form = await this.scheduleService.getById(id);
      this.profissionalSelected.value = this.form.professional.id;
      this.profissionalSelected.label = this.form.professional.name;
      await this.searchProceduresProfessional(this.form.professional.id);
      for (const ELEMENT of this.form.procedures) {
        this.proceduresSelected.push(ELEMENT.id);
      }
      let startDate = this.form.startDate;
      startDate = startDate.split("T");
      this.availableTimes.push(startDate[1]);
      this.startTime = startDate[1];
      this.date = startDate[0];
      this.customerSelected.value = this.form.client.id;
      this.customerSelected.label = this.form.client.name;
      this.disabled = true;
      this.disabledById = true;
      this.carregarProductSelect();
    },
  },
  mounted() {
    this.date = this.dateNow.dateNowISO();
    this.carregarOptionsProfessional();
    if (this.time != undefined) this.screenviaFullCalendar();
    if (this.id != undefined) this.screenById(this.id);
  },
};
</script>
<style></style>
<style src="@vueform/multiselect/themes/default.css"></style>
