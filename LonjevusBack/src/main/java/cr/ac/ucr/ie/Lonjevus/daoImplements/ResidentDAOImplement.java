package cr.ac.ucr.ie.Lonjevus.daoImplements;

import cr.ac.ucr.ie.Lonjevus.Connection.ConnectionDB;
import cr.ac.ucr.ie.Lonjevus.dao.ResidentDAO;
import cr.ac.ucr.ie.Lonjevus.domain.Resident;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author JOSHUACALETCESPEDESG
 */
public class ResidentDAOImplement implements ResidentDAO{

    @Override
    public LinkedList<Resident> getAll() {
        LinkedList<Resident> listResidents = new LinkedList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("call getResidents();");
        
        Connection cn = ConnectionDB.getConnection();
        if(cn == null){
            System.out.println("ERROR: La conexion es NULL");
        }
        PreparedStatement ps;
        try {
            ps = cn.prepareStatement(sql.toString());
            ResultSet rs = ps.executeQuery();
            Resident resident;
            while (rs.next()) {
                resident = new Resident();
                resident.setId(rs.getInt(1));
                resident.setIdentification(rs.getString(2));
                resident.setName(rs.getString(3));
                resident.setAge(rs.getInt(4));
                resident.setHealthStatus(rs.getString(5));
                resident.setNumberRoom(rs.getInt(6));
                resident.setPhoto(rs.getString(7));
                resident.setIsActive(rs.getBoolean(8));
                listResidents.add(resident);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listResidents;
    }

    @Override
    public void add(Resident r) {
        StringBuilder sql = new StringBuilder();
        sql.append("call insertResident(?, ?, ?, ?, ?, ?, ?)");
               
        try {
            Connection cn = ConnectionDB.getConnection();
            if (cn == null) {
                System.out.println("Error: la conexión es NULL en ConnectionDB.getConnection()");
            }
            CallableStatement stmt = cn.prepareCall(sql.toString());
            stmt.setString(1, r.getIdentification());
            stmt.setString(2, r.getName());
            stmt.setDate(3, java.sql.Date.valueOf(r.getBirthdate()));
            stmt.setString(4, r.getHealthStatus());
            stmt.setInt(5, r.getNumberRoom());
            stmt.setString(6, r.getPhoto());
            stmt.setBoolean(7, r.isIsActive());
            
            stmt.execute();
            System.out.println("agregado");
        } catch (SQLException e) {
            System.out.println(sql.toString() + "\nNo sirvio el query\n" + e.getMessage());
        }
    }

    @Override
    public void deleteById(int id) {
        StringBuilder sql = new StringBuilder();
        sql.append("call deleteResident(?);");

        try {
            Connection cn = ConnectionDB.getConnection();
            if (cn == null) {
                System.out.println("Error: la conexión es NULL en ConnectionDB.getConnection()");
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
    public Resident findById(int id) {
        StringBuilder sql = new StringBuilder();
        sql.append("call getResidentById(?)");
        Resident resident = new Resident();
        Connection cn = ConnectionDB.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(sql.toString());
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                resident.setId(rs.getInt(1));
                resident.setIdentification(rs.getString(2));
                resident.setName(rs.getString(3));
                resident.setAge(rs.getInt(4));
                resident.setHealthStatus(rs.getString(5));
                resident.setNumberRoom(rs.getInt(6));
                resident.setPhoto(rs.getString(7));
                resident.setIsActive(rs.getBoolean(8));
            }
        } catch (SQLException ex) {
            System.out.println("Fallo la query" + sql.toString());
        }
        
        return resident;
    }

    @Override
    public void update(Resident t) {
        StringBuilder sql = new StringBuilder();
        sql.append("call updateResident (?,?,?,?,?,?,?,?)");
        
        Connection cn = ConnectionDB.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(sql.toString());
            ps.setInt(1, t.getId());
            ps.setString(2, t.getIdentification());
            ps.setString(3, t.getName());
            ps.setDate(4, java.sql.Date.valueOf(t.getBirthdate()));
            ps.setString(5, t.getHealthStatus());
            ps.setInt(6, t.getNumberRoom());
            ps.setString(7, t.getPhoto());
            ps.setBoolean(8, t.isIsActive());
            ps.execute();
            System.out.println("LA QUERY\n"+sql.toString());
            System.out.println("Residente actualizado");
        } catch (SQLException ex) {
            System.out.println("Fallo la query" + sql.toString());
        }
        
    }
    
}
