<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>com.zhonglingyi.tm</groupId>
        <artifactId>parent</artifactId>
        <version>0.1.0</version>
    </parent>

    <artifactId>impl</artifactId>
    <name>${project.artifactId}</name>
    <packaging>jar</packaging>

    <dependencies>
        <dependency>
            <groupId>com.wee0.box</groupId>
            <artifactId>box-api</artifactId>
        </dependency>
        <dependency>
            <groupId>com.zhonglingyi.tm</groupId>
            <artifactId>api</artifactId>
        </dependency>
    </dependencies>

</project>
