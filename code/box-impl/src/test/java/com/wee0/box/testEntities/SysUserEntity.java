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
     * 用户名称
     */
    @BoxColumn(name = "USER_NAME")
    private String userName;

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 用户密码
     */
    @BoxColumn(name = "USER_PWD")
    private String userPwd;

    public String getUserPwd() {
        return this.userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    /**
     * 用户昵称
     */
    @BoxColumn(name = "NICK_NAME")
    private String nickName;

    public String getNickName() {
        return this.nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * 性别 0:女 1:男
     */
    @BoxColumn(name = "SEX")
    private Integer sex;

    public Integer getSex() {
        return this.sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * 邮箱
     */
    @BoxColumn(name = "EMAIL")
    private String email;

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 移动电话
     */
    @BoxColumn(name = "MOBILE")
    private String mobile;

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}
