package com.wee0.box.model.vo;

import java.util.Date;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/5/23 7:20
 * @Description 权限编码
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SysPermCode implements java.io.Serializable {
    /**
     * 主键Id
     */
    private Long permCodeId;
    /**
     * 上级权限字Id
     */
    private Long parentId;
    /**
     * 权限字标识(一般为有含义的英文字符串)
     */
    private String permCode;
    /**
     * 类型(0: 表单 1: UI片段 2: 操作)
     */
    private Integer permCodeType;
    /**
     * 显示名称
     */
    private String showName;
    /**
     * 显示顺序(数值越小，越靠前)
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

    public SysPermCode() {
    }

    /**
     * 主键Id
     *
     * @return
     */
    public Long getPermCodeId() {
        return permCodeId;
    }

    /**
     * 主键Id
     *
     * @param permCodeId
     */
    public void setPermCodeId(Long permCodeId) {
        this.permCodeId = permCodeId;
    }

    /**
     * 上级权限字Id
     *
     * @return
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 上级权限字Id
     *
     * @param parentId
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 权限字标识(一般为有含义的英文字符串)
     *
     * @return
     */
    public String getPermCode() {
        return permCode;
    }

    /**
     * 权限字标识(一般为有含义的英文字符串)
     *
     * @param permCode
     */
    public void setPermCode(String permCode) {
        this.permCode = permCode;
    }

    /**
     * 类型(0: 表单 1: UI片段 2: 操作)
     *
     * @return
     */
    public Integer getPermCodeType() {
        return permCodeType;
    }

    /**
     * 类型(0: 表单 1: UI片段 2: 操作)
     *
     * @param permCodeType
     */
    public void setPermCodeType(Integer permCodeType) {
        this.permCodeType = permCodeType;
    }

    /**
     * 显示名称
     *
     * @return
     */
    public String getShowName() {
        return showName;
    }

    /**
     * 显示名称
     *
     * @param showName
     */
    public void setShowName(String showName) {
        this.showName = showName;
    }

    /**
     * 显示顺序(数值越小，越靠前)
     *
     * @return
     */
    public Integer getShowOrder() {
        return showOrder;
    }

    /**
     * 显示顺序(数值越小，越靠前)
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
