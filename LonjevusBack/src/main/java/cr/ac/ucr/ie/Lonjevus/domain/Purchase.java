package cr.ac.ucr.ie.Lonjevus.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "purchase")
public class Purchase {

    @Id
    @Column(name = "id")
    private String id;


    private LocalDate date;
    private BigDecimal amount;

    //@ManyToOne
    //@JoinColumn(name = "idAdministrator")
    //private Admin admin;
    private Integer idAdministrator;

    @ManyToOne
    @JoinColumn(name = "idProduct")
    private Product product;

    private boolean isActive;

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseProduct> items = new ArrayList<>();


    public Purchase() {}

    public Purchase(String id, LocalDate date, BigDecimal amount, /*Admin admin*/ Integer idAdministrator, Product product, boolean isActive) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        //this.admin = admin;
        this.idAdministrator = idAdministrator;
        this.product = product;
        this.isActive = isActive;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /*public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }*/

    public Integer getIdAdministrator() {
        return idAdministrator;
    }

    public void setIdAdministrator(Integer idAdministrator) {
        this.idAdministrator = idAdministrator;
    }

    
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public List<PurchaseProduct> getItems() {
        return items;
    }

    public void setItems(List<PurchaseProduct> items) {
        this.items = items;
    }

    
}
