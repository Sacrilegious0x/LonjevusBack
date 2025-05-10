/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.domain;

/**
 *
 * @author User
 */
public class Admin extends Person {
    private int oficeContact;
    
    public Admin(){
        
    }

    public Admin(int id, String identification, String name, String email, String password, String photo, Schedule schedule, int oficeContact) {
        super(id, identification, name, email, password, photo, schedule);
        this.oficeContact = oficeContact;
    }   

    public int getOficeContact() {
        return oficeContact;
    }

    public void setOficeContact(int oficeContact) {
        this.oficeContact = oficeContact;
    }
    
    
    
}
