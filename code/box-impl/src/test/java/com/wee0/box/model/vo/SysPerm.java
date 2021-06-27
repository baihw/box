package com.wee0.box.model.vo;

import java.util.Date;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/5/23 7:17
 * @Description 权限
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SysPerm implements java.io.Serializable {
    /**
     * 权限id
     */
    private Long permId;
    /**
     * 权限所在的权限模块id
     */
    private Long moduleId;
    /**
     * 权限名称
     */
    private String permName;
    /**
     * 关联的url
     */
    private String url;
    /**
     * 权限在当前模块下的顺序，由小到大
     */
    private Integer showOrder;
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

    public SysPerm() {
    }

    /**
     * 权限id
     *
     * @return
     */
    public Long getPermId() {
        return permId;
    }

    /**
     * 权限id
     *
     * @param permId
     */
    public void setPermId(Long permId) {
        this.permId = permId;
    }

    /**
     * 权限所在的权限模块id
     *
     * @return
     */
    public Long getModuleId() {
        return moduleId;
    }

    /**
     * 权限所在的权限模块id
     *
     * @param moduleId
     */
    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    /**
     * 权限名称
     *
     * @return
     */
    public String getPermName() {
        return permName;
    }

    /**
     * 权限名称
     *
     * @param permName
     */
    public void setPermName(String permName) {
        this.permName = permName;
    }

    /**
     * 关联的url
     *
     * @return
     */
    public String getUrl() {
        return url;
    }

    /**
     * 关联的url
     *
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 权限在当前模块下的顺序，由小到大
     *
     * @return
     */
    public Integer getShowOrder() {
        return showOrder;
    }

    /**
     * 权限在当前模块下的顺序，由小到大
     *
     * @param showOrder
     */
    public void setShowOrder(Integer showOrder) {
        this.showOrder = showOrder;
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
