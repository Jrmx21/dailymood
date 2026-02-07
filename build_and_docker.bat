@echo off
REM Compila el proyecto con Maven y construye+despliega la app con Docker Compose
cd /d %~dp0
REM Compilar con Maven (usa mvnw si está presente)
if exist mvnw.cmd (
  echo Usando mvnw.cmd para compilar...
  .\mvnw.cmd clean package -DskipTests
) else (
  echo Usando mvn para compilar...
  mvn clean package -DskipTests
)

n:: Construir imagen y desplegar con docker-compose (app + mysql)
docker-compose build app
docker-compose up -d
echo Aplicación desplegada en http://localhost:8080
pause
