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

package com.wee0.box.subject.impl;

import com.wee0.box.exception.BoxRuntimeException;
import com.wee0.box.subject.IServletRequestSignChecker;
import com.wee0.box.util.shortcut.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;

import static com.wee0.box.BoxConstants.UTF8;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2020/2/23 7:52
 * @Description 一个简单的Servlet环境请求对象签名校验者实现
 * <pre>
 * 签名计算方法：
 * 1. 获取请求中的sign参数值，作为客户端签名结果，与服务端签名结果进行比对。
 * 2. 将请求中的所有参数名不为空的参数，排除sign参数后，以字母升序排列。
 * 3. 将排列后的所有参数，以key1=val1&amp;key2=val2方式连接为一个完整的数据字符串，当val有多个值时以逗号作为分隔符连接。
 * 4. 将数据字符串以HmacSHA256摘要算法进行计算，获取计算结果的16进制小写字符串作为签名与请求的签名值进行比对。
 * 注意：
 * 所有参数值去除头尾空白字符。
 * 当参数值为空时，使用空字符串代替。
 * 当一个参数有多个值时，以逗号作为分隔符连接，每个空参数值使用空字符串代替。
 * </pre>
 **/
public class SimpleServletRequestSignChecker implements IServletRequestSignChecker {

    // 密钥
    private String _secretKey;

    @Override
    public void setSecretKey(String secretKey) {
        this._secretKey = secretKey;
    }

    @Override
    public boolean check(HttpServletRequest request) {
        // 获取当前请求处理过程中使用的密钥
        String _secretKey = StringUtils.parseString(request.getAttribute(KEY_SECRET_KEY), this._secretKey);
        request.removeAttribute(KEY_SECRET_KEY);

        String _sign = StringUtils.trimEmptyDef(request.getParameter(KEY_SIGN), null);
        if (null == _sign)
            return false;

        // 获取请求参数数据
        TreeMap<String, String> _data = new TreeMap<>();
        Map<String, String[]> _parameters = request.getParameterMap();
        for (Map.Entry<String, String[]> _entry : _parameters.entrySet()) {
            String _key = _entry.getKey();
            if (null == _key || 0 == (_key = _key.trim()).length())
                continue;
            if (KEY_SIGN.equals(_key))
                continue;

            String[] _values = _entry.getValue();
            if (null == _values || 0 == _values.length) {
                _data.put(_key, "");
            } else if (1 == _values.length) {
                _data.put(_key, StringUtils.trimEmptyDef(_values[0], ""));
            } else {
                StringBuilder _valueBuilder = new StringBuilder();
                for (String _value : _values) {
                    _valueBuilder.append(StringUtils.trimEmptyDef(_value, "")).append(',');
                }
                if (',' == _valueBuilder.charAt(_valueBuilder.length() - 1))
                    _valueBuilder.deleteCharAt(_valueBuilder.length() - 1);
                _data.put(_key, _valueBuilder.toString());
            }
        }

        // 将签名数据组装成待签名文本
        StringBuilder _sb = new StringBuilder();
        _data.forEach((_key, _val) -> {
            _sb.append(_key).append("=").append(_val).append("&");
        });
        if ('&' == _sb.charAt(_sb.length() - 1))
            _sb.deleteCharAt(_sb.length() - 1);
        String _dataString = _sb.toString();

        String _sign1 = _doHmacSHA256Hex(_dataString, _secretKey);
        return _sign.equals(_sign1);
    }

    // 执行HmacSHA256Hex算法，获取摘要结果。
    private static String _doHmacSHA256Hex(String data, String _key) {
        try {
            Mac _mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec _secretKeySpec = new SecretKeySpec(_key.getBytes(UTF8), _mac.getAlgorithm());
            _mac.init(_secretKeySpec);
            byte[] _resultBytes = _mac.doFinal(data.getBytes(UTF8));
            String _resultHex = DatatypeConverter.printHexBinary(_resultBytes);
            return _resultHex.toLowerCase();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException | InvalidKeyException e) {
            throw new BoxRuntimeException("crypto error!", e);
        }
    }

}
