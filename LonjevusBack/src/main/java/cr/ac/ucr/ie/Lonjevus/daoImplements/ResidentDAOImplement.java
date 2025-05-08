package cr.ac.ucr.ie.Lonjevus.daoImplements;

import cr.ac.ucr.ie.Lonjevus.Connection.ConnectionDB;
import cr.ac.ucr.ie.Lonjevus.dao.ResidentDAO;
import cr.ac.ucr.ie.Lonjevus.domain.Resident;
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
        sql.append("SELECT id, identification, name, age, healthStatus, numberRoom, photo, isActive FROM resident");
        
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
                resident.setRoomNumber(rs.getInt(6));
                resident.setPhoto(rs.getString(7));
                resident.setIsActive(rs.getBoolean(8));
                listResidents.add(resident);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listResidents;
    }
    
}
