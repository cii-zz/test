package com.userlabel.label.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.userlabel.core.common.Result;
import com.userlabel.core.entity.LabelRule;
import com.userlabel.label.service.LabelRuleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 标签规则管理接口
 */
@RestController
@RequestMapping("/api/label/rule")
public class LabelRuleController {

    @Resource
    private LabelRuleService labelRuleService;

    /**
     * 根据标签ID查询规则列表
     */
    @GetMapping("/label/{labelId}")
    public Result<List<LabelRule>> listByLabelId(@PathVariable Long labelId) {
        List<LabelRule> list = labelRuleService.listByLabelId(labelId);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/page")
    public Result<Page<LabelRule>> page(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) Long labelId) {
        Page<LabelRule> page = labelRuleService.page(pageNum, pageSize, labelId);
        return Result.success(page);
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/{id}")
    public Result<LabelRule> getById(@PathVariable Long id) {
        LabelRule rule = labelRuleService.getById(id);
        return Result.success(rule);
    }

    /**
     * 创建规则
     */
    @PostMapping
    public Result<Boolean> create(@RequestBody LabelRule rule) {
        boolean success = labelRuleService.create(rule);
        return success ? Result.success(true) : Result.error("创建失败");
    }

    /**
     * 更新规则
     */
    @PutMapping
    public Result<Boolean> update(@RequestBody LabelRule rule) {
        boolean success = labelRuleService.update(rule);
        return success ? Result.success(true) : Result.error("更新失败");
    }

    /**
     * 删除规则
     */
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        boolean success = labelRuleService.delete(id);
        return success ? Result.success(true) : Result.error("删除失败");
    }
}
