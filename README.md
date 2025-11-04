# 用户画像标签系统

一个基于 Spring Boot 的用户画像标签系统，支持 StarRocks 数据源的标签管理平台。

## 项目结构

```
user-labels/
├── label-core/        # 核心模块，包含公共组件和基础设施
├── label-datasource/  # 数据源管理模块，支持 StarRocks
├── label-compute/     # 标签计算引擎模块
├── label-api/         # API 接口服务模块
└── label-web/         # Web 前端界面模块
```

## 主要功能

1. 标签管理
   - 标签创建、编辑、删除
   - 标签分类管理
   - 标签规则配置
   - 标签版本管理

2. 数据源管理
   - StarRocks 数据源配置
   - 数据源连接测试
   - 数据预览
   - 字段映射

3. 标签计算
   - 实时计算
   - 离线计算
   - 计算任务管理
   - 计算结果预览

4. 系统管理
   - 用户权限管理
   - 系统配置
   - 操作日志

## 技术栈

- 后端：Spring Boot 2.7.0
- 数据库：MySQL + StarRocks
- ORM：MyBatis Plus 3.5.2
- 前端：Vue.js + Element UI

## 快速开始

### 环境要求

- JDK 1.8+
- Maven 3.6+
- MySQL 5.7+
- StarRocks 2.0+

### 安装步骤

1. 克隆项目
```bash
git clone https://github.com/yourusername/user-labels.git
```

2. 配置数据库
- 创建 MySQL 数据库
- 修改配置文件中的数据库连接信息

3. 编译打包
```bash
mvn clean package
```

4. 运行服务
```bash
java -jar label-api/target/label-api.jar
```

## 使用说明

详细的使用说明请参考 [Wiki](wiki-link)

## 贡献指南

欢迎提交 Issue 和 Pull Request

## 开源协议

Apache License 2.0