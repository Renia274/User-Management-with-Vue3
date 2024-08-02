# Demo Spring Vue3

## Overview

This repository contains a full stack user management application that implements basic CRUD operations. The application consists of three main pages:

- **Homepage**
- **Register(Register New User)**
- **Users(Display Users)**

## Pages Description

### Homepage

The homepage contains a menu with the following options:

- **Registerr**: Navigates to the page where a new user can be registered.
- **Users**: Navigates to the page where all users are displayed.

### Register New User

This page contains a form to register a new user. The form includes the following fields:

- **Name** (Textfield - Mandatory)
- **Surname** (Textfield - Mandatory)
- **Gender** (Dropdown list with options M/F - Mandatory)
- **Birthdate** (Date – Mandatory. Uses a JS datepicker)
- **Work Address** (Textarea - Non mandatory)
- **Home Address** (Textarea - Non mandatory)

### Display Users

This page displays a list of users and provides options to view details or delete users. It also includes pagination to navigate through the list of users.

### Error Page

The application includes a global error page that shows a user-friendly message when errors occur, like invalid routes or API failures, and offers a button to return to the homepage.

## Prerequisites

- **Java Development Kit (JDK)** 8 or later
- **Maven**
- **MySQL**
- **IntelliJ IDEA**

### Technologies Used

**Backend:**

- Java
- Spring Framework (including Spring Boot, Spring MVC)
- Hibernate (ORM - Object-Relational Mapping)
- JDBC
- JPA (Java Persistence API)
- Tomcat
- Jakarta EE
- MySQL

**Frontend:**

- Vue.js 3
- Vite

## Getting Started

### Setup Instructions

1. **Clone the repository:**

        git clone https://github.com/yourusername/demo-spring-vue.git
        cd demo-spring-vue
      

3. **Backend Setup:**

    - Update `application.properties` with your MySQL configuration:
        
          spring.datasource.url=jdbc:mysql://localhost:3306/yourdatabase
          spring.datasource.username=yourusername
          spring.datasource.password=yourpassword
          spring.jpa.hibernate.ddl-auto=update
          spring.jpa.show-sql=true
          

    - Build and run the Spring Boot application:
         
          ./mvnw clean install
          ./mvnw spring-boot:run
          

4. **Frontend Setup:**

    - Navigate to the frontend directory and install dependencies:
    
          cd src/main/frontend/vite-project
          yarn install
          # or if you prefer npm
          npm install
   

    - Run the frontend development server:
 
          yarn dev
          # or if you prefer npm
          npm run dev
    

## Endpoints

- **Register User:** `POST /api/v/register`
- **Get All Users (with pagination):** `GET /api/v/users?page={page}&size={size}`
- **Get User By ID:** `GET /api/v/users/{id}`
- **Delete User:** `DELETE /api/v/users/{id}`
- **Trigger Error:** `GET /api/v/error - This endpoint is used to simulate server errors`

### Test with Postman

- **Register User:**
    - **URL:** `POST http://localhost:5173/api/v/register`
    - **Body (JSON):**
      
          {
            "name": "John",
            "surname": "Doe",
            "gender": "M",
            "birthdate": "01/01/1970",
            "address": {
              "workAddress": "123 Work St",
              "homeAddress": "456 Home St"
            }
          }
          

- **Get All Users (with pagination):**
    - **URL:** `GET http://localhost:5173/api/v/users?page=0&size=10`

- **Get User By ID:**
    - **URL:** `GET http://localhost:5173/api/v/users/{id}`

- **Delete User:**
    - **URL:** `DELETE http://localhost:5173/api/v/users/{id}`
 
- **Trigger Error:**
    - **URL:** `GET http://localhost:5173/api/v/users/{id}`      

## Project Structure

- **Backend:** Contains Spring Boot application, controllers, service, and repositories.
- **Frontend:** Contains the frontend code built with Vite.

## Built With

- Spring Boot
- Vue.js
- MySQL
- Maven
- Yarn / NPM


