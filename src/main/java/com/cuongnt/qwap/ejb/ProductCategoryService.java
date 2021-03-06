/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.ejb;

import com.cuongnt.qwap.entity.ProductCategory;
import com.cuongnt.qwap.entity.ProductType;
import java.util.List;

/**
 *
 * @author richard
 */
public interface ProductCategoryService extends BaseService<ProductCategory> {

    public List<ProductCategory> getByType(ProductType type);

    public ProductCategory findBySlug(String slug);

    public int countBySlug(String slug);
}
