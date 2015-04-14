/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.ejb.impl;

import com.cuongnt.qwap.entity.Product;
import com.cuongnt.qwap.ejb.ProductService;
import javax.ejb.Stateless;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author richard
 */
@Stateless
public class ProductServiceBean extends AbstractFacadeBean<Product> implements ProductService {

    private static final long serialVersionUID = 8729142584799323263L;
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceBean.class);

    public ProductServiceBean() {
        super(Product.class);
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }

}
