package com.userlabel.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 标签实体类
 */
@Data
@Accessors(chain = true)
@TableName("t_label")
public class Label {
    
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 标签名称
     */
    private String name;

    /**
     * 标签编码
     */
    private String code;

    /**
     * 标签分类ID
     */
    private Long categoryId;

    /**
     * 标签业务类型：1-基础标签，2-衍生标签
     */
    private Integer businessType;

    /**
     * 数据类型：1-String，2-Number，3-Boolean，4-Date
     */
    private Integer dataType;

    /**
     * 标签状态：0-未发布，1-已发布，2-已下线
     */
    private Integer status;

    /**
     * 更新周期：1-实时，2-每天，3-每周，4-每月
     */
    private Integer updateCycle;

    /**
     * 标签描述
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