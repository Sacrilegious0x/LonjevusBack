
package cr.ac.ucr.ie.Lonjevus.jpa;

import cr.ac.ucr.ie.Lonjevus.domain.Room;
import cr.ac.ucr.ie.Lonjevus.repository.IRoomRepository;
import cr.ac.ucr.ie.Lonjevus.service.IRoomService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomServiceJPA implements IRoomService  {
    
    @Autowired
    private IRoomRepository repo;
    
    @Override
    public void save(Room room) {
        repo.save(room);
    }

    @Override
    public List<Room> getAllRooms() {
       return repo.findAll();
    }

    @Override
    public void delete(int roomId) {
        repo.deleteById(roomId);
    }

    @Override
    public Room getById(int roomId) {
        return repo.findById(roomId).get();
    }
    
}
