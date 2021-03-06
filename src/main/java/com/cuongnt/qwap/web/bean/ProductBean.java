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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    
    private boolean showOnlyHot = false;
    
    
    // order ...
    private String orderField;
    private boolean orderAsc = true;

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
     *
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
        if (this.screenShots == null && current != null) {
            screenShots = current.getScreenshots();
        }
        return screenShots;
    }

    public void setScreenShots(List<ImageFile> screenShots) {
        this.screenShots = screenShots;
    }

    public void setAppFiles(Map<MobileType, AppFile> appFiles) {
        this.appFiles = appFiles;
    }

    public boolean isShowOnlyHot() {
        return showOnlyHot;
    }

    public void setShowOnlyHot(boolean showOnlyHot) {
        this.showOnlyHot = showOnlyHot;
    }

    @Override
    protected Map<String, Object> getMapFilter() {
        Map<String, Object> filter = new HashMap<>();
        if (showOnlyHot) {
            filter.put("hot", true);
        }
        return filter;
    }

    @Override
    protected boolean isOrderAsc() {
        return this.orderAsc;
    }

    @Override
    protected String getOrderField() {
        return this.orderField;
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
    protected void onBeforePersist() {
        super.onBeforePersist(); 
        validateProduct();
    }

    @Override
    protected void onBeforeUpdate() {
        super.onBeforeUpdate(); 
        validateProduct();
    }
    
    @Override
    protected void onUpdateSuccess() {
        super.onUpdateSuccess();
        this.screenShots = null;
    }

    @Override
    protected void onPersistSuccess() {
        super.onPersistSuccess();
        this.current = null;
        screenShots = null;
    }
    
    public void toggleHot(Long productId) {
        JsfUtil.processAction(e -> {
            productService.toggleHot(e);
        }, productId, MessageUtil.REQUEST_SUCCESS_MESSAGE, MessageUtil.REQUEST_FAIL_MESSAGE);
    }
    
    public void toggleEnable(Long productId) {
        JsfUtil.processAction(e -> {
            productService.toogleEnable(e);
        }, productId, MessageUtil.REQUEST_SUCCESS_MESSAGE, MessageUtil.REQUEST_FAIL_MESSAGE);
    }
    
    public void increatePriority(Long productId) {
        JsfUtil.processAction(e -> {
            productService.increatePriority(e);
        }, productId, MessageUtil.REQUEST_SUCCESS_MESSAGE, MessageUtil.REQUEST_FAIL_MESSAGE);
    }
    
    public void decreatePriority(Long productId) {
        JsfUtil.processAction(e -> {
            productService.decreatePriority(e);
        }, productId, MessageUtil.REQUEST_SUCCESS_MESSAGE, MessageUtil.REQUEST_FAIL_MESSAGE);
    }
    
    public void toggleOrder(String orderField) {
        if (this.orderField != null && this.orderField.equals(orderField)) {
            orderAsc = !orderAsc;
        }
        this.orderField = orderField;
    }
    
    private void validateProduct() {
        if (current != null) {
            boolean hasFile = false;
            for (AppFile appFile : current.getAppFiles()) {
                if ((appFile.getUrl()!= null && !appFile.getUrl().trim().isEmpty()) || appFile.getPart() != null || appFile.getTitle() != null) {
                    hasFile = true;
                    break;
                }
            }
            
            if (!hasFile) {
                throw new RuntimeException("your-product-must-have-at-least-one-file-or-link-to-download");
            }
        }
    }

}
