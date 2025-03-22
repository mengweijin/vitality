import "layui/dist/css/layui.css";
import "layui";

import "@/styles/index.css";
import { ajaxSetup } from "@/scripts/ajaxSetup.js";
import { admin } from "@/scripts/admin.js";

ajaxSetup();

admin.isLogin() ? admin.toAdmin() : admin.toLogin();
