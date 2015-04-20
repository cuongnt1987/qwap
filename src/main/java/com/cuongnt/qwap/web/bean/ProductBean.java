/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.web.bean;

import com.cuongnt.qwap.ejb.BaseService;
import com.cuongnt.qwap.ejb.ProductCategoryService;
import com.cuongnt.qwap.ejb.ProductService;
import com.cuongnt.qwap.entity.AppFile;
import com.cuongnt.qwap.entity.ImageFile;
import com.cuongnt.qwap.entity.MobileType;
import com.cuongnt.qwap.entity.Product;
import com.cuongnt.qwap.entity.ProductCategory;
import com.cuongnt.qwap.entity.ProductType;
import com.cuongnt.qwap.util.AppUtil;
import com.cuongnt.qwap.web.util.JsfUtil;
import com.cuongnt.qwap.web.util.MessageUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
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

    private List<SelectItem> productTypeSelectItems;

    @EJB
    private ProductService productService;
    @EJB
    private ProductCategoryService productCategoryService;
    
    // ... processing screenshot and appfile.
    
    private List<ImageFile> screenShots;
    private Map<MobileType, AppFile> appFiles;
    
    @PostConstruct
    public void init() {
        String idStr = JsfUtil.getRequestParameter("id");
        if (idStr != null && !idStr.trim().isEmpty()) {
            Long id = null;
            try {
                id = Long.parseLong(idStr);
            } catch (NumberFormatException e) {
                logger.error("Can not parse id string param to Long", e);
            }
            
            if (id != null) {
                current = productService.find(id);
            }
        }
    }
    
    /**
     * Binding component for add and edit form
     * @return 
     */
    
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

    public void onChangeTitle() {
        if (current.getTitle() != null && !getCurrent().getTitle().trim().isEmpty()) {
            String slug = AppUtil.toSlug(getCurrent().getTitle().trim());
            if (productService.countBySlug(slug) > 0) {
                slug += "-1";
            }
            getCurrent().setSlug(slug);
        }
    }

    public List<SelectItem> getProductTypeSelectItems() {
        if (productTypeSelectItems == null) {
            productTypeSelectItems = new ArrayList<>();
            for (ProductType producType : ProductType.values()) {
                productTypeSelectItems.add(new SelectItem(producType, MessageUtil.getBundleMessage(producType.toString())));
            }
        }
        return productTypeSelectItems;
    }

    public List<ImageFile> getScreenShots() {
        if (this.screenShots == null && current != null) 
            screenShots = current.getScreenshots();
        return screenShots;
    }

    public void setScreenShots(List<ImageFile> screenShots) {
        this.screenShots = screenShots;
    }

    public Map<MobileType, AppFile> getAppFiles() {
        if (appFiles == null && current != null)
            appFiles = current.getAppFiles();
        return appFiles;
    }

    public void setAppFiles(Map<MobileType, AppFile> appFiles) {
        this.appFiles = appFiles;
    }
    
    public List<SelectItem> getProductCategorySelectItems() {
        if (getCurrent().getType() != null) {
            List<ProductCategory> categories = productCategoryService.getByType(getCurrent().getType());
            List<SelectItem> selectItems = new ArrayList<>();
            for (ProductCategory category : categories) {
                selectItems.add(new SelectItem(category, category.getTitle()));
            }
            return selectItems;
        }

        return null;
    }

    
    /**
     * Add new row for screen short
     */
    public void addScreenshort() {
        ImageFile image = new ImageFile();
        image.setProduct(current);
        this.getScreenShots().add(image);
        current.setScreenshots(screenShots);
        //current.getScreenshots().add(new ImageFile());
    }

    public void removeScreenshort(ActionEvent event) {
        if (current != null) {
            List<ImageFile> images = new ArrayList<>();
            for (ImageFile image : this.screenShots) {
                if (!image.isSelected()) {
                    images.add(image);
                }
            }
            this.setScreenShots(images);
            current.setScreenshots(images);
        }
    }    

    @Override
    protected void onUpdateSuccess() {
        super.onUpdateSuccess();
        this.screenShots = null;
    }
    
    
    
}
