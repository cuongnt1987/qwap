/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.web.bean;

import com.cuongnt.qwap.checker.MobileChecker;
import com.cuongnt.qwap.ejb.ProductService;
import com.cuongnt.qwap.entity.Product;
import com.cuongnt.qwap.entity.ProductType;
import java.io.Serializable;
import java.util.Collections;
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
public class ViewProductBean implements Serializable {

    private static final long serialVersionUID = -4991437328707690543L;
    private static final Logger logger = LoggerFactory.getLogger(ViewProductBean.class);

    // for view defail product
    private String slug;

    // product type ... 
    private ProductType type;

    // current product for view detail
    private Product currentProduct;

    // for view by viewType product
    private String viewType;

    private int viewCount = 10;

    private List<Product> mostViewProducts;

    private int hotCount = 10;
    private List<Product> mostHotProducts;

    // using for homepage
    private int downCount = 10;

    private List<Product> mostDownProducts;

    // using for homepage
    private int likeCount = 10;

    private List<Product> mostLikeProducts;

    // using for homepage
    private int newCount = 10;

    private List<Product> mostNewProducts;

    // using for slider
    private List<Product> sliderProducts;

    @Inject
    private MobileChecker mobileChecker;

    @EJB
    private ProductService productService;

    public ViewProductBean() {
    }

    public void loadMoreMostView() {
        viewCount += 10;
    }

    public void loadMoreMostHot() {
        hotCount += 10;
    }

    public void loadMoreMostLike() {
        likeCount += 10;
    }

    public void loadMoreMostDown() {
        downCount += 10;
    }

    public void loadMoreMostNew() {
        newCount += 10;
    }

    /* Getters and Setters */

    public int getHotCount() {
        return hotCount;
    }

    public void setHotCount(int hotCount) {
        this.hotCount = hotCount;
    }

    public List<Product> getMostHotProducts() {
        mostHotProducts = productService.getTopHot(hotCount, type, mobileChecker);
        return mostHotProducts;
    }

    public List<Product> getMostViewProducts() {
        mostViewProducts = productService.getTopView(viewCount, type, mobileChecker);
        return mostViewProducts;
    }

    public List<Product> getMostDownProducts() {
        mostDownProducts = productService.getTopDownload(downCount, type, mobileChecker);
        return mostDownProducts;
    }

    public List<Product> getMostLikeProducts() {
        mostLikeProducts = productService.getTopLike(likeCount, type, mobileChecker);
        return mostLikeProducts;
    }

    public List<Product> getMostNewProducts() {
        mostNewProducts = productService.getTopNew(newCount, type, mobileChecker);
        return mostNewProducts;
    }

    public List<Product> getSliderProducts() {
        sliderProducts = productService.getTopNew(6, null, mobileChecker);
        // Shuffle slider product list.
        Collections.shuffle(sliderProducts);
        return sliderProducts;
    }

    public Product getCurrentProduct() {
        if (slug != null && !slug.trim().isEmpty() && currentProduct == null) {
            try {
                currentProduct = productService.findBySlug(slug);
                productService.updateViewCount(currentProduct.getId());
            } catch (Exception e) {
                logger.error("Error when load product by slug", e);
            }

        }

        return currentProduct;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getViewType() {
        return viewType;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getDownCount() {
        return downCount;
    }

    public void setDownCount(int downCount) {
        this.downCount = downCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getNewCount() {
        return newCount;
    }

    public void setNewCount(int newCount) {
        this.newCount = newCount;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public List<Product> getRelateProducts() {
        if (currentProduct != null) {
            return productService.getRelateProduct(currentProduct, 5, mobileChecker);
        }
        return null;
    }

    public void likeProduct() {
        try {
            productService.updateLikeCount(currentProduct.getId());
            currentProduct = productService.find(currentProduct.getId());
        } catch (Exception e) {
            logger.error("Error when like product {}", currentProduct.getId(), e);
        }
    }
}
