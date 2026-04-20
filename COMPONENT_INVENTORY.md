# EIMS Project - Complete File Inventory

## Project Location
`D:\PES\SEM6\PROJS\ooad-project`

## Core Application Files

### Entry Points
- **App.java** - Main entry point for JavaFX application
  - Delegates to EmployeeManagementApp
  - Location: `src/main/java/com/yourname/myapp/`

- **EmployeeManagementApp.java** - Main JavaFX Application
  - Creates primary stage and main layout
  - Manages view switching (Dashboard, Employee List)
  - Handles application lifecycle
  - Location: `src/main/java/com/yourname/myapp/`

## Entity Layer

### Entity Classes (entity/)
- **Employee.java**
  - JPA Entity with table name "employees"
  - Fields: employeeId (PK), employeeName, department, jobRole, employmentStatus, joiningDate
  - Timestamps: createdAt, updatedAt
  - Auto-lifecycle management (@PrePersist, @PreUpdate)

- **EmploymentStatus.java**
  - Enum defining employment states
  - Values: ACTIVE, INACTIVE, ON_LEAVE

## Data Transfer Objects (dto/)

- **EmployeeRequest.java**
  - DTO for create/update operations
  - Fields: employeeName, department, jobRole, employmentStatus
  - Used by service layer for input validation

- **DashboardStats.java**
  - DTO for dashboard statistics
  - Fields: totalEmployeeCount, activeEmployeeCount, onLeaveCount, newJoinersCount
  - Immutable data holder

## Repository Layer (repository/)

- **EmployeeRepository.java** - Interface
  - Defines data access contract
  - Methods: save, findById, findAll, findByDepartment, findByEmploymentStatus, 
    findByDepartmentAndEmploymentStatus, findByEmployeeNameContains, deleteById, 
    existsById, count, countByEmploymentStatus

- **EmployeeRepositoryImpl.java** - Implementation
  - Uses Hibernate Session API
  - Implements all repository methods
  - Exception handling and logging
  - HQL queries for data retrieval

## Service Layer (service/)

- **EmployeeService.java**
  - Business logic orchestration
  - Methods:
    - getAllEmployees() - Get all or filtered employees
    - getEmployeeById(String id) - Get specific employee
    - createEmployee(EmployeeRequest) - Create new (uses Builder)
    - updateEmployee(String id, EmployeeRequest) - Update existing
    - deleteEmployee(String id) - Delete employee
    - getDashboardStats() - Get statistics
    - searchByName(String name) - Name search
    - getAllDepartments() - Get unique departments
  - Exception handling with custom exceptions
  - Logging throughout

## Builder Pattern (builder/)

- **EmployeeBuilder.java**
  - Implements Builder pattern for Employee creation
  - Methods: 
    - withEmployeeName, withDepartment, withJobRole, 
    - withEmploymentStatus, withJoiningDate, build()
  - Auto-generates unique employee IDs (EMP-XXXXXXXX)
  - Validates required fields before building
  - Used by EmployeeService for creating employees

## Exception Handling (exception/)

- **EmployeeNotFoundException.java**
  - RuntimeException
  - Thrown when employee record not found
  - Caught in UI layer and displayed as error dialog

- **DuplicateEmployeeIdException.java**
  - RuntimeException
  - Thrown when attempting duplicate ID creation
  - Caught in UI layer and displayed as error dialog

## Configuration (config/)

- **DatabaseInitializer.java**
  - Handles SQL schema initialization on application startup
  - Checks if database tables exist
  - Automatically executes src/main/resources/schema.sql if tables are missing
  - Creates all database tables with relationships and indexes
  - Logging for debugging and monitoring
  - Methods: initializeDatabase(), tablesExist(), executeSchemaScript()
  - Purpose: Replace Hibernate's auto-schema generation with SQL files

- **HibernateUtil.java**
  - Singleton SessionFactory provider
  - Calls DatabaseInitializer.initializeDatabase() on startup
  - Database configuration:
    - Database: eims_db
    - Driver: MySQL Connector/J
    - Dialect: MySQL8Dialect
  - Entity registration (all JPA entities)
  - Static block initialization
  - Methods: getSession(), closeSessionFactory()
  - Note: Uses hbm2ddl.auto=validate (schema validation only)

## UI Layer (ui/)

### View Classes
- **DashboardView.java**
  - Displays four statistics cards (color-coded)
  - Cards: Total Employees, Active, On Leave, New Joiners
  - Auto-refreshing statistics
  - Location: `src/main/java/com/yourname/myapp/ui/`

- **EmployeeListView.java**
  - TableView of employees with columns
  - Filter bar: Search by name, department, status
  - Buttons: Edit, Delete, Refresh, Clear Filters
  - Callbacks for edit/delete actions
  - Location: `src/main/java/com/yourname/myapp/ui/`

