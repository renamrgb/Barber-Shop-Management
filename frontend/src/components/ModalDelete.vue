<template>
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
      <CButton color="primary" @click="confirm">Confirmar</CButton>
    </CModalFooter>
  </CModal>
</template>

<script>
export default {
  name: "ModalDelete",
  props: {
    modalExcluir: {
      type: Boolean,
      required: true,
    },
  },
  data() {
    return {};
  },
  methods: {
    confirm() {
      this.$emit("confirmeDelete");
    },
  },
};
</script>

<style></style>
