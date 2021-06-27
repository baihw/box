package com.wee0.box.model;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/5/23 7:11
 * @Description 功能描述
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SimpleTest {


    @BeforeClass
    public static void init() {
        System.out.println("init...");
    }

    @AfterClass
    public static void destroy() {
        System.out.println("destroy...");
    }

    @Test
    public void testSql() {
    }


}
