import { h, resolveComponent } from "vue";
import { createRouter, createWebHashHistory } from "vue-router";

import DefaultLayout from "@/layouts/DefaultLayout";

const routes = [
  {
    path: "/",
    name: "Home",
    component: DefaultLayout,
    redirect: "/dashboard",
    children: [
      {
        path: "/dashboard",
        name: "Dashboard",
        component: () =>
          import(/* webpackChunkName: "dashboard" */ "@/views/Dashboard.vue"),
      },
      {
        path: "/forms",
        name: "Forms",
        component: {
          render() {
            return h(resolveComponent("router-view"));
          },
        },
        redirect: "/forms/form-control",
        children: [
          {
            path: "/forms/forma-pagamento",
            name: "Forma Pagamento",
            component: () => import("@/views/list/FormaPagamento.vue"),
          },
          {
            path: "/forms/forma-pagamento/cadastro/:id",
            name: "Alterar forma de pagamento",
            component: () => import("@/views/forms/FormaPagamento.vue"),
          },
          {
            path: "/forms/forma-pagamento/cadastro",
            name: "Cadastrar forma de pagamento",
            component: () => import("@/views/forms/FormaPagamento.vue"),
          },
          {
            path: "/forms/produto",
            name: "Produto",
            component: () => import("@/views/list/Produto.vue"),
          },
          {
            path: "/forms/produto/cadastro",
            name: "Cadastrar produto",
            component: () => import("@/views/forms/Produto.vue"),
          },
          {
            path: "/forms/produto/cadastro/:id",
            name: "Alterar produto",
            component: () => import("@/views/forms/Produto.vue"),
          },
          {
            path: "/forms/procedimento",
            name: "Procedimentos",
            component: () => import("@/views/list/Procedimento.vue"),
          },
          {
            path: "/forms/procedimento/cadastro",
            name: "Cadastrar Procedimento",
            component: () => import("@/views/forms/Procedimento.vue"),
          },
          {
            path: "/forms/procedimento/cadastro/:id",
            name: "Alterar Procedimento",
            component: () => import("@/views/forms/Procedimento.vue"),
          },
          {
            path: "/forms/tipoDespesas",
            name: "Tipo de despesa",
            component: () => import("@/views/list/TipoDespesa.vue"),
          },
          {
            path: "/forms/tipoDespesas/cadastro",
            name: "Cadastrar Tipo de despesa",
            component: () => import("@/views/forms/TipoDespesa.vue"),
          },
          {
            path: "/forms/tipoDespesas/cadastro/:id",
            name: "Alterar Tipo de despesa",
            component: () => import("@/views/forms/TipoDespesa.vue"),
          },
          {
            path: "/forms/modeloMensagem",
            name: "Modelo Mensagem",
            component: () => import("@/views/list/ModeloMensagem.vue"),
          },
          {
            path: "/forms/modeloMensagem/cadastro",
            name: "Cadastrar Modelo Mensagem",
            component: () => import("@/views/forms/ModeloMensagem.vue"),
          },
          {
            path: "/forms/modeloMensagem/cadastro/:id",
            name: "Alterar Modelo Mensagem",
            component: () => import("@/views/forms/ModeloMensagem.vue"),
          },
          {
            path: "/forms/tipoProdutoProcedimento",
            name: "Modelo Tipo Produto ou Procedimento",
            component: () => import("@/views/list/TipoProdutoProcedimento.vue"),
          },
          {
            path: "/forms/tipoProdutoProcedimento/cadastro",
            name: "Cadastrar Tipo Produto ou Procedimento",
            component: () =>
              import("@/views/forms/TipoProdutoProcedimento.vue"),
          },
          {
            path: "/forms/tipoProdutoProcedimento/cadastro/:id",
            name: "Alterar Modelo Tipo Produto ou Procedimento",
            component: () =>
              import("@/views/forms/TipoProdutoProcedimento.vue"),
          },
          {
            path: "/forms/cliente",
            name: "Cliente",
            component: () => import("@/views/list/Cliente.vue"),
          },
          {
            path: "/forms/cliente/cadastro",
            name: "Cadastrar Cliente",
            component: () => import("@/views/forms/Cliente.vue"),
          },
          {
            path: "/forms/cliente/cadastro/:id",
            name: "Alterar Cliente",
            component: () => import("@/views/forms/Cliente.vue"),
          },
          {
            path: "/forms/profissional",
            name: "Profissional",
            component: () => import("@/views/list/Profissional.vue"),
          },
          {
            path: "/forms/profissional/cadastro",
            name: "Cadastrar Profissional",
            component: () => import("@/views/forms/Profissional.vue"),
          },
          {
            path: "/forms/profissional/cadastro/:id",
            name: "Alterar Profissional",
            component: () => import("@/views/forms/Profissional.vue"),
          },
        ],
      },
      {
        path: "/expense",
        name: "Expense",
        component: {
          render() {
            return h(resolveComponent("router-view"));
          },
        },
        redirect: "/expense",
        children: [
          {
            path: "/expense",
            name: "Expense",
            component: () => import("@/views/expense/ExpenseList.vue"),
          },
          {
            path: "/expense/form/:id",
            name: "Alterar Despesa",
            component: () => import("@/views/expense/ExpenseForm.vue"),
          },
          {
            path: "/expense/form",
            name: "Cadastrar despesa",
            component: () => import("@/views/expense/ExpenseForm.vue"),
          },
        ],
      },
      {
        path: "/settings",
        name: "Settings",
        component: {
          render() {
            return h(resolveComponent("router-view"));
          },
        },
        redirect: "/settings",
        children: [
          {
            path: "/settings/parameters",
            name: "Parâmetros",
            component: () => import("@/views/parameters/Parameters.vue"),
          },
        ],
      },
      {
        path: "/stock",
        name: "Stock",
        component: {
          render() {
            return h(resolveComponent("router-view"));
          },
        },
        redirect: "/stock",
        children: [
          {
            path: "/stock/launchProducts",
            name: "Lançar Produtos em estoque",
            component: () => import("@/views/stock/LançarProdutos.vue"),
          },
          {
            path: "/stock/listLaunchProducts",
            name: "Listagem de lançamentos",
            component: () => import("@/views/stock/LançarProdutosList.vue"),
          },
          {
            path: "/stock/launchProducts/:id",
            name: "Visualizar Lançamento",
            component: () => import("@/views/stock/LançarProdutos.vue"),
          },
        ],
      },
      {
        path: "/schedule",
        name: "Schedule",
        component: {
          render() {
            return h(resolveComponent("router-view"));
          },
        },
        redirect: "/schedule",
        children: [
          {
            path: "/schedule/create",
            name: "Registar Agendamento",
            component: () => import("@/views/schedule/ScheduleCreate.vue"),
          },
          {
            path: "/schedule/get",
            name: "Consultar Atendimentos",
            component: () => import("@/views/schedule/ScheduleGet.vue"),
          },
          {
            path: "/schedule/create/:time",
            name: "Registrar Agendamento",
            component: () => import("@/views/schedule/ScheduleCreate.vue"),
          },
        ],
      },
    ],
  },
  {
    path: "/pages",
    redirect: "/pages/404",
    name: "Pages",
    component: {
      render() {
        return h(resolveComponent("router-view"));
      },
    },
    children: [
      {
        path: "404",
        name: "Page404",
        component: () => import("@/views/pages/Page404"),
      },
      {
        path: "500",
        name: "Page500",
        component: () => import("@/views/pages/Page500"),
      },
      {
        path: "login",
        name: "Login",
        component: () => import("@/views/pages/Login"),
      },
      {
        path: "register",
        name: "Register",
        component: () => import("@/views/pages/Register"),
      },
    ],
  },
];

const router = createRouter({
  history: createWebHashHistory(process.env.BASE_URL),
  routes,
  scrollBehavior() {
    // always scroll to top
    return { top: 0 };
  },
});

router.beforeEach((to, from, next) => {
  if (
    to.name !== "Login" &&
    to.name !== "Register" &&
    !localStorage.getItem("access_token")
  ) {
    next({ name: "Login" });
  } else {
    next();
  }
});

export default router;
