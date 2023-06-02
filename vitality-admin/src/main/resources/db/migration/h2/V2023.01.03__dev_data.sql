--
INSERT INTO VTL_MESSAGE VALUES(1, 'NOTICE', 'https://gw.alipayobjects.com/zos/rmsportal/ThXAXghbEsBCCSDihZxY.png', '你收到了 14 份新周报', '这是消息内容。', 1, 0, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MESSAGE VALUES(2, 'NOTICE', 'https://gw.alipayobjects.com/zos/rmsportal/OKJXDXrmkNshAMvwtvhu.png', '曲妮妮 已通过第三轮面试', '这是消息内容。', 1, 0, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MESSAGE VALUES(3, 'NOTICE', 'https://gw.alipayobjects.com/zos/rmsportal/kISTdvpyTAhtGxpovNWd.png', '可以区分多种通知类型', '这是消息内容。', 1, 0, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MESSAGE VALUES(4, 'NOTICE', 'https://gw.alipayobjects.com/zos/rmsportal/GvqBnKhFgObvnSGkDsje.png', '左侧图标用于区分不同的类型', '这是消息内容。', 1, 0, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MESSAGE VALUES(5, 'NOTICE', 'https://gw.alipayobjects.com/zos/rmsportal/ThXAXghbEsBCCSDihZxY.png', '内容不要超过两行字', '这是消息内容。', 1, 0, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());


-- TYPE：菜单类型。{ 0=目录; 1=菜单; 2=按钮; 3=其它 }
-- OPEN_TYPE：TYPE 为 1 时生效，{ _iframe：正常打开；_blank：新建浏览器标签页 }
INSERT INTO VTL_MENU VALUES(100000, 0, '工作空间', 0, 'sys:workspace', 100000, 'layui-icon layui-icon-console', null, null, 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MENU VALUES(10000010, 100000, '控制后台', 1, 'sys:workspace:control:background', 1, null, 'view/console/console1.html', '_iframe', 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MENU VALUES(10000020, 100000, '数据分析', 1, 'sys:workspace:data:analysis', 2, null, 'view/console/console2.html', '_iframe', 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MENU VALUES(10000030, 100000, '百度一下', 1, 'sys:workspace:baidu', 3, null, 'https://www.baidu.com', '_iframe', 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MENU VALUES(10000040, 100000, '主题预览', 1, 'sys:workspace:themepreview', 4, null, 'view/system/theme.html', '_iframe', 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MENU VALUES(10000050, 100000, '酸爽翻倍', 1, 'sys:workspace:double:acid', 5, null, 'view/document/core.html', '_iframe', 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());

INSERT INTO VTL_MENU VALUES(200000, 0, '常用组件', 0, 'sys:common:component', 200000, 'layui-icon layui-icon-component', null, null, 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MENU VALUES(20000010, 200000, '基础组件', 0, 'sys:common:component:base', 1, null, null, null, 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MENU VALUES(20000010010, 20000010, '功能按钮', 1, 'sys:common:component:base:function:button', 1, null, 'view/document/button.html', '_iframe', 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MENU VALUES(20000010020, 20000010, '表单集合', 1, 'sys:common:component:base:form:collection', 2, null, 'view/document/form.html', '_iframe', 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MENU VALUES(20000010030, 20000010, '字体图标', 1, 'sys:common:component:base:font:icon', 3, null, 'view/document/icon.html', '_iframe', 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MENU VALUES(20000010040, 20000010, '多选下拉', 1, 'sys:common:component:base:double:multiselect', 4, null, 'view/document/select.html', '_iframe', 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MENU VALUES(20000010050, 20000010, '动态标签', 1, 'sys:common:component:base:double:dynamic:label', 5, null, 'view/document/tag.html', '_iframe', 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MENU VALUES(20000020, 200000, '进阶组件', 0, 'sys:common:component:advanced', 2, null, null, null, 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MENU VALUES(20000020010, 20000020, '数据表格', 1, 'sys:common:component:advanced:data:table', 1, null, 'view/document/table.html', '_iframe', 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MENU VALUES(20000020020, 20000020, '分步表单', 1, 'sys:common:component:advanced:step:form', 2, null, 'view/document/step.html', '_iframe', 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MENU VALUES(20000020030, 20000020, '树形表格', 1, 'sys:common:component:advanced:tree:table', 3, null, 'view/document/treetable.html', '_iframe', 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MENU VALUES(20000020040, 20000020, '树状结构', 1, 'sys:common:component:advanced:tree', 4, null, 'view/document/dtree.html', '_iframe', 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MENU VALUES(20000020050, 20000020, '文本编辑', 1, 'sys:common:component:advanced:tinymce', 5, null, 'view/document/tinymce.html', '_iframe', 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MENU VALUES(20000020060, 20000020, '卡片组件', 1, 'sys:common:component:advanced:card', 6, null, 'view/document/card.html', '_iframe', 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MENU VALUES(20000030, 200000, '弹层组件', 0, 'sys:common:component:layer', 3, null, null, null, 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MENU VALUES(20000030010, 20000030, '抽屉组件', 1, 'sys:common:component:layer:drawer', 1, null, 'view/document/drawer.html', '_iframe', 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MENU VALUES(20000030020, 20000030, '消息通知 (过时)', 1, 'sys:common:component:layer:notice', 2, null, 'view/document/notice.html', '_iframe', 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MENU VALUES(20000030030, 20000030, '消息通知 (新增)', 1, 'sys:common:component:layer:toast', 3, null, 'view/document/toast.html', '_iframe', 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MENU VALUES(20000030040, 20000030, '加载组件', 1, 'sys:common:component:layer:loading', 4, null, 'view/document/loading.html', '_iframe', 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MENU VALUES(20000030050, 20000030, '弹层组件', 1, 'sys:common:component:layer:popup', 5, null, 'view/document/popup.html', '_iframe', 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MENU VALUES(20000040, 200000, '高级组件', 0, 'sys:common:component:high', 4, null, null, null, 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MENU VALUES(20000040010, 20000040, '多选项卡', 1, 'sys:common:component:high:tab', 1, null, 'view/document/tab.html', '_iframe', 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MENU VALUES(20000040020, 20000040, '数据菜单', 1, 'sys:common:component:high:menu', 2, null, 'view/document/menu.html', '_iframe', 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MENU VALUES(20000050, 200000, '其他组件', 0, 'sys:common:component:other', 5, null, null, null, 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MENU VALUES(20000050010, 20000050, '哈希加密', 1, 'sys:common:component:other:encrypt', 1, null, 'view/document/encrypt.html', '_iframe', 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MENU VALUES(20000050020, 20000050, '图标选择', 1, 'sys:common:component:other:iconPicker', 2, null, 'view/document/iconPicker.html', '_iframe', 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MENU VALUES(20000050030, 20000050, '省市级联', 1, 'sys:common:component:other:area', 3, null, 'view/document/area.html', '_iframe', 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MENU VALUES(20000050040, 20000050, '数字滚动', 1, 'sys:common:component:other:count', 4, null, 'view/document/count.html', '_iframe', 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MENU VALUES(20000050050, 20000050, '顶部返回', 1, 'sys:common:component:other:topBar', 5, null, 'view/document/topBar.html', '_iframe', 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MENU VALUES(20000050060, 20000050, '水印组件', 1, 'sys:common:component:other:watermark', 6, null, 'view/document/watermark.html', '_iframe', 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MENU VALUES(20000050070, 20000050, '全屏组件', 1, 'sys:common:component:other:fullscreen', 7, null, 'view/document/fullscreen.html', '_iframe', 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MENU VALUES(20000050080, 20000050, '汽泡组件', 1, 'sys:common:component:other:popover', 8, null, 'view/document/popover.html', '_iframe', 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());

INSERT INTO VTL_MENU VALUES(300000, 0, '结果页面', 0, 'sys:result', 300000, 'layui-icon layui-icon-auz', null, null, 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MENU VALUES(30000010, 300000, '成功', 1, 'sys:result:success', 1, null, 'view/result/success.html', '_iframe', 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MENU VALUES(30000020, 300000, '失败', 1, 'sys:result:error', 2, null, 'view/result/error.html', '_iframe', 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());

INSERT INTO VTL_MENU VALUES(400000, 0, '错误页面', 0, 'sys:error', 400000, 'layui-icon layui-icon-face-cry', null, null, 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MENU VALUES(40000010, 400000, '403', 1, 'sys:error:403', 1, null, 'view/error/403.html', '_iframe', 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MENU VALUES(40000020, 400000, '404', 1, 'sys:error:404', 2, null, 'view/error/404.html', '_iframe', 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MENU VALUES(40000030, 400000, '500', 1, 'sys:error:500', 3, null, 'view/error/500.html', '_iframe', 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());

INSERT INTO VTL_MENU VALUES(500000, 0, '系统管理', 0, 'sys:manage', 500000, 'layui-icon layui-icon-set-fill', null, null, 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MENU VALUES(50000010, 500000, '用户管理', 1, 'sys:manage:user', 1, null, 'view/system/user.html', '_iframe', 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MENU VALUES(50000020, 500000, '角色管理', 1, 'sys:manage:role', 2, null, 'view/system/role.html', '_iframe', 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MENU VALUES(50000030, 500000, '权限管理', 1, 'sys:manage:power', 3, null, 'view/system/power.html', '_iframe', 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MENU VALUES(50000040, 500000, '部门管理', 1, 'sys:manage:deptment', 4, null, 'view/system/deptment.html', '_iframe', 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MENU VALUES(50000050, 500000, '行为日志', 1, 'sys:manage:log', 5, null, 'view/system/log.html', '_iframe', 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MENU VALUES(50000060, 500000, '数据字典', 1, 'sys:manage:dict', 6, null, 'view/system/list.html', '_iframe', 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());

INSERT INTO VTL_MENU VALUES(600000, 0, '常用页面', 0, 'sys:commonPage', 600000, 'layui-icon layui-icon-template-1', null, null, 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MENU VALUES(60000010, 600000, '登录页面', 1, 'sys:commonPage:login', 1, null, 'login.html', '_iframe', 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MENU VALUES(60000020, 600000, '空白页面', 1, 'sys:commonPage:space', 2, null, 'view/system/space.html', '_iframe', 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());

INSERT INTO VTL_MENU VALUES(700000, 0, '数据图表', 0, 'sys:echarts', 700000, 'layui-icon layui-icon-chart', null, null, 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MENU VALUES(70000010, 700000, '折线图', 1, 'sys:echarts:line', 1, null, 'view/echarts/line.html', '_iframe', 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MENU VALUES(70000020, 700000, '柱状图', 1, 'sys:echarts:column', 2, null, 'view/echarts/column.html', '_iframe', 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());

INSERT INTO VTL_MENU VALUES(800000, 0, '开发工具', 0, 'sys:devTools', 800000, 'layui-icon layui-icon-util', null, null, 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MENU VALUES(80000010, 800000, '表单构建', 1, 'sys:devTools:form', 1, null, 'component/code/index.html', '_iframe', 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MENU VALUES(80000020, 800000, '代码生成器', 1, 'sys:devTools:generator', 2, null, 'views/generator/index.html', '_iframe', 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_MENU VALUES(80000030, 800000, '代码生成器（文档）', 1, 'sys:devTools:generatorDocment', 3, null, 'views/generator/document.html', '_iframe', 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());


INSERT INTO VTL_DICT_TYPE (ID, NAME, TYPE_CODE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) VALUES (99999, '是或否', 'yes_or_no', null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_DICT_DATA (ID, TYPE_CODE, DATA_CODE, LABEL, SEQ, DEFAULT_SELECTED, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) VALUES (999990001, 'yes_or_no', 'yes', '是', 1, 0, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_DICT_DATA (ID, TYPE_CODE, DATA_CODE, LABEL, SEQ, DEFAULT_SELECTED, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) VALUES (999990002, 'yes_or_no', 'no', '否', 3, 0, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
INSERT INTO VTL_DICT_DATA (ID, TYPE_CODE, DATA_CODE, LABEL, SEQ, DEFAULT_SELECTED, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) VALUES (999990003, 'yes_or_no', 'unknow', '未知', 2, 1, 0, null, 1, CURRENT_TIMESTAMP(), 1, CURRENT_TIMESTAMP());
