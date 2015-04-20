/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.entity;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author richard
 */
@Cacheable
@Entity
@XmlRootElement
public class ImageFile extends File {

    private static final long serialVersionUID = -6150194829860682151L;
    
    @ManyToOne(cascade = {})
    @JoinColumn(name = "PRODUCTID", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Product product;

    public ImageFile() {
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    
    public String getURL() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        return externalContext.getRequestScheme() + "://"
                + externalContext.getRequestServerName() + ":"
                + externalContext.getRequestServerPort()
                + externalContext.getRequestContextPath() + "/download/image/inline/" + id;
    }

    public String getDownloadURL() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        return externalContext.getRequestScheme()
                + "://" + externalContext.getRequestServerName()
                + ":" + externalContext.getRequestServerPort()
                + externalContext.getRequestContextPath() + "/download/image/" + id;
    }

}