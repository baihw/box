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

package com.wee0.box.subject.shiro;

import com.wee0.box.subject.ISubject;
import com.wee0.box.subject.ISubjectContext;
import com.wee0.box.subject.ITokenFactory;
import com.wee0.box.util.shortcut.CheckUtils;
import com.wee0.box.util.shortcut.ThreadUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.config.Ini;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;

import java.io.ObjectStreamException;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/13 22:05
 * @Description 基于shiro的当前使用者主体对象信息管理上下文环境实现
 * <pre>
 * 补充说明
 * </pre>
 **/
public class ShiroSubjectContext implements ISubjectContext {

    // 默认的资源配置文件
    private static final String DEF_RESOURCE = "classpath:config/shiro.ini";

    // 对象存储容器
    private static final ThreadLocal<ISubject> SUBJECT_HOLDER = ThreadUtils.createThreadLocal();

    // 令牌工厂对象实例
    private static final ShiroTokenFactory TOKEN_FACTORY = new ShiroTokenFactory();

    @Override
    public ISubject getSubject(String id) {
        id = CheckUtils.checkTrimEmpty(id, "id can't be empty!");
        Subject _subject = new Subject.Builder().sessionId(id).buildSubject();
        ThreadContext.bind(_subject);
        return new ShiroSubject(_subject);
    }

    @Override
    public ISubject getSubject() {
        ISubject _subject = SUBJECT_HOLDER.get();
        if (null == _subject) {
            _subject = new ShiroSubject(SecurityUtils.getSubject());
            SUBJECT_HOLDER.set(_subject);
        }
        return _subject;
    }

    @Override
    public ITokenFactory getTokenFactory() {
        return TOKEN_FACTORY;
    }

    private void init() {
        IniSecurityManagerFactory _factory = new IniSecurityManagerFactory();
        Ini _ini = Ini.fromResourcePath(DEF_RESOURCE);
        _factory.setIni(_ini);
        _factory.setSingleton(true);
        SecurityUtils.setSecurityManager(_factory.getInstance());
    }

    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private ShiroSubjectContext() {
        if (null != ShiroSubjectContextHolder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }

        // 初始化
        init();
    }

    // 当前对象唯一实例持有者。
    private static final class ShiroSubjectContextHolder {
        private static final ShiroSubjectContext _INSTANCE = new ShiroSubjectContext();
    }

    // 防止使用反序列化操作获取多个对象实例。
    private Object readResolve() throws ObjectStreamException {
        return ShiroSubjectContextHolder._INSTANCE;
    }

    /**
     * 获取当前对象唯一实例。
     *
     * @return 当前对象唯一实例
     */
    public static ShiroSubjectContext me() {
        return ShiroSubjectContextHolder._INSTANCE;
    }

}
