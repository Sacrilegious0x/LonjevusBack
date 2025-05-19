/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.daoImplements;

import cr.ac.ucr.ie.Lonjevus.Connection.ConnectionDB;
import cr.ac.ucr.ie.Lonjevus.dao.CaregiverDao;
import cr.ac.ucr.ie.Lonjevus.domain.Caregiver;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author User
 */
public class CaregiverDaoImplement implements CaregiverDao{

    @Override
    public LinkedList<Caregiver> getAll() {
        String sql = "call spGetCaregiver()";
        LinkedList<Caregiver> list = new LinkedList<>();
        
        try{
            Connection cn = ConnectionDB.getConnection();
            CallableStatement smtp = cn.prepareCall(sql);
            ResultSet rs = smtp.executeQuery();
            
            while(rs.next()){
                Caregiver c = new Caregiver();
                c.setId(rs.getInt(1));
                c.setIdentification(rs.getString(2));
                c.setName(rs.getString(3));    
                c.setSalary(rs.getDouble(4));               
                c.setEmail(rs.getString(5));
                c.setPassword(rs.getString(6));
                c.setShift(rs.getString(7));
                c.setPhotoUrl(rs.getString(8));
                boolean temp = rs.getBoolean(9);
                c.setIsActive(temp);
                c.setScheduleId(rs.getInt(10));
                if(temp){
                    list.add(c);
                }
                
            }
            
        }catch(SQLException e){
            System.err.println("Error al ejecutar la consulta "+e.getMessage());
        }
        
         return list;
    }

    @Override
    public void add(Caregiver t) {
       String sql = "call spAddCaregiver(?,?,?,?,?,?,?,?)";
       try{
            Connection cn = ConnectionDB.getConnection();
            CallableStatement smtp = cn.prepareCall(sql);
            smtp.setString(1, t.getIdentification());
            smtp.setString(2, t.getName());
            smtp.setDouble(3, t.getSalary());
            smtp.setString(4, t.getEmail());
            smtp.setString(5, t.getPassword());
            smtp.setString(6, t.getShift());
            smtp.setString(7, t.getPhotoUrl());
            smtp.setInt(8, t.getScheduleId());
            smtp.executeQuery();
       }catch(SQLException e){
            System.err.println("Error al ejecutar la consulta "+e.getMessage());
        }
    }

    @Override
    public void update(Caregiver t) {
         String sql = "call spUpdateCaregiver(?,?,?,?,?,?,?,?,?)";
       try{
            Connection cn = ConnectionDB.getConnection();
            CallableStatement smtp = cn.prepareCall(sql);
            smtp.setInt(1, t.getId());
            smtp.setString(2,t.getName());
            smtp.setString(3, t.getIdentification());
            smtp.setDouble(4, t.getSalary());
            smtp.setString(5, t.getEmail());
            smtp.setString(6, t.getPassword());
            smtp.setString(7, t.getShift());
            smtp.setString(8, t.getPhotoUrl());
            smtp.setInt(9, t.getScheduleId());
            smtp.executeQuery();
       }catch(SQLException e){
            System.err.println("Error al ejecutar la consulta "+e.getMessage());
        }
    }

    @Override
    public void deleteById(Integer y) {
        String sql = "call spCaregiverLogicalDelete(?)";
          try{
            Connection cn = ConnectionDB.getConnection();
            CallableStatement smtp = cn.prepareCall(sql);
            smtp.setInt(1, y);
            smtp.executeQuery();
        }catch(SQLException e){
            System.err.println("Error al ejecutar la consulta " + e.getMessage());
        }
   
    }

    @Override
    public Caregiver getById(Integer y) {
        
        String sql = "call spGetCaregiverById(?)";
       try{
         
            Connection cn = ConnectionDB.getConnection();
            
            CallableStatement smtp = cn.prepareCall(sql);
            smtp.setInt(1, y);
            ResultSet rs = smtp.executeQuery();
            while(rs.next()){
                Caregiver t = new Caregiver();
                 t.setId(rs.getInt(1));
                 t.setIdentification(rs.getString(2));
                 t.setName(rs.getString(3));
                 t.setSalary(rs.getInt(4));
                 t.setEmail(rs.getString(5));
                 t.setPassword(rs.getString(6));
                 t.setShift(rs.getString(7));
                 t.setPhotoUrl(rs.getString(8));
                 t.setIsActive(rs.getBoolean(9));
                 t.setScheduleId(rs.getInt(10));
                return t;
            }
            
       }catch(SQLException e){
            System.err.println("Error al ejecutar la consulta "+e.getMessage());
        }
        return null;
    }
    
}
