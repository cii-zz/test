package com.userlabel.datasource.config;

import com.userlabel.core.entity.Datasource;
import lombok.Data;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据源连接池配置
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "datasource.pool")
public class DatasourcePoolConfig {

    /**
     * 最大连接数
     */
    private int maxTotal = 20;

    /**
     * 最大空闲连接数
     */
    private int maxIdle = 10;

    /**
     * 最小空闲连接数
     */
    private int minIdle = 5;

    /**
     * 获取连接超时时间（毫秒）
     */
    private long maxWaitMillis = 3000;

    /**
     * 连接池配置
     */
    @Bean
    public GenericObjectPoolConfig<Connection> poolConfig() {
        GenericObjectPoolConfig<Connection> config = new GenericObjectPoolConfig<>();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setMaxWaitMillis(maxWaitMillis);
        return config;
    }

    /**
     * 数据源连接池Map
     */
    @Bean
    public Map<Long, GenericObjectPool<Connection>> connectionPoolMap() {
        return new ConcurrentHashMap<>();
    }

    /**
     * 创建连接池
     */
    public GenericObjectPool<Connection> createPool(Datasource datasource) {
        return new GenericObjectPool<>(new DatasourceConnectionFactory(datasource), poolConfig());
    }
}