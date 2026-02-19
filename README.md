# 🎫 Ticketing API - Sistema de Gestión de Tickets

![Java](https://img.shields.io/badge/Java-17-orange?logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.1-brightgreen?logo=spring)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?logo=postgresql)
![Maven](https://img.shields.io/badge/Maven-3.6+-red?logo=apache-maven)

API REST backend completa para un sistema de gestión de tickets (help desk/soporte técnico), diseñada para facilitar la comunicación entre clientes y agentes de soporte. Desarrollado con **Java 17** y **Spring Boot 4.0.1**, incluye autenticación JWT, gestión de usuarios, tickets, comentarios y paginación.

---

## 📋 Tabla de Contenidos

- [Propósito del Proyecto](#-propósito-del-proyecto)
- [Características](#-características)
- [Tecnologías](#-tecnologías)
- [Arquitectura](#-arquitectura)
- [Modelo de Datos](#-modelo-de-datos)
- [Flujos Principales](#-flujos-principales)
- [Requisitos Previos](#-requisitos-previos)
- [Instalación](#-instalación)
- [Configuración](#-configuración)
- [Ejecución](#-ejecución)
- [Endpoints de la API](#-endpoints-de-la-api)
- [Enumeraciones](#-enumeraciones)
- [Migraciones de Base de Datos](#-migraciones-de-base-de-datos)
- [Seguridad y Autenticación](#-seguridad-y-autenticación)
- [Manejo de Errores](#-manejo-de-errores)
- [Contribuir](#-contribuir)

---

## 🎯 Propósito del Proyecto

**Ticketing API** es una solución backend completa para sistemas de gestión de tickets, ideal para implementar help desk, soporte técnico o gestión de incidencias.

### Casos de Uso Principales

1. **Sistema de Help Desk/Soporte Técnico**
   - Los clientes reportan problemas y consultas
   - Los agentes responden y resuelven tickets
   - Seguimiento completo del ciclo de vida del ticket
   - Comunicación bidireccional mediante comentarios

2. **Sistema de Gestión de Incidencias**
   - Priorización de problemas (LOW, MEDIUM, HIGH, URGENT)
   - Asignación automática o manual a agentes
   - Estados configurables del ticket
   - Métricas y seguimiento de resolución

3. **Base para Aplicaciones de Atención al Cliente**
   - Backend completo listo para integrarse con frontend (web/móvil)
   - Sistema de comentarios para historial de conversaciones
   - Autenticación segura con JWT
   - APIs RESTful bien documentadas

---

## ✨ Características

### Funcionalidades Principales

- ✅ **Gestión Completa de Tickets**
  - Crear, leer, actualizar y eliminar tickets
  - Asignación de tickets a agentes
  - Actualización de estados (OPEN, IN_PROGRESS, RESOLVED, CLOSED, REOPENED)
  - Niveles de prioridad (LOW, MEDIUM, HIGH, URGENT)
  - Búsqueda y filtrado avanzado con paginación

- 🔐 **Autenticación y Autorización**
  - Autenticación basada en JWT (JSON Web Tokens)
  - Refresh tokens con almacenamiento en base de datos
  - Control de acceso basado en roles (ADMIN, AGENT, CUSTOMER)
  - Endpoints protegidos con Spring Security

- 💬 **Sistema de Comentarios**
  - Comentarios en tickets para comunicación continua
  - Historial completo de conversaciones
  - Filtrado por autor y paginación
  - Auditoría automática de timestamps

- 👥 **Gestión de Usuarios**
  - CRUD completo de usuarios
  - Sistema de roles y permisos
  - Validación de datos con Bean Validation
  - Búsqueda por email y nombre

- 📊 **Características Técnicas**
  - Paginación en todos los listados
  - Validación de entrada automática
  - Manejo centralizado de excepciones
  - Migraciones de base de datos versionadas con Flyway
  - Conversión case-insensitive para enums
  - Auditoría automática de creación y actualización

---

## 🛠 Tecnologías

| Categoría | Tecnología | Versión | Uso |
|-----------|------------|---------|-----|
| **Lenguaje** | Java | 17 | Lenguaje principal |
| **Framework** | Spring Boot | 4.0.1 | Framework backend |
| **ORM** | Spring Data JPA | 4.0.1 | Persistencia de datos |
| **Base de Datos** | PostgreSQL | 16 | RDBMS |
| **Migraciones** | Flyway | 10.x | Control de versiones de BD |
| **Seguridad** | Spring Security | 6.x | Autenticación/Autorización |
| **JWT** | JJWT | 0.11.5 | Tokens JWT |
| **Validación** | Bean Validation | 3.x | Validación de datos |
| **Build Tool** | Maven | 3.6+ | Gestión de dependencias |
| **Contenedores** | Docker | Latest | Contenedorización |
| **Lombok** | Lombok | Latest | Reducción de boilerplate |

---

## 🏗 Arquitectura

El proyecto sigue una **arquitectura en capas** (Layered Architecture) que separa las responsabilidades:

### Diagrama de Capas Detallado

```
┌─────────────────────────────────────────────┐
│           CLIENTE (Frontend)                │
│   (Web App, Mobile App, API Consumer)      │
└─────────────────┬───────────────────────────┘
                  │ HTTP/REST
                  │ JSON
┌─────────────────▼───────────────────────────┐
│         CONTROLLER LAYER                    │
│  ┌──────────────────────────────────────┐  │
│  │ AuthController     - /api/auth       │  │
│  │ TicketController   - /api/tickets    │  │
│  │ CommentController  - /api/tickets/   │  │
│  │                      {id}/comments   │  │
│  │ UserController     - /api/users      │  │
│  └──────────────────────────────────────┘  │
└─────────────────┬───────────────────────────┘
                  │
┌─────────────────▼───────────────────────────┐
│         SECURITY LAYER                      │
│  ┌──────────────────────────────────────┐  │
│  │ JwtAuthFilter    - Filtro JWT        │  │
│  │ JwtService       - Generación/Valid. │  │
│  │ UserDetailsService - Carga usuarios  │  │
│  │ SecurityConfig   - Configuración     │  │
│  └──────────────────────────────────────┘  │
└─────────────────┬───────────────────────────┘
                  │
┌─────────────────▼───────────────────────────┐
│         SERVICE LAYER                       │
│  ┌──────────────────────────────────────┐  │
│  │ IAuthService                         │  │
│  │ ITicketService                       │  │
│  │ ITicketCommentService                │  │
│  │ IUserService                         │  │
│  │ (+ implementaciones)                 │  │
│  └──────────────────────────────────────┘  │
└─────────────────┬───────────────────────────┘
                  │
┌─────────────────▼───────────────────────────┐
│         REPOSITORY LAYER                    │
│  ┌──────────────────────────────────────┐  │
│  │ ITicketRepository (JPA)              │  │
│  │ IUserRepository (JPA)                │  │
│  │ ITicketCommentRepository (JPA)       │  │
│  │ IRefreshTokenRepository (JPA)        │  │
│  └──────────────────────────────────────┘  │
└─────────────────┬───────────────────────────┘
                  │ JPA/Hibernate
┌─────────────────▼───────────────────────────┐
│         DATABASE LAYER                      │
│            PostgreSQL 16                    │
│  ┌──────────────────────────────────────┐  │
│  │ Tables:                              │  │
│  │  • users                             │  │
│  │  • tickets                           │  │
│  │  • ticket_comments                   │  │
│  │  • refresh_tokens                    │  │
│  └──────────────────────────────────────┘  │
└─────────────────────────────────────────────┘
```

### Estructura de Paquetes

```
com.fran.ticketing_api/
├── config/              # Configuraciones (Security, WebMVC)
│   ├── CaseInsensitiveEnumConvertFactory.java
│   ├── SecurityConfig.java
│   └── WebConfig.java
├── controller/          # Controladores REST
│   ├── AuthController.java
│   ├── TicketController.java
│   ├── CommentController.java
│   └── UserController.java
├── dto/                 # Data Transfer Objects
│   ├── auth/           # DTOs de autenticación
│   │   ├── RegisterRequest.java
│   │   ├── LoginRequest.java
│   │   ├── RefreshRequest.java
│   │   ├── LogoutRequest.java
│   │   ├── AuthResponse.java
│   │   └── MeResponse.java
│   ├── CreateTicketRequest.java
│   ├── UpdateTicketRequest.java
│   ├── TicketResponse.java
│   ├── TicketDetailResponse.java
│   └── ... (más DTOs)
├── entitie/            # Entidades JPA
│   ├── Ticket.java
│   ├── User.java
│   ├── TicketComment.java
│   ├── RefreshToken.java
│   ├── Status.java (enum)
│   ├── Priority.java (enum)
│   └── Role.java (enum)
├── exception/          # Excepciones personalizadas
│   ├── GlobalExceptionHandler.java
│   └── ResourceNotFoundException.java
├── repository/         # Interfaces JPA Repository
│   ├── ITicketRepository.java
│   ├── IUserRepository.java
│   ├── ITicketCommentRepository.java
│   └── IRefreshTokenRepository.java
├── security/           # Componentes de seguridad
│   ├── JwtService.java
│   ├── JwtAuthFilter.java
│   ├── CustomUserDetailsService.java
│   └── SecurityConfig.java
├── service/            # Interfaces de servicio
│   ├── IAuthService.java
│   ├── ITicketService.java
│   ├── ITicketCommentService.java
│   ├── IUserService.java
│   └── impl/          # Implementaciones
│       ├── AuthServiceImpl.java
│       ├── TicketServiceImpl.java
│       ├── TicketCommentServiceImpl.java
│       └── UserServiceImpl.java
├── spec/              # Specifications (filtros dinámicos)
│   ├── TicketSpecifications.java
│   └── CommentSpecifications.java
└── util/              # Utilidades
    └── PageResponseMapper.java
```

---

## 🗂️ Modelo de Datos

### Diagrama de Entidad-Relación (ERD)

```
┌─────────────────────────┐
│         USERS           │
├─────────────────────────┤
│ id (PK)                 │
│ name                    │
│ email (UNIQUE)          │
│ password_hash           │
│ role (ENUM)             │◄────────┐
│ enabled (BOOLEAN)       │         │
│ created_at              │         │
└─────────────────────────┘         │
        ▲                           │
        │                           │
        │ author_id                 │ assigned_to
        │                           │
┌───────┴─────────────┐   ┌─────────┴───────────┐
│  TICKET_COMMENTS    │   │      TICKETS        │
├─────────────────────┤   ├─────────────────────┤
│ id (PK)             │   │ id (PK)             │
│ ticket_id (FK) ─────┼───┤ title               │
│ author_id (FK)      │   │ description         │
│ message             │   │ status (ENUM)       │
│ created_at          │   │ priority (ENUM)     │
│ updated_at          │   │ assigned_to (FK)    │
└─────────────────────┘   │ created_at          │
                          │ updated_at          │
                          └─────────────────────┘

┌───────────────────────┐
│   REFRESH_TOKENS      │
├───────────────────────┤
│ id (PK)               │
│ user_id (FK) ─────────┼──┐
│ token_hash            │  │
│ expires_at            │  │
│ revoked (BOOLEAN)     │  │
│ created_at            │  │
└───────────────────────┘  │
        ▲                  │
        └──────────────────┘
```

### Entidades Principales

#### 1. User (Usuario)
```java
{
  "id": 1,
  "name": "Juan Pérez",
  "email": "juan@example.com",
  "role": "AGENT",              // ADMIN | AGENT | CUSTOMER
  "enabled": true,
  "createdAt": "2026-01-15T09:00:00Z"
}
```

#### 2. Ticket
```java
{
  "id": 1,
  "title": "Error en inicio de sesión",
  "description": "Descripción detallada del problema",
  "status": "OPEN",              // OPEN | IN_PROGRESS | RESOLVED | CLOSED | REOPENED
  "priority": "HIGH",            // LOW | MEDIUM | HIGH | URGENT
  "assignedId": 2,               // ID del agente asignado (nullable)
  "createdAt": "2026-02-18T10:30:00Z",
  "updatedAt": "2026-02-18T15:45:00Z"
}
```

#### 3. TicketComment
```java
{
  "id": 1,
  "ticketId": 5,
  "authorId": 2,
  "comment": "Texto del comentario",
  "createdAt": "2026-02-18T11:20:00Z",
  "updatedAt": "2026-02-18T11:20:00Z"
}
```

#### 4. RefreshToken
```java
{
  "id": 1,
  "userId": 3,
  "tokenHash": "hashed_token_string",
  "expiresAt": "2026-02-25T10:00:00Z",
  "revoked": false,
  "createdAt": "2026-02-18T10:00:00Z"
}
```

---

## 🔄 Flujos Principales

### 1. Flujo de Autenticación Completo

```
┌─────────┐                ┌──────────┐              ┌──────────┐
│ Cliente │                │   API    │              │   BD     │
└────┬────┘                └─────┬────┘              └─────┬────┘
     │                           │                         │
     │─── POST /register ───────>│                         │
     │    {name, email, pass}    │                         │
     │                           │─── Hashear Password ────│
     │                           │─── Save User ──────────>│
     │                           │<──── User ID ───────────│
     │                           │─── Generate JWT ────────│
     │                           │─── Save Refresh Token ─>│
     │<─── Access + Refresh ─────│                         │
     │    {accessToken, refresh} │                         │
     │                           │                         │
     │─── GET /me ──────────────>│                         │
     │    Header: Bearer Token   │                         │
     │                           │─── Validate JWT ────────│
     │                           │─── Extract Email ───────│
     │                           │─── Get User ───────────>│
     │                           │<──── User Data ─────────│
     │<─── User Profile ─────────│                         │
     │    {id, name, email, ...} │                         │
     │                           │                         │
     │─── POST /refresh ────────>│                         │
     │    {refreshToken}         │                         │
     │                           │─── Verify Token ───────>│
     │                           │<──── Token Valid ───────│
     │                           │─── Generate New JWT ────│
     │<─── New Access Token ─────│                         │
     │                           │                         │
```

### 2. Flujo de Gestión de Tickets

```
┌─────────┐                ┌──────────┐              ┌──────────┐
│ Cliente │                │   API    │              │   BD     │
└────┬────┘                └─────┬────┘              └─────┬────┘
     │                           │                         │
     │─── POST /tickets ────────>│                         │
     │    + Bearer Token         │                         │
     │    {title, desc, ...}     │                         │
     │                           │─── Validate JWT ────────│
     │                           │─── Validate Data ───────│
     │                           │─── Create Ticket ──────>│
     │                           │<──── Ticket ID ─────────│
     │<─── 201 Created ──────────│                         │
     │    Location: /tickets/1   │                         │
     │    {ticket data}          │                         │
     │                           │                         │
     │─── GET /tickets?status=   │                         │
     │    OPEN&priority=HIGH     │                         │
     │                           │─── Build Query ────────>│
     │                           │<──── Results ───────────│
     │<─── Paginated Results ────│                         │
     │    {content, meta, links} │                         │
     │                           │                         │
     │─── PATCH /tickets/1/status│                         │
     │    ?status=IN_PROGRESS    │                         │
     │                           │─── Update Status ──────>│
     │                           │─── Update Timestamp ───>│
     │                           │<──── OK ────────────────│
     │<─── Updated Ticket ───────│                         │
     │                           │                         │
```

### 3. Flujo de Comentarios

```
┌─────────┐                ┌──────────┐              ┌──────────┐
│ Cliente │                │   API    │              │   BD     │
└────┬────┘                └─────┬────┘              └─────┬────┘
     │                           │                         │
     │─POST /tickets/1/comments─>│                         │
     │    + Bearer Token         │                         │
     │    {comment, authorId}    │                         │
     │                           │─── Validate JWT ────────│
     │                           │─── Verify Ticket ──────>│
     │                           │<──── Ticket Exists ─────│
     │                           │─── Save Comment ───────>│
     │                           │<──── Comment ID ────────│
     │<─── 201 Created ──────────│                         │
     │    Location: .../comments │                         │
     │    /5                     │                         │
     │                           │                         │
```

---


## 📦 Requisitos Previos

Antes de comenzar, asegúrate de tener instalado:

- **Java 17** o superior ([Download](https://adoptium.net/))
- **Maven 3.6+** ([Download](https://maven.apache.org/download.cgi))
- **PostgreSQL 16** ([Download](https://www.postgresql.org/download/))
- **Docker** y **Docker Compose** (opcional) ([Download](https://www.docker.com/))
- **Git** ([Download](https://git-scm.com/))
- **IDE** recomendado: IntelliJ IDEA, Eclipse o VS Code

---

## 🚀 Instalación

### 1. Clonar el Repositorio

```bash
git clone https://github.com/tu-usuario/ticketing-api.git
cd ticketing-api
```

### 2. Instalar Dependencias

```bash
mvn clean install
```

---

## ⚙ Configuración

### Opción 1: Configuración Manual

#### Crear Base de Datos PostgreSQL

```sql
CREATE DATABASE ticketing_db;
CREATE USER ticketing_user WITH PASSWORD 'ticketing_pass';
GRANT ALL PRIVILEGES ON DATABASE ticketing_db TO ticketing_user;
```

#### Configurar Variables de Entorno

Crea un archivo `.env` en la raíz del proyecto:

```env
# Database Configuration
DB_HOST=localhost
DB_PORT=5433
DB_NAME=ticketing_db
DB_USER=ticketing_user
DB_PASSWORD=ticketing_pass

# JWT Configuration
JWT_SECRET=tu_secreto_super_seguro_de_al_menos_32_caracteres
JWT_ACCESS_EXP_MIN=15
JWT_REFRESH_EXP_DAYS=7

# Application Configuration
API_PORT=8084
SPRING_PROFILES_ACTIVE=dev
```

### Opción 2: Usar Docker Compose (Recomendado)

El proyecto incluye un `docker-compose.yml` que configura automáticamente PostgreSQL y la aplicación.

```bash
# Levantar servicios
docker-compose up -d

# Ver logs
docker-compose logs -f api

# Detener servicios
docker-compose down
```

---

## ▶ Ejecución

### Modo Desarrollo (Local)

```bash
# Con Maven
mvn spring-boot:run

# O compilar y ejecutar
mvn clean package
java -jar target/ticketing-api-0.0.1.jar
```

La API estará disponible en: **http://localhost:8084**

### Modo Producción (Docker)

```bash
docker-compose up -d
```

---

## 📡 Endpoints de la API

### 🔐 Autenticación (`/api/auth`)

| Método | Endpoint | Descripción | Auth |
|--------|----------|-------------|------|
| `POST` | `/api/auth/register` | Registrar nuevo usuario | ❌ |
| `POST` | `/api/auth/login` | Iniciar sesión | ❌ |
| `POST` | `/api/auth/refresh` | Renovar access token | ❌ |
| `POST` | `/api/auth/logout` | Cerrar sesión | ❌ |
| `GET`  | `/api/auth/me` | Obtener perfil del usuario actual | ✅ |

#### Ejemplo: Registro de Usuario

```bash
POST /api/auth/register
Content-Type: application/json

{
  "name": "Juan Pérez",
  "email": "juan@example.com",
  "password": "password123"
}
```

**Respuesta:**
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "a1b2c3d4e5f6...",
  "expiresInSeconds": 900
}
```

---

### 🎫 Tickets (`/api/tickets`)

| Método | Endpoint | Descripción | Auth |
|--------|----------|-------------|------|
| `GET` | `/api/tickets` | Listar tickets (paginado) | ✅ |
| `POST` | `/api/tickets` | Crear ticket | ✅ |
| `GET` | `/api/tickets/{id}` | Obtener ticket por ID | ✅ |
| `PATCH` | `/api/tickets/{id}` | Actualizar ticket | ✅ |
| `DELETE` | `/api/tickets/{id}` | Eliminar ticket | ✅ |
| `PATCH` | `/api/tickets/{id}/status` | Actualizar estado | ✅ |

#### Parámetros de Búsqueda (Query Params)

- `assigneeId` - Filtrar por ID de agente asignado
- `priority` - Filtrar por prioridad (LOW, MEDIUM, HIGH, URGENT)
- `status` - Filtrar por estado (OPEN, IN_PROGRESS, RESOLVED, CLOSED, REOPENED)
- `q` - Búsqueda de texto en título/descripción
- `page` - Número de página (default: 0)
- `size` - Tamaño de página (default: 20)

#### Ejemplo: Crear Ticket

```bash
POST /api/tickets
Authorization: Bearer {token}
Content-Type: application/json

{
  "title": "Error en inicio de sesión",
  "description": "No puedo acceder a mi cuenta",
  "priority": "HIGH",
  "status": "OPEN",
  "assignedTo": 2
}
```

---

### 💬 Comentarios (`/api/tickets/{ticketId}/comments`)

| Método | Endpoint | Descripción | Auth |
|--------|----------|-------------|------|
| `GET` | `/api/tickets/{ticketId}/comments` | Listar comentarios del ticket (paginado) | ✅ |
| `POST` | `/api/tickets/{ticketId}/comments` | Añadir comentario al ticket | ✅ |
| `GET` | `/api/tickets/{ticketId}/comments/{id}` | Obtener comentario por ID | ✅ |
| `PATCH` | `/api/tickets/{ticketId}/comments/{id}` | Actualizar comentario | ✅ |
| `DELETE` | `/api/tickets/{ticketId}/comments/{id}` | Eliminar comentario | ✅ |

#### Ejemplo: Añadir Comentario

```bash
POST /api/tickets/1/comments
Authorization: Bearer {token}
Content-Type: application/json

{
  "comment": "Estamos investigando el problema",
  "authorId": 2
}
```

---

### 👥 Usuarios (`/api/users`)

| Método | Endpoint | Descripción | Auth |
|--------|----------|-------------|------|
| `GET` | `/api/users` | Listar usuarios | ✅ |
| `POST` | `/api/users` | Crear usuario | ✅ |
| `GET` | `/api/users/{id}` | Obtener usuario por ID | ✅ |
| `PATCH` | `/api/users/{id}` | Actualizar usuario | ✅ |
| `DELETE` | `/api/users/{id}` | Eliminar usuario | ✅ |

#### Ejemplo: Crear Usuario

> Nota: al crear un usuario se requiere proporcionar una contraseña; el servidor la almacenará hasheada (BCrypt).

```bash
POST /api/users
Authorization: Bearer {token}
Content-Type: application/json

{
  "name": "María García",
  "email": "maria@example.com",
  "role": "AGENT",
  "password": "MiPasswordSeguro123"
}
```

Validaciones importantes:
- `name`: obligatorio, máximo 100 caracteres
- `email`: obligatorio, formato válido, máximo 150 caracteres
- `role`: obligatorio (ADMIN, AGENT, CUSTOMER)
- `password`: obligatorio, entre 8 y 72 caracteres

---

## 📊 Enumeraciones

El sistema utiliza enumeraciones para garantizar consistencia y type-safety en los valores permitidos.

### Status (Estados de Ticket)

| Estado | Descripción | Uso Típico |
|--------|-------------|------------|
| **OPEN** | Abierto | Ticket recién creado, esperando asignación |
| **IN_PROGRESS** | En Progreso | Agente trabajando activamente en el ticket |
| **RESOLVED** | Resuelto | Problema resuelto, esperando confirmación |
| **CLOSED** | Cerrado | Ticket completamente cerrado |
| **REOPENED** | Reabierto | Ticket previamente cerrado que se ha reabierto |

```java
public enum Status {
    OPEN,
    IN_PROGRESS,
    RESOLVED,
    CLOSED,
    REOPENED
}
```

### Priority (Prioridades)

| Prioridad | Descripción | SLA Sugerido |
|-----------|-------------|--------------|
| **LOW** | Baja | 5-7 días |
| **MEDIUM** | Media | 2-3 días |
| **HIGH** | Alta | 24 horas |
| **URGENT** | Urgente | 4 horas |

```java
public enum Priority {
    LOW,
    MEDIUM,
    HIGH,
    URGENT
}
```

### Role (Roles de Usuario)

| Rol | Descripción | Permisos |
|-----|-------------|----------|
| **ADMIN** | Administrador | Acceso completo al sistema |
| **AGENT** | Agente de soporte | Gestionar tickets asignados, responder |
| **CUSTOMER** | Cliente | Crear tickets, ver propios tickets |

```java
public enum Role {
    ADMIN,
    AGENT,
    CUSTOMER
}
```

---

## 🗄 Migraciones de Base de Datos

El proyecto usa **Flyway** para gestionar migraciones de base de datos de forma versionada.

### Archivos de Migración

```
src/main/resources/db/migration/
├── V1__create_tickets.sql
├── V2__create_ticket_commets.sql
├── V3__create_users.sql
├── V4__add_fk_tickets_assignee.sql
├── V5__update_ticket_commets.sql
├── V6__add_auth_to_users.sql
├── V7__create_table_refresh_tokens.sql
├── V8__update_table_users.sql
```

### Ejecutar Migraciones

Las migraciones se ejecutan automáticamente al iniciar la aplicación. Para ejecutar manualmente:

```bash
mvn flyway:migrate
```

---

## 🔒 Seguridad y Autenticación

### Flujo de Autenticación JWT

1. **Registro/Login** → El usuario recibe `accessToken` y `refreshToken`
2. **Peticiones autenticadas** → Incluir header: `Authorization: Bearer {accessToken}`
3. **Token expirado** → Usar `/auth/refresh` con el `refreshToken`
4. **Logout** → Enviar `refreshToken` a `/auth/logout` para revocarlo

### Configuración de Seguridad

- **Access Token:** Expira en 15 minutos (configurable)
- **Refresh Token:** Expira en 7 días (configurable)
- **Algoritmo:** HMAC SHA-256
- **Contraseñas:** Hash con BCrypt

### Endpoints Públicos

- `POST /api/auth/register`
- `POST /api/auth/login`
- `POST /api/auth/refresh`
- `POST /api/auth/logout`

Todos los demás endpoints requieren autenticación.

---

## ⚠ Manejo de Errores

La API implementa un manejador global de excepciones que devuelve respuestas consistentes:

### Formato de Error

```json
{
  "timestamp": "2026-02-18T16:30:00Z",
  "status": 404,
  "error": "Not Found",
  "message": "Ticket with id 999 not found",
  "path": "/api/tickets/999",
  "violations": []
}
```

### Códigos HTTP

| Código | Descripción |
|--------|-------------|
| `200 OK` | Petición exitosa |
| `201 Created` | Recurso creado exitosamente |
| `204 No Content` | Eliminación exitosa |
| `400 Bad Request` | Datos inválidos |
| `401 Unauthorized` | No autenticado |
| `403 Forbidden` | Sin permisos |
| `404 Not Found` | Recurso no encontrado |
| `409 Conflict` | Conflicto (ej: email duplicado) |
| `500 Internal Server Error` | Error del servidor |

---

## 🤝 Contribuir

Las contribuciones son bienvenidas. Para contribuir al proyecto:

1. Fork el repositorio
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

Para más detalles, consulta [CONTRIBUTING.md](CONTRIBUTING.md).

---

## 📞 Soporte

Si encuentras algún problema o tienes preguntas, por favor abre un [issue](https://github.com/tu-usuario/ticketing-api/issues).

---

## 👨‍💻 Autor

**Fran**

---


**¡Gracias por usar Ticketing API!** ⭐

---

## 🧪 Pruebas y comandos útiles

Estos comandos te ayudan a probar y mantener el proyecto localmente.

### Ejecutar tests

```bash
# Ejecutar todos los tests
mvn test

# Ejecutar un test específico (por ejemplo TicketingApiApplicationTests)
mvn -Dtest=TicketingApiApplicationTests test
```

### Ejecutar migraciones manualmente

```bash
mvn flyway:migrate
```

### Comandos de Maven útiles

```bash
# Compilar sin tests
mvn clean package -DskipTests

# Ejecutar en modo desarrollo
mvn spring-boot:run
```

### Generar JWT_SECRET seguro (PowerShell)

Usa este comando en PowerShell para generar una cadena base64 de 32 bytes adecuada para `JWT_SECRET`:

```powershell
[Convert]::ToBase64String((1..32 | ForEach-Object { Get-Random -Maximum 256 }))
```

Copia el resultado en `JWT_SECRET` (o en tu `.env`) antes de arrancar en entornos de desarrollo/producción.

---

## 📝 Notas finales sobre creación de usuarios

- `POST /api/auth/register` está pensado para que se registren usuarios finales (rol por defecto: CUSTOMER). Es público y no requiere token.

- `POST /api/users` crea usuarios directamente en la base de datos y actualmente **requiere autenticación**. En producción se recomienda restringir su uso únicamente a usuarios con rol `ADMIN` (configurar reglas en `SecurityConfig` o controlarlas desde las capas de servicio).

- Evita exponer la creación de usuarios sin validación ni control de permisos en entornos productivos.

---

## 🔐 Formato del header Authorization

Cuando hagas peticiones a endpoints protegidos debes incluir el header exactamente así:

```
Authorization: Bearer {accessToken}
```

- Debe empezar por la palabra `Bearer` seguida de un espacio y luego el token JWT.
- Si el header no está presente o está mal formado recibirás `401 Unauthorized` o `403 Forbidden`.

---

### 🛠 Problemas comunes y soluciones rápidas

Aquí tienes los problemas que más suelen aparecer y cómo resolverlos rápido:

1) Error 401 / 403 al llamar a endpoints protegidos
- Causa: no se envió el header `Authorization` o el token expiró o es inválido.
- Solución: haz login (`POST /api/auth/login`) o register para obtener `accessToken` y añade el header `Authorization: Bearer {{accessToken}}`.

2) NoResourceFoundException: "No static resource ..." o rutas no encontradas
- Causa: estás llamando una ruta que no existe (por ejemplo `/api/comments` en vez de `/api/tickets/{ticketId}/comments`), o hay un typo en la URL.
- Solución: verifica la ruta exacta en la sección de Endpoints. Para comentarios usa `/api/tickets/{ticketId}/comments`.

3) DataIntegrityViolationException: columna `password_hash` no puede ser nula
- Causa: se intentó crear un usuario sin enviar `password` al endpoint que persiste usuarios (`POST /api/users`) o una migración/seed insertó un usuario sin password.
- Solución: enviar `password` en el body al crear usuarios (entre 8 y 72 caracteres) o revisar scripts SQL para asegurarse de insertar `password_hash` hasheado; evita dejar la columna NULL.

4) 409 Conflict - Email duplicado
- Causa: intentar registrar o crear un usuario con un email que ya existe en la base de datos.
- Solución: usa otro email o implementa lógica para manejar registros duplicados (por ejemplo un endpoint de recuperación o reporte de conflicto).

5) JWT secret demasiado corto / errores de firma
- Causa: `JWT_SECRET` por defecto es muy corto o no cumple la longitud mínima para HMAC SHA-256.
- Solución: genera un `JWT_SECRET` seguro (recomendado: al menos 32 bytes en Base64). Usa el comando PowerShell provisto en la sección "Pruebas y comandos útiles".

6) Problemas con migraciones (Flyway)
- Causa: migraciones conflictivas o cambios manuales en la BD que hacen que Flyway no pueda aplicar scripts.
- Solución: revisa los scripts en `src/main/resources/db/migration/` y, si trabajas en local, puedes limpiar la BD y ejecutar `mvn flyway:migrate` (solo en local y con cuidado).

---

## 📘 Documentación automática (Swagger / OpenAPI)

Se ha integrado **Springdoc OpenAPI** para generar la documentación automática de la API.

- Swagger UI (interfaz web interactiva):
  - URL local: `http://localhost:8084/swagger-ui/index.html`
  - También disponible en: `http://localhost:8084/swagger-ui.html`

- OpenAPI JSON (esquema):
  - URL local: `http://localhost:8084/v3/api-docs`

### Exportar e importar en Postman

1. Abre `http://localhost:8084/v3/api-docs` en el navegador y guarda el JSON (Guardar como `openapi.json`).
2. En Postman: Import -> File -> selecciona `openapi.json`. Postman generará automáticamente la colección con los endpoints.

---
