# 数据源管理 API 文档

## 基础信息

- Base URL: `http://localhost:8080/api/datasource`
- Content-Type: `application/json`

## API 列表

### 1. 创建数据源

**接口地址：** `POST /api/datasource`

**请求示例：**
```json
{
  "name": "StarRocks生产环境",
  "type": 1,
  "host": "192.168.1.100",
  "port": 9030,
  "database": "user_profile",
  "username": "root",
  "password": "password123",
  "params": "useSSL=false&serverTimezone=UTC",
  "description": "生产环境StarRocks数据源"
}
```

**响应示例：**
```json
{
  "code": 200,
  "message": "success",
  "data": 1
}
```

---

### 2. 更新数据源

**接口地址：** `PUT /api/datasource`

**请求示例：**
```json
{
  "id": 1,
  "name": "StarRocks生产环境",
  "type": 1,
  "host": "192.168.1.101",
  "port": 9030,
  "database": "user_profile",
  "username": "root",
  "password": "password123",
  "params": "useSSL=false&serverTimezone=UTC",
  "description": "更新后的生产环境StarRocks数据源"
}
```

**响应示例：**
```json
{
  "code": 200,
  "message": "success",
  "data": true
}
```

---

### 3. 删除数据源

**接口地址：** `DELETE /api/datasource/{id}`

**路径参数：**
- `id`: 数据源ID

**响应示例：**
```json
{
  "code": 200,
  "message": "success",
  "data": true
}
```

---

### 4. 获取数据源详情

**接口地址：** `GET /api/datasource/{id}`

**路径参数：**
- `id`: 数据源ID

**响应示例：**
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 1,
    "name": "StarRocks生产环境",
    "type": 1,
    "host": "192.168.1.100",
    "port": 9030,
    "database": "user_profile",
    "username": "root",
    "password": "password123",
    "params": "useSSL=false&serverTimezone=UTC",
    "status": 1,
    "description": "生产环境StarRocks数据源",
    "createTime": "2024-01-01 10:00:00",
    "updateTime": "2024-01-01 10:00:00"
  }
}
```

---

### 5. 分页查询数据源列表

**接口地址：** `GET /api/datasource/list`

**查询参数：**
- `pageNum`: 页码（默认：1）
- `pageSize`: 每页数量（默认：10）

**响应示例：**
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [
      {
        "id": 1,
        "name": "StarRocks生产环境",
        "type": 1,
        "host": "192.168.1.100",
        "port": 9030,
        "database": "user_profile",
        "status": 1
      }
    ],
    "total": 1,
    "size": 10,
    "current": 1,
    "pages": 1
  }
}
```

---

### 6. 测试数据源连接

**接口地址：** `POST /api/datasource/test`

**请求示例：**
```json
{
  "name": "测试数据源",
  "type": 1,
  "host": "192.168.1.100",
  "port": 9030,
  "database": "user_profile",
  "username": "root",
  "password": "password123",
  "params": "useSSL=false&serverTimezone=UTC"
}
```

**响应示例：**
```json
{
  "code": 200,
  "message": "success",
  "data": true
}
```

---

### 7. 启用数据源

**接口地址：** `POST /api/datasource/{id}/enable`

**路径参数：**
- `id`: 数据源ID

**响应示例：**
```json
{
  "code": 200,
  "message": "success",
  "data": true
}
```

---

### 8. 禁用数据源

**接口地址：** `POST /api/datasource/{id}/disable`

**路径参数：**
- `id`: 数据源ID

**响应示例：**
```json
{
  "code": 200,
  "message": "success",
  "data": true
}
```

---

### 9. 获取数据源的表列表

**接口地址：** `GET /api/datasource/{datasourceId}/tables`

**路径参数：**
- `datasourceId`: 数据源ID

**响应示例：**
```json
{
  "code": 200,
  "message": "success",
  "data": [
    "user_info",
    "user_behavior",
    "user_order"
  ]
}
```

---

### 10. 获取表的字段列表

**接口地址：** `GET /api/datasource/{datasourceId}/tables/{tableName}/columns`

**路径参数：**
- `datasourceId`: 数据源ID
- `tableName`: 表名

**响应示例：**
```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "field": "id",
      "type": "bigint(20)",
      "null": "NO",
      "key": "PRI",
      "default": null,
      "extra": "auto_increment"
    },
    {
      "field": "user_id",
      "type": "varchar(100)",
      "null": "NO",
      "key": "",
      "default": null,
      "extra": ""
    }
  ]
}
```

---

### 11. 预览表数据

**接口地址：** `GET /api/datasource/{datasourceId}/tables/{tableName}/preview`

**路径参数：**
- `datasourceId`: 数据源ID
- `tableName`: 表名

**查询参数：**
- `limit`: 限制行数（默认：100）

**响应示例：**
```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "id": 1,
      "user_id": "U001",
      "name": "张三",
      "age": 25
    },
    {
      "id": 2,
      "user_id": "U002",
      "name": "李四",
      "age": 30
    }
  ]
}
```

---

### 12. 执行SQL查询

**接口地址：** `POST /api/datasource/{datasourceId}/query`

**路径参数：**
- `datasourceId`: 数据源ID

**请求示例：**
```json
{
  "sql": "SELECT user_id, COUNT(*) as order_count FROM user_order GROUP BY user_id LIMIT 10"
}
```

**响应示例：**
```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "user_id": "U001",
      "order_count": 15
    },
    {
      "user_id": "U002",
      "order_count": 8
    }
  ]
}
```

---

## 数据源类型说明

- `1`: StarRocks
- `2`: MySQL
- `3`: ClickHouse

## 数据源状态说明

- `0`: 未启用
- `1`: 已启用

## 错误码说明

- `200`: 成功
- `500`: 服务器内部错误
- `400`: 请求参数错误
- `404`: 资源不存在