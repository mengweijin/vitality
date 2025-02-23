export const icons = {
    favicon: import('public/favicon.svg?component'), 
    logo: import('public/logo.svg?component') 
  };
   
  // 使用 
  import { icons } from './svg-icons.js'; 
  const HomeIcon = icons.home; 

// 路径后添加 ?component 后缀，表示按组件加载
import Favicon from "public/favicon.svg?component";
import Logo from "public/logo.svg?component";

import LoginBackground from "@/assets/svg/login-background.svg?component";

export { Favicon, Logo, LoginBackground };
