# 环境检查和安装指南

## 当前系统环境状态

经过检查，当前系统缺少以下运行环境：

- ❌ Java (JDK 1.8+)
- ❌ Maven (3.6+)
- ❌ Node.js (14.x+)
- ❌ MySQL (5.7+)

## 安装步骤（Windows系统）

### 1. 安装 JDK 1.8

1. 访问 Oracle 官网下载 JDK 1.8：
   https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html
   
2. 或者下载 OpenJDK：
   https://adoptium.net/
   
3. 安装后配置环境变量：
   - 新建 `JAVA_HOME` = `C:\Program Files\Java\jdk1.8.0_xxx`
   - 在 `Path` 中添加 `%JAVA_HOME%\bin`
   
4. 验证安装：
   ```cmd
   java -version
   javac -version
   ```

### 2. 安装 Maven

1. 下载 Maven：
   https://maven.apache.org/download.cgi
   
2. 解压到目录，如：`C:\Program Files\Apache\maven-3.8.6`

3. 配置环境变量：
   - 新建 `MAVEN_HOME` = `C:\Program Files\Apache\maven-3.8.6`
   - 在 `Path` 中添加 `%MAVEN_HOME%\bin`
   
4. 验证安装：
   ```cmd
   mvn -version
   ```

### 3. 安装 Node.js

1. 下载 Node.js LTS 版本：
   https://nodejs.org/
   
2. 运行安装程序，按默认选项安装即可（会自动配置环境变量）

3. 验证安装：
   ```cmd
   node -v
   npm -v
   ```

### 4. 安装 MySQL

1. 下载 MySQL Community Server：
   https://dev.mysql.com/downloads/mysql/
   
2. 运行安装程序，设置root密码

3. 启动 MySQL 服务

4. 验证安装：
   ```cmd
   mysql -u root -p
   ```

## 快速安装（推荐使用包管理器）

### 使用 Chocolatey（Windows包管理器）

如果你安装了 Chocolatey，可以快速安装所有依赖：

```cmd
# 安装 Chocolatey（以管理员身份运行PowerShell）
Set-ExecutionPolicy Bypass -Scope Process -Force; [System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072; iex ((New-Object System.Net.WebClient).DownloadString('https://community.chocolatey.org/install.ps1'))

# 安装所有依赖
choco install openjdk8 -y
choco install maven -y
choco install nodejs-lts -y
choco install mysql -y
```

## 安装完成后

1. 重新打开命令行窗口（让环境变量生效）

2. 验证所有环境：
   ```cmd
   java -version
   mvn -version
   node -v
   npm -v
   mysql --version
   ```

3. 按照 `QUICKSTART.md` 文档启动项目

## 如果已经安装IDEA

如果你使用 IntelliJ IDEA：

1. IDEA 已经集成了 Maven，可以直接使用 IDEA 内置的 Maven
2. 打开项目后，IDEA 会自动下载依赖
3. 直接运行 `LabelApiApplication.java` 即可启动后端

## 临时测试方案

如果暂时不想安装环境，可以：

1. 使用在线IDE（如 Gitpod、GitHub Codespaces）
2. 使用 Docker 容器运行（需要安装 Docker Desktop）
3. 查看项目代码结构和文档
