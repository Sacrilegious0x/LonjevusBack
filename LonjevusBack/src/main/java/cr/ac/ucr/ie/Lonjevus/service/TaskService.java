/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.service;

import cr.ac.ucr.ie.Lonjevus.daoImplements.TaskDaoImplement;
import cr.ac.ucr.ie.Lonjevus.domain.Task;
import java.util.LinkedList;

/**
 *
 * @author User
 */
public class TaskService {
    private static TaskDaoImplement data = new TaskDaoImplement();
    
    public static LinkedList<Task> getList(){
        return data.getAll();
    }
    
    public void addTask(Task t){
           data.add(t);
    }
    
    public void updateTask(Task t){
        data.update(t);
    }
    
    public Task getById(int y){
        return data.findById(y);
    }
    
    public void deleteTask(int y){
        data.deleteById(y);
    }
    
}
