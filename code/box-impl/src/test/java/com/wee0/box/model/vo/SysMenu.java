package com.wee0.box.model.vo;

import java.util.Date;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/5/23 7:17
 * @Description 菜单
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SysMenu implements java.io.Serializable {
    /**
     * 主键Id
     */
    private Long menuId;
    /**
     * 父菜单Id，目录菜单的父菜单为null
     */
    private Long parentId;
    /**
     * 菜单显示名称
     */
    private String menuName;
    /**
     * (0: 目录 1: 菜单 2: 按钮 3: UI片段)
     */
    private Integer menuType;
    /**
     * 前端表单路由名称，仅用于menu_type为1的菜单类型
     */
    private String formRouterName;
    /**
     * 菜单显示顺序 (值越小，排序越靠前)
     */
    private Integer showOrder;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 创建者Id
     */
    private Long createUserId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新者Id
     */
    private Long updateUserId;
    /**
     * 最后更新时间
     */
    private Date updateTime;
    /**
     * 逻辑删除标记(1: 正常 -1: 已删除)
     */
    private Integer deletedFlag;

    public SysMenu() {
    }

    /**
     * 主键Id
     *
     * @return
     */
    public Long getMenuId() {
        return menuId;
    }

    /**
     * 主键Id
     *
     * @param menuId
     */
    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    /**
     * 父菜单Id，目录菜单的父菜单为null
     *
     * @return
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 父菜单Id，目录菜单的父菜单为null
     *
     * @param parentId
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 菜单显示名称
     *
     * @return
     */
    public String getMenuName() {
        return menuName;
    }

    /**
     * 菜单显示名称
     *
     * @param menuName
     */
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    /**
     * (0: 目录 1: 菜单 2: 按钮 3: UI片段)
     *
     * @return
     */
    public Integer getMenuType() {
        return menuType;
    }

    /**
     * (0: 目录 1: 菜单 2: 按钮 3: UI片段)
     *
     * @param menuType
     */
    public void setMenuType(Integer menuType) {
        this.menuType = menuType;
    }

    /**
     * 前端表单路由名称，仅用于menu_type为1的菜单类型
     *
     * @return
     */
    public String getFormRouterName() {
        return formRouterName;
    }

    /**
     * 前端表单路由名称，仅用于menu_type为1的菜单类型
     *
     * @param formRouterName
     */
    public void setFormRouterName(String formRouterName) {
        this.formRouterName = formRouterName;
    }

    /**
     * 菜单显示顺序 (值越小，排序越靠前)
     *
     * @return
     */
    public Integer getShowOrder() {
        return showOrder;
    }

    /**
     * 菜单显示顺序 (值越小，排序越靠前)
     *
     * @param showOrder
     */
    public void setShowOrder(Integer showOrder) {
        this.showOrder = showOrder;
    }

    /**
     * 菜单图标
     *
     * @return
     */
    public String getIcon() {
        return icon;
    }

    /**
     * 菜单图标
     *
     * @param icon
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * 创建者Id
     *
     * @return
     */
    public Long getCreateUserId() {
        return createUserId;
    }

    /**
     * 创建者Id
     *
     * @param createUserId
     */
    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * 创建时间
     *
     * @return
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     *
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 更新者Id
     *
     * @return
     */
    public Long getUpdateUserId() {
        return updateUserId;
    }

    /**
     * 更新者Id
     *
     * @param updateUserId
     */
    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }

    /**
     * 最后更新时间
     *
     * @return
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 最后更新时间
     *
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 逻辑删除标记(1: 正常 -1: 已删除)
     *
     * @return
     */
    public Integer getDeletedFlag() {
        return deletedFlag;
    }

    /**
     * 逻辑删除标记(1: 正常 -1: 已删除)
     *
     * @param deletedFlag
     */
    public void setDeletedFlag(Integer deletedFlag) {
        this.deletedFlag = deletedFlag;
    }

}
