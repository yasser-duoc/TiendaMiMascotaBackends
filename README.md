# 🐾 Tienda Mi Mascota Backend

Backend con Spring Boot 3.5.7 para la aplicación de e-commerce de productos para mascotas.

## 🚀 **Stack Tecnológico**

- **Java 21**
- **Spring Boot 3.5.7**
- **Spring Security + JWT** (JJWT 0.12.3)
- **Spring Data JPA + Hibernate**
- **MySQL** (Producción) / H2 (Desarrollo)
- **Maven 3.14.1**
- **Swagger/OpenAPI 3.0**

## 📋 **Requisitos**

- Java 21 o superior
- Maven 3.6 o superior
- MySQL 8.0+ (para producción)

## ⚙️ **Instalación**

```bash
# Clonar repositorio
git clone https://github.com/yasser-duoc/TiendaMiMascotaBackends.git
cd TiendaMiMascotaBackends

# Instalar dependencias
mvn clean install
```

## 🏃 **Ejecutar Localmente**

### **Desarrollo (H2 en memoria):**
```bash
mvn spring-boot:run
```

### **Producción (MySQL):**
```bash
# Configurar variables de entorno
set DB_USERNAME=root
set DB_PASSWORD=tu_password
set DATABASE_URL=jdbc:mysql://localhost:3306/tienda_mimascota?useSSL=false^&serverTimezone=UTC
set JWT_SECRET=mi-secreto-desarrollo

# Ejecutar
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

El servidor estará disponible en: `http://localhost:8080/api`

## 📚 **Endpoints API**

### **🔐 Autenticación**

- `POST /api/auth/registro` - Registrar nuevo usuario
- `POST /api/auth/login` - Iniciar sesión (devuelve JWT)
- `GET /api/auth/verificar` - Verificar token JWT válido
- `POST /api/auth/logout` - Cerrar sesión

### **🛍️ Productos**

- `GET /api/productos?page=0&size=20` - Listar productos (paginado)
- `GET /api/productos/{id}` - Obtener producto por ID
- `GET /api/productos/categoria/{categoria}` - Filtrar por categoría
- `POST /api/productos/verificar-stock` - Verificar disponibilidad de stock
- `POST /api/productos` - Crear producto (admin)
- `PUT /api/productos/{id}` - Actualizar producto (admin)
- `DELETE /api/productos/{id}` - Eliminar producto (admin)

### **📦 Órdenes**

- `POST /api/ordenes` - Crear nueva orden
- `POST /api/ordenes/verificar-stock` - Verificar stock antes de ordenar
- `GET /api/ordenes/usuario/{usuarioId}` - Historial de órdenes del usuario

## 📖 **Documentación API (Swagger)**

Accede a Swagger UI en: `http://localhost:8080/api/swagger-ui.html`

OpenAPI JSON: `http://localhost:8080/api/v3/api-docs`

## 🗄️ **Base de Datos**

### **H2 Console (Desarrollo)**

Accede en: `http://localhost:8080/api/h2-console`

```
JDBC URL: jdbc:h2:mem:testdb
User: sa
Password: (vacío)
```

### **MySQL (Producción)**

```sql
CREATE DATABASE tienda_mimascota 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;
```

Hibernate creará las tablas automáticamente con `ddl-auto=update`.

## 🔑 **Autenticación JWT**

### **Claims incluidos:**
- `usuario_id` (Integer)
- `email` (String)
- `nombre` (String)
- `rol` (String)

### **Expiración:** 7 días (604800000 ms)

### **Uso:**
```bash
# 1. Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"test@test.com","password":"123456"}'

# 2. Usar token
curl -X GET http://localhost:8080/api/auth/verificar \
  -H "Authorization: Bearer eyJhbGc..."
```

## 🌐 **CORS Configurado**

- ✅ `https://mimascota.vercel.app` (Producción web)
- ✅ `http://localhost:5173` (Vite desarrollo)
- ✅ `http://localhost:3000` (React desarrollo)
- ✅ Android (allowedOriginPatterns: *)

## 🚀 **Despliegue en Render**

Ver guía completa en **[DEPLOY_RENDER.md](./DEPLOY_RENDER.md)**

### **Resumen rápido:**

