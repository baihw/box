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

package com.wee0.box.sql.transaction;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/11/10 15:12
 * @Description 事务管理器
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface ITxManger {

    /**
     * 默认的实现类名称
     */
    String DEF_IMPL_CLASS_NAME = "com.wee0.box.sql.dao.mybatis.MybatisTxManager";

    /**
     * 在一个事务内执行原子操作逻辑
     *
     * @param transactionLevel 事务级别
     * @param atom             原子操作逻辑
     * @return 成功返回true
     */
    boolean tx(TxLevel transactionLevel, IAtom atom);

    /**
     * 在一个事务内执行原子操作逻辑，使用框架默认的事务级别
     *
     * @param atom 原子操作逻辑
     * @return 成功返回true
     */
    boolean tx(IAtom atom);

}
