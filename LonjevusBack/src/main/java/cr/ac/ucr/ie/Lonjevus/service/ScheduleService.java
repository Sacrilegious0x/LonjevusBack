/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.service;

import cr.ac.ucr.ie.Lonjevus.daoImplements.ScheduleDaoImplement;
import cr.ac.ucr.ie.Lonjevus.domain.Schedule;

/**
 *
 * @author User
 */
public class ScheduleService {
    private static ScheduleDaoImplement data = new ScheduleDaoImplement();
    
    public void addSchedule(Schedule s){
           data.add(s);
    }
    public int addAndReturn(Schedule s){
        return data.addAndReturnId(s);
    }
    public void updateSchedule(Schedule s){
        data.update(s);
    }
    
    public Schedule getScheduleById(int y){
        return data.getById(y);
    }
    
    public void deleteSchedule(int y){
        data.deleteById(y);
    }
    
}
