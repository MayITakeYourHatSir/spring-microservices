# Spring Microservices E-Commerce Backend

一個以 Spring Boot 為核心打造的微服務電商後端專案。

本專案以實際電商系統為情境，將不同的業務功能拆分為獨立服務，包含身份驗證、商品管理、庫存管理、訂單管理與通知服務，並透過 API Gateway 統一對外提供服務，利用 RabbitMQ 實作事件驅動的非同步溝通。

除了完成各個服務的業務邏輯外，也整合了 JWT 身份驗證、Gmail API、Docker 等技術，希望透過實作深入理解微服務架構、服務整合及分散式系統的設計方式。

---

# 專案目的

之前工作主要以單體式系統開發為主，因此希望透過 Side Project 實際建構一套微服務架構，學習大型系統常見的設計方式，而不只是停留在理論。

本專案主要希望實踐以下幾個方向：

- 微服務架構設計
- API Gateway
- JWT 身份驗證
- RabbitMQ 非同步事件
- Docker 容器化部署
- Service-to-Service 通訊
- Gmail API 整合

---

# 系統架構

```text
                    +----------------------+
                    |        Client        |
                    +----------+-----------+
                               |
                               v
                    +----------------------+
                    |     API Gateway      |
                    | Spring Cloud Gateway |
                    +----------+-----------+
                               |
          +--------------------+--------------------+
          |                    |                    |
          v                    v                    v
 +----------------+    +----------------+   +----------------+
 | Auth Service   |    | Product Service|   |Inventory Service|
 +----------------+    +----------------+   +----------------+
          |                                          |
          +--------------------+----------------------+
                               |
                               v
                    +----------------------+
                    |    Order Service     |
                    +----------+-----------+
                               |
                        RabbitMQ Event
                               |
                               v
                    +----------------------+
                    | Notification Service |
                    |     Gmail API        |
                    +----------------------+
```

---

# 技術架構

## 後端框架

- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA

## 微服務

- Spring Cloud Gateway
- RabbitMQ
- RESTful API
- HTTP Interface Client

## 身份驗證

- JWT
- Spring Authorization Server
- OAuth2 Resource Server

## 資料庫

- MySQL
- MongoDB

## 開發工具

- Docker
- Docker Compose
- Maven
- Git

---

# 系統服務

| Service | 說明 |
|----------|------|
| API Gateway | 系統唯一入口，負責請求轉發 |
| Auth Service | 使用者登入、JWT Token 簽發 |
| Product Service | 商品管理（MongoDB） |
| Inventory Service | 庫存管理（MySQL） |
| Order Service | 訂單建立與商業流程 |
| Notification Service | 接收 RabbitMQ 訊息並寄送 Email |

---

# 功能介紹

## Auth Service

- 使用者登入
- JWT Token 發行
- Token 驗證

---

## Product Service

- 商品 CRUD
- 商品查詢
- 分頁查詢

---

## Inventory Service

- 查詢庫存
- 保留庫存
- 扣除庫存

---

## Order Service

- 建立訂單
- 訂單驗證
- Idempotency Key
- 發布 RabbitMQ Event

---

## Notification Service

- 接收 RabbitMQ Event
- Gmail API 寄送通知信
- HTML Email

---

# 訂單流程

```text
Client
    │
    ▼
API Gateway
    │
    ▼
Order Service
    │
    ▼
建立訂單
    │
    ▼
RabbitMQ
    │
    ▼
Notification Service
    │
    ▼
Gmail API
    │
    ▼
寄送 Email
```

---

# 專案結構

```text
spring-microservices
│
├── api-gateway
├── auth-service
├── common-lib
├── inventory-service
├── notification-service
├── order-service
├── product-service
└── docker-compose.yml
```

---

# 專案特色

- 採用微服務架構設計
- JWT 身份驗證
- API Gateway 統一入口
- RabbitMQ 非同步事件
- Gmail API 通知整合
- Docker 容器化部署
- MySQL + MongoDB 混合資料庫設計
- RESTful API 設計

---

# 後續規劃

目前仍持續擴充中，預計加入：

- Redis 快取
- Kubernetes 部署
- Prometheus + Grafana 監控
- OpenTelemetry 分散式追蹤
- CI/CD 自動化部署
- API Rate Limiting
- Circuit Breaker
