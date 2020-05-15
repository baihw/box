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
     * 默认的过期时间：8小时（ 60 * 60 * 8 ）。
     */
    int DEF_EXPIRE_TIME = 28800;

    /**
     * 获取指定标识对象的授权上传地址
     *
     * @param id         唯一标识
     * @param expireTime 授权过期时间，单位：秒。
     * @return 授权上传地址
     */
    String getUploadUrl(String id, int expireTime);

    /**
     * 获取指定标识对象的授权上传地址
     *
     * @param id 唯一标识
     * @return 授权上传地址
     */
    default String getUploadUrl(String id) {
        return getUploadUrl(id, DEF_EXPIRE_TIME);
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
