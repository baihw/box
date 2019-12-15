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

package com.wee0.box.subject.impl;

import com.wee0.box.subject.ISubject;
import com.wee0.box.subject.ISubjectContext;
import com.wee0.box.subject.ITokenFactory;
import com.wee0.box.util.shortcut.ThreadUtils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ObjectStreamException;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/1 8:57
 * @Description 一个简单的当前使用者主体对象信息管理上下文环境实现
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SimpleSubjectContext implements ISubjectContext {

    // 对象存储容器
    private static final ThreadLocal<ISubject> SUBJECT_HOLDER = ThreadUtils.createThreadLocal();

    public void setSubject(ISubject subject) {
        if (null == subject) {
            SUBJECT_HOLDER.remove();
            return;
        }
        SUBJECT_HOLDER.set(subject);
    }

    @Override
    public ISubject getSubject(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("This method is not yet implemented.");
    }

    @Override
    public ISubject getSubject(String id) {
        throw new UnsupportedOperationException("This method is not yet implemented.");
    }

    @Override
    public ISubject getSubject() {
        ISubject _subject = SUBJECT_HOLDER.get();
        if (null == _subject) {
            _subject = new SimpleSubject();
            SUBJECT_HOLDER.set(_subject);
        }
        return _subject;
    }

    @Override
    public ITokenFactory getTokenFactory() {
        throw new UnsupportedOperationException("This method is not yet implemented.");
    }

    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private SimpleSubjectContext() {
        if (null != SimpleSubjectContextHolder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }
    }

    // 当前对象唯一实例持有者。
    private static final class SimpleSubjectContextHolder {
        private static final SimpleSubjectContext _INSTANCE = new SimpleSubjectContext();
    }

    // 防止使用反序列化操作获取多个对象实例。
    private Object readResolve() throws ObjectStreamException {
        return SimpleSubjectContextHolder._INSTANCE;
    }

    /**
     * 获取当前对象唯一实例。
     *
     * @return 当前对象唯一实例
     */
    public static SimpleSubjectContext me() {
        return SimpleSubjectContextHolder._INSTANCE;
    }

}
