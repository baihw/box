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

package com.wee0.box.storage.oss;

import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2020/5/17 7:25
 * @Description 功能描述
 * <pre>
 * 补充说明
 * </pre>
 **/
public class OssStorageTest {

    static final String _DEF_BUCKET = "test1";

    static void m1() {
        String _result = null;
        Map<String, String> _params = new HashMap<>();
        _params.put("max-keys", "1");
        _result = OssStorage.me().generateCurlAuthorization(null, null, null, _params, null);
        System.out.println(_result);

        _params.clear();
        _params.put("acl", "0");
        // /test/start.jpg?acl
        _result = OssStorage.me().generateCurlAuthorization(_DEF_BUCKET, "/test/start.jpg", null, _params, null);
        System.out.println(_result);
    }

    static void m2() {
        String _result = null;
        Map<String, String> _params = new HashMap<>();
        _result = OssStorage.me().generatePreSignedUrl(_DEF_BUCKET, "/test/start.jpg", "PUT", _params, null);
        System.out.println(_result);
    }

    static void testSignUpload() throws IOException {
        List<Protocol> _protocol = new LinkedList();
        _protocol.add(Protocol.HTTP_1_1);
        OkHttpClient _httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(900L, TimeUnit.SECONDS)
                .writeTimeout(900L, TimeUnit.SECONDS)
                .readTimeout(900L, TimeUnit.SECONDS)
                .protocols(_protocol).build();

        String _uploadUrl = OssStorage.me().generatePreSignedUrl(_DEF_BUCKET, "/test/start.jpg", "PUT", null, null);

//        File _content = new File("D:\\C\\Desktop\\t1\\128.png");
        File _content = new File("D:\\C\\Desktop\\start.jpg");
        final MediaType DEF_UPLOAD_MEDIA_TYPE = MediaType.parse("application/octet-stream");
        RequestBody _body = RequestBody.create(DEF_UPLOAD_MEDIA_TYPE, _content);
        Request _request = new Request.Builder().url(_uploadUrl)
//                .header("Content-Type", "application/octet-stream")
//                .header("Connection", "keep-alive")
//                .header("Content-Length", String.valueOf(length))
                .put(_body).build();
//            System.out.println("_request:" + _request);
        Response _response = _httpClient.newCall(_request).execute();
        System.out.println("response:" + _response);
        System.out.println("response.body:\n" + _response.body().string());
    }

    public static void main(String[] args) throws IOException {
//        m1();
//        m2();
//        testSignUpload();
    }
}
