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

package com.wee0.box.sql.dao.mybatis;

import com.wee0.box.sql.dao.IPage;
import com.wee0.box.sql.dao.IPageHelper;
import com.wee0.box.util.shortcut.MapUtils;

import java.io.ObjectStreamException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/11/10 10:02
 * @Description mybatis环境下的分页助手实现
 * <pre>
 * 补充说明
 * </pre>
 **/
public class MybatisPageHelper implements IPageHelper {

    // 默认键名
    private static final String DEF_KEY_PAGE_NUM = "pageNum";
    private static final String DEF_KEY_PAGE_SIZE = "pageSize";
    private static final String DEF_KEY_PAGE = "page";

    // 默认参数值
    private static final long DEF_PAGE_NUM = 1;
    private static final long DEF_PAGE_SIZE = 10;

    // 页码参数名
    private String keyPageNum;
    // 每页数据量参数名
    private String keyPageSize;
    // 分页对象参数名
    private String keyPage;

    @Override
    public Map<String, Object> createPageParams(long pageNum, long pageSize) {
        Map<String, Object> _pageParams = new HashMap<>(8);
        _pageParams.put(getPageNumKey(), pageNum);
        _pageParams.put(getPageSizeKey(), pageSize);
        return _pageParams;
    }

    @Override
    public IPage parseMap(Map<String, Object> pageParams) {
        if (null == pageParams)
            return null;
        final String _KEY = getPageKey();
        if (pageParams.containsKey(_KEY)) {
            return (IPage) pageParams.get(_KEY);
        }
        SimplePage _page = new SimplePage();
        Long _pageNum = MapUtils.getLong(pageParams, getPageNumKey());
        Long _pageSize = MapUtils.getLong(pageParams, getPageSizeKey());
        _page.setPageNum(null == _pageNum ? DEF_PAGE_NUM : _pageNum);
        _page.setPageSize(null == _pageSize ? DEF_PAGE_SIZE : _pageSize);
        if (_page.getPageNum() < 1)
            _page.setPageNum(1L);
        if (_page.getPageSize() < 0)
            _page.setPageSize(0);
        pageParams.put(_KEY, _page);
        return _page;
    }

    @Override
    public String getPageNumKey() {
        return this.keyPageNum;
    }

    @Override
    public String getPageSizeKey() {
        return this.keyPageSize;
    }

    @Override
    public String getPageKey() {
        return this.keyPage;
    }

    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private MybatisPageHelper() {
        if (null != MybatisPageHelperHolder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }
        this.keyPageNum = DEF_KEY_PAGE_NUM;
        this.keyPageSize = DEF_KEY_PAGE_SIZE;
        this.keyPage = DEF_KEY_PAGE;
    }

    // 当前对象唯一实例持有者。
    private static final class MybatisPageHelperHolder {
        private static final MybatisPageHelper _INSTANCE = new MybatisPageHelper();
    }

    // 防止使用反序列化操作获取多个对象实例。
    private Object readResolve() throws ObjectStreamException {
        return MybatisPageHelperHolder._INSTANCE;
    }

    /**
     * 获取当前对象唯一实例。
     *
     * @return 当前对象唯一实例
     */
    public static MybatisPageHelper me() {
        return MybatisPageHelperHolder._INSTANCE;
    }

}
