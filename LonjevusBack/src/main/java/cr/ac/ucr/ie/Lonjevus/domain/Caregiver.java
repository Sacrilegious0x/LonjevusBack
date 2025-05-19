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

    public Caregiver(int id, String identification, String name, double salary, String email, String password, String Shift, String photoUrl, Schedule schedule) {
        super(id, identification, name, salary, email, password, photoUrl, schedule);
        this.Shift = Shift;
    }

    public String getShift() {
        return Shift;
    }

    public void setShift(String Shift) {
        this.Shift = Shift;
    }

}