1. **Crear MySQL Database en Render**
2. **Crear Web Service:**
   - Build: `./mvnw clean package -DskipTests`
   - Start: `java -Dserver.port=$PORT -Dspring.profiles.active=prod -jar target/*.jar`
3. **Variables de entorno:**
   ```
   DATABASE_URL=jdbc:mysql://...
   DB_USERNAME=admin_tienda
   DB_PASSWORD=********
   JWT_SECRET=tu-secreto-seguro
   ```
4. **Auto-Deploy:** Push a `main` o `desarrollo`

**URL producción:** `https://[tu-service].onrender.com/api`

## 📁 **Estructura del Proyecto**

```
src/
├── main/
│   ├── java/com/tiendamascota/
│   │   ├── TiendaMascotaApplication.java
│   │   ├── config/
│   │   │   ├── CorsConfig.java
│   │   │   ├── SecurityConfig.java
│   │   │   └── DataInitializer.java
│   │   ├── controller/
│   │   │   ├── AuthController.java
│   │   │   ├── ProductoController.java
│   │   │   └── OrdenController.java
│   │   ├── dto/
│   │   │   ├── AuthResponse.java
│   │   │   ├── LoginRequest.java
│   │   │   ├── RegistroRequest.java
│   │   │   ├── CrearOrdenRequest.java
│   │   │   └── ...
│   │   ├── model/
│   │   │   ├── Usuario.java
│   │   │   ├── Producto.java
│   │   │   ├── Orden.java
│   │   │   └── OrdenItem.java
│   │   ├── repository/
│   │   │   ├── UsuarioRepository.java
│   │   │   ├── ProductoRepository.java
│   │   │   └── OrdenRepository.java
│   │   ├── security/
│   │   │   ├── JwtUtil.java
│   │   │   └── JwtAuthenticationFilter.java
│   │   ├── service/
│   │   │   ├── AuthService.java
│   │   │   └── OrdenService.java
│   │   ├── util/
│   │   │   └── RutValidator.java
│   │   └── validation/
│   │       ├── ValidRut.java
│   │       └── RutValidatorConstraint.java
│   └── resources/
│       ├── application.properties
│       └── application-prod.properties
└── test/
```

## 🧪 **Testing**

```bash
# Ejecutar tests
mvn test

# Con coverage
mvn clean test jacoco:report
```

## 🔧 **Variables de Entorno**

| Variable | Descripción | Ejemplo |
|----------|-------------|---------|
| `DATABASE_URL` | URL completa de MySQL | `jdbc:mysql://host:3306/db?useSSL=true` |
| `DB_USERNAME` | Usuario de base de datos | `admin_tienda` |
| `DB_PASSWORD` | Contraseña de BD | `********` |
| `JWT_SECRET` | Secret para firmar JWT | `mi-secreto-super-seguro-2025` |
| `PORT` | Puerto del servidor (Render) | `8080` |
| `SPRING_PROFILES_ACTIVE` | Perfil activo | `prod` |

## 📊 **Características**

- ✅ Autenticación JWT con refresh
- ✅ Gestión de usuarios con BCrypt
- ✅ CRUD completo de productos
- ✅ Sistema de órdenes con validación de stock
- ✅ Paginación en listados
- ✅ Validación de RUT chileno
- ✅ CORS multi-plataforma (Web + Mobile)
- ✅ Manejo de errores robusto
- ✅ Swagger/OpenAPI documentation
- ✅ Health checks para Render
- ✅ Connection pooling (HikariCP)
- ✅ SQL injection protection
- ✅ Password encryption (BCrypt)

## 🤝 **Integración Frontend**

### **React (Vercel):**
```javascript
const API_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/api';
```

### **Android (Kotlin):**
```kotlin
const val BASE_URL = "https://tu-backend.onrender.com/api/"
```

Nota: El backend ya no utiliza integraciones externas para generación de imágenes (ej., Unsplash). Cada producto contiene `imageUrl` que obtiene imágenes estáticas o de terceros; la generación automática fue removida del backend.

## 📝 **Licencia**

Este proyecto es privado y propiedad de Tienda Mi Mascota.

## 👥 **Equipo**

- Backend: Spring Boot + MySQL
- Frontend Web: React + Vercel
- Frontend Mobile: Android Nativo
- Infraestructura: Render.com

---

**¿Preguntas?** Revisa la documentación en [DEPLOY_RENDER.md](./DEPLOY_RENDER.md)
