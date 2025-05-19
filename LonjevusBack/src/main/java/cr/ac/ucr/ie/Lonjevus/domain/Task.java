/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.domain;

/**
 *
 * @author User
 */
public class Task {
    private int id;
    private int caregiverId;
    private String description;
    
    public Task (){
        
    }

    public Task(int id, int caregiverId, String description) {
        this.id = id;
        this.caregiverId = caregiverId;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCaregiver() {
        return caregiverId;
    }

    public void setCaregiver(int caregiverId) {
        this.caregiverId = caregiverId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
}
