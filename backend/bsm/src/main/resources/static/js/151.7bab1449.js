"use strict";(self["webpackChunkbsm"]=self["webpackChunkbsm"]||[]).push([[151],{1688:(e,t,a)=>{a.d(t,{Z:()=>r});var s=a(1505);class r{url="/messageTemplate";async consultarTodos(){try{const e=await s.Z.get(this.url);return e.data}catch(e){return e}}async getAllPaged(e){try{const t=await s.Z.get(`${this.url}/paged?page=${e}`);return t.data}catch(t){return t}}async cadastrar(e){try{const t=await s.Z.post(this.url,e);return t}catch(t){return t}}async excluir(e){try{const t=await s.Z["delete"](`${this.url}/${e}`);return t}catch(t){return t}}async alterar(e,t){try{const a=await s.Z.put(`${this.url}/${e}`,t);return a}catch(a){return a}}async buscarUm(e){try{const t=await s.Z.get(`${this.url}/${e}`);return t.data}catch(t){return t}}async getByTitle(e){try{const t=await s.Z.get(`${this.url}?title=${e}`);return t.data}catch(t){return t}}async getByTitlePaged(e,t){try{const a=await s.Z.get(`${this.url}/paged?title=${e}&page=${t}`);return a.data}catch(a){return a}}}},1151:(e,t,a)=>{a.r(t),a.d(t,{default:()=>C});var s=a(3396),r=a(7139);const o=(0,s._)("strong",null,"Modelo de mensagem",-1),i={class:"mb-3"},l={key:0,class:"invalid-input-form"},d={class:"mb-3"},n={key:0,class:"invalid-input-form"},u={class:"mb-3"},m={class:"d-grid gap-2 d-md-flex justify-content-md-end"},c=(0,s._)("a",{class:"btn btn-danger",href:"/#/forms/modeloMensagem"},"Cancelar",-1);function h(e,t,a,h,g,f){const p=(0,s.up)("CCardHeader"),v=(0,s.up)("CFormLabel"),$=(0,s.up)("CFormInput"),y=(0,s.up)("CFormTextarea"),w=(0,s.up)("CFormSwitch"),C=(0,s.up)("CButton"),b=(0,s.up)("CForm"),M=(0,s.up)("CCardBody"),_=(0,s.up)("CCard"),k=(0,s.up)("CCol"),Z=(0,s.up)("CRow"),T=(0,s.up)("toast");return(0,s.wg)(),(0,s.iD)(s.HY,null,[(0,s.Wm)(Z,null,{default:(0,s.w5)((()=>[(0,s.Wm)(k,{xs:12},{default:(0,s.w5)((()=>[(0,s.Wm)(_,{class:"mb-4"},{default:(0,s.w5)((()=>[(0,s.Wm)(p,null,{default:(0,s.w5)((()=>[o])),_:1}),(0,s.Wm)(M,null,{default:(0,s.w5)((()=>[(0,s.Wm)(b,null,{default:(0,s.w5)((()=>[(0,s._)("div",i,[(0,s.Wm)(v,{for:"titulo"},{default:(0,s.w5)((()=>[(0,s.Uk)("* Título da mensagem")])),_:1}),(0,s.Wm)($,{id:"titulo",type:"text",placeholder:"Mensagem de confirmação...",modelValue:g.form.title,"onUpdate:modelValue":t[0]||(t[0]=e=>g.form.title=e),modelModifiers:{lazy:!0},required:""},null,8,["modelValue"]),g.v$.form.title.$errors.length>0?((0,s.wg)(),(0,s.iD)("div",l,(0,r.zw)(g.v$.form.title.$errors[0].$message),1)):(0,s.kq)("",!0)]),(0,s._)("div",d,[(0,s.Wm)(y,{id:"mensagem",label:"* Corpo da Mensagem",rows:"5",modelValue:g.form.bodyMessage,"onUpdate:modelValue":t[1]||(t[1]=e=>g.form.bodyMessage=e),modelModifiers:{lazy:!0},required:""},null,8,["modelValue"]),g.v$.form.bodyMessage.$errors.length>0?((0,s.wg)(),(0,s.iD)("div",n,(0,r.zw)(g.v$.form.bodyMessage.$errors[0].$message),1)):(0,s.kq)("",!0)]),(0,s._)("div",u,[(0,s.Wm)(w,{id:"formSwitchCheckDefault",label:"Ativo",modelValue:g.form.isActive,"onUpdate:modelValue":t[2]||(t[2]=e=>g.form.isActive=e)},null,8,["modelValue"])]),(0,s._)("div",m,[(0,s.Wm)(C,{color:"primary",onClick:f.submitForm},{default:(0,s.w5)((()=>[(0,s.Uk)("Confirmar")])),_:1},8,["onClick"]),c])])),_:1})])),_:1})])),_:1})])),_:1})])),_:1}),(0,s.Wm)(T,{ref:"toast"},null,512)],64)}var g=a(3053),f=a(7829),p=a(1688),v=a(5066);const $={components:{Toast:v.Z},name:"Tipo de Despesa",data(){return{v$:(0,g.Xw)(),validationsMessage:new f.Z,id:this.$route.params.id,service:new p.Z,form:{title:"",bodyMessage:"",isActive:!1}}},validations(){return{form:{title:{required:this.validationsMessage.requiredMessage,maxLength:this.validationsMessage.maxLengthMenssage(60)},bodyMessage:{required:this.validationsMessage.requiredMessage,maxLength:this.validationsMessage.maxLengthMenssage(1e3)}}}},methods:{submitForm(){this.v$.$validate(),this.v$.$error||this.salvar()},async salvar(){let e;if(e=void 0==this.id?await this.service.cadastrar(this.form):await this.service.alterar(this.id,this.form),201==e.status)this.$refs.toast.createToast("Cadastrado com sucesso!"),this.$router.push("/forms/modeloMensagem");else if(200==e.status)this.$refs.toast.createToast("Alterado com sucesso!");else if(500==e.status)this.$refs.toast.createToast(e.error);else{let t=e.response.data.fieldErrors;t.forEach((e=>{this.$refs.toast.createToast(`${e.message} `)}))}},async consultarUm(){void 0!=this.id&&(this.form=await this.service.buscarUm(this.id))}},mounted(){void 0!=this.id&&this.consultarUm()}};var y=a(89);const w=(0,y.Z)($,[["render",h]]),C=w}}]);
//# sourceMappingURL=151.7bab1449.js.map