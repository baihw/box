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

import com.wee0.box.log.ILogger;
import com.wee0.box.log.LoggerFactory;
import com.wee0.box.sql.transaction.IAtom;
import com.wee0.box.sql.transaction.ITxManger;
import com.wee0.box.sql.transaction.TxLevel;
import org.apache.ibatis.session.SqlSessionManager;
import org.apache.ibatis.session.TransactionIsolationLevel;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.TransactionFactory;

import javax.sql.DataSource;
import java.io.ObjectStreamException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/11/10 15:22
 * @Description 基于Mybatis的事务管理器实现
 * <pre>
 * 补充说明
 * </pre>
 **/
public class MybatisTxManager implements ITxManger {

    // 日志对象
    private static ILogger log = LoggerFactory.getLogger(MybatisTxManager.class);

    private SqlSessionManager sqlSessionManager;
    private TransactionFactory transactionFactory;
    private DataSource dataSource;

    @Override
    public boolean tx(TxLevel transactionLevel, IAtom atom) {
        TransactionIsolationLevel _level = parseTxLevel(transactionLevel);
        this.sqlSessionManager.startManagedSession(_level);
        try {
            atom.run();
            this.sqlSessionManager.commit();
            return true;
        } catch (Exception e) {
            log.debug("Transaction run error!", e);
            this.sqlSessionManager.rollback();
        } finally {
            this.sqlSessionManager.close();
        }
        return false;
    }

    @Override
    public boolean tx(IAtom atom) {
        return tx(TxLevel.REPEATABLE_READ, atom);
    }

    void init(SqlSessionManager sqlSessionManager, TransactionFactory transactionFactory, DataSource dataSource) {
        this.sqlSessionManager = sqlSessionManager;
        this.transactionFactory = transactionFactory;
        this.dataSource = dataSource;
    }

    static TransactionIsolationLevel parseTxLevel(TxLevel level) {
        switch (level) {
            case NONE:
                return TransactionIsolationLevel.NONE;
            case READ_COMMITTED:
                return TransactionIsolationLevel.READ_COMMITTED;
            case READ_UNCOMMITTED:
                return TransactionIsolationLevel.READ_UNCOMMITTED;
            case REPEATABLE_READ:
                return TransactionIsolationLevel.REPEATABLE_READ;
            case SERIALIZABLE:
                return TransactionIsolationLevel.SERIALIZABLE;
            default:
                return TransactionIsolationLevel.REPEATABLE_READ;
        }
    }

    static TransactionIsolationLevel parseIsolationLevel(int level) {
        switch (level) {
            case Connection.TRANSACTION_NONE:
                return TransactionIsolationLevel.NONE;
            case Connection.TRANSACTION_READ_COMMITTED:
                return TransactionIsolationLevel.READ_COMMITTED;
            case Connection.TRANSACTION_READ_UNCOMMITTED:
                return TransactionIsolationLevel.READ_UNCOMMITTED;
            case Connection.TRANSACTION_SERIALIZABLE:
                return TransactionIsolationLevel.SERIALIZABLE;
            default:
                return TransactionIsolationLevel.REPEATABLE_READ;
        }
    }

    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private MybatisTxManager() {
        if (null != MybatisTxManagerHolder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }
    }

    // 当前对象唯一实例持有者。
    private static final class MybatisTxManagerHolder {
        private static final MybatisTxManager _INSTANCE = new MybatisTxManager();
    }

    // 防止使用反序列化操作获取多个对象实例。
    private Object readResolve() throws ObjectStreamException {
        return MybatisTxManagerHolder._INSTANCE;
    }

    /**
     * 获取当前对象唯一实例。
     *
     * @return 当前对象唯一实例
     */
    public static MybatisTxManager me() {
        return MybatisTxManagerHolder._INSTANCE;
    }

}
