import{useCategory as F}from"./hook-BAijJXDc.js";import{P as N}from"./index-B6j1Cnd3.js";import{u as s}from"./hooks-CU6ReGbj.js";import{u as P}from"./dict-Bc4Ygahn.js";import{d as q}from"./delete-Ce0TSdK5.js";import{d as z}from"./edit-pen-Ci7W0xNM.js";import{d as H}from"./refresh-C_2cW1e5.js";import{d as x}from"./add-circle-line-DOJqVT3s.js";import{w as K,r as V,E as a,p as c,q as R,I as l,S as t,k as e,K as O,L as j,V as A,T as m,_ as G}from"./index-DcU5f0E9.js";import"./form.vue_vue_type_script_setup_true_lang-BR2mePQA.js";import"./index-C4DFYXhp.js";import"./index-o8UmT3vP.js";import"./sortable.esm-VSgMS8pS.js";import"./epTheme-CQbZ088i.js";import"./collapse-C1UAU0Jk.js";const J={class:"main"},M=K({name:"SystemCategory",__name:"index",setup(Q){const _=V(),b=V(),{form:r,loading:g,columns:w,dataList:$,onSearch:k,resetForm:S,openDialog:f,handleDelete:B,handleSelectionChange:T}=F();return(W,o)=>{var y;const v=a("el-input"),d=a("el-form-item"),D=a("el-option"),I=a("el-select"),i=a("el-button"),L=a("el-form"),U=a("el-popconfirm"),E=a("pure-table");return c(),R("div",J,[l(L,{ref_key:"formRef",ref:_,inline:!0,model:e(r),class:"search-form bg-bg_color w-[99/100] pl-8 pt-[12px] overflow-auto"},{default:t(()=>[l(d,{label:"名称：",prop:"name"},{default:t(()=>[l(v,{modelValue:e(r).name,"onUpdate:modelValue":o[0]||(o[0]=n=>e(r).name=n),placeholder:"请输入名称",clearable:"",class:"!w-[180px]"},null,8,["modelValue"])]),_:1}),l(d,{label:"编码：",prop:"code"},{default:t(()=>[l(v,{modelValue:e(r).code,"onUpdate:modelValue":o[1]||(o[1]=n=>e(r).code=n),placeholder:"请输入编码",clearable:"",class:"!w-[180px]"},null,8,["modelValue"])]),_:1}),l(d,{label:"状态：",prop:"disabled"},{default:t(()=>[l(I,{modelValue:e(r).disabled,"onUpdate:modelValue":o[2]||(o[2]=n=>e(r).disabled=n),placeholder:"请选择状态",clearable:"",class:"!w-[180px]"},{default:t(()=>[(c(!0),R(O,null,j(e(P)().getDicts("vtl_disabled"),(n,u)=>(c(),A(D,{key:u,label:n.label,value:n.val},null,8,["label","value"]))),128))]),_:1},8,["modelValue"])]),_:1}),l(d,null,{default:t(()=>[l(i,{type:"primary",icon:e(s)("ri:search-line"),loading:e(g),onClick:e(k)},{default:t(()=>o[5]||(o[5]=[m(" 搜索 ")])),_:1},8,["icon","loading","onClick"]),l(i,{icon:e(s)(e(H)),onClick:o[3]||(o[3]=n=>e(S)(_.value))},{default:t(()=>o[6]||(o[6]=[m(" 重置 ")])),_:1},8,["icon"])]),_:1})]),_:1},8,["model"]),l(e(N),{title:"分类管理",columns:e(w),tableRef:(y=b.value)==null?void 0:y.getTableRef(),onRefresh:e(k)},{buttons:t(()=>[l(i,{type:"primary",icon:e(s)(e(x)),onClick:o[4]||(o[4]=n=>e(f)())},{default:t(()=>o[7]||(o[7]=[m(" 新增分类 ")])),_:1},8,["icon"])]),default:t(({size:n,dynamicColumns:u})=>[l(E,{ref_key:"tableRef",ref:b,adaptive:"",adaptiveConfig:{offsetBottom:45},"align-whole":"center","row-key":"id",showOverflowTooltip:"","table-layout":"auto","default-expand-all":!1,loading:e(g),size:n,data:e($),columns:u,"header-cell-style":{background:"var(--el-fill-color-light)",color:"var(--el-text-color-primary)"},onSelectionChange:e(T)},{operation:t(({row:p})=>[l(i,{class:"reset-margin",link:"",type:"primary",size:n,icon:e(s)(e(z)),onClick:C=>e(f)("修改",p)},{default:t(()=>o[8]||(o[8]=[m(" 修改 ")])),_:2},1032,["size","icon","onClick"]),l(i,{class:"reset-margin",link:"",type:"primary",size:n,icon:e(s)(e(x)),onClick:C=>e(f)("新增",{parentId:p.id})},{default:t(()=>o[9]||(o[9]=[m(" 新增 ")])),_:2},1032,["size","icon","onClick"]),l(U,{width:500,title:`是否确认【删除】名称为【${p.name}】的数据？`,onConfirm:C=>e(B)(p)},{reference:t(()=>[l(i,{class:"reset-margin",link:"",type:"primary",size:n,icon:e(s)(e(q))},{default:t(()=>o[10]||(o[10]=[m(" 删除 ")])),_:2},1032,["size","icon"])]),_:2},1032,["title","onConfirm"])]),_:2},1032,["loading","size","data","columns","onSelectionChange"])]),_:1},8,["columns","tableRef","onRefresh"])])}}}),pe=G(M,[["__scopeId","data-v-299c4799"]]);export{pe as default};
