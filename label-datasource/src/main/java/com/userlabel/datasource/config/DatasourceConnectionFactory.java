package com.userlabel.datasource.config;

import com.userlabel.core.entity.Datasource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 数据源连接工厂
 */
@Slf4j
public class DatasourceConnectionFactory extends BasePooledObjectFactory<Connection> {

    private final Datasource datasource;

    public DatasourceConnectionFactory(Datasource datasource) {
        this.datasource = datasource;
    }

    @Override
    public Connection create() throws Exception {
        String url = buildJdbcUrl();
        log.info("创建数据源连接: {}", url);
        return DriverManager.getConnection(url, datasource.getUsername(), datasource.getPassword());
    }

    @Override
    public PooledObject<Connection> wrap(Connection connection) {
        return new DefaultPooledObject<>(connection);
    }

    @Override
    public void destroyObject(PooledObject<Connection> p) throws Exception {
        Connection connection = p.getObject();
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Override
    public boolean validateObject(PooledObject<Connection> p) {
        Connection connection = p.getObject();
        try {
            return connection != null && !connection.isClosed();
        } catch (Exception e) {
            log.error("验证连接失败", e);
            return false;
        }
    }

    /**
     * 构建 JDBC URL
     */
    private String buildJdbcUrl() {
        StringBuilder url = new StringBuilder();
        
        // 根据数据源类型构建 URL
        switch (datasource.getType()) {
            case 1: // StarRocks
                url.append("jdbc:mysql://");
                break;
            case 2: // MySQL
                url.append("jdbc:mysql://");
                break;
            case 3: // ClickHouse
                url.append("jdbc:clickhouse://");
                break;
            default:
                throw new IllegalArgumentException("不支持的数据源类型: " + datasource.getType());
        }

        url.append(datasource.getHost())
           .append(":")
           .append(datasource.getPort())
           .append("/")
           .append(datasource.getDatabase());

        // 添加连接参数
        if (datasource.getParams() != null && !datasource.getParams().isEmpty()) {
            url.append("?").append(datasource.getParams());
        }

        return url.toString();
    }
}