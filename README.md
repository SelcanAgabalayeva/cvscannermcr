# CVScanner — Automated Bulk CV Parsing and Extraction System

CVScanner is a microservices-based backend system designed for HR teams and recruitment platforms to automatically process and extract information from thousands of CVs in PDF and DOCX formats.

The system supports bulk upload of resume files, batch processing with Spring Batch, text extraction using Apache Tika, and structured candidate data storage in PostgreSQL.

CVScanner simulates a real-world HR automation workflow where recruiters can upload large collections of candidate resumes and retrieve searchable candidate information without manual processing.

---

# Features

## Bulk CV Upload
- Upload `.zip` files containing hundreds or thousands of resumes
- Automatic extraction and temporary file storage

## CV Parsing and Extraction
- PDF and DOCX parsing with Apache Tika
- Extracts:
  - Full Name
  - Technical Skills
  - Years of Experience
  - Preferred Job Type
  - Preferred Location

## Spring Batch Processing
- Chunk-oriented processing
- Job and Step configuration
- Retry and Skip support for corrupted files
- High-volume file processing

## Candidate Management
- Store parsed candidate data in PostgreSQL
- Search and filter candidates
- Retrieve candidate details via REST APIs

## Batch Monitoring
- Track job execution status
- Monitor processed, skipped, and failed files
- API-based monitoring support

## Export Functionality
- Export parsed candidates as:
  - CSV
  - Excel (.xlsx)

## Notification System
- Optional email notification after batch completion

## Swagger/OpenAPI Documentation
- Centralized API documentation through API Gateway

## Dockerized Architecture
- Fully containerized microservices environment using Docker Compose

---

# Microservices Architecture

The project follows a microservices architecture using Spring Cloud components.

## Services

| Service | Description |
|---|---|
| API Gateway | Central entry point for all services |
| Discovery Server | Eureka service registry |
| Auth Service | Authentication and JWT handling |
| Upload Service | Handles CV uploads |
| Batch Service | Spring Batch job processing |
| Parser Service | CV parsing and text extraction |
| Candidate Service | Candidate data management |
| Notification Service | Email notifications |

---

# Technologies Used

- Java 17
- Spring Boot
- Spring Batch
- Spring Cloud Gateway
- Eureka Discovery Server
- Spring Security + JWT
- PostgreSQL
- Apache Tika
- Docker & Docker Compose
- Swagger/OpenAPI
- Maven

---

# System Workflow

1. User uploads a ZIP file containing CVs
2. Upload Service extracts files
3. Batch Service starts Spring Batch Job
4. Parser Service extracts CV content using Apache Tika
5. Candidate information is processed and saved
6. Results become accessible through APIs
7. HR users can export filtered candidate data

---

# API Documentation

Swagger UI is available through the API Gateway:

```bash
http://localhost:8080/swagger-ui/index.html
