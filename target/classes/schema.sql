/*
* Archivo de script SQL (schema.sql)
* Justificación metodológica: Como estudiante, centralizo la creación de la base de datos
* en este script para que el instalador lo lea y ejecute automáticamente, facilitando
* el despliegue dinámico en cualquier motor sin configuración manual.
*/

CREATE TABLE usuarios (
    id VARCHAR(50) PRIMARY KEY,
    clave VARCHAR(255) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(150),
    rol VARCHAR(50) NOT NULL
);

CREATE TABLE gastos (
    id_gasto SERIAL PRIMARY KEY, -- En MySQL se adaptará a AUTO_INCREMENT, y en PostgreSQL es SERIAL
    fecha DATE NOT NULL,
    valor_total_sin_iva DOUBLE PRECISION NOT NULL,
    iva_total DOUBLE PRECISION NOT NULL,
    valor_total_con_iva DOUBLE PRECISION NOT NULL,
    nombre_usuario VARCHAR(50) NOT NULL,
    lugar VARCHAR(150) NOT NULL,
    descripcion TEXT,
    CONSTRAINT fk_usuario FOREIGN KEY (nombre_usuario) REFERENCES usuarios(id)
);

CREATE TABLE configuracion_smtp (
    id INT PRIMARY KEY,
    host VARCHAR(100) NOT NULL,
    puerto INT NOT NULL,
    usuario VARCHAR(100) NOT NULL,
    clave VARCHAR(255) NOT NULL,
    remitente VARCHAR(100) NOT NULL
);

-- Datos por defecto (opcional)
INSERT INTO usuarios (id, clave, nombre, rol) VALUES ('admin', 'admin123', 'Administrador Principal', 'Administrador');
