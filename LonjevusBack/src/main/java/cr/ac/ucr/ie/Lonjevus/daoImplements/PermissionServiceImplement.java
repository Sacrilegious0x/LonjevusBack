
package cr.ac.ucr.ie.Lonjevus.daoImplements;


import cr.ac.ucr.ie.Lonjevus.domain.Permission;
import cr.ac.ucr.ie.Lonjevus.repository.IPermissionRepository;
import cr.ac.ucr.ie.Lonjevus.service.IPermissionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PermissionServiceImplement implements IPermissionService {
    
    @Autowired
    private IPermissionRepository permRepo;

    @Override
    public void save(Permission permission) {
       permRepo.save(permission);
    }

    @Override
    public List<Permission> getAllPermissions() {
        return permRepo.findAll();
    }

    @Override
    public void delete(int permissionId) {
       permRepo.deleteById(permissionId);
    }

    @Override
    public Permission getById(int permissionId) {
        return permRepo.findById(permissionId).get();
    }


    
    
}
