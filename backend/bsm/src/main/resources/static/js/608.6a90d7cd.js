"use strict";(self["webpackChunkbsm"]=self["webpackChunkbsm"]||[]).push([[608],{2012:(e,t,a)=>{a.d(t,{Z:()=>l});var s=a(1505);class l{url="/expenses";replaceItem(e){return"string"!=typeof e.total&&(e.total=e.total.toString()),e.total=e.total.replace(",","."),e.total=parseFloat(e.total),e}async saveOrUpdate(e,t,a){return void 0!=t&&void 0==e&&t.forEach((e=>{a.installments.push({installmentValue:e.installmentValue,dueDate:e.dueDate})})),void 0==e?await this.cadastrar(this.replaceItem(a)):await this.alterar(this.replaceItem(a),e)}async consultarTodos(){try{const e=await s.Z.get(this.url);return e.data}catch(e){return e}}async cadastrar(e){try{e.expenseType.generateInstallments||e.installments.push({installmentValue:e.total,dueDate:e.dueDate});const t=await s.Z.post(this.url,e);return t}catch(t){return t}}async alterar(e,t){try{const a=await s.Z.put(`${this.url}/${t}`,e);return a}catch(a){return a}}async generateInstallments(e){try{const t=await s.Z.post(`${this.url}/generateInstallments`,e);return t.data}catch(t){return t}}async getOneExpense(e){try{const t=await s.Z.get(`${this.url}/${e}`);return t.data}catch(t){return t}}async delete(e){try{const t=await s.Z["delete"](`${this.url}/${e}`);return t.data}catch(t){return t.response.data}}async getAllPaged(e,t){try{const a=await s.Z.get(`${this.url}/paged?dtStart=${t.dtStart}&dtEnd=${t.dtEnd}&bringPaid=${t.bringPaid}&page=${e}`);return a.data}catch(a){return a}}async getByDescriptionPaged(e,t,a){try{const l=await s.Z.get(`${this.url}/paged?dtStart=${a.dtStart}&dtEnd=${a.dtEnd}&description=${e}&bringPaid=${a.bringPaid}&page=${t}`);return l.data}catch(l){return l}}async payOffExpense(e,t,a){try{a.amountPaid=a.amountPaid.replace(",",".");const l=await s.Z.post(`${this.url}/payOffExpense/${e}?installmentId=${t}`,a);return l}catch(l){return l}}async reverse(e,t){try{const a=await s.Z.post(`${this.url}/reverse?expenseId=${e}&installmentId=${t}`);return a}catch(a){return a}}}},8312:(e,t,a)=>{a.d(t,{Z:()=>l});var s=a(1505);class l{url="/expenseTypes";async consultarTodos(){try{const e=await s.Z.get(this.url);return e.data}catch(e){return e}}async getAllPaged(e){try{const t=await s.Z.get(`${this.url}/paged?page=${e}`);return t.data}catch(t){return t}}async getByDescription(e){try{const t=await s.Z.get(`${this.url}?description=${e}`);return t.data}catch(t){return t}}async getByDescriptionPaged(e,t){try{const a=await s.Z.get(`${this.url}/paged?description=${e}&page=${t}`);return a.data}catch(a){return a}}async cadastrar(e){try{const t=await s.Z.post(this.url,e);return t}catch(t){return t}}async excluir(e){try{const t=await s.Z["delete"](`${this.url}/${e}`);return t}catch(t){return t}}async alterar(e,t){try{const a=await s.Z.put(`${this.url}/${e}`,t);return a}catch(a){return a}}async buscarUm(e){try{const t=await s.Z.get(`${this.url}/${e}`);return t.data}catch(t){return t}}}},8608:(e,t,a)=>{a.r(t),a.d(t,{default:()=>R});var s=a(3396),l=a(7139),n=a(9242);const r=e=>((0,s.dD)("data-v-a126c76e"),e=e(),(0,s.Cn)(),e),i=r((()=>(0,s._)("strong",null,"Registar Despesa",-1))),o={class:"mb-3"},d={key:0,class:"invalid-input-form"},u={class:"row mb-3"},m={class:"col"},c={class:"row mb-3"},p={class:"col"},h={key:0,class:"invalid-input-form"},f={class:"col"},g={key:0,class:"invalid-input-form"},y={key:0,class:"row"},w={class:"col mb-3"},v={class:"col"},$={key:1},D={class:"row mb-3"},_={class:"col"},I={key:0,class:"invalid-input-form"},k={class:"col"},x={key:0,class:"invalid-input-form"},W={key:0},T={id:"tableInstallments"},V={class:"bdr"},b=["onUpdate:modelValue"],C={style:{"font-weight":"bold"}},U={class:"d-grid gap-2 d-md-flex justify-content-md-end"},q=r((()=>(0,s._)("a",{href:"/#/expense",class:"btn btn-danger"},"Cancelar",-1)));function Z(e,t,a,r,Z,S){const O=(0,s.up)("CCardHeader"),M=(0,s.up)("CFormLabel"),B=(0,s.up)("CFormInput"),E=(0,s.up)("CFormSelect"),P=(0,s.up)("CInputGroupText"),F=(0,s.up)("CInputGroup"),z=(0,s.up)("CForm"),H=(0,s.up)("CTableHeaderCell"),R=(0,s.up)("CTableRow"),Q=(0,s.up)("CTableHead"),j=(0,s.up)("CTableDataCell"),A=(0,s.up)("CTableBody"),G=(0,s.up)("CTable"),L=(0,s.up)("CButton"),Y=(0,s.up)("CCardBody"),K=(0,s.up)("CCard"),X=(0,s.up)("CCol"),J=(0,s.up)("CRow"),N=(0,s.up)("toast");return(0,s.wg)(),(0,s.iD)(s.HY,null,[(0,s.Wm)(J,null,{default:(0,s.w5)((()=>[(0,s.Wm)(X,{xs:12},{default:(0,s.w5)((()=>[(0,s.Wm)(K,{class:"mb-4"},{default:(0,s.w5)((()=>[(0,s.Wm)(O,null,{default:(0,s.w5)((()=>[i])),_:1}),(0,s.Wm)(Y,null,{default:(0,s.w5)((()=>[(0,s.Wm)(z,null,{default:(0,s.w5)((()=>[(0,s._)("div",o,[(0,s.Wm)(M,{for:"description"},{default:(0,s.w5)((()=>[(0,s.Uk)("* Descrição")])),_:1}),(0,s.Wm)(B,{name:"description",type:"text",placeholder:"",modelValue:Z.form.description,"onUpdate:modelValue":t[0]||(t[0]=e=>Z.form.description=e),required:""},null,8,["modelValue"]),Z.v$.form.description.$errors.length>0?((0,s.wg)(),(0,s.iD)("div",d,(0,l.zw)(Z.v$.form.description.$errors[0].$message),1)):(0,s.kq)("",!0)]),(0,s._)("div",u,[(0,s._)("div",m,[(0,s.Wm)(M,{for:"typeExpense"},{default:(0,s.w5)((()=>[(0,s.Uk)("* Tipo De Despesa")])),_:1}),(0,s.Wm)(E,{modelValue:Z.form.expenseType.id,"onUpdate:modelValue":t[1]||(t[1]=e=>Z.form.expenseType.id=e),options:Z.optionsSelect,searchable:!0,onClick:t[2]||(t[2]=()=>{void 0!=this.form.expenseType.id&&1==this.optionsSelect[this.form.expenseType.id].generateInstallments?(this.minQtyInstallments=1,this.minDaysBInstallments=1):(this.minQtyInstallments=0,this.minDaysBInstallments=0)})},null,8,["modelValue","options"])])]),(0,s._)("div",c,[(0,s._)("div",p,[(0,s.Wm)(M,{for:"total"},{default:(0,s.w5)((()=>[(0,s.Uk)("* Total")])),_:1}),(0,s.Wm)(F,null,{default:(0,s.w5)((()=>[(0,s.Wm)(P,null,{default:(0,s.w5)((()=>[(0,s.Uk)("R$")])),_:1}),(0,s.Wm)(B,{id:"total",modelValue:Z.form.total,"onUpdate:modelValue":t[3]||(t[3]=e=>Z.form.total=e),min:"0"},null,8,["modelValue"])])),_:1}),Z.v$.form.total.$errors.length>0?((0,s.wg)(),(0,s.iD)("div",h,(0,l.zw)(Z.v$.form.total.$errors[0].$message),1)):(0,s.kq)("",!0)]),(0,s._)("div",f,[(0,s.Wm)(M,{for:"releaseDate"},{default:(0,s.w5)((()=>[(0,s.Uk)("* Data de lançamento")])),_:1}),(0,s.wy)((0,s._)("input",{type:"date",name:"releaseDate","onUpdate:modelValue":t[4]||(t[4]=e=>Z.form.releaseDate=e),class:"form-control"},null,512),[[n.nr,Z.form.releaseDate]]),Z.v$.form.releaseDate.$errors.length>0?((0,s.wg)(),(0,s.iD)("div",g,(0,l.zw)(Z.v$.form.releaseDate.$errors[0].$message),1)):(0,s.kq)("",!0)])]),void 0==this.id&&void 0!=this.form.expenseType.id&&0==this.optionsSelect[this.form.expenseType.id].generateInstallments?((0,s.wg)(),(0,s.iD)("div",y,[(0,s._)("div",w,[(0,s._)("div",v,[(0,s.Wm)(M,{for:"releaseDate"},{default:(0,s.w5)((()=>[(0,s.Uk)("* Data de vencimento")])),_:1}),(0,s.wy)((0,s._)("input",{type:"date",name:"releaseDate","onUpdate:modelValue":t[5]||(t[5]=e=>Z.form.dueDate=e),class:"form-control"},null,512),[[n.nr,Z.form.dueDate]])])])])):(0,s.kq)("",!0),void 0!=this.form.expenseType.id&&1==this.optionsSelect[this.form.expenseType.id].generateInstallments?((0,s.wg)(),(0,s.iD)("div",$,[(0,s._)("div",D,[(0,s._)("div",_,[(0,s.Wm)(M,{for:"quantityOfInstallments"},{default:(0,s.w5)((()=>[(0,s.Uk)("* Qtde de parcelas")])),_:1}),(0,s.Wm)(B,{name:"quantityOfInstallments",type:"number",modelValue:Z.form.quantityOfInstallments,"onUpdate:modelValue":t[6]||(t[6]=e=>Z.form.quantityOfInstallments=e),min:"0",required:""},null,8,["modelValue"]),Z.v$.form.quantityOfInstallments.$errors.length>0?((0,s.wg)(),(0,s.iD)("div",I,(0,l.zw)(Z.v$.form.quantityOfInstallments.$errors[0].$message),1)):(0,s.kq)("",!0)]),(0,s._)("div",k,[(0,s.Wm)(M,{for:"total"},{default:(0,s.w5)((()=>[(0,s.Uk)("* Dias entre as parcelas")])),_:1}),(0,s.Wm)(B,{id:"total",modelValue:Z.form.daysBeetwenInstallments,"onUpdate:modelValue":t[7]||(t[7]=e=>Z.form.daysBeetwenInstallments=e),min:"0"},null,8,["modelValue"]),Z.v$.form.daysBeetwenInstallments.$errors.length>0?((0,s.wg)(),(0,s.iD)("div",x,(0,l.zw)(Z.v$.form.daysBeetwenInstallments.$errors[0].$message),1)):(0,s.kq)("",!0)])])])):(0,s.kq)("",!0)])),_:1}),this.installments.length>0?((0,s.wg)(),(0,s.iD)("div",W,[(0,s._)("div",T,[(0,s._)("div",V,[(0,s.Wm)(G,{responsive:"xl"},{default:(0,s.w5)((()=>[(0,s.Wm)(Q,{class:"table-dark"},{default:(0,s.w5)((()=>[(0,s.Wm)(R,null,{default:(0,s.w5)((()=>[(0,s.Wm)(H,{scope:"col"},{default:(0,s.w5)((()=>[(0,s.Uk)("#")])),_:1}),(0,s.Wm)(H,{scope:"col"},{default:(0,s.w5)((()=>[(0,s.Uk)("Descrição")])),_:1}),(0,s.Wm)(H,{scope:"col"},{default:(0,s.w5)((()=>[(0,s.Uk)("Valor")])),_:1}),(0,s.Wm)(H,{scope:"col"},{default:(0,s.w5)((()=>[(0,s.Uk)("Vencimento")])),_:1})])),_:1})])),_:1}),(0,s.Wm)(A,null,{default:(0,s.w5)((()=>[((0,s.wg)(!0),(0,s.iD)(s.HY,null,(0,s.Ko)(this.installments,(e=>((0,s.wg)(),(0,s.j4)(R,{key:e.id},{default:(0,s.w5)((()=>[(0,s.Wm)(H,{scope:"row"},{default:(0,s.w5)((()=>[(0,s.Uk)((0,l.zw)(e.id),1)])),_:2},1024),(0,s.Wm)(j,null,{default:(0,s.w5)((()=>[(0,s.Uk)((0,l.zw)(this.form.description),1)])),_:1}),(0,s.Wm)(j,null,{default:(0,s.w5)((()=>[(0,s.Wm)(F,null,{default:(0,s.w5)((()=>[(0,s.Wm)(P,null,{default:(0,s.w5)((()=>[(0,s.Uk)("R$")])),_:1}),(0,s.Wm)(B,{name:"price",modelValue:e.installmentValue,"onUpdate:modelValue":t=>e.installmentValue=t,required:""},null,8,["modelValue","onUpdate:modelValue"])])),_:2},1024)])),_:2},1024),(0,s.Wm)(j,null,{default:(0,s.w5)((()=>[(0,s.wy)((0,s._)("input",{type:"date",name:"releaseDate","onUpdate:modelValue":t=>e.dueDate=t,class:"form-control"},null,8,b),[[n.nr,e.dueDate]])])),_:2},1024)])),_:2},1024)))),128)),(0,s._)("tr",C,[(0,s.Wm)(j,{colspan:"3"},{default:(0,s.w5)((()=>[(0,s.Uk)(" Valor Total: ")])),_:1}),(0,s.Wm)(j,{style:{"text-align":"right"}},{default:(0,s.w5)((()=>[(0,s.Uk)(" R$ "+(0,l.zw)(S.amount),1)])),_:1})])])),_:1})])),_:1})])])])):(0,s.kq)("",!0),(0,s._)("div",null,[(0,s._)("div",U,[void 0!=this.form.expenseType.id&&1==this.optionsSelect[this.form.expenseType.id].generateInstallments?((0,s.wg)(),(0,s.j4)(L,{key:0,color:"primary",class:"me-md-2",onClick:t[8]||(t[8]=e=>this.generateInstallments())},{default:(0,s.w5)((()=>[(0,s.Uk)("Gerar parcelas")])),_:1})):(0,s.kq)("",!0),void 0!=this.form.expenseType.id&&0==this.optionsSelect[this.form.expenseType.id].generateInstallments||this.installments.length>0?((0,s.wg)(),(0,s.j4)(L,{key:1,color:"primary",class:"me-md-2",onClick:S.submitForm},{default:(0,s.w5)((()=>[(0,s.Uk)("Confirmar")])),_:1},8,["onClick"])):(0,s.kq)("",!0),q])])])),_:1})])),_:1})])),_:1})])),_:1}),(0,s.Wm)(N,{ref:"toast"},null,512)],64)}var S=a(3053),O=a(7829),M=a(9117),B=a(2012),E=a(5066),P=a(8312);const F={components:{Toast:E.Z},name:"Produtos",data(){return{v$:(0,S.Xw)(),validationsMessage:new O.Z,tipoDeDespesaService:new P.Z,id:this.$route.params.id,service:new B.Z,alreadyGeneratedInstallments:!1,generateInstallment:!1,form:{description:"",expenseType:{id:void 0,generateInstallments:void 0},total:"00.00",daysBeetwenInstallments:0,quantityOfInstallments:0,releaseDate:this.currentDate(),dueDate:this.currentDate(),installments:[]},optionsSelect:["Abra este menu de seleção"],installments:[],minQtyInstallments:0,minDaysBInstallments:0}},computed:{amount(){let e=0;for(let t=0;t<this.installments.length;t++)""!=this.installments[t].installmentValue&&(e=parseFloat(e)+parseFloat(this.installments[t].installmentValue));return e.toFixed(2)}},validations(){return{form:{description:{required:this.validationsMessage.requiredMessage,maxLength:this.validationsMessage.maxLengthMenssage(60)},total:{required:this.validationsMessage.requiredMessage,decimal:M.gH,minValue:this.validationsMessage.minMenssage(.1)},releaseDate:{required:this.validationsMessage.requiredMessage},daysBeetwenInstallments:{required:this.validationsMessage.requiredMessage,minValue:this.validationsMessage.minMenssage(this.minDaysBInstallments)},quantityOfInstallments:{required:this.validationsMessage.requiredMessage,minValue:this.validationsMessage.minMenssage(this.minQtyInstallments)}}}},methods:{submitForm(){this.v$.$validate(),this.v$.$error||(this.amount==this.form.total?this.salvar():this.$refs.toast.createToastDanger("Os valor total das parcelas não é igual ao valor total da despesa"))},currentDate(){const e=Date.now(),t=new Date(e);let a=t.toISOString().split("T");return a[0]},async salvar(){this.form.expenseType.generateInstallments=this.optionsSelect[this.form.expenseType.id].generateInstallments;let e=await this.service.saveOrUpdate(this.id,this.installments,this.form);if(201==e.status)this.$refs.toast.createToast("Cadastrado com sucesso!"),this.$router.push("/expense");else if(200==e.status)this.$refs.toast.createToast("Alterado com sucesso!");else{let t=e.response.data.fieldErrors;t.forEach((e=>{this.$refs.toast.createToast(`${e.message} `)}))}},async carregarOptionsSelect(){let e=await this.tipoDeDespesaService.consultarTodos();e.forEach((e=>{this.optionsSelect.push({value:e.id,label:e.description,generateInstallments:e.generateInstallments})}))},async generateInstallments(){this.v$.$validate(),this.v$.$error||(this.installments=await this.service.generateInstallments(this.form),this.generateInstallment=!0)},async getOneExpense(e){this.form=await this.service.getOneExpense(e),this.installments=this.form.installments}},mounted(){this.carregarOptionsSelect(),void 0!=this.id&&this.getOneExpense(this.id)}};var z=a(89);const H=(0,z.Z)(F,[["render",Z],["__scopeId","data-v-a126c76e"]]),R=H}}]);
//# sourceMappingURL=608.6a90d7cd.js.map