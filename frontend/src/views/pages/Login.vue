<template>
  <div class="bg-light min-vh-100 d-flex flex-row align-items-center">
    <CContainer>
      <CRow class="justify-content-center">
        <CCol :md="8">
          <CCardGroup>
            <CCard class="p-4">
              <CCardBody>
                <CForm>
                  <h1>Login</h1>
                  <p class="text-medium-emphasis">Faça login em sua conta</p>
                  <CInputGroup class="mb-3">
                    <CInputGroupText>
                      <CIcon icon="cil-user" />
                    </CInputGroupText>
                    <CFormInput
                      placeholder="Username"
                      autocomplete="username"
                      v-model="usuario"
                    />
                  </CInputGroup>
                  <CInputGroup class="mb-4">
                    <CInputGroupText>
                      <CIcon icon="cil-lock-locked" />
                    </CInputGroupText>
                    <CFormInput
                      type="password"
                      placeholder="Password"
                      autocomplete="current-password"
                      v-model="senha"
                    />
                  </CInputGroup>
                  <CRow>
                    <CCol :xs="6">
                      <CButton color="primary" class="px-4" @click="login">
                        Login
                      </CButton>
                    </CCol>
                  </CRow>
                </CForm>
              </CCardBody>
            </CCard>
          </CCardGroup>
        </CCol>
      </CRow>
    </CContainer>
  </div>
  <toast ref="toast" />
</template>

<script>
import Toast from "@/components/Toast.vue";
import UsuarioService from "@/Services/usuarioService.js";
export default {
  components: { Toast },
  name: "Login",
  data() {
    return {
      service: new UsuarioService(),
      usuario: "",
      senha: "",
    };
  },
  methods: {
    async login() {
      let res = await this.service.login(this.usuario, this.senha);      
      if (res.status == 200) {
        localStorage.setItem("access_token", res.data.access_token);
        localStorage.setItem("nome_usuario", res.data.userFirstName);
        localStorage.setItem("userId", res.data.userId);
        this.$router.push({ path: `/` });
      } else if (res.response.status == 400) {
        this.$refs.toast.createToastDanger("Usuário não existe ou senha inválida");
      }
    },
  },
};
</script>
