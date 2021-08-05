<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>com.zhonglingyi.tm</groupId>
        <artifactId>parent</artifactId>
        <version>0.1.0</version>
    </parent>

    <artifactId>api</artifactId>
    <name>${project.artifactId}</name>
    <packaging>jar</packaging>

    <dependencies>
        <dependency>
            <groupId>com.wee0.box</groupId>
            <artifactId>box-api</artifactId>
        </dependency>
    </dependencies>
    <!--    <build>-->
    <!--        <plugins>-->
    <!--            <plugin>-->
    <!--                <groupId>org.mybatis.generator</groupId>-->
    <!--                <artifactId>mybatis-generator-maven-plugin</artifactId>-->
    <!--                <version>1.3.2</version>-->
    <!--                <configuration>-->
    <!--                    <configurationFile>src/main/resources/generatorConfig.xml</configurationFile>-->
    <!--                    <verbose>true</verbose>-->
    <!--                    <overwrite>true</overwrite>-->
    <!--                </configuration>-->
    <!--                <executions>-->
    <!--                    <execution>-->
    <!--                        <id>Generate MyBatis Artifacts</id>-->
    <!--                        <goals>-->
    <!--                            <goal>generator</goal>-->
    <!--                        </goals>-->
    <!--                    </execution>-->
    <!--                </executions>-->
    <!--                <dependencies>-->
    <!--                    <dependency>-->
    <!--                        <groupId>org.mybatis.generator</groupId>-->
    <!--                        <artifactId>mybatis-generator-core</artifactId>-->
    <!--                        <version>1.3.2</version>-->
    <!--                    </dependency>-->
    <!--                </dependencies>-->
    <!--            </plugin>-->
    <!--        </plugins>-->
    <!--    </build>-->
</project>
