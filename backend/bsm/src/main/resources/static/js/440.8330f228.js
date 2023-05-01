"use strict";(self["webpackChunkbsm"]=self["webpackChunkbsm"]||[]).push([[440],{964:(t,e,a)=>{a.d(e,{Z:()=>s});var r=a(1505);class s{url="/products";async updateOrInsert(t,e){return void 0==t?await this.cadastrar(e):await this.alterar(t,e)}async consultarTodos(){try{const t=await r.Z.get(this.url);return t.data}catch(t){return t}}async getAllPaged(t,e){let a=`?page=${t}`;e&&(a=`?isActive=true&page=${t}`);try{const t=await r.Z.get(`${this.url}/productsPaged${a}`);return t.data}catch(s){return s}}async cadastrar(t){try{const e=await r.Z.post(this.url,t);return e}catch(e){return e}}async excluir(t){try{const e=await r.Z["delete"](`${this.url}/${t}`);return e}catch(e){return e}}async alterar(t,e){try{const a=await r.Z.put(`${this.url}/${t}`,e);return a}catch(a){return a}}async buscarUm(t){try{const e=await r.Z.get(`${this.url}/${t}`);return e.data}catch(e){return e}}async getByTitle(t){try{const e=await r.Z.get(`${this.url}?title=${t}`);return e.data}catch(e){return e}}async getByTitlePaged(t,e,a){let s="";a&&(s="&isActive=true");try{const a=await r.Z.get(`${this.url}/productsPaged?title=${t}${s}&page=${e}`);return a.data}catch(i){return i}}}},5925:(t,e,a)=>{a.d(e,{Z:()=>s});var r=a(1505);class s{url="/productProcedureTypes";async consultarTodos(){try{const t=await r.Z.get(this.url);return t.data}catch(t){return t}}async getAllPaged(t){try{const e=await r.Z.get(`${this.url}/paged?page=${t}`);return e.data}catch(e){return e}}async cadastrar(t){try{const e=await r.Z.post(this.url,t);return e}catch(e){return e}}async excluir(t){try{const e=await r.Z["delete"](`${this.url}/${t}`);return e}catch(e){return e}}async alterar(t,e){try{const a=await r.Z.put(`${this.url}/${t}`,e);return a}catch(a){return a}}async buscarUm(t){try{const e=await r.Z.get(`${this.url}/${t}`);return e.data}catch(e){return e}}async getByDescription(t){try{const e=await r.Z.get(`${this.url}?description=${t}`);return e.data}catch(e){return e}}async getByDescriptionPaged(t,e){try{const a=await r.Z.get(`${this.url}/paged?description=${t}&page=${e}`);return a.data}catch(a){return a}}}},2440:(t,e,a)=>{a.r(e),a.d(e,{default:()=>M});var r=a(3396),s=a(7139);const i=(0,r._)("strong",null,"Produtos",-1),l={class:"mb-3"},o={key:0,class:"invalid-input-form"},n={class:"row mb-3"},u={class:"col"},c={class:"mb-3"},d={key:0,class:"invalid-input-form"},m={class:"row mb-3"},p={class:"col"},h={key:0,class:"invalid-input-form"},g={class:"col"},f={key:0,class:"invalid-input-form"},y={class:"mb-1"},$={class:"d-grid gap-2 d-md-flex justify-content-md-end"},v=(0,r._)("a",{href:"/#/forms/produto",class:"btn btn-danger"},"Cancelar",-1);function w(t,e,a,w,b,C){const _=(0,r.up)("CCardHeader"),Z=(0,r.up)("CFormLabel"),k=(0,r.up)("CFormInput"),q=(0,r.up)("CFormSelect"),W=(0,r.up)("CInputGroupText"),V=(0,r.up)("CInputGroup"),U=(0,r.up)("CFormSwitch"),M=(0,r.up)("CButton"),T=(0,r.up)("CForm"),P=(0,r.up)("CCardBody"),x=(0,r.up)("CCard"),A=(0,r.up)("CCol"),S=(0,r.up)("CRow"),D=(0,r.up)("toast");return(0,r.wg)(),(0,r.iD)(r.HY,null,[(0,r.Wm)(S,null,{default:(0,r.w5)((()=>[(0,r.Wm)(A,{xs:12},{default:(0,r.w5)((()=>[(0,r.Wm)(x,{class:"mb-4"},{default:(0,r.w5)((()=>[(0,r.Wm)(_,null,{default:(0,r.w5)((()=>[i])),_:1}),(0,r.Wm)(P,null,{default:(0,r.w5)((()=>[(0,r.Wm)(T,null,{default:(0,r.w5)((()=>[(0,r._)("div",l,[(0,r.Wm)(Z,{for:"title"},{default:(0,r.w5)((()=>[(0,r.Uk)("* Título")])),_:1}),(0,r.Wm)(k,{name:"title",type:"text",placeholder:"Creme de barbear...",modelValue:b.form.title,"onUpdate:modelValue":e[0]||(e[0]=t=>b.form.title=t),required:""},null,8,["modelValue"]),b.v$.form.title.$errors.length>0?((0,r.wg)(),(0,r.iD)("div",o,(0,s.zw)(b.v$.form.title.$errors[0].$message),1)):(0,r.kq)("",!0)]),(0,r._)("div",n,[(0,r._)("div",u,[(0,r.Wm)(Z,{for:"typeExpense"},{default:(0,r.w5)((()=>[(0,r.Uk)("* Tipo De Produto")])),_:1}),(0,r.Wm)(q,{modelValue:b.form.productType.id,"onUpdate:modelValue":e[1]||(e[1]=t=>b.form.productType.id=t),options:b.optionsSelect,searchable:!0},null,8,["modelValue","options"])])]),(0,r._)("div",c,[(0,r.Wm)(Z,{for:"descricao"},{default:(0,r.w5)((()=>[(0,r.Uk)("Marca")])),_:1}),(0,r.Wm)(k,{id:"brand",type:"text",placeholder:"",modelValue:b.form.brand,"onUpdate:modelValue":e[2]||(e[2]=t=>b.form.brand=t),maxlength:"65"},null,8,["modelValue"]),b.v$.form.brand.$errors.length>0?((0,r.wg)(),(0,r.iD)("div",d,(0,s.zw)(b.v$.form.brand.$errors[0].$message),1)):(0,r.kq)("",!0)]),(0,r._)("div",m,[(0,r._)("div",p,[(0,r.Wm)(Z,{for:"price"},{default:(0,r.w5)((()=>[(0,r.Uk)("* Preço")])),_:1}),(0,r.Wm)(V,null,{default:(0,r.w5)((()=>[(0,r.Wm)(W,null,{default:(0,r.w5)((()=>[(0,r.Uk)("R$")])),_:1}),(0,r.Wm)(k,{name:"price",modelValue:b.form.price,"onUpdate:modelValue":e[3]||(e[3]=t=>b.form.price=t),required:""},null,8,["modelValue"])])),_:1}),b.v$.form.price.$errors.length>0?((0,r.wg)(),(0,r.iD)("div",h,(0,s.zw)(b.v$.form.price.$errors[0].$message),1)):(0,r.kq)("",!0)]),(0,r._)("div",g,[(0,r.Wm)(Z,{for:"quantity"},{default:(0,r.w5)((()=>[(0,r.Uk)("* Qtde em estoque")])),_:1}),(0,r.Wm)(k,{name:"quantity",type:"number",modelValue:b.form.quantity,"onUpdate:modelValue":e[4]||(e[4]=t=>b.form.quantity=t),min:"0",required:""},null,8,["modelValue"]),b.v$.form.quantity.$errors.length>0?((0,r.wg)(),(0,r.iD)("div",f,(0,s.zw)(b.v$.form.quantity.$errors[0].$message),1)):(0,r.kq)("",!0)])]),(0,r._)("div",y,[(0,r.Wm)(U,{id:"formSwitchCheckDefault",label:"Ativo",modelValue:b.form.isActive,"onUpdate:modelValue":e[5]||(e[5]=t=>b.form.isActive=t)},null,8,["modelValue"])]),(0,r._)("div",$,[(0,r.Wm)(M,{color:"primary",class:"me-md-2",onClick:C.submitForm},{default:(0,r.w5)((()=>[(0,r.Uk)("Confirmar")])),_:1},8,["onClick"]),v])])),_:1})])),_:1})])),_:1})])),_:1})])),_:1}),(0,r.Wm)(D,{ref:"toast"},null,512)],64)}var b=a(3053),C=a(7829),_=a(9117),Z=a(964),k=a(5925),q=a(5066);const W={components:{Toast:q.Z},name:"Produtos",data(){return{v$:(0,b.Xw)(),validationsMessage:new C.Z,typeProductProcedureService:new k.Z,service:new Z.Z,id:this.$route.params.id,form:{title:"",brand:"",productType:{id:""},quantity:0,price:"",isActive:!1},optionsSelect:["Abra este menu de seleção"]}},validations(){return{form:{title:{required:this.validationsMessage.requiredMessage,maxLength:this.validationsMessage.maxLengthMenssage(60)},brand:{maxLength:this.validationsMessage.maxLengthMenssage(60)},quantity:{required:this.validationsMessage.requiredMessage,minValue:this.validationsMessage.minMenssage(0)},price:{required:this.validationsMessage.requiredMessage,decimal:_.gH,minValue:this.validationsMessage.minMenssage(.1)}}}},methods:{submitForm(){this.v$.$validate(),this.v$.$error||this.salvar()},async salvar(){let t=await this.service.updateOrInsert(this.id,this.form);if(201==t.status)this.$refs.toast.createToast("Cadastrado com sucesso!"),this.$router.push("/forms/produto");else if(200==t.status)this.$refs.toast.createToast("Alterado com sucesso!");else{let e=t.response.data.fieldErrors;e.forEach((t=>{this.$refs.toast.createToast(`${t.message} `)}))}},async consultarUm(){void 0!=this.id&&(this.form=await this.service.buscarUm(this.id))},async carregarOptionsSelect(){let t=await this.typeProductProcedureService.consultarTodos();t.forEach((t=>{this.optionsSelect.push({value:t.id,label:t.description,generateInstallments:t.generateInstallments})}))}},mounted(){this.carregarOptionsSelect(),void 0!=this.id&&this.consultarUm()}};var V=a(89);const U=(0,V.Z)(W,[["render",w]]),M=U}}]);
//# sourceMappingURL=440.8330f228.js.map