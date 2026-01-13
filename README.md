# Zenith E-Commerce: Distributed Event-Driven Microservices

A robust, production-ready microservices ecosystem built with **Spring Boot 3**, **Apache Kafka**, and a **Polyglot Persistence** strategy. This project demonstrates high-scale e-commerce operations using the **Choreography-based Saga Pattern** to maintain eventual consistency across distributed services.

## 🏗️ System Architecture & Stack

The system is decomposed into three core bounded contexts:

### 1. Catalog Service

* **Purpose**: Manages the "Source of Truth" for products.
* **Tech**: Spring Data JPA, PostgreSQL.
* **Responsibility**: Product creation, price management, and inventory initialization.
* **Events**: Broadcasts `ProductCreatedEvent` when new items are added.

### 2. Order Service

* **Purpose**: Orchestrates the checkout process.
* **Tech**: Spring Data JPA, PostgreSQL, Kafka Template.
* **Resilience**: Implements **Dead Letter Topics (DLT)** and **Custom Error Handlers** to manage transaction failures.
* **Logic**: Manages order state machine (PENDING -> PLACED/REJECTED).

### 3. Inventory Service

* **Purpose**: Manages stock levels and reservations.
* **Tech**: Spring Data JPA, PostgreSQL.
* **Responsibility**: Listens for orders, validates stock availability, and emits success/failure signals.

---

## 🛠️ Technology Stack

| Layer | Technology |
| --- | --- |
| **Framework** | Spring Boot 3.x, Spring Cloud |
| **Messaging** | Apache Kafka (Event Streaming) |
| **Persistence** | PostgreSQL (Relational) |
| **Communication** | Asynchronous JSON Events |
| **Serialization** | Jackson with Type Headers |
| **DevOps** | Docker, Docker-Compose |
| **Reliability** | Fixed Backoff Retries, Dead Letter Queuing (DLQ) |

---

## 🔄 The Saga Workflow

1. **Creation**: `Catalog Service` creates a product and notifies the system.
2. **Ordering**: `Order Service` persists an order as `PENDING` and emits `OrderPlacedEvent`.
3. **Validation**: `Inventory Service` consumes the event, checks the DB, and emits `InventoryResultEvent`.
4. **Completion**: `Order Service` consumes the result:
* **Success**: Order status updated to `PLACED`.
* **Failure**: Triggers a **Compensating Transaction** to mark the order as `REJECTED`.



---

## 🛡️ Resilience & Fault Tolerance

We implemented advanced error-handling patterns to ensure no order is ever "lost" in the system:

* **Non-Blocking Retries**: If a service is temporarily down (e.g., Database lock), the Kafka listener retries with a `FixedBackOff`.
* **Dead Letter Topic (DLT)**: After exhaustive retries, "poison pill" messages are routed to `.DLT` topics.
* **Manual Intervention/Auto-Rollback**: The DLT listener is configured to automatically trigger compensating transactions to keep the system state consistent.

---

## 🚀 How to Run

### Infrastructure Setup

Ensure Docker is running, then start the backbone:

```bash
docker-compose up -d

```

*This starts Kafka, Zookeeper, and three independent PostgreSQL instances (`catalog_db`, `order_db`, `inventory_db`).*

### Service Execution

Run each service using Maven:

```bash
# In each service directory
mvn spring-boot:run

```

---

## 📈 Future Roadmap

* [ ] **Payment Service**: Integration of stripe/paypal mock for payment Saga.
* [ ] **API Gateway**: Implementing Spring Cloud Gateway for unified entry.
* [ ] **Observability**: Adding Zipkin/Jaeger for distributed tracing of Kafka messages.
* [ ] **Circuit Breaker**: Implementing Resilience4j for synchronous fallback.

---

