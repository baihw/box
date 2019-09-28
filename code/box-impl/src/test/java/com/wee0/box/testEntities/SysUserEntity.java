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

package com.wee0.box.testEntities;

import com.wee0.box.sql.annotation.BoxColumn;
import com.wee0.box.sql.annotation.BoxTable;

import java.util.Date;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/15 8:16
 * @Description 用户实体
 * <pre>
 * 补充说明
 * </pre>
 **/
@BoxTable(name = "SYS_USER")
public class SysUserEntity extends BaseEntity {

    /**
     * 用户登录名
     */
    @BoxColumn(name = "LOGIN_NAME")
    private String loginName;


    /**
     * 用户编码
     */
    @BoxColumn(name = "USER_NUM")
    private String userNum;

    /**
     * 真实姓名
     */
    @BoxColumn(name = "REALNAME")
    private String realname;


    /**
     * 密码
     */
    @BoxColumn(name = "PASSWORD")
    private String password;

    /**
     * 盐
     */
    @BoxColumn(name = "SALT")
    private String salt;

    /**
     * 状态STATUS:无效0/有效1
     */
    @BoxColumn(name = "STATUS_CD")
    private String statusCd;

    /**
     * 性别(SEX:男1\女2)
     */
    @BoxColumn(name = "SEX_CD")
    private String sexCd;

    /**
     * 证件类型(CERTIFICATE_TYPE:身份证0\学生证1\工作证2\士兵证3\军官证4\护照5)
     */
    @BoxColumn(name = "CERTIFICATE_TYPE_CD")
    private String certificateTypeCd;

    /**
     * 证件号码
     */
    @BoxColumn(name = "CERTIFICATE_NUM")
    private String certificateNum;

    /**
     *
     */
    @BoxColumn(name = "QQ")
    private String qq;

    /**
     * 微信
     */
    @BoxColumn(name = "WECHAT")
    private String wechat;

    /**
     *
     */
    @BoxColumn(name = "EMAIL")
    private String email;

    /**
     * 固定电话
     */
    @BoxColumn(name = "TELEPHONE")
    private String telephone;

    /**
     * 移动电话
     */
    @BoxColumn(name = "MOBILE")
    private String mobile;

    /**
     * 传真
     */
    @BoxColumn(name = "FAX")
    private String fax;

    /**
     * 密码错误次数
     */
    @BoxColumn(name = "PASS_ERROR_COUNT")
    private Integer passErrorCount;

    /**
     * 登录成功次数
     */
    @BoxColumn(name = "LOGIN_SUCC_COUNT")
    private Integer loginSuccCount;

    /**
     * 锁定时间
     */
    @BoxColumn(name = "LOCK_TIME")
    private Date lockTime;


    /**
     * 备注
     */
    @BoxColumn(name = "REMARK")
    private String remark;

    /**
     * 省级
     */
    @BoxColumn(name = "PROVINCE")
    private String province;

    /**
     * 城市级
     */
    @BoxColumn(name = "CITY")
    private String city;

    /**
     * 区域级
     */
    @BoxColumn(name = "DISTRICT")
    private String district;

    /**
     * 地址
     */
    @BoxColumn(name = "ADDRESS")
    private String address;

    /**
     * 客户端编号
     */
    @BoxColumn(name = "CLNTEND_ID")
    private String clntendId;
    /**
     * 客户端名称
     */
    @BoxColumn(name = "CLNTEND_NM")
    private String clntendNm;


