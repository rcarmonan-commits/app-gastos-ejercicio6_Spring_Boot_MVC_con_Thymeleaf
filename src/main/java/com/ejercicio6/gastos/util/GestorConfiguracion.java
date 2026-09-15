package com.ejercicio6.gastos.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Archivo: GestorConfiguracion.java
 * Gestiona el archivo de configuración local generado por el asistente de instalación.
 * Escribe las propiedades en formato estándar de Spring Boot para que puedan ser leídas 
 * por la aplicación mediante spring.config.additional-location.
 */
public class GestorConfiguracion {

    public static final String RUTA_ARCHIVO = System.getProperty("user.home") + File.separator + "db_config.properties";

    public static void guardarConfiguracion(String motor, String host, String puerto, String nombreBD, String usuario, String clave) throws IOException {
        Properties propiedades = new Properties();
        
        String url = "";
        String driver = "";
        
        if ("MySQL".equals(motor)) {
            url = "jdbc:mysql://" + host + ":" + puerto + "/" + nombreBD;
            driver = "com.mysql.cj.jdbc.Driver";
        } else if ("PostgreSQL".equals(motor)) {
            url = "jdbc:postgresql://" + host + ":" + puerto + "/" + nombreBD;
            driver = "org.postgresql.Driver";
        }
        
        propiedades.setProperty("spring.datasource.url", url);
        propiedades.setProperty("spring.datasource.username", usuario);
        propiedades.setProperty("spring.datasource.password", clave);
        propiedades.setProperty("spring.datasource.driver-class-name", driver);
        propiedades.setProperty("app.installed", "true"); // Marca para saber que fue instalado vía web

        FileOutputStream salida = new FileOutputStream(RUTA_ARCHIVO);
        propiedades.store(salida, "Configuracion de Base de Datos Generada por Asistente");
        salida.close();
    }

    public static boolean estaConfigurado() {
        File archivo = new File(RUTA_ARCHIVO);
        return archivo.exists();
    }
}
