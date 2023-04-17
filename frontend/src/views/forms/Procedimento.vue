<template>
  <CRow>
    <CCol :xs="12">
      <CCard class="mb-4">
        <CCardHeader>
          <strong>Procedimentos</strong>
        </CCardHeader>
        <CCardBody>
          <CForm>
            <div class="mb-3">
              <CFormLabel for="description">* Descrição</CFormLabel>
              <CFormInput
                name="description"
                type="text"
                v-model.lazy="form.description"
                required
              />
              <div
                v-if="v$.form.description.$errors.length > 0"
                class="invalid-input-form"
              >
                {{ v$.form.description.$errors[0].$message }}
              </div>
            </div>
            <div class="row mb-3">
              <div class="col">
                <CFormLabel for="typeExpense"
                  >* Tipo De Procedimento</CFormLabel
                >
                <CFormSelect
                  v-model="form.procedureType.id"
                  :options="optionsSelect"
                  :searchable="true"
                >
                </CFormSelect>
              </div>
            </div>
            <div class="row">
              <div class="col">
                <CFormLabel for="price">* Preço</CFormLabel>
                <CInputGroup class="mb-1">
                  <CInputGroupText>R$</CInputGroupText>
                  <CFormInput
                    price="price"
                    v-model.lazy="form.price"
                    min="0"
                    required
                  />
                </CInputGroup>
                <div
                  v-if="v$.form.price.$errors.length > 0"
                  class="invalid-input-form"
                >
                  {{ v$.form.price.$errors[0].$message }}
                </div>
              </div>
              <div class="col">
                <CFormLabel for="price">* Tempo de duração</CFormLabel>
                <CFormSelect
                  :options="optionsDuration"
                  :searchable="true"
                  v-model="form.duration"
                ></CFormSelect>
                <div
                  v-if="v$.form.duration.$errors.length > 0"
                  class="invalid-input-form"
                >
                  {{ v$.form.duration.$errors[0].$message }}
                </div>
              </div>
            </div>
            <div class="mb-3">
              <br />
              <CFormSwitch
                id="formSwitchCheckDefault"
                label="Ativo"
                v-model="form.isActive"
              />
            </div>
            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
              <CButton color="primary" class="me-md-2" @click="submitForm"
                >Confirmar</CButton
              >
              <a href="/#/forms/procedimento" class="btn btn-danger"
                >Cancelar</a
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
import Service from "@/Services/procedimentoService.js";
import Toast from "@/components/Toast.vue";
import { useVuelidate } from "@vuelidate/core";
import ValidationsMessage from "@/util/ValidationsMessage.js";
import TypeProductProcedure from "@/Services/typeProductProcedureService.js";
export default {
  components: { Toast },
  name: "Procedimento",
  data() {
    return {
      v$: useVuelidate(),
      validationsMessage: new ValidationsMessage(),
      typeProductProcedureService: new TypeProductProcedure(),
      id: this.$route.params.id,
      service: new Service(),
      form: {
        description: "",
        price: "",
        duration: "",
        procedureType: {
          id: "",
        },
        isActive: false,
      },
      optionsSelect: ["Abra este menu de seleção"],
      optionsDuration: ["00:30:00", "01:00:00", "01:30:00", "02:00:00", "02:30:00", "03:00:00"],
    };
  },
  validations() {
    return {
      form: {
        description: {
          required: this.validationsMessage.requiredMessage,
          maxLength: this.validationsMessage.maxLengthMenssage(60),
        },
        price: {
          required: this.validationsMessage.requiredMessage,
          decimal: this.validationsMessage.decimalMessage,
          minValue: this.validationsMessage.minMenssage(0.1),
        },
        duration: {
          required: this.validationsMessage.requiredMessage,
        },
      },
    };
  },
  methods: {
    submitForm(event) {
      console.log(this.form.duration);
      this.v$.$validate();
      if (!this.v$.$error) {
        this.salvar();
      }
    },
    async salvar() {
      let res = await this.service.updateOrInsert(this.id, this.form);
      if (res.status == 201) {
        this.$refs.toast.createToast("Cadastrado com sucesso!");
        this.$router.push("/forms/procedimento");
      } else if (res.status == 200) {
        this.$refs.toast.createToast("Alterado com sucesso!");
      } else {
        if (res.status == 500) {
          this.$refs.toast.createToast(res.error);
        } else {
          console.log(res);
          let vetErros = res.response.data.fieldErrors;
          vetErros.forEach((element) => {
            this.$refs.toast.createToast(
              ` [${element.fieldName}] ${element.message} `
            );
          });
        }
      }
    },
    async carregarOptionsSelect() {
      let res = await this.typeProductProcedureService.consultarTodos();
      res.forEach((element) => {
        this.optionsSelect.push({
          value: element.id,
          label: element.description,
          generateInstallments: element.generateInstallments,
        });
      });
    },
    async consultarUm() {
      if (this.id != undefined) {
        this.form = await this.service.buscarUm(this.id);
      }
      console.log(this.form.duration);
    },
  },
  mounted() {
    this.carregarOptionsSelect();
    if (this.id != undefined) {
      this.consultarUm();
    }
  },
};
</script>
