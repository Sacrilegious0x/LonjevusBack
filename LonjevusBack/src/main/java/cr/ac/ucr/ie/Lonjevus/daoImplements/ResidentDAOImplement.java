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
            stmt.setInt(3, r.getAge());
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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void findById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
