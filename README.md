# Aplicación de Gastos - Spring Boot MVC + Thymeleaf

Este proyecto es la migración de la aplicación Java EE original hacia un entorno moderno basado en **Spring Boot**, cumpliendo con la **Unidad 2** de la asignatura Desarrollo Web.

## Características Técnicas
- **Framework Principal:** Spring Boot 3
- **Arquitectura:** Modelo-Vista-Controlador (MVC)
- **Motor de Plantillas:** Thymeleaf
- **Persistencia:** Spring Data JPA + Hibernate
- **Base de Datos:** MySQL
- **Seguridad:** Interceptores personalizados (AuthInterceptor)

## Estructura de Capas
- `model`: Entidades JPA mapeadas a las tablas de la BD (`Usuario`, `Gasto`).
- `repository`: Interfaces que extienden `JpaRepository` para el manejo automático de consultas SQL.
- `service`: Capa de lógica de negocio (`UsuarioService`, `GastoService`, `EmailService`).
- `controller`: Controladores Web que manejan las peticiones HTTP y renderizan las vistas Thymeleaf (`AuthController`, `UsuarioController`, `GastoController`).

## Configuración y Ejecución
1. **Base de Datos**: 
   Asegúrese de tener MySQL corriendo. La aplicación está configurada en `src/main/resources/application.properties` con las siguientes credenciales:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/app_gastos_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
   spring.datasource.username=root
   spring.datasource.password=
   ```
   *Nota: Spring Data JPA (`ddl-auto=update`) creará automáticamente las tablas basándose en las entidades.*
   
2. **Correo Electrónico (Recuperación de Clave)**:
   Debe configurar credenciales SMTP válidas en `application.properties` para que el envío de correos funcione.

3. **Ejecución**:
   El proyecto es estándar Maven. Puede ejecutarlo usando su IDE (Eclipse, IntelliJ, VSCode) o mediante la línea de comandos si tiene Maven instalado:
   ```bash
   mvn spring-boot:run
   ```
   La aplicación estará disponible en `http://localhost:8080`.
