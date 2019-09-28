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

package com.wee0.box.exception;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 8:21
 * @Description 统一的程序异常
 * <pre>
 * 补充说明
 * </pre>
 **/
public class BoxRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    // 异常代码
    private int code;

    // 默认异常代码
    public static final int CODE_DEF = 500;

    public BoxRuntimeException(Throwable e) {
        super(e);
    }

    public BoxRuntimeException(String msg) {
        this(CODE_DEF, msg);
    }

    public BoxRuntimeException(String msg, Throwable e) {
        this(CODE_DEF, msg, e);
    }

    public BoxRuntimeException(int code, String msg) {
        this(code, msg, null);
    }

    public BoxRuntimeException(int code, String msg, Throwable e) {
        super(msg, e);
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}
