<template>
  <CButton color="primary" @click="createToast">Send a toast</CButton>
  <CToaster placement="top-end">
    <CToast v-for="(toast, index) in toasts" :key="index">
      <CToastHeader closeButton>
        <span class="me-auto fw-bold">{{ toast.title }}</span>
        <small>7 min ago</small>
      </CToastHeader>
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
    }
  },
  methods: {
    createToast() {
      this.toasts.push({
        title: 'new toast',
        content: 'Lorem ipsum dolor cet emit',
      })
    },
  },
}
</script>
