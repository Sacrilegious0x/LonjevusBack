# Longevus Backend API - Especificación Técnica

> **"Porque en cada vida hay un legado que cuidar."**

**Longevus** es una plataforma web de gestión integral para centros de cuidado de adultos mayores, diseñada para digitalizar y optimizar los procesos internos del hogar, eliminando el uso de documentos físicos y mejorando la conexión entre el personal, los residentes y sus familias.

---

## 🛠 Stack Tecnológico

| Capa | Tecnología |
| :--- | :--- |
| **Runtime** | Java 17 |
| **Framework Principal** | Spring Boot 3.x |
| **Seguridad** | Spring Security 6 + JSON Web Token (JWT) |
| **Persistencia** | Spring Data JPA + Hibernate |
| **Base de Datos** | MySQL 8.0 |
| **Build Tool** | Maven |
| **Frontend** | React + Vite (repositorio separado) |

---

## 🏗 Arquitectura del Sistema

El proyecto sigue un patrón de diseño por capas (N-Tier Architecture):

1. **Layer de Dominio:** Entidades JPA que representan el modelo relacional.
2. **Layer de Repositorio:** Interfaces que extienden de `JpaRepository` para abstracción de consultas SQL.
3. **Layer de Servicio:** Implementación de la lógica de negocio, manejo de transaccionalidad (`@Transactional`) y orquestación de datos.
4. **Layer de Controlador:** REST Endpoints con validación de seguridad mediante anotaciones.

### Decisiones Técnicas Relevantes

- **Gestión de Inventario Atómica:** En `PurchaseServiceJPA`, el registro de una compra dispara un proceso que desglosa la cantidad de productos en registros individuales dentro del inventario, asegurando trazabilidad por fecha de vencimiento.
- **Persistencia Híbrida:** Aunque el proyecto usa JPA, existe una clase `ConnectionDB` para conexiones JDBC directas (Legacy Support o tareas específicas de bajo nivel).
- **Almacenamiento de Recursos:** Implementación de `WebMvcConfigurer` para el mapeo de recursos estáticos externos, permitiendo que archivos locales en el servidor sean servidos como URLs públicas.

---

## 👥 Perfiles de Usuario

### Administrador
Usuario con acceso completo al sistema. Gestiona la información más importante y tiene control sobre todos los módulos y usuarios.

**Permisos:**
- Acceso completo a todos los módulos del sistema
- Gestión y registro de residentes
- Gestión de personal y turnos
- Asignación y control de habitaciones
- Registro, modificación y eliminación de actividades en la agenda
- Gestión de inventario y control de suministros
- Gestión de proveedores, compras y facturación
- Generación de reportes y control de facturación a familiares
- Gestión de cuentas de usuarios del sistema (crear, modificar, eliminar)

### Trabajador / Cuidador
Usuario con acceso limitado. Su rol se enfoca en actividades operativas diarias y consulta de información.

**Permisos:**
- Consulta de datos de residentes (sin modificar ni eliminar)
- Registro y control de visitas
- Visualización de actividades en la agenda
- Reporte de necesidades o faltantes de inventario
- Consulta de habitaciones asignadas a residentes
- Visualización de información del personal y sus turnos
- Actualización de estado de actividades asignadas
- ⛔ Sin acceso a gestión de usuarios, facturación, proveedores ni configuraciones del sistema

---

## 📦 Módulos del Sistema

| # | Módulo | Descripción |
| :- | :--- | :--- |
| 1 | **Residentes** | Gestión de adultos mayores: datos personales, historial médico, contactos familiares, estado de salud y habitación asignada |
| 2 | **Visitas** | Registro y control de visitantes: nombre, fecha, residente a visitar, parentesco y fotografía |
| 3 | **Personal** | Gestión integral del personal: turnos, salarios, puestos, responsabilidades y relación con residentes a su cargo |
| 4 | **Habitaciones** | Control de habitaciones y camas: estado (disponible/asignada), tipo (individual/grupal) y residente asignado |
| 5 | **Actividades** | Agenda de actividades recreativas y citas médicas: solo administradores pueden agendar o modificar |
| 6 | **Inventario** | Control de suministros, medicamentos y productos: entradas, salidas y trazabilidad por vencimiento |
| 7 | **Proveedores** | Gestión de proveedores de alimentos, medicinas e insumos: historial de compras y facturación |
| 8 | **Facturación** | Administración de pagos de residentes: emisión de facturas, registro de pagos y asociación a familiares responsables |
| 9 | **Productos** | Gestión de productos relacionados con compras e inventario |

---

## 🔐 Seguridad y Control de Acceso (RBAC)

La seguridad se implementa de forma *stateless* mediante un filtro personalizado `JwtRequestFilter` que intercepta cada petición.

### Flujo de Autorización

1. **Autenticación:** El `AuthenticationController` valida credenciales y genera un JWT con el `Subject` (email) y `Claims` personalizados.
2. **Validación:** `JwtUtils` se encarga de la firma (HS256) y expiración del token.
3. **Autorización Fina:** Se utiliza **RBAC (Role-Based Access Control)** dinámico. Los permisos se cargan desde la base de datos y se inyectan en el contexto de seguridad de Spring, permitiendo el uso de:

