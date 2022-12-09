<template>
  <CDropdown variant="nav-item">
    <CDropdownToggle placement="bottom-end" class="py-0" :caret="false">      
      <p> {{nomeUsuario}}</p>
    </CDropdownToggle>
    <a @click="sair">
      <CDropdownMenu class="pt-0">
        <CDropdownItem>
          <CIcon icon="cil-lock-locked" />Sair
        </CDropdownItem>
      </CDropdownMenu>
    </a>

  </CDropdown>
</template>

<script>
import avatar from '@/assets/images/avatars/8.jpg'
export default {
  name: 'AppHeaderDropdownAccnt',
  setup() {
    return {
      avatar: avatar,
      itemsCount: 42,
      nomeUsuario: localStorage.getItem('nome_usuario')
    }
  },
  methods: {
    sair() {
      localStorage.removeItem('access_token')
      this.$router.push('/#/login')
    }
  }
}
</script>
