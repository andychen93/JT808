# 部标车联网平台后端

## 技术栈
- JDK 21
- Spring Boot 3
- Netty（支持接收十六进制字符串）
- LMAX Disruptor（内部流转）
- PostgreSQL（建议 18）
- MQTT（可选）
- SSE 实时推送

## 支持能力
1. JT/T 808 与 GB/T 32960 十六进制报文解析。
2. Netty 协议解析器 + 虚拟线程启动。
3. Disruptor 将解析结果分发到：日志、数据库、MQTT、SSE。
4. RBAC（用户/角色/权限）+ 登录接口。

## 启动
```bash
cd backend
mvn spring-boot:run
```
默认账号：admin / Admin@123
