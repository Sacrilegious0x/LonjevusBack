package cr.ac.ucr.ie.Lonjevus.domain;

/**
 *
 * @author JOSHUACALETCESPEDESG
 */
public class Resident {
    private int id;
    private String identification;
    private String name;
    private int age;
    private String healthStatus;
    private int numberRoom;
    private String photo;
    private boolean isActive;

    public Resident() {
    }

    public Resident(String identification, String name, int age, String healthStatus, int roomNumber, String photo, boolean isActive) {
        this.identification = identification;
        this.name = name;
        this.age = age;
        this.healthStatus = healthStatus;
        this.numberRoom = roomNumber;
        this.photo = photo;
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    public int getNumberRoom() {
        return numberRoom;
    }

    public void setNumberRoom(int numberRoom) {
        this.numberRoom = numberRoom;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
    
}
