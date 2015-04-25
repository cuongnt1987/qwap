/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.web.bean;

import com.cuongnt.qwap.checker.MobileChecker;
import com.cuongnt.qwap.ejb.ProductCategoryService;
import com.cuongnt.qwap.ejb.ProductService;
import com.cuongnt.qwap.entity.Product;
import com.cuongnt.qwap.entity.ProductCategory;
import com.cuongnt.qwap.entity.ProductType;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author richard
 */
@Named
@ViewScoped
public class ViewAppCategoryBean implements Serializable {

    private static final long serialVersionUID = 3038234833985592638L;
    private static final Logger logger = LoggerFactory.getLogger(ViewAppCategoryBean.class);

    private String slug;
    private ProductCategory category;
    private int numberOfItems = 10;
    private List<Product> products;
    private List<ProductCategory> listGameCategory;
    private List<ProductCategory> listAppCategory;

    @EJB
    private ProductCategoryService categoryService;
    @EJB
    private ProductService productService;
    @Inject
    private MobileChecker mobileChecker;

    public ViewAppCategoryBean() {
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public ProductCategory getCategory() {
        if (slug != null && !slug.trim().isEmpty() && category == null) {
            try {
                category = categoryService.findBySlug(slug);
            } catch (Exception e) {
                logger.error("Error when load product category by slug", e);
            }

        }
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    public List<Product> getProducts() {
        try {
            products = productService.getByCategory(category, numberOfItems, mobileChecker);
        } catch (Exception e) {
            logger.error("Error when load product by category", e);
        }
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void loadMore() {
        numberOfItems += 10;
    }

    public List<ProductCategory> getListGameCategory() {
        if (listGameCategory == null) {
            try {
                listGameCategory = categoryService.getByType(ProductType.GAME);
            } catch (Exception e) {
                logger.error("Error when load game category");
            }
        }
        return listGameCategory;
    }

    public void setListGameCategory(List<ProductCategory> listGameCategory) {
        this.listGameCategory = listGameCategory;
    }

    public List<ProductCategory> getListAppCategory() {
        if (listAppCategory == null) {
            try {
                listAppCategory = categoryService.getByType(ProductType.APP);
            } catch (Exception e) {

                logger.error("Error when load app category");
            }
        }
        return listAppCategory;
    }

}
