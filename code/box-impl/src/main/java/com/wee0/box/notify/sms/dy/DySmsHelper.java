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

package com.wee0.box.notify.sms.dy;

import com.wee0.box.BoxConfig;
import com.wee0.box.BoxConstants;
import com.wee0.box.exception.BoxRuntimeException;
import com.wee0.box.impl.BoxConfigKeys;
import com.wee0.box.notify.sms.ISmsHelper;
import com.wee0.box.struct.CMD;
import com.wee0.box.struct.CmdFactory;
import com.wee0.box.util.IHttpUtils;
import com.wee0.box.util.shortcut.CheckUtils;
import com.wee0.box.util.shortcut.HttpUtils;
import com.wee0.box.util.shortcut.JsonUtils;

import java.io.ObjectStreamException;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2020/3/28 8:06
 * @Description 基于阿里大于短信平台的短信操作助手实现
 * <pre>
 * 补充说明
 * </pre>
 **/
public class DySmsHelper implements ISmsHelper {

//    // 日志对象
//    private static ILogger log = LoggerFactory.getLogger(DySmsHelper.class);

    // 默认的请求端点
    static final String DEF_ENDPOINT = "dysmsapi.aliyuncs.com";
    // 默认编码
    static final String DEF_ENCODING = "UTF-8";
    // 默认算法
    static final String DEF_ALGORITHM = "HmacSHA1";

    // 请求端点
    final String _endpoint;
    // 访问密钥标识
    final String _accessKeyId;
    // 访问密钥
    final String _accessSecret;
    // 默认使用的签名名称
    final String _defSignName;
    // 默认使用的短信模板编码
    final String _defTemplateCode;

    final Key _key;

    /**
     * 发送短信
     *
     * @param params 请求参数集合，参考官方文档
     * @return 响应结果
     */
    public CMD sendSms(Map<String, String> params) {
        if (!params.containsKey("PhoneNumbers")) throw new IllegalArgumentException("PhoneNumbers can not be empty!");
        if (!params.containsKey("Action")) params.put("Action", "SendSms");
        if (!params.containsKey("SignName")) {
            if (null == this._defSignName) throw new IllegalArgumentException("PhoneNumbers can not be empty!");
            params.put("SignName", this._defSignName);
        }
        if (!params.containsKey("TemplateCode")) {
            if (null == this._defTemplateCode) throw new IllegalArgumentException("TemplateCode can not be empty!");
            params.put("TemplateCode", this._defTemplateCode);
        }
        // 因为要处理返回结果，所以强制使用json返回格式
        params.put("Format", "json");
        String _postUrl = buildPostUrl(params);
        IHttpUtils.IHttpResult _result = HttpUtils.impl().httpPost(_postUrl);
        return parseHttpResult(_result);
    }

    /**
     * 查询短信发送结果
     *
     * @param params 请求参数集合，参考官方文档
     * @return 响应结果
     */
    public CMD querySendSmsDetail(Map<String, String> params) {
        if (!params.containsKey("PhoneNumber")) throw new IllegalArgumentException("PhoneNumber can not be empty!");
//        if (!params.containsKey("BizId")) throw new IllegalArgumentException("BizId can not be empty!");
        if (!params.containsKey("Action")) params.put("Action", "QuerySendDetails");
        if (!params.containsKey("PageSize")) params.put("PageSize", "10");
        if (!params.containsKey("CurrentPage")) params.put("CurrentPage", "1");
        if (!params.containsKey("SendDate")) params.put("SendDate", new SimpleDateFormat("yyyyMMdd").format(new Date()));
        // 因为要处理返回结果，所以强制使用json返回格式
        params.put("Format", "json");
        String _postUrl = buildPostUrl(params);
        IHttpUtils.IHttpResult _result = HttpUtils.impl().httpPost(_postUrl);
        return parseHttpResult(_result);
    }

    /**
     * 根据指定请求参数集合创建一个完整的GET请求路径
     *
     * @param params 参数集合
     * @return 完整的GET请求路径
     */
    public String buildGetUrl(Map<String, String> params) {
        String _queryString = _buildQueryString(params);
        String _signature = _buildSignature(_queryString);
        String _getUrl = _buildUrl(_queryString, _signature);
        return _getUrl;
    }

    /**
     * 根据指定请求参数集合创建一个完整的POST请求路径
     *
     * @param params 参数集合
     * @return 完整的Get请求路径
     */
    public String buildPostUrl(Map<String, String> params) {
        String _queryString = _buildQueryString(params);
        String _signature = _buildSignature(_queryString, "POST");
        String _getUrl = _buildUrl(_queryString, _signature);
        return _getUrl;
    }

    /**
     * 阿里云短信算法指定的UrlEncode格式
     *
     * @param value 原始值
     * @return 格式化后的值
     */
    public String specialUrlEncode(String value) {
        try {
            return java.net.URLEncoder.encode(value, "UTF-8").replace("+", "%20").replace("*", "%2A").replace("%7E", "~");
        } catch (UnsupportedEncodingException e) {
            throw new BoxRuntimeException(e);
        }
    }

    /**
     * 阿里云短信算法指定的日期格式
     *
     * @param date 原始日期
     * @return 格式化后的值
     */
    public String specialDateFormat(Date date) {
        java.text.SimpleDateFormat _sdf = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        _sdf.setTimeZone(new java.util.SimpleTimeZone(0, "GMT"));// 这里一定要设置GMT时区
        return _sdf.format(date);
    }