```java
@PreAuthorize("hasAuthority('PERMISSION_INVENTARIO_VIEW')")
```

---

## 📊 Modelo de Datos

El sistema gestiona una base de datos relacional con las siguientes entidades críticas:

- **Persona (Admin / Cuidador):** Herencia lógica mediante roles para diferenciar privilegios. El Administrador incluye `ContactoOficina`; el Cuidador incluye `Turno` y lista de `Diligencias`.
- **Residente:** Identificación, nombre, edad, estado de salud, habitación asignada y fotografía.
- **Habitación:** Número, estado, tipo de habitación y cantidad de camas.
- **Inventario:** Vinculado a `Producto` y `Compra`, permitiendo control de stock y mermas por vencimiento.
- **Actividad:** Tipo (recreativa, médica, capacitación), fecha, horario, ubicación, responsable y estado.
- **Visita / Contacto:** Registro de visitantes y contactos familiares de cada residente.
- **Proveedor / Compra:** Historial de proveedores y compras con impacto directo en inventario.
- **Facturación:** Asociada al residente, con fecha, monto, periodo, método de pago y consecutivo.
- **Permissions:** Tabla de unión que mapea acciones (View, Create, Update, Delete) sobre módulos específicos para cada rol.

---

## 📡 API Endpoints Principales

| Módulo | Endpoint | Método | Auth | Descripción |
| :--- | :--- | :--- | :--- | :--- |
| **Auth** | `/api/auth/login` | `POST` | PermitAll | Intercambio de credenciales por JWT |
| **Residentes** | `/resident/list` | `GET` | JWT (View) | Listado completo de residentes |
| **Residentes** | `/resident/save` | `POST` | JWT (Create) | Registro de nuevo residente con foto |
| **Residentes** | `/resident/update` | `PUT` | JWT (Update) | Actualización de datos del residente |
| **Residentes** | `/resident/delete/{id}` | `DELETE` | JWT (Delete) | Eliminación de residente |
| **Visitas** | `/visit/list` | `GET` | JWT (View) | Historial de visitas registradas |
| **Visitas** | `/visit/save` | `POST` | JWT (Create) | Registro de nueva visita |
| **Personal** | `/caregiver/listCaregiver` | `GET` | JWT (View) | Listado de personal con carga Lazy de horarios |
| **Personal** | `/caregiver/save` | `POST` | JWT (Create) | Registro de nuevo trabajador |
| **Habitaciones** | `/room/list` | `GET` | JWT (View) | Listado de habitaciones y disponibilidad |
| **Habitaciones** | `/room/assign` | `PUT` | JWT (Update) | Asignación de habitación a residente |
| **Actividades** | `/activity/list` | `GET` | JWT (View) | Listado de actividades en agenda |
| **Actividades** | `/activity/save` | `POST` | JWT (Create) | Registro de nueva actividad (solo Admin) |
| **Inventario** | `/api/inventory/list` | `GET` | JWT (View) | Consulta de stock disponible |
| **Inventario** | `/api/inventory/save` | `POST` | JWT (Create) | Carga de stock con soporte para `multipart/form-data` |
| **Proveedores** | `/supplier/list` | `GET` | JWT (View) | Listado de proveedores registrados |
| **Proveedores** | `/supplier/save` | `POST` | JWT (Create) | Registro de nuevo proveedor |
| **Compras** | `/purchase` | `POST` | JWT (Create) | Registro transaccional de compras e impacto en inventario |
| **Facturación** | `/billing/list` | `GET` | JWT (View) | Listado de facturas emitidas |
| **Facturación** | `/billing/save` | `POST` | JWT (Create) | Emisión de nueva factura (solo Admin) |

---

## ⚙️ Configuración de Entorno

### Variables de Aplicación (`application.properties`)

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/longevusdb
spring.datasource.username=root
spring.datasource.password=

# Configuración de carga de archivos
spring.servlet.multipart.max-file-size=50MB
```

### Configuración de CORS

El backend está configurado para aceptar peticiones exclusivamente desde el origen del frontend (Vite/React):

- **Allowed Origins:** `http://localhost:5173`
- **Allowed Methods:** `GET, POST, PUT, DELETE, OPTIONS`

---

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

## 👨‍💻 Equipo de Desarrollo

| Nombre | Área |
| :--- | :--- |
| Gabriel Moya Caravaca | Backend + Frontend (Personal, Horarios, Tareas, Roles y Permisos e Implementacion JWT) |
| Joshua Céspedes Gómez | Backend + Frontend (Residentes, Contactos) |
| Pablo Ramírez Ugalde | Backend + Frontend (Proveedores, Roles y Permisos) |
| Britany Villalobos Salazar | Backend + Frontend (Inventario, Compras) |

---

> **Nota:** Asegúrese de que el puerto definido en `ConnectionDB` (3399) coincida con su instancia local de MySQL si no está utilizando la configuración estándar de Spring.
