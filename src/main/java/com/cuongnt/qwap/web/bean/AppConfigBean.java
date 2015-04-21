/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.web.bean;

import com.cuongnt.qwap.ejb.AppConfigService;
import com.cuongnt.qwap.ejb.NavigatorService;
import com.cuongnt.qwap.entity.AppConfig;
import com.cuongnt.qwap.entity.Navigator;
import com.cuongnt.qwap.web.util.JsfUtil;
import com.cuongnt.qwap.web.util.MessageUtil;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author richard
 */
@Named
@ApplicationScoped
public class AppConfigBean implements Serializable {

    private static final long serialVersionUID = 3729438171967591644L;
    private static final Logger logger = LoggerFactory.getLogger(AppConfigBean.class);

    @EJB
    private AppConfigService appConfigService;
    @EJB
    private NavigatorService navigatorService;

    public AppConfigBean() {
    }
    
    @PostConstruct
    public void init() {
        appConfig = appConfigService.getCurrentConfig();
        if (appConfig == null) appConfig = new AppConfig();
    }
    
    private AppConfig appConfig;

    public AppConfig getAppConfig() {
        return appConfig;
    }

    public void setAppConfig(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    public void persist() {
        JsfUtil.processAction(e -> {
            appConfig = appConfigService.persist(appConfig);
        },
                appConfig,
                MessageUtil.REQUEST_SUCCESS_MESSAGE,
                MessageUtil.REQUEST_FAIL_MESSAGE);
    }

    public void update() {
        JsfUtil.processAction(e -> {
            appConfig = appConfigService.update(appConfig);
        },
                appConfig,
                MessageUtil.REQUEST_SUCCESS_MESSAGE,
                MessageUtil.REQUEST_FAIL_MESSAGE);
    }
    
    public List<Navigator> getPostNavigator() {
        List<Navigator> postNavs = null;
        try {
            postNavs = navigatorService.getAppNavigators();
        } catch (Exception e) {
            logger.warn("Cannot get app navigator", e);
        }
        return postNavs;
    }

}
