# NSDMS (MerSETA) - High-Level Codebase Overview

## 🌟 Executive Summary
The **National Skills Development Management System (NSDMS)** is a comprehensive enterprise-grade Java web application designed for **merSETA** (Manufacturing, Engineering and Related Services SETA). It serves as the central orchestration platform for managing the skills development lifecycle, including stakeholder management, learner registrations, grant applications/allocations, and regulatory reporting in South Africa.

---

## 🛠️ Technology Stack
| Layer | Technologies |
|---|---|
| **Language** | Java 1.8 (Maven Project with `war` packaging) |
| **Frontend** | JavaServer Faces (JSF), PrimeFaces 6.2, OmniFaces 1.11 |
| **Business Logic** | EJB-style Service Layer |
| **Persistence** | Hibernate 5.2.12 (ORM), C3P0 connection pooling, MySQL |
| **Reporting** | JasperReports 6.20 for PDF/Document generation |
| **Messaging & Background Tasks** | ActiveMQ (JMS), Quartz Scheduler 2.2.3 |
| **Integration & APIs** | Axis (SOAP), Jersey (REST), Apache POI/JCSV (Data imports/exports) |

---

## 🏗️ Architectural Overview
The system follows a classic **Multi-Tiered Monolithic Architecture**:

### 1. Presentation Layer (`haj.com.ui`)
Built with **JSF** and **PrimeFaces**, providing a rich, stateful web interface. It handles complex data tables, file uploads, document generation triggers (via Lapis JSF Exporter), and interactive dashboards.

### 2. Service Layer (`haj.com.service`)
The core of the system containing dense business logic and rules. This layer orchestrates data between the UI and the persistence layer. 
*   **Key Services**: `WspService` (Workplace Skills Plan flow), `CompanyLearnersService`, `TrainingProviderApplicationService`, and `AssessorModeratorApplicationService` manage high-volume transactional logic for the SETA's operations.

### 3. Data Access / Domain Layer (`haj.com.dao` & `haj.com.entity`)
Powered by **Hibernate**, mapping a massive relational schema (SQL Server / MySQL) to Java POJOs. 
*   **Entities**: It includes over 300 entities (e.g., `Company`, `Users`, `Wsp`, `Learners`, `MandatoryGrant`) covering every aspect of the South African skills development landscape. Many entities use an auditing approach via `Envers` or manual history tables (e.g., `CompanyHistory`, `WspCompanyHistory`).

### 4. Integration & Messaging Layer (`za.co.merseta.nsdms` / `haj.com.dataextract`)
Handles critical external connectivity and asynchronous task execution:
*   **Regulatory Reporting (DHET/SAQA)**: Generating and extracting complex SETMIS and NLRD flat files indicating compliance and learner status.
*   **Government Integration**: SARS data reconciliation for levy income and allocations (`SarsLevyDetailsService`).
*   **Asynchronous Tasks**: Quartz jobs and ActiveMQ for scheduled report generation, bulk processing, and email notifications without blocking the UI.

---

## 📦 Core Business Modules

### 🎓 Learner Management
Manages the entire learner lifecycle from registration (Apprenticeships, Learnerships, ARPL) to certification. Tracks progress, achievements (EISA), unit standards, and summative assessments. 

### 💰 Grant Management (WSP/ATR/DG)
Orchestrates the massive **Workplace Skills Plan (WSP)**, **Annual Training Report (ATR)**, and **Discretionary Grant (DG)** submission process. Handles company linkages, grant applications, DG allocations, and verification visit scheduling.

### 🏢 Stakeholder Management (Companies & Providers)
A centralized database of Companies (Levy paying and non-levy paying), Skills Development Providers (SDPs), Skills Development Facilitators (SDFs), Assessors, and Moderators. Includes banking detail verification workflows and complex accreditation / re-accreditation flows.

### 📊 Compliance & Reporting (QMR / SETMIS / NLRD)
Automated generation of mandatory national reports and complex analytics using JasperReports and Java-based data extractors (QMR reports, SETMIS files, NLRD files), ensuring strict compliance with South African legislative requirements.

---

## 📍 Key Entry Points & Structure Highlights
*   `pom.xml`: Defines all dependencies, plugin versions, and build configurations.
*   `haj.com.entity`: The central source of truth for the Database object model.
*   `haj.com.service`: The main integration point containing business rules.
*   `za.co.merseta.nsdms.*`: Packages for specific modular extensions like JMS messaging, external systems integration, and batch processing.
