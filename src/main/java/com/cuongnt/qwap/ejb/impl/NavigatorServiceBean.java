/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuongnt.qwap.ejb.impl;

import com.cuongnt.qwap.ejb.NavigatorService;
import com.cuongnt.qwap.entity.Navigator;
import com.cuongnt.qwap.entity.Post;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author richard
 */
@Stateless
public class NavigatorServiceBean extends AbstractFacadeBean<Navigator> implements NavigatorService {

    private static final long serialVersionUID = -5043121109735572700L;
    private static final Logger logger = LoggerFactory.getLogger(NavigatorServiceBean.class);

    public NavigatorServiceBean() {
        super(Navigator.class);
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public List<Navigator> getAppNavigators() {
        TypedQuery<Navigator> q = em.createQuery("SELECT n FROM Navigator n ORDER BY n.orderNumber ASC", Navigator.class);
        return q.getResultList();
    }

    @Override
    public Navigator findBySlug(String slug) {
        TypedQuery<Navigator> q = em.createNamedQuery("Navigator.findBySlug", Navigator.class);
        q.setParameter("slug", slug);
        return q.getSingleResult();
    }

    @Override
    public int countBySlug(String slug) {
        TypedQuery<Long> q = em.createNamedQuery("Navigator.countBySlug", Long.class);
        q.setParameter("slug", slug);
        return q.getSingleResult().intValue();
    }

}
