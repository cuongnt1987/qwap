/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.web.converter;

import com.cuongnt.qwap.ejb.BaseService;
import com.cuongnt.qwap.ejb.ProductCategoryService;
import com.cuongnt.qwap.entity.ProductCategory;
import javax.ejb.EJB;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author richard
 */
@FacesConverter("productCategoryConverter")
public class ProductCategoryConverter extends AbstractEntityConverter<ProductCategory> {

    @EJB
    private ProductCategoryService service;

    @Override
    protected BaseService<ProductCategory> getBaseService() {
        return service;
    }

    @Override
    protected Class<ProductCategory> getEntityClass() {
        return ProductCategory.class;
    }

}
