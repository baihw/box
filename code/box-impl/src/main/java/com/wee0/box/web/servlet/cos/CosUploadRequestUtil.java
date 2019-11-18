///*
// * Copyright (c) 2019-present, wee0.com.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package com.wee0.box.web.servlet.cos;
//
//import com.wee0.box.web.servlet.IUploadRequest;
//import com.wee0.box.web.servlet.IUploadRequestUtils;
//
//import javax.servlet.http.HttpServletRequest;
//
///**
// * @author <a href="78026399@qq.com">白华伟</a>
// * @CreateDate 2019/10/13 8:22
// * @Description 基于cos组件实现的文件上传请求对象处理工具
// * <pre>
// * 补充说明
// * </pre>
// **/
//public class CosUploadRequestUtil implements IUploadRequestUtils {
//    @Override
//    public IUploadRequest parseRequest(HttpServletRequest request) {
//        String _diskPath = "";
//        int _maxSize = 10485760;  // 10 * 1024 * 1024 ; 10M ( 10485760 )
//        String _encoding = "";
//        MultipartRequest _multipartRequest = new MultipartRequest( request, _diskPath, _maxSize, _encoding, new DefaultFileRenamePolicy() );
//        Enumeration<?> _fileNames = _multipartRequest.getFileNames();
//        while( _fileNames.hasMoreElements() ){
//            String paramName = ( String )_fileNames.nextElement();
//            String fileName = _multipartRequest.getFilesystemName( paramName );
//            if( null == fileName ){
//                // 跳过未上传成功的文件。
//                continue;
//            }
//
//            // 去空格处理。
//            fileName = fileName.trim();
//
//            // 处理用户上传jsp等恶意文件。
//            String fileLowerName = fileName.toLowerCase();
//            if( fileLowerName.endsWith( ".jsp" ) || fileLowerName.endsWith( ".jspx" ) ){
//                _multipartRequest.getFile( paramName ).delete();
//                continue;
//            }
//
//            // 上传成功后的文件相对路径。
//            uploadDir.concat( fileName );
//        }
//
//        // 解析参数部分
//        Enumeration<?> _parameterNames = _multipartRequest.getParameterNames();
//        while( _parameterNames.hasMoreElements() ){
//            String _parameterName = ( String )_parameterNames.nextElement();
//            request.addParameter( _parameterName, _multipartRequest.getParameterValues( _parameterName ) );
//        }
//        return null;
//    }
//}
