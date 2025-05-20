/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.service;

import cr.ac.ucr.ie.Lonjevus.daoImplements.CaregiverDaoImplement;
import cr.ac.ucr.ie.Lonjevus.domain.Caregiver;
import java.util.LinkedList;

/**
 *
 * @author User
 */
public class CaregiverService {
    private static  CaregiverDaoImplement data = new  CaregiverDaoImplement();
    
    public LinkedList<Caregiver> getAll(){
        return data.getAll();
    }
    
    public void addCaregiver(Caregiver c){
           data.add(c);
    }
    
    public void updateCaregiver(Caregiver c){
        data.update(c);
    }
    
    public Caregiver getCaregiverById(int y){
        return data.findById(y);
    }
    
    public void deleteCaregiver(int y){
        data.deleteById(y);
    }
}
