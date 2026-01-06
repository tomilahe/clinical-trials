# Clinical Trial Management Service - Home Test Assignment

## Overview
A simple clinical trial application that let's the user add, edit and view clinical trials.

### Backend
- Java 21
- Spring Boot
- Spring Data JPA
- H2 (in-memory database)
- Gradle

### Frontend
- React
- TypeScript
- Vite
---

## Running the Application

### Backend
```bash
cd backend/trial-service
./gradlew bootRun
```
Backend will start at localhost:8080

### Frontend
```bash
cd frontend/trial-frontend
npm install
npm run dev
```
Frontend is reachable via localhost:5137


### Project guidelines
89% salvestusruumist on kasutuses … Kui teie ruum saab otsa, ei saa te Drive’i salvestada, teenusesse Google Photos varundada ega Gmaili kasutada. Hankige uusaasta eripakkumisega aastapaketid üheks aastaks 50% soodsamalt.
# Clinical Trial Management Service - Home Test Assignment

## Overview

Build a simple Clinical Trial management service that allows users to list, create, and update clinical trials. This assignment assesses your ability to create REST APIs using Spring Boot and build a functional frontend interface.

## Requirements

### Backend (Spring Boot)

Create a REST API with the following capabilities:

#### Trial Entity

A Trial has the following attributes:

| Attribute | Type   | Constraints                              |
|-----------|--------|------------------------------------------|
| id        | Long   | Auto-generated, unique identifier        |
| name      | String | Required, 10-100 characters              |
| location  | String | Optional, max 200 characters             |
| status    | Enum   | Required, one of: DRAFT, ONGOING, COMPLETED |

#### API Endpoints

Implement REST endpoints that support the following operations:

- List all trials
- Create a new trial
- Update an existing trial

### Frontend

Build a simple user interface that provides the following functionality:

1. **Trial List View**
   - Display all trials in a list or table format
   - Show name, location, and status for each trial

2. **Add Trial**
   - Form to create a new trial
   - Input fields for name, location (optional), and status (dropdown)
   - Validation according to the entity constraints

3. **Update Trial**
   - Ability to edit an existing trial
   - Pre-populated form with current values
   - Validation according to the entity constraints

## Technical Requirements

- **Backend**: Java with Spring Boot. There's a skeleton of the project provided with minimal setup (see [TrialController](backend/trial-service/src/main/java/ee/menken/trial/controller/TrialController.java)).
- **Database**: In-memory database is fine, but if you have time you can embed a MongoDB or Postgres.
- **Frontend**: Any modern framework/library (React, Angular, Vue, etc.) or plain HTML/CSS/JavaScript
- **Build Tool**: Gradle

## Evaluation Criteria

- **Code Quality**: Clean, readable, and well-structured code
- **API Design**: RESTful principles, proper HTTP methods and status codes
- **Validation**: Input validation on both backend and frontend
- **Error Handling**: Appropriate error responses and user feedback
- **Functionality**: I can view, add and update Clinical Trials!

## Submission

Please provide:
1. Source code (via Git repository or zip file)
2. Instructions on how to run the application
3. Any assumptions or decisions made during development

---

Good luck!


