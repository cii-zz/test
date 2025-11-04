-- 用户画像标签系统数据库初始化脚本

-- 创建数据库
CREATE DATABASE IF NOT EXISTS user_labels DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE user_labels;

-- 数据源配置表
CREATE TABLE IF NOT EXISTS t_datasource (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    name VARCHAR(100) NOT NULL COMMENT '数据源名称',
    type INT NOT NULL COMMENT '数据源类型：1-StarRocks，2-MySQL，3-ClickHouse',
    host VARCHAR(200) NOT NULL COMMENT '主机地址',
    port INT NOT NULL COMMENT '端口',
    database_name VARCHAR(100) NOT NULL COMMENT '数据库名',
    username VARCHAR(100) NOT NULL COMMENT '用户名',
    password VARCHAR(200) NOT NULL COMMENT '密码',
    params VARCHAR(500) COMMENT '连接参数',
    status INT DEFAULT 0 COMMENT '数据源状态：0-未启用，1-已启用',
    description VARCHAR(500) COMMENT '描述',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    creator VARCHAR(50) COMMENT '创建人',
    updater VARCHAR(50) COMMENT '更新人',
    is_deleted INT DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
    INDEX idx_name (name),
    INDEX idx_status (status),
    INDEX idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='数据源配置表';

-- 标签分类表
CREATE TABLE IF NOT EXISTS t_label_category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    name VARCHAR(100) NOT NULL COMMENT '分类名称',
    code VARCHAR(100) NOT NULL COMMENT '分类编码',
    parent_id BIGINT DEFAULT 0 COMMENT '父分类ID',
    level INT DEFAULT 1 COMMENT '分类层级',
    sort INT DEFAULT 0 COMMENT '排序号',
    description VARCHAR(500) COMMENT '分类描述',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    creator VARCHAR(50) COMMENT '创建人',
    updater VARCHAR(50) COMMENT '更新人',
    is_deleted INT DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
    UNIQUE KEY uk_code (code),
    INDEX idx_parent_id (parent_id),
    INDEX idx_level (level)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='标签分类表';

-- 标签表
CREATE TABLE IF NOT EXISTS t_label (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    name VARCHAR(100) NOT NULL COMMENT '标签名称',
    code VARCHAR(100) NOT NULL COMMENT '标签编码',
    category_id BIGINT NOT NULL COMMENT '标签分类ID',
    business_type INT NOT NULL COMMENT '标签业务类型：1-基础标签，2-衍生标签',
    data_type INT NOT NULL COMMENT '数据类型：1-String，2-Number，3-Boolean，4-Date',
    status INT DEFAULT 0 COMMENT '标签状态：0-未发布，1-已发布，2-已下线',
    update_cycle INT DEFAULT 1 COMMENT '更新周期：1-实时，2-每天，3-每周，4-每月',
    description VARCHAR(500) COMMENT '标签描述',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    creator VARCHAR(50) COMMENT '创建人',
    updater VARCHAR(50) COMMENT '更新人',
    is_deleted INT DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
    UNIQUE KEY uk_code (code),
    INDEX idx_category_id (category_id),
    INDEX idx_status (status),
    INDEX idx_business_type (business_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='标签表';

-- 标签规则表
CREATE TABLE IF NOT EXISTS t_label_rule (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    label_id BIGINT NOT NULL COMMENT '标签ID',
    name VARCHAR(100) NOT NULL COMMENT '规则名称',
    rule_type INT NOT NULL COMMENT '规则类型：1-SQL规则，2-实时规则',
    datasource_id BIGINT NOT NULL COMMENT '数据源ID',
    content TEXT NOT NULL COMMENT '规则内容（SQL语句或规则表达式）',
    status INT DEFAULT 0 COMMENT '规则状态：0-未启用，1-已启用',
    version VARCHAR(20) COMMENT '规则版本',
    description VARCHAR(500) COMMENT '规则描述',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    creator VARCHAR(50) COMMENT '创建人',
    updater VARCHAR(50) COMMENT '更新人',
    is_deleted INT DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
    INDEX idx_label_id (label_id),
    INDEX idx_datasource_id (datasource_id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='标签规则表';

-- 标签计算任务表
CREATE TABLE IF NOT EXISTS t_label_task (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    label_id BIGINT NOT NULL COMMENT '标签ID',
    rule_id BIGINT NOT NULL COMMENT '规则ID',
    task_type INT NOT NULL COMMENT '任务类型：1-全量计算，2-增量计算',
    status INT DEFAULT 0 COMMENT '任务状态：0-待执行，1-执行中，2-执行成功，3-执行失败',
    start_time DATETIME COMMENT '开始时间',
    end_time DATETIME COMMENT '结束时间',
    result_count BIGINT DEFAULT 0 COMMENT '结果数量',
    error_message TEXT COMMENT '错误信息',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    creator VARCHAR(50) COMMENT '创建人',
    INDEX idx_label_id (label_id),
    INDEX idx_rule_id (rule_id),
    INDEX idx_status (status),
    INDEX idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='标签计算任务表';

-- 用户标签值表
CREATE TABLE IF NOT EXISTS t_user_label_value (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    user_id VARCHAR(100) NOT NULL COMMENT '用户ID',
    label_id BIGINT NOT NULL COMMENT '标签ID',
    label_value VARCHAR(500) COMMENT '标签值',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_user_label (user_id, label_id),
    INDEX idx_label_id (label_id),
    INDEX idx_update_time (update_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户标签值表';

-- 插入默认标签分类
INSERT INTO t_label_category (name, code, parent_id, level, sort, description) VALUES
('用户基础属性', 'user_basic', 0, 1, 1, '用户的基础属性信息'),
('用户行为属性', 'user_behavior', 0, 1, 2, '用户的行为数据'),
('用户消费属性', 'user_consume', 0, 1, 3, '用户的消费相关数据'),
('用户偏好属性', 'user_preference', 0, 1, 4, '用户的偏好信息');

-- 插入示例数据源（StarRocks）
INSERT INTO t_datasource (name, type, host, port, database_name, username, password, params, description) VALUES
('StarRocks数据源示例', 1, 'localhost', 9030, 'user_profile', 'root', '', 'useSSL=false&serverTimezone=UTC', 'StarRocks示例数据源');