<template>
  <CRow>
    <CCol :xs="12">
      <CCard class="mb-4">
        <CCardHeader>
          <strong>Registrar Agendamento</strong>
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
              />
            </div>
            <div class="col">
              <CFormLabel for="nome">* Profissional</CFormLabel>
              <v-select
                id="selectV"
                v-model="profissionalSelected"
                label="label"
                :options="optionsSelectProfessional"
              ></v-select>
            </div>
          </div>
          <div class="row mb-3">
            <div class="col d-grid gap-2">
              <CButton color="secondary" @click="searchValidation()"
                >Buscar</CButton
              >
            </div>
          </div>
          <div v-if="availableTimes.length > 0">
            <div class="row mb-3">
              <div class="col">
                <CFormLabel for="nome">* Horários disponíveis</CFormLabel>
                <CFormSelect
                  :options="availableTimes"
                  :searchable="true"
                  v-model="startTime"
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
              <div class="col"><span style="font-weight: bold;">Tempo de atendimento:</span> {{ timeService }}</div>
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
          </div>
          <div class="d-grid gap-2 d-md-flex justify-content-md-end">
            <CButton
              v-if="availableTimes.length > 0"
              color="primary"
              class="me-md-2"
              @click="formValidation"
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
import ProfissionalService from "@/Services/profissionalService";
import ProcedimentoService from "@/Services/procedimentoService";
import Multiselect from "@vueform/multiselect/src/Multiselect";
import ClienteService from "@/Services/clienteService";
import ScheduleService from "@/Services/scheduleService";
import DateNow from "@/util/dateNow.js";
export default {
  name: "Registrar Atendiemtno",
  components: { Toast, Multiselect },
  data() {
    return {
      dateNow: new DateNow(),
      id: this.$route.params.id,
      date: "",
      optionsSelectProfessional: [],
      optionsSelectProcedures: [],
      pService: new ProfissionalService(),
      prService: new ProcedimentoService(),
      scheduleService: new ScheduleService(),
      cServeice: new ClienteService(),
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
      },
      profissionalSelected: {
        value: "",
      },
      customerSelected: {
        value: "",
      },
      proceduresSelected: [],
      optionsSelectCustomer: [],
      availableTimes: [],
      startTime: "",
    };
  },
  computed: {
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
  },
  methods: {
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
      const RES = await this.pService.buscarUm(id);
      RES.procedures.forEach((element) => {
        this.optionsSelectProcedures.push({
          value: element.id,
          description: element.description,
          duration: element.duration,
        });
      });
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
      let res = await this.scheduleService.save(
        this.form,
        this.date,
        this.startTime
      );
      if (res.status == 201)
        this.$refs.toast.createToast("Agendamento registrado com sucesso!");
      else
        this.$refs.toast.createToastDanger(
          "Ocorreu um erro ao registar agendamento!"
        );
    },
    async getById(id) {
      this.form = await this.service.getById(id);
    },
  },
  mounted() {
    this.date = this.dateNow.dateNowISO();
    this.carregarOptionsProfessional();
    if (this.id != undefined) {
      this.disabladInpluts();
      this.getById(this.id);
    }
  },
};
</script>
<style></style>
<style src="@vueform/multiselect/themes/default.css"></style>
