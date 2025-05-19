/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.daoImplements;

import cr.ac.ucr.ie.Lonjevus.Connection.ConnectionDB;
import cr.ac.ucr.ie.Lonjevus.dao.ScheduleDao;
import cr.ac.ucr.ie.Lonjevus.domain.Schedule;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author User
 */
public class ScheduleDaoImplement implements ScheduleDao{

    @Override
    public LinkedList<Schedule> getAll() {
        LinkedList<Schedule> list = new LinkedList<>();
    
        return list; 
    }

    @Override
    public void add(Schedule t) {
        String sql = "call spAddSchedule(?,?,?,?,?)";
        try{
            Connection cn = ConnectionDB.getConnection();
            CallableStatement smtp = cn.prepareCall(sql);
            smtp.setString(1, t.getDays());
            smtp.setString(2, t.getEntryTime1());
            smtp.setString(3, t.getExitTime1());
            smtp.setString(4, t.getEntryTime2());
            smtp.setString(5, t.getExitTime2());
            smtp.executeQuery();
        }catch(SQLException e){
             System.err.println("Error al ejecutar la consulta "+ e.getMessage());
        }
    }

    @Override
    public void update(Schedule t) {
        String sql = "call spUpdateSchedule(?,?,?,?,?,?)";
        try{
            Connection cn = ConnectionDB.getConnection();
            CallableStatement smtp = cn.prepareCall(sql);
            smtp.setInt(1, t.getId());
            smtp.setString(2, t.getDays());
            smtp.setString(3, t.getEntryTime1());
            smtp.setString(4, t.getExitTime1());
            smtp.setString(5, t.getEntryTime2());
            smtp.setString(6, t.getExitTime2());
            smtp.executeQuery();
        }catch(SQLException e){
            System.err.println("Error al ejecutar la consulta "+ e.getMessage());
        }     
       
    }

    @Override
    public void deleteById(Integer y) {
        String sql = "call spDeleteSchedule(?)";
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
    public Schedule getById(Integer y) {
        String sql = "call spGetScheduleById(?)";
        try{
            Connection cn = ConnectionDB.getConnection();
            CallableStatement smtp = cn.prepareCall(sql);
            smtp.setInt(1, y);
            ResultSet rs = smtp.executeQuery();
            
            while(rs.next()){
                Schedule shd = new Schedule();
                shd.setId(y);
                shd.setDays(rs.getString(1));
                shd.setEntryTime1(rs.getString(2));
                shd.setExitTime1(rs.getString(3));
                shd.setEntryTime2(rs.getString(4));
                shd.setExitTime2(rs.getString(5));
                return shd;
            }
        }catch(SQLException e){
            System.err.println("Error al ejecutar la comsulta");
        }
        
        return null;
    }
    
    public int addAndReturnId(Schedule t) {
    String sql = "CALL spAddSchedule(?, ?, ?, ?, ?)";
    int generatedId = -1;

    try (Connection cn = ConnectionDB.getConnection();
         CallableStatement stmt = cn.prepareCall(sql)) {

        stmt.setString(1, t.getDays());
        stmt.setString(2, t.getEntryTime1());
        stmt.setString(3, t.getExitTime1());
        stmt.setString(4, t.getEntryTime2());
        stmt.setString(5, t.getExitTime2());

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            generatedId = rs.getInt("scheduleId");
        }

    } catch (SQLException e) {
        System.err.println("Error al ejecutar la consulta: " + e.getMessage());
    }

    return generatedId;
}
    
}
