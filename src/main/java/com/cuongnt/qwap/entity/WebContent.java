/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author richard
 */
@MappedSuperclass
public abstract class WebContent extends BaseEntity {

    private static final long serialVersionUID = 707428908486294175L;

    protected String metaKeywords;
    protected String metaDescription;
    @Column(unique = true)
    protected String slug;

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

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}
