package com.example.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConfigDB {
    static Connection objConnection = null;

    public static Connection openConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://127.0.0.1:3306/RENTAL_MACHINE";
            String user = "root";
            String password = "tu_contraseña";
            objConnection = (Connection) DriverManager.getConnection(url, user, password);
            System.out.println("Me conecte perfectamente");

        } catch (ClassNotFoundException error) {
            System.out.println("ERROR >> Driver no instalado" + error.getMessage());
        } catch (SQLException error) {
            System.out.println("ERROR >> error al conectar la base de datos" + error.getMessage());
        }
        return objConnection;
    }

    public static void closeConnection() {
        try {
            // Si hay una conexion actica entonces la cierra
            if (objConnection != null)
                objConnection.close();
            System.out.println("Se finalizo la conexion con exito");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}