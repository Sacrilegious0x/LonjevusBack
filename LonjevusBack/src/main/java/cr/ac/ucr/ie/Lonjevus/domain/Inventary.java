/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.ie.Lonjevus.domain;

import java.util.function.Supplier;

/**
 *
 * @author Usuario
 */
public class Inventary {
    
    private Integer id;
    private Integer quantity;
    private String category;
    private String photoURL;
    private Product product;
    private Supplier supplier;
    private Boolean isActive;

    public Inventary() {}

    public Inventary(Integer id, Integer quantity, String category, String photoURL, Product product, Supplier supplier, Boolean isActive) {
        this.id = id;
        this.quantity = quantity;
        this.category = category;
        this.photoURL = photoURL;
        this.product = product;
        this.supplier = supplier;
        this.isActive = isActive;
    }

    public Integer getId() {
        return id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getCategory() {
        return category;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public Product getProduct() {
        return product;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    
}
