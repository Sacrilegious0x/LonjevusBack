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

    public Admin(int id, String identification, String name, double salary, String email, String password, String photoUrl, Schedule schedule) {
        super(id, identification, name, salary, email, password, photoUrl, schedule);
    }

    public int getOficeContact() {
        return oficeContact;
    }

    public void setOficeContact(int oficeContact) {
        this.oficeContact = oficeContact;
    } 
    
}
