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

package com.wee0.box.spring.boot;

import com.wee0.box.action.Hello;
import com.wee0.box.action.diy.Custom;
import com.wee0.box.action.user.SysUser;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/1 22:30
 * @Description 功能描述
 * <pre>
 * 补充说明
 * </pre>
 **/
public class BoxActionRequestMappingHandlerMappingTest {

    @Test
    public void generateUri() throws NoSuchMethodException {
        Method _string = Hello.class.getMethod("string");
        String _uri = BoxActionRequestMappingHandlerMapping.generateUri(Hello.class, _string);
        Assert.assertEquals(convertUri("/hello/string"), _uri);

        Method _queryAll = SysUser.class.getMethod("queryAll");
        String _queryAllUri = BoxActionRequestMappingHandlerMapping.generateUri(SysUser.class, _queryAll);
        Assert.assertEquals(convertUri("/user/sysUser/queryAll"), _queryAllUri);

        Method _customM1 = Custom.class.getMethod("m1");
        Assert.assertEquals(convertUri("/custom/login"), BoxActionRequestMappingHandlerMapping.generateUri(Custom.class, _customM1));
        Method _customM2 = Custom.class.getMethod("m2");
        Assert.assertEquals(convertUri("/custom/logout"), BoxActionRequestMappingHandlerMapping.generateUri(Custom.class, _customM2));
        Method _customM3 = Custom.class.getMethod("m3");
        Assert.assertEquals(convertUri("/custom/test"), BoxActionRequestMappingHandlerMapping.generateUri(Custom.class, _customM3));
        Method _customM4 = Custom.class.getMethod("m4");
        Assert.assertEquals(convertUri("/custom/m4"), BoxActionRequestMappingHandlerMapping.generateUri(Custom.class, _customM4));
    }

    private static String convertUri(String uri) {
        return BoxActionRequestMappingHandlerMapping.DEF_PREFIX + uri;
    }

}
