
package cr.ac.ucr.ie.Lonjevus.Controller;

import cr.ac.ucr.ie.Lonjevus.domain.Supplier;
import cr.ac.ucr.ie.Lonjevus.service.SupplierService;
import java.util.Collections;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@RequestMapping("suppliers")
@Controller
public class SupplierController {
    
    
    @RequestMapping("/list")
    @ResponseBody
    public Map getList(){
        return Collections.singletonMap("suppliers", SupplierService.getAllSupplier());
    }
    
    @PostMapping("/save")
    @ResponseBody
    public Map saveStadium(@RequestBody Supplier supplier){
        SupplierService.addSupplier(supplier);
        return getList();
    }
    
    @DeleteMapping("/delete")
    @ResponseBody
    public Map deleteStadium(@RequestParam int id){
        SupplierService.deleteSupplierById(id);
        return getList();
    }
    
    @GetMapping("/getById")
    @ResponseBody
    public Supplier getStadiumById(@RequestParam int id){
        return SupplierService.getBySupplierId(id);
    }
    
    
    
}
