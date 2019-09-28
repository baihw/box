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

import com.wee0.box.log.ILogger;
import com.wee0.box.log.LoggerFactory;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;

import java.io.Serializable;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/8 7:25
 * @Description 基于关系型数据库的会话数据访问对象
 * <pre>
 * 补充说明
 * </pre>
 **/
final class BoxSessionDAO extends EnterpriseCacheSessionDAO {

    // 日志对象
    private static ILogger log = LoggerFactory.getLogger(BoxSessionDAO.class);

    @Override
    protected Serializable doCreate(Session session) {
        log.debug("session: {}", session);
        return super.doCreate(session);
    }

    @Override
    protected void doDelete(Session session) {
        log.debug("session: {}", session);
        super.doDelete(session);
    }

    @Override
    protected void doUpdate(Session session) {
        log.debug("session: {}", session);
        super.doUpdate(session);
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        log.debug("sessionId: {}", sessionId);
        return super.doReadSession(sessionId);
    }

    @Override
    protected void assignSessionId(Session session, Serializable sessionId) {
        log.debug("session: {}, sessionId: {}", session, sessionId);
        super.assignSessionId(session, sessionId);
    }
}
