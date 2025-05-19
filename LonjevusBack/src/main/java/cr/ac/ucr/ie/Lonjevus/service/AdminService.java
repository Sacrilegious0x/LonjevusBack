/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.service;

import cr.ac.ucr.ie.Lonjevus.daoImplements.AdminDaoImplement;
import cr.ac.ucr.ie.Lonjevus.domain.Admin;
import java.util.LinkedList;

/**
 *
 * @author User
 */
public class AdminService {
    private static  AdminDaoImplement data = new  AdminDaoImplement();
     
    public LinkedList<Admin> getAll(){
        return data.getAll();
    }
    
    public void addAdmin(Admin a){
           data.add(a);
    }
    
    public void updateAdmin(Admin a){
        data.update(a);
    }
    
    public Admin getAdminById(int y){
        return data.getById(y);
    }
    
    public void deleteAdmin(int y){
        data.deleteById(y);
    }
    public Admin login(String email, String plainPassword) {
    Admin admin = data.findByEmail(email); 

        if (admin != null && admin.isIsActive()) {

            if (admin.getPassword().equals(plainPassword)) {
                return admin; 
            }
        }
        return null; 
    }
    
}
