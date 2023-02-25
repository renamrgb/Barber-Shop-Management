<template>
  <CFormLabel for="cpf">{{ txtLabel }}</CFormLabel>
  <input
    type="text"
    class="form-control"
    :value="valor"
    :placeholder="placeholder"
    @input="$emit('input', $event.target.value)"
  />
  <!-- v-mask="['###.###.###-##', '##.###.###/####-##']" -->
  <!-- <div v-if="messageTypePersonValid != undefined" class="invalid-input-form">{{ messageTypePersonValid.message }}</div> -->
</template>

<script>
export default {
  name: "MeuInput",
  props: {
    valor: String,
    typePerson: Number,
    placeholder: String,
  },
  data() {
    return {
      txtLabel: "",
    };
  },
  methods: {
    updatePlaceholderAndLabel() {
      if (this.$props.typePerson == 1) {
        this.txtLabel = "CPF";
      } else {
        this.txtLabel = "CNPJ";
      }
    },
  },
  mounted() {
    this.updatePlaceholderAndLabel();
  },
};
</script>

<style></style>
