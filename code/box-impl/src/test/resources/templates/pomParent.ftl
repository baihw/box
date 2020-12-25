<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>${groupId}</groupId>
    <artifactId>${artifactId}-parent</artifactId>
    <version>${version}</version>
    <name>${r'${project.artifactId}'}</name>
    <packaging>pom</packaging>

    <modules>
        <!-- 模块1接口 -->
        <module>api</module>
        <!-- 模块1实现 -->
        <module>impl</module>
        <!-- 项目路由 -->
        <module>route</module>
        <!-- 项目web方式启动入口 -->
        <module>web</module>
    </modules>

    <properties>
        <encoding.default>UTF-8</encoding.default>
        <jdk.default>1.8</jdk.default>
        <!-- 项目基础配置 -->
        <project.build.sourceEncoding>${r'${encoding.default}'}</project.build.sourceEncoding>
        <project.reporting.outputEncoding>${r'${encoding.default}'}</project.reporting.outputEncoding>
        <maven.compiler.source>${r'${jdk.default}'}</maven.compiler.source>
        <maven.compiler.target>${r'${jdk.default}'}</maven.compiler.target>
        <java.version>${r'${jdk.default}'}</java.version>

        <!-- 主要依赖库版本 -->
        <box.version>0.2.8</box.version>
        <spring.boot.version>2.1.11.RELEASE</spring.boot.version>
        <druid.version>1.1.20</druid.version>
        <h2.version>1.4.199</h2.version>
        <mysql.version>5.1.48</mysql.version>
        <oracle.version>11.2.0.3</oracle.version>
        <ehcache.version>2.10.3</ehcache.version>
        <shiro.version>1.3.2</shiro.version>
        <servlet-api.version>3.1.0</servlet-api.version>

        <!-- 测试依赖库版本 -->
        <junit.version>4.12</junit.version>
        <hamcrest.version>1.3</hamcrest.version>
        <mockito.version>2.28.2</mockito.version>
    </properties>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>${spring.boot.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <dependency>
                <groupId>javax.servlet</groupId>
                <artifactId>javax.servlet-api</artifactId>
                <version>${servlet-api.version}</version>
                <scope>provided</scope>
                <optional>true</optional>
            </dependency>
            <!-- project -->
            <dependency>
                <groupId>com.zhonglingyi.tm</groupId>
                <artifactId>api</artifactId>
                <version>${project.version}</version>
            </dependency>
            <dependency>
                <groupId>com.zhonglingyi.tm</groupId>
                <artifactId>impl</artifactId>
                <version>${project.version}</version>
            </dependency>

            <dependency>
                <groupId>com.zhonglingyi.tm</groupId>
                <artifactId>route</artifactId>
                <version>${project.version}</version>
            </dependency>
            <!-- box -->
            <dependency>
                <groupId>com.wee0.box</groupId>
                <artifactId>box-api</artifactId>
                <version>${box.version}</version>
            </dependency>
            <dependency>
                <groupId>com.wee0.box</groupId>
                <artifactId>box-impl</artifactId>
                <version>${box.version}</version>
            </dependency>
            <dependency>
                <groupId>com.wee0.box</groupId>
                <artifactId>box-spring-boot</artifactId>
                <version>${box.version}</version>
            </dependency>
        </dependencies>
    </dependencyManagement>

</project>
