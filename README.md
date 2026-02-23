# JT808 / JT1078 / GB32960 车联网平台（示例实现）

## 项目结构
- `backend`：Spring Boot + Netty + Disruptor + PostgreSQL + Flyway + MQTT + SSE
- `frontend`：React + Ant Design + Redux
- `init.sql`：根目录数据库初始化 SQL
- `docker-compose.yml`：PostgreSQL 18 一键启动

## 后端能力清单
1. 解析 JT/T 808（含 JT1078 同族报文头）十六进制字符串。
2. 解析 GB/T 32960 十六进制字符串。
3. Netty 协议解析器（按行输入十六进制）。
4. Netty 与 Disruptor 消费线程均使用 Java 虚拟线程。
5. RBAC 模型与登录接口。
6. 使用 Flyway SQL 迁移管理表结构和种子数据（含管理员）。
7. Disruptor 内部流转：日志、入库、MQTT 发布、SSE 推送。

## 快速启动
### 1) 启动 PostgreSQL 18
```bash
docker compose up -d postgres18
```

### 2) 启动 backend（自动执行 Flyway 迁移）
```bash
cd backend
mvn spring-boot:run
```
默认账号：`admin / Admin@123`

### 3) 启动 frontend
```bash
cd frontend
npm install
npm run dev
```

## SQL 说明
- 根目录 `init.sql` 用于手工初始化数据库（如未使用 docker-compose）。
- 版本化 SQL 在 `backend/src/main/resources/db/migration`：
  - `V1__init_schema.sql`：建表
  - `V2__seed_rbac_admin.sql`：RBAC 初始数据
