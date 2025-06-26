# 📷 Photo Storage Backend

A secure cloud-based photo storage backend built with **Spring Boot** and **AWS S3**. The main goal of this project was to build a system to securely store personal photos in the cloud and access them anytime. It also served as a hands-on learning project to dive deeper into **Spring Security**, **AWS S3 integration**, and **system design** best practices.

---

## 🔍 Purpose

I created this project because I wanted a secure way to upload and view my photos from the cloud. It was also an opportunity to explore AWS S3 by leveraging a free AWS account, and to deepen my understanding of Spring Boot, especially Spring Security and backend architecture.

---

## 🚀 What I Learned

- Spring Security (Authentication, Authorization, Password Hashing)
- AWS S3 Integration (Photo Upload, Secure Access via Pre-signed URLs)
- Email Verification (Account Activation)
- System Design and API Structuring

---

## 🛠️ Technology Stack

### Backend
- **Spring Boot**
- **PostgreSQL 17**
- **AWS S3**
- **Thymeleaf** (for email templates)
- **Maven** (build tool)

### Frontend (Work in Progress)
- **React**
- **TypeScript**
- **Vite** (build tool)

### Development Tools
- **IntelliJ IDEA**
- **Git & GitHub** (version control)
- **Postman** (API testing)
- **MailDev** (mock email testing)

---

### File Structure

```bash
├── src
│   ├── main
│   │   ├── java
│   │   │   └── org
│   │   │       └── example
│   │   │           └── server
│   │   │               ├── Application.java
│   │   │               ├── audit
│   │   │               │   ├── AuditConfig.java
│   │   │               │   ├── Auditing.java
│   │   │               │   └── SpringSecurityAuditorAware.java
│   │   │               ├── auth
│   │   │               │   └── AuthService.java
│   │   │               ├── aws
│   │   │               │   └── AwsS3Config.java
│   │   │               ├── configuration
│   │   │               │   └── SecurityConfig.java
│   │   │               ├── dto
│   │   │               │   ├── LoginRequest.java
│   │   │               │   ├── LoginResponse.java
│   │   │               │   └── PhotoResponse.java
│   │   │               ├── email
│   │   │               │   ├── EmailSender.java
│   │   │               │   ├── EmailService.java
│   │   │               │   └── EmailTemplate.java
│   │   │               ├── registration
│   │   │               │   ├── EmailValidation.java
│   │   │               │   ├── RegistrationController.java
│   │   │               │   └── RegistrationService.java
│   │   │               ├── token
│   │   │               │   ├── VerificationToken.java
│   │   │               │   ├── VerificationTokenRepository.java
│   │   │               │   └── VerificationTokenService.java
│   │   │               ├── upload
│   │   │               │   ├── Photo.java
│   │   │               │   ├── PhotoController.java
│   │   │               │   ├── PhotoRepository.java
│   │   │               │   └── PhotoService.java
│   │   │               └── user
│   │   │                   ├── User.java
│   │   │                   ├── UserController.java
│   │   │                   ├── UserRepository.java
│   │   │                   ├── UserRole.java
│   │   │                   └── UserService.java
│   │   └── resources
│   │       ├── application.yml
│   │       ├── dbConfig.properties # YOU MUST ADD THIS FILE
│   │       ├── static
│   │       └── templates
│   │           └── email-verification-template.html
```

---

## ✅ Features

- User Registration with Duplicate Checking
- Email Verification with Token Confirmation
- User Login and Password Change
- Password Hashing for Security
- Photo Upload to AWS S3
- Photo Deletion from AWS S3
- View User’s Photo Gallery
- Database Integration with PostgreSQL
- Secure and Unique Pre-signed URLs for Photo Access
- Token generation, verification, and deletion

---

## 🌐 API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/user` | List all users |
| GET | `/api/user/username/{username}` | Get user by username |
| POST | `/api/user/login` | Login user |
| POST | `/api/user/change-password/{username}` | Change user password |
| POST | `/api/user/register` | Register new user |
| GET | `/api/user/register/confirm` | Confirm user via email |
| POST | `/api/photo-service/{username}` | Upload photo for a user |
| DELETE | `/api/photo-service/delete-photo/{username}` | Delete user’s photo |
| GET | `/api/photo-service/gallery/{username}` | View user’s photo gallery |

---

## ☁️ AWS S3 Bucket Design

- Each user has a unique subdirectory in the bucket based on their user ID.
- The S3 bucket is private by default.
- Pre-signed URLs are generated dynamically per request to provide secure, temporary access.
- No image URLs are saved in the database, making the system more secure.

---

## ⚙️ Setup & Run Instructions

### 📥 Clone & Run Locally

### 1. Clone the Repository

```bash
git clone https://github.com/nissubba1/photo-storage.git
cd photo-storage
```

### 2. **Install Prerequisites**
    - PostgreSQL 17
    - Java 17+
    - MailDev
    - Maven

### 3. **Create a PostgreSQL Database**
    - Name: `photo_storage`

### 4. **Create `dbConfig.properties` in `src/main/resources`**
```properties
# Database credentials
datasource.url=jdbc:postgresql://localhost:5432/photo_storage
datasource.username=YOUR_USERNAME
datasource.password=YOUR_PASSWORD

# AWS S3 credentials
aws.accessKey=YOUR_ACCESS_KEY
aws.secretKey=YOUR_SECRET_KEY
aws.region=YOUR_REGION
aws.bucketName=YOUR_BUCKET_NAME
```
### 5. **Run the Application**
```bash
cd server
./mvnw spring-boot:run
```

---

## 🖥️ Frontend Status
The frontend is still under development.
- **✅ Login and Home pages are functional**
- **✅ APIs tested and working with backend**
- **🔨 Upload, gallery, and account management pages in progress**

---

## 🙏 Thank You

Thank you for taking the time to view my project!  
This has been a rewarding learning experience and a practical deep dive into backend development with cloud integration.

If you have any **feedback, suggestions, or improvements**, feel free to open an issue or create a pull request.  
I'm always open to learning and growing as a developer.

By @nissubba1
---
