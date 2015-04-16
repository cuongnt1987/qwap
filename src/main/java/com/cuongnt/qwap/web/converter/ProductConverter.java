/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.web.converter;

import com.cuongnt.qwap.ejb.BaseService;
import com.cuongnt.qwap.ejb.ProductService;
import com.cuongnt.qwap.entity.Product;
import javax.ejb.EJB;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author richard
 */
@FacesConverter("productConverter")
public class ProductConverter extends AbstractEntityConverter<Product> {

    @EJB
    private ProductService service;

    @Override
    protected BaseService<Product> getBaseService() {
        return service;
    }

    @Override
    protected Class<Product> getEntityClass() {
        return Product.class;
    }

}
