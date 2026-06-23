# 🌿 NutriPulse — Fitness Tracking Application

NutriPulse is a personal fitness tracking web application built with Spring Boot and Thymeleaf. It allows users to log their daily nutrition, workouts, and water intake, while automatically calculating personalized calorie targets based on their biometric profile.

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.4.0 |
| Frontend | Thymeleaf + HTML/CSS/JS |
| ORM | Spring Data JPA + Hibernate |
| Database | MySQL |
| Build Tool | Maven |
| Security | Session-based authentication |

---

## ✨ Features

### Authentication & Security
- User registration with hashed passwords (BCrypt)
- Session-based login and logout
- Role-based access control — `USER` and `ADMIN` roles
- Guests can only access register, login, and the landing page
- Admin panel for viewing all users' daily logs

### Daily Log
- One daily log per user per day — automatically created
- Tracks total calories consumed, water intake, and macronutrients

### Meal Tracking
- Log meals with name, type (Breakfast / Lunch / Dinner / Snack), calories, protein, carbs, and fats
- Edit and delete meals
- All macro totals automatically recalculated on every change

### Workout Tracking
- Log workouts with type (Cardio, Strength, HIIT, Yoga, Other), duration, and calories burned
- Edit and delete workouts

### Water Intake
- Add water intake in milliliters throughout the day
- Running total displayed on the dashboard

### Biometric Profile & Calorie Calculator
- Set gender, age, weight, height, activity level, and fitness goal
- Target daily calories automatically calculated using the **Mifflin-St Jeor formula**
- Goal adjustments: Maintain Weight, Lose Weight (−500 kcal), Gain Weight (+300 kcal)
- Target macros (protein 30%, carbs 40%, fats 30%) derived from calorie target

### Progress Visualization
- Energy balance card: Target / Consumed / Burned
- Net calories progress bar
- Macro progress bars (protein, carbs, fats) on the home page log cards

---

## ⚙️ Functionalities

| # | Functionality | Operations |
|---|---|---|
| 1 | **Meal Management** | Create, Read, Update, Delete |
| 2 | **Workout Management** | Create, Read, Update, Delete |
| 3 | **Water Intake Tracking** | Create, Read |
| 4 | **Biometric Profile** | Create, Update |

---

## 🗂️ Project Structure
src/

├── main/

│   ├── java/app/

│   │   ├── config/          # WebMvcConfiguration

│   │   ├── exception/       # Custom exceptions

│   │   ├── mapper/          # Entity ↔ DTO mappers

│   │   ├── models/

│   │   │   ├── dto/         # Request DTOs and response DTOs

│   │   │   └── entity/      # JPA entities

│   │   ├── repository/      # Spring Data JPA repositories

│   │   ├── security/        # SessionInterceptor

│   │   ├── service/         # Business logic

│   │   └── web/             # Controllers

│   └── resources/

│       ├── templates/       # Thymeleaf HTML pages

│       └── static/          # CSS, JS, images

---

## 📄 Pages

| Page | Type | Description |
|---|---|---|
| `/` | Static | Landing page |
| `/login` | Dynamic | Login form |
| `/register` | Dynamic | Registration form |
| `/home` | Dynamic | Daily logs history + user profile card |
| `/daily-log/{id}` | Dynamic | Dashboard — energy balance, meals, workouts, biometrics |
| `/meals/add` | Dynamic | Add / Edit meal form |
| `/workouts/add` | Dynamic | Add / Edit workout form |
| `/admin/logs` | Dynamic | Admin panel — all users' logs |

---

## ⚠️ Note

Due to an unexpected medical situation (hospitalization and ongoing chemotherapy treatment), some planned features could not be fully completed before the submission deadline. These include:

- Full custom exception coverage across all service methods
- "Last 7 Days" filter on the home page log history
- Validation error messages on the biometric profile form within the dashboard
- Minor UI/UX improvements across several pages

All remaining improvements will be pushed after the evaluation period ends (after 3 July 2026).

---

## 🚀 Running the Application

1. Clone the repository
2. Configure your MySQL database in `application.properties`
3. Run with Maven: `mvn spring-boot:run`
4. Open `http://localhost:8080` in your browser
