//package com.wee0.box.generator.impl;
//
//import com.wee0.box.generator.IGenerator;
//import org.yaml.snakeyaml.Yaml;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.ObjectStreamException;
//import java.util.Map;
//
///**
// * @author <a href="78026399@qq.com">白华伟</a>
// * @CreateDate 2020/9/5 7:16
// * @Description 一个简单的生成器实现
// * <pre>
// * 补充说明
// * </pre>
// **/
//public class SimpleGenerator implements IGenerator {
//
//    @Override
//    public void generateProject(InputStream projectConfig, File directory) throws IOException {
//        Yaml _yml = new Yaml();
//        Map<String, Object> _ymlObj = _yml.load(projectConfig);
//        System.out.println("_ymlObj:" + _ymlObj);
//    }
//
//    /************************************************************
//     ************* 单例样板代码。
//     ************************************************************/
//    private SimpleGenerator() {
//        if (null != SimpleGeneratorHolder._INSTANCE) {
//            // 防止使用反射API创建对象实例。
//            throw new IllegalStateException("that's not allowed!");
//        }
//    }
//
//    // 当前对象唯一实例持有者。
//    private static final class SimpleGeneratorHolder {
//        private static final SimpleGenerator _INSTANCE = new SimpleGenerator();
//    }
//
//    // 防止使用反序列化操作获取多个对象实例。
//    private Object readResolve() throws ObjectStreamException {
//        return SimpleGeneratorHolder._INSTANCE;
//    }
//
//    /**
//     * 获取当前对象唯一实例。
//     *
//     * @return 当前对象唯一实例
//     */
//    public static SimpleGenerator me() {
//        return SimpleGeneratorHolder._INSTANCE;
//    }
//
//
//}
