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

package com.wee0.box.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/12/15 7:02
 * @Description Http处理工具
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface IHttpUtils {

    /**
     * 默认的实现类名称
     */
    String DEF_IMPL_CLASS_NAME = "com.wee0.box.util.impl.SimpleHttpUtils";

    /**
     * 默认使用的字符集编码
     */
    String DEF_CHARSET = "UTF-8";

    /**
     * 默认的请求超时时间，10秒。
     */
    int DEF_TIMEOUT = 10000;

    /**
     * 请求方法
     */
    enum METHOD {
        GET, POST, PUT, DELETE
    }

    /**
     * 响应结果对象
     */
    interface IHttpResult {

        /**
         * @return 响应代码
         */
        int getCode();

        /**
         * @return 响应内容
         */
        String getContent();

        /**
         * @return 响应头信息集合
         */
        Map<String, List<String>> getHeaders();
    }

    /**
     * 执行http请求动作。
     *
     * @param method   请求方法
     * @param url      请求地址
     * @param sendData 发送数据
     * @param headMap  请求头信息
     * @param charset  使用的编码
     * @param timeout  超时时间
     * @return 响应结果
     */
    IHttpResult httpAction(METHOD method, String url, Consumer<OutputStream> sendData, Map<String, String> headMap, String charset, int timeout);

    /**
     * 使用默认请求方法执行http请求动作。
     *
     * @param url     请求地址
     * @param data    发送的内容
     * @param headMap 请求头信息
     * @param charset 使用的编码
     * @param timeout 超时时间
     * @return 响应结果
     */
    default IHttpResult httpAction(String url, String data, Map<String, String> headMap, String charset, int timeout) {
        if (null == data)
            return httpAction(METHOD.POST, url, null, headMap, charset, timeout);
        return httpAction(METHOD.POST, url, (_outStream) -> {
            try {
                _outStream.write(data.getBytes(charset));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, headMap, charset, timeout);
    }

    /**
     * 使用默认请求方法执行http请求动作。
     *
     * @param url     请求地址
     * @param data    发送的内容
     * @param headMap 请求头信息
     * @param charset 使用的编码
     * @return 响应结果
     */
    default IHttpResult httpAction(String url, String data, Map<String, String> headMap, String charset) {
        return httpAction(url, data, headMap, charset, DEF_TIMEOUT);
    }

    /**
     * 使用默认请求方法执行http请求动作。
     *
     * @param url     请求地址
     * @param headMap 请求头信息
     * @param data    发送的内容
     * @return 响应结果
     */
    default IHttpResult httpAction(String url, String data, Map<String, String> headMap) {
        return httpAction(url, data, headMap, DEF_CHARSET, DEF_TIMEOUT);
    }

    /**
     * 使用默认请求方法执行http请求动作。
     *
     * @param url  请求地址
     * @param data 发送的内容
     * @return 响应结果
     */
    default IHttpResult httpAction(String url, String data) {
        return httpAction(url, data, null, DEF_CHARSET, DEF_TIMEOUT);
    }

    /**
     * 使用默认请求方法执行http请求动作。
     *
     * @param url     请求地址
     * @param headMap 请求头信息
     * @return 响应结果
     */
    default IHttpResult httpAction(String url, Map<String, String> headMap) {
        return httpAction(METHOD.POST, url, null, headMap, DEF_CHARSET, DEF_TIMEOUT);
    }

    /**
     * 执行一个简单的GET请求
     *
     * @param url 请求地址
     * @return 响应结果
     */
    default IHttpResult httpGet(String url) {
        return httpAction(METHOD.GET, url, null, null, DEF_CHARSET, DEF_TIMEOUT);
    }

    /**
     * 执行一个简单的POST请求
     *
     * @param url 请求地址
     * @return 响应结果
     */
    default IHttpResult httpPost(String url) {
        return httpAction(METHOD.POST, url, null, null, DEF_CHARSET, DEF_TIMEOUT);
    }

    /**
     * 模拟表单上传，当前版本的formData集合中遇到File类型对象时作为文件上传，其它类型对象统一作为文本处理。
     *
     * @param method   请求方法
     * @param url      请求地址
     * @param headMap  请求头信息
     * @param formData 请求数据
     * @param charset  使用的编码
     * @param timeout  超时时间
     * @return 响应结果
     */
    IHttpResult formUpload(METHOD method, String url, Map<String, String> headMap, Map<String, Object> formData, String charset, int timeout);

    /**
     * 模拟表单上传
     *
     * @param url      请求地址
     * @param formData 请求数据
     * @param timeout  超时时间
     * @return 响应结果
     */
    default IHttpResult formUpload(String url, Map<String, Object> formData, int timeout) {
        return formUpload(METHOD.POST, url, null, formData, DEF_CHARSET, timeout);
    }

    /**
     * 模拟表单上传
     *
     * @param url      请求地址
     * @param formData 请求数据
     * @return 响应结果
     */
    default IHttpResult formUpload(String url, Map<String, Object> formData) {
        return formUpload(METHOD.POST, url, null, formData, DEF_CHARSET, DEF_TIMEOUT);
    }

    /**
     * 上传资源
     *
     * @param method   请求方法
     * @param url      请求地址
     * @param headMap  请求头信息
     * @param inStream 资源流
     * @param timeout  超时时间
     * @return 响应结果
     */
    IHttpResult httpUpload(METHOD method, String url, Map<String, String> headMap, InputStream inStream, int timeout);

    /**
     * 使用Post请求上传资源
     *
     * @param url      请求地址
     * @param headMap  请求头信息
     * @param inStream 资源流
     * @param timeout  超时时间
     * @return 响应结果
     */
    default IHttpResult httpUpload(String url, Map<String, String> headMap, InputStream inStream, int timeout) {
        return httpUpload(METHOD.POST, url, headMap, inStream, timeout);
    }

    /**
     * 使用Post请求上传资源
     *
     * @param url      请求地址
     * @param headMap  请求头信息
     * @param inStream 资源流
     * @return 响应结果
     */
    default IHttpResult httpUpload(String url, Map<String, String> headMap, InputStream inStream) {
        return httpUpload(METHOD.POST, url, headMap, inStream, DEF_TIMEOUT);
    }

    /**
     * 使用Post请求上传资源
     *
     * @param url      请求路径
     * @param inStream 资源流
     * @return 响应结果
     */
    default IHttpResult httpUpload(String url, InputStream inStream) {
        return httpUpload(METHOD.POST, url, null, inStream, DEF_TIMEOUT);
    }

    /**
     * 下载资源
     *
     * @param method    请求方法
     * @param url       请求地址
     * @param headMap   请求头信息
     * @param outStream 输出流
     * @param timeout   超时时间
     * @return 响应结果
     */
    IHttpResult httpDownload(METHOD method, String url, Map<String, String> headMap, OutputStream outStream, int timeout);

    /**
     * 下载资源
     *
     * @param url       请求地址
     * @param headMap   请求头信息
     * @param outStream 输出流
     * @param timeout   超时时间
     * @return 响应结果
     */
    default IHttpResult httpDownload(String url, Map<String, String> headMap, OutputStream outStream, int timeout) {
        return httpDownload(METHOD.GET, url, headMap, outStream, timeout);
    }

    /**
     * 下载资源
     *
     * @param url       请求地址
     * @param headMap   请求头信息
     * @param outStream 输出流
     * @return 响应结果
     */
    default IHttpResult httpDownload(String url, Map<String, String> headMap, OutputStream outStream) {
        return httpDownload(METHOD.GET, url, headMap, outStream, DEF_TIMEOUT);
    }

    /**
     * 下载资源
     *
     * @param url       请求地址
     * @param outStream 输出流
     * @return 响应结果
     */
    default IHttpResult httpDownload(String url, OutputStream outStream) {
        return httpDownload(METHOD.GET, url, null, outStream, DEF_TIMEOUT);
    }

}
