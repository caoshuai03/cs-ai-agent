# cs-ai-agent

🤖 欢迎来到 `cs-ai-agent` 项目！

本项目是一个基于 Spring Boot 的智能 AI 代理应用，旨在提供智能对话服务。核心功能包括集成阿里云通义千问 (DashScope) 模型，实现了数据库持久化和多轮对话记忆管理。项目还支持流式调用、自定义 Advisor、RAG 检索增强、结构化输出、工具集成和 MCP 协议等功能。

## 🛠️ 技术栈

- **核心框架**: Spring Boot 3.4.4, Spring AI
- **ORM框架**: MyBatis-Plus
- **AI模型**: 阿里云通义千问 (DashScope)
- **数据库**: MySQL (聊天记忆存储), PostgreSQL + pgvector (向量数据存储)
- **工具库**: Lombok, Hutool, Jsoup, iText, Knife4j
- **RAG支持**: 检索增强生成
- **工具集成**: 文件操作(Hutool)、网络搜索(Google Search API)、网页抓取(Jsoup)、终端命令(ProcessBuilder)、资源下载(Hutool)、PDF生成(iText)
- **MCP 协议**: 可调用高德地图、时间获取、文生图等工具



### ✨ 功能特性

#### 1. 多轮对话记忆管理
通过 Spring AI 的 ChatMemory 实现对话状态持久化，支持会话ID跟踪和历史会话数量控制，确保上下文连贯性。
- 通过设置会话ID实现多轮对话跟踪
- 可配置历史会话数量限制
- MySQL 数据库持久化存储聊天记录

#### 2. 自定义Advisor
- 自定义Advisor包括：
  - 日志记录：记录用户输入和AI输出
  - 重读机制：提示词复述提高模型能力
  - 违禁词校验：确保内容合规性

#### 3. RAG检索增强生成
通过RAG技术解决大模型幻觉问题，提升特定领域知识理解能力。文档处理流程包括：
- 文档拆分：设置最大最小长度和段落混合拆分，设置重叠字符增强片段联系
- 向量存储：使用PostgreSQL pgvector扩展
- 查询重写增强：优化用户查询提高检索效果
  ```plainText
  原始查询: 怎么脱单
  重写后的查询: 单身人士提高脱单成功率的方法有哪些
  ```
- 高效近似最近邻搜索(HNSW索引)：提高大规模向量数据搜索性能

#### 4. 结构化输出
通过 Spring AI 的结构化输出功能，确保AI返回结果符合预设格式。
- 定义Record类作为结构化输出模板
- 使用entity方法进行结构化处理
- 适用于报告生成等场景

#### 5. 工具集成框架
实现了六种核心工具集成:
- 文件操作(Hutool): 文件读写、目录管理等文件系统操作
- 联网搜索: 集成Google Search API实现网络信息获取
- 网页抓取: 使用Jsoup解析网页内容
- 终端命令: 通过ProcessBuilder执行系统命令
- 资源下载: 使用Hutool工具类实现网络资源下载
- PDF生成: 基于iText实现文档生成

#### 6. MCP协议集成
通过标准化协议实现AI对外部服务的调用，包括：
- 高德地图API调用
- 实时时间获取
- minimax文生图/文生视频

## 🚀 快速启动


### 环境准备

在开始之前，请确保您已安装以下环境：

- **Java 21+** 或更高版本
- **Maven 3.8+**
- **MySQL 数据库**
- **PostgreSQL 数据库**: 需安装 `pgvector` 扩展。

### 项目设置

1. **克隆项目仓库**:

   ```bash
   git clone https://github.com/caoshuai03/cs-ai-agent.git
   cd cs-ai-agent
   ```

2. **数据库配置**:

   项目使用了 MySQL 和 PostgreSQL + pgvector 两种数据库。

   - **MySQL 配置 (聊天记忆存储)**:

     更新数据库连接信息：

     ```yaml
     spring:
       datasource:
         mysql:
           driver-class-name: com.mysql.cj.jdbc.Driver
           url: jdbc:mysql://localhost:3306/cs_ai_agent # <-- 连接 URL
           username: root # <-- 用户名
           password: 123456 # <-- 密码
     ```

   - **PostgreSQL + pgvector 配置 (向量存储)**:

     创建一个 PostgreSQL 数据库，并确保已启用 `pgvector` 扩展，更新数据库连接信息：

     ```yaml
     spring:
       datasource:
         postgres:
           driver-class-name: org.postgresql.Driver
           url: jdbc:postgresql://localhost:5432/vector_db?useSSL=false # <-- 替换为 PostgreSQL 连接 URL
           username: postgres # <--  用户名
           password: postgres # <--  密码
     ```   

3. **配置阿里云 DashScope API Key**:

   配置阿里云 DashScope API Key。可以将密钥直接放在配置文件中，或使用环境变量 `${DASHSCOPE_API_KEY}` (推荐方式)：

   ```yaml
   spring:
     ai:
       dashscope:
         api-key: ${DASHSCOPE_API_KEY} # <--  DashScope API Key
         chat:
           model: qwen-plus # <-- 可选，根据需要修改模型名称
   ```

###  运行项目


```bash
mvn clean install spring-boot:run
```

  访问地址：

- **应用程序接口 (端口 8123)**: `http://localhost:8123`
- **API 文档 (Knife4j)**: `http://localhost:8123/swagger-ui.html`


## 🤝 贡献
欢迎提交 Pull Request 或报告 Issue。

## 📄 许可证
本项目采用 Apache 许可证。