/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.entity;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author richard
 */
@Cacheable
@Entity
@NamedQueries({
    @NamedQuery(name = "ProductCategory.getByType", query = "SELECT c FROM ProductCategory c WHERE c.type = :type ORDER BY c.orderNumber DESC"),
    @NamedQuery(name = "ProductCategory.findBySlug", query = "SELECT c FROM ProductCategory c WHERE c.slug = :slug"),
    @NamedQuery(name = "ProductCategory.countBySlug", query = "SELECT COUNT(p.id) FROM ProductCategory p WHERE p.slug = :slug")
})
@XmlRootElement
public class ProductCategory extends WebContent {

    private static final long serialVersionUID = 4078687014073678150L;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "THUMBFILEID", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private ImageFile thumbnail = new ImageFile();

    @Enumerated(EnumType.ORDINAL)
    private ProductType type;

    private int orderNumber;

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public ImageFile getThumbnail() {
        if (thumbnail == null) {
            thumbnail = new ImageFile();
        }
        return thumbnail;
    }

    public void setThumbnail(ImageFile thumbnail) {

        this.thumbnail = thumbnail;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Override
    public String toString() {
        return title;
    }
}
