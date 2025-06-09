package cr.ac.ucr.ie.Lonjevus.jpa;

import cr.ac.ucr.ie.Lonjevus.domain.Product;
import cr.ac.ucr.ie.Lonjevus.domain.Purchase;
import cr.ac.ucr.ie.Lonjevus.domain.PurchaseProduct;
import cr.ac.ucr.ie.Lonjevus.domain.PurchaseProductId;
import cr.ac.ucr.ie.Lonjevus.repository.IProductRepository;
import cr.ac.ucr.ie.Lonjevus.repository.IPurchaseRepository;
import cr.ac.ucr.ie.Lonjevus.service.IPurchaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class PurchaseServiceJPA implements IPurchaseService {

    @Autowired
    private IPurchaseRepository repository;

    @Autowired
    private IProductRepository productRepository;

    @Override
    public List<Purchase> getAll() {
        List<Purchase> purchases = repository.findByIsActiveTrue();
        setProductPrices(purchases);
        return purchases;
    }

    @Override
    public List<Purchase> getAllInactive() {
        List<Purchase> purchases = repository.findByIsActiveFalse();
        setProductPrices(purchases);
        return purchases;
    }

    @Override
    public Purchase findById(String id) {
        Purchase purchase = repository.findById(id)
                .filter(Purchase::isIsActive)
                .orElseThrow(() -> new RuntimeException("Compra no encontrada o inactiva"));

        // Forzar carga del admin si es LAZY (opcional si es EAGER)
        if (purchase.getAdmin() != null) {
            purchase.getAdmin().getName(); // Esto garantiza que admin se serialice
        }

        for (PurchaseProduct item : purchase.getItems()) {
            Integer productId = item.getIdProduct();

            productRepository.findById(productId).ifPresentOrElse(
                    product -> {
                        item.setProduct(product);
                        item.setPrice(product.getPrice());
                        item.setProductName(product.getName());
                    },
                    () -> {
                        item.setProduct(null);
                        item.setPrice(null);
                        item.setProductName("Producto eliminado (#" + productId + ")");
                    }
            );
        }

        return purchase;
    }

    @Override
    @Transactional
    public void save(Purchase purchase) {
        String datePart = purchase.getDate().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd"));
        long count = repository.count() + 1;
        String newId = String.format("%04d-%s", count, datePart);
        purchase.setId(newId);

        purchase.setIsActive(true);

        for (PurchaseProduct item : purchase.getItems()) {
            item.setPurchase(purchase);
            Integer productId = item.getIdProduct();
            if (productId == null) {
                throw new RuntimeException("Falta idProduct en un item");
            }

            item.setId(new PurchaseProductId(newId, productId));
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + productId));
            item.setProduct(product);
        }

        repository.save(purchase);
    }

    @Override
    @Transactional
    public void update(String id, Purchase updatedPurchase) {
        Purchase existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compra no encontrada"));

        existing.setDate(updatedPurchase.getDate());
        existing.setAmount(updatedPurchase.getAmount());
        existing.getItems().clear();

        for (PurchaseProduct item : updatedPurchase.getItems()) {
            item.setPurchase(existing);
            Integer productId = item.getIdProduct();
            if (productId == null) {
                throw new RuntimeException("Falta idProduct en un item");
            }

            item.setId(new PurchaseProductId(id, productId));
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + productId));
            item.setProduct(product);
            existing.getItems().add(item);
        }

        repository.save(existing);
    }

    @Override
    @Transactional
    public void delete(String id) {
        Purchase purchase = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compra no encontrada"));
        purchase.setIsActive(false);
        repository.save(purchase);
    }

    private void setProductPrices(List<Purchase> purchases) {
        for (Purchase purchase : purchases) {
            for (PurchaseProduct item : purchase.getItems()) {
                if (item.getProduct() != null) {
                    item.setPrice(item.getProduct().getPrice());
                }
            }
        }
    }
}
