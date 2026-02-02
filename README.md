A Spring Boot based test result display system with RESTful API

# 测试结果展示系统

一个基于Spring Boot的测试结果展示系统，提供完整的RESTful API接口用于测试数据的管理、查询和统计分析。

## 🚀 功能特性

- **项目管理**：创建、查询、更新、删除测试项目
- **测试用例管理**：管理测试用例，支持分组和标签
- **测试结果记录**：记录测试执行结果，包括状态、时间和错误信息
- **数据模拟**：自动生成模拟测试数据
- **统计分析**：提供丰富的统计和趋势分析功能
- **API文档**：集成Swagger UI，提供完整的API文档

## 📋 技术栈

- **后端框架**：Spring Boot 3.x
- **数据库**：H2 (开发环境) / MySQL 8.0 (生产环境)
- **ORM**：Spring Data JPA
- **API文档**：SpringDoc OpenAPI (Swagger)
- **数据生成**：Java Faker
- **构建工具**：Maven

## 🏗️ 项目结构

```
src/main/java/com/test/result/
├── TestResultDisplayApplication.java    # 主启动类
├── config/
│   └── SwaggerConfig.java               # Swagger配置
├── controller/                           # 控制器层
│   ├── ProjectController.java           # 项目管理API
│   ├── TestCaseController.java          # 用例管理API
│   ├── ResultController.java            # 结果管理API
│   └── MockDataController.java          # 数据模拟API
├── service/                              # 服务层
│   ├── ProjectService.java              # 项目服务接口
│   ├── impl/                            # 服务实现类
│   ├── TestCaseService.java             # 用例服务接口
│   ├── ResultService.java               # 结果服务接口
│   └── MockDataService.java             # 数据模拟服务
├── repository/                           # 数据访问层
│   ├── ProjectRepository.java           # 项目数据访问
│   ├── TestCaseRepository.java          # 用例数据访问
│   └── TestResultRepository.java        # 结果数据访问
├── model/                                # 数据模型层
│   ├── entity/                          # 实体类
│   ├── dto/                             # 数据传输对象
│   └── request/                         # 请求对象
├── exception/                            # 异常处理
│   ├── GlobalExceptionHandler.java      # 全局异常处理器
│   └── custom exceptions...             # 自定义异常
└── util/                                 # 工具类
    └── MockDataGenerator.java           # 数据生成工具
```

## 🚀 快速开始

### 1. 环境要求

- Java 17+
- Maven 3.6+
- 浏览器（用于访问UI）

### 2. 启动应用

```bash
# 克隆项目
git clone <repository-url>
cd test-result-display

# 编译项目
mvn clean compile

# 启动应用
mvn spring-boot:run
```

### 3. 访问系统

应用启动后，可以通过以下地址访问：

- **主页面**：http://localhost:8080
- **API文档**：http://localhost:8080/swagger-ui/index.html
- **H2控制台**：http://localhost:8080/h2-console

### 4. H2数据库配置

访问H2控制台时，使用以下配置：

- **JDBC URL**: `jdbc:h2:mem:testdb`
- **用户名**: `sa`
- **密码**: (空)

## 📊 API接口说明

### 项目管理接口

- `GET /api/v1/projects` - 获取项目列表
- `POST /api/v1/projects` - 创建项目
- `GET /api/v1/projects/{id}` - 获取项目详情
- `PUT /api/v1/projects/{id}` - 更新项目
- `DELETE /api/v1/projects/{id}` - 删除项目

### 测试用例接口

- `GET /api/v1/testcases?projectId={id}` - 获取用例列表
- `POST /api/v1/testcases` - 创建测试用例
- `GET /api/v1/testcases/{id}` - 获取用例详情
- `PUT /api/v1/testcases/{id}` - 更新用例
- `DELETE /api/v1/testcases/{id}` - 删除用例
- `POST /api/v1/testcases/{id}/execute` - 执行测试用例

### 测试结果接口

- `GET /api/v1/results` - 获取测试结果列表
- `GET /api/v1/results/{id}` - 获取结果详情
- `GET /api/v1/results/testcase/{id}` - 获取用例执行历史

### 数据模拟接口

- `POST /api/v1/mock/generate-data` - 生成模拟数据
- `POST /api/v1/mock/execute-random` - 随机执行测试

## 🎯 使用示例

### 1. 生成模拟数据

```bash
curl -X POST "http://localhost:8080/api/v1/mock/generate-data" \
  -H "Content-Type: application/json" \
  -d '{"projectCount": 3, "testCaseCount": 20}'
```

### 2. 获取项目列表

```bash
curl "http://localhost:8080/api/v1/projects?page=0&size=10"
```

### 3. 执行测试用例

```bash
curl -X POST "http://localhost:8080/api/v1/testcases/1/execute" \
  -H "Content-Type: application/json" \
  -d '{"environment": "test-1", "executor": "auto-generator"}'
```

### 4. 获取测试结果

```bash
curl "http://localhost:8080/api/v1/results?page=0&size=10&status=passed"
```

## 📈 系统演示

1. **启动应用**后访问 http://localhost:8080
2. **点击"生成数据"** 按钮生成模拟数据
3. **点击"刷新统计"** 查看系统统计信息
4. **访问Swagger文档** 查看完整的API文档和在线测试

## 🔧 配置说明

### 开发环境配置

配置文件位于 `src/main/resources/application.yml`：

```yaml
spring:
  datasource:
    url: jdbc:h2:mem:testdb  # 内存数据库
  jpa:
    hibernate:
      ddl-auto: update       # 自动创建表结构
  h2:
    console:
      enabled: true          # 启用H2控制台
```

### 生产环境配置

复制 `application-prod.yml` 并配置MySQL数据库连接。

## 🧪 测试

### 单元测试

```bash
mvn test
```

### 集成测试

```bash
mvn verify
```

## 📦 部署

### Docker部署

```bash
# 构建镜像
docker build -t test-result-display .

# 运行容器
docker run -p 8080:8080 test-result-display
```

### 生产部署

1. 修改 `application-prod.yml` 配置
2. 打包应用：`mvn clean package`
3. 运行：`java -jar target/test-result-display-1.0.0.jar`

## 🤝 贡献

欢迎提交Issue和Pull Request！

## 📄 许可证

MIT License

## 🙏 致谢

- Spring Boot
- H2 Database
- Java Faker
- Swagger UI