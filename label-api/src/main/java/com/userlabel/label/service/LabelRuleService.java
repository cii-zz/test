package com.userlabel.label.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.userlabel.core.entity.LabelRule;
import com.userlabel.label.mapper.LabelRuleMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 标签规则服务
 */
@Service
public class LabelRuleService {

    @Resource
    private LabelRuleMapper labelRuleMapper;

    /**
     * 根据标签ID查询规则列表
     */
    public List<LabelRule> listByLabelId(Long labelId) {
        return labelRuleMapper.selectList(
                new LambdaQueryWrapper<LabelRule>()
                        .eq(LabelRule::getLabelId, labelId)
                        .eq(LabelRule::getIsDeleted, 0)
                        .orderByDesc(LabelRule::getCreateTime)
        );
    }

    /**
     * 分页查询
     */
    public Page<LabelRule> page(int pageNum, int pageSize, Long labelId) {
        Page<LabelRule> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<LabelRule> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LabelRule::getIsDeleted, 0);
        
        if (labelId != null) {
            wrapper.eq(LabelRule::getLabelId, labelId);
        }
        
        wrapper.orderByDesc(LabelRule::getCreateTime);
        
        return labelRuleMapper.selectPage(page, wrapper);
    }

    /**
     * 根据ID查询
     */
    public LabelRule getById(Long id) {
        return labelRuleMapper.selectById(id);
    }

    /**
     * 创建规则
     */
    public boolean create(LabelRule rule) {
        return labelRuleMapper.insert(rule) > 0;
    }

    /**
     * 更新规则
     */
    public boolean update(LabelRule rule) {
        return labelRuleMapper.updateById(rule) > 0;
    }

    /**
     * 删除规则（逻辑删除）
     */
    public boolean delete(Long id) {
        LabelRule rule = new LabelRule();
        rule.setId(id);
        rule.setIsDeleted(1);
        return labelRuleMapper.updateById(rule) > 0;
    }
}
