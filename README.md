# Lonjevus - Backend API

Este es el backend del sistema **Lonjevus**, una plataforma diseñada para la gestión de centros de cuidado (enfocado en adultos mayores). La aplicación está construida con **Java** y **Spring Boot**, siguiendo una arquitectura orientada a servicios y seguridad basada en tokens JWT.

## 🚀 Tecnologías Utilizadas

*   **Java 17+**
*   **Spring Boot 3.x**
*   **Spring Security** (Autenticación y Autorización)
*   **JSON Web Token (JWT)** para sesiones stateless.
*   **Spring Data JPA / Hibernate** (Persistencia de datos).
*   **MySQL** (Base de datos relacional).
*   **Maven** (Gestión de dependencias).

## 🛠️ Configuración del Proyecto

### 1. Base de Datos
El sistema utiliza MySQL. Asegúrate de tener una instancia corriendo con los siguientes parámetros (configurados en `ConnectionDB.java`):

*   **URL:** `jdbc:mysql://localhost:3399/longevusdb`
*   **Usuario:** `root`
*   **Contraseña:** (vacía por defecto)

### 2. Almacenamiento de Archivos (Fotos)
El sistema gestiona fotos de cuidadores e inventario. Actualmente, la ruta está configurada de forma absoluta en `WebConfig.java`:
*   Ruta local: `C:/Users/User/Desktop/Proyecto Lenguajes/proyecto-lonjevus-back/LonjevusBack/uploads/photos/`
*   Acceso vía API: `http://localhost:8080/photos/**`

### 3. Seguridad y Roles
Se implementa un control de acceso basado en permisos (RBAC). Los roles principales son:
*   **ADMIN:** Acceso total a gestión de personal, compras e inventario.
*   **CAREGIVER:** Acceso limitado según los permisos asignados en la base de datos.

## 📑 Módulos Principales

### Autenticación (`/api/auth`)
*   `POST /login`: Genera un token JWT y devuelve los datos del usuario.
*   `POST /newPassword`: Permite restablecer contraseñas para administradores y cuidadores.

### Inventario y Compras (`/api/inventory`, `/purchase`)
*   Gestión de productos, stock y fechas de vencimiento.
*   Al registrar una compra, el sistema desglosa automáticamente los productos y los ingresa al inventario de forma individual.

### Cuidadores (`/caregiver`)
*   CRUD completo de trabajadores, incluyendo la carga de fotos de perfil y asignación de horarios (`Schedule`).

### Permisos (`/permissions`)
*   Gestión dinámica de permisos por rol, permitiendo habilitar o deshabilitar acciones de Ver, Crear, Actualizar o Eliminar por módulo.

## ⚙️ Instalación

1.  Clonar el repositorio.
2.  Importar el proyecto en tu IDE favorito (IntelliJ IDEA, Eclipse, NetBeans).
3.  Asegurarte de que el puerto `3399` para MySQL esté disponible o modificar `ConnectionDB.java`.
4.  Ejecutar la aplicación mediante el comando:
    ```bash
    mvn spring-boot:run
    ```

## 🛡️ Seguridad (CORS)
La API está configurada para aceptar peticiones desde el frontend en `http://localhost:5173` (típicamente un entorno de desarrollo con Vite/React).

## 📝 Notas de Desarrollo
*   **Filtro JWT:** Cada petición protegida debe incluir el header `Authorization: Bearer <token>`.
*   **Transaccionalidad:** Los servicios de compras e inventario utilizan `@Transactional` para asegurar la integridad de los datos al realizar múltiples inserciones.

---
Desarrollado para el Proyecto de Lenguajes - UCR.
```

### Sugerencias de mejora para tu proyecto:

1.  **Externalización de configuraciones:** Actualmente, la configuración de la base de datos (`ConnectionDB.java`) y las rutas de fotos (`WebConfig.java`) tienen rutas fijas (hardcoded). Te recomendaría mover estas configuraciones al archivo `src/main/resources/application.properties` para que el proyecto sea más fácil de desplegar en diferentes computadoras.
2.  **Manejo de Excepciones:** Podrías implementar un `@ControllerAdvice` para manejar los errores de forma global y devolver respuestas JSON más limpias en lugar de lanzar `RuntimeException` directamente en los servicios.

<!--
[PROMPT_SUGGESTION]¿Cómo puedo mover la configuración de la base de datos de ConnectionDB.java al archivo application.properties de Spring? [/PROMPT_SUGGESTION]
[PROMPT_SUGGESTION]¿Cómo implementar un manejador global de excepciones para que los errores de "Producto no encontrado" devuelvan un código 404?[/PROMPT_SUGGESTION]
