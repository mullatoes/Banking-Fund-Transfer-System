
# 💳 Banking Fund Transfer System

![Java](https://img.shields.io/badge/Java-24+-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2+-green)
![JPA](https://img.shields.io/badge/JPA-Hibernate-yellow)
![License](https://img.shields.io/badge/License-MIT-lightgrey

A modern and extensible **Banking Fund Transfer System** built with **Spring Boot**, **JPA**, and **Domain-Driven Design (DDD)** principles. It supports secure account management, seamless fund transfers, and safeguards against race conditions in concurrent environments.

---

## 🚀 Features

- ✅ Create, view, and manage bank accounts
- ✅ Transfer funds between accounts with **pessimistic locking**
- ✅ Concurrent transfer safety (no race conditions)
- ✅ RESTful API with clear separation of concerns
- ✅ In-memory H2 database for dev/testing
- ✅ Unit & Integration test coverage

---

## 🧱 Tech Stack

| Layer            | Technology                        |
|------------------|------------------------------------|
| Language         | Java 24+                          |
| Framework        | Spring Boot                       |
| ORM              | Spring Data JPA (Hibernate)       |
| Testing          | JUnit 5, Mockito                  |
| Build Tool       | Maven / Gradle                    |
| Database (Dev)   | H2 (in-memory)                    |
| API              | REST (Spring MVC)                 |

---

## 📦 Project Structure

```

src
├── domain
│   ├── model
│   │   ├── Account.java
│   │   ├── Money.java
│   ├── repository
│   │   └── AccountRepository.java
│
├── application
│   └── TransferService.java
│
├── infrastructure
│   └── persistence
│       └── JpaAccountRepository.java
│
├── web
│   └── AccountController.java
│
└── test
├── unit
└── integration

````

---

## 🧪 Running Tests

To run all tests:

```bash
./mvnw test
# or
./gradlew test
````

---

## 📬 API Endpoints

| Method | Endpoint             | Description                     |
| ------ | -------------------- | ------------------------------- |
| POST   | `/api/accounts`      | Create a new account            |
| GET    | `/api/accounts/{id}` | Get account by ID               |
| POST   | `/api/transfers`     | Transfer funds between accounts |

---

## 🔒 Concurrency & Safety

* Uses `@Lock(LockModeType.PESSIMISTIC_WRITE)` to **prevent race conditions** during simultaneous transfers.
* Ensures **transactional integrity** using `@Transactional` service methods.

---

## 🌍 Sample Transfer Request

```json
POST /api/transfers

{
  "sourceAccountId": 1,
  "destinationAccountId": 2,
  "amount": 150.00,
  "currency": "KES"
}
```

---

## 👨‍💻 Author

**Kevin Kyei**
*Building secure financial systems with clarity and DDD.*

---

## 📝 License

This project is open-source under the [MIT License](LICENSE).

```
