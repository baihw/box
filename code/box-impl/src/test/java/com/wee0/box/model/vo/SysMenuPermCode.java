package com.wee0.box.model.vo;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/5/23 7:22
 * @Description 菜单权限编码关系
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SysMenuPermCode implements java.io.Serializable {
    /**
     * 关联菜单Id
     */
    private Long menuId;
    /**
     * 关联权限字Id
     */
    private Long permCodeId;

    public SysMenuPermCode() {
    }

    /**
     * 关联菜单Id
     *
     * @return
     */
    public Long getMenuId() {
        return menuId;
    }

    /**
     * 关联菜单Id
     *
     * @param menuId
     */
    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    /**
     * 关联权限字Id
     *
     * @return
     */
    public Long getPermCodeId() {
        return permCodeId;
    }

    /**
     * 关联权限字Id
     *
     * @param permCodeId
     */
    public void setPermCodeId(Long permCodeId) {
        this.permCodeId = permCodeId;
    }
}
