# 🚀 Guía de Despliegue en Render

## 📋 **Tabla de Contenidos**

1. [Pre-requisitos](#pre-requisitos)
2. [Preparar Base de Datos MySQL](#preparar-base-de-datos-mysql)
3. [Configurar Web Service en Render](#configurar-web-service-en-render)
4. [Variables de Entorno](#variables-de-entorno)
5. [Comandos de Build y Start](#comandos-de-build-y-start)
6. [Verificación Post-Despliegue](#verificación-post-despliegue)
7. [Configuración del Frontend](#configuración-del-frontend)
8. [Troubleshooting](#troubleshooting)

---

## 🔑 **Pre-requisitos**

- ✅ Cuenta en [Render.com](https://render.com)
- ✅ Repositorio Git (GitHub, GitLab o Bitbucket)
- ✅ Base de datos MySQL (Render MySQL o externa)
- ✅ Java 21 compatible

---

## 🗄️ **1. Preparar Base de Datos MySQL**

### **Opción A: MySQL en Render (Recomendado)**

1. **Ir a Dashboard de Render**
   - Click en **"New +"** → **"MySQL"**

2. **Configurar MySQL Database:**
   ```
   Name: tienda-mimascota-db
   Database: tienda_mimascota
   User: admin_tienda
   Region: Oregon (us-west) o el más cercano
   ```

3. **Crear Database**
   - Render generará automáticamente las credenciales
   - Guarda el **Internal Database URL** (para conexión desde el backend)

4. **Conectar y crear schema (opcional si usas ddl-auto=update):**
   ```sql
   -- Hibernate creará las tablas automáticamente
   -- Solo necesitas crear la base de datos
   CREATE DATABASE IF NOT EXISTS tienda_mimascota 
   CHARACTER SET utf8mb4 
   COLLATE utf8mb4_unicode_ci;
   ```

### **Opción B: MySQL Externa (AWS RDS, Railway, PlanetScale, etc.)**

Asegúrate de tener:
- Host/URL de conexión
- Puerto (generalmente 3306)
- Nombre de base de datos
- Usuario y contraseña
- Permitir conexiones desde IPs de Render

---

## ⚙️ **2. Configurar Web Service en Render**

### **Paso 1: Crear Web Service**

1. En Dashboard de Render → **"New +"** → **"Web Service"**

2. **Conectar repositorio:**
   - Selecciona tu repositorio Git
   - Render detectará automáticamente que es un proyecto Maven/Spring Boot

### **Paso 2: Configuración Básica**

```
Name: tienda-mimascota-backend
Region: Oregon (us-west) - mismo que la DB
Branch: main (o desarrollo)
Root Directory: . (dejar vacío si está en raíz)
Runtime: Java
```

### **Paso 3: Build Command**

```bash
./mvnw clean package -DskipTests
```

**Si no tienes Maven wrapper (mvnw), usa:**
```bash
mvn clean package -DskipTests
```

### **Paso 4: Start Command**

```bash
java -Dserver.port=$PORT -Dspring.profiles.active=prod -jar target/tienda-mascota-backend-1.0.0.jar
```

**⚠️ IMPORTANTE:** Render asigna el puerto dinámicamente en la variable `$PORT`

### **Paso 5: Plan**

- **Free Plan:** 750 horas/mes gratis (suficiente para pruebas)
- **Starter Plan:** $7/mes (producción, sin sleep, mejor performance)

---

## 🔐 **3. Variables de Entorno**

En la sección **"Environment"** de tu Web Service, agrega:

### **Variables Requeridas:**

```env
# Database Configuration (obtener de Render MySQL)
DB_USERNAME=admin_tienda
DB_PASSWORD=tu_password_generado_por_render
DATABASE_URL=jdbc:mysql://dpg-xxxxx.oregon-postgres.render.com:3306/tienda_mimascota?useSSL=true&serverTimezone=UTC

# JWT Secret (generar uno seguro)
JWT_SECRET=mi-super-secreto-jwt-produccion-2025-muy-seguro-12345678

# CORS Origins (tu frontend en Vercel)
CORS_ORIGINS=https://mimascota.vercel.app,https://tu-dominio.com

# Server Port (Render lo asigna automáticamente)
SERVER_PORT=8080

# Spring Profile
SPRING_PROFILES_ACTIVE=prod
```

### **Cómo obtener DATABASE_URL de Render MySQL:**

1. Ve a tu MySQL Database en Render
2. Copia el **Internal Database URL**
3. Convierte el formato de PostgreSQL a MySQL:
   
   **De:**
   ```
   mysql://admin_tienda:password@dpg-xxxxx.oregon-mysql.render.com/tienda_mimascota
   ```
   
   **A (para Spring Boot):**
   ```
   jdbc:mysql://dpg-xxxxx.oregon-mysql.render.com:3306/tienda_mimascota?useSSL=true&serverTimezone=UTC
   ```

### **Generar JWT_SECRET seguro:**

```bash
# Linux/Mac
openssl rand -base64 64

# Windows (PowerShell)
[Convert]::ToBase64String((1..64 | ForEach-Object { Get-Random -Minimum 0 -Maximum 256 }))

# O simplemente usa:
mi-secreto-jwt-super-seguro-produccion-2025-tienda-mimascota-12345678
```

---

## 🛠️ **4. Configuración Avanzada**

### **Health Check Path:**

```
/api/productos
```

Render hará ping a esta URL para verificar que el servicio está funcionando.

### **Auto-Deploy:**

- ✅ Habilitar **"Auto-Deploy"** para que Render despliegue automáticamente cuando hagas push a la rama principal

### **Environment Variables desde Database:**

Si usas Render MySQL, puedes usar variables automáticas:

```env
MYSQL_URL=${tienda_mimascota_db.MYSQL_URL}
DB_USERNAME=${tienda_mimascota_db.MYSQL_USER}
DB_PASSWORD=${tienda_mimascota_db.MYSQL_PASSWORD}
DATABASE_URL=jdbc:mysql://${tienda_mimascota_db.MYSQL_HOST}:${tienda_mimascota_db.MYSQL_PORT}/${tienda_mimascota_db.MYSQL_DATABASE}?useSSL=true&serverTimezone=UTC
```

---

## 📦 **5. Ajustar application-prod.properties**

Actualiza para usar las variables de Render:

```properties
# Database Configuration
spring.datasource.url=${DATABASE_URL:jdbc:mysql://localhost:3306/tienda_mimascota}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

# Server Port (Render lo asigna dinámicamente)
server.port=${PORT:8080}
```

---

## ✅ **6. Verificación Post-Despliegue**

### **Paso 1: Verificar Logs**

En Render Dashboard → Tu Web Service → **"Logs"**

Busca:
```
Started TiendaMascotaApplication in X seconds
Tomcat started on port(s): 8080 (http)
```

### **Paso 2: Probar Endpoints**

**URL de tu servicio:**
```
https://tienda-mimascota-backend.onrender.com
```

**Probar endpoints:**

1. **Health Check:**
```bash
curl https://tienda-mimascota-backend.onrender.com/api/productos
```

2. **Registro de usuario:**
```bash
curl -X POST https://tienda-mimascota-backend.onrender.com/api/auth/registro \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Test User",
    "email": "test@test.com",
    "password": "123456",
    "telefono": "+56912345678",
    "direccion": "Test 123",
    "run": "12345678-9"
  }'
```

3. **Login:**
```bash
curl -X POST https://tienda-mimascota-backend.onrender.com/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@test.com",
    "password": "123456"
  }'
```

4. **Verificar token:**
```bash
curl -X GET https://tienda-mimascota-backend.onrender.com/api/auth/verificar \
  -H "Authorization: Bearer TU_TOKEN_AQUI"
```

---

## 🌐 **7. Configuración del Frontend**

### **React (Vercel):**

Actualiza `.env.production`:

```env
VITE_API_URL=https://tienda-mimascota-backend.onrender.com/api
```

### **Android:**

Actualiza `Constants.kt`:

```kotlin
object ApiConstants {
    const val BASE_URL = "https://tienda-mimascota-backend.onrender.com/api/"
}
```

### **Configurar CORS en Render:**

Las URLs permitidas ya están configuradas en `SecurityConfig.java`:

```java
config.addAllowedOrigin("https://mimascota.vercel.app");
config.addAllowedOrigin("http://localhost:5173"); // Desarrollo
config.setAllowedOriginPatterns(Arrays.asList("*")); // Android
```

Si necesitas agregar más dominios, actualiza el código o usa la variable de entorno:

```env
CORS_ORIGINS=https://mimascota.vercel.app,https://otro-dominio.com
```

---

## 🔧 **8. Troubleshooting**

### **Problema 1: Build falla**

**Error:**
```
[ERROR] Failed to execute goal org.apache.maven.plugins:maven-compiler-plugin
```

**Solución:**
- Verifica que `pom.xml` tenga `<java.version>21</java.version>`
- Asegúrate de que Render use Java 21 (se configura automáticamente)

### **Problema 2: Database Connection Failed**

**Error:**
```
com.mysql.cj.jdbc.exceptions.CommunicationsException: Communications link failure
```

**Solución:**
1. Verifica que `DATABASE_URL` esté correctamente formateada
2. Asegura que uses **Internal Database URL** de Render (no External)
3. Verifica usuario y contraseña correctos

### **Problema 3: Port Already in Use**

**Error:**
```
Port 8080 was already in use
```

**Solución:**
Asegúrate de usar `$PORT` en el start command:
```bash
java -Dserver.port=$PORT -jar target/*.jar
```

### **Problema 4: CORS Errors en Frontend**

**Error:**
```
Access to XMLHttpRequest has been blocked by CORS policy
```

**Solución:**
1. Verifica que tu dominio de Vercel esté en `SecurityConfig.java`
2. Agrega el dominio a la variable de entorno `CORS_ORIGINS`
3. Redeploy el backend

### **Problema 5: 502 Bad Gateway**

**Error:**
```
502 Bad Gateway
```

**Solución:**
1. Revisa los logs en Render
2. Verifica que la aplicación inicie correctamente
3. Aumenta el timeout del Health Check
4. Verifica que el JAR se compile correctamente

### **Problema 6: Free Plan se duerme**

**Comportamiento:**
El servicio gratuito de Render se "duerme" después de 15 minutos de inactividad y tarda ~30 segundos en despertar.

**Soluciones:**
1. **Upgrade a Starter Plan ($7/mes)** - sin sleep, mejor performance
2. **Usar un servicio de ping:** [UptimeRobot](https://uptimerobot.com) para hacer ping cada 10 minutos
3. **Aceptar el delay** en primera carga (para desarrollo/pruebas)

---

## 📊 **9. Monitoreo y Mantenimiento**

### **Ver Logs en Tiempo Real:**

Render Dashboard → Tu Service → **"Logs"**

### **Métricas:**

Render Dashboard → Tu Service → **"Metrics"**
- CPU Usage
- Memory Usage
- Response Time
- Error Rate

### **Reiniciar Servicio:**

Render Dashboard → Tu Service → **"Manual Deploy"** → **"Clear build cache & deploy"**

---

## 🎯 **10. Checklist de Despliegue**

- [ ] Base de datos MySQL creada en Render
- [ ] Variables de entorno configuradas (DB_USERNAME, DB_PASSWORD, DATABASE_URL, JWT_SECRET)
- [ ] Build Command configurado: `./mvnw clean package -DskipTests`
- [ ] Start Command configurado con `$PORT`
- [ ] Health Check configurado en `/api/productos`
- [ ] Auto-Deploy habilitado
- [ ] Endpoints probados con curl/Postman
- [ ] Frontend actualizado con URL de Render
- [ ] CORS verificado desde frontend
- [ ] Logs revisados sin errores

---

## 🔗 **11. URLs Importantes**

- **Dashboard Render:** https://dashboard.render.com
- **Documentación Render:** https://render.com/docs
- **Tu Backend:** `https://[tu-service-name].onrender.com`
- **Logs:** Dashboard → Tu Service → Logs
- **MySQL:** Dashboard → Tu Database → Info

---

## 💡 **12. Ventajas de Render vs AWS EC2**

| Característica | Render | AWS EC2 |
|----------------|--------|---------|
| **Setup** | 5 minutos | 1-2 horas |
| **Costo inicial** | $0 (Free) | ~$10-20/mes |
| **Auto-deploy** | ✅ Incluido | ❌ Necesitas CI/CD |
| **SSL/HTTPS** | ✅ Automático | ❌ Manual (Certbot) |
| **Logs** | ✅ Dashboard | ❌ SSH o CloudWatch |
| **Escalado** | ✅ 1 click | ❌ Manual |
| **MySQL** | ✅ Incluido | ❌ Separado (RDS) |
| **Monitoreo** | ✅ Dashboard | ❌ CloudWatch ($) |

---

## 🚀 **¡Listo para Producción!**

Tu backend Spring Boot está desplegado en Render con:
- ✅ MySQL configurado
- ✅ JWT con 7 días de expiración
- ✅ CORS para Vercel y Android
- ✅ HTTPS automático
- ✅ Auto-deploy desde Git
- ✅ Logs y métricas incluidos

**Próximos pasos:**
1. Configura tu frontend en Vercel con la URL de Render
2. Actualiza tu app Android con la URL de producción
3. Prueba todos los flujos (registro, login, productos, órdenes)
4. Considera upgrade a Starter Plan ($7/mes) para mejor performance

¡Tu e-commerce está en línea! 🎉
