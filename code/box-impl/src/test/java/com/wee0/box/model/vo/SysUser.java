package com.wee0.box.model.vo;

import java.util.Date;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/5/23 7:12
 * @Description 用户
 * <pre>
 * 补充说明
 * </pre>
 **/
//@Table(name="zz_sys_user")
public class SysUser implements java.io.Serializable {
    /**
     * 主键Id
     */
    //@AssignID
    private Long userId;
    /**
     * 用户登录名称
     */
    private String loginName;
    /**
     * 密码
     */
    private String password;
    /**
     * 用户显示名称
     */
    private String showName;
    /**
     * 用户类型(0: 管理员 1: 系统管理用户 2: 系统业务用户)
     */
    private Integer userType;
    /**
     * 用户头像的Url
     */
    private String headImageUrl;
    /**
     * 状态(0: 正常 1: 锁定)
     */
    private Integer userStatus;
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
     * 删除标记(1: 正常 -1: 已删除)
     */
    private Integer deletedFlag;

    public SysUser() {
    }

    /**
     * 主键Id
     *
     * @return
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 主键Id
     *
     * @param userId
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 用户登录名称
     *
     * @return
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * 用户登录名称
     *
     * @param loginName
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    /**
     * 密码
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * 密码
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 用户显示名称
     *
     * @return
     */
    public String getShowName() {
        return showName;
    }

    /**
     * 用户显示名称
     *
     * @param showName
     */
    public void setShowName(String showName) {
        this.showName = showName;
    }

    /**
     * 用户类型(0: 管理员 1: 系统管理用户 2: 系统业务用户)
     *
     * @return
     */
    public Integer getUserType() {
        return userType;
    }

    /**
     * 用户类型(0: 管理员 1: 系统管理用户 2: 系统业务用户)
     *
     * @param userType
     */
    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    /**
     * 用户头像的Url
     *
     * @return
     */
    public String getHeadImageUrl() {
        return headImageUrl;
    }

    /**
     * 用户头像的Url
     *
     * @param headImageUrl
     */
    public void setHeadImageUrl(String headImageUrl) {
        this.headImageUrl = headImageUrl;
    }

    /**
     * 状态(0: 正常 1: 锁定)
     *
     * @return
     */
    public Integer getUserStatus() {
        return userStatus;
    }

    /**
     * 状态(0: 正常 1: 锁定)
     *
     * @param userStatus
     */
    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
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
     * 删除标记(1: 正常 -1: 已删除)
     *
     * @return
     */
    public Integer getDeletedFlag() {
        return deletedFlag;
    }

    /**
     * 删除标记(1: 正常 -1: 已删除)
     *
     * @param deletedFlag
     */
    public void setDeletedFlag(Integer deletedFlag) {
        this.deletedFlag = deletedFlag;
    }

}

