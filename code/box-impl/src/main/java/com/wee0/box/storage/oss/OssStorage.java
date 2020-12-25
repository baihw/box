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

package com.wee0.box.storage.oss;

import com.wee0.box.BoxConfig;
import com.wee0.box.impl.BoxConfigKeys;
import com.wee0.box.log.ILogger;
import com.wee0.box.log.LoggerFactory;
import com.wee0.box.storage.IStorage;
import com.wee0.box.util.shortcut.CheckUtils;
import com.wee0.box.util.shortcut.StringUtils;

import java.io.ObjectStreamException;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.time.Instant;
import java.util.*;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2020/5/17 7:22
 * @Description 基于阿里OSS云存储实现的存储操作对象
 * <pre>
 * 补充说明
 * </pre>
 **/
public class OssStorage implements IStorage {

    private static final ILogger _LOG = LoggerFactory.getLogger(OssStorage.class);

    // 默认的请求端点
    static final String DEF_ENDPOINT = "oss-cn-shanghai.aliyuncs.com";
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
    // 默认使用的存储桶名称
    final String _defBucketName;

    final Key _key;

    /**
     * 获取公开访问资源请求地址
     *
     * @param bucket   存储桶
     * @param resource 资源完整标识
     * @return 公开访问资源请求地址
     */
    public String getUrl(String bucket, String resource) {
        bucket = CheckUtils.checkNotTrimEmpty(bucket, "bucket cannot be empty!");
        resource = CheckUtils.checkNotTrimEmpty(resource, "resource cannot be empty!");
        StringBuilder _builder = new StringBuilder();
        _builder.append("https://");
        _builder.append(bucket);
        _builder.append('.');
        _builder.append(_endpoint);
        _builder.append(resource);
        return _builder.toString();
    }

    // 生成预签名的请求地址
    String generatePreSignedUrl(String bucket, String resource, String verb, Map<String, String> params, Map<String, String> ossHeaders) {
        final String _fullResource = _buildFullResource(resource, params);
        long _expire = Instant.now().getEpochSecond() + 3600;
        String _signature = _buildSignature(bucket, _fullResource, _expire, verb, params, ossHeaders, null, "application/octet-stream");

        StringBuilder _builder = new StringBuilder();
        // "Authorization: OSS " + _ACCESS_ID + ":" + signature;
        _builder.append("http://");
        if (null != bucket)
            _builder.append(bucket).append('.');
        _builder.append(this._endpoint).append(_fullResource);
        if (-1 == _builder.indexOf("?"))
            _builder.append('?');
        else
            _builder.append('&');
        // Expires=**&Signature=**&OSSAccessKeyId=**&Signature=**
        _builder.append("Expires=").append(_expire);
        _builder.append("&OSSAccessKeyId=").append(this._accessKeyId);
        _builder.append("&Signature=").append(_signature);
        return _builder.toString();
    }

	// curl  -v -H "Authorization: OSS xxxxxxxxxxx:5wgVeFhJ4ZhX4RGm2Snup1H8Hs4=" -H "Date: Mon, 18 May 2020 08:08:21 GMT" http://xxx.oss-cn-shanghai.aliyuncs.com/?max-keys=1
    // curl  -v -H "Authorization: OSS xxxxxxxxxxx:9kvucF2fm7PxYd+pWsFOXZJFmh0=" -H "Date: Mon, 18 May 2020 08:11:01 GMT" http://xxx.oss-cn-shanghai.aliyuncs.com/test/start.jpg?acl
    // 创建一个可直接执行的curl请求命令。
    String generateCurlAuthorization(String bucket, String resource, String verb, Map<String, String> params, Map<String, String> ossHeaders) {
        final String _fullResource = _buildFullResource(resource, params);
        String _date = _specialDateFormat(new Date());
        String _authorization = _buildAuthorization(bucket, _fullResource, _date, verb, params, ossHeaders, null, null);

        StringBuilder _builder = new StringBuilder();
        _builder.append("curl -v ");
        // "Authorization: OSS " + _ACCESS_ID + ":" + signature;
        _builder.append("-H \"Authorization: OSS ").append(this._accessKeyId).append(':').append(_authorization).append("\" ");
        _builder.append("-H \"Date: ").append(_date).append("\" ");
        _builder.append("http://");
        if (null != bucket)
            _builder.append(bucket).append('.');
        _builder.append(this._endpoint).append(_fullResource);
        return _builder.toString();
    }

