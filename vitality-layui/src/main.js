import "layui/dist/css/layui.css";
import "layui";

import "@/styles/index.css";
import { ajaxSetup } from "@/scripts/jquery-setting.js";

layui.use(function () {
  ajaxSetup(layui.jquery, layui.layer);
});
