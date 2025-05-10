/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.daoImplements;

import cr.ac.ucr.ie.Lonjevus.Connection.ConnectionDB;
import cr.ac.ucr.ie.Lonjevus.dao.AdminDao;
import cr.ac.ucr.ie.Lonjevus.domain.Admin;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author User
 */
public class AdminDaoImplement implements AdminDao{

    @Override
    public LinkedList<Admin> getAll() {
        LinkedList<Admin> list = new LinkedList<>();
        Admin admin = new Admin();
        String sqlPs = ""; //Llamar procedimiento de almacenado
        try{
            Connection cn = ConnectionDB.getConnection();
            CallableStatement smtp = cn.prepareCall(sqlPs);
            
            ResultSet rs = smtp.executeQuery();
            
            while(rs.next()){
                admin.setId(rs.getInt(1));
                admin.setIdentification(rs.getString(2));
                admin.setName(rs.getString(3));
                //....
            }
        }catch(SQLException e){
            System.err.println("Error al ejecutar la consulta " + e);
            
        }
        return list;
    }

    @Override
    public void add(Admin t) {
        
    }

    @Override
    public void update(Admin t) {
        
    }

    @Override
    public void deleteById(Integer y) {
       
    }

    @Override
    public Admin getById(Integer y) {
        return null;
    }
    
}
