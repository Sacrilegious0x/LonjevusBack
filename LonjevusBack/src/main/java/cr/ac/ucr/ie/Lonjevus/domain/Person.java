/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.domain;

/**
 *
 * @author User
 */
public class Person {
    private int id;
    private String identification;
    private String name;
    private String email;
    private String password;
    private String photo;
    private Schedule schedule;
    
    public Person(){
        
    }
    public Person(int id, String identification, String name, String email, String password, String photo, Schedule schedule) {
        this.id = id;
        this.identification = identification;
        this.name = name;
        this.email = email;
        this.password = password;
        this.photo = photo;
        this.schedule = schedule;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
    
    
    
}
