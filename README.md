# Aplicación de Gastos - Spring Boot MVC + Thymeleaf

Este proyecto es el resultado de la **Unidad 2** de la asignatura Desarrollo Web. Cumple con la migración estricta desde un entorno Servlets hacia un entorno moderno basado en el framework **Spring Boot MVC**.

## Requisitos Previos y Entorno
- **Versión de Java:** Java 17 (o superior).
- **IDE Recomendado:** Eclipse, IntelliJ IDEA o VS Code (con Spring Boot Extension Pack).
- **Conexión a Internet:** Requerida para que la aplicación se conecte a la base de datos remota.

## Configuración de Base de Datos y Scripts
El proyecto utiliza un servicio remoto y gratuito de bases de datos llamado **AlwaysData**. 
- **No es necesario instalar MySQL localmente**.
- Las credenciales están preconfiguradas de manera dinámica en el archivo `src/main/resources/application.properties`.
- **Creación de BD:** El repositorio incluye el archivo `src/main/resources/schema.sql` (opcionalmente) que contiene la estructura original de las tablas, sin embargo, gracias a **Spring Data JPA**, las entidades se mapean de forma automática hacia la base de datos remota mediante la propiedad `spring.jpa.hibernate.ddl-auto=update`.
- El sistema cuenta con las tablas obligatorias: `usuario`, `gasto` (ejercicio 6 asignado) y `configuracion_smtp`.

## Configuración de Variables Necesarias (Correo SMTP)
A diferencia de configuraciones estáticas, el sistema de recuperación de claves por correo es 100% dinámico. Las variables necesarias para el servidor de correo se obtienen directamente de la tabla `configuracion_smtp`. 
- Un Administrador puede editar esta configuración ingresando a la aplicación y navegando al menú "Configuración SMTP".

## Estructura de Arquitectura por Capas
El proyecto sigue estrictamente el flujo MVC requerido:
1. **Model (`com.ejercicio6.gastos.model`):** Entidades JPA mapeadas a las tablas (`Usuario`, `Gasto`, `ConfiguracionSMTP`).
2. **Repository (`com.ejercicio6.gastos.repository`):** Encapsula el acceso a datos mediante `JpaRepository`.
3. **Service (`com.ejercicio6.gastos.service`):** Centraliza la lógica de negocio.
4. **Controller (`com.ejercicio6.gastos.controller`):** Recibe las solicitudes HTTP, prepara los datos y devuelve las plantillas Thymeleaf. El control de sesión se gestiona con `AuthInterceptor`.
5. **Vistas (`src/main/resources/templates`):** Plantillas HTML procesadas del lado del servidor con Thymeleaf.

## Instrucciones para Ejecutar la Aplicación
1. Clonar el repositorio en su máquina local.
2. Abrir el proyecto en su IDE favorito como un proyecto **Maven** existente.
3. Esperar a que Maven descargue las dependencias.
4. Ejecutar la clase principal `GastosApplication.java` como una aplicación Java (o aplicación Spring Boot).
5. (Alternativa) Si tiene Maven instalado en su línea de comandos, ejecute: `mvn spring-boot:run`
6. Abrir el navegador e ingresar a la URL: `http://localhost:8080/`

## Usuarios de Prueba (Datos Iniciales)
Puede iniciar sesión con los siguientes datos (si existen previamente en la BD remota, o crear uno nuevo):
- **ID:** (Debe crear un usuario o probar registrarse)
- **Clave:** (La que asigne al registrarse)
