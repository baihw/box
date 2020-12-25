<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.11.RELEASE</version>
    </parent>

    <groupId>com.zhonglingyi.eeg</groupId>
    <artifactId>web</artifactId>
    <name>${project.artifactId}</name>
    <version>0.1.0</version>
    <packaging>jar</packaging>

    <properties>
        <encoding.default>UTF-8</encoding.default>
        <jdk.default>1.8</jdk.default>
        <!-- 项目基础配置 -->
        <project.build.sourceEncoding>${encoding.default}</project.build.sourceEncoding>
        <project.reporting.outputEncoding>${encoding.default}</project.reporting.outputEncoding>
        <maven.compiler.source>${jdk.default}</maven.compiler.source>
        <maven.compiler.target>${jdk.default}</maven.compiler.target>
        <java.version>${jdk.default}</java.version>

        <box.version>0.2.8</box.version>
        <mysql.version>5.1.48</mysql.version>
        <druid.version>1.1.20</druid.version>
        <mybatis.version>3.5.2</mybatis.version>
        <jedis.version>3.1.0</jedis.version>
        <shiro.version>1.3.2</shiro.version>
        <commons-fileupload.version>1.4</commons-fileupload.version>
        <freemarker.version>2.3.29</freemarker.version>
    </properties>


    <dependencies>
        <dependency>
            <groupId>com.zhonglingyi.tm</groupId>
            <artifactId>route</artifactId>
            <version>${project.version}</version>
        </dependency>
        <dependency>
            <groupId>com.wee0.box</groupId>
            <artifactId>box-spring-boot</artifactId>
            <version>${box.version}</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>${mysql.version}</version>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>${druid.version}</version>
        </dependency>
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>${mybatis.version}</version>
        </dependency>
        <dependency>
            <groupId>redis.clients</groupId>
            <artifactId>jedis</artifactId>
            <version>${jedis.version}</version>
        </dependency>
        <dependency>
            <groupId>org.apache.shiro</groupId>
            <artifactId>shiro-core</artifactId>
            <version>${shiro.version}</version>
        </dependency>
        <dependency>
            <groupId>org.freemarker</groupId>
            <artifactId>freemarker</artifactId>
            <version>${freemarker.version}</version>
        </dependency>
        <dependency>
            <groupId>commons-fileupload</groupId>
            <artifactId>commons-fileupload</artifactId>
            <version>${commons-fileupload.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <fork>true</fork>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
