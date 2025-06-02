package cr.ac.ucr.ie.Lonjevus.jpa;

import cr.ac.ucr.ie.Lonjevus.domain.Purchase;
import cr.ac.ucr.ie.Lonjevus.domain.PurchaseProduct;
import cr.ac.ucr.ie.Lonjevus.repository.IPurchaseRepository;
import cr.ac.ucr.ie.Lonjevus.service.IPurchaseService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseServiceJPA implements IPurchaseService {

    @Autowired
    private IPurchaseRepository repository;

   @Override
public List<Purchase> getAll() {
    List<Object[]> rows = repository.getAllPurchasesRaw();
    Map<String, Purchase> purchaseMap = new HashMap<>();

    for (Object[] row : rows) {
        String id = (String) row[0];

        // Si ya fue añadida, ignorar
        if (!purchaseMap.containsKey(id)) {
            // Usar findById para obtener con items
            Purchase fullPurchase = findById(id);
            purchaseMap.put(id, fullPurchase);
        }
    }

    return new ArrayList<>(purchaseMap.values());
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

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Purchase purchase) {
        // Paso 1: Llamada al procedimiento y registrar parámetros
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("insert_purchase");

        query.registerStoredProcedureParameter("p_date", Date.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_amount", BigDecimal.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_admin_id", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_new_id", String.class, ParameterMode.OUT);

        query.setParameter("p_date", Date.valueOf(purchase.getDate()));
        query.setParameter("p_amount", purchase.getAmount());
        query.setParameter("p_admin_id", purchase.getIdAdministrator());

        // Paso 2: Ejecutar y obtener el ID
        query.execute();

        String newId = (String) query.getOutputParameterValue("p_new_id");
        System.out.println("ID de compra generado por el SP: " + newId);
        purchase.setId(newId);

        // Paso 3: Insertar productos relacionados
        for (PurchaseProduct item : purchase.getItems()) {
            repository.insertPurchaseProduct(
                    newId,
                    item.getIdProduct(),
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
