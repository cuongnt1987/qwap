/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author richard
 */
@Cacheable
@Entity
@NamedQueries({
    @NamedQuery(name = "Product.findBySlug", query = "SELECT p FROM Product p WHERE p.slug = :slug"),
    @NamedQuery(name = "Product.countBySlug", query = "SELECT COUNT(p.id) FROM Product p WHERE p.slug = :slug")
})
@XmlRootElement
public class Product extends WebContent {

    private static final long serialVersionUID = 4943994337680551419L;

    @Min(0)
    private double price;

    @Min(0)
    private int downCount;

    @Min(0)
    private int viewCount;

    @Min(0)
    private int likeCount;

    private boolean hot;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name = "THUMBFILEID", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private ImageFile thumbnail = new ImageFile();

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "BGFILEID", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private ImageFile bgFile = new ImageFile();

    @OneToMany(mappedBy = "product", cascade = {CascadeType.ALL})
    private List<ImageFile> screenshots = new ArrayList<>();

    @Lob
    @Basic(optional = false, fetch = FetchType.LAZY)
    private String productDesc;

    @Enumerated
    private ProductType type = ProductType.GAME;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CATEGORYID", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private ProductCategory category;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<AppFile> appFiles = new ArrayList<>();

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

    public int getDownCount() {
        return downCount;
    }
    
    private int priority;

    public void setDownCount(int downCount) {
        this.downCount = downCount;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public List<AppFile> getAppFiles() {
        
        for (MobileType mtype : MobileType.values()) {
            boolean exist = false;
            for (AppFile app : appFiles) {
                if (app.getType() == mtype) {
                    exist = true;
                }
            }
            if (!exist) {
                AppFile app = new AppFile();
                app.setOwner(this);
                app.setType(mtype);
                appFiles.add(app);
            }
        }
        
        return appFiles;
    }

    public void setAppFiles(List<AppFile> appFiles) {
        this.appFiles = appFiles;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public List<ImageFile> getScreenshots() {
        return screenshots;
    }

    public void setScreenshots(List<ImageFile> screenshots) {
        this.screenshots = screenshots;
    }

    public boolean isHot() {
        return hot;
    }

    public void setHot(boolean hot) {
        this.hot = hot;
    }

    public ImageFile getThumbnail() {
        if (thumbnail == null) thumbnail = new ImageFile();
        return thumbnail;
    }

    public void setThumbnail(ImageFile thumbnail) {
        this.thumbnail = thumbnail;
    }

    public ImageFile getBgFile() {
        if (bgFile == null) bgFile = new ImageFile();
        return bgFile;
    }

    public void setBgFile(ImageFile bgFile) {
        this.bgFile = bgFile;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
    
}
