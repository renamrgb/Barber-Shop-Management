<template>
  <div class="col">
    <CFormLabel for="typePerson">Tipo De Pessoa</CFormLabel>
    <select class="form-select" v-model="typePerson">
      <option value="PHYSICAL_PERSON" selected>Pessoa Física</option>
      <option value="LEGAL_PERSON">Pessoa Jurícida</option>
    </select>
  </div>
  <div class="col" v-if="typePerson != undefined">
    <CFormLabel for="rg">{{ setTextRg }}</CFormLabel>
    <input
      name="rg"
      class="form-control"
      type="text"
      v-model="rg"
      v-mask="setMaskRgInscricao"
    />
  </div>
  <div class="col" v-if="typePerson != undefined">
    <CFormLabel for="document">{{ setTextDocument }}</CFormLabel>
    <input
      name="document"
      type="text"
      class="form-control"
      v-model="document"
      v-mask="setMaskDocument"
      @input="validationDocument()"
    />
    <div v-if="resValidation != undefined" class="warning-input-form">
      {{ resValidation.message }}
    </div>
  </div>
</template>

<script>
import ValidationTypePerson from "@/util/validationTypePerson";
export default {
  name: "DocumentForUser",
  props: {
    documentProps: String,
    typePersonProps: String,
    rgProps: String,
  },
  data() {
    return {
      typePerson: "",
      rg: "",
      txtLabel: "",
      document: "",
      validationTypePerson: new ValidationTypePerson(),
      resValidation: undefined,
      flagUpdate: false,
    };
  },
  computed: {
    setTextRg() {
      if (this.typePerson == "PHYSICAL_PERSON") return "RG";
      else return "Inscrição Estadual";
    },
    setTextDocument() {
      if (this.typePerson == "PHYSICAL_PERSON") return "CPF";
      else return "CNPJ";
    },
    setMaskDocument() {
      if (this.typePerson == "PHYSICAL_PERSON") return "###.###.###-##";
      else return "##.###.###/####-##";
    },
    setMaskRgInscricao() {      
      if (this.typePerson == "PHYSICAL_PERSON") return "#########";
      else return "###.###.###.###";
    },
  },
  methods: {
    validationDocument() {
      if (this.typePerson == "PHYSICAL_PERSON" && this.document.length == 14) {
        this.resValidation = this.validationTypePerson.validation(
          this.typePerson,
          this.document
        );
      } else if (
        this.typePerson == "LEGAL_PERSON" &&
        this.document.length == 18
      ) {
        this.resValidation = this.validationTypePerson.validation(
          this.typePerson,
          this.document
        );
      }
      if (this.document == "") {
        this.resValidation = "";
      }
    },
  },
};
</script>

<style></style>
