///*
// * Copyright (c) 2019-present, wee0.com.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package com.wee0.box.testObjects;
//
//import com.wee0.box.log.ILogger;
//import com.wee0.box.log.LoggerFactory;
//import org.aopalliance.intercept.MethodInterceptor;
//import org.aopalliance.intercept.MethodInvocation;
//import org.springframework.aop.framework.ProxyFactory;
//import org.springframework.aop.support.AopUtils;
//import org.springframework.beans.factory.FactoryBean;
//
///**
// * @author <a href="78026399@qq.com">白华伟</a>
// * @CreateDate 2019/9/1 22:11
// * @Description 功能描述
// * <pre>
// * 补充说明
// * </pre>
// **/
//@SuppressWarnings("unchecked")
//public class TestDaoFactoryBean<T> implements FactoryBean<T> {
//
//    // 日志对象
//    private static ILogger log = LoggerFactory.getLogger(TestDaoFactoryBean.class);
//
//    private Class<T> interfaceClass;
//
//    @Override
//    public T getObject() throws Exception {
//        log.trace("get...");
//        ProxyFactory _factory = new ProxyFactory();
//        _factory.setInterfaces(interfaceClass);
//        _factory.setOpaque(true);
//        _factory.addAdvice(new MethodInterceptor() {
//            @Override
//            public Object invoke(MethodInvocation invocation) throws Throwable {
//                if (AopUtils.isToStringMethod(invocation.getMethod())) {
//                    return super.toString();
//                }
//                log.debug("do method: {}", invocation.getMethod());
//                return invocation.proceed();
//            }
//        });
//        return (T) _factory.getProxy();
//    }
//
//    @Override
//    public Class<?> getObjectType() {
//        return this.interfaceClass;
//    }
//
//    @Override
//    public boolean isSingleton() {
//        return true;
//    }
//
//    public Class<T> getInterfaceClass() {
//        return interfaceClass;
//    }
//
//    public void setInterfaceClass(Class<T> interfaceClass) {
//        this.interfaceClass = interfaceClass;
//    }
//}
