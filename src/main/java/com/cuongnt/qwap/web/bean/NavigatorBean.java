/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.web.bean;

import com.cuongnt.qwap.ejb.BaseService;
import com.cuongnt.qwap.ejb.NavigatorService;
import com.cuongnt.qwap.entity.Navigator;
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
public class NavigatorBean extends AbstractManagedBean<Navigator> {

    private static final long serialVersionUID = 3821914257792949979L;
    private static final Logger logger = LoggerFactory.getLogger(NavigatorBean.class);

    private List<SelectItem> typeSelectItems;

    @EJB
    private NavigatorService navigatorService;

    @PostConstruct
    public void init() {
        String id = JsfUtil.getRequestParameter("id");
        if (id != null && !id.trim().isEmpty()) {
            try {
                current = navigatorService.find(Long.parseLong(id));
            } catch (Exception e) {
                logger.warn("can not load navigation with id = {}", id);
            }
        }
    }

    @Override
    protected Navigator initEntity() {
        return new Navigator();
    }

    @Override
    protected BaseService<Navigator> getBaseService() {
        return navigatorService;
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }

    public List<SelectItem> getTypeSelectItems() {
        if (typeSelectItems == null) {
            typeSelectItems = new ArrayList<>();
            for (Navigator.Type t : Navigator.Type.values()) {
                typeSelectItems.add(new SelectItem(t, MessageUtil.getBundleMessage(t.toString())));
            }
        }
        return typeSelectItems;
    }

    public boolean isShowContent() {
        if (this.current != null) {
            return current.getType() == Navigator.Type.Single;
        }
        return false;
    }

    public void onChangeTitle() {
        if (current.getTitle() != null && !getCurrent().getTitle().trim().isEmpty()) {
            String slug = AppUtil.toSlug(getCurrent().getTitle().trim());
            if (navigatorService.countBySlug(slug) > 0) {
                slug += "-1";
            }
            getCurrent().setSlug(slug);
        }
    }
}
