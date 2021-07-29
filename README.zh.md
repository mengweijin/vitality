# quickboot

Language: [English](README.md)

<p align="center">	
	<a target="_blank" href="https://search.maven.org/search?q=g:%22com.github.mengweijin%22%20AND%20a:%22quickboot-parent%22">
		<img src="https://img.shields.io/maven-central/v/com.github.mengweijin/quickboot-parent" />
	</a>
	<a target="_blank" href="https://github.com/mengweijin/quickboot/blob/master/LICENSE">
		<img src="https://img.shields.io/badge/license-Apache2.0-blue.svg" />
	</a>
	<a target="_blank" href="https://www.oracle.com/technetwork/java/javase/downloads/index.html">
		<img src="https://img.shields.io/badge/JDK-8+-green.svg" />
	</a>
	<a target="_blank" href="https://gitee.com/mengweijin/quickboot/stargazers">
		<img src="https://gitee.com/mengweijin/quickboot/badge/star.svg?theme=dark" alt='gitee star'/>
	</a>
	<a target="_blank" href='https://github.com/mengweijin/quickboot'>
		<img src="https://img.shields.io/github/stars/mengweijin/quickboot.svg?style=social" alt="github star"/>
	</a>
</p>

## 介绍
快速搭建springboot 2.0项目，整合和配置常用的模块功能，节省搭建项目工程的时间。

## 模块文档
- [quickboot-parent document](README.md)
    - [quickboot-framework document](doc/quickboot-framework.zh.md)
    - [quickboot-jpa document](doc/quickboot-jpa.zh.md)
    - [quickboot-mybatis-plus document](doc/quickboot-mybatis-plus.zh.md)
    - [quickboot-web document](doc/quickboot-web.zh.md)

## 如何使用? 
以使用quickboot-mybatis-plus为例。Maven: 
~~~~xml
<project>
	<parent>
		<artifactId>quickboot-parent</artifactId>
		<groupId>com.github.mengweijin</groupId>
		<version>Latest version</version>
	</parent>
    
    <dependencies>
      <dependency>
        <groupId>com.github.mengweijin</groupId>
        <artifactId>quickboot-mybatis-plus</artifactId>
      </dependency>
    </dependencies>
</project>
~~~~

其他参考各模块文档。

## 依赖包文档
|依赖|文档|代码|最新版本|
|---:|:---|:---|:---|
|Spring-Boot|[Spring-Boot](https://spring.io/projects/spring-boot)|[Github](https://github.com/spring-projects/spring-boot)|<a target="_blank" href="https://search.maven.org/search?q=g:%22org.springframework.boot%22%20AND%20a:%22spring-boot-dependencies%22"><img src="https://img.shields.io/maven-central/v/org.springframework.boot/spring-boot-dependencies"/></a>|
|Hutool|[Hutool](https://hutool.cn/)|[Gitee](https://gitee.com/dromara/hutool/) <br> [Github](https://github.com/dromara/hutool/)|<a target="_blank" href="https://search.maven.org/search?q=g:%22cn.hutool%22%20AND%20a:%22hutool-all%22"><img src="https://img.shields.io/maven-central/v/cn.hutool/hutool-all"/></a>|
|jsoup|[jsoup](https://jsoup.org/)|[Github](https://github.com/jhy/jsoup)|<a target="_blank" href="https://search.maven.org/search?q=g:%22org.jsoup%22%20AND%20a:%22jsoup%22"><img src="https://img.shields.io/maven-central/v/org.jsoup/jsoup"/></a>|
|mybatis-plus|[mybatis-plus](https://baomidou.com/)|[Gitee](https://gitee.com/baomidou/mybatis-plus) <br> [Github](https://github.com/baomidou/mybatis-plus)|<a target="_blank" href="https://search.maven.org/search?q=g:%22com.baomidou%22%20AND%20a:%22mybatis-plus-boot-starter%22"><img src="https://img.shields.io/maven-central/v/com.baomidou/mybatis-plus-boot-starter"/></a>|					
|p6spy|[p6spy](https://p6spy.readthedocs.io/en/latest/integration.html#spring-boot-autoconfiguration)|[Github](https://github.com/p6spy/p6spy)|<a target="_blank" href="https://search.maven.org/search?q=g:%22p6spy%22%20AND%20a:%22p6spy%22"><img src="https://img.shields.io/maven-central/v/p6spy/p6spy"/></a>|
|p6spy-spring-boot-starter| |[Github](https://github.com/gavlyukovskiy/spring-boot-data-source-decorator)|<a target="_blank" href="https://search.maven.org/search?q=g:%22com.github.gavlyukovskiy%22%20AND%20a:%22p6spy-spring-boot-starter%22"><img src="https://img.shields.io/maven-central/v/com.github.gavlyukovskiy/p6spy-spring-boot-starter"/></a>|
|knife4j-spring-boot-starter|[knife4j](https://doc.xiaominfo.com/knife4j/documentation/)|[Gitee](https://gitee.com/xiaoym/knife4j)|<a target="_blank" href="https://search.maven.org/search?q=g:%22com.github.xiaoymin%22%20AND%20a:%22knife4j-spring-boot-starter%22"><img src="https://img.shields.io/maven-central/v/com.github.xiaoymin/knife4j-spring-boot-starter"/></a>|





