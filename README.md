# Vita（微塔）

<p align="center">	
	<a target="_blank" href="https://search.maven.org/search?q=g:%22com.github.mengweijin%22%20AND%20a:%22vita-parent%22">
		<img src="https://img.shields.io/maven-central/v/com.github.mengweijin/vita-parent" />
	</a>
	<a target="_blank" href="https://github.com/mengweijin/vita/blob/master/LICENSE">
		<img src="https://img.shields.io/badge/license-Apache2.0-blue.svg" />
	</a>
	<a target="_blank" href="https://www.oracle.com/technetwork/java/javase/downloads/index.html">
		<img src="https://img.shields.io/badge/JDK-17-green.svg" />
	</a>
	<a target="_blank" href="https://gitee.com/mengweijin/vita/stargazers">
		<img src="https://gitee.com/mengweijin/vita/badge/star.svg?theme=dark" alt='gitee star'/>
	</a>
	<a target="_blank" href='https://github.com/mengweijin/vita'>
		<img src="https://img.shields.io/github/stars/mengweijin/vita.svg?style=social" alt="github star"/>
	</a>
</p>

<p align="center">
    <a target="_blank" href="https://sonarcloud.io/summary/overall?id=mengweijin_vita&branch=master">
		<img src="https://sonarcloud.io/api/project_badges/quality_gate?project=mengweijin_vita&branch=master" />
	</a>
</p>

<p align="center">
	<a target="_blank" href="https://sonarcloud.io/summary/overall?id=mengweijin_vita&branch=master">
		<img src="https://sonarcloud.io/api/project_badges/measure?project=mengweijin_vita&metric=alert_status&branch=master" />
	</a>
    <a target="_blank" href="https://sonarcloud.io/summary/overall?id=mengweijin_vita&branch=master">
		<img src="https://sonarcloud.io/api/project_badges/measure?project=mengweijin_vita&metric=reliability_rating&branch=master" />
	</a>
    <a target="_blank" href="https://sonarcloud.io/summary/overall?id=mengweijin_vita&branch=master">
		<img src="https://sonarcloud.io/api/project_badges/measure?project=mengweijin_vita&metric=security_rating&branch=master" />
	</a>
    <a target="_blank" href="https://sonarcloud.io/summary/overall?id=mengweijin_vita&branch=master">
		<img src="https://sonarcloud.io/api/project_badges/measure?project=mengweijin_vita&metric=sqale_rating&branch=master" />
	</a>
    <a target="_blank" href="https://sonarcloud.io/summary/overall?id=mengweijin_vita&branch=master">
		<img src="https://sonarcloud.io/api/project_badges/measure?project=mengweijin_vita&metric=vulnerabilities&branch=master" />
	</a>
    <a target="_blank" href="https://sonarcloud.io/summary/overall?id=mengweijin_vita&branch=master">
		<img src="https://sonarcloud.io/api/project_badges/measure?project=mengweijin_vita&metric=bugs&branch=master" />
	</a>
    <a target="_blank" href="https://sonarcloud.io/summary/overall?id=mengweijin_vita&branch=master">
		<img src="https://sonarcloud.io/api/project_badges/measure?project=mengweijin_vita&metric=ncloc&branch=master" />
	</a>
    <a target="_blank" href="https://sonarcloud.io/summary/overall?id=mengweijin_vita&branch=master">
		<img src="https://sonarcloud.io/api/project_badges/measure?project=mengweijin_vita&metric=duplicated_lines_density&branch=master" />
	</a>
    <a target="_blank" href="https://sonarcloud.io/summary/overall?id=mengweijin_vita&branch=master">
		<img src="https://sonarcloud.io/api/project_badges/measure?project=mengweijin_vita&metric=code_smells&branch=master" />
	</a>
</p>

## 介绍

**Vita（中文名：微塔）**：是一款**轻量级快速开发平台应用系统**。

基于 SpringBoot 3、sa-token、mybatis-plus、vite、npm、layui、jquery 等技术，**前后端分离开发**，不依赖任何第三方服务。

有时候我们就想做一个简单的东西，采用已有的开源框架却要依赖一大堆东西，和很复杂的配置文件，自己搭建又太耗费时间，**真的太麻烦了！**

于是，就有了 **Vita**，它可以帮你节省很多时间和精力，非常适合一个人即一个团队的工作环境。

### 在线演示
|        版本         |            演示链接            |
|:-----------------:|:--------------------------:|
|    Vita Vue 版     | http://124.70.184.112:8002 |
|   Vita Layui 版    | http://124.70.184.112:8001 |

#### 最简单的启动

你只需要一个 JDK 17 的运行环境，然后直接启动即可！

```shell
java -jar vita-admin.jar
```

浏览器访问：http://localhost:8080

### 系统功能

- 我的消息：系统消息列表查看和维护。
- 系统管理
  - 菜单管理：配置系统菜单、按钮基本信息及权限编码。
  - 部门管理：配置系统组织机构。
  - 岗位管理：系统用户所担任的岗位。
  - 用户管理：系统用户的管理。
  - 角色管理：角色管理，及其所拥有的菜单及按钮权限配置。
  - 分类管理：树状分类基础数据维护。
  - 字典管理：枚举字典值配置和管理。
  - 配置管理：对系统动态配置常用参数。
  - 文件管理：对系统上传的文件进行管理。
  - 通知公告：系统通知公告信息的发布维护。
- 系统监控 
  - 应用监控：监视当前系统的系统信息、CPU、内存、磁盘、JVM信息等。
  - 缓存监控：查看系统实时缓存数据。
  - 在线用户：当前系统中活跃用户状态监控及踢人下线。
  - 登录日志：系统登录日志记录和查询。
  - 操作日志：系统正常操作日志记录和查询；
  - 错误日志：系统异常信息日志记录和查询。
- 开发工具
  - 代码生成器：前后端代码的生成（java、vue、js、sql、脚本）支持代码直接下载。
  - 接口文档：后台接口文档。

### 开发功能

主要包含用户-部门-岗位-角色-菜单-权限管理、代码生成、数据脱敏、字典翻译、接口限流、日志管理、系统监控、缓存过期、接口防抖等功能。

### 演示图
|                                    |                                    |    
|-----------------------------------:|:-----------------------------------|
| ![image](docs/readme/images/1.png) | ![image](docs/readme/images/2.png) | 
| ![image](docs/readme/images/3.png) | ![image](docs/readme/images/4.png) | 
| ![image](docs/readme/images/5.png) | ![image](docs/readme/images/6.png) | 
| ![image](docs/readme/images/7.png) | ![image](docs/readme/images/8.png) |

## ⭐Star Vita on GitHub

[![Stargazers over time](https://starchart.cc/mengweijin/vita.svg)](https://starchart.cc/mengweijin/vita)
