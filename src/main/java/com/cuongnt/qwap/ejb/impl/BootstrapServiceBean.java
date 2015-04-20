/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.ejb.impl;

import com.cuongnt.qwap.ejb.BootstrapService;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext; 

/**
 *
 * @author richard
 */
@Startup
@Singleton
public class BootstrapServiceBean implements BootstrapService {

    private static final long serialVersionUID = -3094434835334252545L;
    
    @PersistenceContext
    private EntityManager em;
    
    @PostConstruct
    public void initBootstrap() {
        em.getEntityManagerFactory().getCache().evictAll();
    }

}
