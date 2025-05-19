
package cr.ac.ucr.ie.Lonjevus.domain;


public class Permission {
    
    private int roleId;       
    private int moduleId;     
    private String  moduleCode;  
    private String  moduleName;   

    private Boolean canView;      
    private Boolean canCreate;   
    private Boolean canUpdate;    
    private Boolean canDelete;    

    public Permission(int roleId, int moduleId, String moduleCode, String moduleName, Boolean canView, Boolean canCreate, Boolean canUpdate, Boolean canDelete) {
        this.roleId = roleId;
        this.moduleId = moduleId;
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;
        this.canView = canView;
        this.canCreate = canCreate;
        this.canUpdate = canUpdate;
        this.canDelete = canDelete;
    }

    public Permission() {
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public Boolean getCanView() {
        return canView;
    }

    public void setCanView(Boolean canView) {
        this.canView = canView;
    }

    public Boolean getCanCreate() {
        return canCreate;
    }

    public void setCanCreate(Boolean canCreate) {
        this.canCreate = canCreate;
    }

    public Boolean getCanUpdate() {
        return canUpdate;
    }

    public void setCanUpdate(Boolean canUpdate) {
        this.canUpdate = canUpdate;
    }

    public Boolean getCanDelete() {
        return canDelete;
    }

    public void setCanDelete(Boolean canDelete) {
        this.canDelete = canDelete;
    }
    
    


    
}
