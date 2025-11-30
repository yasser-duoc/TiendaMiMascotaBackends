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

### **👥 Usuarios**

- `GET /api/usuarios` - Listar usuarios
- `GET /api/usuarios/{id}` - Obtener usuario por ID
- `POST /api/usuarios` - Crear usuario
- `PUT /api/usuarios/{id}` - Actualizar usuario
- `DELETE /api/usuarios/{id}` - Eliminar usuario

### **📦 Órdenes**

- `POST /api/ordenes` - Crear nueva orden
 # 🐾 Tienda Mi Mascota — Backend

Backend en Java con Spring Boot para una tienda de productos para mascotas.

## Resumen rápido

- Base URL (context-path): `/api` (configurado como `server.servlet.context-path=/api`).
- Swagger UI (ej. en producción): `https://<tu-host>/api/swagger-ui/index.html`
- OpenAPI JSON: `https://<tu-host>/api/v3/api-docs`

> Nota: la integración externa con "Huachitos" fue eliminada del código fuente — no hay beans ni configuraciones activas para esa integración.

## Stack tecnológico

- Java 21
- Spring Boot 3.5.x
- Spring Security + JWT
- Spring Data JPA (Hibernate)
- H2 (desarrollo) / MySQL (producción)
- Maven

## Instalación y ejecución

1) Clonar y compilar:

```cmd
git clone https://github.com/yasser-duoc/TiendaMiMascotaBackends.git
cd TiendaMiMascotaBackends
mvn clean package -DskipTests
```

2) Ejecutar en desarrollo (perfil `local`, H2):

```cmd
set SPRING_PROFILES_ACTIVE=local
mvn -Dspring-boot.run.profiles=local spring-boot:run
```

3) Ejecutar JAR (producción-similar):

```cmd
java -Dserver.port=%PORT% -Dspring.profiles.active=prod -jar target\tienda-mascota-backend-1.0.0.jar
```

## Configuración recomendada para producción

- `DATABASE_URL`, `DB_USERNAME`, `DB_PASSWORD` (MySQL)
- `JWT_SECRET` (secreto para tokens)
- `APP_CORS_ALLOWED_ORIGINS` (lista de orígenes permitidos)

## Endpoints principales (rutas relativas a `/api`)

### Autenticación

- `POST /api/auth/registro` — Registrar usuario
- `POST /api/auth/login` — Login (devuelve JWT)

### Productos

- `GET /api/productos` — Listar (paginado)
- `GET /api/productos/{id}` — Obtener por ID
- `POST /api/productos/verificar-stock` — Verificar stock
- `POST /api/productos` — Crear (admin)
- `PUT /api/productos/{id}` — Actualizar (admin)

### Usuarios

- `GET /api/usuarios` — Listar usuarios
- `GET /api/usuarios/{id}` — Obtener usuario
- `PUT /api/usuarios/{id}` — Actualizar usuario (preserva password si no se envía)

### Órdenes

- `POST /api/ordenes` — Crear orden
- `POST /api/ordenes/verificar-stock` — Verificar stock antes de crear orden

## Admin — Órdenes (paginación y filtros)

- `GET /api/ordenes?page=0&size=20` — Listar órdenes (admin)
  - Parámetros opcionales:
    - `usuarioId` (Long) — filtra por ID de usuario
    - `email` (String) — filtra por email de usuario
    - `estado` (String) — filtra por estado (ej. `pendiente`, `enviado`, `entregado`)
    - `page` y `size` — paginación
- `GET /api/ordenes/{id}` — Obtener orden con items
- `PUT /api/ordenes/{id}` — Actualizar estado y datos de envío (admin)

Ejemplo (obtener órdenes filtradas por email):

```cmd
curl -i "http://localhost:8080/api/ordenes?page=0&size=20&email=cliente@correo.com" \
  -H "Authorization: Bearer <TOKEN_ADMIN>"
```

## Swagger / OpenAPI

- Swagger UI: `/api/swagger-ui/index.html`
- OpenAPI JSON: `/api/v3/api-docs`

Recomendación: en producción restringir el acceso a Swagger (por rol o IP).

## Docker (opcional)

```bash
docker build -t tienda-mascota-backend:local .
docker run --rm -p 8080:8080 -e SPRING_PROFILES_ACTIVE=local tienda-mascota-backend:local
```

## Verificaciones rápidas

- Cargar Swagger: `http://localhost:8080/api/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/api/v3/api-docs`

## Soporte / próximos pasos

- Puedo añadir protección a Swagger para `ROLE_ADMIN`.
- Puedo generar un `settings.xml` o script de deploy para Render.

## 👤 Autores

**ddoblejotadev**
- GitHub: [@ddoblejotadev](https://github.com/ddoblejotadev)

**yasser-duoc**
- GitHub: [@yasser-duoc](https://github.com/yasser-duoc)

---

Tienda Mi Mascota — Backend
