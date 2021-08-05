package com.wee0.box.data.empire;

import com.wee0.box.BoxContext;
import com.wee0.box.data.*;
import com.wee0.box.log.ILogger;
import com.wee0.box.log.LoggerFactory;
import com.wee0.box.util.shortcut.CheckUtils;
import com.wee0.box.util.shortcut.MapUtils;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.empire.db.DBDatabase;
import org.apache.empire.db.DBDatabaseDriver;
import org.apache.empire.db.DBSQLScript;
import org.apache.empire.db.h2.DBDatabaseDriverH2;
import org.apache.empire.db.mysql.DBDatabaseDriverMySQL;
import org.apache.empire.db.postgresql.DBDatabaseDriverPostgreSQL;
import org.apache.empire.db.sqlite.DBDatabaseDriverSQLite;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/5/27 7:25
 * @Description 一个基于Empire的数据存储对象实现
 * <pre>
 * 补充说明
 * </pre>
 **/
@EqualsAndHashCode
@ToString
public class EmpireDataStore implements IDataStore {

    private static final ILogger log = LoggerFactory.getLogger(EmpireDataStore.class);

    // 配置信息
    private Map<String, Object> _config;
    // 数据库驱动
    private DBDatabaseDriver _driver;
    private DataSource dataSource = null; // DsUtil.getH2MemDataSource();

    // Empire数据库模式对象
    private final DBDatabase _DB;
    // 模型集合
    private Map<String, IDataModel> _models;

    EmpireDataStore() {
        this._DB = new EmpireDBDatabase();
        this._models = new HashMap<>(32, 1.0f);
    }

    @Override
    public void init(Map<String, Object> config) {
        if (null != this._config)
            throw new IllegalStateException("EmpireDataStore already initialized!");
        this._config = new HashMap<>(16 > config.size() ? 16 : config.size());
        this._config.putAll(config);

        // 判断使用的数据库方言
        String _dialect = String.valueOf(this._config.get(Key.DIALECT.name()));
        switch (_dialect) {
            case "h2":
                this._driver = new DBDatabaseDriverH2();
                break;
            case "mysql":
                this._driver = new DBDatabaseDriverMySQL();
                break;
            case "pg":
                this._driver = new DBDatabaseDriverPostgreSQL();
                break;
            case "SQLite":
                this._driver = new DBDatabaseDriverSQLite();
                break;
            default:
                throw new IllegalArgumentException("unKnow dialect: " + _dialect);
        }

        // 获取数据库连接池对象
        String _dsObjName = MapUtils.getString(this._config, Key.DATASOURCE.name());
        _dsObjName = CheckUtils.checkNotTrimEmpty(_dsObjName, Key.DATASOURCE.name() + " cannot be empty!");
        this.dataSource = BoxContext.impl().getBean(_dsObjName, DataSource.class);
        CheckUtils.checkNotNull(this.dataSource, "datasource cannot be null!");

        // 判断是否需要进行数据库初始化操作
        String _generate_ddl = String.valueOf(this._config.get(Key.GENERATE_DDL.name())).toLowerCase();
        if ("true".equals(_generate_ddl)) {
            try (Connection _conn = this.dataSource.getConnection()) {
                this._DB.open(this._driver, _conn);
                Set<String> _tables = DbUtil.getDBTables(_conn, this._DB.getSchema());

//            // 判断数据库表是否已经存在，存在先删除。
//            DBSQLScript _dropSql = new DBSQLScript();
//            for (DBTable _table : db.getTables()) {
//                if (_tables.contains(_table.getName())) {
//                    driver.getDDLScript(DBCmdType.DROP, _table, _dropSql);
//                }
//            }
//            _dropSql.executeAll(driver, _conn, false);

                DBSQLScript _createSql = new DBSQLScript();
                this._DB.getCreateDDLScript(this._driver, _createSql);
                log.debug("_script: {}", _createSql.toString());
                _createSql.executeAll(this._driver, _conn, false);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void destroy() {

    }

    @Override
    public IDataModel addModel(String modelName) {
        IDataModel _model = this._models.get(modelName);
        if (null == _model) {
            _model = new EmpireDataModel(this._DB, modelName);
            this._models.put(modelName, _model);
        }
        return _model;
    }

    @Override
    public IDataModel getModel(String modelName) {
        return this._models.get(modelName);
    }

    @Override
    public IDataStoreCreator newCreator() {
        return new EmpireDataCreator(this);
    }

    @Override
    public IDataStoreUpdater newUpdater() {
        return new EmpireDataUpdater(this);
    }

    @Override
    public IDataStoreDeleter newDeleter() {
        return new EmpireDataDeleter(this);
    }

    @Override
    public IDataStoreSelector newSelector() {
        return new EmpireDataSelector(this);
    }

    @Override
    public IDataResponse apply(IDataRequest request) {
        log.debug("request: {}", request);
        Set<IDataRequestModel> _models = request.getModels();
        for (IDataRequestModel _model : _models) {
            log.debug("model: {}", _model);
        }
        IDataResponse _res = new SimpleDataResponse();
        return _res;
    }

    /************************************************************
     * 内部方法
     ************************************************************/
    /**
     * @return Empire数据库模式对象
     */
    DBDatabase getDB() {
        return this._DB;
    }

    /**
     * @return 使用的数据源
     */
    DataSource getDataSource() {
        return this.dataSource;
    }

//    /************************************************************
//     ************* 单例样板代码。
//     ************************************************************/
//    private EmpireDataStore() {
//        if (null != EmpireDataStoreHolder._INSTANCE) {
//            // 防止使用反射API创建对象实例。
//            throw new IllegalStateException("that's not allowed!");
//        }
//        this._DB = new EmpireDBDatabase();
//        this._models = new HashMap<>(32, 1.0f);
//    }
//
//    // 当前对象唯一实例持有者。
//    private static final class EmpireDataStoreHolder {
//        private static final EmpireDataStore _INSTANCE = new EmpireDataStore();
//    }
//
//    // 防止使用反序列化操作获取多个对象实例。
//    private Object readResolve() throws ObjectStreamException {
//        return EmpireDataStoreHolder._INSTANCE;
//    }
//
//    /**
//     * 获取当前对象唯一实例。
//     *
//     * @return 当前对象唯一实例
//     */
//    public static EmpireDataStore me() {
//        return EmpireDataStoreHolder._INSTANCE;
//    }


}
