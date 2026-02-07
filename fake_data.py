#!/usr/bin/env python3
"""
Script para generar datos falsos en la base de datos dailymood
Utiliza Faker para generar datos realistas
"""

import mysql.connector
from mysql.connector import Error
from faker import Faker
from datetime import datetime, timedelta
import random

# Configuración de conexión
DB_CONFIG = {
    'host': 'localhost',
    'user': 'root',
    'password': '123456',
    'database': 'dailymood',
    'charset': 'utf8mb3',
    'autocommit': False
}

fake = Faker('es_ES')
NUM_RECORDS = 10  # Número de registros por tabla

def get_connection():
    """Establece conexión con la base de datos"""
    try:
        connection = mysql.connector.connect(**DB_CONFIG)
        if connection.is_connected():
            cursor = connection.cursor()
            cursor.execute("SET NAMES utf8mb3")
            cursor.execute("SET CHARACTER SET utf8mb3")
            cursor.close()
            print("✅ Conexión a la base de datos establecida")
            return connection
    except Error as e:
        print(f"❌ Error de conexión: {e}")
        print("   💡 Solución: Asegúrate que MySQL está corriendo en localhost:3306")
        print("   💡 Usuario: root, Contraseña: 123456")
        return None

def clear_tables(cursor):
    """Limpia las tablas (con cuidado de foreign keys)"""
    try:
        print("\n🗑️  Limpiando tablas...")
        cursor.execute("SET FOREIGN_KEY_CHECKS = 0")

        tables = ['daily_status', 'family_resident', 'family_member', 'family', 'resident']
        for table in tables:
            cursor.execute(f"TRUNCATE TABLE {table}")
            print(f"   ✓ Tabla '{table}' limpiada")

        cursor.execute("SET FOREIGN_KEY_CHECKS = 1")
        print("✅ Limpieza completada\n")
    except Error as e:
        print(f"❌ Error al limpiar tablas: {e}")

def generate_residents(cursor):
    """Genera datos falsos para la tabla Resident"""
    print(f"📝 Generando {NUM_RECORDS} Residentes...")

    rooms = ['101', '102', '103', '104', '105', '201', '202', '203', '204', '205']
    conditions = [
        'Diabetes',
        'Hipertensión',
        'Artritis',
        'Asma',
        'Osteoporosis',
        'Insuficiencia Cardíaca',
        'EPOC',
        'Alzheimer',
        'Ninguna',
        'Múltiples'
    ]

    residents = []
    for i in range(NUM_RECORDS):
        name = fake.first_name()
        surnames = fake.last_name() + ' ' + fake.last_name()
        birth_date = fake.date_of_birth(minimum_age=60, maximum_age=100).isoformat()
        condition = random.choice(conditions)
        room = random.choice(rooms)

        query = """
        INSERT INTO resident (name, surnames, birth_date, conditions, room)
        VALUES (%s, %s, %s, %s, %s)
        """

        try:
            cursor.execute(query, (name, surnames, birth_date, condition, room))
            residents.append(cursor.lastrowid)
            print(f"   ✓ {i+1}. {name} {surnames} - Habitación {room}")
        except Error as e:
            print(f"   ❌ Error al insertar residente {i+1}: {e}")

    return residents

def generate_families(cursor):
    """Genera datos falsos para la tabla Family"""
    print(f"\n👨‍👩‍👧‍👦 Generando {NUM_RECORDS} Familias...")

    families = []
    for i in range(NUM_RECORDS):
        query = "INSERT INTO family () VALUES ()"

        try:
            cursor.execute(query)
            families.append(cursor.lastrowid)
            print(f"   ✓ Familia {i+1} creada")
        except Error as e:
            print(f"   ❌ Error al crear familia {i+1}: {e}")

    return families

def generate_family_members(cursor, families):
    """Genera datos falsos para la tabla FamilyMember"""
    print(f"\n👤 Generando {NUM_RECORDS} Miembros de Familia...")

    family_members = []
    for i in range(NUM_RECORDS):
        name = fake.first_name()
        surnames = fake.last_name() + ' ' + fake.last_name()
        email = fake.email()
        birth_date = fake.date_of_birth(minimum_age=18, maximum_age=80).isoformat()
        phone = fake.phone_number()
        receive_notifications = random.choice([True, False])
        family_id = random.choice(families)

        query = """
        INSERT INTO family_member (name, surnames, email, birth_date, phone_number, 
                                   receive_status_notifications, family_id)
        VALUES (%s, %s, %s, %s, %s, %s, %s)
        """

        try:
            cursor.execute(query, (name, surnames, email, birth_date, phone,
                                  receive_notifications, family_id))
            family_members.append(cursor.lastrowid)
            print(f"   ✓ {i+1}. {name} {surnames} ({email})")
        except Error as e:
            print(f"   ❌ Error al insertar miembro {i+1}: {e}")

    return family_members

