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

package com.wee0.box.code.impl;

import com.wee0.box.code.IBizCode;
import com.wee0.box.code.IBizCodeInitializer;
import com.wee0.box.code.IBizCodeSetter;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 7:51
 * @Description 默认内置的业务编码
 * <pre>
 * 建议所有模块的业务编码都以BizCode作为前缀（如：BizCodeModule1, BizCodeModule2,...），方便开发人员查找使用。
 *
 *  业务编码由1位字母和6位数字组成，并做如下约定：
 *  1位字母，编码类型 主要分为两类 S：系统编码 B：业务编码
 *  6位数字第1~3位：系统模块或者业务模块编号，000代表公共使用的通用模块，其他业务模块不可使用
 *  6位数字第3~6位：具体业务编码编号
 * </pre>
 **/
public enum BizCodeDef implements IBizCode, IBizCodeInitializer {

    /**
     * 保存成功
     */
    SaveSuccess("B000000"),

    /**
     * 保存失败
     */
    SaveFailed("B000001"),

    /**
     * 更新成功
     */
    UpdateSuccess("B000002"),

    /**
     * 更新失败
     */
    UpdateFailed("B000003"),

    /**
     * 删除成功
     */
    DeleteSuccess("B000004"),

    /**
     * 删除失败
     */
    DeleteFailed("B000005"),

    /**
     * 查询成功
     */
    QuerySuccess("B000006"),

    /**
     * 查询失败
     */
    QueryFailed("B000007"),

    /**
     * 数据校验失败
     */
    ValidateFailed("B000008"),

    /**
     * 默认的无参系统异常
     */
    S000000("S000000"),

    /**
     * 默认的有参系统异常
     */
    S000001("S000001"),

    /**
     * 需要登陆后访问
     */
    NeedLogin("S000002"),

    /**
     * 需要授权后访问
     */
    Unauthorized("S000003"),

    /**
     * 签名错误
     */
    SignError("S000004"),

    /**
     * 参数错误
     */
    ParamsError("S000005");


    // 实际的业务编码
    private final String CODE;

    BizCodeDef(String code) {
        this.CODE = code;
    }

    @Override
    public String getCode() {
        return this.CODE;
    }

    @Override
    public void initialize(IBizCodeSetter setter) {
        setter.set(SaveSuccess, "保存成功");
        setter.set(SaveFailed, "保存失败");
        setter.set(UpdateSuccess, "更新成功");
        setter.set(UpdateFailed, "更新失败");
        setter.set(DeleteSuccess, "删除成功");
        setter.set(DeleteFailed, "删除失败");
        setter.set(QuerySuccess, "查询成功");
        setter.set(QueryFailed, "查询失败");
        setter.set(ValidateFailed, "数据非法：{0}");
        setter.set(S000000, "系统异常，请跟管理员联系");
        setter.set(S000001, "系统异常，请跟管理员联系，信息：{0}");
        setter.set(NeedLogin, "请先登陆后访问");
        setter.set(Unauthorized, "没有访问权限，请联系管理员");
        setter.set(SignError, "签名错误");
        setter.set(ParamsError, "参数非法：{0}");
    }

}
