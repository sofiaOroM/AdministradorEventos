/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template

package com.mycompany.backend.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author sofia
 */

/*public class DBConnectionSingleton {

    private static final String IP = "localhost";
    private static final int PUERTO = 3306;
    private static final String SCHEMA = "congresosdb";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "sofia2808";
    private static final String URL = "jdbc:mysql://" + IP + ":" + PUERTO + "/" + SCHEMA;

    private static DBConnectionSingleton instance;

    private Connection connection;

    private DBConnectionSingleton() {
        try {
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (SQLException e) {
            // manejamos la exception
            System.out.println("Error al conectarse");
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static DBConnectionSingleton getInstance() {
        if (instance == null) {
            instance = new DBConnectionSingleton();
        }
        return instance;
    }

}*/

package com.mycompany.backend.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionSingleton {

    private static final String IP = "localhost";
    private static final int PUERTO = 3306;
    private static final String SCHEMA = "congresosdb";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "sofia2808";

    // URL con parámetros recomendados para MySQL 8
    private static final String URL = "jdbc:mysql://" + IP + ":" + PUERTO + "/" + SCHEMA
            + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    private static volatile DBConnectionSingleton instance;
    private Connection connection;

    private DBConnectionSingleton() {
    }

    public static DBConnectionSingleton getInstance() {
        if (instance == null) {
            synchronized (DBConnectionSingleton.class) {
                if (instance == null) {
                    instance = new DBConnectionSingleton();
                }
            }
        }
        return instance;
    }

    /** Nunca retornar null. Si no puede abrir, lanza SQLException con la causa real. */
    public synchronized Connection getConnection() throws SQLException {
        try {
            // Carga explícita del driver (útil en algunos contenedores)
            Class.forName("com.mysql.cj.jdbc.Driver");

            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            }
            return connection;
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL no encontrado: com.mysql.cj.jdbc.Driver. "
                    + "Agrega mysql-connector-j en WEB-INF/lib o en pom.xml.", e);
        } catch (SQLException e) {
            throw new SQLException("No se pudo abrir conexión a MySQL. "
                    + "Revisa URL/usuario/clave/BD. URL=" + URL, e);
        }
    }
}
