/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.ejb.impl;

import com.cuongnt.qwap.ejb.AppConfigService;
import com.cuongnt.qwap.entity.AppConfig;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author richard
 */
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class AppConfigServiceBean extends AbstractFacadeBean<AppConfig> implements AppConfigService {

    private static final long serialVersionUID = 6645247559864266826L;
    private static final Logger logger = LoggerFactory.getLogger(AppConfigServiceBean.class);

    public AppConfigServiceBean() {
        super(AppConfig.class);
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Lock(LockType.WRITE)
    @Override
    public AppConfig update(AppConfig entity) {
        return super.update(entity); 
    }

    @Override
    protected void onAfterUpdate(AppConfig entity) {
        super.onAfterUpdate(entity); 
        saveFile(entity.getAppLogo());
    }

    @Lock(LockType.WRITE)
    @Override
    public AppConfig persist(AppConfig entity) {
        return super.persist(entity); 
    }

    @Lock(LockType.READ)
    @Override
    public AppConfig find(Object id) {
        return super.find(id); 
    }

    @Lock(LockType.READ)
    @Override
    public AppConfig getCurrentConfig() {
        TypedQuery<AppConfig> q = em.createQuery("SELECT ac FROM AppConfig ac ORDER BY ac.createDate DESC", AppConfig.class);
        q.setMaxResults(1);
        AppConfig config = null;
        try {
            config = q.getSingleResult();
        } catch (NoResultException e) {
            logger.info("Has no config for app");
        } catch (Exception e) {
            logger.error("Error, fuck you", e);
        }
        return config;
    }

}
