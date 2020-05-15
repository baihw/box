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

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2020/2/8 8:08
 * @Description 分片上传信息对象
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface IMultipartUploadInfo {

    /**
     * @return 对象标识
     */
    String getObjectId();

    /**
     * @return 上传标识
     */
    String getUploadId();

    /**
     * @return 对象大小
     */
    long getObjectSize();

    /**
     * @return 所有分片大小
     */
    int[] getPartSizes();

}