    /**
     * 解析返回结果，转换为平台标准的CMD类型数据
     *
     * @param httpResult 原始返回结果
     * @return 标准的CMD类型数据
     */
    protected CMD parseHttpResult(IHttpUtils.IHttpResult httpResult) {
        Map<String, Object> _result;
        if (null == httpResult || null == httpResult.getContent() || null == (_result = JsonUtils.readToMap(httpResult.getContent()))) {
            return CmdFactory.create("404", "result not found.");
        }
        String _resultCode = (String) _result.get("Code");
        if ("OK".equals(_resultCode))
            return CmdFactory.create(_result);
        return CmdFactory.create("500", "result Code is not OK.", _result);
    }

    /**
     * 构建查询字符串
     *
     * @param params 参数集合
     * @return 查询字符串
     */
    protected String _buildQueryString(Map<String, String> params) {
        if (null == params || params.isEmpty()) return BoxConstants.EMPTY_STRING;

        // 根据参数Key排序（升序）
        TreeMap<String, String> _params = new TreeMap(params);
        if (_params.containsKey("Signature"))
            _params.remove("Signature");
        if (_params.isEmpty()) return BoxConstants.EMPTY_STRING;

        // 统一时间格式
        Object _timestampObj = _params.get("Timestamp");
        if (null == _timestampObj) {
            _params.put("Timestamp", specialDateFormat(new Date()));
        } else if (_timestampObj instanceof Date) {
            _params.put("Timestamp", specialDateFormat((Date) _timestampObj));
        }

        // 一些未指定参数的默认值设置
        // 签名方法
        if (!_params.containsKey("SignatureMethod")) {
            _params.put("SignatureMethod", "HMAC-SHA1");
        }
        // 签名版本
        if (!_params.containsKey("SignatureVersion")) {
            _params.put("SignatureVersion", "1.0");
        }
        // 签名随机数
        if (!_params.containsKey("SignatureNonce")) {
            _params.put("SignatureNonce", java.util.UUID.randomUUID().toString());
        }
        if (!_params.containsKey("Version")) {
            _params.put("Version", "2017-05-25");
        }
        if (!_params.containsKey("RegionId")) {
            _params.put("RegionId", "cn-hangzhou");
        }
        if (!_params.containsKey("Format")) {
            _params.put("Format", "json"); // json, XML
        }
        if (!_params.containsKey("AccessKeyId")) {
            _params.put("AccessKeyId", this._accessKeyId);
        }

        StringBuilder _sb = new StringBuilder();
        for (Map.Entry<String, String> _entry : _params.entrySet()) {
            String _key = _entry.getKey();
            String _value = _entry.getValue();
            _sb.append("&").append(specialUrlEncode(_key)).append("=").append(specialUrlEncode(_value));
        }
        return _sb.substring(1);// 去除第一个多余的&符号
    }

    // 构建签名信息
    protected String _buildSignature(String queryString, String httpMethod) {
        if (null == httpMethod) httpMethod = "GET";
        StringBuilder _sb = new StringBuilder();
        _sb.append(httpMethod).append("&");
        _sb.append(specialUrlEncode("/")).append("&");
        _sb.append(specialUrlEncode(queryString));

        String _signature;
        try {
            javax.crypto.Mac _mac = javax.crypto.Mac.getInstance(DEF_ALGORITHM);
            _mac.init(this._key);
            byte[] _signData = _mac.doFinal(_sb.toString().getBytes(DEF_ENCODING));
            _signature = new sun.misc.BASE64Encoder().encode(_signData);
        } catch (Exception e) {
            throw new BoxRuntimeException(e);
        }

        return specialUrlEncode(_signature);
    }

    // 构建签名信息
    protected String _buildSignature(String queryString) {
        return _buildSignature(queryString, null);
    }

    // 构建完整请求地址
    protected String _buildUrl(String queryString, String signature) {
        return "http://".concat(this._endpoint).concat("/?Signature=").concat(signature).concat("&").concat(queryString);
    }

    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private DySmsHelper() {
        if (null != DySmsHelperHolder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }
        Map<String, String> _smsConfig = BoxConfig.impl().getByPrefix(BoxConfigKeys.smsPrefix);
//        log.debug("smsConfig: {}", _smsConfig);
        if (null == _smsConfig || _smsConfig.isEmpty()) throw new IllegalArgumentException("invalid sms config!");
        this._endpoint = CheckUtils.checkTrimEmpty(_smsConfig.get("endpoint"), DEF_ENDPOINT);
        this._accessKeyId = CheckUtils.checkNotTrimEmpty(_smsConfig.get("accessKeyId"), "accessKeyId can't be empty!");
        this._accessSecret = CheckUtils.checkNotTrimEmpty(_smsConfig.get("accessSecret"), "accessSecret can't be empty!");
        this._defSignName = CheckUtils.checkTrimEmpty(_smsConfig.get("defaultSignName"), null);
        this._defTemplateCode = CheckUtils.checkTrimEmpty(_smsConfig.get("defaultTemplateCode"), null);
        try {
            this._key = new javax.crypto.spec.SecretKeySpec((this._accessSecret + "&").getBytes(DEF_ENCODING), DEF_ALGORITHM);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }

    // 当前对象唯一实例持有者。
    private static final class DySmsHelperHolder {
        private static final DySmsHelper _INSTANCE = new DySmsHelper();
    }

    // 防止使用反序列化操作获取多个对象实例。
    private Object readResolve() throws ObjectStreamException {
        return DySmsHelperHolder._INSTANCE;
    }

    /**
     * 获取当前对象唯一实例。
     *
     * @return 当前对象唯一实例
     */
    public static DySmsHelper me() {
        return DySmsHelperHolder._INSTANCE;
    }

}
