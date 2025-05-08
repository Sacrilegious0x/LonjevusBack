/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class ConnectionDB {
    private final static String DATABASE = "LongevusDB";
    private final static String USER = "root";
    private final static String PASS = "";
    private final static int PORT = 3306;
    private final static String HOST = "localhost";
    private final static String URL = "jdbc:mysql://"+HOST+":"+PORT+"/"+DATABASE;
    
    private static Connection connect;
    
    public static Connection getConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Error al importar la clase");
        }
        
        try {
            connect = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Conexión establecida");
        } catch (SQLException ex) {
            System.out.println("Error de conexión");
        }
        
        return connect;
    }
}