### Form Classes
- **AddEmployeeForm.java**
  - JavaFX Stage for adding new employee
  - Form fields: Name, Department, Job Role, Status
  - Dropdowns for department and role selection
  - Validation before submission
  - Success callback for view refresh
  - Location: `src/main/java/com/yourname/myapp/ui/`

- **UpdateEmployeeForm.java**
  - JavaFX Stage for editing employee
  - Pre-populated form with existing data
  - Read-only employee ID display
  - Same form fields as Add form
  - Update button submits changes
  - Success callback for view refresh
  - Location: `src/main/java/com/yourname/myapp/ui/`

### Utility Classes (ui/util/)
- **DialogUtil.java**
  - Static utility for JavaFX dialogs
  - Methods:
    - showInfo(title, header, content)
    - showError(title, header, content)
    - showWarning(title, header, content)
    - showConfirmation(title, header, content) - Returns boolean
    - showTextInput(title, header, content) - Returns Optional<String>

## Build Configuration

- **pom.xml**
  - Maven project configuration
  - Artifact: eims-desktop
  - Java version: 11
  - Key dependencies:
    - javafx-controls, javafx-fxml, javafx-graphics (21.0.2)
    - hibernate-core (6.2.0.Final)
    - jakarta.persistence-api (3.1.0)
    - mysql-connector-java (8.0.33)
    - lombok (1.18.30)
    - slf4j-api, logback-classic
  - Plugins:
    - maven-compiler-plugin
    - javafx-maven-plugin
    - maven-shade-plugin (for fat JAR)

## Resources

- **logback.xml** - Logging configuration
  - Console appender (DEBUG level)
  - File appender rolling policy (10MB, daily)
  - Logs saved to: logs/eims.log
  - Location: `src/main/resources/`

## Documentation Files

- **README.md**
  - Comprehensive project documentation
  - Features, technology stack, architecture
  - Database setup instructions
  - Build and run guide
  - Usage documentation
  - Troubleshooting guide

- **SETUP_GUIDE.md**
  - Quick setup checklist
  - Database setup commands
  - Build and run commands
  - Common issues and solutions
  - Key file locations

- **COMPONENT_INVENTORY.md** (this file)
  - Complete file listing with descriptions
  - Architecture overview
  - All classes and their purposes

## Class Hierarchy

```
JavaFX Application
├── EmployeeManagementApp (extends Application)
└── UI Screens
    ├── DashboardView
    ├── EmployeeListView
    ├── AddEmployeeForm
    ├── UpdateEmployeeForm
    └── DialogUtil

Business Logic
├── EmployeeService
└── EmployeeBuilder

Data Access
├── EmployeeRepository (interface)
└── EmployeeRepositoryImpl

Database
├── Employee (entity)
├── EmploymentStatus (enum)
└── HibernateUtil (config)

Exception Handling
├── EmployeeNotFoundException
└── DuplicateEmployeeIdException
```

## Package Structure

```
com.yourname.myapp
├── (root)
│   ├── App.java
│   └── EmployeeManagementApp.java
├── builder
│   └── EmployeeBuilder.java
├── config
│   └── HibernateUtil.java
├── dto
│   ├── DashboardStats.java
│   └── EmployeeRequest.java
├── entity
│   ├── Employee.java
│   └── EmploymentStatus.java
├── exception
│   ├── DuplicateEmployeeIdException.java
│   └── EmployeeNotFoundException.java
├── repository
│   ├── EmployeeRepository.java
│   └── EmployeeRepositoryImpl.java
├── service
│   └── EmployeeService.java
└── ui
    ├── AddEmployeeForm.java
    ├── DashboardView.java
    ├── EmployeeListView.java
    ├── UpdateEmployeeForm.java
    └── util
        └── DialogUtil.java
```

## Data Flow

1. **User Action** → UI (JavaFX View/Form)
2. **UI** → Service Layer (EmployeeService)
3. **Service** → Repository (EmployeeRepositoryImpl)
4. **Repository** → Hibernate (HibernateUtil)
5. **Hibernate** → MySQL Database
6. **Response** → Service → UI → Dialog/Update

## Design Patterns Used

1. **Singleton** - HibernateUtil (SessionFactory)
2. **Builder** - EmployeeBuilder for Employee construction
3. **Repository** - EmployeeRepository/EmployeeRepositoryImpl
4. **DAO** - Data Access Object pattern in Repository
5. **MVC** - Model (Entity), View (UI), Controller (Service)
6. **Exception Handler** - Custom exceptions caught at service/UI layer

## Total Classes: 19

- Entry Points: 2
- Entities: 2
- DTOs: 2
- Repository: 2
- Service: 1
- Builder: 1
- Exceptions: 2
- UI Views: 4
- UI Utilities: 1
- Configuration: 1
- Documentation: 3

---

**Last Updated: April 2026**
**Version: 1.0**
