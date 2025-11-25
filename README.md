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
  *** Begin Replacement ***
  # 🐾 Tienda Mi Mascota Backend

  Backend con Spring Boot 3.5.7 para la aplicación de e‑commerce de productos para mascotas.

  ---

  ## 🚀 Resumen rápido

  - Base URL (context-path): `https://<tu-host>/api` (la aplicación usa `server.servlet.context-path=/api`).
  - Swagger UI (producción): `https://tiendamimascotabackends.onrender.com/api/swagger-ui/index.html`
  - OpenAPI JSON: `https://tiendamimascotabackends.onrender.com/api/v3/api-docs`

  > Si tu dominio de producción es distinto, sustituye `tiendamimascotabackends.onrender.com` por tu host.

  ---

  ## 🧰 Stack tecnológico

  - Java 21
  - Spring Boot 3.5.7
  - Spring Security + JWT (JJWT)
  - Spring Data JPA (Hibernate)
  - H2 (desarrollo) / MySQL (producción)
  - Maven
  - OpenAPI / Swagger (springdoc)

  ---

  ## 🔁 Ejecutar localmente

  1) Clonar y compilar:

  ```cmd
  git clone https://github.com/yasser-duoc/TiendaMiMascotaBackends.git
  cd TiendaMiMascotaBackends
  mvn clean package -DskipTests
  ```

  2) Ejecutar en modo desarrollo (H2, perfil `local`):

  ```cmd
  set SPRING_PROFILES_ACTIVE=local
  mvn -Dspring-boot.run.profiles=local -DskipTests spring-boot:run
  ```

  3) Ejecutar el JAR (producción-similar):

  ```cmd
  mvn clean package -DskipTests
  set SPRING_PROFILES_ACTIVE=local
  java -jar target\tienda-mascota-backend-1.0.0.jar
  ```

  4) Probar endpoints:

  ```cmd
  curl -i http://localhost:8080/api/productos
  curl -i http://localhost:8080/api/v3/api-docs
  ```

  ---

  ## 📦 Despliegue en Render (resumen práctico)

  1. En el panel de Render crea un **Web Service** y conecta el repo.
  2. Build command:

  ```text
  mvn clean package -DskipTests
  ```

  3. Start command (ejemplo):

  ```text
  java -Dserver.port=$PORT -Dspring.profiles.active=prod -jar target/*.jar
  ```

  4. Variables de entorno recomendadas (en Render → Environment):

  - `DATABASE_URL` = `jdbc:mysql://host:3306/tu_db?useSSL=true&serverTimezone=UTC&allowPublicKeyRetrieval=true`
  - `DB_USERNAME` = `<usuario_mysql>`
  - `DB_PASSWORD` = `<pass_mysql>`
  - `JWT_SECRET` = `<secreto_jwt_seguro>`
  - `APP_CORS_ALLOWED_ORIGINS` = `https://tienda-mi-mascota.vercel.app` (o una lista separada por comas)
  - `APP_ADMIN_INIT_SECRET` = `<secreto_para_init_admin>` (opcional, ver sección Admin)
  - `APP_DATA_INIT_ENABLED` = `true|false` (si quieres inicializar data automáticamente)

  5. Push a la rama que Render vigila para disparar el build.

  ---

  ## 🔒 Swagger en producción (seguro)

  La UI de Swagger está disponible en:

  ```
  https://tiendamimascotabackends.onrender.com/api/swagger-ui/index.html
  ```

  OpenAPI JSON:

  ```
  https://tiendamimascotabackends.onrender.com/api/v3/api-docs
  ```

  Recomendaciones de seguridad:

  - No exponer Swagger públicamente en producción si contiene datos sensibles o facilita el descubrimiento de endpoints de administración.
  - Opciones seguras para controlar acceso a Swagger:
    - Permitir solo a usuarios con rol `ADMIN` (configurar en `SecurityConfig`).
    - Habilitar Swagger solo mediante variable de entorno temporal `SWAGGER_ENABLED=true`.
    - Filtrar por IP en la capa de infraestructura (firewall o reglas de Render).

  Si quieres, puedo añadir en el código un matcher que permita Swagger solo a `ROLE_ADMIN` y mostrar el snippet.

  ---

  ## 🔧 Endpoints principales

  (Se usan rutas relativas al context-path `/api`)

  - `POST /api/auth/login` — Iniciar sesión
  - `POST /api/auth/registro` — Registrar usuario
  - `GET /api/productos` — Listar productos
  - `GET /api/productos/{id}` — Obtener producto
  - `POST /api/ordenes` — Crear orden
  - `POST /api/productos/verificar-stock` — Verificar stock

  Consulta la UI de Swagger para ver todos los endpoints y modelos.

  ---

  ## 🛠️ Inicializar admin (temporal)

  Si necesitas crear el usuario `admin` en producción sin ejecutar scripts en la BD, el proyecto incluye un endpoint temporal protegido por secreto:

  - `POST /api/auth/init-admin`
    - Header: `X-Admin-Secret: <valor_de_APP_ADMIN_INIT_SECRET>`
    - Requiere que la variable de entorno `APP_ADMIN_INIT_SECRET` esté configurada en Render.

  Alternativa: activar `APP_DATA_INIT_ENABLED=true` (si `DataInitializer` crea el admin automáticamente). Usar con precaución.

  ---

  ## 🌐 CORS

  - Variable en producción: `APP_CORS_ALLOWED_ORIGINS` (lista separada por comas). Ejemplo:

  ```
  APP_CORS_ALLOWED_ORIGINS=https://tienda-mi-mascota.vercel.app,http://localhost:3000
  ```

  - La configuración actual admite patrones (`allowedOriginPatterns`) para permitir subdominios `*.vercel.app`.

  ---

  ## 🐳 Docker (opcional)

  - Construir imagen:

  ```bash
  docker build -t tienda-mascota-backend:local .
  ```

  - Ejecutar contenedor (ejemplo):

  ```bash
  docker run --rm -p 8080:8080 -e SPRING_PROFILES_ACTIVE=local -e APP_CORS_ALLOWED_ORIGINS=http://localhost:3000 tienda-mascota-backend:local
  ```

  ---

  ## ✅ Verificaciones útiles

  - ¿Swagger carga en producción?  -> `https://tiendamimascotabackends.onrender.com/api/swagger-ui/index.html`
  - ¿OpenAPI JSON accesible? -> `https://tiendamimascotabackends.onrender.com/api/v3/api-docs`
  - ¿Frontend recibe errores CORS? -> Revisa `APP_CORS_ALLOWED_ORIGINS` en Render y que `allowCredentials=true` combine con `allowedOriginPatterns` (no usar `*` cuando `allowCredentials=true`).

  ---

  ## 📂 Estructura del proyecto (resumen)

  ```
  src/main/java/com/tiendamascota
    ├─ config/ (Cors, Security, DataInitializer)
    ├─ controller/ (Auth, Producto, Orden, Usuario)
    ├─ dto/
    ├─ model/
    ├─ repository/
    └─ service/
  ```

  ---

  ## 📞 Soporte / próximos pasos

  Si quieres que:
  - limite el acceso a Swagger por `ROLE_ADMIN` → escribo el patch en `SecurityConfig`.
  - añada un ejemplo `settings.xml` para `mvn deploy` → lo genero.
  - prepare un script de deploy para Render con instrucciones paso a paso → lo escribo.

  Indica cuál de las tareas prefieres y la implemento.

  ---

  **Tienda Mi Mascota — Backend**

  *** End Replacement ***
