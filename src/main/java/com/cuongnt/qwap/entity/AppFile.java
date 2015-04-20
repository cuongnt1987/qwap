/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.entity;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author richard
 */
@Cacheable
@Entity
@XmlRootElement
public class AppFile extends File {

    private static final long serialVersionUID = -1542564934903179093L;

    @ManyToOne(cascade = {})
    private Product owner;

    public AppFile() {
    }
   
    public Product getOwner() {
        return owner;
    }

    public void setOwner(Product owner) {
        this.owner = owner;
    }

    public String getURL() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        return externalContext.getRequestScheme() + "://"
                + externalContext.getRequestServerName() + ":"
                + externalContext.getRequestServerPort()
                + externalContext.getRequestContextPath() + "/download/app/inline/" + id;
    }

    public String getDownloadURL() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        return externalContext.getRequestScheme()
                + "://" + externalContext.getRequestServerName()
                + ":" + externalContext.getRequestServerPort()
                + externalContext.getRequestContextPath() + "/download/app/" + id;
    }
}
