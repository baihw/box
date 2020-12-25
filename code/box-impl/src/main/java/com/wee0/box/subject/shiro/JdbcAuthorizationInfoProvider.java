package com.wee0.box.subject.shiro;

import com.wee0.box.log.ILogger;
import com.wee0.box.log.LoggerFactory;
import com.wee0.box.sql.SqlHelper;
import com.wee0.box.subject.IAuthorizationInfoProvider;
import com.wee0.box.util.shortcut.CheckUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/21 9:29
 * @Description 自定义认证域数据提供者
 * <pre>
 * 补充说明
 * </pre>
 **/
//@BoxBean(name = IAuthorizationInfoProvider.DEF_BEAN_NAME)
public class JdbcAuthorizationInfoProvider implements IAuthorizationInfoProvider {

    // 日志对象
    private static ILogger log = LoggerFactory.getLogger(JdbcAuthorizationInfoProvider.class);

    // 角色查询语句，接收1个参数：userId。
    private String queryRoles;
    // 权限查询语句，接收1个参数：userId。
    private String queryPermissions;


    @Override
    public Set<String> getRoles(String userId) {
        log.debug("getRoles...");
        userId = CheckUtils.checkNotTrimEmpty(userId, "userId cannot be empty!");
        Set<String> _result = new HashSet<>();
        List<Map<String, Object>> _roles = SqlHelper.impl().queryMapList(this.queryRoles, new Object[]{userId});
        if (null == _roles || _roles.isEmpty()) {
            log.warn("roles is empty!");
        } else {
            _roles.forEach(roleMap -> {
                _result.add(String.valueOf(roleMap.values().iterator().next()));
            });
        }
        return _result;
    }

    @Override
    public Set<String> getPermissions(String userId) {
        log.debug("getPermissions...");
        userId = CheckUtils.checkNotTrimEmpty(userId, "userId cannot be empty!");
        Set<String> _result = new HashSet<>();
        List<Map<String, Object>> _permissions = SqlHelper.impl().queryMapList(this.queryPermissions, new Object[]{userId});
        if (null == _permissions || _permissions.isEmpty()) {
            log.warn("permission is empty!");
        } else {
            _permissions.forEach(permissionMap -> {
                _result.add(String.valueOf(permissionMap.values().iterator().next()));
            });
        }
        return _result;
    }

    // getter and setter

    public String getQueryRoles() {
        return this.queryRoles;
    }

    public void setQueryRoles(String queryRoles) {
        this.queryRoles = queryRoles;
    }

    public String getQueryPermissions() {
        return this.queryPermissions;
    }

    public void setQueryPermissions(String queryPermissions) {
        this.queryPermissions = queryPermissions;
    }

}
