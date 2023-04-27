<template>
  <CRow>
    <CCol :xs="12">
      <CCard class="mb-4">
        <CCardHeader>
          <strong>Parâmetros</strong>
        </CCardHeader>
        <CCardBody>
          <CCard class="mb-4">
            <CCardHeader>
              <strong>Organização</strong>
            </CCardHeader>
            <CCardBody>
              <CForm>
                <div class="row mb-3">
                  <div class="col">
                    <CFormLabel for="nome">* Razão Social</CFormLabel>
                    <CFormInput
                      name="nome"
                      type="text"
                      placeholder=""
                      v-model="parameter.organization.name"
                    />
                  </div>
                </div>
                <div class="row mb-3">
                  <div class="col">
                    <CFormLabel for="nome">* CNPJ</CFormLabel>
                    <input
                      name="nome"
                      type="text"
                      class="form-control"
                      v-model="parameter.organization.cnpj"
                      v-mask="'##.###.###/####-##'"
                    />
                  </div>
                </div>
                <CCard class="mb-4">
                  <CCardHeader>
                    <strong>Endereço</strong>
                  </CCardHeader>
                  <CCardBody>
                    <AddressForUser ref="addressForUser" />
                  </CCardBody>
                </CCard>
              </CForm>
            </CCardBody>
          </CCard>
          <CCard class="mb-4">
            <CCardHeader>
              <strong>Parametros</strong>
            </CCardHeader>
            <CCardBody>
              <div
                class="row mb-3"
                v-for="e in this.parameter.parameterValues"
                :key="e.id"
              >
                <div class="col">
                  <CFormLabel for="nome">* {{ e.parameter_name }}</CFormLabel>
                  <input
                    v-if="e.parameter_key != 'DISCOUNT_LOYALYTY_CARD'"
                    :name="e.parameter_key"
                    :type="type(e)"
                    class="form-control"
                    v-model="e.parameter_value"
                  />
                  <CInputGroup
                    class="mb-1"
                    v-if="e.parameter_key == 'DISCOUNT_LOYALYTY_CARD'"
                  >
                    <CInputGroupText>R$</CInputGroupText>
                    <CFormInput v-model.lazy="e.parameter_value"
                  /></CInputGroup>
                </div>
              </div>
            </CCardBody>
          </CCard>
          <div class="d-grid gap-2 d-md-flex justify-content-md-end">
            <CButton color="primary" class="me-md-2" @click="save"
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
import { useVuelidate } from "@vuelidate/core";
import ValidationsMessage from "@/util/ValidationsMessage.js";
import Service from "@/Services/parameterService.js";
import Toast from "@/components/Toast.vue";
import AddressForUser from "@/components/AddressForUser.vue";
export default {
  components: { Toast, AddressForUser },
  name: "Parameters",
  data() {
    return {
      v$: useVuelidate(),
      validationsMessage: new ValidationsMessage(),
      id: this.$route.params.id,
      service: new Service(),
      parameter: {
        organization: {
          name: "",
          cnpj: "",
        },
      },
    };
  },
  validations() {
    return {
      parameter: {
        organization: {
          name: {
            required: this.validationsMessage.requiredMessage,
            maxLength: this.validationsMessage.maxLengthMenssage(100),
          },
        },
      },
    };
  },
  methods: {
    type(item) {
      if (item.parameter_key == "QTY_LOYALYTY_CARD") return "number";
      else if (item.parameter_key == "DISCOUNT_LOYALYTY_CARD") return "text";
      return "time";
    },
    submitForm() {},
    async getParameter() {
      this.parameter = await this.service.getParamerter();
    },
    async save() {
      let res = await this.service.save(this.parameter);
      console.log(res);
      debugger;
    },
  },
  mounted() {
    this.getParameter();
  },
};
</script>
