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

package com.wee0.box.plugins.storage;

import com.wee0.box.plugin.IPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/12/28 10:00
 * @Description 云存储插件
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface ICloudStoragePlugin extends IPlugin {

    /**
     * 默认的内容类型：
     */
    String DEF_CONTENT_TYPE = "application/octet-stream";

    /**
     * 默认的过期时间：8小时（ 60 * 60 * 8 ）。
     */
    int DEF_EXPIRE_TIME = 28800;

    /**
     * 默认允许上传的最大文件大小：100M。
     */
    long DEF_FILE_MAX_SIZE = 104857600L;

    /**
     * 获取指定标识对象的授权上传地址,Form表单方式上传。
     *
     * @param id          唯一标识
     * @param expireTime  授权过期时间，单位：秒。
     * @param contentType 内容类型
     * @param fileMaxSize 允许上传的文件大小
     * @return Form表单方式上传所需提交数据
     */
    Map<String, String> getUploadForm(String id, int expireTime, String contentType, long fileMaxSize);

    /**
     * 获取指定标识对象的授权上传地址,Form表单方式上传。
     *
     * @param id          唯一标识
     * @param expireTime  授权过期时间，单位：秒。
     * @param contentType 内容类型
     * @return Form表单方式上传所需提交数据
     */
    default Map<String, String> getUploadForm(String id, int expireTime, String contentType) {
        return getUploadForm(id, expireTime, contentType, DEF_FILE_MAX_SIZE);
    }

    /**
     * 获取指定标识对象的授权上传地址,Form表单方式上传。
     *
     * @param id          唯一标识
     * @param contentType 内容类型
     * @return Form表单方式上传所需提交数据
     */
    default Map<String, String> getUploadForm(String id, String contentType) {
        return getUploadForm(id, DEF_EXPIRE_TIME, contentType, DEF_FILE_MAX_SIZE);
    }

    /**
     * 获取指定标识对象的授权上传地址,Form表单方式上传。
     *
     * @param id 唯一标识
     * @return Form表单方式上传所需提交数据
     */
    default Map<String, String> getUploadForm(String id) {
        return getUploadForm(id, DEF_EXPIRE_TIME, DEF_CONTENT_TYPE, DEF_FILE_MAX_SIZE);
    }


    /**
     * 获取指定标识对象的授权上传地址,PUT方式上传。
     *
     * @param id          唯一标识
     * @param expireTime  授权过期时间，单位：秒。
     * @param contentType 内容类型
     * @return 授权上传地址
     */
    String getUploadUrl(String id, int expireTime, String contentType);

    /**
     * 获取指定标识对象的授权上传地址,PUT方式上传。
     *
     * @param id          唯一标识
     * @param contentType 内容类型
     * @return 授权上传地址
     */
    default String getUploadUrl(String id, String contentType) {
        return getUploadUrl(id, DEF_EXPIRE_TIME, contentType);
    }

    /**
     * 获取指定标识对象的授权上传地址,PUT方式上传。
     *
     * @param id 唯一标识
     * @return 授权上传地址
     */
    default String getUploadUrl(String id) {
        return getUploadUrl(id, DEF_EXPIRE_TIME, DEF_CONTENT_TYPE);
    }

    /**
     * 获取指定标识对象的授权下载地址
     *
     * @param id         唯一标识
     * @param expireTime 授权过期时间，单位：秒。
     * @return 授权下载地址
     */
    String getDownloadUrl(String id, int expireTime);

    /**
     * 获取指定标识对象的授权下载地址
     *
     * @param id 唯一标识
     * @return 授权下载地址
     */
    default String getDownloadUrl(String id) {
        return getDownloadUrl(id, DEF_EXPIRE_TIME);
    }

    /**
     * 获取指定标识对象的公开请求地址。注意：非公开对象可能会出现请求失败。
     *
     * @param id 唯一标识
     * @return 公开请求地址
     */
    String getPublicUrl(String id);

    /**
     * 判断指定标识对象是否存在
     *
     * @param id 唯一标识
     * @return 是否存在
     */
    boolean exists(String id);

    /**
     * 向云存储中存入对象数据
     *
     * @param id          对象标识
     * @param inStream    对象输入数据流
     * @param contentType 对象数据内容类型
     * @throws IOException 数据操作异常
     */
    void putObject(String id, InputStream inStream, String contentType) throws IOException;

    /**
     * 获取云存储指定对象数据
     *
     * @param id 对象标识
     * @return 对象数据流
     * @throws IOException 数据操作异常
     */
    InputStream getObject(String id) throws IOException;

    /**
     * 删除指定标识对象
     *
     * @param id 唯一标识
     * @return 是否成功
     */
    boolean remove(String id);

    /**
     * 拷贝指定标识对象
     *
     * @param from 源对象
     * @param to   目标对象
     * @return 是否成功
     */
    boolean copy(String from, String to);

    /**
     * 获取分片上传信息对象
     *
     * @param id   对象标识
     * @param size 对象大小
     * @return 分片上传信息
     */
    IMultipartUploadInfo multipartUploadInfoGet(String id, long size);

    /**
     * 获取预签名的分片数据上传地址
     *
     * @param objectId  对象标识
     * @param uploadId  上传标识
     * @param partIndex 分片索引
     * @param partSize  分片数据大小
     * @param expires   上传地址过期时间
     * @return 预签名的分片数据上传地址
     */
    String multipartUploadUrlGet(String objectId, String uploadId, int partIndex, int partSize, int expires);

    /**
     * 获取预签名的分片数据上传地址
     *
     * @param objectId  对象标识
     * @param uploadId  上传标识
     * @param partIndex 分片索引
     * @param partSize  分片数据大小
     * @return 预签名的分片数据上传地址
     */
    default String multipartUploadUrlGet(String objectId, String uploadId, int partIndex, int partSize) {
        return multipartUploadUrlGet(objectId, uploadId, partIndex, partSize, DEF_EXPIRE_TIME);
    }

    /**
     * 上传指定文件的分片数据
     *
     * @param uploadUrl 预签名的分片数据上传地址
     * @param file      数据文件
     * @param offset    数据读取偏移量
     * @param length    数据读取长度
     * @return 是否上传成功
     */
    boolean multipartUploadDo(String uploadUrl, File file, int offset, int length);

    /**
     * 通知分片数据上传完成，执行数据合并逻辑。
     *
     * @param objectId 对象标识
     * @param uploadId 上传标识
     * @return 合并结果
     */
    boolean multipartUploadComplete(String objectId, String uploadId);

}
