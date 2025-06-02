package cr.ac.ucr.ie.Lonjevus.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "purchase_product")
public class PurchaseProduct implements Serializable {

    @EmbeddedId
    private PurchaseProductId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idPurchase")
    @JoinColumn(name = "idPurchase")
    private Purchase purchase;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idProduct")
    @JoinColumn(name = "idProduct")
    private Product product;

    @Column(name = "quantity")
    private Integer quantity;

    @Transient
    private String productName;

    @Transient
    private BigDecimal price;

    @Transient
    private LocalDate expirationDate;

    public PurchaseProduct() {
    }

    public PurchaseProduct(PurchaseProductId id, Purchase purchase, Product product, Integer quantity) {
        this.id = id;
        this.purchase = purchase;
        this.product = product;
        this.quantity = quantity;
    }

    public PurchaseProductId getId() {
        return id;
    }

    public void setId(PurchaseProductId id) {
        this.id = id;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    // Métodos de conveniencia para claves
    public String getIdPurchase() {
        return id != null ? id.getIdPurchase() : null;
    }

    public void setIdPurchase(String idPurchase) {
        if (this.id == null) {
            this.id = new PurchaseProductId();
        }
        this.id.setIdPurchase(idPurchase);
    }

    public Integer getIdProduct() {
        return id != null ? id.getIdProduct() : null;
    }

    public void setIdProduct(Integer idProduct) {
        if (this.id == null) {
            this.id = new PurchaseProductId();
        }
        this.id.setIdProduct(idProduct);
    }
}