    /**
     * 上次登录时间
     */
    @BoxColumn(name = "LAST_LOGIN_TIME")
    private Date lastLoginTime;


    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }


    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(String statusCd) {
        this.statusCd = statusCd;
    }

    public String getSexCd() {
        return sexCd;
    }

    public void setSexCd(String sexCd) {
        this.sexCd = sexCd;
    }

    public String getCertificateTypeCd() {
        return certificateTypeCd;
    }

    public void setCertificateTypeCd(String certificateTypeCd) {
        this.certificateTypeCd = certificateTypeCd;
    }

    public String getCertificateNum() {
        return certificateNum;
    }

    public void setCertificateNum(String certificateNum) {
        this.certificateNum = certificateNum;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public Integer getPassErrorCount() {
        return passErrorCount;
    }

    public void setPassErrorCount(Integer passErrorCount) {
        this.passErrorCount = passErrorCount;
    }

    public Integer getLoginSuccCount() {
        return loginSuccCount;
    }

    public void setLoginSuccCount(Integer loginSuccCount) {
        this.loginSuccCount = loginSuccCount;
    }

    public Date getLockTime() {
        return lockTime;
    }

    public void setLockTime(Date lockTime) {
        this.lockTime = lockTime;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getClntendId() {
        return clntendId;
    }

    public void setClntendId(String clntendId) {
        this.clntendId = clntendId;
    }

    public String getClntendNm() {
        return clntendNm;
    }

    public void setClntendNm(String clntendNm) {
        this.clntendNm = clntendNm;
    }

//    public SysUserEntity coverToEntity(Map<String, Object> params, SysUserEntity entity) {
//        if (entity == null) {
//            entity = new SysUserEntity();
//        }
//        try {
//            String loginName = MapUtils.getString(params, "loginName");//用户编码
//            if (!StringUtils.isEmpty(loginName)) {
//                entity.setLoginName(loginName);
//            }
//            String realname = MapUtils.getString(params, "realname");//真实姓名
//            if (!StringUtils.isEmpty(realname)) {
//                entity.setRealname(realname);
//            }
//            String userNum = MapUtils.getString(params, "userNum");//用户登录名
//            if (!StringUtils.isEmpty(userNum)) {
//                entity.setUserNum(userNum);
//            }
//            String password = MapUtils.getString(params, "password");//密码
//            if (!StringUtils.isEmpty(password)) {
//                entity.setPassword(password);
//            }
//            String salt = MapUtils.getString(params, "salt");//盐
//            if (!StringUtils.isEmpty(salt)) {
//                entity.setSalt(salt);
//            }
//            String statusCd = MapUtils.getString(params, "statusCd");//状态STATUS:无效0/有效1
//            if (!StringUtils.isEmpty(statusCd)) {
//                entity.setStatusCd(statusCd);
//            }
//            String sexCd = MapUtils.getString(params, "sexCd");//性别(SEX:男1\女2)
//            if (!StringUtils.isEmpty(sexCd)) {
//                entity.setSexCd(sexCd);
//            }
//            String certificateTypeCd = MapUtils.getString(params, "certificateTypeCd");//证件类型(CERTIFICATE_TYPE:身份证0\学生证1\工作证2\士兵证3\军官证4\护照5)
//            if (!StringUtils.isEmpty(certificateTypeCd)) {
//                entity.setCertificateTypeCd(certificateTypeCd);
//            }
//            String certificateNum = MapUtils.getString(params, "certificateNum");//证件号码
//            if (!StringUtils.isEmpty(certificateNum)) {
//                entity.setCertificateNum(certificateNum);
//            }
//            String qq = MapUtils.getString(params, "qq");//qq
//            if (!StringUtils.isEmpty(qq)) {
//                entity.setQq(qq);
//            }
//            String wechat = MapUtils.getString(params, "wechat");//微信
//            if (!StringUtils.isEmpty(wechat)) {
//                entity.setWechat(wechat);
//            }
//            String email = MapUtils.getString(params, "email");//邮箱
//            if (!StringUtils.isEmpty(email)) {
//                entity.setEmail(email);
//            }
//            String telephone = MapUtils.getString(params, "telephone");//固定电话
//            if (!StringUtils.isEmpty(telephone)) {
//                entity.setTelephone(telephone);
//            }
//            String mobile = MapUtils.getString(params, "mobile");//移动电话
//            if (!StringUtils.isEmpty(mobile)) {
//                entity.setMobile(mobile);
//            }
//            String fax = MapUtils.getString(params, "fax");//传真
//            if (!StringUtils.isEmpty(fax)) {
//                entity.setFax(fax);
//            }
//            Integer passErrorCount = MapUtils.getInteger(params, "passErrorCount");//密码错误次数
//            if (!StringUtils.isEmpty(passErrorCount)) {
//                entity.setPassErrorCount(passErrorCount);
//            }
//            Integer loginSuccCount = MapUtils.getInteger(params, "loginSuccCount");//登录成功次数
//            if (!StringUtils.isEmpty(loginSuccCount)) {
//                entity.setLoginSuccCount(loginSuccCount);
//            }
//            String lockTime = MapUtils.getString(params, "lockTime");//锁定时间
//            if (!StringUtils.isEmpty(lockTime)) {
//                entity.setLockTime(DateUtils.parse(lockTime));
//            }
//
//            String remark = MapUtils.getString(params, "remark");//备注
//            if (!StringUtils.isEmpty(remark)) {
//                entity.setRemark(remark);
//            }
//            String province = MapUtils.getString(params, "province");//省级
//            if (!StringUtils.isEmpty(province)) {
//                entity.setProvince(province);
//            }
//            String city = MapUtils.getString(params, "city");//城市级
//            if (!StringUtils.isEmpty(city)) {
//                entity.setCity(city);
//            }
//            String district = MapUtils.getString(params, "district");//区域级
//            if (!StringUtils.isEmpty(district)) {
//                entity.setDistrict(district);
//            }
//            String address = MapUtils.getString(params, "address");//地址
//            if (!StringUtils.isEmpty(address)) {
//                entity.setAddress(address);
//            }
//            if (params.containsKey("clntendId")) {
//                String clntendId = MapUtils.getString(params, "clntendId");//客户端ID
//                entity.setClntendId(clntendId);
//            }
//            if (params.containsKey("clntendNm")) {
//                String clntendNm = MapUtils.getString(params, "clntendNm");//客户端名称
//                entity.setClntendNm(clntendNm);
//            }
//            //上次登录时间
//            if (params.containsKey("lastLoginTime")) {
//                Object lastLoginTime = MapUtils.getObject(params, "lastLoginTime");
//                entity.setLastLoginTime((Date) lastLoginTime);
//            }
//
//            //BaseEntity
//            Integer id = MapUtils.getInteger(params, "id");
//            if (id != null) {
//                entity.setId(id);
//            }
//            String delInd = MapUtils.getString(params, "delInd");
//            if (!StringUtils.isEmpty(delInd)) {
//                entity.setDelInd(delInd);
//            }
//
//            //给租户创建管理员时需要赋值
//            String tenantId = MapUtils.getString(params, "tenantId");
//            if (!StringUtils.isEmpty(tenantId)) {
//                entity.setTenantId(tenantId);
//            }
//            //给租户创建管理员时需要赋值
//            String createUser = MapUtils.getString(params, "createUser");
//            if (!StringUtils.isEmpty(createUser)) {
//                entity.setCreateUser(createUser);
//            }
//            return entity;
//        } catch (Exception e) {
//            throw ExceptionFactory.getBizException("sys-00001", "1");
//        }
//    }

}
