/**
 * 注意：如果图片是在 public 目录下，则直接引用即可。比如：<img src="/favicon.svg" />
 * 
 * 如果在 src/assets/svg 目录下，则通过以下方式引用：
 * 
 * 路径后添加 ?component 后缀，表示按组件加载。
 * 
 * 使用方式一：作为 Vue 组件使用
 * import { LogoIcon } from '@utils/svg-icons.js' 
 * 
 * <template>
 *   <LogoIcon class="custom-icon" />
 * </template>
 * 
 * <style scoped>
 * .custom-icon {
 *    width: 24px;
 *    color: #ff0000;
 *   }
 * </style>
 *
 * 使用方式二：作为 URL 路径使用
 * import { LogoIcon } from '@utils/svg-icons.js' 
 * 
 * <template>
 *   <img :src="LogoIcon" alt="Icon">
 * </template>
 */

import LoginBackgroundIcon from "@/assets/svg/login-background.svg?component";

export { LoginBackgroundIcon };
