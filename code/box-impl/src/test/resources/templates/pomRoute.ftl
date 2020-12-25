<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>com.zhonglingyi.tm</groupId>
        <artifactId>parent</artifactId>
        <version>0.1.0</version>
    </parent>

    <artifactId>route</artifactId>
    <name>${project.artifactId}</name>
    <packaging>jar</packaging>

    <dependencies>
        <dependency>
            <groupId>com.zhonglingyi.tm</groupId>
            <artifactId>api</artifactId>
        </dependency>
        <dependency>
            <groupId>com.zhonglingyi.tm</groupId>
            <artifactId>impl</artifactId>
        </dependency>
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>org.hibernate.validator</groupId>
            <artifactId>hibernate-validator</artifactId>
        </dependency>
    </dependencies>
</project>
