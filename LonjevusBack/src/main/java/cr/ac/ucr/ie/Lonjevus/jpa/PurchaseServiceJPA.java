package cr.ac.ucr.ie.Lonjevus.jpa;

import cr.ac.ucr.ie.Lonjevus.domain.Inventory;
import cr.ac.ucr.ie.Lonjevus.domain.Product;
import cr.ac.ucr.ie.Lonjevus.domain.Purchase;
import cr.ac.ucr.ie.Lonjevus.domain.PurchaseProduct;
import cr.ac.ucr.ie.Lonjevus.domain.PurchaseProductId;
import cr.ac.ucr.ie.Lonjevus.repository.IInventoryRepository;
import cr.ac.ucr.ie.Lonjevus.repository.IProductRepository;
import cr.ac.ucr.ie.Lonjevus.repository.IPurchaseRepository;
import cr.ac.ucr.ie.Lonjevus.service.IPurchaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PurchaseServiceJPA implements IPurchaseService {

    @Autowired
    private IPurchaseRepository repository;

    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private IInventoryRepository inventoryRepository;

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

        if (purchase.getAdmin() != null) {
            purchase.getAdmin().getName(); 
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
            item.setProductName(product.getName());
        }

        repository.save(purchase);

        for (PurchaseProduct item : purchase.getItems()) {
            Product product = item.getProduct();

            for (int i = 0; i < item.getQuantity(); i++) {
                Inventory inv = new Inventory();
                inv.setProduct(product);
                inv.setQuantity(1);
                inv.setIsActive(true);
                inv.setCategory(product.getCategory());
                inv.setPhotoURL(product.getPhotoURL());
                inv.setPurchase(purchase);
                inventoryRepository.save(inv);
            }
        }
    }

    @Override
    @Transactional
    public void update(String id, Purchase updatedPurchase) {
        Purchase existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compra no encontrada"));

        existing.setDate(updatedPurchase.getDate());
        existing.setAmount(updatedPurchase.getAmount());
        existing.getItems().clear();

        // Eliminar del inventario inserts previos de esta compra
        List<Inventory> oldInventory = inventoryRepository.findByPurchaseId(id);
        for (Inventory inv : oldInventory) {
            inventoryRepository.delete(inv);
        }

        // Registrar nuevos productos e inventario
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

            // Crear nuevas entradas en inventario
            for (int i = 0; i < item.getQuantity(); i++) {
                Inventory inv = new Inventory();
                inv.setProduct(product);
                inv.setQuantity(1);
                inv.setIsActive(true);
                inv.setCategory(product.getCategory());
                inv.setPhotoURL(product.getPhotoURL());
                inv.setPurchase(existing);
                inventoryRepository.save(inv);
            }
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
                Integer productId = item.getIdProduct();

                productRepository.findById(productId).ifPresentOrElse(
                        product -> {
                            if (Boolean.TRUE.equals(product.isIsActive())) {
                                item.setProduct(product);
                                item.setPrice(product.getPrice());
                                item.setProductName(product.getName());
                            } else {
                                item.setProduct(null);
                                item.setPrice(null);
                                if (item.getProductName() == null || item.getProductName().isBlank()) {
                                    item.setProductName("Producto inactivo (#" + productId + ")");
                                }
                            }
                        },
                        () -> {
                            item.setProduct(null);
                            item.setPrice(null);
                            if (item.getProductName() == null || item.getProductName().isBlank()) {
                                item.setProductName("Producto no disponible");
                            }
                        }
                );
            }
        }
    }

}
