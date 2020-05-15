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

import com.wee0.box.cache.CacheManager;
import com.wee0.box.log.ILogger;
import com.wee0.box.log.LoggerFactory;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;

import java.io.Serializable;
import java.util.Iterator;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2020/5/5 9:19
 * @Description 基于缓存的自定义会话数据访问对象
 * <pre>
 * 补充说明
 * </pre>
 **/
public final class BoxCacheSessionDAO extends CachingSessionDAO {

    // 日志对象
    private static final ILogger log = LoggerFactory.getLogger(BoxCacheSessionDAO.class);

    // 基于Token的会话超时时间，单位：秒。2592000 seconds = 30 day
    private long tokenSessionTimeout;

    @Override
    protected Serializable doCreate(Session session) {
        log.debug("create session: {}", session);
        printSession(session);
        Serializable _sessionId = generateSessionId(session);
        assignSessionId(session, _sessionId);
        printSession(session);
        return _sessionId;
    }

    @Override
    protected void doDelete(Session session) {
        log.debug("delete session: {}", session);
        printSession(session);
    }

    @Override
    protected void doUpdate(Session session) {
        log.debug("update session: {}", session);
        printSession(session);
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        log.debug("read sessionId: {}", sessionId);
        if (null != sessionId) {
            String _sessionId = sessionId.toString();
            if (0 == _sessionId.indexOf("tk_")) {
                Object _keepVal = CacheManager.impl().getDefaultCache().get(_sessionId);
                if (null != _keepVal && _keepVal instanceof Session)
                    return (Session) _keepVal;
            }
        }
        return null;
    }

    @Override
    protected void assignSessionId(Session session, Serializable sessionId) {
//        log.debug("assign session: {}, sessionId: {}", session, sessionId);
        super.assignSessionId(session, sessionId);
    }


    static void printSession(Session session) {
        log.debug("------------ begin session: {} ------------", session);
        Iterator<Object> _keys = session.getAttributeKeys().iterator();
        while (_keys.hasNext()) {
            Object _key = _keys.next();
            log.debug("key:{}, val:{}", _key, session.getAttribute(_key));
        }
        log.debug("------------ end session: {} ------------", session);
    }

    public long getTokenSessionTimeout() {
        return this.tokenSessionTimeout;
    }

    public void setTokenSessionTimeout(long tokenSessionTimeout) {
        this.tokenSessionTimeout = tokenSessionTimeout;
    }

}
