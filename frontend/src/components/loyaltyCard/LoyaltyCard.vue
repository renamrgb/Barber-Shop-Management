<template>
  <CButton
    color="secondary"
    variant="outline"
    @click="
      () => {
        visibleLiveDemo = true;
      }
    "
    >Cartela de fidelidade</CButton
  >
  <CModal
    :visible="visibleLiveDemo"
    @close="
      () => {
        visibleLiveDemo = false;
      }
    "
  >
    <CModalHeader>
      <CModalTitle>Cartela de fidelidade</CModalTitle>
    </CModalHeader>
    <CModalBody>
      <div class="row mb-3">
        <div class="col">
          <CFormLabel>Qtde de pontos</CFormLabel>
          <CFormInput v-model="card.points" :disabled="true"></CFormInput>
        </div>
        <div class="col">
          <CFormLabel>Qtde usos</CFormLabel>
          <CFormInput v-model="card.quantityUsed" :disabled="true"></CFormInput>
        </div>
      </div>
      <div class="row mb-3" style="text-align: center">
        <div class="col">
          <CButton color="link" shape="rounded-0" @click="resetCard"
            >Zerar cartela</CButton
          >
        </div>
        <div class="col">
          <CButton color="link" shape="rounded-0" @click="addPoints"
            >Adicionar >></CButton
          >
        </div>
      </div>
      <div class="row mb-3">
        <div class="col" style="text-align: center">
          <CButton color="link" shape="rounded-0" @click="applyDiscount"
            >Aplicar desconto</CButton
          >
        </div>
      </div>
    </CModalBody>
    <CModalFooter>
      <CButton
        color="secondary"
        @click="
          () => {
            visibleLiveDemo = false;
          }
        "
      >
        Fechar
      </CButton>
    </CModalFooter>
  </CModal>
  <toast ref="toast" />
</template>
<script>
import { CFormInput, CFormLabel } from "@coreui/vue";
import ParameterService from "@/Services/parameterService";
import Toast from "@/components/Toast.vue";

export default {
  components: { CFormInput, CFormLabel, Toast },
  name: "LoyaltyCard",
  props: {
    card: {
      type: Object,
      required: true,
    },
  },
  data() {
    return {
      visibleLiveDemo: false,
      parameterService: new ParameterService(),
      maxPoints: 0,
    };
  },
  methods: {
    addPoints() {
      if (this.card.points < this.maxPoints)
        this.card.points = parseInt(this.card.points + 1);
      else
        this.$refs.toast.createToastDanger(
          `Os pontos não pode ser maior que ${this.maxPoints}`
        );
    },
    async getMaxPoints() {
      this.maxPoints = await this.parameterService.getMaxPointsCard();
    },
    resetCard() {
      this.card.points = 0;
    },
    async applyDiscount() {
      if (this.card.points == this.maxPoints) {
        const DATA = await this.parameterService.getParamerterKey(
          "DISCOUNT_LOYALYTY_CARD"
        );
        this.$emit("apply-discount", parseFloat(DATA.parameter_value));
        this.visibleLiveDemo = false;
      } else
        this.$refs.toast.createToastDanger(
          `Você só pode aplicar o desconto quando a quantidade de pontos for igual a ${this.maxPoints}`
        );
    },
  },
  mounted() {
    this.getMaxPoints();
  },
};
</script>

<style></style>
