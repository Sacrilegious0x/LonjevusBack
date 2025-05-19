/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.Controller;

import cr.ac.ucr.ie.Lonjevus.domain.Task;
import cr.ac.ucr.ie.Lonjevus.service.TaskService;
import java.util.Collections;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
    
}