def link_residents_to_families(cursor, residents, families):
    """Relaciona residentes con familias (many-to-many)"""
    print(f"\n🔗 Relacionando Residentes con Familias...")

    links = 0
    for resident_id in residents:
        # Cada residente se relaciona con 1-3 familias
        num_families = random.randint(1, 3)
        selected_families = random.sample(families, min(num_families, len(families)))

        for family_id in selected_families:
            query = """
            INSERT INTO family_resident (family_id, resident_id)
            VALUES (%s, %s)
            """

            try:
                cursor.execute(query, (family_id, resident_id))
                links += 1
            except Error as e:
                print(f"   ⚠️  Error al relacionar: {e}")

    print(f"   ✓ {links} relaciones creadas")

def generate_daily_status(cursor, residents):
    """Genera datos falsos para la tabla DailyStatus"""
    print(f"\n😊 Generando {NUM_RECORDS * 5} Estados Diarios...")

    status_types = ['HAPPY', 'SAD', 'NEUTRAL']
    observations = [
        'El residente se encontraba de buen humor',
        'Tuvo un día tranquilo',
        'Participó en actividades',
        'Comió bien',
        'Durmió adecuadamente',
        'Visitaron familiares',
        'Realizó ejercicio',
        'Vio a sus amigos',
        'Tuvo algunos dolores',
        'Se sentía nostálgico'
    ]

    daily_statuses = []
    count = 0

    for resident_id in residents:
        # Crear 5 registros por residente con fechas variadas
        for days_ago in range(1, 6):
            date = (datetime.now() - timedelta(days=days_ago)).date().isoformat()
            status = random.choice(status_types)
            observation = random.choice(observations)

            query = """
            INSERT INTO daily_status (date, id_resident, status_type, observations)
            VALUES (%s, %s, %s, %s)
            """

            try:
                cursor.execute(query, (date, resident_id, status, observation))
                daily_statuses.append(cursor.lastrowid)
                count += 1
                if count % 10 == 0:
                    print(f"   ✓ {count} estados diarios creados")
            except Error as e:
                print(f"   ⚠️  Error al insertar estado diario: {e}")

    print(f"   ✓ Total: {count} estados diarios creados")
    return daily_statuses

def show_statistics(cursor):
    """Muestra estadísticas de los datos insertados"""
    print("\n" + "="*60)
    print("📊 ESTADÍSTICAS DE DATOS GENERADOS")
    print("="*60)

    tables = {
        'resident': 'Residentes',
        'family': 'Familias',
        'family_member': 'Miembros de Familia',
        'daily_status': 'Estados Diarios',
        'family_resident': 'Relaciones Residente-Familia'
    }

    for table, label in tables.items():
        cursor.execute(f"SELECT COUNT(*) FROM {table}")
        count = cursor.fetchone()[0]
        print(f"   {label:.<40} {count:>6} registros")

    print("="*60)

def main():
    """Función principal"""
    print("\n" + "="*60)
    print("🚀 GENERADOR DE DATOS FALSOS - DAILYMOOD")
    print("="*60)

    connection = get_connection()
    if not connection:
        return

    try:
        cursor = connection.cursor()

        # Limpiar tablas existentes
        clear_tables(cursor)
        connection.commit()

        # Generar datos
        residents = generate_residents(cursor)
        connection.commit()

        families = generate_families(cursor)
        connection.commit()

        family_members = generate_family_members(cursor, families)
        connection.commit()

        link_residents_to_families(cursor, residents, families)
        connection.commit()

        daily_statuses = generate_daily_status(cursor, residents)
        connection.commit()

        # Mostrar estadísticas
        show_statistics(cursor)

        print("\n✅ ¡Datos generados exitosamente!")
        print("\nℹ️  Puedes acceder a la aplicación en: http://localhost:8080")

    except Error as e:
        print(f"\n❌ Error durante la ejecución: {e}")
        connection.rollback()

    finally:
        if connection.is_connected():
            cursor.close()
            connection.close()
            print("\n🔌 Conexión cerrada")

if __name__ == "__main__":
    main()

