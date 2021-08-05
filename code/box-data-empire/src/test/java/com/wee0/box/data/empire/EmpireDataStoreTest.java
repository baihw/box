package com.wee0.box.data.empire;

import com.alibaba.druid.pool.DruidDataSource;
import com.wee0.box.BoxContext;
import com.wee0.box.data.*;
import com.wee0.box.log.ILogger;
import com.wee0.box.log.LoggerFactory;
import com.wee0.box.util.shortcut.StringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class EmpireDataStoreTest {

    static final ILogger log = LoggerFactory.getLogger(EmpireDataStoreTest.class);

    static EmpireDataStore _store;

    static void initBoxContext() {
        BoxHelper.initBoxSimple();

        DruidDataSource _ds = new DruidDataSource();
        //内存数据库
        _ds.setUrl("jdbc:h2:mem:db1;DB_CLOSE_DELAY=-1");
        _ds.setUsername("sa");
        _ds.setPassword("123456");
        _ds.setDriverClassName("org.h2.Driver");
        _ds.setValidationQuery("select 1");

        // 注册数据源对象
        BoxContext.impl().addBean("box_ds", _ds);
    }

    static void initDB() {
        EmpireDataStoreBuilder _builder = new EmpireDataStoreBuilder();
        _builder.config(IDataStore.Key.DIALECT, "h2")
                .config(IDataStore.Key.GENERATE_DDL, "True")
                .config(IDataStore.Key.DATASOURCE, "box_ds");

        _builder.withModel("sys_user")
                .field("用户标识", "user_id", IDataField.Type.Char, 32, "UUID|32")
                .field("用户名", "name", IDataField.Type.Str, 32, true, null, null)
                .field("密码", "password", IDataField.Type.Str, 64, true, null, null)
                .field("邮箱", "email", IDataField.Type.Str, 32, false, null, null)
                .field("移动电话", "mobile", IDataField.Type.Str, 32, false, null, null)
                .field("状态(0: 正常 1: 锁定)", "state", IDataField.Type.Int, 1, true, "0", null)
                .primaryKeys("user_id")
                .index("UNIQUE_SYS_USER_NAME", true, "name")
                .index(true, "email")
                .index(true, "mobile")
        ;
        appendCommonFields(_builder);

        _builder.withModel("sys_role")
                .field("角色标识", "role_id", IDataField.Type.Char, 32, "UUID|32")
                .field("角色名", "name", IDataField.Type.Str, 32, true, null, null)
                .primaryKeys("role_id")
                .index(true, "name")
        ;
        appendCommonFields(_builder);

        _builder.withModel("sys_user_role")
                .field("user_id", IDataField.Type.Char, 32, true)
                .field("role_id", IDataField.Type.Char, 32, true)
                .primaryKeys("user_id", "role_id")
                .index(true, "user_id", "role_id")
        ;

        _store = (EmpireDataStore) _builder.build();
    }

    static void appendCommonFields(EmpireDataStoreBuilder builder) {
        builder.field("创建者", "create_user", IDataField.Type.Char, 32, false, null, null)
                .field("创建时间", "create_time", IDataField.Type.Date, 1, true, "DateTime|now", null)
                .field("更新者", "update_user", IDataField.Type.Char, 32, false, null, null)
                .field("更新时间", "update_time", IDataField.Type.Date, 1, false, "DateTime", null)
                .field("假删除标记", "is_deleted", IDataField.Type.Bool, 1, true, "fixed|false", null);
    }

    static void initData() {
        // 插入初始化数据。
        IDataStoreCreator _saver = _store.newCreator();
        _saver.withModel("sys_user").clear()
                .field("name", "user1")
                .field("password", "123456")
                .save();
        _saver.clear()
                .field("name", "user2")
                .field("password", "123456")
                .save();
        _saver.withModel("sys_role").clear()
                .field("name", "管理员")
                .save();
        _saver.clear()
                .field("name", "用户")
                .save();

        // 更新数据
        IDataStoreUpdater _updater = _store.newUpdater();
        _updater.withModel("sys_user").clear()
                .field("", "")
                .save();

//        _saver.withModel("sys_user_role1").clear()
//                .field("name", "管理员")
//                .save();


//        _store.createSaver().withModel("sys_user")
//                .fields("name", "password")
//                .values("user1", "123456")
//                .values("user2", "123456")
//                .save();

    }

    @BeforeAll
    static void init() {
        initBoxContext();
        initDB();
        initData();
    }

    @Test
    void test1() {
        IDataRequest _req = new SimpleDataRequestBuilder()
                .use("sys_user")
                .field("user_id").field("name").field("password")
                .build();
        IDataResponse _response = _store.apply(_req);
        log.debug("_res: {}", _response);
    }

    @Disabled
    void test2() {
        ILogger _log = LoggerFactory.getLogger(EmpireDataStoreTest.class);
        _log.debug("hello box log...");
        String _hello = StringUtils.capitalize("helloBox");
        _log.debug("_hello: {}", _hello);
//        Logger _log1 = LogUtil.getLogger(EmpireDataStoreTest.class);
//        _log1.debug("hello log...");
    }

//    public static void main(String[] args) {
//        String _str = "13";
//        double _val = Double.parseDouble(_str);
//        int _part = (int) _val;
//        int _digit = (int) (_val * 100) - (_part * 100);
//        System.out.println("_val:" + _val + ", _part:" + _part + ", _digit:" + _digit);
//
//    }
}
