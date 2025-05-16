/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author Usuario
 */
public class Purchase {
    private Integer id;
    private LocalDate date;
    private Product product;
    private BigDecimal amount;
    private Administrator administrator;
    private Boolean isActive;

    public Purchase() {}

    public Purchase(Integer id, LocalDate date, Product product, BigDecimal amount, Administrator administrator, Boolean isActive) {
        this.id = id;
        this.date = date;
        this.product = product;
        this.amount = amount;
        this.administrator = administrator;
        this.isActive = isActive;
    }

    public Integer getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Product getProduct() {
        return product;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Administrator getAdministrator() {
        return administrator;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setAdministrator(Administrator administrator) {
        this.administrator = administrator;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
