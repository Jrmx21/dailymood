# Docker - Documentación combinada

Resumen y guía práctica unificada para todo lo relacionado con Docker en este proyecto.

## Contenido
- Modo producción (multi-stage build)
- Modo desarrollo (hot-reload)
- Comandos útiles
- Arquitectura y variables de entorno

---

## Modo producción (multi-stage build)

El `Dockerfile` usa un build multi-stage: compila el proyecto con Maven en un stage y copia el JAR resultante a una imagen JRE ligera.

Pasos rápidos:

```bash
# Compilar y levantar (docker-compose usa el Dockerfile para build)
docker-compose up -d
```

Accede a la app en: `http://localhost:8080`

Ventajas:
- Imagen optimizada
- No necesitas Maven localmente para crear la imagen

---

## Modo desarrollo (hot reload)

Para desarrollo con reinicio automático al cambiar código fuente:

```bash
docker-compose -f docker-compose.dev.yml up
```

Características:
- `src/` montado como volumen
- Maven corre dentro del contenedor (`./mvnw spring-boot:run`)
- Spring DevTools reinicia la aplicación al detectar cambios
- Cache de Maven se monta en `~/.m2` para acelerar builds

---

## Comandos útiles

```bash
# Ver estado
docker-compose ps

# Ver logs
docker-compose logs -f app
# En desarrollo:
docker-compose -f docker-compose.dev.yml logs -f app

# Detener y limpiar
docker-compose down
docker-compose down -v

# Reconstruir imagen
docker-compose build --no-cache
```

---

## Arquitectura y variables de entorno

La app se conecta a MySQL mediante:

```
SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/dailymood
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=123456
```

Servicios principales:
- `dailymood-app` (Spring Boot) puerto 8080
- `dailymood-mysql` (MySQL 8.0) puerto 3306

---

## Archivos relevantes

- `Dockerfile` (multi-stage)
- `docker-compose.yml` (producción)
- `docker-compose.dev.yml` (desarrollo / hot-reload)
- `.dockerignore`

---

(Contenido combinado de `DOCKER.md`, `DOCKER_SETUP_COMPLETO.md` y `DOCKER_FINAL.md`)

