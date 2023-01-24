export default [  
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
]
