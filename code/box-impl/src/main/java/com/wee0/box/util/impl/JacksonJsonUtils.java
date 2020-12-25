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

package com.wee0.box.util.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.wee0.box.util.IJsonUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 9:25
 * @Description 基于Jackson库实现的Json处理工具
 * <pre>
 * 补充说明
 * </pre>
 **/
public class JacksonJsonUtils implements IJsonUtils {

    private static final TypeReference TYPE_MAP = new TypeReference<Map<String, Object>>() {
    };

    private static final TypeReference TYPE_MAP_LIST = new TypeReference<List<Map<String, Object>>>() {
    };

    // 核心实现类实例
    private final ObjectMapper objectMapper;

    @Override
    public String writeToString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T readToObject(String jsonString, Class<T> type) {
        try {
            return objectMapper.readValue(jsonString, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T readToObject(InputStream jsonStream, Class<T> type) {
        try {
            return objectMapper.readValue(jsonStream, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T readToObject(URL jsonUrl, Class<T> type) {
        try {
            return objectMapper.readValue(jsonUrl, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<String, Object> readToMap(String jsonString) {
        try {
            Map<String, Object> _result = objectMapper.readValue(jsonString, TYPE_MAP);
            return _result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<String, Object> readToMap(InputStream jsonStream) {
        try {
            Map<String, Object> _result = objectMapper.readValue(jsonStream, TYPE_MAP);
            return _result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<String, Object> readToMap(URL jsonUrl) {
        try {
            Map<String, Object> _result = objectMapper.readValue(jsonUrl, TYPE_MAP);
            return _result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Map<String, Object>> readToMapList(String jsonString) {
        try {
            List<Map<String, Object>> _result = objectMapper.readValue(jsonString, TYPE_MAP_LIST);
            return _result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Map<String, Object>> readToMapList(InputStream jsonStream) {
        try {
            List<Map<String, Object>> _result = objectMapper.readValue(jsonStream, TYPE_MAP_LIST);
            return _result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Map<String, Object>> readToMapList(URL jsonUrl) {
        try {
            List<Map<String, Object>> _result = objectMapper.readValue(jsonUrl, TYPE_MAP_LIST);
            return _result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    private static TypeReference createMapReference() {
//        return new TypeReference<Map<String, Object>>() {
//        };
//    }
//
//    private static TypeReference createMapListReference() {
//        return new TypeReference<List<Map<String, Object>>>() {
//        };
//    }

    /**
     * @return 获取映射对象
     */
    public ObjectMapper getObjectMapper() {
        return this.objectMapper;
    }

    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private JacksonJsonUtils() {
        if (null != JacksonJsonUtilsHolder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }

        objectMapper = new ObjectMapper();
//        //不把值为null的字段映射到json字符串中
//        objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        //空值不序列化，此配置项会有争议，建议后期改为默认输出所有字段。
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //序列化时，日期的统一格式
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        //设置为东八区
        objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        //去掉序列化日期时以默认的时间戳格式timestamps输出，默认true
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //序列化枚举是以toString()来输出，默认false，即默认以name()来输出
        objectMapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
        //序列化枚举是以ordinal()来输出，默认false
        objectMapper.configure(SerializationFeature.WRITE_ENUMS_USING_INDEX, false);
        //类为空时，不要抛异常
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        //反序列化时，属性不存在的兼容处理
        objectMapper.getDeserializationConfig().withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        //反序列化时,遇到未知属性时是否引起结果失败
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //反序列化时,是否允许浮点数做为整形使用
        objectMapper.configure(DeserializationFeature.ACCEPT_FLOAT_AS_INT, false);
        //单引号处理
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        //解析器支持解析结束符
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);

//        // 注册自定义模块
//        SimpleModule _customModule1 = new SimpleModule("boxModule1", new Version(1, 0, 0, null, null, null));
//        _customModule1.addDeserializer(SMD.class, new StdDeserializer<SMD>(SMD.class) {
//            @Override
//            public SMD deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
//                return jsonParser.readValueAs(SimpleSMD.class);
//            }
//        });
//        objectMapper.registerModule(_customModule1);
    }

    // 当前对象唯一实例持有者。
    private static final class JacksonJsonUtilsHolder {
        private static final JacksonJsonUtils _INSTANCE = new JacksonJsonUtils();
    }

    // 防止使用反序列化操作获取多个对象实例。
    private Object readResolve() throws ObjectStreamException {
        return JacksonJsonUtilsHolder._INSTANCE;
    }

    /**
     * 获取当前对象唯一实例。
     *
     * @return 当前对象唯一实例
     */
    public static JacksonJsonUtils me() {
        return JacksonJsonUtilsHolder._INSTANCE;
    }


}
