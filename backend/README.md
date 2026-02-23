# 部标车联网平台后端

## 技术栈
- JDK 21
- Spring Boot 3
- Netty
- LMAX Disruptor
- PostgreSQL 18
- Flyway（SQL 版本管理）
- MQTT（可选）
- SSE

## 支持能力
1. JT/T 808 与 GB/T 32960 十六进制报文解析。
2. Netty 协议解析器 + 虚拟线程启动。
3. Disruptor 分发到：日志、数据库、MQTT、SSE。
4. RBAC（用户/角色/权限）+ 登录接口。
5. Flyway 管理数据库 schema 与初始化数据。

## 数据库迁移
- `db/migration/V1__init_schema.sql`：核心表 + RBAC 表
- `db/migration/V2__seed_rbac_admin.sql`：管理员与权限种子数据

## 启动
```bash
cd backend
mvn spring-boot:run
```
