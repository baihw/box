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

package com.wee0.box.io.impl;

import com.wee0.box.io.IFileSystem;
import com.wee0.box.log.ILogger;
import com.wee0.box.log.LoggerFactory;
import com.wee0.box.util.shortcut.StringUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;
import java.util.function.Function;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/15 7:16
 * @Description 一个简单的文件系统管理对象实现
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SimpleFileSystem implements IFileSystem {

    // 日志对象
    private static ILogger log = LoggerFactory.getLogger(SimpleFileSystem.class);

    @Override
    public List<String> list(String directory, Function<String, Boolean> nameFilter) throws IOException {
        List<String> _result = new ArrayList<>();
        Enumeration<URL> _urls = Thread.currentThread().getContextClassLoader().getResources(directory);
        while (_urls.hasMoreElements()) {
            URL _url = _urls.nextElement();
            _result.addAll(list(_url, directory, nameFilter));
        }
        return _result;
    }

    @Override
    public List<String> list(String directory) throws IOException {
        return list(directory, null);
    }

    /**
     * 列出指定路径下的资源标识
     *
     * @param url        资源定位地址
     * @param path       父级路径
     * @param nameFilter 名称过滤器
     * @return 子级资源标识
     * @throws IOException 无法确定资源时抛出
     */
    static List<String> list(URL url, String path, Function<String, Boolean> nameFilter) throws IOException {
        URL _jarUrl = getJarURL(url);
        if (null != _jarUrl) {
            try (InputStream _inStream = _jarUrl.openStream();
                 JarInputStream _jarStream = new JarInputStream(_inStream);) {
                return listJarResources(_jarStream, path, nameFilter);
            }
        }
        path = StringUtils.endsWithChar(path, '/');
        List<String> _result = new ArrayList<>();
        List<String> _children = new ArrayList<>();
        try {
            if (isJar(url)) {
                // 虽然资源文件可能不是jar文件，但有些版本的应用服务器会给出jar流
                try (InputStream _inStream = url.openStream();
                     JarInputStream _jarStream = new JarInputStream(_inStream);) {
                    for (JarEntry _entry; null != (_entry = _jarStream.getNextJarEntry()); ) {
                        String _name = _entry.getName();
                        if (null != nameFilter && !nameFilter.apply(_name)) {
                            log.trace("Skip resource: {}", _name);
                            continue;
                        }
                        log.trace("Found resource: {}", _name);
                        _children.add(_name);
                    }
                }
            } else {
                // 一些应用服务器会返回一个每行列出一个子资源的多行文本流，尝试作为当前资源的子级资源进行加载。
                List<String> _lines = new ArrayList<>();
                try (InputStream _inStream = url.openStream();
                     BufferedReader _reader = new BufferedReader(new InputStreamReader(_inStream));) {
                    for (String _line; null != (_line = _reader.readLine()); ) {
                        if (getResources(path + _line).isEmpty()) {
                            _lines.clear();
                            break;
                        }
                        if (log.isTraceEnabled()) {
                            log.trace("Reader entry: {}", _line);
                        }
                        _lines.add(_line);
                    }
                }
                if (!_lines.isEmpty()) {
                    _children.addAll(_lines);
                }
            }
        } catch (FileNotFoundException e) {
            if ("file".equals(url.getProtocol())) {
                File _file = new File(url.getFile());
                log.trace("Listing directory: {}", _file.getAbsolutePath());
                if (_file.isDirectory()) {
                    log.trace("Listing {}", url);
                    _children = Arrays.asList(_file.list());
                }
            } else {
                throw e;
            }
        }

        String _prefix = StringUtils.endsWithChar(url.toExternalForm(), '/');

        for (String _child : _children) {
            String _resourcePath = path + _child;
            if (null != nameFilter && !nameFilter.apply(_child)) {
                log.trace("Skip resource: {}", _child);
                continue;
            }
            _result.add(_resourcePath);
            URL _childUrl = new URL(_prefix + _child);
            _result.addAll(list(_childUrl, _resourcePath, nameFilter));
        }

        return _result;
    }

    // 获取指定路径下的资源
    static List<URL> getResources(String path) throws IOException {
        return Collections.list(Thread.currentThread().getContextClassLoader().getResources(path));
    }

    // jar文件魔数
    private static final byte[] JAR_MAGIC = {'P', 'K', 3, 4};

    /**
     * 判断指定资源是否是jar文件
     *
     * @param url 资源定位地址
     * @return 如果是jar文件返回true
     */
    static boolean isJar(URL url) {
        byte _buff[] = new byte[JAR_MAGIC.length];
        try (InputStream _stream = url.openStream();) {
            _stream.read(_buff, 0, JAR_MAGIC.length);
            if (Arrays.equals(_buff, JAR_MAGIC)) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 获取jar资源定位地址
     *
     * @param url 原始定位地址
     * @return 如果不是jar文件返回null
     */
    static URL getJarURL(URL url) {
        // 处理url指向问题
        try {
            for (; ; ) {
                url = new URL(url.getFile());
                log.debug("Inner URL: {}", url);
            }
        } catch (MalformedURLException e) {
        }

        String _url = url.toExternalForm();
        if (!_url.endsWith(".jar"))
            return null;

        try {
            URL _testUrl = new URL(_url);
            if (isJar(_testUrl))
                return _testUrl;

            File _file = new File(_testUrl.getFile());
            if (!_file.exists()) {
                try {
                    _file = new File(URLEncoder.encode(_testUrl.getFile(), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException("Unsupported UTF-8 encoding?");
                }
            }

            if (_file.exists()) {
                _testUrl = _file.toURI().toURL();
                if (isJar(_testUrl)) {
                    return _testUrl;
                }
            }
        } catch (MalformedURLException e) {
            log.debug("Invalid JAR URL: {}", _url);
        }
        return null;
    }

    // 列出jar文件中符合条件的资源
    static List<String> listJarResources(JarInputStream jar, String path, Function<String, Boolean> nameFilter) throws IOException {
        path = StringUtils.startsWithChar(path, '/');
        path = StringUtils.endsWithChar(path, '/');
        List<String> _result = new ArrayList<>();
        for (JarEntry _entry; null != (_entry = jar.getNextJarEntry()); ) {
            if (!_entry.isDirectory()) {
                StringBuilder _sb = new StringBuilder(_entry.getName());
                if (_sb.charAt(0) != '/') {
                    _sb.insert(0, '/');
                }
                if (0 == _sb.indexOf(path)) {
                    String _name = _sb.substring(1);
                    if (null != nameFilter && !nameFilter.apply(_name)) {
                        log.trace("Skip resource: {}", _name);
                        continue;
                    }
                    log.trace("Found resource: {}", _name);
                    _result.add(_name);
                }
            }
        }
        return _result;
    }

    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private SimpleFileSystem() {
        if (null != SimpleFileSystemHolder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }
    }

    // 当前对象唯一实例持有者。
    private static final class SimpleFileSystemHolder {
        private static final SimpleFileSystem _INSTANCE = new SimpleFileSystem();
    }

    // 防止使用反序列化操作获取多个对象实例。
    private Object readResolve() throws ObjectStreamException {
        return SimpleFileSystemHolder._INSTANCE;
    }

    /**
     * 获取当前对象唯一实例。
     *
     * @return 当前对象唯一实例
     */
    public static SimpleFileSystem me() {
        return SimpleFileSystemHolder._INSTANCE;
    }

}
