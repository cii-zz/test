package com.userlabel.label.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.userlabel.core.entity.Label;
import com.userlabel.label.mapper.LabelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 标签服务
 */
@Service
public class LabelService {

    @Resource
    private LabelMapper labelMapper;

    /**
     * 查询所有标签
     */
    public List<Label> list() {
        return labelMapper.selectList(
                new LambdaQueryWrapper<Label>()
                        .eq(Label::getIsDeleted, 0)
        );
    }

    /**
     * 分页查询
     */
    public Page<Label> page(int pageNum, int pageSize, String name, Long categoryId, Integer status) {
        Page<Label> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Label> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Label::getIsDeleted, 0);
        
        if (StringUtils.hasText(name)) {
            wrapper.like(Label::getName, name);
        }
        if (categoryId != null) {
            wrapper.eq(Label::getCategoryId, categoryId);
        }
        if (status != null) {
            wrapper.eq(Label::getStatus, status);
        }
        
        wrapper.orderByDesc(Label::getCreateTime);
        
        return labelMapper.selectPage(page, wrapper);
    }

    /**
     * 根据ID查询
     */
    public Label getById(Long id) {
        return labelMapper.selectById(id);
    }

    /**
     * 根据分类ID查询标签
     */
    public List<Label> listByCategoryId(Long categoryId) {
        return labelMapper.selectList(
                new LambdaQueryWrapper<Label>()
                        .eq(Label::getCategoryId, categoryId)
                        .eq(Label::getIsDeleted, 0)
        );
    }

    /**
     * 创建标签
     */
    public boolean create(Label label) {
        return labelMapper.insert(label) > 0;
    }

    /**
     * 更新标签
     */
    public boolean update(Label label) {
        return labelMapper.updateById(label) > 0;
    }

    /**
     * 删除标签（逻辑删除）
     */
    public boolean delete(Long id) {
        Label label = new Label();
        label.setId(id);
        label.setIsDeleted(1);
        return labelMapper.updateById(label) > 0;
    }
}
