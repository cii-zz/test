package com.userlabel.datasource.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.userlabel.core.entity.Datasource;

import java.util.List;
import java.util.Map;

/**
 * 数据源服务接口
 */
public interface DatasourceService {

    /**
     * 创建数据源
     */
    Long createDatasource(Datasource datasource);

    /**
     * 更新数据源
     */
    boolean updateDatasource(Datasource datasource);

    /**
     * 删除数据源
     */
    boolean deleteDatasource(Long id);

    /**
     * 根据ID获取数据源
     */
    Datasource getDatasourceById(Long id);

    /**
     * 分页查询数据源列表
     */
    Page<Datasource> listDatasources(Page<Datasource> page);

    /**
     * 测试数据源连接
     */
    boolean testConnection(Datasource datasource);

    /**
     * 启用数据源
     */
    boolean enableDatasource(Long id);

    /**
     * 禁用数据源
     */
    boolean disableDatasource(Long id);

    /**
     * 获取数据源的表列表
     */
    List<String> getTables(Long datasourceId);

    /**
     * 获取表的字段列表
     */
    List<Map<String, Object>> getTableColumns(Long datasourceId, String tableName);

    /**
     * 预览表数据
     */
    List<Map<String, Object>> previewTableData(Long datasourceId, String tableName, Integer limit);

    /**
     * 执行SQL查询
     */
    List<Map<String, Object>> executeQuery(Long datasourceId, String sql);
}