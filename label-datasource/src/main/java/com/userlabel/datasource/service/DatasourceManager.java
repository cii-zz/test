package com.userlabel.datasource.service;

import com.userlabel.core.entity.Datasource;
import com.userlabel.datasource.config.DatasourcePoolConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据源管理服务
 */
@Slf4j
@Service
public class DatasourceManager {

    @Autowired
    private DatasourcePoolConfig poolConfig;

    @Autowired
    private Map<Long, GenericObjectPool<Connection>> connectionPoolMap;

    /**
     * 获取数据源连接
     */
    public Connection getConnection(Long datasourceId) throws Exception {
        GenericObjectPool<Connection> pool = connectionPoolMap.get(datasourceId);
        if (pool == null) {
            throw new IllegalArgumentException("数据源不存在: " + datasourceId);
        }
        return pool.borrowObject();
    }

    /**
     * 归还数据源连接
     */
    public void returnConnection(Long datasourceId, Connection connection) {
        GenericObjectPool<Connection> pool = connectionPoolMap.get(datasourceId);
        if (pool != null && connection != null) {
            pool.returnObject(connection);
        }
    }

    /**
     * 注册数据源
     */
    public void registerDatasource(Datasource datasource) {
        try {
            GenericObjectPool<Connection> pool = poolConfig.createPool(datasource);
            connectionPoolMap.put(datasource.getId(), pool);
            log.info("注册数据源成功: {}", datasource.getName());
        } catch (Exception e) {
            log.error("注册数据源失败: {}", datasource.getName(), e);
            throw new RuntimeException("注册数据源失败", e);
        }
    }

    /**
     * 注销数据源
     */
    public void unregisterDatasource(Long datasourceId) {
        GenericObjectPool<Connection> pool = connectionPoolMap.remove(datasourceId);
        if (pool != null) {
            pool.close();
            log.info("注销数据源成功: {}", datasourceId);
        }
    }

    /**
     * 测试数据源连接
     */
    public boolean testConnection(Datasource datasource) {
        Connection connection = null;
        try {
            GenericObjectPool<Connection> pool = poolConfig.createPool(datasource);
            connection = pool.borrowObject();
            boolean valid = connection.isValid(3);
            pool.returnObject(connection);
            pool.close();
            return valid;
        } catch (Exception e) {
            log.error("测试数据源连接失败", e);
            return false;
        }
    }

    /**
     * 执行查询
     */
    public List<Map<String, Object>> executeQuery(Long datasourceId, String sql) throws Exception {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = getConnection(datasourceId);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            
            List<Map<String, Object>> result = new ArrayList<>();
            int columnCount = resultSet.getMetaData().getColumnCount();
            
            while (resultSet.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = resultSet.getMetaData().getColumnName(i);
                    Object value = resultSet.getObject(i);
                    row.put(columnName, value);
                }
                result.add(row);
            }
            
            return result;
        } finally {
            closeResources(resultSet, statement, null);
            if (connection != null) {
                returnConnection(datasourceId, connection);
            }
        }
    }

    /**
     * 获取表列表
     */
    public List<String> getTables(Long datasourceId) throws Exception {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = getConnection(datasourceId);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SHOW TABLES");
            
            List<String> tables = new ArrayList<>();
            while (resultSet.next()) {
                tables.add(resultSet.getString(1));
            }
            
            return tables;
        } finally {
            closeResources(resultSet, statement, null);
            if (connection != null) {
                returnConnection(datasourceId, connection);
            }
        }
    }

    /**
     * 获取表字段列表
     */
    public List<Map<String, Object>> getTableColumns(Long datasourceId, String tableName) throws Exception {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = getConnection(datasourceId);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("DESC " + tableName);
            
            List<Map<String, Object>> columns = new ArrayList<>();
            while (resultSet.next()) {
                Map<String, Object> column = new HashMap<>();
                column.put("field", resultSet.getString("Field"));
                column.put("type", resultSet.getString("Type"));
                column.put("null", resultSet.getString("Null"));
                column.put("key", resultSet.getString("Key"));
                column.put("default", resultSet.getString("Default"));
                column.put("extra", resultSet.getString("Extra"));
                columns.add(column);
            }
            
            return columns;
        } finally {
            closeResources(resultSet, statement, null);
            if (connection != null) {
                returnConnection(datasourceId, connection);
            }
        }
    }

    /**
     * 关闭资源
     */
    private void closeResources(ResultSet rs, Statement stmt, Connection conn) {
        try {
            if (rs != null) rs.close();
        } catch (SQLException e) {
            log.error("关闭ResultSet失败", e);
        }
        
        try {
            if (stmt != null) stmt.close();
        } catch (SQLException e) {
            log.error("关闭Statement失败", e);
        }
        
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            log.error("关闭Connection失败", e);
        }
    }
}