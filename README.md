# 🛠️ SmartService AI Platform — Backend

> REST API built with Spring Boot for managing services, bookings, and AI-powered review summarization.

---

## 📸 Overview

SmartService Backend is the core API of the SmartService platform. It handles:
- User authentication with JWT
- Service & booking management
- AI-generated review summarization using **Groq API (Llama 3.3 70B)**

---

## 🚀 Tech Stack

| Technology | Usage |
|---|---|
| **Java** | Programming language |
| **Spring Boot 4.x** | Backend framework |
| **Spring Security** | Authentication & authorization |
| **JWT** | Stateless authentication |
| **Spring Data JPA** | Database access |
| **Hibernate** | ORM |
| **Groq API** | AI inference |
| **Llama 3.3 70B** | LLM model for review summarization |
| **Gradle** | Build tool |

---

## ✨ Features

- 🔐 Register & Login with JWT authentication
- 📦 CRUD for services
- 📅 Booking management system
- ⭐ Review & rating system
- 🤖 AI-powered review summarization (Groq + Llama 3.3 70B)
- 🔒 Role-based access control (USER / PROVIDER / ADMIN)

---

## ⚙️ Installation & Setup

# 1. Clone the repository
git clone https://github.com/souhir-fazzaini/SmartService-Backend.git

# 2. Configure application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/smartservice
spring.datasource.username=your_username
spring.datasource.password=your_password
openai.api.key=your_groq_api_key

# 3. Run the project
./gradlew bootRun

API available at: http://localhost:8080

---

## 🔌 Main API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/auth/register` | Register a new user |
| POST | `/api/auth/login` | Login & get JWT token |
| GET | `/api/services` | Get all services |
| POST | `/api/services` | Create a service |
| POST | `/api/bookings` | Book a service |
| GET | `/api/reviews/{serviceId}` | Get reviews |
| GET | `/api/reviews/{serviceId}/summary` | AI review summary |

---

## 🤖 AI Feature

Customer Reviews → Groq API (Llama 3.3 70B) → Summary in French

---

## 🔗 Frontend

👉 https://github.com/souhir-fazzaini/SmartService-AI-Platform-Front

---

## 👩‍💻 Author

**Souhir Fazzaini**
GitHub: @souhir-fazzaini

---

## 📄 License

MIT License
