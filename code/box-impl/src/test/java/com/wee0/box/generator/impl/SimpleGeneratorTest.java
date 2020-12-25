package com.wee0.box.generator.impl;

import com.wee0.box.BoxConfig;
import com.wee0.box.generator.IGenerator;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2020/9/5 9:02
 * @Description 功能描述
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SimpleGeneratorTest {

    private static IGenerator impl = SimpleGenerator.me();

    @BeforeClass
    public static void setup() {
        String tempDir = System.getProperty("java.io.tmpdir");
        System.out.println("OS current temporary directory is: " + tempDir);
    }

    @Test
    public void test1() {
        String _configFile = "config/project.yaml";
        File _projectDirectory = new File("D:\\baihw\\Desktop\\tmp\\src\\box-allInOne");
        try (InputStream _configIn = BoxConfig.impl().getResourceAsStream(_configFile)) {
            impl.generateProject(_configIn, _projectDirectory);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
