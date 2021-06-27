package com.wee0.box.model.vo;

import java.util.Date;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/5/23 7:15
 * @Description 角色
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SysRole implements java.io.Serializable {
    /**
     * 主键Id
     */
//    @AssignID
    private Long roleId;
    /**
     * 角色名称
     */
    private String roleName;
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

    public SysRole() {
    }

    /**
     * 主键Id
     *
     * @return
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * 主键Id
     *
     * @param roleId
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    /**
     * 角色名称
     *
     * @return
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * 角色名称
     *
     * @param roleName
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
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
