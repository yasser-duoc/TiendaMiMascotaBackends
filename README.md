# Tienda Mi Mascota Backend

Backend con Spring Boot para la aplicación de tienda de mascotas.

## Requisitos

- Java 17 o superior
- Maven 3.6 o superior

## Instalación

```bash
mvn clean install
```

## Ejecutar

```bash
mvn spring-boot:run
```

El servidor estará disponible en: `http://localhost:8080/api`

## Endpoints

### Productos

- `GET /api/productos` - Obtener todos los productos
- `GET /api/productos/{id}` - Obtener producto por ID
- `GET /api/productos/categoria/{categoria}` - Obtener productos por categoría
- `POST /api/productos` - Crear nuevo producto
- `PUT /api/productos/{id}` - Actualizar producto
- `DELETE /api/productos/{id}` - Eliminar producto

## H2 Console

Accede a la consola H2 en: `http://localhost:8080/api/h2-console`

- JDBC URL: `jdbc:h2:mem:testdb`
- User: `sa`
- Password: (dejar vacío)

## Estructura

```
src/
├── main/
│   ├── java/com/tiendamascota/
│   │   ├── TiendaMascotaApplication.java
│   │   ├── controller/
│   │   ├── model/
│   │   ├── repository/
│   │   └── config/
│   └── resources/
│       └── application.properties
└── test/
```
