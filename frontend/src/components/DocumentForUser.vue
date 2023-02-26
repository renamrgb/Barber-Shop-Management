<template>
  <div class="col">
    <CFormLabel for="typePerson">Tipo De Pessoa</CFormLabel>
    <select
      class="form-select"
      v-model="typePerson"
      @change="updatePlaceholderAndLabel()"
    >
      <option value="PHYSICAL_PERSON" selected>Pessoa Física</option>
      <option value="LEGAL_PERSON">Pessoa Jurícida</option>
    </select>
  </div>
  <div class="col" v-if="this.typePerson == 'PHYSICAL_PERSON'">
    <CFormLabel for="rg">RG</CFormLabel>
    <CFormInput name="rg" type="text" v-model="rg" />
  </div>
  <div class="col" v-if="typePerson != undefined">
    <CFormLabel for="document">{{ txtLabel }}</CFormLabel>
    <input
      name="document"
      type="text"
      class="form-control"
      v-model="document"
      v-mask="maskDocument"
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
      typePerson: "PHYSICAL_PERSON",
      rg: "",
      txtLabel: "",
      document: "",
      maskDocument: "###.###.###-##",
      validationTypePerson: new ValidationTypePerson(),
      resValidation: undefined,
    };
  },
  methods: {
    validationDocument() {
      // console.log(`TypePerson ${this.typePerson} | Lenght ${this.document.length}`);
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
    },
    updatePlaceholderAndLabel() {
      this.resValidation = "";
      this.document = "";
      if (this.typePerson == "PHYSICAL_PERSON") {
        this.txtLabel = "CPF";
        this.maskDocument = "###.###.###-##";
      } else {
        this.txtLabel = "CNPJ";
        this.maskDocument = "##.###.###/####-##";        
      }
    },
  },
  mounted() {
    if (this.$props.typePerson != 0) {
      this.typePerson = this.$props.typePersonProps;
      this.document = this.$props.documentProps;
      this.rg = this.$props.rgProps;
    }
    this.updatePlaceholderAndLabel();
  },
};
</script>

<style></style>
