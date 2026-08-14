# 📝 Postcraft App

> A powerful, production-ready blogging platform REST API — built with Spring Boot, secured with JWT, and backed by PostgreSQL.

---

## 🚀 Live Demo
> API Base URL: `coming soon`

---

## ✨ Features

- 🔐 **JWT Authentication** — Secure register & login with token-based auth
- 📝 **Post Management** — Full CRUD with draft/publish workflow
- 💬 **Comments System** — Add, edit, delete comments on any post
- 🏷️ **Tags** — Organize posts with multiple tags
- 🔍 **Full-text Search** — Search posts by title or content
- 📄 **Pagination & Sorting** — Efficient data loading with page controls
- ⚠️ **Global Exception Handling** — Clean, consistent error responses

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 4.1.0 |
| Security | Spring Security + JWT (jjwt 0.11.5) |
| Database | PostgreSQL 16 |
| ORM | Spring Data JPA + Hibernate |
| Build Tool | Maven |
| API Testing | Postman |

---

## 📁 Project Structure

```
postcraft-app/
├── src/main/java/com/blogging/
│   ├── controller/
│   │   ├── AuthController.java
│   │   ├── PostController.java
│   │   └── CommentController.java
│   ├── service/
│   │   ├── AuthService.java
│   │   ├── PostService.java
│   │   └── CommentService.java
│   ├── repository/
│   │   ├── UserRepository.java
│   │   ├── PostRepository.java
│   │   ├── CommentRepository.java
│   │   └── TagRepository.java
│   ├── entity/
│   │   ├── User.java
│   │   ├── Post.java
│   │   ├── Comment.java
│   │   └── Tag.java
│   ├── dto/
│   │   ├── RegisterRequest.java
│   │   ├── LoginRequest.java
│   │   ├── AuthResponse.java
│   │   ├── PostRequest.java
│   │   ├── PostResponse.java
│   │   ├── CommentRequest.java
│   │   └── CommentResponse.java
│   ├── security/
│   │   ├── JwtUtil.java
│   │   ├── JwtFilter.java
│   │   ├── SecurityConfig.java
│   │   └── UserDetailsServiceImpl.java
│   └── exception/
│       └── GlobalExceptionHandler.java
└── src/main/resources/
    └── application.properties
```

---

## ⚙️ Getting Started

### Prerequisites
- Java 21+
- PostgreSQL 16+
- Maven (or use Eclipse built-in)

### 1️⃣ Clone the Repository
```bash
git clone https://github.com/YOUR_USERNAME/postcraft-app.git
cd postcraft-app
```

### 2️⃣ Create PostgreSQL Database
```sql
CREATE DATABASE blogging_db;
```

### 3️⃣ Configure Application Properties
Open `src/main/resources/application.properties` and update:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/blogging_db
spring.datasource.username=postgres
spring.datasource.password=YOUR_PASSWORD
```

### 4️⃣ Run the Application
```bash
mvn spring-boot:run
```
Or run directly from **Eclipse → Run As → Spring Boot App**

App starts at: `http://localhost:8080`

---

## 📡 API Endpoints

### 🔐 Auth
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/auth/register` | Register new user |
| POST | `/api/auth/login` | Login & get JWT token |

### 📝 Posts
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/posts` | Create a new post |
| GET | `/api/posts` | Get all published posts |
| GET | `/api/posts/{id}` | Get post by ID |
| PUT | `/api/posts/{id}` | Update a post |
| DELETE | `/api/posts/{id}` | Delete a post |
| GET | `/api/posts/search?keyword=` | Search posts |

### 💬 Comments
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/posts/{postId}/comments` | Add a comment |
| GET | `/api/posts/{postId}/comments` | Get all comments |
| PUT | `/api/posts/{postId}/comments/{id}` | Update a comment |
| DELETE | `/api/posts/{postId}/comments/{id}` | Delete a comment |

---

## 🔐 Authentication

All endpoints except `/api/auth/**` require a JWT token.

Add this header to every request:
```
Authorization: Bearer YOUR_JWT_TOKEN
```

### Register Example
```json
POST /api/auth/register
{
  "username": "johndoe",
  "email": "john@example.com",
  "password": "Test@123",
  "fullName": "John Doe",
  "bio": "Java Developer"
}
```

### Login Example
```json
POST /api/auth/login
{
  "username": "johndoe",
  "password": "Test@123"
}
```

---

## 📊 Database Schema

```
users          posts           comments        tags
─────────      ──────────      ────────────    ────────
id             id              id              id
username       title           content         name
email          content         author_id
password       summary         post_id
fullName       status          created_at
bio            author_id       updated_at
created_at     created_at
               updated_at

                    post_tags
                    ─────────
                    post_id
                    tag_id
```

---

## 👨‍💻 Author

**Ashutosh Kumar**
- Backend Developer | Java + Spring Boot
- 📧 your-email@gmail.com
- 🔗 [LinkedIn](https://linkedin.com/in/your-profile)
- 🐙 [GitHub](https://github.com/your-username)

---

## 📄 License
This project is open source and available under the [MIT License](LICENSE).

---

⭐ **If you found this project helpful, give it a star!**
