package com.wee0.box.model.vo;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/5/23 7:22
 * @Description 角色菜单关系
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SysRoleMenu implements java.io.Serializable {
    /**
     * 角色Id
     */
    private Long roleId;
    /**
     * 菜单Id
     */
    private Long menuId;

    public SysRoleMenu() {
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

    /**
     * 菜单Id
     *
     * @return
     */
    public Long getMenuId() {
        return menuId;
    }

    /**
     * 菜单Id
     *
     * @param menuId
     */
    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }
}
