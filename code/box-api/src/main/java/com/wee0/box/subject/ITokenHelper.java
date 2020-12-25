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

package com.wee0.box.subject;

import com.wee0.box.util.shortcut.CheckUtils;

import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2020/5/16 7:02
 * @Description 令牌操作助手
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface ITokenHelper {

    /**
     * 算法名称
     */
    String ALGORITHM_DES = "DES";
    String ALGORITHM_DESEDE = "DESede";
    String ALGORITHM_AES = "AES";
    String ALGORITHM_BLOWFISH = "Blowfish";
    String ALGORITHM_RC2 = "RC2";
    String ALGORITHM_RC4 = "RC4";

    /**
     * 默认使用的算法名称。
     */
    String DEF_ALGORITHM = ALGORITHM_DES;

    /**
     * 编码令牌
     *
     * @param data      数据
     * @param secret    密钥
     * @param algorithm 算法名称
     * @return 令牌
     */
    String encode(Map<String, Object> data, String secret, String algorithm);

    /**
     * 编码令牌
     *
     * @param data   数据
     * @param secret 密钥
     * @return 令牌
     */
    default String encode(Map<String, Object> data, String secret) {
        String _algorithm = CheckUtils.checkTrimEmpty(getAlgorithm(), DEF_ALGORITHM);
        return encode(data, secret, _algorithm);
    }

    /**
     * 编码令牌
     *
     * @param data 数据
     * @return 令牌
     */
    default String encode(Map<String, Object> data) {
        String _algorithm = CheckUtils.checkTrimEmpty(getAlgorithm(), DEF_ALGORITHM);
        return encode(data, getSecret(), _algorithm);
    }

    /**
     * 解码令牌
     *
     * @param token     令牌
     * @param secret    密钥
     * @param algorithm 算法名称
     * @return 数据
     */
    Map<String, Object> decode(String token, String secret, String algorithm);

    /**
     * 解码令牌
     *
     * @param token  令牌
     * @param secret 密钥
     * @return 数据
     */
    default Map<String, Object> decode(String token, String secret) {
        String _algorithm = CheckUtils.checkTrimEmpty(getAlgorithm(), DEF_ALGORITHM);
        return decode(token, secret, _algorithm);
    }

    /**
     * 解码令牌
     *
     * @param token 令牌
     * @return 数据
     */
    default Map<String, Object> decode(String token) {
        String _algorithm = CheckUtils.checkTrimEmpty(getAlgorithm(), DEF_ALGORITHM);
        return decode(token, getSecret(), _algorithm);
    }

    /**
     * 设置默认密钥
     *
     * @param secret 默认密钥
     */
    void setSecret(String secret);

    /**
     * @return 获取默认密钥
     */
    String getSecret();

    /**
     * 设置默认算法名称
     *
     * @param algorithm 默认算法名称
     */
    void setAlgorithm(String algorithm);

    /**
     * @return 获取默认算法名称
     */
    String getAlgorithm();

}
