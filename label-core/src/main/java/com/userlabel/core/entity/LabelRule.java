package com.userlabel.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 标签规则实体类
 */
@Data
@Accessors(chain = true)
@TableName("t_label_rule")
public class LabelRule {
    
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 标签ID
     */
    private Long labelId;

    /**
     * 规则名称
     */
    private String name;

    /**
     * 规则类型：1-SQL规则，2-实时规则
     */
    private Integer ruleType;

    /**
     * 数据源ID
     */
    private Long datasourceId;

    /**
     * 规则内容（SQL语句或规则表达式）
     */
    private String content;

    /**
     * 规则状态：0-未启用，1-已启用
     */
    private Integer status;

    /**
     * 规则版本
     */
    private String version;

    /**
     * 规则描述
     */
    private String description;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 更新人
     */
    private String updater;

    /**
     * 是否删除：0-未删除，1-已删除
     */
    private Integer isDeleted;
}