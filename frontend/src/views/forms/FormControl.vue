<template>
  <CRow>
    <CCol :xs="12">
      <CCard class="mb-4">
        <CCardHeader>
          <strong>Vue Form Control</strong>
        </CCardHeader>
        <CCardBody>
          <CForm>
            <div class="mb-3">
              <CFormLabel for="exampleFormControlInput1"
                >Email address</CFormLabel
              >
              <CFormInput
                id="exampleFormControlInput1"
                type="email"
                placeholder="name@example.com"
              />
            </div>
            <div class="mb-3">
              <CFormLabel for="exampleFormControlTextarea1"
                >Example textarea</CFormLabel
              >
              <CFormTextarea
                id="exampleFormControlTextarea1"
                rows="3"
              ></CFormTextarea>
            </div>
          </CForm>
        </CCardBody>
      </CCard>
    </CCol>
  </CRow>
</template>

<script>
export default {
  name: 'FormControl',
}
</script>
