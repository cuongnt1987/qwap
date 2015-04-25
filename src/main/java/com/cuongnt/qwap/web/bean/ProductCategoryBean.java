/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.web.bean;

import com.cuongnt.qwap.ejb.BaseService;
import com.cuongnt.qwap.ejb.ProductCategoryService;
import com.cuongnt.qwap.entity.ProductCategory;
import com.cuongnt.qwap.entity.ProductType;
import com.cuongnt.qwap.util.AppUtil;
import com.cuongnt.qwap.web.util.JsfUtil;
import com.cuongnt.qwap.web.util.MessageUtil;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
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
public class ProductCategoryBean extends AbstractManagedBean<ProductCategory> {

    private static final long serialVersionUID = 3523057023058768065L;
    private static final Logger logger = LoggerFactory.getLogger(ProductCategoryBean.class);

    private List<SelectItem> typeSelectItems;

    @EJB
    private ProductCategoryService categoryService;

    @Override
    protected ProductCategory initEntity() {
        return new ProductCategory();
    }

    @PostConstruct
    public void init() {
        String id = JsfUtil.getRequestParameter("id");
        if (id != null && !id.trim().isEmpty()) {
            try {
                current = categoryService.find(Long.parseLong(id));
            } catch (NumberFormatException e) {
                logger.error("Cannot parses product category id from request param.");
            } catch (Exception e) {
                logger.error("Error when load product category by request param.");
            }

        }
    }

    @Override
    protected BaseService<ProductCategory> getBaseService() {
        return categoryService;
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }

    public List<SelectItem> getTypeSelectItems() {
        if (typeSelectItems == null) {
            typeSelectItems = new ArrayList<>();
            for (ProductType t : ProductType.values()) {
                typeSelectItems.add(new SelectItem(t, MessageUtil.getBundleMessage(t.toString())));
            }
        }
        return typeSelectItems;
    }

    public void onChangeTitle() {
        if (current.getTitle() != null && !getCurrent().getTitle().trim().isEmpty()) {
            String slug = AppUtil.toSlug(getCurrent().getTitle().trim());
            if (categoryService.countBySlug(slug) > 0) {
                slug += "-1";
            }
            getCurrent().setSlug(slug);
        }
    }

}
