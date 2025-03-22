import "layui/dist/css/layui.css";
import "layui";

import "@/styles/index.css";
import { ajaxSetup } from "@/scripts/ajaxSetup.js";
import { admin } from "@/scripts/admin.js";

let $ = layui.jquery;
let layer = layui.layer;

ajaxSetup($, layer);

admin.isLogin() ? admin.toAdmin() : admin.toLogin();