    // 构建签名信息
    protected String _buildSignature(String bucket, String fullResource, long expire, String verb, Map<String, String> params, Map<String, String> ossHeaders, String contentMd5, String contentType) {
        // security-token=SecurityToken
        bucket = CheckUtils.checkTrimEmpty(bucket, null);
        fullResource = CheckUtils.checkNotTrimEmpty(fullResource, "fullResource cannot be empty!");
        verb = CheckUtils.checkTrimEmpty(verb, "GET");
        contentMd5 = CheckUtils.checkTrimEmpty(contentMd5, null);
        contentType = CheckUtils.checkTrimEmpty(contentType, null);

        StringBuilder _builder = new StringBuilder();
        _builder.append(null == verb ? "GET" : verb).append('\n');
        if (null != contentMd5)
            _builder.append(contentMd5);
        _builder.append('\n');
        if (null != contentType)
            _builder.append(contentType);
        _builder.append('\n');
        long _epochSecond = Instant.now().getEpochSecond();
        _builder.append(expire > _epochSecond ? expire : _epochSecond + 3600).append('\n');
        String _headers = _buildOssHeaders(ossHeaders);
        if (null != _headers)
            _builder.append(_headers).append('\n');
        if (null != bucket) {
            _builder.append('/').append(bucket);
            _builder.append(fullResource);
        } else {
            _builder.append('/');
        }

        String _data = _builder.toString();
        String _signature = _specialSignature(this._key, _data, DEF_ENCODING);
        _signature = _specialUrlEncode(_signature);
        return _signature;
    }

    // 构建授权信息头
    protected String _buildAuthorization(String bucket, String fullResource, String date, String verb, Map<String, String> params, Map<String, String> ossHeaders, String contentMd5, String contentType) {
//        bucket = CheckUtils.checkNotTrimEmpty(bucket, "bucket cannot be empty!");
        bucket = CheckUtils.checkTrimEmpty(bucket, null);
        fullResource = CheckUtils.checkNotTrimEmpty(fullResource, "fullResource cannot be empty!");
        verb = CheckUtils.checkTrimEmpty(verb, "GET");
        contentMd5 = CheckUtils.checkTrimEmpty(contentMd5, null);
        contentType = CheckUtils.checkTrimEmpty(contentType, null);

        StringBuilder _builder = new StringBuilder();
        _builder.append(null == verb ? "GET" : verb).append('\n');
        if (null != contentMd5)
            _builder.append(contentMd5);
        _builder.append('\n');
        if (null != contentType)
            _builder.append(contentType);
        _builder.append('\n');
        _builder.append(date).append('\n');
        String _headers = _buildOssHeaders(ossHeaders);
        if (null != _headers)
            _builder.append(_headers).append('\n');
        if (null != bucket) {
            _builder.append('/').append(bucket);
            _builder.append(fullResource);
        } else {
            _builder.append('/');
        }

        String _data = _builder.toString();
        String _signature = _specialSignature(this._key, _data, DEF_ENCODING);
        return _signature;
    }

    // 资源请求参数部分拼接为待签名的完整字符串。
    static String _buildFullResource(String resource, Map<String, String> params) {
        resource = CheckUtils.checkTrimEmpty(resource, "/");
        resource = StringUtils.startsWithChar(resource, '/');

        StringBuilder _builder = new StringBuilder();
        _builder.append(resource);

        if (null == params || params.isEmpty()) return _builder.toString();

        _builder.append('?');
        // 根据参数Key排序（升序）
        TreeMap<String, String> _params = new TreeMap(params);
        for (Map.Entry<String, String> _entry : _params.entrySet()) {
            String _key = _entry.getKey();
            String _value = _entry.getValue();
            if ('?' != _builder.charAt(_builder.length() - 1))
                _builder.append('&');
            _builder.append(_key).append("=").append(_value);
        }
        return _builder.toString();
    }

