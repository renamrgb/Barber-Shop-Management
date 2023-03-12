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
                      v-model="organization.name"
                    />
                    <!-- <div
                      v-if="v$.organization.name.$errors.length > 0"
                      class="invalid-input-form"
                    >
                      {{ v$.organization.name.$errors[0].$message }}
                    </div> -->
                  </div>
                </div>
                <div class="row mb-3">
                  <div class="col">
                    <CFormLabel for="nome">* CNPJ</CFormLabel>
                    <input
                      name="nome"
                      type="text"
                      class="form-control"
                      v-model="organization.cnpj"
                      v-mask="'##.###.###/####-##'"
                    />
                    <!-- <div
                      v-if="v$.organization.cnpj.$errors.length > 0"
                      class="invalid-input-form"
                    >
                      {{ v$.organization.cnpj.$errors[0].$message }}
                    </div> -->
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
          <div class="d-grid gap-2 d-md-flex justify-content-md-end">
            <CButton color="primary" class="me-md-2" @click="submitForm"
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
import Service from "@/Services/clienteService.js";
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
      organization: {
        name: "",
        cnpj: "",
      },
    };
  },
  validations() {
    return {
      organization: {
        name: {
          required: this.validationsMessage.requiredMessage,
          maxLength: this.validationsMessage.maxLengthMenssage(100),
        },
      },
    };
  },
  methods: {},
  mounted() {},
};
</script>
