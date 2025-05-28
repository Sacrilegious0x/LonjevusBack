/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.daoImplements;

import cr.ac.ucr.ie.Lonjevus.Connection.ConnectionDB;
import cr.ac.ucr.ie.Lonjevus.dao.TaskDao;
import cr.ac.ucr.ie.Lonjevus.domain.Task;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author User
 */
public class TaskDaoImplement implements TaskDao {
    
    @Override
    public LinkedList<Task> getAll() {
        String sql = "call spGetTask()";
        LinkedList<Task> list = new LinkedList<>();
        
        try{
            Connection cn = ConnectionDB.getConnection();
            CallableStatement smtp = cn.prepareCall(sql);
            ResultSet rs = smtp.executeQuery();
            
            while(rs.next()){
                Task t = new Task();
                t.setId(rs.getInt(1));
                t.setCaregiver(rs.getInt(2));
                t.setDescription(rs.getString(3));
                list.add(t);
            }
            
        }catch(SQLException e){
            System.err.println("Error al ejecutar la consulta "+e.getMessage());
        }
        
         return list;
    }

    @Override
    public void add(Task t) {
        String sql = "call spAddTask(?,?)";
        try{
           Connection cn = ConnectionDB.getConnection();
            CallableStatement smtp = cn.prepareCall(sql);
            smtp.setInt(1, 1);//t.getCaregiver().getId()
            smtp.setString(2, t.getDescription());
            smtp.executeQuery();
        }catch(SQLException e){
            System.err.println("Error al ejecutar la consutlta " + e.getMessage());
        }
         
    }

    @Override
    public void update(Task t) {
        String sql = "call spUpdateTask(?,?)";
        try{
            Connection cn = ConnectionDB.getConnection();
            CallableStatement smtp = cn.prepareCall(sql);
            smtp.setInt(1, t.getId());
            smtp.setString(2, t.getDescription());
            smtp.executeQuery();
        }catch(SQLException e){
            System.err.println("Error al ejecutar la consulta "+ e.getMessage());
        }         
        
    }

    @Override
    public void deleteById(Integer y) {
       String sql = "call spDeleteTask(?)";
       try{
           Connection cn = ConnectionDB.getConnection();
           CallableStatement smtp = cn.prepareCall(sql);
           smtp.setInt(1, y);
           smtp.executeQuery();
       }catch(SQLException e){
           System.err.println("Error al ejecutar la consulta " +e.getMessage());
       }
    }

    @Override
    public Task findById(Integer y) {
        String sql = "call spGetTaskById(?)";
        try{
            Connection cn = ConnectionDB.getConnection();
            CallableStatement smtp = cn.prepareCall(sql);
            smtp.setInt(1, y);
            ResultSet rs = smtp.executeQuery();
            while(rs.next()){
                Task t = new Task();
                t.setId(rs.getInt(1));
                t.setCaregiver(rs.getInt(2));
                t.setDescription(rs.getString(3));
                return t;
            }
        }catch(SQLException e){
            System.err.println("Error al ejecutar la consulta " + e.getMessage());
        }
        
      return null;
    }
    
}
