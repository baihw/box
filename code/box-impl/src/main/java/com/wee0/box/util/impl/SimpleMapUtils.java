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

import com.wee0.box.util.IMapUtils;

import java.io.ObjectStreamException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 9:22
 * @Description 一个简单的Map处理工具实现
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SimpleMapUtils implements IMapUtils {

    @Override
    public String getString(Map map, Object key) {
        if (null == map)
            return null;
        Object _obj = map.get(key);
        if (null == _obj)
            return null;
        return _obj.toString();
    }

    @Override
    public Boolean getBoolean(Map map, Object key) {
        if (null == map)
            return null;
        Object _obj = map.get(key);
        if (null == _obj)
            return null;
        if (_obj instanceof Boolean)
            return (Boolean) _obj;
        if (_obj instanceof String)
            return Boolean.parseBoolean((String) _obj);
        if (_obj instanceof Number)
            return 0 == ((Number) _obj).intValue() ? Boolean.FALSE : Boolean.TRUE;
        return Boolean.FALSE;
    }

    @Override
    public Number getNumber(Map map, Object key) {
        if (null == map)
            return null;
        Object _obj = map.get(key);
        if (null == _obj)
            return null;
        if (_obj instanceof Number)
            return (Number) _obj;
        if (_obj instanceof String) {
            try {
                return NumberFormat.getInstance().parse((String) _obj);
            } catch (ParseException e) {
                return null;
            }
        }
        return null;
    }

    @Override
    public Integer getInteger(Map map, Object key) {
        Number _number = getNumber(map, key);
        if (null == _number)
            return null;
        if (_number instanceof Integer) {
            return (Integer) _number;
        }
        return new Integer(_number.intValue());
    }

    @Override
    public Long getLong(Map map, Object key) {
        Number _number = getNumber(map, key);
        if (null == _number)
            return null;
        if (_number instanceof Long) {
            return (Long) _number;
        }
        return new Long(_number.longValue());
    }

    @Override
    public BigDecimal getBigDecimal(Map map, Object key) {
        if (null == map)
            return null;
        Object _obj = map.get(key);
        if (null == _obj)
            return null;
        if (_obj instanceof BigDecimal)
            return (BigDecimal) _obj;
        if (_obj instanceof String)
            return new BigDecimal((String) _obj);
        return null;
    }

    @Override
    public Date getDate(Map map, Object key) {
        if (null == map)
            return null;
        Object _obj = map.get(key);
        if (null == _obj)
            return null;
        if (_obj instanceof Date)
            return (Date) _obj;
        return null;
    }

    @Override
    public List getList(Map map, Object key) {
        if (null == map)
            return null;
        Object _obj = map.get(key);
        if (null == _obj)
            return null;
        if (_obj instanceof List)
            return (List) _obj;
        return null;
    }

    @Override
    public boolean isContainsValue(Map map, Object key) {
        if (null == map || !map.containsKey(key))
            return false;
        Object _obj = map.get(key);
        if (null == _obj)
            return false;
        if (_obj instanceof Collection<?>) {
            return !((Collection<?>) _obj).isEmpty();
        }
        if (_obj instanceof Map<?, ?>) {
            return !((Map<?, ?>) _obj).isEmpty();
        }
        if (_obj instanceof Object[]) {
            return 0 != ((Object[]) _obj).length;
        }
        return 0 != _obj.toString().length();
    }

    @Override
    public void remove(Map map, Object... keys) {
        if (null == map || null == keys || 0 == keys.length)
            return;
        for (Object _key : keys) {
            map.remove(_key);
        }
    }

    @Override
    public void keep(Map map, Object... keys) {
        if (null == map || null == keys || 0 == keys.length)
            return;
        Set<Object> _keys = new HashSet<>(Arrays.asList(keys));
        Iterator _iterator = map.entrySet().iterator();
        while (_iterator.hasNext()) {
            Map.Entry _entry = (Map.Entry) _iterator.next();
            if (!_keys.contains(_entry.getKey())) {
                _iterator.remove();
            }
        }
    }

    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private SimpleMapUtils() {
        if (null != SimpleMapUtilsHolder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }
    }

    // 当前对象唯一实例持有者。
    private static final class SimpleMapUtilsHolder {
        private static final SimpleMapUtils _INSTANCE = new SimpleMapUtils();
    }

    // 防止使用反序列化操作获取多个对象实例。
    private Object readResolve() throws ObjectStreamException {
        return SimpleMapUtilsHolder._INSTANCE;
    }

    /**
     * 获取当前对象唯一实例。
     *
     * @return 当前对象唯一实例
     */
    public static SimpleMapUtils me() {
        return SimpleMapUtilsHolder._INSTANCE;
    }

}
