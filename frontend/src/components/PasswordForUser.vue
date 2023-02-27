<template>
  <div class="row mb-3" v-if="btnChangePassword != true">
    <div class="col">
      <CFormLabel for="senha">* Senha</CFormLabel>
      <CFormInput
        id="senha"
        name="senha"
        type="password"
        placeholder="*******"
        v-model="password"
      />
      <div v-if="v$.password.$errors.length > 0" class="invalid-input-form">
        {{ v$.password.$errors[0].$message }}
      </div>
      <CFormSwitch
        name="mostraSenha"
        label="Mostrar Senha"
        v-model="mostraSenha"
        @change="mostrarSenha()"
      />
    </div>
    <div class="col">
      <CFormLabel for="confirmaSenha">* Confirmar senha</CFormLabel>
      <CFormInput
        id="confirmaSenha"
        name="confirmaSenha"
        type="password"
        placeholder="*******"
        v-model="confirmPassword"
        @input="comparePasswords()"
      />
      <div
        v-if="v$.confirmPassword.$errors.length > 0"
        class="invalid-input-form"
      >
        {{ v$.confirmPassword.$errors[0].$message }}
      </div>
    </div>
    <div class="invalid-input-form">
      {{ comparePasswords }}
    </div>
  </div>
  <div class="mb-3" v-else>
    <button
      type="button"
      class="btn btn-light"
      @click="
        () => {
          btnChangePassword = false;
        }
      "
    >
      Alterar senha
    </button>
  </div>
</template>

<script>
import { useVuelidate } from "@vuelidate/core";
import ValidationsMessage from "@/util/ValidationsMessage.js";
export default {
  name: "PasswordForUser",
  props: {},
  data() {
    return {
      v$: useVuelidate(),
      validationsMessage: new ValidationsMessage(),
      password: "",
      confirmPassword: "",
      mostraSenha: false,
      messagePassword: "",
      btnChangePassword: false,
      massageError: undefined,
    };
  },
  validations() {
    return {
      password: {
        required: this.validationsMessage.requiredMessage,
        maxLength: this.validationsMessage.maxLengthMenssage(10),
      },
      confirmPassword: {
        required: this.validationsMessage.requiredMessage,
        maxLength: this.validationsMessage.maxLengthMenssage(10),
      },
    };
  },
  computed: {
    comparePasswords() {
      if (
        this.password != this.confirmPassword &&
        this.confirmPassword.length > 0
      ) {
        return "As senhas não são iguais.";
      } else if (this.password == this.confirmPassword) {
        return "";
      }
    },
  },
  methods: {
    mostrarSenha() {
      if (this.mostraSenha == false) {
        document.getElementById("senha").type = "text";
        document.getElementById("confirmaSenha").type = "text";
      } else {
        document.getElementById("senha").type = "password";
        document.getElementById("confirmaSenha").type = "password";
      }
    },
    isValid() {
      if (this.password == undefined) {
        this.password = "*******";
        this.confirmPassword = "*******";
      }

      this.v$.$validate();
      if (!this.v$.$error && this.comparePasswords == "") return true;
      return false;
    },
  },
};
</script>

<style></style>
