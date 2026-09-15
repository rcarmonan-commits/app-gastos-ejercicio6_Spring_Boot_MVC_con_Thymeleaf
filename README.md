# Aplicación de Gastos - Spring Boot MVC + Thymeleaf

## Ficha de Entrega

| Campo | Información |
|---|---|
| **Estudiante** | Rosary Carmona |
| **Datos académicos** | Desarrollo Web \| Semestre IV |
| **Actividad** | Spring Boot MVC con Thymeleaf: desarrollo web basado en framework |
| **Ejercicio asignado** | Número 6 - Gasto |
| **Guía utilizada** | Guía de Spring Web MVC |
| **Código fuente** | [Enlace al Repositorio en GitHub](https://github.com/rcarmonan-commits/app-gastos-ejercicio6_Spring_Boot_MVC_con_Thymeleaf) |
| **Sustentación** | `[ENLACE_A_TU_VIDEO_AQUI]` |
| **Aplicación desplegada** | [http://app-gastos-ejercicio6.alwaysdata.net/](http://app-gastos-ejercicio6.alwaysdata.net/) |

---
Este proyecto es el resultado de la **Unidad 2** de la asignatura Desarrollo Web. Cumple con la migración estricta desde un entorno Servlets hacia un entorno moderno basado en el framework **Spring Boot MVC**.

## Requisitos Previos y Entorno
- **Versión de Java:** Java 17 (o superior).
- **IDE Recomendado:** Eclipse, IntelliJ IDEA o VS Code (con Spring Boot Extension Pack).
- **Motor de Base de Datos:** MariaDB o MySQL instalado localmente (o acceso a uno remoto).

## Configuración de Base de Datos y Scripts
El proyecto incluye un **Asistente de Instalación (Setup Wizard)** integrado.
- **No es necesario pre-configurar archivos de propiedades con credenciales**.
- Al iniciar la aplicación por primera vez, si no existe el archivo de configuración `db_config.properties` en su directorio local (`${user.home}`), el sistema redirigirá automáticamente a una pantalla de instalación (Instalador).
- Desde el Instalador web podrá ingresar las credenciales root de su motor de base de datos, y el sistema automáticamente creará la base de datos `app_gastos_db`, un usuario seguro para la app y ejecutará los scripts necesarios.
- Gracias a **Spring Data JPA**, el esquema se actualiza automáticamente.

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
