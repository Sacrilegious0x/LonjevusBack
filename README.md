# Lonjevus Backend API - Especificación Técnica

Este repositorio contiene el núcleo lógico y de persistencia de **Lonjevus**, una plataforma de gestión para centros de cuidado. El sistema está construido bajo una arquitectura de microservicios monolíticos utilizando el ecosistema de **Spring Boot 3**.

## 🛠 Stack Tecnológico

*   **Runtime:** Java 17
*   **Framework Principal:** Spring Boot 3.x
*   **Seguridad:** Spring Security 6 + JSON Web Token (JWT)
*   **Persistencia:** Spring Data JPA + Hibernate
*   **Base de Datos:** MySQL 8.0
*   **Build Tool:** Maven

## 🏗 Arquitectura del Sistema

El proyecto sigue un patrón de diseño por capas (N-Tier Architecture):
1.  **Layer de Dominio:** Entidades JPA que representan el modelo relacional.
2.  **Layer de Repositorio:** Interfaces que extienden de `JpaRepository` para abstracción de consultas SQL.
3.  **Layer de Servicio:** Implementación de la lógica de negocio, manejo de transaccionalidad (`@Transactional`) y orquestación de datos.
4.  **Layer de Controlador:** REST Endpoints con validación de seguridad mediante anotaciones.

### Decisiones Técnicas Relevantes

*   **Gestión de Inventario Atómica:** En `PurchaseServiceJPA`, el registro de una compra dispara un proceso que desglosa la cantidad de productos en registros individuales dentro del inventario, asegurando trazabilidad por fecha de vencimiento.
*   **Persistencia Híbrida:** Aunque el proyecto usa JPA, existe una clase `ConnectionDB` para conexiones JDBC directas (Legacy Support o tareas específicas de bajo nivel).
*   **Almacenamiento de Recursos:** Implementación de `WebMvcConfigurer` para el mapeo de recursos estáticos externos, permitiendo que archivos locales en el servidor sean servidos como URLs públicas.

## 🔐 Seguridad y Control de Acceso (RBAC)

La seguridad se implementa de forma *stateless* mediante un filtro personalizado `JwtRequestFilter` que intercepta cada petición.

### Flujo de Autorización:
1.  **Autenticación:** El `AuthenticationController` valida credenciales y genera un JWT con el `Subject` (email) y `Claims` personalizados.
2.  **Validación:** `JwtUtils` se encarga de la firma (HS256) y expiración del token.
3.  **Autorización Fina:** Se utiliza **RBAC (Role-Based Access Control)** dinámico. Los permisos se cargan desde la base de datos y se inyectan en el contexto de seguridad de Spring, permitiendo el uso de:
    ```java
    @PreAuthorize("hasAuthority('PERMISSION_INVENTARIO_VIEW')")
    ```

## 📊 Modelo de Datos

El sistema gestiona una base de datos relacional con las siguientes entidades críticas:
*   **Users (Admin/Caregiver):** Herencia lógica mediante roles para diferenciar privilegios.
*   **Inventory:** Vinculado a `Product` y `Purchase`, permitiendo el control de stock y mermas.
*   **Permissions:** Tabla de unión que mapea acciones (View, Create, Update, Delete) sobre módulos específicos para cada Rol.

## 📡 API Endpoints Principales

| Módulo | Endpoint | Método | Auth | Descripción |
| :--- | :--- | :--- | :--- | :--- |
| **Auth** | `/api/auth/login` | `POST` | PermitAll | Intercambio de credenciales por JWT. |
| **Inventory** | `/api/inventory/save` | `POST` | JWT (Create) | Carga de stock con soporte para `multipart/form-data`. |
| **Purchases** | `/purchase` | `POST` | JWT (Create) | Registro transaccional de compras e impacto en inventario. |
| **Caregivers** | `/caregiver/listCaregiver` | `GET` | JWT (View) | Listado de personal con carga Lazy de horarios. |

## ⚙️ Configuración de Entorno

### Variables de Aplicación (`application.properties`)

Es necesario configurar el origen de datos y las políticas de archivos:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/longevusdb
spring.datasource.username=root
spring.datasource.password=

# Configuración de carga de archivos
spring.servlet.multipart.max-file-size=50MB
```

### Configuración de CORS
El backend está configurado para aceptar peticiones exclusivamente desde el origen del frontend (Vite/React):
*   **Allowed Origins:** `http://localhost:5173`
*   **Allowed Methods:** `GET, POST, PUT, DELETE, OPTIONS`

## 🛠 Comandos de Desarrollo

**Compilar el proyecto:**
```bash
mvn clean install
```

**Ejecutar en modo desarrollo:**
```bash
mvn spring-boot:run
```

---
**Nota:** Asegúrese de que el puerto definido en `ConnectionDB` (3399) coincida con su instancia local de MySQL si no está utilizando la configuración estándar de Spring.
```
