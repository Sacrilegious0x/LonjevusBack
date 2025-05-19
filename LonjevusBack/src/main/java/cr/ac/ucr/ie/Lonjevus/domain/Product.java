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
public class Product {
    
    private Integer id;
    private String name;
    private BigDecimal price;
    private String category;
    private LocalDate expirationDate;
    private String photoURL;
    private Unit unit;
    private Supplier supplier;

    public Product() {}

    public Product(Integer id, String name, BigDecimal price, String category, LocalDate expirationDate, String photoURL, Unit unit, Supplier supplier) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.expirationDate = expirationDate;
        this.photoURL = photoURL;
        this.unit = unit;
        this.supplier = supplier;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public Unit getUnit() {
        return unit;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    
    
    
}
