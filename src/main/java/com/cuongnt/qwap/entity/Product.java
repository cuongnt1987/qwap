/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.entity;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author richard
 */
@Cacheable
@Entity
@XmlRootElement
public class Product extends BaseEntity {
    
    private static final long serialVersionUID = 4943994337680551419L;
    
    @Min(0)
    private double price;
    @Min(0)
    private int downCount;
    @Min(0)
    private int viewCount;
    @Lob
    @Basic(optional = false, fetch = FetchType.LAZY)
    private String productDesc;
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoryId", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private ProductCategory category;
    @OneToMany(mappedBy = "owner")
    @Enumerated(EnumType.STRING)
    @MapKeyJoinColumn(name = "PRODUCT_TYPE")
    private Map<MobileType, AppFile> appFile = new HashMap<>();
    
    public Product() {
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }
    
    public ProductType getType() {
        if (category != null) return category.getType();
        return null;
    }

    public int getDownCount() {
        return downCount;
    }

    public void setDownCount(int downCount) {
        this.downCount = downCount;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public Map<MobileType, AppFile> getAppFile() {
        return appFile;
    }

    public void setAppFile(Map<MobileType, AppFile> appFile) {
        this.appFile = appFile;
    }
    
}
