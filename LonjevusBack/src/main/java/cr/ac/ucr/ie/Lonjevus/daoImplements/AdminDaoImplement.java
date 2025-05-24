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
public class AdminDaoImplement implements AdminDao {

    @Override
    public LinkedList<Admin> getAll() {
        LinkedList<Admin> list = new LinkedList<>();

        return list;
    }

    @Override
    public void add(Admin t) {
        String sql = "call spAddAdmin(?,?,?,?,?,?,?,?)";
        try {
            Connection cn = ConnectionDB.getConnection();
            CallableStatement smtp = cn.prepareCall(sql);
            smtp.setString(1, t.getIdentification());
            smtp.setString(2, t.getName());
            smtp.setDouble(3, t.getSalary());
            smtp.setString(4, t.getEmail());
            smtp.setString(5, t.getPassword());
            smtp.setString(6, t.getOfficeContact());
            smtp.setString(7, t.getPhotoUrl());
            smtp.setInt(8, t.getScheduleId());
            smtp.executeQuery();
        } catch (SQLException e) {
            System.err.println("Error al ejecutar la consulta " + e.getMessage());
        }

    }

    @Override
    public void update(Admin t) {
        String sql = "call spUpdateAdmin(?,?,?,?,?,?,?,?)";
        try {
            Connection cn = ConnectionDB.getConnection();
            CallableStatement smtp = cn.prepareCall(sql);
            smtp.setInt(1, t.getId());
            smtp.setString(2, t.getIdentification());
            smtp.setString(3, t.getName());
            smtp.setDouble(4, t.getSalary());
            smtp.setString(5, t.getEmail());
            smtp.setString(6, t.getOfficeContact());
            smtp.setString(7, t.getPhotoUrl());
            smtp.setInt(8, t.getScheduleId());
            smtp.executeQuery();
        } catch (SQLException e) {
            System.err.println("Error al ejecutar la consulta " + e.getMessage());
        }
    }

    @Override
    public void deleteById(Integer y) {
        String sql = "call spCaregiverLogicalDelete(?)";
        try {
            Connection cn = ConnectionDB.getConnection();
            CallableStatement smtp = cn.prepareCall(sql);
            smtp.setInt(1, y);
            smtp.executeQuery();
        } catch (SQLException e) {
            System.err.println("Error al ejecutar la consulta " + e.getMessage());
        }
    }

    @Override
    public Admin findById(Integer y) {
        String sql = "call spGetAdmin(?)";
        try {
            Connection cn = ConnectionDB.getConnection();
            CallableStatement smtp = cn.prepareCall(sql);
            smtp.setInt(1, y);
            ResultSet rs = smtp.executeQuery();
            while (rs.next()) {
                Admin a = new Admin();
                a.setId(rs.getInt(1));
                a.setIdentification(rs.getString(2));
                a.setName(rs.getString(3));
                a.setSalary(rs.getInt(4));
                a.setEmail(rs.getString(5));
                a.setPassword(rs.getString(6));
                a.setOficeContact(rs.getString(7));
                a.setPhotoUrl(rs.getString(8));
                a.setScheduleId(rs.getInt(9));
                a.setIsActive(rs.getBoolean(10));
                return a;
            }
        } catch (SQLException e) {
            System.err.println("Error al ejecutar la consutal" + e.getMessage());
        }
        return null;
    }

    public Admin findByEmail(String email) {
        String sql = "call spGetAdminByEmail(?)";
        Admin admin = null;
        try (Connection cn = ConnectionDB.getConnection(); CallableStatement smtp = cn.prepareCall(sql)) {

            smtp.setString(1, email);
            try (ResultSet rs = smtp.executeQuery()) {
                if (rs.next()) { // Si se encontró un admin con ese email
                    admin = new Admin();
                    admin.setId(rs.getInt(1)); // Usa nombres de columna, es más seguro
                    admin.setIdentification(rs.getString(2));
                    admin.setName(rs.getString(3));
                    admin.setSalary(rs.getDouble(4));
                    admin.setEmail(rs.getString(5));
                    admin.setPassword(rs.getString(6));
                    admin.setOficeContact(rs.getString(7));
                    admin.setPhotoUrl(rs.getString(8));
                    admin.setScheduleId(rs.getInt(9));
                    admin.setIsActive(rs.getBoolean(10));
                    return admin;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar admin por email: " + e.getMessage());
           
        }
        return null;
    }
}
