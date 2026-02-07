# DailyMood - Hogar para Adultos Mayores

Sistema de gestión integral para residencias de adultos mayores, permitiendo monitoreo del estado de residentes, gestión de familias y seguimiento diario de bienestar.

## 📋 Tabla de Contenidos

- [Características](#características)
- [Requisitos Previos](#requisitos-previos)
- [Instalación](#instalación)
- [Configuración](#configuración)
- [Uso](#uso)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Generador de Datos Falsos](#generador-de-datos-falsos)
- [Tecnologías](#tecnologías)

## ✨ Características

### Gestión de Residentes
- Crear, editar y eliminar residentes
- Registrar condiciones de salud
- Asignar habitaciones
- Ver historial de estados diarios

### Gestión de Familias
- Crear y gestionar familias
- Agregar miembros familiares
- Registrar datos de contacto
- Asociar residentes con familias
- Preferencias de notificaciones

### Estados Diarios
- Registrar estado emocional diario (HAPPY, SAD, NEUTRAL)
- Agregar observaciones del día
- Ver histórico de estados
- Seguimiento por residente

### Interfaz Web
- Navbar personalizado con gradiente
- Responsive design
- Formularios intuitivos
- Validación de datos

## 🔧 Requisitos Previos

- **Java 11+** (para Spring Boot)
- **MySQL 8.0+**
- **Maven 3.6+**
- **Python 3.7+** (para generador de datos)

## 📥 Instalación

### 1. Clonar el Repositorio
```bash
cd C:\Users\Jrmx2\Desktop\dailymood2
```

### 2. Configurar Base de Datos

```sql
CREATE DATABASE IF NOT EXISTS dailymood;
```

Editar credenciales en `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/dailymood
spring.datasource.username=root
spring.datasource.password=123456
```

### 3. Instalar Dependencias de Python (Opcional)

Para usar el generador de datos falsos:
```bash
pip install -r requirements.txt
```

## ⚙️ Configuración

### application.properties

```properties
spring.application.name=dailymood
spring.datasource.url=jdbc:mysql://localhost:3306/dailymood
spring.datasource.username=root
spring.datasource.password=123456
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
```

## 🚀 Uso

### Iniciar la Aplicación Localmente

```bash
# Con Maven
mvnw.cmd spring-boot:run

# O compilar y ejecutar
mvnw.cmd clean package
java -jar target/dailymood-0.0.1-SNAPSHOT.jar
```

Acceder a: `http://localhost:8080`

### Iniciar con Docker

#### Requisito
- Docker y Docker Compose instalados

#### Pasos

1. Compilar la aplicación:
```bash
mvnw.cmd clean package
```

2. Levantar contenedores:
```bash
docker-compose up -d
```

3. Acceder a:
- App: `http://localhost:8080`
- MySQL: `localhost:3306`

4. Ver logs:
```bash
docker-compose logs -f app
```

5. Detener:
```bash
docker-compose down
```

### Generar Datos de Prueba

#### Opción 1: Script Rápido
```bash
python fake_data.py
```

#### Opción 2: Doble-click
```
run_fake_data.bat
```

#### Opción 3: Personalizado
```bash
python generate_fake_data_advanced.py --records 50
```

Esto generará:
- 10+ Residentes
- 10+ Familias
- 10+ Miembros de Familia
- 50+ Estados Diarios

## 📁 Estructura del Proyecto

```
dailymood2/
├── src/
│   ├── main/
│   │   ├── java/com/ruis/dailymood/
│   │   │   ├── DailymoodApplication.java       # Clase principal
│   │   │   ├── config/                         # Configuraciones
│   │   │   ├── controller/                     # Controladores web
│   │   │   ├── restcontroller/                 # Controladores REST
│   │   │   ├── domain/
│   │   │   │   ├── entity/                     # Entidades JPA
│   │   │   │   └── enums/                      # Enumeraciones
│   │   │   ├── dto/                            # Data Transfer Objects
│   │   │   ├── service/                        # Servicios de negocio
│   │   │   └── repository/                     # Repositorios JPA
│   │   └── resources/
│   │       ├── application.properties          # Configuración
│   │       ├── static/
│   │       │   ├── css/global.css              # Estilos
│   │       │   └── js/                         # JavaScript
│   │       └── templates/
│   │           ├── index.html                  # Inicio
│   │           ├── residents.html              # Residentes
│   │           ├── families.html               # Familias
│   │           ├── daily_status.html           # Estados diarios
│   │           └── fragments/                  # Fragmentos HTML
│   └── test/                                   # Tests
├── pom.xml                                     # Dependencias Maven
├── fake_data.py                                # Generador datos
├── generate_fake_data_advanced.py              # Generador avanzado
├── requirements.txt                            # Dependencias Python
└── README.md                                   # Este archivo
```

## 🗄️ Modelo de Datos

### Entidades

#### Resident
```java
- id (PK)
- name
- surnames
- birthDate
- conditions
- room
- families (N:M)
- dailyStatuses (1:N)
```

#### Family
```java
- id (PK)
- residents (N:M)
- familyMembers (1:N)
```

#### FamilyMember
```java
- id (PK)
- name
- surnames
- email
- birthDate
- phoneNumber
- receiveStatusNotifications
- family (N:1)
```

#### DailyStatus
```java
- id (PK)
- date
- resident (N:1)
- statusType (ENUM: HAPPY, SAD, NEUTRAL)
- observations
```

## 🔗 Relaciones

- **Resident ↔ DailyStatus**: 1:N
- **Family ↔ FamilyMember**: 1:N
- **Family ↔ Resident**: N:M (through family_resident table)

## 📊 Generador de Datos Falsos

### Características

- Genera datos realistas en español
- 10 residentes con edades 60-100 años
- 10 familias con miembros asociados
- 50 estados diarios (últimos 5 días)
- Integridad referencial completa

### Uso

```bash
# Generar 10 registros (por defecto)
python fake_data.py

# Generar cantidad personalizada
python generate_fake_data_advanced.py --records 50

# Ver opciones disponibles
python generate_fake_data_advanced.py --help
```

### Requisitos

- MySQL corriendo (localhost:3306)
- Usuario: root
- Contraseña: 123456

## 🛠️ Tecnologías

### Backend
- **Spring Boot 3.x**
- **Spring Data JPA**
- **MySQL 8.0**
- **Java 11+**

### Frontend
- **Thymeleaf**
- **Bootstrap 5**
- **HTML5/CSS3**
- **JavaScript**

### Herramientas
- **Maven**
- **Python 3.7+**
- **Faker (generación de datos)**
- **MySQL Connector/J**

## 📝 Endpoints Principales

### Web (Thymeleaf)
- `GET /` - Inicio
- `GET /residents` - Listar residentes
- `GET /residents/form` - Formulario nuevo residente
- `GET /families` - Listar familias
- `GET /daily_status` - Estados diarios

### REST API
- `GET /api/get_residents` - Listar residentes (JSON)
- `POST /api/get_residents` - Crear residente
- `GET /api/daily_status` - Estados diarios

## 🐳 Docker

### Archivos Incluidos

- `Dockerfile` - Imagen de la aplicación
- `docker-compose.yml` - Orquestación de servicios
- `.dockerignore` - Archivos a excluir

### Comandos Útiles

```bash
# Construir imagen
docker build -t dailymood:latest .

# Levantar servicios
docker-compose up -d

# Ver estado
docker-compose ps

# Ver logs
docker-compose logs -f app
docker-compose logs -f mysql

# Acceder a MySQL
docker exec -it dailymood-mysql mysql -u root -p123456 dailymood

# Detener
docker-compose down

# Limpiar todo
docker-compose down -v
```

### Arquitectura

```
┌─────────────────────┐
│   dailymood-app     │
│   (Spring Boot)     │
│   puerto 8080       │
└──────────┬──────────┘
           │
           │ (jdbc:mysql://mysql:3306)
           │
┌──────────▼──────────┐
│  dailymood-mysql    │
│   (MySQL 8.0)       │
│   puerto 3306       │
└─────────────────────┘
```

## 🔐 Configuración de Seguridad

Actualmente con SecurityConfig básica. Para producción:
- Implementar autenticación
- Agregar roles y permisos
- Validar CSRF tokens
- Usar HTTPS

## 📞 Soporte

Para problemas con los scripts Python, revisa:
- `QUICK_START_FAKE_DATA.md`
- `FAKE_DATA_README.md`
- `DATABASE_STRUCTURE.md`

## 📝 Licencia

Proyecto educativo - DailyMood 2026

## ✅ Checklist de Inicio

- [ ] Java 11+ instalado
- [ ] MySQL corriendo
- [ ] BD 'dailymood' creada
- [ ] Maven instalado
- [ ] Python 3.7+ (opcional)
- [ ] Dependencias Maven descargadas
- [ ] Aplicación inicia sin errores

## 🚀 Próximos Pasos

1. Ejecuta: `mvnw.cmd spring-boot:run`
2. Abre: `http://localhost:8080`
3. Genera datos: `python fake_data.py`
4. ¡Comienza a usar!

---

## Documentación adicional

Toda la documentación relacionada con Docker, generación de datos y guías rápidas está centralizada en la carpeta `docs/`.

- `docs/README.md` — Índice de la documentación del proyecto
- `docs/docker/README.md` — Instrucciones de Docker (producción / desarrollo)
- `docs/seed/README.md` — Cómo ejecutar `fake_data.py` en Docker

**Creado:** Febrero 2026  
**Versión:** 2.0  
**Estado:** ✅ Listo para usar
