package com.wee0.box.model.vo;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/5/23 7:21
 * @Description 用户角色关系
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SysUserRole implements java.io.Serializable {
    /**
     * 用户Id
     */
    private Long userId;
    /**
     * 角色Id
     */
    private Long roleId;

    public SysUserRole() {
    }

    /**
     * 用户Id
     *
     * @return
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 用户Id
     *
     * @param userId
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 角色Id
     *
     * @return
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * 角色Id
     *
     * @param roleId
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
