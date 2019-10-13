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

package com.wee0.box.action;

import com.wee0.box.web.annotation.BoxAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/1 22:31
 * @Description 功能描述
 * <pre>
 * 补充说明
 * </pre>
 **/
@BoxAction
public class Hello {

    public String string() {
        return "string";
    }

    public Map<String, String> map() {
        return new HashMap<>();
    }

    public List<String> list() {
        return new ArrayList<>();
    }

    public List<Map<String, String>> listMap() {
        return new ArrayList<>();
    }

}
