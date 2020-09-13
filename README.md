# quickboot

Language: [中文](README.zh.md)

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

## Introduction
 Quickly build the SpringBoot 2.0 project, integrate and configure common module functions, and save time to build the project.

## Module Documentation
- [quickboot-parent document](README.md)
    - [quickboot-framework document](doc/quickboot-framework.zh.md)
    - [quickboot-jpa document](doc/quickboot-jpa.zh.md)
    - [quickboot-mybatis-plus document](doc/quickboot-mybatis-plus.zh.md)
    - [quickboot-web document](doc/quickboot-web.zh.md)

## How to use it?
Take the example of using quickboot-mybatis-plus. Maven: 
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
            <version>${quickboot.version}</version>
        </dependency>
    </dependencies>
</project>
~~~~

Add it to your startup class: 
~~~~
@SpringBootApplication(scanBasePackages = {"your package path", "com.github.mengweijin"})
~~~~
