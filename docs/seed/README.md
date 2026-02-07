# Seed (Generador de Datos)

Para generar datos falsos en la base de datos, ejecuta el script `fake_data.py` localmente contra la BD Docker.

## Pasos

1) Asegúrate de que la BD Docker está levantada:
```bash
docker-compose up -d
```

2) Instala las dependencias de Python (solo la primera vez):
```bash
pip install -r requirements.txt
```

3) Ejecuta el script:
```bash
python fake_data.py
```

El script:
- Se conecta a `localhost:3306` (BD Docker)
- Crea tablas si faltan
- Limpia tablas existentes
- Inserta 10 residentes, 10 familias, 10 miembros, 50 estados diarios

## Variables de entorno (opcional)

Puedes cambiar host, usuario, contraseña y número de registros:
```bash
MYSQL_HOST=localhost MYSQL_USER=root MYSQL_PASSWORD=123456 NUM_RECORDS=20 python fake_data.py
```
