
package cr.ac.ucr.ie.Lonjevus.Controller;

import cr.ac.ucr.ie.Lonjevus.domain.Room;
import cr.ac.ucr.ie.Lonjevus.service.IRoomService;
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

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("rooms")
public class RoomController {

    @Autowired
    private IRoomService service;


    @GetMapping("/list")
    public Map<String, Object> getList() {
        return Collections.singletonMap("rooms", service.getAllRooms());
    }


    @PostMapping(path = "/save", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Map<String, Object> saveRoom(
            @RequestParam("statusRoom") String statusRoom,
            @RequestParam("roomType") String roomType,
            @RequestParam("bedCount") int bedCount,
            @RequestParam("isActive") boolean isActive,
            @RequestParam("roomNumber") int roomNumber
    ) {
        Room room = new Room();
        room.setStatusRoom(statusRoom);
        room.setRoomType(roomType);
        room.setBedCount(bedCount);
        room.setIsActive(isActive);
        room.setRoomNumber(roomNumber);
        service.save(room);
        return getList();
    }

    @DeleteMapping("/delete")
    public Map<String, Object> deleteRoom(@RequestParam int id) {
        service.delete(id);
        return getList();
    }


    @GetMapping("/getById")
    public Room getRoomById(@RequestParam int id) {
        return service.getById(id);
    }


    @PostMapping(path = "/update", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Map<String, Object> updateRoom(
            @RequestParam("id") int id,
            @RequestParam("statusRoom") String statusRoom,
            @RequestParam("roomType") String roomType,
            @RequestParam("bedCount") int bedCount,
            @RequestParam("isActive") boolean isActive,
            @RequestParam("roomNumber") Integer roomNumber
    ) {
        Room room = new Room();
        room.setId(id);
        room.setStatusRoom(statusRoom);
        room.setRoomType(roomType);
        room.setBedCount(bedCount);
        room.setIsActive(isActive);
        room.setRoomNumber(roomNumber);
        service.save(room);
        return getList();
    }
}
