# Project Styleguide

This style guide applies to the entire application:
- **Backend:** Java (Spring Boot, Maven, Hexagonal Architecture, PostgreSQL, Docker)
- **Frontend:** React + TypeScript (Dockerized)

---

## 🧱 Architecture Overview

### Backend (Java, Spring Boot)
- Follow **Hexagonal Architecture (Ports & Adapters)**:
  - The **domain layer** must be independent of frameworks, databases, and infrastructure.
  - Communication with external systems occurs through **Ports (interfaces)** and **Adapters (implementations)**.
- **PostgreSQL** is the primary relational database.
- The **persistence adapter** implements data access logic through Spring Data JPA or JDBC Template.
- No adapter may directly depend on another adapter.
- Only the **application core** contains business logic.
- Configuration values (DB URLs, credentials, etc.) are externalized via environment variables or Docker Compose.

### Frontend (React + TypeScript)
- Recommended structure:  
  `/src/components`, `/src/hooks`, `/src/pages`, `/src/services`, `/src/types`
- Separate UI (presentational) components from logic (container/hooks).
- Use **functional components** and **React Hooks** exclusively.
- Use state management tools sparingly (e.g., React Query or Zustand, avoid global Redux).
---

## 🧑‍💻 Coding Conventions

### Backend (Java)
- **Java Version:** Use the latest LTS (e.g., Java 21+).
- **Build Tool:** Maven with standard directory layout.
- **Base Package:** `de.mk.<project>.<module>`
- **Database:** PostgreSQL 15+ via Spring Data JPA or JDBC Template.
- **Naming conventions:**
  - Packages: `de.mk.<project>.<module>`
  - Port interfaces: `<UseCase>Port`
  - Adapters: `<UseCase><Technology>Adapter`
  - Entities: Singular (`Stock`, `Order`, `Portfolio`)
  - Tables: snake_case (`user_account`, `stock_transaction`)
- **Entity Design:**
  - Use `@Entity` for persistence models; keep them separate from domain models.
  - Map entities via DTOs or mappers (e.g., MapStruct).
  - Use `@Id` with UUIDs (generated in the application).
  - Add `@Version` for optimistic locking.
- **Lombok:** Allowed (`@Getter`, `@Setter`, `@Builder`, `@Value`, etc.).
- **Error Handling:**
  - Avoid generic `throws Exception`; use specific exception types.
  - Domain exceptions should be consistent (checked or runtime).
- **Logging:** Use `Slf4j` (Lombok `@Slf4j`), never `System.out`.
- **Null Handling:** Prefer `Optional` for return values where appropriate.
- **Immutability:** Prefer `record` or `@Value` for value objects.

### Database Migrations
- **Tool:** Use **Flyway** for schema versioning.
- Migration scripts live in:  
  `src/main/resources/db/migration/`
- File naming: `V<version>__<description>.sql`  
  Example: `V1__create_stock_table.sql`
- Each migration must be **idempotent** and **reversible** (if possible).
- Never manually modify the schema in production outside migrations.

### Frontend (React + TypeScript)
- **TypeScript:** Strict mode enabled (`"strict": true` in `tsconfig.json`).
- **Code Style:** ESLint + Prettier, max line length 100.
- **Imports:** Use absolute imports via `@/` alias, ordered by: React → Libraries → Custom.
- **Components:**
  - Functional components only.
  - Explicit prop types (no `any`).
  - Use destructuring (`const { name } = props`).
- **Styling:** MaterialUI (MUI) for a modern design
- **Data Fetching:** Use React Query or a dedicated API service.
- **Error Handling:** Use Error Boundaries or `try/catch` inside hooks.

---

## 🧪 Testing & Quality

### Backend
- **Frameworks:** JUnit 5, Mockito, Testcontainers (PostgreSQL).
- **Coverage:** ≥ 80% for domain layer.
- **Structure:**
  - Unit tests: `src/test/java/...`
  - Integration tests: `src/integration-test/java/...`
- **Naming:** `XyzServiceTest`, `XyzAdapterIntegrationTest`
- **Guidelines:**
  - Do not mock domain entities.
  - Ports may be mocked.
  - Integration tests must spin up a PostgreSQL Testcontainer and apply Flyway migrations.

### Frontend
- **Frameworks:** React Testing Library.
- **Coverage:** ≥ 80%.
- **Guidelines:**
  - Test behavior, not implementation.
  - Separate unit (hooks, utils) and integration (components) tests.
  - Use `data-testid` only when necessary.

---

## 🧩 Code Quality & Security

### Backend
- **Static Analysis:** Checkstyle & SpotBugs required.
- **SonarQube:** All warnings addressed before merge.
- **Security:**
  - No secrets in source code (use environment variables).
  - Input validation with Bean Validation (`@Valid`, `@NotNull`).
  - Parameterized queries only (no raw SQL).
  - Enforce SSL/TLS for DB connections.
  - Sensitive configs in `.env` or Docker secrets.

### Frontend
- **Prettier:** Mandatory (auto-format).
- **Security:**
  - No `dangerouslySetInnerHTML`.
  - API keys via `.env`.
  - Use secure cookies and headers for CSRF/XSS protection.

---

## 🐳 Docker & Containerization

### Overview
Both backend and frontend must be containerized for consistent local and production environments.

### Backend (Spring Boot)
- **Base Image:** `eclipse-temurin:21-jdk`
- **Dockerfile Example:**
  ```dockerfile
  FROM eclipse-temurin:21-jdk
  WORKDIR /app
  COPY target/*.jar app.jar
  ENV SPRING_PROFILES_ACTIVE=prod
  ENTRYPOINT ["java", "-jar", "app.jar"]
