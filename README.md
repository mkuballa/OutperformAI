# 🚀 OutperformAI 🚀

Welcome to the **OutperformAI** – your ultimate companion for managing and visualizing your stock investments with style and ease! This application isn't just about numbers; it's about making your financial journey *funky* and insightful. Track your holdings, watch your portfolio grow (or shrink, but let's stay positive!), and make informed decisions with a sleek, modern interface.

## ✨ Features That Shine ✨

*   **Dynamic Dashboard**: A vibrant overview of your portfolio's performance.
*   **Interactive Charts**: Visualize your gains and losses over time with beautiful, responsive charts.
*   **Holdings at a Glance**: See all your stock holdings in a clear, concise table.
*   **Add/Sell Stocks**: Easily update your portfolio with new purchases or sales.
*   **Dark Mode Ready**: Because your eyes deserve a break, even when tracking fortunes.
*   **Robust Backend**: Powered by Spring Boot for reliable data management.
*   **Persistent Storage**: All your precious investment data is safely stored in PostgreSQL.

## 🛠️ Technologies Under the Hood 🛠️

This project is a full-stack marvel, combining the best of modern web and backend technologies:

### Frontend:
*   **React**: A JavaScript library for building user interfaces.
*   **Tailwind CSS**: A utility-first CSS framework for rapid UI development.
*   **Recharts**: A composable charting library built with React and D3.
*   **Vite**: A blazing fast build tool for modern web projects.

### Backend:
*   **Spring Boot**: The powerful framework for building robust, standalone, production-grade Spring applications.
*   **Java**: The language that powers the backend logic.
*   **Spring Data JPA**: For easy interaction with the database.
*   **PostgreSQL**: A powerful, open-source relational database system.
*   **Flyway**: Database migration tool to keep your schema in sync.

### Infrastructure:
*   **Docker**: Containerization for consistent development and deployment.
*   **Docker Compose**: For orchestrating multi-container Docker applications (backend + database).

## 🚀 Getting Started (Blast Off!) 🚀

To get this stellar application running on your local machine, follow these steps:

### Prerequisites

Make sure you have the following installed:
*   [Docker Desktop](https://www.docker.com/products/docker-desktop)
*   [Node.js & npm](https://nodejs.org/)
*   [Java Development Kit (JDK) 21 or higher](https://www.oracle.com/java/technologies/downloads/)
*   [Maven](https://maven.apache.org/install.html)

### 📦 Backend Setup

1.  **Navigate to the backend directory:**
    ```bash
    cd backend
    ```

2.  **Build the Java application:**
    ```bash
    mvn clean install
    ```
    This will compile the Java code and package it into a JAR file.

### 🐳 Dockerized Database & Backend

We'll use Docker Compose to spin up both the PostgreSQL database and your Spring Boot backend.

1.  **Navigate to the project root directory:**
    ```bash
    cd ./portfolio/
    ```

2.  **Start the Docker containers:**
    ```bash
    docker-compose up --build -d
    ```
    This command will:
    *   Build the `backend` Docker image (using the `Dockerfile` in the `backend` folder).
    *   Pull the `postgres:13-alpine` image (if not already present).
    *   Create and start the `db` container (PostgreSQL).
    *   Create and start the `backend` container, linking it to the `db` container.
    *   Flyway will automatically apply the database migrations (`V1__create_portfolio_schema.sql`) to your PostgreSQL database.

3.  **Verify containers are running:**
    ```bash
    docker ps
    ```
    You should see both `portfolio-backend` and `portfolio-db` containers listed as running.

### 🌐 Frontend Setup

1.  **Navigate to the frontend directory:**
    ```bash
    cd frontend
    ```

2.  **Install dependencies:**
    ```bash
    npm install
    ```

3.  **Start the development server:**
    ```bash
    npm start
    ```
    The frontend application will open in your browser at `http://localhost:3000`.

## 🛑 Stopping the Application

To stop and remove the Docker containers:

1.  **Navigate to the project root directory:**
    ```bash
    cd ./portfolio/
    ```

2.  **Stop and remove containers:**
    ```bash
    docker-compose down
    ```
    If you also want to remove the volumes (which means losing your database data), add the `-v` flag:
    ```bash
    docker-compose down -v
    ```

## 🤝 Contributing

Feel free to fork this repository, open issues, and submit pull requests. Contributions are always welcome!

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
