# Maven 依赖修复说明

## 已修复的问题

### 1. ✅ 移除了不存在的模块引用
- 从父 pom.xml 中移除了 `label-compute` 和 `label-web` 模块（这两个模块不存在）
- 只保留实际存在的三个模块：
  - `label-core`
  - `label-datasource`
  - `label-api`

### 2. ✅ 统一了依赖版本管理
在父 pom.xml 的 `<properties>` 中定义了所有版本号：
- `spring-boot.version`: 2.7.0
- `mybatis-plus.version`: 3.5.2
- `mysql.version`: 8.0.30
- `druid.version`: 1.2.11
- `commons-pool2.version`: 2.11.1
- `java.version`: 1.8

### 3. ✅ 在父 pom 中添加了依赖管理
在 `<dependencyManagement>` 中统一管理所有依赖版本：
- Spring Boot Dependencies
- MyBatis Plus
- MySQL Connector
- Druid 连接池
- Commons Pool2
- Lombok

### 4. ✅ 优化了子模块依赖
- **label-core**: 只保留必要的核心依赖
- **label-datasource**: 依赖 label-core，添加数据源相关依赖
- **label-api**: 依赖其他模块，作为启动入口

### 5. ✅ 修复了版本引用
- 所有子模块使用 `${project.version}` 引用父项目版本
- 所有依赖版本由父 pom 统一管理，子模块不再指定版本号

## 依赖结构说明

```
user-labels (父项目)
├── label-core (核心模块)
│   ├── MyBatis Plus (持久层)
│   ├── Lombok (代码生成)
│   └── Spring Web (provided，用于异常处理)
│
├── label-datasource (数据源模块)
│   ├── label-core (依赖)
│   ├── Spring Boot Web
│   ├── MyBatis Plus
│   ├── MySQL Driver
│   ├── Druid 连接池
│   └── Commons Pool2
│
└── label-api (API服务模块，启动入口)
    ├── label-core (依赖)
    ├── label-datasource (依赖)
    ├── Spring Boot Web
    ├── MyBatis Plus
    ├── MySQL Driver
    └── Druid 连接池
```

## 验证依赖是否修复成功

### 方法一：命令行验证

```bash
# 在项目根目录执行
mvn clean compile

# 查看依赖树
mvn dependency:tree

# 检查是否有依赖冲突
mvn dependency:analyze
```

### 方法二：IDEA 验证

1. 打开项目后，等待 IDEA 自动导入依赖
2. 查看 Maven 工具窗口，检查依赖是否正常加载
3. 检查是否有红色错误提示

## 常见问题解决

### 问题1：依赖下载失败

**解决方案**：配置国内 Maven 镜像

编辑 `~/.m2/settings.xml`（如果不存在则创建），添加阿里云镜像：

```xml
<settings>
  <mirrors>
    <mirror>
      <id>aliyun</id>
      <mirrorOf>central</mirrorOf>
      <name>Aliyun Maven Mirror</name>
      <url>https://maven.aliyun.com/repository/public</url>
    </mirror>
  </mirrors>
</settings>
```

### 问题2：编译报错找不到类

**解决方案**：
```bash
# 清理并重新编译
mvn clean install -U
```

### 问题3：IDEA 中代码有红线但能编译

**解决方案**：
1. File -> Invalidate Caches / Restart
2. 重新导入 Maven 项目

### 问题4：Lombok 不生效

**解决方案**：
1. 安装 Lombok 插件（IDEA）
2. 开启注解处理器：Settings -> Build -> Compiler -> Annotation Processors -> Enable annotation processing

## 下一步操作

依赖修复完成后，可以尝试编译项目：

```bash
# 1. 清理并编译整个项目
mvn clean install -DskipTests

# 2. 如果编译成功，启动应用
cd label-api
mvn spring-boot:run
```

或者在 IDEA 中直接运行 `LabelApiApplication.java`

## 依赖版本说明

当前使用的都是稳定版本：
- Spring Boot 2.7.0 - 2022年发布的LTS版本
- MyBatis Plus 3.5.2 - 支持Java 8
- MySQL 8.0.30 - 支持最新的MySQL 8.x
- Druid 1.2.11 - 阿里巴巴数据库连接池

如果需要升级版本，只需修改父 pom.xml 中的 `<properties>` 即可。
