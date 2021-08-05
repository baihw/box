package com.wee0.box.data.empire;

import com.wee0.box.data.IDataStoreSelector;
import org.apache.empire.db.DBDatabase;
import org.apache.empire.db.DBDatabaseDriver;

import javax.sql.DataSource;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/6/26 7:12
 * @Description 基于Empire的数据选择操作对象实现
 * <pre>
 * 补充说明
 * </pre>
 **/
public class EmpireDataSelector extends EmpireDataOperation implements IDataStoreSelector {
    EmpireDataSelector(EmpireDataStore store) {
        super(store);
    }
}
