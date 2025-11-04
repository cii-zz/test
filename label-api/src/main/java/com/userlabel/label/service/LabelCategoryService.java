package com.userlabel.label.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.userlabel.core.entity.LabelCategory;
import com.userlabel.label.mapper.LabelCategoryMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 标签分类服务
 */
@Service
public class LabelCategoryService {

    @Resource
    private LabelCategoryMapper labelCategoryMapper;

    /**
     * 查询所有分类
     */
    public List<LabelCategory> list() {
        return labelCategoryMapper.selectList(
                new LambdaQueryWrapper<LabelCategory>()
                        .eq(LabelCategory::getIsDeleted, 0)
                        .orderByAsc(LabelCategory::getSort)
        );
    }

    /**
     * 分页查询
     */
    public Page<LabelCategory> page(int pageNum, int pageSize) {
        Page<LabelCategory> page = new Page<>(pageNum, pageSize);
        return labelCategoryMapper.selectPage(page,
                new LambdaQueryWrapper<LabelCategory>()
                        .eq(LabelCategory::getIsDeleted, 0)
                        .orderByAsc(LabelCategory::getSort)
        );
    }

    /**
     * 根据ID查询
     */
    public LabelCategory getById(Long id) {
        return labelCategoryMapper.selectById(id);
    }

    /**
     * 创建分类
     */
    public boolean create(LabelCategory category) {
        return labelCategoryMapper.insert(category) > 0;
    }

    /**
     * 更新分类
     */
    public boolean update(LabelCategory category) {
        return labelCategoryMapper.updateById(category) > 0;
    }

    /**
     * 删除分类（逻辑删除）
     */
    public boolean delete(Long id) {
        LabelCategory category = new LabelCategory();
        category.setId(id);
        category.setIsDeleted(1);
        return labelCategoryMapper.updateById(category) > 0;
    }
}
