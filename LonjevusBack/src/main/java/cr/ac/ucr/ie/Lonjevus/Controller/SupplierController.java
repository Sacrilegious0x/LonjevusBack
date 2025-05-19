package cr.ac.ucr.ie.Lonjevus.Controller;

import cr.ac.ucr.ie.Lonjevus.domain.Supplier;
import cr.ac.ucr.ie.Lonjevus.service.LocalStorageService;
import cr.ac.ucr.ie.Lonjevus.service.SupplierService;
import java.util.Collections;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("suppliers")
@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class SupplierController {
    

    private final LocalStorageService localStorageService;

    @Autowired
    public SupplierController(LocalStorageService localStorageService) {
        this.localStorageService = localStorageService;
    }
    
    @RequestMapping("/list")
    public Map getList() {
        return Collections.singletonMap("suppliers", SupplierService.getAllSupplier());
    }

    @PostMapping(path = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, Object> saveSupplier(
            @RequestParam("name") String name,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("email") String email,
            @RequestParam("address") String address,
            @RequestParam("isActive") boolean isActive,
            @RequestParam("photo") MultipartFile photo
    ) {       
        Supplier supplier = new Supplier();
        supplier.setName(name);
        supplier.setPhoneNumber(phoneNumber);
        supplier.setEmail(email);
        supplier.setAddress(address);
        supplier.setIsActive(isActive);
        String photoPath = localStorageService.save(photo);
        supplier.setPhoto(photoPath);
        SupplierService.addSupplier(supplier);
        return getList();
    }
    
    @DeleteMapping("/delete")
    public Map deleteSupplier(@RequestParam int id) {
        SupplierService.deleteSupplierById(id);
        return getList();
    }
    
    @GetMapping("/getById")
    public Supplier getSupplierById(@RequestParam int id) {
        return SupplierService.getBySupplierId(id);
    }
    
    @PostMapping("/update")
    public Map updateSupplier(@RequestParam int id,
            @RequestParam("name") String name,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("email") String email,
            @RequestParam("address") String address,
            @RequestParam("isActive") boolean isActive,
            @RequestParam("photo") MultipartFile photo){
        
            Supplier supplier = new Supplier();
            supplier.setId(id);
            supplier.setName(name);
            supplier.setPhoneNumber(phoneNumber);
            supplier.setEmail(email);
            supplier.setAddress(address);
            supplier.setIsActive(isActive);
            String photoPath = localStorageService.save(photo);
            supplier.setPhoto(photoPath);
        
        
        SupplierService.updateSupplier(supplier);
        return getList();
    }
    
}
