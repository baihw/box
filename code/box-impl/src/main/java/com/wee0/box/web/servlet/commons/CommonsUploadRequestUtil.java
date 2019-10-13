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

package com.wee0.box.web.servlet.commons;

import com.wee0.box.BoxConfig;
import com.wee0.box.exception.BoxRuntimeException;
import com.wee0.box.web.servlet.IUploadFile;
import com.wee0.box.web.servlet.IUploadRequest;
import com.wee0.box.web.servlet.IUploadRequestUtil;
import com.wee0.box.web.servlet.impl.SimpleUploadRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/10/13 8:26
 * @Description 基于commons-fileupload组件实现的文件上传请求对象处理工具
 **/
public class CommonsUploadRequestUtil implements IUploadRequestUtil {

    // 临时文件存储目录
    private static final File TMP_DIR = new File(System.getProperty("java.io.tmpdir"));

    // 默认使用的编码
    private static final String DEF_ENCODING = "UTF-8";

    // 默认的允许上传的文件的总大小:10M。
    private static final long DEF_MAX_FILESIZE = 10485760L;    // 10 * 1024 * 1024 ; 10M ( 10485760 ) .

    // 文件名称过滤器，匹配上此规则的文件将会被忽略。
    private static final String DEF_REG_DENY_NAME = "\\S+\\.{1}(?i)(exe|bat|vbs|sh|perl|py|jsp|)$";

    private final ServletFileUpload fileUpload;

    public CommonsUploadRequestUtil(ServletFileUpload servletFileUpload) {
        if (null == servletFileUpload)
            servletFileUpload = createDefaultServletFileUpload();
        this.fileUpload = servletFileUpload;
    }

    private ServletFileUpload createDefaultServletFileUpload() {
        // 配置上传组件磁盘文件工厂
        DiskFileItemFactory _diskFileItemFactory = new DiskFileItemFactory();
        // 上传文件时用于临时存放文件的内存大小:10K。
        _diskFileItemFactory.setSizeThreshold(10240);
        // 设置一旦文件大小超过sizeThreshold()的值时数据存放在硬盘的目录。
        _diskFileItemFactory.setRepository(TMP_DIR);

        // 配置上传组件
        ServletFileUpload _fileUpload = new ServletFileUpload(_diskFileItemFactory);
        // 设置允许上传的单个文件最大文件尺寸。
        _fileUpload.setFileSizeMax(DEF_MAX_FILESIZE);
        // 设置允许上传的文件的总大小。
        _fileUpload.setSizeMax(DEF_MAX_FILESIZE);
        // 设置解析字段使用的字符集编码
        _fileUpload.setHeaderEncoding(BoxConfig.impl().getEncoding());
        return _fileUpload;
    }

    @Override
    public IUploadRequest parseRequest(javax.servlet.http.HttpServletRequest request) {
        // 解析表单信息
        // 注意：上传页面的input标签name属性不能为空。如：
        // <form action="/api/upload" method="post" enctype="multipart/form-data" >
        //   <input name="file1" type="file" />
        // </form>
        try {
            List<FileItem> _fileItemList = this.fileUpload.parseRequest(request);
            Map<String, String> _parameterMap = new HashMap<>(_fileItemList.size());
            Map<String, IUploadFile> _uploadFileList = new HashMap<>(_fileItemList.size());
            for (FileItem _fileItem : _fileItemList) {
                if (null == _fileItem)
                    continue;
                if (_fileItem.isFormField()) {
                    // 表单字段。
                    String _name = _fileItem.getFieldName();
//                    String _value = _fileItem.getString().getBytes( "ISO-8859-1" ), "UTF-8" );
                    String _value = _fileItem.getString(DEF_ENCODING);
                    _parameterMap.put(_name, _value);
                } else {
                    // 上传数据
                    String _itemName = _fileItem.getName();
                    // 如果文件不合法，则不处理。
                    if (null == _itemName || 0 == (_itemName = _itemName.trim()).length() || 0 == _fileItem.getSize() || _itemName.matches(DEF_REG_DENY_NAME))
                        continue;
                    CommonsUploadFile _uploadFile = new CommonsUploadFile(_fileItem);
                    _uploadFileList.put(_itemName, _uploadFile);
                }
            }
            IUploadRequest _uploadRequest = new SimpleUploadRequest(_parameterMap, _uploadFileList);
            return _uploadRequest;
        } catch (FileUploadException e) {
            throw new BoxRuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new BoxRuntimeException(e);
        }
    }

}
