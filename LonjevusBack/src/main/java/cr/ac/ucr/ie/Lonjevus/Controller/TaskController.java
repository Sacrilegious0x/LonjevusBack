/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.Controller;

import cr.ac.ucr.ie.Lonjevus.domain.Task;
import cr.ac.ucr.ie.Lonjevus.service.TaskService;
import java.util.Collections;
import java.util.Map;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author User
 */

@RequestMapping("task")
@RestController
public class TaskController {
    public static TaskService service = new TaskService();
    
    @GetMapping("/listTask") 
    public Map getList(){
        return Collections.singletonMap("data", service.getList());
    }
    @PostMapping("/saveTask")
    public Map saveTask(@RequestBody Task t) {
        service.addTask(t);
        return getList();
    }
    
    @PostMapping("/updateTask")
    public Map updateTask(@RequestBody Task t){
        service.updateTask(t);
        return getList();
    }
    
    @DeleteMapping("/deleteTask/{id}")
    public Map deleteTask(@PathVariable int id){
        service.deleteTask(id);
        return getList();
    }
    
    @GetMapping("/getById/{id}")
    public Task getById(@PathVariable int id){
        return service.getById(id);
    }  
    
}
