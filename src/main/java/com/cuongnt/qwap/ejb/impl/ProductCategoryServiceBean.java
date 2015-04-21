/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.ejb.impl;

import com.cuongnt.qwap.ejb.ProductCategoryService;
import com.cuongnt.qwap.entity.ProductCategory;
import com.cuongnt.qwap.entity.ProductType;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author richard
 */
@Stateless
public class ProductCategoryServiceBean extends AbstractFacadeBean<ProductCategory> implements ProductCategoryService {

    private static final long serialVersionUID = -3745981918030224033L;
    private static final Logger logger = LoggerFactory.getLogger(ProductCategoryServiceBean.class);

    public ProductCategoryServiceBean() {
        super(ProductCategory.class);
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public List<ProductCategory> getByType(ProductType type) {
        TypedQuery<ProductCategory> q = em.createNamedQuery("ProductCategory.getByType", ProductCategory.class);
        q.setParameter("type", type);
        return q.getResultList();
    }

    @Override
    protected void onAfterUpdate(ProductCategory entity) {
        super.onAfterUpdate(entity);
        saveFile(entity.getThumbnail());
    }

    @Override
    public ProductCategory findBySlug(String slug) {
        TypedQuery<ProductCategory> q = em.createNamedQuery("ProductCategory.findBySlug", ProductCategory.class);
        q.setParameter("slug", slug);
        return q.getSingleResult();
    }

    @Override
    public int countBySlug(String slug) {
        TypedQuery<Long> q = em.createNamedQuery("ProductCategory.countBySlug", Long.class);
        q.setParameter("slug", slug);
        return q.getSingleResult().intValue();
    }

}
