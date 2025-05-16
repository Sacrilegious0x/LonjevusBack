/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author User
 */
public class ConnectionDB {
    
    private static final String DATABASE="LongevusDB";
    private static final String USER= "root";
    private static final String PASS="";
    private static final int PORT= 3399;
    private static final String HOST= "localhost";
    private static final String URL="jdbc:mysql://"+HOST+":"+PORT+"/"+DATABASE;
    
    private static Connection connect;
    
    public static Connection getConnection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            System.out.println("No se encontro la clase: "+e.getMessage());
        }
        try{
            connect= DriverManager.getConnection(URL,USER,PASS);
            System.out.println("Se conecto a la base de datos");
        }catch (SQLException e){
            System.out.println("No se conecto a la base de datos");
        }
        return connect;
    }
    
}