    // 资源请求头参数部分拼接为待签名的完整字符串。
    static String _buildOssHeaders(Map<String, String> ossHeaders) {
        if (null == ossHeaders || ossHeaders.isEmpty()) return null;

        // 根据参数Key排序（升序）
        TreeMap<String, String> _headers = new TreeMap();
        Iterator<Map.Entry<String, String>> _entries = ossHeaders.entrySet().iterator();
        while (_entries.hasNext()) {
            Map.Entry<String, String> _entry = _entries.next();
            String _entryKey = _entry.getKey();
            if (null == _entryKey)
                continue;
            _entryKey = _entryKey.toLowerCase();
            if (0 != _entryKey.indexOf("x-oss-"))
                continue;

            _headers.put(_entryKey, _entry.getValue());
        }

        StringBuilder _sb = new StringBuilder();
        for (Map.Entry<String, String> _entry : _headers.entrySet()) {
            String _key = _entry.getKey();
            String _value = _entry.getValue();
            _sb.append("&").append(_key).append("=").append(_value);
        }
        return _sb.substring(1);// 去除第一个多余的&符号
    }

    // 阿里云算法指定的签名方式
    static String _specialSignature(Key key, String data, String encoding) {
        try {
            javax.crypto.Mac _mac = javax.crypto.Mac.getInstance(DEF_ALGORITHM);
            _mac.init(key);
            byte[] _signData = _mac.doFinal(data.getBytes(encoding));
            String _signature = new sun.misc.BASE64Encoder().encode(_signData);
//            _LOG.trace("data: {}, sign: {}", data, _signature);
            return _signature;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 阿里云算法指定的日期格式
     *
     * @param date 原始日期
     * @return 格式化后的值
     */
    static String _specialDateFormat(Date date) {
        java.text.SimpleDateFormat _sdf = new java.text.SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        _sdf.setTimeZone(new java.util.SimpleTimeZone(0, "GMT"));// 这里一定要设置GMT时区
        return _sdf.format(date);
    }

    /**
     * 阿里云短信算法指定的UrlEncode格式
     *
     * @param value 原始值
     * @return 格式化后的值
     */
    static String _specialUrlEncode(String value) {
        try {
            return java.net.URLEncoder.encode(value, DEF_ENCODING);
//            return java.net.URLEncoder.encode(value, DEF_ENCODING).replace("+", "%20").replace("*", "%2A").replace("%7E", "~");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private OssStorage() {
        if (null != OssStorageHolder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }

        Map<String, String> _storageConfig = BoxConfig.impl().getByPrefix(BoxConfigKeys.storagePrefix);
        if (null == _storageConfig || _storageConfig.isEmpty()) throw new IllegalArgumentException("invalid storage config!");
        this._endpoint = CheckUtils.checkTrimEmpty(_storageConfig.get("endpoint"), DEF_ENDPOINT);
        this._accessKeyId = CheckUtils.checkNotTrimEmpty(_storageConfig.get("accessKeyId"), "accessKeyId can't be empty!");
        this._accessSecret = CheckUtils.checkNotTrimEmpty(_storageConfig.get("accessSecret"), "accessSecret can't be empty!");
        this._defBucketName = CheckUtils.checkTrimEmpty(_storageConfig.get("defaultBucketName"), null);
        try {
            this._key = new javax.crypto.spec.SecretKeySpec(this._accessSecret.getBytes(DEF_ENCODING), DEF_ALGORITHM);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }

    // 当前对象唯一实例持有者。
    private static final class OssStorageHolder {
        private static final OssStorage _INSTANCE = new OssStorage();
    }

    // 防止使用反序列化操作获取多个对象实例。
    private Object readResolve() throws ObjectStreamException {
        return OssStorageHolder._INSTANCE;
    }

    /**
     * 获取当前对象唯一实例。
     *
     * @return 当前对象唯一实例
     */
    public static OssStorage me() {
        return OssStorageHolder._INSTANCE;
    }
}
