package cr.ac.ucr.ie.Lonjevus.jpa;

import cr.ac.ucr.ie.Lonjevus.domain.Purchase;
import cr.ac.ucr.ie.Lonjevus.domain.PurchaseProduct;
import cr.ac.ucr.ie.Lonjevus.repository.IPurchaseRepository;
import cr.ac.ucr.ie.Lonjevus.service.IPurchaseService;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseServiceImplements implements IPurchaseService {

    @Autowired
    private IPurchaseRepository repository;

    @Override
    public List<Purchase> getAll() {
        List<Object[]> rows = repository.getAllPurchasesRaw();
        List<Purchase> purchases = new ArrayList<>();

        for (Object[] row : rows) {
            String id = (String) row[0];
            Date date = (Date) row[1];
            BigDecimal amount = (BigDecimal) row[2];

            Purchase p = new Purchase();
            p.setId(id);
            p.setDate(date.toLocalDate());
            p.setAmount(amount);
            purchases.add(p);
        }
        return purchases;
    }

    @Override
    public Purchase findById(String id) {
        List<Object[]> rows = repository.getPurchaseDetailsById(id);
        if (rows.isEmpty()) {
            throw new RuntimeException("Compra no encontrada");
        }

        Purchase purchase = new Purchase();
        List<PurchaseProduct> items = new ArrayList<>();

        for (Object[] row : rows) {
            if (purchase.getId() == null) {
                purchase.setId((String) row[0]);
                purchase.setDate(((Date) row[1]).toLocalDate());
                purchase.setAmount((BigDecimal) row[2]);
            }

            PurchaseProduct item = new PurchaseProduct();
            item.setIdProduct((Integer) row[3]);
            item.setProductName((String) row[4]);
            item.setPrice((BigDecimal) row[5]);
            item.setQuantity((Integer) row[6]);
            items.add(item);
        }

        purchase.setItems(items);
        return purchase;
    }

    @Override
    public void save(Purchase purchase) {
        repository.insertPurchase(Date.valueOf(purchase.getDate()), purchase.getAmount(), purchase.getAdmin().getId());
        for (PurchaseProduct item : purchase.getItems()) {
            repository.insertPurchaseProduct(
                    item.getId().getIdPurchase(),
                    item.getId().getIdProduct(),
                    item.getQuantity(),
                    Date.valueOf(item.getExpirationDate())
            );
        }

    }

    @Override
    public void update(String id, Purchase updatedPurchase) {
        repository.updatePurchase(id, Date.valueOf(updatedPurchase.getDate()), updatedPurchase.getAmount());
        repository.deletePurchaseProducts(id);

        for (PurchaseProduct item : updatedPurchase.getItems()) {
            repository.insertPurchaseProduct(
                    id,
                    item.getIdProduct(),
                    item.getQuantity(),
                    Date.valueOf(item.getExpirationDate())
            );
        }
    }

    @Override
    public void delete(String id) {
        repository.deletePurchaseById(id);
    }
}
