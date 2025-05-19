/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.domain;

/**
 *
 * @author User
 */
public class Schedule {
    private int id;
    private String days;
    private String entryTime1;
    private String exitTime1;
    private String entryTime2;
    private String exitTime2;
    
    public Schedule(){
        
    }

    public Schedule(int id, String days, String entryTime1, String exitTime1, String entryTime2, String exitTime2) {
        this.id = id;
        this.days = days;
        this.entryTime1 = entryTime1;
        this.exitTime1 = exitTime1;
        this.entryTime2 = entryTime2;
        this.exitTime2 = exitTime2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getEntryTime1() {
        return entryTime1;
    }

    public void setEntryTime1(String entryTime1) {
        this.entryTime1 = entryTime1;
    }

    public String getExitTime1() {
        return exitTime1;
    }

    public void setExitTime1(String exitTime1) {
        this.exitTime1 = exitTime1;
    }

    public String getEntryTime2() {
        return entryTime2;
    }

    public void setEntryTime2(String entryTime2) {
        this.entryTime2 = entryTime2;
    }

    public String getExitTime2() {
        return exitTime2;
    }

    public void setExitTime2(String exitTime2) {
        this.exitTime2 = exitTime2;
    }
    
}
