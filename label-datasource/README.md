# label-datasource 数据源管理模块

## 模块介绍

数据源管理模块负责管理用户画像系统中的各类数据源连接，主要支持 StarRocks、MySQL、ClickHouse 等数据库。

## 主要功能

### 1. 数据源管理
- 创建、更新、删除数据源配置
- 启用/禁用数据源
- 测试数据源连接
- 数据源列表查询

### 2. 数据源连接池
- 基于 Apache Commons Pool2 实现的连接池
- 支持连接池配置（最大连接数、最小空闲连接数等）
- 自动管理连接的创建、销毁和复用
- 连接有效性验证

### 3. 元数据管理
- 获取数据源中的表列表
- 查询表的字段信息
- 预览表数据

### 4. SQL 查询
- 执行自定义 SQL 查询
- 查询结果以 JSON 格式返回
- 支持参数化查询

## 核心类说明

### 配置类
- `DatasourcePoolConfig`: 数据源连接池配置类
- `DatasourceConnectionFactory`: 数据源连接工厂，负责创建和管理连接

### 服务类
- `DatasourceManager`: 数据源连接管理器，管理连接池的创建、销毁和连接的获取/归还
- `DatasourceService`: 数据源业务服务接口
- `DatasourceServiceImpl`: 数据源业务服务实现类

### 控制器
- `DatasourceController`: 数据源 RESTful API 控制器

### 持久层
- `DatasourceMapper`: 数据源数据访问接口

## 数据源类型支持

| 类型 | 值 | JDBC URL 格式 |
|------|----|----|
| StarRocks | 1 | jdbc:mysql://host:port/database |
| MySQL | 2 | jdbc:mysql://host:port/database |
| ClickHouse | 3 | jdbc:clickhouse://host:port/database |

## 使用示例

### 创建 StarRocks 数据源

```java
Datasource datasource = new Datasource()
    .setName("StarRocks生产环境")
    .setType(1)
    .setHost("192.168.1.100")
    .setPort(9030)
    .setDatabase("user_profile")
    .setUsername("root")
    .setPassword("password")
    .setParams("useSSL=false&serverTimezone=UTC")
    .setDescription("生产环境数据源");

Long datasourceId = datasourceService.createDatasource(datasource);
```

### 启用数据源

```java
datasourceService.enableDatasource(datasourceId);
```

### 执行查询

```java
String sql = "SELECT user_id, age, city FROM user_info WHERE age > 18 LIMIT 100";
List<Map<String, Object>> result = datasourceService.executeQuery(datasourceId, sql);
```

### 获取表列表

```java
List<String> tables = datasourceService.getTables(datasourceId);
```

### 获取表字段

```java
List<Map<String, Object>> columns = datasourceService.getTableColumns(datasourceId, "user_info");
```

## 连接池配置

可在 `application.yml` 中配置连接池参数：

```yaml
datasource:
  pool:
    max-total: 20        # 最大连接数
    max-idle: 10         # 最大空闲连接数
    min-idle: 5          # 最小空闲连接数
    max-wait-millis: 3000  # 获取连接超时时间（毫秒）
```

## API 接口

详细的 API 文档请参考：[数据源 API 文档](../docs/api/datasource-api.md)

## 注意事项

1. **密码安全**：生产环境建议对密码进行加密存储
2. **连接池配置**：根据实际业务场景调整连接池参数
3. **SQL 注入防护**：执行自定义 SQL 时需要做好 SQL 注入防护
4. **权限控制**：建议为数据源配置只读账号，避免误操作
5. **连接测试**：创建数据源时会自动测试连接，确保配置正确

## 未来扩展

- [ ] 支持更多数据源类型（PostgreSQL、Hive、Doris 等）
- [ ] 数据源密码加密存储
- [ ] 数据源连接监控和告警
- [ ] SQL 执行权限控制
- [ ] 查询结果缓存
- [ ] 慢查询日志记录