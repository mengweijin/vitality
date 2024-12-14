var ne=Object.defineProperty;var D=Object.getOwnPropertySymbols;var ae=Object.prototype.hasOwnProperty,ue=Object.prototype.propertyIsEnumerable;var F=(t,e,o)=>e in t?ne(t,e,{enumerable:!0,configurable:!0,writable:!0,value:o}):t[e]=o,j=(t,e)=>{for(var o in e||(e={}))ae.call(e,o)&&F(t,o,e[o]);if(D)for(var o of D(e))ue.call(e,o)&&F(t,o,e[o]);return t};import{fT as w,p as m,fW as le,fM as U,R as re,a as H,az as L,fX as se,ax as fe,fH as z,fY as E,fZ as ie,ae as ce,f_ as me,f$ as de,a7 as he,aZ as G,g0 as B,fG as ge,fP as pe,aa as O,g1 as Te,fL as ve,g2 as ye}from"./index-BOgzzpjy.js";import{u as Z}from"./app-BpGhgLF0.js";import{u as A}from"./epTheme-CyfjZ5DK.js";import{e as W}from"./mitt-E5P-NQ8u.js";import{u as be}from"./user-D6ptNJJ0.js";function $e(){const{$storage:t,$config:e}=w(),o=()=>{var f,$,T,h,v,p,C,k,y,g,u,l,c,d,i,b,S;le().multiTagsCache&&(!t.tags||t.tags.length===0)&&(t.tags=U),t.locale||(t.locale={locale:(f=e==null?void 0:e.Locale)!=null?f:"zh"},re().locale.value=($=e==null?void 0:e.Locale)!=null?$:"zh"),t.layout||(t.layout={layout:(T=e==null?void 0:e.Layout)!=null?T:"vertical",theme:(h=e==null?void 0:e.Theme)!=null?h:"light",darkMode:(v=e==null?void 0:e.DarkMode)!=null?v:!1,sidebarStatus:(p=e==null?void 0:e.SidebarStatus)!=null?p:!0,epThemeColor:(C=e==null?void 0:e.EpThemeColor)!=null?C:"#409EFF",themeColor:(k=e==null?void 0:e.Theme)!=null?k:"light",overallStyle:(y=e==null?void 0:e.OverallStyle)!=null?y:"light"}),t.configure||(t.configure={grey:(g=e==null?void 0:e.Grey)!=null?g:!1,weak:(u=e==null?void 0:e.Weak)!=null?u:!1,hideTabs:(l=e==null?void 0:e.HideTabs)!=null?l:!1,hideFooter:(c=e.HideFooter)!=null?c:!0,showLogo:(d=e==null?void 0:e.ShowLogo)!=null?d:!0,showModel:(i=e==null?void 0:e.ShowModel)!=null?i:"smart",multiTagsCache:(b=e==null?void 0:e.MultiTagsCache)!=null?b:!1,stretch:(S=e==null?void 0:e.Stretch)!=null?S:!1})},n=m(()=>t==null?void 0:t.layout.layout),r=m(()=>t.layout);return{layout:n,layoutTheme:r,initStorage:o}}const N={outputDir:"",defaultScopeName:"",includeStyleWithColors:[],extract:!0,themeLinkTagId:"theme-link-tag",themeLinkTagInjectTo:"head",removeCssScopeName:!1,customThemeCssFileName:null,arbitraryMode:!1,defaultPrimaryColor:"",customThemeOutputPath:"D:/code/vue-pure-admin/node_modules/.pnpm/@pureadmin+theme@3.3.0/node_modules/@pureadmin/theme/setCustomTheme.js",styleTagId:"custom-theme-tagid",InjectDefaultStyleTagToHtml:!0,hueDiffControls:{low:0,high:0},multipleScopeVars:[{scopeName:"layout-theme-light",varsContent:`
        $subMenuActiveText: #000000d9 !default;
        $menuBg: #fff !default;
        $menuHover: #f6f6f6 !default;
        $subMenuBg: #fff !default;
        $subMenuActiveBg: #e0ebf6 !default;
        $menuText: rgb(0 0 0 / 60%) !default;
        $sidebarLogo: #fff !default;
        $menuTitleHover: #000 !default;
        $menuActiveBefore: #4091f7 !default;
      `},{scopeName:"layout-theme-default",varsContent:`
        $subMenuActiveText: #fff !default;
        $menuBg: #001529 !default;
        $menuHover: rgb(64 145 247 / 15%) !default;
        $subMenuBg: #0f0303 !default;
        $subMenuActiveBg: #4091f7 !default;
        $menuText: rgb(254 254 254 / 65%) !default;
        $sidebarLogo: #002140 !default;
        $menuTitleHover: #fff !default;
        $menuActiveBefore: #4091f7 !default;
      `},{scopeName:"layout-theme-saucePurple",varsContent:`
        $subMenuActiveText: #fff !default;
        $menuBg: #130824 !default;
        $menuHover: rgb(105 58 201 / 15%) !default;
        $subMenuBg: #000 !default;
        $subMenuActiveBg: #693ac9 !default;
        $menuText: #7a80b4 !default;
        $sidebarLogo: #1f0c38 !default;
        $menuTitleHover: #fff !default;
        $menuActiveBefore: #693ac9 !default;
      `},{scopeName:"layout-theme-pink",varsContent:`
        $subMenuActiveText: #fff !default;
        $menuBg: #28081a !default;
        $menuHover: rgb(216 68 147 / 15%) !default;
        $subMenuBg: #000 !default;
        $subMenuActiveBg: #d84493 !default;
        $menuText: #7a80b4 !default;
        $sidebarLogo: #3f0d29 !default;
        $menuTitleHover: #fff !default;
        $menuActiveBefore: #d84493 !default;
      `},{scopeName:"layout-theme-dusk",varsContent:`
        $subMenuActiveText: #fff !default;
        $menuBg: #2a0608 !default;
        $menuHover: rgb(225 60 57 / 15%) !default;
        $subMenuBg: #000 !default;
        $subMenuActiveBg: #e13c39 !default;
        $menuText: rgb(254 254 254 / 65.1%) !default;
        $sidebarLogo: #42090c !default;
        $menuTitleHover: #fff !default;
        $menuActiveBefore: #e13c39 !default;
      `},{scopeName:"layout-theme-volcano",varsContent:`
        $subMenuActiveText: #fff !default;
        $menuBg: #2b0e05 !default;
        $menuHover: rgb(232 95 51 / 15%) !default;
        $subMenuBg: #0f0603 !default;
        $subMenuActiveBg: #e85f33 !default;
        $menuText: rgb(254 254 254 / 65%) !default;
        $sidebarLogo: #441708 !default;
        $menuTitleHover: #fff !default;
        $menuActiveBefore: #e85f33 !default;
      `},{scopeName:"layout-theme-mingQing",varsContent:`
        $subMenuActiveText: #fff !default;
        $menuBg: #032121 !default;
        $menuHover: rgb(89 191 193 / 15%) !default;
        $subMenuBg: #000 !default;
        $subMenuActiveBg: #59bfc1 !default;
        $menuText: #7a80b4 !default;
        $sidebarLogo: #053434 !default;
        $menuTitleHover: #fff !default;
        $menuActiveBefore: #59bfc1 !default;
      `},{scopeName:"layout-theme-auroraGreen",varsContent:`
        $subMenuActiveText: #fff !default;
        $menuBg: #0b1e15 !default;
        $menuHover: rgb(96 172 128 / 15%) !default;
        $subMenuBg: #000 !default;
        $subMenuActiveBg: #60ac80 !default;
        $menuText: #7a80b4 !default;
        $sidebarLogo: #112f21 !default;
        $menuTitleHover: #fff !default;
        $menuActiveBefore: #60ac80 !default;
      `}]},Ce="/vitality/",ke="assets",K=t=>{let e=t.replace("#","").match(/../g);for(let o=0;o<3;o++)e[o]=parseInt(e[o],16);return e},V=(t,e,o)=>{let n=[t.toString(16),e.toString(16),o.toString(16)];for(let r=0;r<3;r++)n[r].length==1&&(n[r]=`0${n[r]}`);return`#${n.join("")}`},Se=(t,e)=>{let o=K(t);for(let n=0;n<3;n++)o[n]=Math.floor(o[n]*(1-e));return V(o[0],o[1],o[2])},Me=(t,e)=>{let o=K(t);for(let n=0;n<3;n++)o[n]=Math.floor((255-o[n])*e+o[n]);return V(o[0],o[1],o[2])},_=t=>`(^${t}\\s+|\\s+${t}\\s+|\\s+${t}$|^${t}$)`,q=({scopeName:t,multipleScopeVars:e})=>{const o=Array.isArray(e)&&e.length?e:N.multipleScopeVars;let n=document.documentElement.className;new RegExp(_(t)).test(n)||(o.forEach(r=>{n=n.replace(new RegExp(_(r.scopeName),"g"),` ${t} `)}),document.documentElement.className=n.replace(/(^\s+|\s+$)/g,""))},Q=({id:t,href:e})=>{const o=document.createElement("link");return o.rel="stylesheet",o.href=e,o.id=t,o},Be=t=>{const e=j({scopeName:"theme-default",customLinkHref:f=>f},t),o=e.themeLinkTagId||N.themeLinkTagId;let n=document.getElementById(o);const r=e.customLinkHref(`${Ce.replace(/\/$/,"")}${`/${ke}/${e.scopeName}.css`.replace(/\/+(?=\/)/g,"")}`);if(n){n.id=`${o}_old`;const f=Q({id:o,href:r});n.nextSibling?n.parentNode.insertBefore(f,n.nextSibling):n.parentNode.appendChild(f),f.onload=()=>{setTimeout(()=>{n.parentNode.removeChild(n),n=null},60),q(e)};return}n=Q({id:o,href:r}),q(e),document[(e.themeLinkTagInjectTo||N.themeLinkTagInjectTo||"").replace("-prepend","")].appendChild(n)};function Ie(){var y,g;const{layoutTheme:t,layout:e}=$e(),o=H([{color:"#ffffff",themeColor:"light"},{color:"#1b2a47",themeColor:"default"},{color:"#722ed1",themeColor:"saucePurple"},{color:"#eb2f96",themeColor:"pink"},{color:"#f5222d",themeColor:"dusk"},{color:"#fa541c",themeColor:"volcano"},{color:"#13c2c2",themeColor:"mingQing"},{color:"#52c41a",themeColor:"auroraGreen"}]),{$storage:n}=w(),r=H((y=n==null?void 0:n.layout)==null?void 0:y.darkMode),f=H((g=n==null?void 0:n.layout)==null?void 0:g.overallStyle),$=document.documentElement;function T(u,l,c){const d=c||document.body;let{className:i}=d;i=i.replace(l,"").trim(),d.className=u?`${i} ${l}`:i}function h(u=(c=>(c=L().Theme)!=null?c:"light")(),l=!0){var i,b;t.value.theme=u,Be({scopeName:`layout-theme-${u}`});const d=n.layout.themeColor;if(n.layout={layout:e.value,theme:u,darkMode:r.value,sidebarStatus:(i=n.layout)==null?void 0:i.sidebarStatus,epThemeColor:(b=n.layout)==null?void 0:b.epThemeColor,themeColor:l?u:d,overallStyle:f.value},u==="default"||u==="light")p(L().EpThemeColor);else{const S=o.value.find(x=>x.themeColor===u);p(S.color)}}function v(u,l,c){document.documentElement.style.setProperty(`--el-color-primary-${u}-${l}`,r.value?Se(c,l/10):Me(c,l/10))}const p=u=>{A().setEpThemeColor(u),document.documentElement.style.setProperty("--el-color-primary",u);for(let l=1;l<=2;l++)v("dark",l,u);for(let l=1;l<=9;l++)v("light",l,u)};function C(u){f.value=u,A().epTheme==="light"&&r.value?h("default",!1):h(A().epTheme,!1),r.value?document.documentElement.classList.add("dark"):(n.layout.themeColor==="light"&&h("light",!1),document.documentElement.classList.remove("dark"))}function k(){se(),fe().clear();const{Grey:u,Weak:l,MultiTagsCache:c,EpThemeColor:d,Layout:i}=L();Z().setLayout(i),p(d),z().multiTagsCacheChange(c),T(u,"html-grey",document.querySelector("html")),T(l,"html-weakness",document.querySelector("html")),E.push("/login"),z().handleTags("equal",[...U]),ie()}return{body:$,dataTheme:r,overallStyle:f,layoutTheme:t,themeColors:o,onReset:k,toggleClass:T,dataThemeChange:C,setEpThemeColor:p,setLayoutThemeColor:h}}const Le="The current routing configuration is incorrect, please check the configuration";function Pe(){var I,P;const t=Z(),e=ce().options.routes,{isFullscreen:o,toggle:n}=me(),{wholeMenus:r}=de(he()),f=(P=(I=L())==null?void 0:I.TooltipEffect)!=null?P:"light",$=m(()=>({width:"100%",display:"flex",alignItems:"center",justifyContent:"space-between",overflow:"hidden"})),T=m(()=>{var a,s;return G((a=B())==null?void 0:a.avatar)?be:(s=B())==null?void 0:s.avatar}),h=m(()=>{var a,s,M;return G((a=B())==null?void 0:a.nickname)?(s=B())==null?void 0:s.username:(M=B())==null?void 0:M.nickname}),v=m(()=>(a,s)=>({background:a===s?A().epThemeColor:"",color:a===s?"#f4f4f5":"#000"})),p=m(()=>(a,s)=>a===s?"":"dark:hover:!text-primary"),C=m(()=>h.value?{marginRight:"10px"}:""),k=m(()=>!t.getSidebarStatus),y=m(()=>t.getDevice),{$storage:g,$config:u}=w(),l=m(()=>{var a;return(a=g==null?void 0:g.layout)==null?void 0:a.layout}),c=m(()=>u.Title);function d(a){const s=L().Title;s?document.title=`${O(a.title)} | ${s}`:document.title=O(a.title)}function i(){Te().then(()=>{B().logOut()})}function b(){var a;E.push((a=ve())==null?void 0:a.path)}function S(){W.emit("openPanel")}function x(){E.push({name:"AccountSettings"})}function X(){t.toggleSideBar()}function Y(a){a==null||a.handleResize()}function J(a){var R;if(!a.children)return console.error(Le);const s=/^http(s?):\/\//,M=(R=a.children[0])==null?void 0:R.path;return s.test(M)?a.path+"/"+M:M}function ee(a){r.value.length===0||te(a)||W.emit("changLayoutRoute",a)}function te(a){return ye.includes(a)}function oe(){return new URL("/vitality/logo.svg",import.meta.url).href}return{title:c,device:y,layout:l,logout:i,routers:e,$storage:g,isFullscreen:o,Fullscreen:ge,ExitFullscreen:pe,toggle:n,backTopMenu:b,onPanel:S,getDivStyle:$,changeTitle:d,toggleSideBar:X,menuSelect:ee,handleResize:Y,resolvePath:J,getLogo:oe,isCollapse:k,pureApp:t,username:h,userAvatar:T,avatarsStyle:C,tooltipEffect:f,toAccountSettings:x,getDropdownItemStyle:v,getDropdownItemClass:p}}export{Ie as a,$e as b,Be as t,Pe as u};
