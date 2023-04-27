<template>
  <CRow>
    <CCol :xs="12">
      <CCard class="mb-4">
        <CCardHeader>
          <strong>Clientes</strong>
        </CCardHeader>
        <CCardBody>
          <CForm>
            <div class="mb-3">
              <CFormLabel for="nome">* Nome</CFormLabel>
              <CFormInput
                name="nome"
                type="text"
                placeholder=""
                v-model="form.user.name"
              />
              <div
                v-if="v$.form.user.name.$errors.length > 0"
                class="invalid-input-form"
              >
                {{ v$.form.user.name.$errors[0].$message }}
              </div>
            </div>
            <div class="mb-3">
              <CFormLabel for="email">* E-mail</CFormLabel>
              <CFormInput
                name="email"
                type="email"
                placeholder="exemplo@gmail.com"
                v-model="form.user.email"
              />
              <div
                v-if="v$.form.user.email.$errors.length > 0"
                class="invalid-input-form"
              >
                {{ v$.form.user.email.$errors[0].$message }}
              </div>
            </div>
            <div class="row mb-3">
              <PasswordForUserVue
                :passwordProps="this.form.user.password"
                ref="passwordForUser"
              />
            </div>
            <div class="row mb-3">
              <DocumentForUser
                ref="documentForUser"
                :typePersonProps="this.form.user.typePerson"
                :documentProps="this.form.user.document"
                :rgProps="this.form.user.rg"
              />
            </div>
            <div class="mb-3">
              <CFormLabel for="telefone">* Telefone / Celular</CFormLabel>
              <input
                name="telefone"
                type="tel"
                class="form-control"
                v-mask="['(##) #####-####']"
                v-model="form.user.phoneNumber"
              />
              <div
                v-if="v$.form.user.phoneNumber.$errors.length > 0"
                class="invalid-input-form"
              >
                {{ v$.form.user.phoneNumber.$errors[0].$message }}
              </div>
            </div>
            <AddressForUser ref="addressForUser" />
            <div class="row mb-3" v-if="form.loyaltyCard != undefined">
              <div class="col">
                <LoyaltyCard :card="form.loyaltyCard" />
              </div>
            </div>
            <div class="mb-3">
              <br />
              <CFormSwitch
                name="formSwitchCheckDefault"
                label="Ativo"
                v-model="form.user.isActive"
              />
            </div>
            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
              <CButton color="primary" class="me-md-2" @click="submitForm"
                >Confirmar</CButton
              >
              <a href="/#/forms/cliente" class="btn btn-danger">Cancelar</a>
            </div>
          </CForm>
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
import DocumentForUser from "@/components/DocumentForUser.vue";
import AddressForUser from "@/components/AddressForUser.vue";
import PasswordForUserVue from "@/components/PasswordForUser.vue";
import LoyaltyCard from "@/components/loyaltyCard/LoyaltyCard.vue";
export default {
  components: {
    Toast,
    DocumentForUser,
    AddressForUser,
    PasswordForUserVue,
    LoyaltyCard,
  },
  name: "Cliente",
  data() {
    return {
      v$: useVuelidate(),
      validationsMessage: new ValidationsMessage(),
      id: this.$route.params.id,
      service: new Service(),
      form: {
        user: {
          name: "",
          email: "",
          password: "",
          phoneNumber: "",
          typePerson: "",
          document: "",
          rg: "",
          address: {
            zipCode: "",
            publicPlace: "",
            neighborhood: "",
            complement: "",
            city: "",
            state: "",
          },
          nivelAcesso: {
            authority: "ROLE_USER",
          },
          isActive: false,
        },
      },
    };
  },
  validations() {
    return {
      form: {
        user: {
          name: {
            required: this.validationsMessage.requiredMessage,
            maxLength: this.validationsMessage.maxLengthMenssage(100),
          },
          email: {
            required: this.validationsMessage.requiredMessage,
            maxLength: this.validationsMessage.maxLengthMenssage(100),
            email: this.validationsMessage.emailMessage,
          },
          phoneNumber: {
            required: this.validationsMessage.requiredMessage,
            maxLength: this.validationsMessage.maxLengthMenssage(15),
          },
        },
      },
    };
  },
  methods: {
    submitForm() {
      this.$refs.passwordForUser.setPasswordNotUpdate();
      this.v$.$validate();
      if (
        !this.v$.$error &&
        !this.$refs.passwordForUser.btnChangePassword &&
        this.$refs.passwordForUser.isValid()
      ) {
        this.salvar();
      } else if (
        !this.v$.$error &&
        this.$refs.passwordForUser.btnChangePassword
      ) {
        this.salvar();
      }
    },
    async salvar() {
      let res = undefined;
      this.form.user.document = this.$refs.documentForUser.document;
      this.form.user.rg = this.$refs.documentForUser.rg;
      this.form.user.typePerson = this.$refs.documentForUser.typePerson;
      this.form.user.address = this.$refs.addressForUser.address;
      this.form.user.password = this.$refs.passwordForUser.password;
      if (this.id == undefined) {
        res = await this.service.cadastrar(this.form);
      } else {
        this.form.password = "";
        res = await this.service.alterar(this.id, this.form);
      }
      if (res.status == 201) {
        this.$refs.toast.createToast("Cadastrado com sucesso!");
        this.$router.push("/forms/cliente");
      } else if (res.status == 200) {
        this.$refs.toast.createToast("Alterado com sucesso!");
      } else {
        let vetErros = res.response.data.fieldErrors;
        vetErros.forEach((element) => {
          this.$refs.toast.createToast(
            ` [${element.fieldName}] ${element.message} `
          );
        });
      }
    },
    async consultarUm() {
      if (this.id != undefined) {
        this.form.user = await this.service.buscarUm(this.id);
        this.form.loyaltyCard = this.form.user.loyaltyCard;
        this.$refs.documentForUser.document = this.form.user.document;
        this.$refs.documentForUser.rg = this.form.user.rg;
        this.$refs.documentForUser.typePerson = this.form.user.typePerson;

        this.$refs.addressForUser.address = this.form.user.address;
        this.$refs.addressForUser.change = true;

        this.$refs.passwordForUser.password = this.form.user.password;
      }
    },
    mostrarSenha() {
      if (this.mostraSenha == false) {
        document.getElementById("senha").type = "text";
        document.getElementById("confirmaSenha").type = "text";
      } else {
        document.getElementById("senha").type = "password";
        document.getElementById("confirmaSenha").type = "password";
      }
    },
    compararSenhas() {
      if (this.form.user.password == this.form.user.confirmPassword) {
        return true;
      } else {
        this.messagePassword = "As senhas não são iguais.";
        return false;
      }
    },
  },
  mounted() {
    if (this.id != undefined) {
      this.consultarUm();
      this.$refs.passwordForUser.btnChangePassword = true;
    }
  },
};
</script>
