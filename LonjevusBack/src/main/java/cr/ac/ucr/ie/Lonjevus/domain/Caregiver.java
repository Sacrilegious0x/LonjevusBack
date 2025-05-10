/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.domain;

/**
 *
 * @author User
 */
public class Caregiver extends Person {
    private String Shift;

    public Caregiver() {
    }

    public Caregiver(int id, String identification, String name, String email, String password, String photo, Schedule schedule, String shift) {
        super(id, identification, name, email, password, photo, schedule);
        this.Shift = shift;
    }

    public String getShift() {
        return Shift;
    }

    public void setShift(String Shift) {
        this.Shift = Shift;
    }
    
    
}
