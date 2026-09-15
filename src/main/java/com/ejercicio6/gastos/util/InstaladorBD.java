package com.ejercicio6.gastos.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Archivo: InstaladorBD.java
 * Ejecuta la creación de la base de datos, el usuario y corre el schema.sql.
 */
public class InstaladorBD {

    public static boolean instalar(String motor, String host, String puerto, String rootUsuario, String rootClave, 
                                   String nombreNuevaBD, String appUsuario, String appClave, String rutaAbsolutaEsquema) {
        Connection conexionRoot = null;
        Statement sentencia = null;
        boolean exito = false;

        try {
            String urlRoot = "";
            String driver = "";

            if ("MySQL".equals(motor)) {
                driver = "com.mysql.cj.jdbc.Driver";
                urlRoot = "jdbc:mysql://" + host + ":" + puerto + "/";
            } else if ("PostgreSQL".equals(motor)) {
                driver = "org.postgresql.Driver";
                urlRoot = "jdbc:postgresql://" + host + ":" + puerto + "/postgres";
            }

            Class.forName(driver);

            conexionRoot = DriverManager.getConnection(urlRoot, rootUsuario, rootClave);
            sentencia = conexionRoot.createStatement();

            if ("MySQL".equals(motor)) {
                sentencia.executeUpdate("CREATE DATABASE IF NOT EXISTS " + nombreNuevaBD);
                sentencia.executeUpdate("CREATE USER IF NOT EXISTS '" + appUsuario + "'@'localhost' IDENTIFIED BY '" + appClave + "'");
                sentencia.executeUpdate("CREATE USER IF NOT EXISTS '" + appUsuario + "'@'%' IDENTIFIED BY '" + appClave + "'");
                sentencia.executeUpdate("GRANT ALL PRIVILEGES ON " + nombreNuevaBD + ".* TO '" + appUsuario + "'@'localhost'");
                sentencia.executeUpdate("GRANT ALL PRIVILEGES ON " + nombreNuevaBD + ".* TO '" + appUsuario + "'@'%'");
                sentencia.executeUpdate("FLUSH PRIVILEGES");
                sentencia.executeUpdate("USE " + nombreNuevaBD);
            } else if ("PostgreSQL".equals(motor)) {
                try {
                    sentencia.executeUpdate("CREATE DATABASE " + nombreNuevaBD);
                } catch (SQLException e) {
                    System.out.println("La BD podria ya existir: " + e.getMessage());
                }
                try {
                    sentencia.executeUpdate("CREATE USER " + appUsuario + " WITH PASSWORD '" + appClave + "'");
                } catch (SQLException e) {
                    System.out.println("El usuario podria ya existir: " + e.getMessage());
                }
                sentencia.executeUpdate("GRANT ALL PRIVILEGES ON DATABASE " + nombreNuevaBD + " TO " + appUsuario);
                
                sentencia.close();
                conexionRoot.close();
                
                String urlNuevaBD = "jdbc:postgresql://" + host + ":" + puerto + "/" + nombreNuevaBD;
                conexionRoot = DriverManager.getConnection(urlNuevaBD, rootUsuario, rootClave);
                sentencia = conexionRoot.createStatement();
            }

            File archivoSQL = new File(rutaAbsolutaEsquema);
            if (archivoSQL.exists()) {
                BufferedReader lector = new BufferedReader(new FileReader(archivoSQL));
                String linea;
                StringBuilder constructorConsultas = new StringBuilder();

                while ((linea = lector.readLine()) != null) {
                    if (linea.trim().startsWith("--") || linea.trim().startsWith("/*") || linea.trim().isEmpty()) {
                        continue;
                    }
                    constructorConsultas.append(linea);
                    if (linea.trim().endsWith(";")) {
                        sentencia.executeUpdate(constructorConsultas.toString());
                        constructorConsultas.setLength(0);
                    }
                }
                lector.close();
            }

            GestorConfiguracion.guardarConfiguracion(motor, host, puerto, nombreNuevaBD, appUsuario, appClave);
            exito = true;

        } catch (Exception excepcion) {
            excepcion.printStackTrace();
        } finally {
            try {
                if (sentencia != null) sentencia.close();
                if (conexionRoot != null) conexionRoot.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return exito;
    }
}
