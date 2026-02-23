# JT808 / JT1078 / GB32960 车联网平台（示例实现）

## 项目结构
- `backend`：Spring Boot + Netty + Disruptor + PostgreSQL + MQTT + SSE
- `frontend`：React + Ant Design + Redux

## 后端能力清单
1. 解析 JT/T 808（含 JT1078 同族报文头）十六进制字符串。
2. 解析 GB/T 32960 十六进制字符串。
3. Netty 协议解析器（按行输入十六进制）。
4. Netty 与 Disruptor 消费线程均使用 Java 虚拟线程。
5. RBAC 模型与登录接口。
6. 提供车联网核心表设计参考（终端、定位、原始报文、RBAC）。
7. Disruptor 内部流转：日志、入库、MQTT 发布、SSE 推送。

## 前端能力清单
1. 登录与权限显示（RBAC）。
2. SSE 实时车辆“地图”展示与车辆列表展示。

## 快速启动
### backend
```bash
cd backend
mvn spring-boot:run
```

### frontend
```bash
cd frontend
npm install
npm run dev
```
