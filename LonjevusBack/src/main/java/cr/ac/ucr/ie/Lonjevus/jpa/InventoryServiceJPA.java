package cr.ac.ucr.ie.Lonjevus.jpa;

import cr.ac.ucr.ie.Lonjevus.domain.Inventory;
import cr.ac.ucr.ie.Lonjevus.repository.IInventoryRepository;
import cr.ac.ucr.ie.Lonjevus.service.IInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class InventoryServiceJPA implements IInventoryService {

    @Autowired
    private IInventoryRepository repository;

    @Override
    public void save(Inventory inventory) {
        repository.save(inventory);
    }

    @Override
    public List<Inventory> getAll() {
        List<Inventory> inventoryList = repository.findAll();

        for (Inventory inv : inventoryList) {
            if (inv.getPurchase() != null && inv.getProduct() != null) {
                // Buscar el productId en los items de la compra
                inv.getPurchase().getItems().stream()
                        .filter(item -> item.getIdProduct().equals(inv.getProduct().getId()))
                        .findFirst()
                        .ifPresent(item -> {
                            if (item.getPrice() == null && item.getProduct() != null) {
                                item.setPrice(item.getProduct().getPrice());
                            }
                        });
            }
        }

        return inventoryList;
    }

    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }

    @Override
    public Inventory getById(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Inventory> findByExpirationDate(LocalDate expirationDate) {
        return repository.findByProduct_ExpirationDate(expirationDate);
    }
}
