import { h, resolveComponent } from 'vue'
import { createRouter, createWebHashHistory } from 'vue-router'

import DefaultLayout from '@/layouts/DefaultLayout'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: DefaultLayout,
    redirect: '/dashboard',
    children: [
      {
        path: '/dashboard',
        name: 'Dashboard',
        component: () =>
          import(/* webpackChunkName: "dashboard" */ '@/views/Dashboard.vue'),
      },
      {
        path: '/theme',
        name: 'Theme',
        redirect: '/theme/typography',
      },
      {
        path: '/theme/colors',
        name: 'Colors',
        component: () => import('@/views/theme/Colors.vue'),
      },
      {
        path: '/theme/typography',
        name: 'Typography',
        component: () => import('@/views/theme/Typography.vue'),
      },
      {
        path: '/base',
        name: 'Base',
        component: {
          render() {
            return h(resolveComponent('router-view'))
          },
        },
        redirect: '/base/breadcrumbs',
        children: [
          {
            path: '/base/accordion',
            name: 'Accordion',
            component: () => import('@/views/base/Accordion.vue'),
          },
          {
            path: '/base/breadcrumbs',
            name: 'Breadcrumbs',
            component: () => import('@/views/base/Breadcrumbs.vue'),
          },
          {
            path: '/base/cards',
            name: 'Cards',
            component: () => import('@/views/base/Cards.vue'),
          },
          {
            path: '/base/carousels',
            name: 'Carousels',
            component: () => import('@/views/base/Carousels.vue'),
          },
          {
            path: '/base/collapses',
            name: 'Collapses',
            component: () => import('@/views/base/Collapses.vue'),
          },
          {
            path: '/base/list-groups',
            name: 'List Groups',
            component: () => import('@/views/base/ListGroups.vue'),
          },
          {
            path: '/base/navs',
            name: 'Navs',
            component: () => import('@/views/base/Navs.vue'),
          },
          {
            path: '/base/paginations',
            name: 'Paginations',
            component: () => import('@/views/base/Paginations.vue'),
          },
          {
            path: '/base/placeholders',
            name: 'Placeholders',
            component: () => import('@/views/base/Placeholders.vue'),
          },
          {
            path: '/base/popovers',
            name: 'Popovers',
            component: () => import('@/views/base/Popovers.vue'),
          },
          {
            path: '/base/progress',
            name: 'Progress',
            component: () => import('@/views/base/Progress.vue'),
          },
          {
            path: '/base/spinners',
            name: 'Spinners',
            component: () => import('@/views/base/Spinners.vue'),
          },
          {
            path: '/base/tables',
            name: 'Tables',
            component: () => import('@/views/base/Tables.vue'),
          },
          {
            path: '/base/tooltips',
            name: 'Tooltips',
            component: () => import('@/views/base/Tooltips.vue'),
          },
        ],
      },
      {
        path: '/buttons',
        name: 'Buttons',
        component: {
          render() {
            return h(resolveComponent('router-view'))
          },
        },
        redirect: '/buttons/standard-buttons',
        children: [
          {
            path: '/buttons/standard-buttons',
            name: 'Buttons',
            component: () => import('@/views/buttons/Buttons.vue'),
          },
          {
            path: '/buttons/dropdowns',
            name: 'Dropdowns',
            component: () => import('@/views/buttons/Dropdowns.vue'),
          },
          {
            path: '/buttons/button-groups',
            name: 'Button Groups',
            component: () => import('@/views/buttons/ButtonGroups.vue'),
          },
        ],
      },
      {
        path: '/forms',
        name: 'Forms',
        component: {
          render() {
            return h(resolveComponent('router-view'))
          },
        },
        redirect: '/forms/form-control',
        children: [          
          {
            path: '/forms/forma-pagamento',
            name: 'Forma Pagamento',
            component: () => import('@/views/list/FormaPagamento.vue'),
          },
          {
            path: '/forms/forma-pagamento/cadastro/:id',
            name: 'Alterar forma de pagamento',
            component: () => import('@/views/forms/FormaPagamento.vue'),
          },
          {
            path: '/forms/forma-pagamento/cadastro',
            name: 'Cadastrar forma de pagamento',
            component: () => import('@/views/forms/FormaPagamento.vue'),
          },
          {
            path: '/forms/produto',
            name: 'Produto',
            component: () => import('@/views/list/Produto.vue'),
          },
          {
            path: '/forms/produto/cadastro',
            name: 'Cadastrar produto',
            component: () => import('@/views/forms/Produto.vue'),
          },
          {
            path: '/forms/produto/cadastro/:id',
            name: 'Alterar produto',
            component: () => import('@/views/forms/Produto.vue'),
          },
          {
            path: '/forms/procedimento',
            name: 'Procedimentos',
            component: () => import('@/views/list/Procedimento.vue'),
          },
          {
            path: '/forms/procedimento/cadastro',
            name: 'Cadastrar Procedimento',
            component: () => import('@/views/forms/Procedimento.vue'),
          },
          {
            path: '/forms/procedimento/cadastro/:id',
            name: 'Alterar Procedimento',
            component: () => import('@/views/forms/Procedimento.vue'),
          },
          {
            path: '/forms/tipoDespesas',
            name: 'Tipo de despesa',
            component: () => import('@/views/list/TipoDespesa.vue'),
          },
          {
            path: '/forms/tipoDespesas/cadastro',
            name: 'Cadastrar Tipo de despesa',
            component: () => import('@/views/forms/TipoDespesa.vue'),
          },
          {
            path: '/forms/tipoDespesas/cadastro/:id',
            name: 'Alterar Tipo de despesa',
            component: () => import('@/views/forms/TipoDespesa.vue'),
          },
          {
            path: '/forms/modeloMensagem',
            name: 'Modelo Mensagem',
            component: () => import('@/views/list/ModeloMensagem.vue'),
          },
          {
            path: '/forms/modeloMensagem/cadastro',
            name: 'Cadastrar Modelo Mensagem',
            component: () => import('@/views/forms/ModeloMensagem.vue'),
          },
          {
            path: '/forms/modeloMensagem/cadastro/:id',
            name: 'Alterar Modelo Mensagem',
            component: () => import('@/views/forms/ModeloMensagem.vue'),
          },
          {
            path: '/forms/tipoProdutoProcedimento',
            name: 'Modelo Tipo Produto ou Procedimento',
            component: () => import('@/views/list/TipoProdutoProcedimento.vue'),
          },
          {
            path: '/forms/tipoProdutoProcedimento/cadastro',
            name: 'Cadastrar Tipo Produto ou Procedimento',
            component: () => import('@/views/forms/TipoProdutoProcedimento.vue'),
          },
          {
            path: '/forms/tipoProdutoProcedimento/cadastro/:id',
            name: 'Alterar Modelo Tipo Produto ou Procedimento',
            component: () => import('@/views/forms/TipoProdutoProcedimento.vue'),
          },
          {
            path: '/forms/cliente',
            name: 'Cliente',
            component: () => import('@/views/list/Cliente.vue'),
          },
          {
            path: '/forms/cliente/cadastro',
            name: 'Cadastrar Cliente',
            component: () => import('@/views/forms/Cliente.vue'),
          },
          {
            path: '/forms/cliente/cadastro/:id',
            name: 'Alterar Cliente',
            component: () => import('@/views/forms/Cliente.vue'),
          },
        ],
      },
      {
        path: '/charts',
        name: 'Charts',
        component: () => import('@/views/charts/Charts.vue'),
      },
      {
        path: '/icons',
        name: 'Icons',
        component: {
          render() {
            return h(resolveComponent('router-view'))
          },
        },
        redirect: '/icons/coreui-icons',
        children: [
          {
            path: '/icons/coreui-icons',
            name: 'CoreUI Icons',
            component: () => import('@/views/icons/CoreUIIcons.vue'),
          },
          {
            path: '/icons/brands',
            name: 'Brands',
            component: () => import('@/views/icons/Brands.vue'),
          },
          {
            path: '/icons/flags',
            name: 'Flags',
            component: () => import('@/views/icons/Flags.vue'),
          },
        ],
      },
      {
        path: '/notifications',
        name: 'Notifications',
        component: {
          render() {
            return h(resolveComponent('router-view'))
          },
        },
        redirect: '/notifications/alerts',
        children: [
          {
            path: '/notifications/alerts',
            name: 'Alerts',
            component: () => import('@/views/notifications/Alerts.vue'),
          },
          {
            path: '/notifications/badges',
            name: 'Badges',
            component: () => import('@/views/notifications/Badges.vue'),
          },
          {
            path: '/notifications/modals',
            name: 'Modals',
            component: () => import('@/views/notifications/Modals.vue'),
          },
        ],
      },
      {
        path: '/widgets',
        name: 'Widgets',
        component: () => import('@/views/widgets/Widgets.vue'),
      },
    ],
  },
  {
    path: '/pages',
    redirect: '/pages/404',
    name: 'Pages',
    component: {
      render() {
        return h(resolveComponent('router-view'))
      },
    },
    children: [
      {
        path: '404',
        name: 'Page404',
        component: () => import('@/views/pages/Page404'),
      },
      {
        path: '500',
        name: 'Page500',
        component: () => import('@/views/pages/Page500'),
      },
      {
        path: 'login',
        name: 'Login',
        component: () => import('@/views/pages/Login'),
      },
      {
        path: 'register',
        name: 'Register',
        component: () => import('@/views/pages/Register'),
      },
    ],
  },
]

const router = createRouter({
  history: createWebHashHistory(process.env.BASE_URL),
  routes,
  scrollBehavior() {
    // always scroll to top
    return { top: 0 }
  },
})

export default router
