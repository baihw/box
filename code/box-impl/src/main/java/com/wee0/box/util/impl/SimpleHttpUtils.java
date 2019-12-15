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

package com.wee0.box.util.impl;

import com.wee0.box.log.ILogger;
import com.wee0.box.log.LoggerFactory;
import com.wee0.box.util.IHttpUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/12/15 7:12
 * @Description Http处理工具快捷入口
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SimpleHttpUtils implements IHttpUtils {

    // 日志对象
    private static ILogger log = LoggerFactory.getLogger(SimpleHttpUtils.class);

    /**
     * 默认的数据分隔线定义
     */
    public static final String DEF_BOUNDARY = "---------7d4a6d158c9";

    // 默认的请求头信息数据
    private final Map<String, String> DEF_HEAD_MAP;


    /**
     * http请求常用动作的处理。用此方法上传文件比较占用内存，如果需要上传比较大的文件，请使用httpUpload()方法。
     *
     * @param method   请求方式 { GET, POST, PUT, DELETE }
     * @param url      完整的url地址,如：http://www.wee0.com/index.jsp
     * @param headMap  附加的头数据信息
     * @param sendData 发送数据
     * @param charset  指定使用的编码
     * @param timeout  超时时间
     * @return 请求返回的结果。如果过程中出错，则返回null。
     */
    @Override
    public IHttpResult httpAction(METHOD method, String url, Consumer<OutputStream> sendData, Map<String, String> headMap, String charset, int timeout) {
        return _httpAction(method, url, sendData, _RES_HANDLER_COMMON, headMap, charset, timeout);
    }

    /**
     * 基于流的文件上传方式，比较节省内存。
     *
     * @param method   请求方式 { GET, POST, PUT, DELETE }
     * @param url      完整的url地址,如：http://localhost/index.jsp
     * @param headMap  附加的头数据信息
     * @param inStream 发送的数据流
     * @param timeout  超时时间
     * @return 请求返回的结果。
     */
    @Override
    public IHttpResult httpUpload(METHOD method, String url, Map<String, String> headMap, InputStream inStream, int timeout) {
        return httpAction(METHOD.POST, url, (_outStream) -> {
            copyStream(inStream, _outStream);
//				// 多个文件需要发送时，手动进行组装。
//				OutputStream out = new DataOutputStream(conn.getOutputStream());
//
//				byte[] end_data = ("\r\n--" + BOUNDARY + "--\r\n")
//						.getBytes();// 定义最后数据分隔线
//
//				StringBuilder sb1 = new StringBuilder();
//
//				sb1.append("--");
//				sb1.append(BOUNDARY);
//				sb1.append("\r\n");
//				sb1.append("Content-Disposition: form-data;filename=\"uploadFile\"\r\n");
//				sb1.append("Content-Type:application/octet-stream\r\n\r\n");
//
//				byte[] data = sb1.toString().getBytes("UTF-8");
//				out.write(data);
//				DataInputStream in = new DataInputStream(inStream);
//				int bytes = 0;
//				byte[] bufferOut = new byte[1024];
//				while ((bytes = in.read(bufferOut)) != -1) {
//					out.write(bufferOut, 0, bytes);
//				}
//			//	out.write("\r\n".getBytes()); // 多个文件时，二个文件之间加入这个
//				in.close();
//				out.write(end_data);
//				out.flush();
//				out.close();
        }, headMap, DEF_CHARSET, timeout);
    }

    @Override
    public IHttpResult httpDownload(METHOD method, String url, Map<String, String> headMap, OutputStream outStream, int timeout) {
        return _httpAction(method, url, null, (connection, charset) -> {
            try {
                int _responseCode = connection.getResponseCode();
//			    log.trace( "responseCode:" + responseCode ) ;
                SimpleHttpResult _result = new SimpleHttpResult();
                _result.setCode(_responseCode);

                if (_responseCode >= HttpURLConnection.HTTP_BAD_REQUEST) {
                    try (InputStream _inStream = connection.getErrorStream();
                         InputStreamReader _inReader = new InputStreamReader(_inStream, charset);
                         BufferedReader _bufReader = new BufferedReader(_inReader);) {
                        StringBuilder _builder = new StringBuilder();
                        // 读取流信息。
                        String _line = _bufReader.readLine();
                        while (null != _line) {
                            _builder.append(_line).append("\r\n");
                            _line = _bufReader.readLine();
                        }
                        _result.setContent(_builder.toString());
                    }
                } else if (HttpURLConnection.HTTP_OK == _responseCode || HttpURLConnection.HTTP_PARTIAL == _responseCode) {
                    try (InputStream _inStream = connection.getInputStream();) {
                        copyStream(_inStream, outStream);
                    }
                }
                _result.setHeaders(connection.getHeaderFields());
                return _result;
            } catch (IOException e) {
                throw new RuntimeException("response handle error!", e);
            }
        }, headMap, DEF_CHARSET, timeout);
    }

    /**
     * http请求常用动作的处理。此方法忽略响应正文内容，只获取响应的头信息，适合下载文件之前获取文件大小信息等类似业务场景。
     *
     * @param method   请求方式 { GET, POST, PUT, DELETE }
     * @param url      完整的url地址,如：http://www.wee0.com/index.jsp
     * @param sendData 发送数据
     * @param headMap  附加的头数据信息
     * @param timeout  超时时间
     * @return 请求返回的结果。注意：如果有错误才会有内容，如果没有错误，则只有头信息。
     */
    public IHttpResult httpHead(METHOD method, String url, Consumer<OutputStream> sendData, Map<String, String> headMap, String charset, int timeout) {
        return _httpAction(method, url, sendData, _RES_HANDLER_HEAD, headMap, charset, timeout);
    }

    // 实际执行请求动作的方法。
    private IHttpResult _httpAction(METHOD method, String url, Consumer<OutputStream> sendData, BiFunction<HttpURLConnection, String, IHttpResult> responseHandler, Map<String, String> headMap, String charset, int timeout) {
        if (null == url || 0 == (url = url.trim()).length())
            throw new IllegalArgumentException("url can not be null!");
        if (null == method)
            method = METHOD.POST;
        if (null == charset)
            charset = DEF_CHARSET;
        if (null == headMap)
            headMap = DEF_HEAD_MAP;
        if (0 > timeout)
            timeout = DEF_TIMEOUT;

        HttpURLConnection _conn = null;
        try {
            log.trace("[{}] {}", method.name(), url);
            _conn = (HttpURLConnection) new URL(url).openConnection();
            _conn.setConnectTimeout(timeout);
            _conn.setReadTimeout(timeout);
            _conn.setRequestMethod(method.name());
            _conn.setUseCaches(false); // 不使用缓存
            _conn.setDoOutput(true);// 可以使用conn.getOutputStream().write()向服务器传输数据。
            // 设置头信息部分。
            if (null != headMap && !headMap.isEmpty()) {
                for (Map.Entry<String, String> _headEntry : headMap.entrySet()) {
                    _conn.setRequestProperty(_headEntry.getKey(), _headEntry.getValue());
                }
            }
//			log.trace( "数据设置完成，准备建立连接。" ) ;
            _conn.connect(); // 建立连接，此时必须头信息全部设置完毕。
//			log.trace( "连接建立，接收数据。" ) ;
            // 如果有需要发送的数据，则发送数据。
            if (null != sendData) {
                try (OutputStream _outStream = _conn.getOutputStream();) {
//                    _outStream.write(data);
                    sendData.accept(_outStream);
                }
            }

            if (null == responseHandler)
                responseHandler = _RES_HANDLER_COMMON;
            return responseHandler.apply(_conn, charset);
        } catch (IOException e) {
            throw new RuntimeException("Fail for url:".concat(url), e);
        } finally {
            if (null != _conn) {
                // 断开连接。
                _conn.disconnect();
            }
        }
    }

    // 通用的响应处理逻辑
    private static final BiFunction<HttpURLConnection, String, IHttpResult> _RES_HANDLER_COMMON = new BiFunction<HttpURLConnection, String, IHttpResult>() {

        @Override
        public IHttpResult apply(HttpURLConnection connection, String charset) {
            try {
                int _responseCode = connection.getResponseCode();
//                String _contentType = connection.getContentType();
//                _contentType.indexOf("charset=");
//                log.trace("responseCode:" + responseCode);
                StringBuilder _builder = new StringBuilder();
                try (InputStream _inStream = _responseCode >= 400 ? connection.getErrorStream() : connection.getInputStream();
                     InputStreamReader _inReader = new InputStreamReader(_inStream, charset);
                     BufferedReader _bufReader = new BufferedReader(_inReader);) {
                    // 读取流信息。
                    String _line = _bufReader.readLine();
                    while (null != _line) {
                        _builder.append(_line).append("\r\n");
                        _line = _bufReader.readLine();
                    }
                }

//                log.trace("封装结果。");
                SimpleHttpResult _result = new SimpleHttpResult();
                _result.setCode(_responseCode);
                _result.setContent(_builder.toString());

                if (HttpURLConnection.HTTP_OK == _responseCode) {
                    _result.setHeaders(connection.getHeaderFields());
                }

//                log.trace("content: {}" + hr.getContent());
                return _result;
            } catch (IOException e) {
                throw new RuntimeException("response handle error!", e);
            }
        }
    };

    // 只关注响应头信息的响应处理逻辑
    private static final BiFunction<HttpURLConnection, String, IHttpResult> _RES_HANDLER_HEAD = new BiFunction<HttpURLConnection, String, IHttpResult>() {
        @Override
        public IHttpResult apply(HttpURLConnection connection, String charset) {
            try {
                int _responseCode = connection.getResponseCode();
//			    log.trace( "responseCode:" + responseCode ) ;
                StringBuilder _builder = new StringBuilder();
                if (_responseCode >= 400) {
                    try (InputStream _inStream = connection.getErrorStream();
                         InputStreamReader _inReader = new InputStreamReader(_inStream, charset);
                         BufferedReader _bufReader = new BufferedReader(_inReader);) {
                        // 读取流信息。
                        String _line = _bufReader.readLine();
                        while (null != _line) {
                            _builder.append(_line).append("\r\n");
                            _line = _bufReader.readLine();
                        }
                    }
                }

//                log.trace("封装结果。");
                SimpleHttpResult _result = new SimpleHttpResult();
                _result.setCode(_responseCode);
                _result.setContent(_builder.toString());
                _result.setHeaders(connection.getHeaderFields());

//                log.trace("content: {}" + _result.getContent());
                return _result;
            } catch (IOException e) {
                throw new RuntimeException("response handle error!", e);
            }
        }
    };

//    // 下载的响应处理逻辑
//    private static final BiFunction<HttpURLConnection, String, IHttpResult> _RES_HANDLER_DOWNLOAD = new BiFunction<HttpURLConnection, String, IHttpResult>() {
//        @Override
//        public IHttpResult apply(HttpURLConnection connection, String charset) {
//            try {
//
//            } catch (IOException e) {
//                throw new RuntimeException("response handle error!", e);
//            }
//        }
//    };


    /**
     * 将指定的输入流写入到指定的输出流中。
     *
     * @param inStream  输入流
     * @param outStream 输出流, 如果使用 BufferedOutputStream会相对快一些。
     */
    private static void copyStream(InputStream inStream, OutputStream outStream) {
//        // 写入缓冲区大小:1024K。
//        final int _WRITE_BUF = 1024 * 1024;
        byte[] _buffer = new byte[1048576];
        int _len = 0;
        try {
            while (-1 != (_len = inStream.read(_buffer))) {
                outStream.write(_buffer, 0, _len);
            }
            outStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    private static byte[] toBytes(InputStream inStream) throws IOException {
//        ByteArrayOutputStream _swapStream = new ByteArrayOutputStream();
//        byte[] _buff = new byte[1024]; //buff用于存放循环读取的临时数据
//        int _len = 0;
//        while (-1 != (_len = inStream.read(_buff, 0, 100))) {
//            _swapStream.write(_buff, 0, _len);
//        }
//        return _swapStream.toByteArray();
//    }
//
//    private static InputStream fromBytes(byte[] data) {
//        return new ByteArrayInputStream(data);
//    }

    /**
     * Http请求返回的结果集对象。
     */
    public static final class SimpleHttpResult implements IHttpResult {
        /**
         * http返回文件时的临时存储目录。
         */
        public static String TMPDIR;

        private int _code; // 响应代码。 ResponseCode
        private Map<String, List<String>> _headMap; // 头信息集合。
        private String _content; // 响应内容。
        // private InputStream _inStream ; // 输入流。

        /**
         * 无参数构造函数。
         */
        SimpleHttpResult() {

        }

        /**
         * 构造函数
         *
         * @param responseCode ResponseCode
         * @param headMap      头信息集合
         * @param content      响应内容
         */
        public SimpleHttpResult(int responseCode, Map<String, List<String>> headMap, String content) {
            _code = responseCode;
            _headMap = headMap;
            _content = content;
        }

        /**
         * 设置响应代码。
         *
         * @param responseCode
         */
        void setCode(int responseCode) {
            _code = responseCode;
        }

        /**
         * 获取http响应代码。
         *
         * @return
         */
        public int getCode() {
            return _code;
        }

        /**
         * 设置响应头信息集合
         *
         * @param headMap
         */
        void setHeaders(Map<String, List<String>> headMap) {
            _headMap = headMap;
        }

        /**
         * 获取http请求返回结果中的头信息集合。
         *
         * @return
         */
        public Map<String, List<String>> getHeaders() {
            return _headMap;
        }

        /**
         * 获取指定键名的关信息，只返回第一个元素。适用于只有一个元素的头信息。
         *
         * @param key 键名称
         * @return 对应键名的头信息的第一个元素。
         */
        public String getHead(String key) {
            if (null == _headMap || !_headMap.containsKey(key))
                return null;
            return _headMap.get(key).get(0);
        }

        /**
         * 返回所有头信息的json形式字符串。
         *
         * @return
         */
        public String head2Json() {
            if (null == _headMap)
                return null;
            StringBuilder sb = new StringBuilder().append("{");
            Set<String> keys = _headMap.keySet();
            for (String key : keys) {
                sb.append("\"").append(key).append("\":[");
                List<String> values = _headMap.get(key);
                for (String val : values)
                    sb.append("\"").append(val).append("\",");
                sb.deleteCharAt(sb.length() - 1); // 删除最后一个多余的逗号。
                sb.append("],");
            }
            sb.deleteCharAt(sb.length() - 1); // 删除最后一个多余的逗号。
            return sb.append("}").toString();
        }

        /**
         * 设置http请求响应内容正文。
         *
         * @param content
         */
        void setContent(String content) {
            _content = content;
        }

        /**
         * 获取http请求响应内容正文。
         *
         * @return
         */
        public String getContent() {
            return _content;
        }

        @Override
        public String toString() {
            StringBuilder _sb = new StringBuilder(128);
            _sb.append("{code:").append(_code);
            _sb.append(",content:").append(_content);
            _sb.append(",headers:").append(_headMap);
            return _sb.append("}").toString();
        }
    } // End class


    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private SimpleHttpUtils() {
        if (null != SimpleHttpUtilsHolder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }

        // 初始化默认请求头数据
        this.DEF_HEAD_MAP = new HashMap<>(1);
        this.DEF_HEAD_MAP.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36");
    }

    // 当前对象唯一实例持有者。
    private static final class SimpleHttpUtilsHolder {
        private static final SimpleHttpUtils _INSTANCE = new SimpleHttpUtils();
    }

    // 防止使用反序列化操作获取多个对象实例。
    private Object readResolve() throws ObjectStreamException {
        return SimpleHttpUtilsHolder._INSTANCE;
    }

    /**
     * 获取当前对象唯一实例。
     *
     * @return 当前对象唯一实例
     */
    public static SimpleHttpUtils me() {
        return SimpleHttpUtilsHolder._INSTANCE;
    }

}
