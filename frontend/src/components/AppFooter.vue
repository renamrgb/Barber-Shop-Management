<template>
  <CFooter>
    <div>      
      <span class="ms-1"
        >&copy; {{ new Date().getFullYear() }} RpxLabs.</span
      >
    </div>
    <div class="ms-auto">
      <span class="me-1" target="_blank">Feito por</span>
      <a href="https://github.com/rafael8paulo">Rafael Oliveira</a>
    </div>
  </CFooter>
</template>

<script>
export default {
  name: 'AppFooter',
}
</script>
