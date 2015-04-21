/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.entity;

import javax.persistence.CascadeType;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author richard
 */
@Entity
@XmlRootElement
public class AppConfig extends BaseEntity {

    private static final long serialVersionUID = 6953154229856754026L;

    private String metaKeywords;
    private String metaDescription;

    @Lob
    private String footer;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "LOGOFILEID", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private ImageFile appLogo = new ImageFile();

    public AppConfig() {
    }

    public String getMetaKeywords() {
        return metaKeywords;
    }

    public void setMetaKeywords(String metaKeywords) {
        this.metaKeywords = metaKeywords;
    }

    public String getMetaDescription() {
        return metaDescription;
    }

    public void setMetaDescription(String metaDescription) {
        this.metaDescription = metaDescription;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public ImageFile getAppLogo() {
        return appLogo;
    }

    public void setAppLogo(ImageFile appLogo) {
        this.appLogo = appLogo;
    }

}
