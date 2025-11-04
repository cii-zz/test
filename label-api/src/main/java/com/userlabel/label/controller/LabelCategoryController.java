package com.userlabel.label.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.userlabel.core.common.Result;
import com.userlabel.core.entity.LabelCategory;
import com.userlabel.label.service.LabelCategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 标签分类管理接口
 */
@RestController
@RequestMapping("/api/label/category")
public class LabelCategoryController {

    @Resource
    private LabelCategoryService labelCategoryService;

    /**
     * 查询所有分类
     */
    @GetMapping("/list")
    public Result<List<LabelCategory>> list() {
        List<LabelCategory> list = labelCategoryService.list();
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/page")
    public Result<Page<LabelCategory>> page(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        Page<LabelCategory> page = labelCategoryService.page(pageNum, pageSize);
        return Result.success(page);
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/{id}")
    public Result<LabelCategory> getById(@PathVariable Long id) {
        LabelCategory category = labelCategoryService.getById(id);
        return Result.success(category);
    }

    /**
     * 创建分类
     */
    @PostMapping
    public Result<Boolean> create(@RequestBody LabelCategory category) {
        boolean success = labelCategoryService.create(category);
        return success ? Result.success(true) : Result.error("创建失败");
    }

    /**
     * 更新分类
     */
    @PutMapping
    public Result<Boolean> update(@RequestBody LabelCategory category) {
        boolean success = labelCategoryService.update(category);
        return success ? Result.success(true) : Result.error("更新失败");
    }

    /**
     * 删除分类
     */
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        boolean success = labelCategoryService.delete(id);
        return success ? Result.success(true) : Result.error("删除失败");
    }
}
