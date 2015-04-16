/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.ejb.impl;

import com.cuongnt.qwap.ejb.ProductCategoryService;
import com.cuongnt.qwap.entity.ProductCategory;
import javax.ejb.Stateless;
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

}
