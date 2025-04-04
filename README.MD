# Laptop Shop System
A traditional stateful web application using **Java Spring MVC** to manage an online laptop store.


## Project Description
**Laptop Shop System** is built with **Spring MVC,**, providing a server-side rendered (SSR) e-commerce platform for laptop products. Unlike a stateless, API-focused approach, this project adopts a stateful model, where user sessions (shopping carts, login status) are maintained by the server.

Key project objectives:
- End-to-end management of laptop products, including listings, details, pricing, and inventory.
- **User session** handling (via Spring Session) for maintaining shopping carts, orders, and authentication states.
- Integration of **Spring Security** for login, registration, and role-based permissions (ADMIN vs. USER).
- An admin panel to manage products, orders, and user data through Spring MVC and JSP views.
- File upload for product images (multipart forms), including file validation and server-side storage.

## Main Project Modules
The project is divided into multiple modules or features for easier management and maintenance:

1. Authentication & Authorization (Auth)
    - Custom login, registration forms, password hashing.
    - Session-based security using Spring Security and role-based access (e.g., admin vs. user).

2. User Module
    - CRUD operations for users with validation (Hibernate Validator).
    - Session persistence for logged-in state, shopping cart, order history.

3. Product Module
    - Manage laptop details (name, price, short/long descriptions, quantity, image).
    - Server-side rendering with JSP forms and bootstrap-based UI.
    - Sorting, pagination, and search filters (e.g., brand, price range).

4. Shopping Cart & Checkout Module
    - Cart flow: add items, update quantity, remove items, manage session state.
    - Checkout form capturing shipping information and payment placeholders.
    - Order creation (linking user, items, and final price).

5. Order Module
    - Track orders, display order details to both admin and users.
    - Admin can view, manage (confirm, ship) orders in a dedicated panel.

6. File Upload/Download
    - Upload product images (multipart) to a dedicated folder on the server.
    - Securely handle files with size/type constraints.

9. Common Utilities
    - Validation: (Bean Validation / Hibernate Validator) for form inputs.
    - Exception Handling using Spring’s @ControllerAdvice.
    - Spring Session ensuring user sessions persist across server restarts (optional configuration).
    - Maven for build management, library versions, and packaging.

## Project Prerequisites
Before running the project, ensure the following installations:

- Java (version 17+).

- MySQL (version 8.x, with MySQL Workbench).

- Git (to clone source code, manage versions).

- IDE (Visual Studio Code, IntelliJ, Eclipse, or STS).

- Maven for project builds

- Node.js (version 16.20.0 - if you want to run and test the frontend directly).

## Technical Stacks
 
- Backend:

    - Spring Boot (to bootstrap the project)

    - Spring MVC for server-side rendering with JSP

    - Spring Security for authentication and role-based authorization

    - Spring Data JPA (Hibernate) for ORM

    - Spring Session (if configured) for stateful session management

- Frontend (SSR):

    - JSP pages, JSTL, and Bootstrap for dynamic, styled UI

    - jQuery for form validations and minor AJAX flows (if needed)

- Database:

    - MySQL for relational data storage

- Build Tool:

    - Maven for dependency management and builds

## Personal Contact

**LinkedIn**: https://www.linkedin.com/in/tantruongg23/