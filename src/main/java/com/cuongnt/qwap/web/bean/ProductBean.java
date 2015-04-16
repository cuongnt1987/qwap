/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.web.bean;

import com.cuongnt.qwap.ejb.BaseService;
import com.cuongnt.qwap.ejb.ProductService;
import com.cuongnt.qwap.entity.Product;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author richard
 */
@Named
@ViewScoped
public class ProductBean extends AbstractManagedBean<Product> {

    private static final long serialVersionUID = -2734153089447748548L;
    private static final Logger logger = LoggerFactory.getLogger(ProductBean.class);
    
    @EJB
    private ProductService productService;
    
    @Override
    protected Product initEntity() {
        return new Product();
    }
    
    @Override
    protected BaseService<Product> getBaseService() {
        return productService;
    }
    
    @Override
    protected Logger getLogger() {
        return logger;
    }
    
}
