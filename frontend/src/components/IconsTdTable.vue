<template>
  <td>
    <CIcon icon="cil-check" size="xl" v-if="isActive == true" />
    <CIcon icon="cil-x-circle" size="xl" v-else-if="isActive == false" />
  </td>
  <td>
    <CIcon
      icon="cil-trash"
      size="xl"
      @click="
        () => {
          modalExcluir = true;
          idItem = id;
        }
      "
    />
    <CIcon icon="cil-pencil" size="xl" @click="this.alterar(id)" />    
  </td>
  <CModal
    :visible="modalExcluir"
    @close="
      () => {
        modalExcluir = false;
      }
    "
  >
    <CModalHeader
      dismiss
      @close="
        () => {
          modalExcluir = false;
        }
      "
    >
      <CModalTitle>Deseja excluir esse item?</CModalTitle>
    </CModalHeader>
    <CModalBody
      >O item será excluído permanentemente do banco de dados.</CModalBody
    >
    <CModalFooter>
      <CButton
        color="secondary"
        @click="
          () => {
            modalExcluir = false;
          }
        "
        >Cancelar</CButton
      >
      <CButton color="primary" @click="this.excluir">Confirmar</CButton>
    </CModalFooter>
  </CModal>
  <toast ref="toast" />
</template>

<script>
import Toast from "@/components/Toast.vue";
export default {
  components: { Toast },
  name: "IconsTdTable",
  props: {
    isActiveProps: Boolean,
    idItemProps: Number,
    serviceProps: Object,
  },
  data() {
    return {
      isActive: this.isActiveProps,
      modalExcluir: false,
      service: this.serviceProps,
    };
  },
  computed: {},
  methods: {
    async excluir() {
      this.modalExcluir = false;      
      if (this.idItemProps != undefined) {
        let res = await this.service.excluir(this.idItemProps);
        if (res.status == 204) {
          this.$refs.toast.createToast("Excluído com sucesso!");          
          this.$emit('get-all');
        } else {
          this.$refs.toast.createToast("Ocorreu um erro ao excluir item!");
        }
      }
    },
    alterar() {
      this.$emit('alterar-item', this.idItemProps);
    }
  },
};
</script>

<style></style>
