# Career Assessment Platform

A Mindler-like career assessment platform with a 5-dimensional psychometric assessment system. Built with Angular frontend, Java/Spring Boot backend, and MySQL database.

## Features

- **5-Dimensional Career Assessment**: Orientation Style, Interest, Personality, Aptitude, Emotional Quotient
- **75 Expert Questions**: 15 questions per category with 4 answer options each
- **Real-time Answer Saving**: Each answer is saved to the database as the user selects it
- **Interactive Charts**: Bar charts, Pie charts, and Radar charts on the results page
- **PDF Report Generation**: Comprehensive career report with charts, scores, and career recommendations
- **JWT Authentication**: Secure user registration and login
- **Career Recommendations**: Top 5 career matches with match percentages, salary ranges, and growth outlook
- **Responsive Design**: Mobile-friendly UI with modern design

## Tech Stack

| Layer     | Technology               |
|-----------|--------------------------|
| Frontend  | Angular 19, TypeScript, SCSS, Chart.js |
| Backend   | Java 17, Spring Boot 3.2.5, Spring Security |
| Database  | MySQL 8.0                |
| PDF       | iText 7, JFreeChart      |
| Auth      | JWT (JSON Web Tokens)    |

## Prerequisites

- **Java 17** or higher
- **Node.js 18+** and npm
- **MySQL 8.0** or higher
- **Maven 3.8+**
- **Angular CLI** (`npm install -g @angular/cli`)

## Setup & Run

### 1. Database Setup

```bash
# Login to MySQL as root
mysql -u root -p

# Run the schema and seed data
source database/schema.sql;
source database/seed_data.sql;
```

This creates:
- Database: `career_assessment_db`
- User: `career_app` with password `career_pass_123`
- 5 assessment categories, 75 questions, 300 answer options

### 2. Backend Setup

```bash
cd backend

# Build and run
mvn clean install -DskipTests
mvn spring-boot:run
```

The backend starts at `http://localhost:8080`.

### 3. Frontend Setup

```bash
cd frontend

# Install dependencies
npm install

# Start development server
ng serve
```

The frontend starts at `http://localhost:4200`.

## API Endpoints

### Authentication
| Method | Endpoint            | Description          |
|--------|---------------------|----------------------|
| POST   | `/api/auth/register`| Register new user    |
| POST   | `/api/auth/login`   | Login user           |
| GET    | `/api/auth/health`  | Health check         |

### Categories & Questions
| Method | Endpoint                          | Description                |
|--------|-----------------------------------|----------------------------|
| GET    | `/api/categories`                 | Get all categories         |
| GET    | `/api/categories/{id}`            | Get category by ID         |
| GET    | `/api/questions/category/{id}`    | Get questions by category  |

### Assessment (Authenticated)
| Method | Endpoint                                    | Description              |
|--------|---------------------------------------------|--------------------------|
| POST   | `/api/assessment/start`                     | Start new session        |
| POST   | `/api/assessment/sessions/{id}/answer`      | Submit answer            |
| POST   | `/api/assessment/sessions/{id}/complete`    | Complete session         |
| GET    | `/api/assessment/sessions/{id}/results`     | Get results              |
| GET    | `/api/assessment/sessions/{id}/report/pdf`  | Download PDF report      |
| GET    | `/api/assessment/sessions`                  | Get user sessions        |

## Project Structure

```
├── database/
│   ├── schema.sql          # Database schema
│   └── seed_data.sql       # Seed data (categories, questions, options)
├── backend/
│   ├── src/main/java/com/career/assessment/
│   │   ├── entity/         # JPA entities
│   │   ├── dto/            # Data Transfer Objects
│   │   ├── repository/     # Spring Data JPA repositories
│   │   ├── service/        # Business logic
│   │   ├── controller/     # REST controllers
│   │   ├── security/       # JWT authentication
│   │   └── config/         # Security & CORS config
│   └── pom.xml
├── frontend/
│   ├── src/app/
│   │   ├── components/     # Angular components
│   │   │   ├── navbar/     # Navigation bar
│   │   │   ├── home/       # Landing page
│   │   │   ├── about/      # About page
│   │   │   ├── programs/   # Programs/Categories page
│   │   │   ├── login/      # Login page
│   │   │   ├── register/   # Registration page
│   │   │   ├── dashboard/  # User dashboard
│   │   │   ├── assessment/ # Assessment start page
│   │   │   ├── test-taking/# Question answering flow
│   │   │   ├── results/    # Results with charts
│   │   │   ├── contact/    # Contact page
│   │   │   └── footer/     # Footer
│   │   ├── services/       # API services
│   │   ├── models/         # TypeScript interfaces
│   │   └── guards/         # Route guards
│   └── package.json
└── README.md
```

## Assessment Flow

1. User registers/logs in
2. Starts a new assessment session
3. Answers questions across 5 categories (15 questions each)
4. Each answer is saved to the database in real-time
5. After completing all categories, the session is marked complete
6. Category scores are calculated with trait analysis
7. Career recommendations are generated using weighted matching
8. Results displayed with interactive charts (Bar, Pie, Radar)
9. PDF report can be downloaded with detailed analysis
