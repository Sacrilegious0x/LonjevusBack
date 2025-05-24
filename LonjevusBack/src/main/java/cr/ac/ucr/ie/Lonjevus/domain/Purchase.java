package cr.ac.ucr.ie.Lonjevus.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedList;

public class Purchase {
    private String id;
    private LocalDate date;
    private BigDecimal amount;
    //private Admin admin;
    private LinkedList<PurchaseProduct> items; //La relacion de muchos, la compra tiene 1 o muchos productos

    public Purchase() {
        this.items = new LinkedList<>();
    }

    public Purchase(String id, LocalDate date, BigDecimal amount, LinkedList<PurchaseProduct> items) {
        this.id = id;
        this.date = date;
        this.amount = amount;
      //  this.admin = admin;
        this.items = items;
    }

    public String getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    // Admin getAdmin() {
     //   return admin;
    //}

    public LinkedList<PurchaseProduct> getItems() {
        return items;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDate(LocalDate date) {
            this.date = date;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

   // public void setAdmin(Admin admin) {
     //   this.admin = admin;
    //}

    public void setItems(LinkedList<PurchaseProduct> items) {
        this.items = items;
    }

    public void setSupplier(Supplier supplier) {
        
    }
}