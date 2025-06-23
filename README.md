**注意：本项目仍在积极开发中，部分功能可能尚未完善或存在变动。**

# cs-ai-agent

## 项目简介

`cs-ai-agent` 是一个基于 Spring Boot 构建的 AI 代理应用，旨在提供智能对话和情感咨询服务。项目集成了 Spring AI 框架，利用阿里云 DashScope 模型进行 AI 对话，并通过 MyBatis-Plus 实现聊天记录的持久化管理。此外，项目还包含了自定义的 Advisor 机制，用于实现日志记录、违禁词过滤和提示词复述等高级功能。

## 技术栈

- **Spring Boot 3.4.4**: 快速构建 Spring 应用程序的框架。
- **Spring AI**: Spring 官方提供的 AI 框架，简化 AI 模型集成。
- **MyBatis-Plus**: MyBatis 的增强工具，简化数据库操作。
- **DashScope**: 阿里云通义千问 AI 模型服务。
- **Lombok**: 简化 Java 代码的工具库。
- **Hutool**: 强大的 Java 工具包。
- **Knife4j**: API 文档生成工具，基于 OpenAPI 3 规范。
- **MySQL**: 关系型数据库，用于存储聊天记忆。

## 模块功能

- **AI 对话**: 基于 DashScope 模型的智能对话功能，支持多轮对话记忆。
- **聊天记忆**: 通过 MyBatis-Plus 将聊天记录持久化到 MySQL 数据库，支持会话消息的添加、获取和清除。
- **自定义 Advisor**: 
    - `MyLoggerAdvisor`: 记录用户输入和 AI 输出日志，方便调试。
    - `ProhibitedWordAdvisor`: 检查用户输入是否包含违禁词，确保内容合规。
    - `ReReadingAdvisor`: 提示词复述，提高大型语言模型的推理能力。
- **结构化输出**: 示例展示了如何通过 AI 生成结构化报告（如“恋爱报告”）。

## 快速开始

### 1. 环境准备

- Java 21
- Maven
- MySQL 数据库

### 2. 数据库配置

创建名为 `cs_ai_agent` 的数据库，并在 `src/main/resources/application.yml` 中配置数据库连接信息：

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/cs_ai_agent?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=UTC
    username: root
    password: 123456
```

### 3. 配置 DashScope API Key
在 src/main/resources/application.yml 中配置您的阿里云 DashScope API Key：

```
spring:
  ai:
    dashscope:
      api-key: ${DASHSCOPE_API_KEY}
      chat:
        model: qwen-plus
```
### 4. 运行项目
```
mvn spring-boot:run
```
项目启动后，您可以通过访问 http://localhost:8102/api/swagger-ui.html 查看 API 文档。

## 项目结构
```
cs-ai-agent/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── cs/
│   │   │           └── csaiagent/
│   │   │               ├── 
CsAiAgentApplication.java  # 应用启动类
│   │   │               ├── 
advisor/                 # 自定义 
Advisor 模块
│   │   │               ├── 
app/                     # 应用核心业务逻
辑 (如 LoveApp)
│   │   │               ├── 
chatmemory/              # 聊天记忆实现
│   │   │               ├── 
mapper/                  # MyBatis-Plus 
Mapper 接口
│   │   │               ├── 
pojo/                    # 实体类
│   │   │               └── 
service/                 # 服务层接口及实
现
│   │   └── resources/
│   │       ├── application.yml      # 
应用配置文件
│   │       └── prohibited-words.txt # 
违禁词列表
├── pom.xml                      # 
Maven 项目配置文件
└── README.md                    # 项目
说明文档
```
## 贡献
欢迎提交 Pull Request 或报告 Issue。

## 许可证
本项目采用 MIT 许可证。