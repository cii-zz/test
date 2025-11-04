package com.userlabel.label.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.userlabel.core.common.Result;
import com.userlabel.core.entity.Label;
import com.userlabel.label.service.LabelService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 标签管理接口
 */
@RestController
@RequestMapping("/api/label")
public class LabelController {

    @Resource
    private LabelService labelService;

    /**
     * 查询所有标签
     */
    @GetMapping("/list")
    public Result<List<Label>> list() {
        List<Label> list = labelService.list();
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/page")
    public Result<Page<Label>> page(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Integer status) {
        Page<Label> page = labelService.page(pageNum, pageSize, name, categoryId, status);
        return Result.success(page);
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/{id}")
    public Result<Label> getById(@PathVariable Long id) {
        Label label = labelService.getById(id);
        return Result.success(label);
    }

    /**
     * 根据分类ID查询标签
     */
    @GetMapping("/category/{categoryId}")
    public Result<List<Label>> listByCategoryId(@PathVariable Long categoryId) {
        List<Label> list = labelService.listByCategoryId(categoryId);
        return Result.success(list);
    }

    /**
     * 创建标签
     */
    @PostMapping
    public Result<Boolean> create(@RequestBody Label label) {
        boolean success = labelService.create(label);
        return success ? Result.success(true) : Result.error("创建失败");
    }

    /**
     * 更新标签
     */
    @PutMapping
    public Result<Boolean> update(@RequestBody Label label) {
        boolean success = labelService.update(label);
        return success ? Result.success(true) : Result.error("更新失败");
    }

    /**
     * 删除标签
     */
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        boolean success = labelService.delete(id);
        return success ? Result.success(true) : Result.error("删除失败");
    }
}
