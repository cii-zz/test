# 用户画像标签系统 - 快速启动指南

## 环境准备

### 1. 安装必要软件

#### Java环境
- 下载并安装 JDK 1.8 或更高版本
- 配置 JAVA_HOME 环境变量
- 验证安装：`java -version`

#### Maven
- 下载并安装 Maven 3.6 或更高版本
- 配置 MAVEN_HOME 环境变量
- 验证安装：`mvn -version`

#### Node.js（前端开发）
- 下载并安装 Node.js 14.x 或更高版本（包含npm）
- 验证安装：`node -v` 和 `npm -v`

#### MySQL数据库
- 安装 MySQL 5.7 或更高版本
- 创建数据库并初始化表结构

### 2. 数据库初始化

```bash
# 1. 启动MySQL服务

# 2. 登录MySQL
mysql -u root -p

# 3. 执行初始化脚本
source docs/sql/init.sql

# 或者直接导入
mysql -u root -p < docs/sql/init.sql
```

### 3. 配置数据库连接

编辑 `label-api/src/main/resources/application.yml`，修改数据库连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/user_labels?useSSL=false&serverTimezone=UTC&characterEncoding=utf8&allowPublicKeyRetrieval=true
    username: root      # 修改为你的MySQL用户名
    password: root      # 修改为你的MySQL密码
```

## 启动后端服务

### 方式一：使用Maven命令

```bash
# 1. 在项目根目录下，清理并编译项目
mvn clean install -DskipTests

# 2. 进入label-api目录
cd label-api

# 3. 启动服务
mvn spring-boot:run
```

### 方式二：使用IDEA启动

1. 用IDEA打开项目
2. 找到 `label-api/src/main/java/com/userlabel/api/LabelApiApplication.java`
3. 右键点击 -> Run 'LabelApiApplication'

### 方式三：打包后启动

```bash
# 1. 在项目根目录下打包
mvn clean package -DskipTests

# 2. 运行jar包
java -jar label-api/target/label-api.jar
```

## 启动前端服务

```bash
# 1. 进入前端目录
cd label-web

# 2. 安装依赖（首次运行需要）
npm install

# 3. 启动开发服务器
npm run dev
```

## 访问系统

- **后端API服务**: http://localhost:8080
- **前端页面**: http://localhost:5173（或npm启动时显示的端口）

## 测试接口

启动后可以访问以下接口测试：

- 健康检查: http://localhost:8080/api/health
- 标签分类列表: http://localhost:8080/api/label/category/list
- 数据源列表: http://localhost:8080/api/datasource/list

## 常见问题

### 1. 端口被占用
如果8080端口被占用，可以在 `application.yml` 中修改：
```yaml
server:
  port: 8081  # 修改为其他可用端口
```

### 2. 数据库连接失败
- 检查MySQL服务是否启动
- 检查数据库用户名和密码是否正确
- 检查数据库名称是否正确（默认是 user_labels）

### 3. 编译错误
```bash
# 清理Maven缓存重新编译
mvn clean install -U
```

### 4. 前端启动失败
```bash
# 删除node_modules重新安装
rm -rf node_modules
npm install
```

## 开发说明

### 项目结构
```
user-labels/
├── label-core/        # 核心模块（实体类、公共组件）
├── label-datasource/  # 数据源管理模块
├── label-api/         # API服务（主启动模块）
├── label-web/         # 前端Vue项目
└── docs/              # 文档和配置
```

### 技术栈
- 后端：Spring Boot 2.7.0 + MyBatis Plus + MySQL
- 前端：Vue 3 + Element Plus + TypeScript + Vite
- 数据库：MySQL 5.7+

## 下一步

1. 配置StarRocks数据源（可选）
2. 创建标签分类和标签
3. 配置标签规则
4. 执行标签计算任务

祝你使用愉快！
