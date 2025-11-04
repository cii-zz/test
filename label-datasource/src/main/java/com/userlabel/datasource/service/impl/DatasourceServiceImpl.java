package com.userlabel.datasource.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.userlabel.core.entity.Datasource;
import com.userlabel.core.exception.BusinessException;
import com.userlabel.datasource.mapper.DatasourceMapper;
import com.userlabel.datasource.service.DatasourceManager;
import com.userlabel.datasource.service.DatasourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 数据源服务实现类
 */
@Slf4j
@Service
public class DatasourceServiceImpl implements DatasourceService {

    @Autowired
    private DatasourceMapper datasourceMapper;

    @Autowired
    private DatasourceManager datasourceManager;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createDatasource(Datasource datasource) {
        // 测试连接
        if (!datasourceManager.testConnection(datasource)) {
            throw new BusinessException("数据源连接失败，请检查配置");
        }

        // 设置创建时间
        datasource.setCreateTime(LocalDateTime.now());
        datasource.setUpdateTime(LocalDateTime.now());
        datasource.setStatus(0); // 默认未启用
        datasource.setIsDeleted(0);

        // 保存到数据库
        datasourceMapper.insert(datasource);

        log.info("创建数据源成功: {}", datasource.getName());
        return datasource.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateDatasource(Datasource datasource) {
        // 检查数据源是否存在
        Datasource existDatasource = datasourceMapper.selectById(datasource.getId());
        if (existDatasource == null) {
            throw new BusinessException("数据源不存在");
        }

        // 测试连接
        if (!datasourceManager.testConnection(datasource)) {
            throw new BusinessException("数据源连接失败，请检查配置");
        }

        // 更新数据源
        datasource.setUpdateTime(LocalDateTime.now());
        int result = datasourceMapper.updateById(datasource);

        // 如果数据源已启用，重新注册
        if (existDatasource.getStatus() == 1) {
            datasourceManager.unregisterDatasource(datasource.getId());
            datasourceManager.registerDatasource(datasource);
        }

        log.info("更新数据源成功: {}", datasource.getName());
        return result > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteDatasource(Long id) {
        // 检查数据源是否存在
        Datasource datasource = datasourceMapper.selectById(id);
        if (datasource == null) {
            throw new BusinessException("数据源不存在");
        }

        // 注销数据源
        if (datasource.getStatus() == 1) {
            datasourceManager.unregisterDatasource(id);
        }

        // 逻辑删除
        datasource.setIsDeleted(1);
        datasource.setUpdateTime(LocalDateTime.now());
        int result = datasourceMapper.updateById(datasource);

        log.info("删除数据源成功: {}", datasource.getName());
        return result > 0;
    }

    @Override
    public Datasource getDatasourceById(Long id) {
        Datasource datasource = datasourceMapper.selectById(id);
        if (datasource == null || datasource.getIsDeleted() == 1) {
            throw new BusinessException("数据源不存在");
        }
        return datasource;
    }

    @Override
    public Page<Datasource> listDatasources(Page<Datasource> page) {
        return datasourceMapper.selectPage(page, null);
    }

    @Override
    public boolean testConnection(Datasource datasource) {
        return datasourceManager.testConnection(datasource);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean enableDatasource(Long id) {
        Datasource datasource = getDatasourceById(id);
        
        // 注册数据源
        datasourceManager.registerDatasource(datasource);

        // 更新状态
        datasource.setStatus(1);
        datasource.setUpdateTime(LocalDateTime.now());
        int result = datasourceMapper.updateById(datasource);

        log.info("启用数据源成功: {}", datasource.getName());
        return result > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean disableDatasource(Long id) {
        Datasource datasource = getDatasourceById(id);

        // 注销数据源
        datasourceManager.unregisterDatasource(id);

        // 更新状态
        datasource.setStatus(0);
        datasource.setUpdateTime(LocalDateTime.now());
        int result = datasourceMapper.updateById(datasource);

        log.info("禁用数据源成功: {}", datasource.getName());
        return result > 0;
    }

    @Override
    public List<String> getTables(Long datasourceId) {
        try {
            return datasourceManager.getTables(datasourceId);
        } catch (Exception e) {
            log.error("获取表列表失败", e);
            throw new BusinessException("获取表列表失败: " + e.getMessage());
        }
    }

    @Override
    public List<Map<String, Object>> getTableColumns(Long datasourceId, String tableName) {
        try {
            return datasourceManager.getTableColumns(datasourceId, tableName);
        } catch (Exception e) {
            log.error("获取表字段失败", e);
            throw new BusinessException("获取表字段失败: " + e.getMessage());
        }
    }

    @Override
    public List<Map<String, Object>> previewTableData(Long datasourceId, String tableName, Integer limit) {
        try {
            String sql = String.format("SELECT * FROM %s LIMIT %d", tableName, limit == null ? 100 : limit);
            return datasourceManager.executeQuery(datasourceId, sql);
        } catch (Exception e) {
            log.error("预览表数据失败", e);
            throw new BusinessException("预览表数据失败: " + e.getMessage());
        }
    }

    @Override
    public List<Map<String, Object>> executeQuery(Long datasourceId, String sql) {
        try {
            return datasourceManager.executeQuery(datasourceId, sql);
        } catch (Exception e) {
            log.error("执行SQL查询失败", e);
            throw new BusinessException("执行SQL查询失败: " + e.getMessage());
        }
    }
}