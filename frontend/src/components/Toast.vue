<template>
  <CToaster placement="top-end">
    <CToast v-for="(toast, index) in toasts" :key="index">
      <CToastBody>
        {{ toast.content }}
      </CToastBody>
    </CToast>
  </CToaster>
</template>
<script>
export default {
  data() {
    return {
      toasts: [],
    };
  },
  methods: {
    createToast(mensagem) {
      this.toasts.push({
        content: mensagem,
      });
    },
  },
};
</script>
