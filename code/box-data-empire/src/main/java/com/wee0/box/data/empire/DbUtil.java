package com.wee0.box.data.empire;


import com.wee0.box.log.ILogger;
import com.wee0.box.log.LoggerFactory;

import java.sql.*;
import java.util.*;

final class DbUtil {

    static final ILogger log = LoggerFactory.getLogger(DbUtil.class);

    public static Set<String> getDBTables(Connection conn, String schema) {
        Set<String> _result = new HashSet<>(16, 1.0f);
        try (ResultSet _rs = conn.getMetaData().getTables(schema, null, null, null)) {
//            DatabaseMetaData _metaData = conn.getMetaData();
//            String _dbName = _metaData.getDatabaseProductName();
//            log.debug("dbName: {}, schema: {}", _dbName, schema);
            while (_rs.next()) {
                String _tableName = _rs.getString("TABLE_NAME");
                _result.add(_tableName);
            }
            log.debug("schema: {}, tables: {}", schema, _result);
        } catch (SQLException e) {
            e.printStackTrace();
//            log.error("sql: {}", _currentSql);
//            throw new RuntimeException(e.getMessage(), e);
        }
        return _result;
    }

    public static void showTables(Connection conn, String schema) {
        try (Statement _stmt = conn.createStatement();) {
            DatabaseMetaData _metaData = conn.getMetaData();
            String _dbName = _metaData.getDatabaseProductName();
            log.debug("dbName: {}, schema: {}", _dbName, schema);
            ResultSet _rs = _metaData.getTables(schema, null, null, null);
            ResultSetMetaData _tablesMetaData = _rs.getMetaData();
            final int _colCount = _tablesMetaData.getColumnCount();
            List<Map<String, Object>> _rows = new ArrayList<>(32);
            Map<String, Object> _rowMap = new HashMap<>(_colCount);
            while (_rs.next()) {
//                for (int _i = 1; _i <= _colCount; _i++) {
//                    Object _val = _rs.getObject(_i);
//                    String _colName = _tablesMetaData.getColumnName(_i);
//                    String _colLabel = _tablesMetaData.getColumnLabel(_i);
//                    _rowMap.put(_colName + "->" + _colLabel, _val);
//                }
                String _tableName = _rs.getString("TABLE_NAME");
                _rowMap.put("TABLE_NAME", _tableName);
                _rows.add(_rowMap);
            }
            log.debug("_rows.size: {}", _rows.size());
            log.debug("_rows: {}", _rows);

        } catch (SQLException e) {
            e.printStackTrace();
//            log.error("sql: {}", _currentSql);
//            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
