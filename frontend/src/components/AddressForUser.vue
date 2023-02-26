<template>
  <div id="getAddres">
    <div class="row">
      <CFormLabel for="cep">CEP</CFormLabel>
    </div>
    <div class="row g-4 mb-3">
      <div class="col-md-2">
        <input
          name="cep"
          type="text"
          class="form-control"
          v-mask="'#####-###'"
          v-model="address.zipCode"
          @input="getAddressByCep()"
        />
      </div>      
    </div>
  </div>
  <div id="addres" v-if="this.consultedZipCode == true">
    <div class="row g-4 mb-3">
      <div class="col">
        <CFormLabel for="descricao">Logradouro</CFormLabel>
        <CFormInput
          name="quantidade"
          type="logradouro"
          placeholder="Rua..."
          v-model="address.publicPlace"
        />
      </div>
    </div>
    <div class="row g-4 mb-3">
      <div class="col">
        <CFormLabel for="cep">Bairro</CFormLabel>
        <CFormInput
          name="bairro"
          type="text"
          placeholder="Centro"
          v-model="address.neighborhood"
        />
      </div>
      <div class="col">
        <CFormLabel for="complemento">Complemento</CFormLabel>
        <CFormInput
          name="complemento"
          type="complemento"
          placeholder=""
          v-model="address.complement"
        />
      </div>
      <div class="col">
        <CFormLabel for="cidade">Cidade</CFormLabel>
        <CFormInput name="cidade" v-model="address.city" disabled />
      </div>
      <div class="col-md-2">
        <CFormLabel for="uf">UF</CFormLabel>
        <input
          name="uf"
          class="form-control"
          v-model="address.state"
          max="2"
          disabled
        />
      </div>
    </div>
  </div>
</template>

<script>
import AddressService from "@/Services/addressService.js";
export default {
  name: "AddressForUser",
  props: {
    addressProps: Object,
  },
  data() {
    return {
      addressService: new AddressService(),
      address: {
        zipCode: "",
        publicPlace: "",
        neighborhood: "",
        complement: "",
        city: "",
        state: "",
      },
    };
  },
  methods: {
    async getAddressByCep() {      
      if (this.address.zipCode.length == 9) {
        this.address = await this.addressService.getAddressByCep(
          this.address.zipCode
        );
        if (this.address.zipCode != undefined) {
          this.consultedZipCode = true;
        }
      }
    },
  },
  mounted() {},
};
</script>

<style></style>
