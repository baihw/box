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
import com.wee0.box.sql.SqlHelper;
import com.wee0.box.subject.IWeiXinToken;
import com.wee0.box.subject.SubjectContext;
import com.wee0.box.util.IHttpUtils;
import com.wee0.box.util.shortcut.CheckUtils;
import com.wee0.box.util.shortcut.HttpUtils;
import com.wee0.box.util.shortcut.JsonUtils;
import com.wee0.box.util.shortcut.MapUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2020/5/23 7:11
 * @Description 基于微信登陆的认证域
 * <pre>
 * 补充说明
 * </pre>
 **/
public class BoxWeiXinRealm extends AuthorizingRealm {

    // 日志对象
    private static final ILogger _LOG = LoggerFactory.getLogger(BoxWeiXinRealm.class);

    // 默认名称
    private final String DEF_NAME = "boxWeiXinRealm";

    static final String TMP_URL_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";

    // boxToken管理对象
    private BoxTokenManager boxTokenManager;
    // 微信开放平台创建应用后获取的应用标识与密钥。
    private String appId;
    private String secret;
    // 用户查询语句，接收1个参数: unionId。
    private String queryUser1;

    @Override
    public String getName() {
        return DEF_NAME;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof IWeiXinToken;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        IWeiXinToken _token = (IWeiXinToken) token;
        String _code = _token.getCode();
        //:todo 检查state是否存在
//        String _state = _token.getState();
        String _url = String.format(TMP_URL_ACCESS_TOKEN, this.appId, this.secret, _code);
        IHttpUtils.IHttpResult _httpResult = HttpUtils.impl().httpGet(_url);
        if (null == _httpResult || _httpResult.getCode() >= 400) {
            _LOG.warn("Invalid httpResult: {}", _httpResult);
            throw new IllegalStateException("Invalid http result!");
        }
        Map<String, Object> _jsonResult = JsonUtils.readToMap(_httpResult.getContent());
        _LOG.trace("jsonResult: {}", _jsonResult);
        if (null == _jsonResult || _jsonResult.isEmpty())
            throw new IllegalStateException("Invalid json result!");
        if (_jsonResult.containsKey("errcode")) {
//            String _errCode = String.valueOf(_jsonResult.get("errcode"));
//            if (!"0".equals(_errCode))
            throw new IllegalStateException("validate error code:" + _jsonResult.get("errcode") + ", msg:" + _jsonResult.get("errmsg"));
        }
        String _unionId = CheckUtils.checkTrimEmpty(MapUtils.getString(_jsonResult, "unionid"), null);
        if (null == _unionId) {
//            String _openId = CheckUtils.checkTrimEmpty(MapUtils.getString(_jsonResult, "openid"), null);
//            if (null == _openId)
//                throw new IllegalStateException("openid not found!");
            throw new IllegalStateException("unionid not found!");
        }
        // 通过 unionId 获取用户唯一标识
        String _userId = SqlHelper.impl().queryScalar(this.queryUser1, new Object[]{_unionId}, String.class);
        if (null == _userId || 0 == (_userId = _userId.trim()).length())
            throw new IncorrectCredentialsException("认证失败! Invalid unionId: " + _unionId);

        String _subjectToken = this.boxTokenManager.createToken(_userId);
        ShiroSubject _subject = ((ShiroSubject) SubjectContext.getSubject());
        // 保存当前主体对象唯一标识，便于后期开发人员获取。
        _subject.setId(_userId);
        _subject.setToken(_subjectToken);
        _LOG.trace("checked: {}", _subject);


        AuthenticationInfo _info = new SimpleAuthenticationInfo(_userId, _code, getName());
        return _info;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    @Override
    public String toString() {
        return getName();
    }

    // getter and setter

    public BoxTokenManager getBoxTokenManager() {
        return this.boxTokenManager;
    }

    public void setBoxTokenManager(BoxTokenManager boxTokenManager) {
        this.boxTokenManager = boxTokenManager;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getQueryUser1() {
        return queryUser1;
    }

    public void setQueryUser1(String queryUser1) {
        this.queryUser1 = queryUser1;
    }

}
