/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.service;

import cr.ac.ucr.ie.Lonjevus.daoImplements.ResidentContactDAOImplement;
import cr.ac.ucr.ie.Lonjevus.domain.ResidentContact;
import java.util.LinkedList;

/**
 *
 * @author JOSHUACALETCESPEDESG
 */
public class ResidentContactService {
    private static ResidentContactDAOImplement r = new ResidentContactDAOImplement();
    
    public static LinkedList<ResidentContact> getAll(int id){
        return r.getAll(id);
    }
    
    public static void addContact(ResidentContact rC){
        r.add(rC);
    }
    
    public static void updateContact(ResidentContact rC){
        r.add(rC);
    }
}
