# Ticketing API (Java + Spring Boot)

API backend para un sistema de gestión de tickets, desarrollado con Java y Spring Boot. Este proyecto permite crear, actualizar, ver y eliminar tickets, estados , prioridades asignacion a agentes y comentarios.

## Características
- Crear, actualizar, ver y eliminar tickets.
- Gestión de estados y prioridades de los tickets.
- Asignación de tickets a agentes.
- Añadir comentarios a los tickets.

## Requisitos
- Java 11 o superior
- Maven 3.6 o superior
- Spring Boot 2.5 o superior
- Base de datos MySQL o PostgreSQL
- Postman (opcional, para pruebas de API)
- IDE como IntelliJ IDEA o Eclipse
- Git
- Docker (opcional, para contenedorización)
- JDK (Java Development Kit)
- Spring Data JPA
- Spring Web

## Instalación
1. Clona el repositorio:
   ```bash
   git clone
   

2. Navega al directorio del proyecto:
   ```bash
   cd ticketing-api
   ```
3. Configura la base de datos en `src/main/resources/application.properties`:
   ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/ticketing_db
    spring.datasource.username=tu_usuario
    spring.datasource.password=tu_contraseña
    ```
4. Construye el proyecto con Maven:
5. ```bash
   mvn clean install
   ```
6. Ejecuta la aplicación:
   ```bash
   mvn spring-boot:run
   ```
7. La API estará disponible en `http://localhost:8080`.


## Uso
Puedes usar Postman o cualquier cliente HTTP para interactuar con la API. Aquí hay algunos endpoints básicos:
- `GET /api/tickets` - Obtener todos los tickets
- `POST /api/tickets` - Crear un nuevo ticket
- `GET /api/tickets/{id}` - Obtener un ticket por ID
- `PUT /api/tickets/{id}` - Actualizar un ticket por ID
- `DELETE /api/tickets/{id}` - Eliminar un ticket por ID
- `POST /api/tickets/{id}/comments` - Añadir un comentario a un ticket
- `PUT /api/tickets/{id}/assign` - Asignar un ticket a un agente
- `PUT /api/tickets/{id}/status` - Actualizar el estado de un ticket
- `PUT /api/tickets/{id}/priority` - Actualizar la prioridad de un

    ticket
- `GET /api/agents` - Obtener todos los agentes
- `POST /api/agents` - Crear un nuevo agente
- `GET /api/agents/{id}` - Obtener un agente por ID
- `PUT /api/agents/{id}` - Actualizar un agente por ID    
- `DELETE /api/agents/{id}` - Eliminar un agente por ID
- `GET /api/comments` - Obtener todos los comentarios
- `GET /api/comments/{id}` - Obtener un comentario por ID
- `DELETE /api/comments/{id}` - Eliminar un comentario por ID
- `GET /api/statuses` - Obtener todos los estados de tickets
- `POST /api/statuses` - Crear un nuevo estado de ticket
- `GET /api/statuses/{id}` - Obtener un estado de ticket por ID
- `PUT /api/statuses/{id}` - Actualizar un estado de ticket por ID
- `DELETE /api/statuses/{id}` - Eliminar un estado de ticket por ID
- `GET /api/priorities` - Obtener todas las prioridades de tickets
- `POST /api/priorities` - Crear una nueva prioridad de ticket
- `GET /api/priorities/{id}` - Obtener una prioridad de ticket por ID
- `PUT /api/priorities/{id}` - Actualizar una prioridad de ticket por
- ID
- `DELETE /api/priorities/{id}` - Eliminar una prioridad de ticket por
-  ID
- `GET /api/reports/tickets-by-status` - Obtener reporte de tickets por estado
- `GET /api/reports/tickets-by-priority` - Obtener reporte de tickets por prioridad
- `GET /api/reports/agent-performance` - Obtener reporte de desempeño de agentes

