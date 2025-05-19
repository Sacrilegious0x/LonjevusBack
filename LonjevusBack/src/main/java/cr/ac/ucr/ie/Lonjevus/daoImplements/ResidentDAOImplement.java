package cr.ac.ucr.ie.Lonjevus.daoImplements;

import cr.ac.ucr.ie.Lonjevus.Connection.ConnectionDB;
import cr.ac.ucr.ie.Lonjevus.dao.ResidentDAO;
import cr.ac.ucr.ie.Lonjevus.domain.Resident;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;

/**
 *
 * @author JOSHUACALETCESPEDESG
 */
public class ResidentDAOImplement implements ResidentDAO {

    @Override
    public LinkedList<Resident> getAll() {
        LinkedList<Resident> listResidents = new LinkedList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("call getResidents();");

        try {
            Connection cn = ConnectionDB.getConnection();
            if (cn == null) {
                System.out.println("ERROR: La conexion es NULL");
            }
            PreparedStatement ps = cn.prepareStatement(sql.toString());
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
                resident.setBirthdate(LocalDate.parse(rs.getString(8)));
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
        sql.append("call addResident(?, ?, ?, ?, ?, ?)");

        try {
            Connection cn = ConnectionDB.getConnection();
            if (cn == null) {
                System.out.println("Error: la conexión es NULL en ConnectionDB.getConnection()");
            }
            PreparedStatement ps = cn.prepareCall(sql.toString());
            ps.setString(1, r.getIdentification());
            ps.setString(2, r.getName());
            ps.setDate(3, java.sql.Date.valueOf(r.getBirthdate()));
            ps.setString(4, r.getHealthStatus());
            ps.setInt(5, r.getNumberRoom());
            ps.setString(6, r.getPhoto());

            ps.execute();
            System.out.println("agregado");
        } catch (SQLException e) {
            System.out.println(sql.toString() + "\nNo sirvio el query\n" + e.getMessage());
        }
    }

    @Override
    public void deleteById(Integer id) {
        StringBuilder sql = new StringBuilder();
        sql.append("call deleteResident(?);");

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
    public Resident findById(Integer id) {
        StringBuilder sql = new StringBuilder();
        sql.append("call getResidentById(?)");
        Resident resident = new Resident();
        try {
            Connection cn = ConnectionDB.getConnection();
            PreparedStatement ps = cn.prepareStatement(sql.toString());
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                resident.setId(rs.getInt(1));
                resident.setIdentification(rs.getString(2));
                resident.setName(rs.getString(3));
                resident.setAge(rs.getInt(4));
                resident.setHealthStatus(rs.getString(5));
                resident.setNumberRoom(rs.getInt(6));
                resident.setPhoto(rs.getString(7));
                resident.setIsActive(rs.getBoolean(8));
                resident.setBirthdate(LocalDate.parse(rs.getString(9)));
            }
        } catch (SQLException ex) {
            System.out.println("Fallo la query" + sql.toString());
        }

        return resident;
    }

    @Override
    public void update(Resident t) {
        StringBuilder sql = new StringBuilder();
        sql.append("call updateResident (?,?,?,?,?,?,?)");

        try {
            Connection cn = ConnectionDB.getConnection();
            PreparedStatement ps = cn.prepareStatement(sql.toString());
            ps.setInt(1, t.getId());
            ps.setString(2, t.getIdentification());
            ps.setString(3, t.getName());
            ps.setDate(4, java.sql.Date.valueOf(t.getBirthdate()));
            ps.setString(5, t.getHealthStatus());
            ps.setInt(6, t.getNumberRoom());
            ps.setString(7, t.getPhoto());
            ps.execute();
            System.out.println("LA QUERY\n" + sql.toString());
            System.out.println("Residente actualizado");
        } catch (SQLException ex) {
            System.out.println("Fallo la query" + sql.toString());
        }

    }

    @Override
    public LinkedList<Resident> getByNameorId(String value) {
        StringBuilder sql = new StringBuilder();
        sql.append("call getResidentByNameOrIdentification(?)");
        LinkedList<Resident> list = new LinkedList<>();

        try {
            Connection cn = ConnectionDB.getConnection();
            PreparedStatement ps = cn.prepareStatement(sql.toString());
            ps.setString(1, value);
            ResultSet rs = ps.executeQuery();
            Resident resident;
            while (rs.next()) {
                resident = new Resident();
                resident.setId(rs.getInt(1));
                resident.setIdentification(rs.getString(2));
                resident.setName(rs.getString(3));
                resident.setBirthdate(LocalDate.parse(rs.getString(4)));
                resident.setAge(rs.getInt(5));
                resident.setHealthStatus(rs.getString(6));
                resident.setNumberRoom(rs.getInt(7));
                resident.setPhoto(rs.getString(8));
                list.add(resident);
            }
        } catch (SQLException ex) {
            System.out.println("Fallo la query" + sql.toString());
        }

        return list;
    }

}
