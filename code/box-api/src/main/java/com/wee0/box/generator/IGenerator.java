package com.wee0.box.generator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2020/9/5 7:02
 * @Description 生成器
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface IGenerator {

    /**
     * 默认的实现类名称
     */
    String DEF_IMPL_CLASS_NAME = "com.wee0.box.generator.impl.SimpleGenerator";

    /**
     * 根据项目配置信息，生成项目文件。
     *
     * @param projectConfig 配置信息输入流
     * @param directory     生成项目文件存放目录
     * @throws IOException 数据访问异常
     */
    void generateProject(InputStream projectConfig, File directory) throws IOException;

}
