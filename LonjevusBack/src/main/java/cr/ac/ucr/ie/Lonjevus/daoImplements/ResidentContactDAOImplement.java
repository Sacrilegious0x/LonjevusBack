/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.daoImplements;

import cr.ac.ucr.ie.Lonjevus.Connection.ConnectionDB;
import cr.ac.ucr.ie.Lonjevus.dao.ResidentContactDAO;
import cr.ac.ucr.ie.Lonjevus.domain.Resident;
import cr.ac.ucr.ie.Lonjevus.domain.ResidentContact;
import cr.ac.ucr.ie.Lonjevus.service.ResidentService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author JOSHUACALETCESPEDESG
 */
public class ResidentContactDAOImplement implements ResidentContactDAO {

    @Override
    public LinkedList<ResidentContact> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public LinkedList<ResidentContact> getAll(int id) {
        LinkedList<ResidentContact> listContacts = new LinkedList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("call getContacts(?);");

        try {
            Connection cn = ConnectionDB.getConnection();
        if (cn == null) {
            System.out.println("ERROR: La conexion es NULL");
        }
            PreparedStatement ps = cn.prepareStatement(sql.toString());
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            ResidentContact residentContact;
            while (rs.next()) {
                residentContact = new ResidentContact();
                residentContact.setId(rs.getInt(1));
                residentContact.setName(rs.getString(2));
                residentContact.setPhoneNumber(rs.getString(3));
                residentContact.setRelationShip(rs.getString(4));

                Resident resident = ResidentService.findById(rs.getInt(5));
                residentContact.getResident().setId(resident.getId());
                residentContact.getResident().setName(resident.getName());
                residentContact.getResident().setIdentification(resident.getIdentification());
                residentContact.getResident().setAge(resident.getAge());
                residentContact.getResident().setHealthStatus(resident.getHealthStatus());
                residentContact.getResident().setNumberRoom(resident.getNumberRoom());
                residentContact.getResident().setPhoto(resident.getPhoto());

                listContacts.add(residentContact);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listContacts;
    }

    @Override
    public void add(ResidentContact t) {
        StringBuilder sql = new StringBuilder();
        sql.append("call addResidentContact(?, ?, ?, ?)");

        System.out.println("EL ID DEL RESIDENTE ES " + t.getResident().getId());
        try {
            Connection cn = ConnectionDB.getConnection();
            if (cn == null) {
                System.out.println("Error: la conexión es NULL en ConnectionDB.getConnection()");
            }

            Resident resident = ResidentService.findById(t.getIdResident());
            t.setResident(resident);

            PreparedStatement ps = cn.prepareCall(sql.toString());

            ps.setString(1, t.getName());
            ps.setString(2, t.getPhoneNumber());
            ps.setString(3, t.getRelationShip());
            ps.setInt(4, t.getResident().getId());
            ps.execute();
            System.out.println("contacto agregado");
        } catch (SQLException e) {
            System.out.println(sql.toString() + "\nNo sirvio el query\n" + e.getMessage());
        }
    }

    @Override
    public void deleteById(Integer id) {
        StringBuilder sql = new StringBuilder();
        sql.append("call deleteContact(?);");

        try {
            Connection cn = ConnectionDB.getConnection();
            if (cn == null) {
                System.out.println("Error: la conexión es NULL");
            }
            PreparedStatement ps = cn.prepareStatement(sql.toString());
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("eliminado");
        } catch (SQLException e) {
            System.out.println(sql.toString() + "\nNo sirvio el query\n" + e.getMessage());
        }
    }

    @Override
    public ResidentContact findById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(ResidentContact t) {
        StringBuilder sql = new StringBuilder();
        sql.append("call updateContact(?, ?, ?, ?)");

        try {
            Connection cn = ConnectionDB.getConnection();
            if (cn == null) {
                System.out.println("Error: la conexión es NULL en ConnectionDB.getConnection()");
            }
            PreparedStatement ps = cn.prepareCall(sql.toString());

            ps.setInt(1, t.getId());
            ps.setString(2, t.getName());
            ps.setString(3, t.getPhoneNumber());
            ps.setString(4, t.getRelationShip());

            ps.execute();
            System.out.println("contacto actualizado");
        } catch (SQLException e) {
            System.out.println(sql.toString() + "\nNo sirvio el query\n" + e.getMessage());
        }
    }

}
