/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.ejb;

import com.cuongnt.qwap.entity.Product;
import com.cuongnt.qwap.entity.ProductType;
import java.util.List;
import javax.persistence.TypedQuery;

/**
 *
 * @author richard
 */
public interface ProductService extends BaseService<Product> {

    public Product findBySlug(String slug);

    public int countBySlug(String slug);

    public List<Product> getTopDownload(int numberOfItems, ProductType type);

    public List<Product> getTopHot(int numberOfItems, ProductType type);

    public List<Product> getTopLike(int numberOfItems, ProductType type);

    public List<Product> getTopNew(int numberOfItems, ProductType type);

    public List<Product> getTopView(int numberOfItems, ProductType type);

    public void updateViewCount(Long productId);

    public void updateLikeCount(Long productId);

    public void updateDownCount(Long productId);
    
    public List<Product> getRelateProduct(Product product, int numberOfItems);
}
