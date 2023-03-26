<template>
  <CToaster placement="top-end">
    <CToast v-for="(toast, index) in toasts" :key="index" :color="color">
      <CToastBody>
        <b>{{ toast.content }}</b>
      </CToastBody>
    </CToast>
  </CToaster>
</template>
<script>
export default {
  data() {
    return {
      toasts: [],
      color: "info",
    };
  },
  methods: {
    createToast(mensagem) {
      this.color = "light";
      this.toasts.push({
        content: mensagem,
      });
    },
    createToastDanger(mensagem) {
      this.color = "danger";
      this.toasts.push({
        content: mensagem,
      });
    },
    createToastWarning(mensagem) {
      this.color = "warning";
      this.toasts.push({
        content: mensagem,
      });
    },
  },
};
</script>
