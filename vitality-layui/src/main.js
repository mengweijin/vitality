import "layui/dist/css/layui.css";
import "layui";

import "@/styles/index.css";
import { ajaxSetup } from "@/scripts/jquery-settings.js";
import { admin } from "@/scripts/components/admin.js";

layui.use(function () {
  let $ = layui.jquery;
  let layer = layui.layer;
  ajaxSetup($, layer);

  admin.render();
});
