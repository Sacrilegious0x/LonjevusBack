/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.Controller;

import cr.ac.ucr.ie.Lonjevus.domain.Resident;
import cr.ac.ucr.ie.Lonjevus.service.ResidentService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author User
 */
@RestController
//@Controller
public class ResidentController {
    
    /*@RequestMapping("/url")
    public String page(Model model) {
        model.addAttribute("attribute", "value");
        return "view.name";
    }*/
    
    @GetMapping("/hello")
    public String sayHello(){
        return "Hello World!";
    }
    
    @GetMapping("/residents")
    public List<Resident> getNames() {
        return ResidentService.getAll();
    }
    
    @PostMapping("/addResident")
    @ResponseBody
    public String addResident(@RequestBody Resident resident) {
         ResidentService.add(resident);
         return "Residente listado";
    }
    
    @DeleteMapping("/deleteResident")
    @ResponseBody
    public String deleteResident(@RequestParam int id){
        ResidentService.delete(id);
        return "Residente eliminado";
    }
    
    @GetMapping("/findResident")
    public Resident seachById(@RequestParam int id){
        return ResidentService.findById(id);
    }
    
    @PostMapping("/updateResident")
    public String updateResident(@RequestBody Resident r){
        if(r.getId() != 0){
            System.out.println("\nLOS DATOS DEL RESIDENTE\n" + r.toString());
            ResidentService.update(r);
            
            return "Residente actualizado";
        }
        return "El residente no existe";
    }
}
