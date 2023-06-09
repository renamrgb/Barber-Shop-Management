export default [
  {
    component: 'CNavGroup',
    name: 'Atendimentos',
    icon: 'cil-calendar-check',
    items: [
      {
        component: 'CNavItem',
        name: 'Registrar',
        to: '/schedule/create',
      },
      {
        component: 'CNavItem',
        name: 'Consultar',
        to: '/schedule/get',
      },
      {
        component: 'CNavItem',
        name: 'Bloqueio de horário',
        to: '/schedule/block/get',
      },
    ],
  },
  {
    component: 'CNavGroup',
    name: 'Cadastros',
    to: '/forms',
    icon: 'cil-notes',
    items: [
      {
        component: 'CNavItem',
        name: 'Cliente',
        to: '/forms/cliente',
      },
      {
        component: 'CNavItem',
        name: 'Forma de pagamento',
        to: '/forms/forma-pagamento',
      },
      {
        component: 'CNavItem',
        name: 'Modelo de mensagem',
        to: '/forms/modeloMensagem',
      },
      {
        component: 'CNavItem',
        name: 'Procedimento',
        to: '/forms/procedimento',
      },
      {
        component: 'CNavItem',
        name: 'Produtos',
        to: '/forms/produto',
      },
      {
        component: 'CNavItem',
        name: 'Profissional',
        to: '/forms/profissional',
      },
      {
        component: 'CNavItem',
        name: 'Produto/procedimento',
        to: '/forms/tipoProdutoProcedimento',
      },
      {
        component: 'CNavItem',
        name: 'Tipo de Despesa',
        to: '/forms/tipoDespesas',
      },
    ],
  },
  {
    component: 'CNavGroup',
    name: 'Despesas',
    // to: '/forms',
    icon: 'cil-dollar',
    items: [
      {
        component: 'CNavItem',
        name: 'Registrar Despesa',
        to: '/expense',
      },
    ],
  },
  {
    component: 'CNavGroup',
    name: 'Estoque',
    // to: '/forms',
    icon: 'cil-casino',
    items: [
      {
        component: 'CNavItem',
        name: 'Lançar Produto',
        to: '/stock/listLaunchProducts',
      },
      {
        component: 'CNavItem',
        name: 'Baixas de estoque',
        to: '/stockWriteOff',
      },
    ],
  },
  {
    component: 'CNavGroup',
    name: 'Configuração',
    icon: 'cil-cog',
    items: [
      {
        component: 'CNavItem',
        name: 'Parâmetros',
        to: '/settings/parameters',
      },
    ],
  },
];
