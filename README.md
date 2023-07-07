# Vitality
<p align="center">	
	<a target="_blank" href="https://search.maven.org/search?q=g:%22com.github.mengweijin%22%20AND%20a:%22vitality-parent%22">
		<img src="https://img.shields.io/maven-central/v/com.github.mengweijin/vitality-parent" />
	</a>
	<a target="_blank" href="https://github.com/mengweijin/quickboot/blob/master/LICENSE">
		<img src="https://img.shields.io/badge/license-Apache2.0-blue.svg" />
	</a>
	<a target="_blank" href="https://www.oracle.com/technetwork/java/javase/downloads/index.html">
		<img src="https://img.shields.io/badge/JDK-17-green.svg" />
	</a>
	<a target="_blank" href="https://gitee.com/mengweijin/vitality/stargazers">
		<img src="https://gitee.com/mengweijin/vitality/badge/star.svg?theme=dark" alt='gitee star'/>
	</a>
	<a target="_blank" href='https://github.com/mengweijin/vitality'>
		<img src="https://img.shields.io/github/stars/mengweijin/vitality.svg?style=social" alt="github star"/>
	</a>
</p>

## 介绍
基于 SpringBoot 3+、sa-token、Layui 2.8+ **可前后端分离** 的后台管理系统，适合于一个人既是一个团队的小伙伴们，可以用于常见的的 Web 应用程序，比如网站管理后台等。

### 内置功能
- 系统管理
  - 用户管理：系统用户的管理，以及用户所拥有的菜单及按钮权限配置。
  - 角色管理：角色配置，以及角色所拥有的菜单及按钮权限配置。
  - 菜单管理：配置系统菜单、按钮基本信息及权限编码。
  - 部门管理：配置系统组织机构，以及部门所拥有的菜单及按钮权限配置。
  - 岗位管理：系统用户所担任的岗位，以及岗位所拥有的菜单及按钮权限配置。
  - 字典管理：对系统中经常使用的一些较为固定的数据进行维护。
  - 配置管理：对系统动态配置常用参数。
  - 公告管理：系统公告信息的发布维护。
- 系统监控 
  - 应用监控：监视当前系统的系统信息、CPU、内存、磁盘、JVM信息等。
  - 在线用户：当前系统中活跃用户状态监控及踢人下线。
  - 登录日志：系统登录日志记录和查询。
  - 操作日志：系统正常操作日志记录和查询；
  - 错误日志：系统异常信息日志记录和查询。
- 开发工具
  - 代码生成器：前后端代码的生成（java、html、sql、脚本）支持代码直接生成到工程目录下。
  - 表单构建器：拖动表单元素生成相应的HTML代码。
  - 接口文档：后台接口文档。
  - 数据列表：系统后台支撑的一些不能在前端直接查看的一些表的数据，方面某些问题定位时，可以查看，就不用进入数据库查看了。
    - 通知数据：系统通知信息的查看、发布维护。
    - 文件数据：系统上传文件后，产生的记录列表。

### 演示图
|                                                    演示图【列一】 | 演示图【列二】                                                         |    
|-----------------------------------------------------------:|:----------------------------------------------------------------|
|            ![用户管理](docs/readme/images/user management.png) | ![用户详情](docs/readme/images/user detail.png)                     | 
|            ![角色管理](docs/readme/images/role management.png) | ![角色授权](docs/readme/images/role authorization.png)              | 
|            ![菜单管理](docs/readme/images/menu management.png) | ![部门管理](docs/readme/images/dept management.png)                 | 
|            ![岗位管理](docs/readme/images/post management.png) | ![字典管理](docs/readme/images/dict management.png)                 | 
|          ![公告编辑](docs/readme/images/announcement edit.png) | ![应用监控](docs/readme/images/monitor server.png)                  | 
|        ![在线用户](docs/readme/images/monitor online user.png) | ![登录日志](docs/readme/images/monitor log login.png)               | 
|      ![操作日志](docs/readme/images/monitor log operation.png) | ![错误日志](docs/readme/images/monitor log error.png)               | 
| ![img.png](docs/readme/images/dev tool code generator.png) | ![代码生成器](docs/readme/images/dev tool code generator detail.png) | 


















