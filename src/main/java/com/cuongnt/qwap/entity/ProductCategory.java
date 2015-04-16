/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.entity;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
    @NamedQuery(name = "ProductCategory.getByType", query = "SELECT c FROM ProductCategory c WHERE c.type = :type")
})
@XmlRootElement
public class ProductCategory extends WebContent {

    private static final long serialVersionUID = 4078687014073678150L;
    
    @Enumerated(EnumType.ORDINAL)
    private ProductType type;

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return title;
    }
}
