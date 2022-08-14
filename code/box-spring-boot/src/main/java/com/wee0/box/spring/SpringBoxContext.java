/*
 * Copyright (c) 2019-present, wee0.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wee0.box.spring;

import com.wee0.box.BoxConfig;
import com.wee0.box.IBoxContext;
import com.wee0.box.beans.IDestroyable;
import com.wee0.box.code.BizCodeManager;
import com.wee0.box.code.IBizCodeManager;
import com.wee0.box.code.impl.BizCodeDef;
import com.wee0.box.log.ILogger;
import com.wee0.box.log.LoggerFactory;
import com.wee0.box.plugin.PluginManager;
import org.springframework.context.ApplicationContext;

import java.io.ObjectStreamException;
import java.util.Collection;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/1 8:58
 * @Description 基于spring的上下文对象实现
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SpringBoxContext implements IBoxContext, IDestroyable {

    // 日志对象
    private static final ILogger log = LoggerFactory.getLogger(SpringBoxContext.class);

    // spring上下文对象
    private ApplicationContext springContext;

    @Override
    public boolean hasBean(String id) {
        return springContext.containsBean(id);
    }

    @Override
    public <T> T getBean(Class<T> type) {
        return springContext.getBean(type);
    }

    @Override
    public Object getBean(String id) {
        return springContext.getBean(id);
    }

    @Override
    public <T> T getBean(String id, Class<T> type) {
        return springContext.getBean(id, type);
    }

    @Override
    public void addBean(String id, Object bean) {
        throw new UnsupportedOperationException("This method is not yet implemented.");
    }

    @Override
    public void init() {
        log.info("user.timezone:{}.", System.getProperty("user.timezone"));
        log.info("externalDir:{}.", BoxConfig.impl().getExternalDir());
        log.info("resourceDir:{}.", BoxConfig.impl().getResourceDir());
//        log.info("user.dir:{}.", System.getProperty("user.dir"));
//        log.info("/resource:{}.", BoxConfig.class.getResource("/"));
//        log.info(".dir:{}.", new File(".").getAbsoluteFile().getParent());
        log.trace("before init...");
        // 初始化业务编码管理器
        IBizCodeManager _bizCodeManager = BizCodeManager.impl();
        _bizCodeManager.addBizCodeEnum(BizCodeDef.class);
        if (_bizCodeManager instanceof IDestroyable) {
            ((IDestroyable) _bizCodeManager).init();
        }
        // 启动插件管理器
        PluginManager.impl().start();
        log.trace("after init...");
    }

    @Override
    public void destroy() {
//        // 停止插件管理器
//        PluginManager.impl().stop();
        // 清理资源
        Collection _allImpl = BoxConfig.impl().getAllInterfaceImpl();
        for (Object _impl : _allImpl) {
            if (_impl instanceof IDestroyable && !(_impl instanceof SpringBoxContext)) {
                ((IDestroyable) _impl).destroy();
            }
        }
    }

    /**
     * 设置spring上下文对象
     *
     * @param applicationContext spring上下文对象
     */
    public void setSpringContext(ApplicationContext applicationContext) {
        this.springContext = applicationContext;
        log.debug("springContext:{}", this.springContext);
    }

    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private SpringBoxContext() {
        if (null != SpringBoxContextHolder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }
    }

    // 当前对象唯一实例持有者。
    private static final class SpringBoxContextHolder {
        private static final SpringBoxContext _INSTANCE = new SpringBoxContext();
    }

    // 防止使用反序列化操作获取多个对象实例。
    private Object readResolve() throws ObjectStreamException {
        return SpringBoxContextHolder._INSTANCE;
    }

    /**
     * 获取当前对象唯一实例。
     *
     * @return 当前对象唯一实例
     */
    public static SpringBoxContext me() {
        return SpringBoxContextHolder._INSTANCE;
    }

}
