<template>
  <CCol>
    <CCard class="mb-2">
      <CCardHeader>
        <strong>Bloqueio de horário</strong>
      </CCardHeader>
      <CCardBody>
        <CForm>
          <div class="row mb-3">
            <div class="col">
              <CFormLabel for="description">* Descrição</CFormLabel>
              <CFormInput
                name="description"
                type="text"
                placeholder=""
                v-model="form.description"
              />
            </div>
            <div class="col">
              <CFormLabel for="professional">* Profissional</CFormLabel>
              <v-select
                id="selectV"
                v-model="profissionalSelected"
                label="label"
                :options="optionsSelectProfessional"
              ></v-select>
            </div>
          </div>
          <div class="row mb-3">
            <div class="col">
              <CFormLabel for="startDate">* Data inicial</CFormLabel>
              <VueDatePicker
                v-model="form.startDate"
                locale="pt-BR"
              ></VueDatePicker>
            </div>
            <div class="col">
              <CFormLabel for="startDate">* Data final</CFormLabel>
              <VueDatePicker
                v-model="form.endDate"
                locale="pt-BR"
              ></VueDatePicker>
            </div>
          </div>
          <div class="d-grid gap-2 d-md-flex justify-content-md-end">
            <CButton color="primary" class="me-md-2" @click="formValidation"
              >Confirmar</CButton
            >
            <a href="/#/schedule/block/get" class="btn btn-danger">Cancelar</a>
          </div>
        </CForm>
      </CCardBody>
    </CCard>
  </CCol>
  <toast ref="toast" />
</template>
<script>
import Toast from "@/components/Toast.vue";
import ProfissionalService from "@/Services/profissionalService";
import BlockedTimeService from "@/Services/blockedTimeService";
import VueDatePicker from "@vuepic/vue-datepicker";
import "@vuepic/vue-datepicker/dist/main.css";
export default {
  components: { Toast, VueDatePicker },
  data() {
    return {
      id: this.$route.params.id,
      form: {
        description: "",
        professional: {
          id: "",
        },
        startDate: "",
        endDate: "",
      },
      service: new BlockedTimeService(),
      pService: new ProfissionalService(),
      optionsSelectProfessional: [],
      profissionalSelected: "",
    };
  },
  watch: {
    profissionalSelected(newValue) {
      this.form.professional.id = this.profissionalSelected.value;
    },
  },
  methods: {
    formValidation() {
      let valid = true;
      if (this.form.description == "") {
        valid = false;
        this.$refs.toast.createToastDanger("A descrição é obrigatória");
      }
      if (
        this.profissionalSelected == "" ||
        this.profissionalSelected == null
      ) {
        valid = false;
        this.$refs.toast.createToastDanger("O profissional é obrigatório");
      }
      if (this.form.startDate == "" || this.form.startDate == null) {
        valid = false;
        this.$refs.toast.createToastDanger("A data inicial é obrigatória");
      }
      if (this.form.endDate == "" || this.form.endDate == null) {
        valid = false;
        this.$refs.toast.createToastDanger("A data final é obrigatória");
      }
      if (valid) this.insertOrUpdate();
    },
    async insertOrUpdate() {
      let res = undefined;
      if (this.id != undefined) {
        res = await this.service.alterar(this.id, this.form);
      } else {
        res = await this.service.cadastrar(this.form);
      }
      if (res.status == 201) {
        this.$refs.toast.createToast("Bloqueio registrado com sucesso");
        this.$router.push(`/schedule/block/get`);
      } else if (res.status == 200) {
        this.$refs.toast.createToast("Bloqueio alterado com sucesso");
      } else if (res.status == 400) {
        this.$refs.toast.createToastDanger(res.message);
      } else
        this.$refs.toast.createToastDanger(
          "Ocorreu um erro ao realizar a opção"
        );
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
    async getById() {
      this.form = await this.service.getById(this.id);
      this.profissionalSelected = {
        value: this.form.professional.id,
        label: this.form.professional.name,
      };
    },
  },
  mounted() {
    if (this.id != undefined) {
      this.getById();
    }
    this.carregarOptionsProfessional();
  },
};
</script>
