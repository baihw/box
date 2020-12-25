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

import com.wee0.box.util.IZipUtils;
import com.wee0.box.util.shortcut.CheckUtils;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2020/5/17 22:05
 * @Description 一个简单的Zip处理工具实现
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SimpleZipUtils implements IZipUtils {
    @Override
    public void compress(File source, File zipFile) {
        throw new UnsupportedOperationException("This method is not yet implemented.");
    }

    @Override
    public void decompress(InputStream zipStream, File destDirectory) {
        CheckUtils.checkNotNull(zipStream, "zipStream cannot be null!");
        CheckUtils.checkNotNull(destDirectory, "destDirectory cannot be null!");
        if (!destDirectory.exists()) destDirectory.mkdirs();

        try (ZipInputStream _zipStream = new ZipInputStream(zipStream)) {
            ZipEntry _zipEntry;
            while (null != (_zipEntry = _zipStream.getNextEntry())) {
//                System.out.println(String.format("zipEntry: %s len %d added %TD", _zipEntry.getName(), _zipEntry.getSize(), new Date(_zipEntry.getTime())));
                final String _zipEntryName = _zipEntry.getName();
                File _zipEntryFile = new File(destDirectory, _zipEntryName);
//                if ('/' == _zipEntryName.charAt(_zipEntryName.length() - 1))
                if (_zipEntry.isDirectory()) {
                    _zipEntryFile.mkdir();
                    continue;
                }
                if (!_zipEntryFile.getParentFile().exists()) _zipEntryFile.getParentFile().mkdirs();
                try (FileOutputStream _entryOutStream = new FileOutputStream(_zipEntryFile); BufferedOutputStream _entryOutStreamBuf = new BufferedOutputStream(_entryOutStream);) {
                    int _len = 0;
                    byte[] _buf = new byte[1024];
                    while (-1 != (_len = _zipStream.read(_buf))) {
                        _entryOutStreamBuf.write(_buf, 0, _len);
                    }
                    _entryOutStreamBuf.flush();
                }
                _zipStream.closeEntry();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void unzip(File zipFile, File destDirectory) {
        if (null == zipFile || !zipFile.exists() || !zipFile.isFile())
            throw new IllegalArgumentException("Invalid zipFile: " + zipFile);
        CheckUtils.checkNotNull(destDirectory, "destDirectory cannot be null!");
        if (!destDirectory.exists()) destDirectory.mkdirs();

        try (ZipFile _zipFile = new ZipFile(zipFile)) {
            Enumeration<? extends ZipEntry> _zipEntries = _zipFile.entries();
            while (_zipEntries.hasMoreElements()) {
                ZipEntry _zipEntry = _zipEntries.nextElement();
                String _zipEntryName = _zipEntry.getName();
                File _zipEntryFile = new File(destDirectory, _zipEntryName);
                if (_zipEntry.isDirectory()) {
                    _zipEntryFile.mkdir();
                    continue;
                }
                if (!_zipEntryFile.getParentFile().exists()) _zipEntryFile.getParentFile().mkdirs();
                try (InputStream _zipEntryInStream = _zipFile.getInputStream(_zipEntry); OutputStream _zipEntryOutStream = new FileOutputStream(_zipEntryFile);) {
                    byte[] _buffer = new byte[1024];
                    int _len = 0;
                    while (-1 != (_len = _zipEntryInStream.read(_buffer))) {
                        _zipEntryOutStream.write(_buffer, 0, _len);
                    }
                    _zipEntryOutStream.flush();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private SimpleZipUtils() {
        if (null != SimpleZipUtilsHolder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }
    }

    // 当前对象唯一实例持有者。
    private static final class SimpleZipUtilsHolder {
        private static final SimpleZipUtils _INSTANCE = new SimpleZipUtils();
    }

    // 防止使用反序列化操作获取多个对象实例。
    private Object readResolve() throws ObjectStreamException {
        return SimpleZipUtilsHolder._INSTANCE;
    }

    /**
     * 获取当前对象唯一实例。
     *
     * @return 当前对象唯一实例
     */
    public static SimpleZipUtils me() {
        return SimpleZipUtilsHolder._INSTANCE;
    }
}
