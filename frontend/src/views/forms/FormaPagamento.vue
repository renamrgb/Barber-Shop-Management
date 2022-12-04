<template>
  <CRow>
    <CCol :xs="12">
      <CCard class="mb-4">
        <CCardHeader>
          <strong>Forma de Pagamento</strong>
        </CCardHeader>
        <CCardBody>
          <CForm>
            <div class="mb-3">
              <CFormLabel for="descricao">Descrição</CFormLabel>
              <CFormInput
                id="descricao"
                type="text"
                placeholder="Cartão de crédito"
                v-model.lazy="descricao"
              />
            </div>
            <div class="mb-3">
              <CFormCheck
                id="ativo"
                type="radio"
                name="flexRadioDefault"
                label="Ativo"
                v-model.lazy="ativo"
                checked
              />
            </div>
            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
              <CButton
                color="primary"
                class="me-md-2"
                @click="saveFormaPagamento"
                >Confirmar</CButton
              >
              <CButton color="danger">Cancelar</CButton>
            </div>
          </CForm>
        </CCardBody>
      </CCard>
    </CCol>
  </CRow>
  <toast></toast>
</template>

<script>
import FormaPagamentoService from '@/Services/formaPagamentoService'
import Toast from '@/components/Toast.vue'
const fp = new FormaPagamentoService()
export default {
  components: { Toast },
  name: 'FormaPagamento',
  data() {
    return {
      descricao: '',
      ativo: true,
    }
  },
  methods: {
    saveFormaPagamento() {
      fp.saveFormasPagamento({ descricao: this.descricao, ativo: this.ativo })
    }    
  },
}
</script>
