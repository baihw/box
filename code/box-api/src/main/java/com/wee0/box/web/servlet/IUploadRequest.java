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

package com.wee0.box.web.servlet;

import com.wee0.box.web.IRequest;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/10/13 8:16
 * @Description 文件上传请求对象
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface IUploadRequest extends IRequest {

    /**
     * 获取指定名称的上传文件描述对象
     *
     * @param name 名称
     * @return 文件描述对象
     */
    IUploadFile getUploadFile(String name);

    /**
     * 获取上传的文件描述对象集合
     *
     * @return 上传文件描述对象集合
     */
    Map<String, IUploadFile> getUploadFiles();

}
