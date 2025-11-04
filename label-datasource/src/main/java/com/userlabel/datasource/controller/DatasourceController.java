package com.userlabel.datasource.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.userlabel.core.common.Result;
import com.userlabel.core.entity.Datasource;
import com.userlabel.datasource.service.DatasourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 数据源控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/datasource")
public class DatasourceController {

    @Autowired
    private DatasourceService datasourceService;

    /**
     * 创建数据源
     */
    @PostMapping
    public Result<Long> createDatasource(@RequestBody Datasource datasource) {
        Long id = datasourceService.createDatasource(datasource);
        return Result.success(id);
    }

    /**
     * 更新数据源
     */
    @PutMapping
    public Result<Boolean> updateDatasource(@RequestBody Datasource datasource) {
        boolean result = datasourceService.updateDatasource(datasource);
        return Result.success(result);
    }

    /**
     * 删除数据源
     */
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteDatasource(@PathVariable Long id) {
        boolean result = datasourceService.deleteDatasource(id);
        return Result.success(result);
    }

    /**
     * 根据ID获取数据源
     */
    @GetMapping("/{id}")
    public Result<Datasource> getDatasource(@PathVariable Long id) {
        Datasource datasource = datasourceService.getDatasourceById(id);
        return Result.success(datasource);
    }

    /**
     * 分页查询数据源列表
     */
    @GetMapping("/list")
    public Result<Page<Datasource>> listDatasources(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Datasource> page = new Page<>(pageNum, pageSize);
        Page<Datasource> result = datasourceService.listDatasources(page);
        return Result.success(result);
    }

    /**
     * 测试数据源连接
     */
    @PostMapping("/test")
    public Result<Boolean> testConnection(@RequestBody Datasource datasource) {
        boolean result = datasourceService.testConnection(datasource);
        return Result.success(result);
    }

    /**
     * 启用数据源
     */
    @PostMapping("/{id}/enable")
    public Result<Boolean> enableDatasource(@PathVariable Long id) {
        boolean result = datasourceService.enableDatasource(id);
        return Result.success(result);
    }

    /**
     * 禁用数据源
     */
    @PostMapping("/{id}/disable")
    public Result<Boolean> disableDatasource(@PathVariable Long id) {
        boolean result = datasourceService.disableDatasource(id);
        return Result.success(result);
    }

    /**
     * 获取数据源的表列表
     */
    @GetMapping("/{datasourceId}/tables")
    public Result<List<String>> getTables(@PathVariable Long datasourceId) {
        List<String> tables = datasourceService.getTables(datasourceId);
        return Result.success(tables);
    }

    /**
     * 获取表的字段列表
     */
    @GetMapping("/{datasourceId}/tables/{tableName}/columns")
    public Result<List<Map<String, Object>>> getTableColumns(
            @PathVariable Long datasourceId,
            @PathVariable String tableName) {
        List<Map<String, Object>> columns = datasourceService.getTableColumns(datasourceId, tableName);
        return Result.success(columns);
    }

    /**
     * 预览表数据
     */
    @GetMapping("/{datasourceId}/tables/{tableName}/preview")
    public Result<List<Map<String, Object>>> previewTableData(
            @PathVariable Long datasourceId,
            @PathVariable String tableName,
            @RequestParam(defaultValue = "100") Integer limit) {
        List<Map<String, Object>> data = datasourceService.previewTableData(datasourceId, tableName, limit);
        return Result.success(data);
    }

    /**
     * 执行SQL查询
     */
    @PostMapping("/{datasourceId}/query")
    public Result<List<Map<String, Object>>> executeQuery(
            @PathVariable Long datasourceId,
            @RequestBody Map<String, String> request) {
        String sql = request.get("sql");
        List<Map<String, Object>> result = datasourceService.executeQuery(datasourceId, sql);
        return Result.success(result);
    }
}